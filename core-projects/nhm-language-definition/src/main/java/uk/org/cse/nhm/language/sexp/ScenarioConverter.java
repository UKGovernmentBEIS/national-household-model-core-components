package uk.org.cse.nhm.language.sexp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;

public class ScenarioConverter {

    private final List<ElementMapper> mappers = new ArrayList<>();

    class ElementMapper {

        private final String xmlName;
        private final String sName;
        private String idRefAttribute = null;
        private final List<ContentMapper> content = new LinkedList<>();

        ElementMapper(final String xmlName, final String sName) {
            super();
            this.xmlName = xmlName;
            this.sName = sName;

            content.add(new ContentMapper() {
                @Override
                public void convert(final org.w3c.dom.Node child, final String path,
                        final InvocationBuilder builder) {
                    if (child instanceof Comment) {
                        builder.comment(((Comment) child).getTextContent());
                    }
                }
            });
        }

        public ElementMapper(final String from) {
            this(from, null);
        }

        ElementMapper content(final ContentMapper mapper) {
            content.add(mapper);
            return this;
        }

        ElementMapper attribute(
                final String xmlAttribute,
                final OutputRule output) {
            return content(
                    new AttributeContentMapper(xmlAttribute, output)
            );
        }

        ElementMapper element(
                final String[] elementNames,
                final OutputRule output) {
            return content(
                    new ElementContentMapper(elementNames, output)
            );
        }

        ElementMapper text(final OutputRule output) {
            return content(
                    new TextContentMapper(output)
            );
        }

        ElementMapper idref(final String xmlAttribute) {
            idRefAttribute = xmlAttribute;
            return this;
        }

        public void build() {

        }

        public Node convert(final Element element, final String pathToElement) {
            if (idRefAttribute != null) {
                return Atom.create("#" + element.getAttribute(idRefAttribute));
            } else {
                final InvocationBuilder builder = InvocationBuilder.create(sName);
                for (int i = 0; i < element.getAttributes().getLength(); i++) {
                    for (final ContentMapper c : content) {
                        c.convert(element.getAttributes().item(i), pathToElement, builder);
                    }
                }
                for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                    for (final ContentMapper c : content) {
                        c.convert(element.getChildNodes().item(i), pathToElement, builder);
                    }
                }

                return builder.build();
            }
        }

        public boolean canMap(final String path) {
            return path.endsWith("/" + xmlName);
        }
    }

    abstract class OutputRule {

        protected abstract void handleNode(final InvocationBuilder builder, final Node node);

        public void handleAttribute(final String attribute, final InvocationBuilder builder) {
            handleNode(builder, Atom.create(attribute));
        }

        public void handleElement(final Element element, final String pathToElement, final InvocationBuilder builder) {
            handleNode(
                    builder,
                    ScenarioConverter.this._convert(element, pathToElement + "/" + element.getNodeName()));
        }

        public void handleTextContent(final String textContent, final InvocationBuilder builder) {
            handleNode(builder, Atom.create(textContent));
        }
    }

    abstract class ContentMapper {

        public abstract void convert(final org.w3c.dom.Node child, final String path, final InvocationBuilder builder);

    }

    class TextContentMapper extends ContentMapper {

        private final OutputRule output;

        public TextContentMapper(final OutputRule output) {
            this.output = output;
        }

        @Override
        public void convert(final org.w3c.dom.Node element, final String path, final InvocationBuilder builder) {
            if (element instanceof Text) {
                output.handleTextContent(element.getTextContent(), builder);
            }
        }

    }

    class ElementContentMapper extends ContentMapper {

        private final ImmutableSet<String> elementNames;
        private final OutputRule output;

        public ElementContentMapper(final String[] elementNames, final OutputRule output) {
            this.elementNames = ImmutableSet.copyOf(elementNames);
            this.output = output;
        }

        @Override
        public void convert(final org.w3c.dom.Node node, final String path, final InvocationBuilder builder) {
            if (node instanceof Element) {
                final Element child = (Element) node;
                if (elementNames.contains(child.getNodeName())) {
                    output.handleElement(child, path, builder);
                }
            }
        }
    }

    class AttributeContentMapper extends ContentMapper {

        private final String xmlAttribute;
        private final OutputRule output;

        public AttributeContentMapper(final String xmlAttribute, final OutputRule output) {
            this.xmlAttribute = xmlAttribute;
            this.output = output;
        }

        @Override
        public void convert(final org.w3c.dom.Node node, final String path, final InvocationBuilder builder) {
            if (node instanceof Attr) {
                final Attr attr = (Attr) node;
                if (attr.getName().equals(xmlAttribute)) {
                    output.handleAttribute(attr.getValue(), builder);
                }
            }
        }
    }

    public ElementMapper map(final String from) {
        final ElementMapper elementMapper = new ElementMapper(from);
        mappers.add(elementMapper);
        return elementMapper;
    }

    public ElementMapper map(final String from, final String to) {
        final ElementMapper elementMapper = new ElementMapper(from, to);
        mappers.add(elementMapper);
        return elementMapper;
    }

    OutputRule named(final String name) {
        return new OutputRule() {

            @Override
            protected void handleNode(final InvocationBuilder builder,
                    final Node node) {
                builder.named(name, node);
            }
        };
    }

    OutputRule positional(final int integer) {
        return new OutputRule() {

            @Override
            protected void handleNode(final InvocationBuilder builder,
                    final Node node) {
                builder.position(integer, node);
            }
        };
    }

    OutputRule remainder() {
        return new OutputRule() {

            @Override
            protected void handleNode(final InvocationBuilder builder,
                    final Node node) {
                builder.remainder(node);
            }
        };
    }

    protected void sortMappings() {
        Collections.sort(mappers, new Comparator<ElementMapper>() {
            @Override
            public int compare(final ElementMapper o1, final ElementMapper o2) {
                // attempt to match longest names first
                return -Integer.compare(o1.xmlName.length(), o2.xmlName.length());
            }
        });
    }

    public Node convert(final Element source) {
        return _convert(source, "/" + source.getNodeName());
    }

    static class InvocationBuilder {

        private final String name;
        private final Multimap<String, Node> named = ArrayListMultimap.create();
        private final Multimap<Integer, Node> positioned = ArrayListMultimap.create();

        private final List<Node> remainder = new ArrayList<>();

        private final Map<Node, com.larkery.jasb.sexp.Comment> comments = new IdentityHashMap<>();

        private final StringBuffer commentBuffer = new StringBuffer();

        boolean atHead = true;

        private Optional<String> headComment = Optional.absent();

        public InvocationBuilder(final String name) {
            this.name = name;
        }

        public void comment(final String textContent) {
            commentBuffer.append(textContent);
        }

        private Node shiftComment(final Node node) {
            if (commentBuffer.length() > 0) {
                if (atHead) {
                    headComment = Optional.of(commentBuffer.toString());
                } else {
                    comments.put(node, com.larkery.jasb.sexp.Comment.create(commentBuffer.toString()));
                }

                commentBuffer.setLength(0);
            }

            atHead = false;

            return node;
        }

        public static InvocationBuilder create(final String name) {
            return new InvocationBuilder(name);
        }

        public InvocationBuilder named(final String key, final Node node) {
            named.put(key, shiftComment(node));
            return this;
        }

        public InvocationBuilder position(final int position, final Node node) {
            positioned.put(position, shiftComment(node));
            return this;
        }

        public InvocationBuilder remainder(final Node node) {
            remainder.add(shiftComment(node));
            return this;
        }

        public Node interpolateComments(final Collection<Node> nodes) {
            if (nodes.size() == 1) {
                return nodes.iterator().next();
            } else {
                final Seq.Builder builder = Seq.builder(null, Delim.Bracket);
                for (final Node node : nodes) {
                    if (comments.containsKey(node)) {
                        builder.add(comments.get(node));
                    }
                    builder.add(node);
                }
                return builder.build(null);
            }
        }

        public Node build() {
            final Seq.Builder builder = Seq.builder(null, Delim.Paren);

            if (name != null) {
                builder.add(Atom.create(name));
            }

            if (headComment.isPresent()) {
                builder.add(com.larkery.jasb.sexp.Comment.create(headComment.get()));
            }

            for (final String s : named.keySet()) {
                final Node node = interpolateComments(named.get(s));

                if (comments.containsKey(node)) {
                    builder.add(comments.get(node));
                }

                builder.add(s, node);
            }

            for (final int i : new TreeSet<>(positioned.keySet())) {
                final Node node = interpolateComments(positioned.get(i));

                if (comments.containsKey(node)) {
                    builder.add(comments.get(node));
                }

                builder.add(node);
            }

            for (final Node node : remainder) {
                if (comments.containsKey(node)) {
                    builder.add(comments.get(node));
                }
                builder.add(node);
            }

            if (commentBuffer.length() > 0) {
                builder.add(com.larkery.jasb.sexp.Comment.create(commentBuffer.toString()));
                commentBuffer.setLength(0);
            }

            final Seq build = builder.build(null);
            if (name == null && build.size() == 1) {
                return build.getHead();
            } else {
                return build;
            }
        }
    }

    public Node _convert(final Element source, final String path) {
        for (final ElementMapper mapping : mappers) {
            if (mapping.canMap(path)) {
                return mapping.convert(source, path);
            }
        }
        //TODO I guess if we couldn't map this, but it has some things inside it,
        // we could just map the children out as a sequence of nodes
        System.err.println("No map for " + path + " so I am skipping to its contents.");
        final Seq.Builder builder = Seq.builder(null, Delim.Bracket);
        final NodeList childNodes = source.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            final org.w3c.dom.Node domNode = childNodes.item(i);
            if (domNode instanceof Element) {
                final Element child = (Element) domNode;
                final Node node = _convert(child, path + "/" + child.getNodeName());
                builder.add(node);
            }
        }
        final Seq seq = builder.build(null);
        if (seq.size() == 1) {
            return seq.getHead();
        } else {
            return seq;
        }
    }
}
