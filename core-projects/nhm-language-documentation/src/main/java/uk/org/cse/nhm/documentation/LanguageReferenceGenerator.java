package uk.org.cse.nhm.documentation;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;
import com.larkery.jasb.bind.BindRemainingArguments;
import com.larkery.jasb.bind.Identity;
import com.larkery.jasb.io.IModel;
import com.larkery.jasb.io.IModel.IArgument;
import com.larkery.jasb.io.IModel.IAtomModel;
import com.larkery.jasb.io.IModel.IElement;
import com.larkery.jasb.io.IModel.IInvocationModel;
import com.larkery.jasb.io.IWriter;
import com.larkery.jasb.io.impl.JASB;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.PrettyPrinter;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;
import com.larkery.jasb.sexp.parse.IMacro;
import com.larkery.jasb.sexp.parse.MacroExpander;
import com.larkery.jasb.sexp.parse.MacroModel;
import com.larkery.jasb.sexp.parse.Parser;

import uk.org.cse.nhm.documentation.Exemplar.Example;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.ProducesTags;
import uk.org.cse.nhm.language.definition.ProducesTags.Tag;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.TopLevel;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.sexp.Defaults;
import uk.org.cse.nhm.language.validate.contents.ForbidChild;
import uk.org.cse.nhm.language.validate.contents.RequireParent;
import uk.org.cse.nhm.macros.ExtraMacros;

/**
 * Tool which generates the language reference, in docbook format.
 *
 */
public class LanguageReferenceGenerator {

    private DocbookWriter writer;

    private final IModel model;

    private final Exemplar exemplar;

    private final Multimap<String, String> tagsBySource = HashMultimap.create();

    final Map<String, String> specialCaseDocs;

    private final IWriter atomWriter;

    private static Map<String, String> loadExtraDocs(final Path path) throws IOException {
        /*
		 * Load special case docs from the typeDocs.s file.
         */
        Node node;
        try {
            node = Node.copy(Parser.source(("bogus:uri"),
                    new InputStreamReader(Files.newInputStream(path)),
                    IErrorHandler.SLF4J));
        } catch (final UnfinishedExpressionException e) {
            throw new RuntimeException(e);
        }

        final ImmutableMap.Builder<String, String> b = ImmutableMap.builder();

        for (final Node sub : ((Seq) node)) {
            if (sub instanceof Seq) {
                final Invocation inv = Invocation.of(sub, IErrorHandler.SLF4J);

                for (final Node type : ((Seq) (inv.arguments.get("types")))) {
                    b.put(((Atom) type).getValue(),
                            org.apache.commons.lang3.StringEscapeUtils.escapeXml(((Atom) inv.arguments.get("doc")).getValue()));
                }
            }
        }

        return b.build();
    }

    private final IdentityHashMap<IElement, String> names;

    private final Multimap<String, MacroExample> macroExamples;

    private final ImmutableSet<IElement> elementsUsedOnlyOnce;

    private static final CharMatcher STRIPCHARS
            = (CharMatcher.JAVA_LETTER.or(CharMatcher.anyOf("-."))).negate();

    private static String wrangle(final String name) {
        final String symbold
                = name.replace("<", "less")
                        .replace("=", "eq")
                        .replace(">", "gt")
                        .replace("*", "star")
                        .replace("/", "slash")
                        .replace("~", "tilde")
                        .replace("^", "hat")
                        .replace("+", "plus")
                        .replace("%", "per");
        return STRIPCHARS.replaceFrom(symbold, '-').toLowerCase();
    }

    static class MacroExample {

        public final Node before;
        public final String description;
        private final String title;

        public MacroExample(final String title, final String description, final Node before) {
            super();
            this.title = title;
            this.before = before;
            this.description = description;
        }
    }

    public LanguageReferenceGenerator(
            final JASB context,
            final Exemplar exemplar,
            final Map<String, String> docs,
            final Multimap<String, MacroExample> macroExamples) {
        this.macroExamples = macroExamples;
        this.model = context.getModel();
        this.atomWriter = context.getWriter();
        this.exemplar = exemplar;
        this.specialCaseDocs = docs;

        final Multimap<String, IElement> elementsByName = LinkedListMultimap.create();

        final HashSet<IElement> elementsUsedOnlyOnce = new HashSet<>();
        final HashSet<IElement> elementsUsedMoreThanOnce = new HashSet<>();
        for (final IInvocationModel inv : this.model.getInvocations()) {
            final HashSet<IElement> seenHere = new HashSet<>();

            for (final IArgument arg : inv.getArguments()) {
                for (final IElement lv : arg.getLegalValues()) {
                    if (seenHere.contains(lv)) {
                        continue;
                    }
                    seenHere.add(lv);

                    if (elementsUsedMoreThanOnce.contains(lv)) {
                    } else if (elementsUsedOnlyOnce.contains(lv)) {
                        elementsUsedMoreThanOnce.add(lv);
                        elementsUsedOnlyOnce.remove(lv);
                    } else {
                        elementsUsedOnlyOnce.add(lv);
                    }
                }
            }
        }
        // some things should not be embedded into the place where they are used
        {
            final Iterator<IElement> iterator = elementsUsedOnlyOnce.iterator();
            while (iterator.hasNext()) {
                final IElement e = iterator.next();
                if (e.getJavaType().isAnnotationPresent(TopLevel.class)
                        || e.getJavaType().isAnnotationPresent(Obsolete.class)) {
                    iterator.remove();
                }
            }
        }

        this.elementsUsedOnlyOnce = ImmutableSet.copyOf(elementsUsedOnlyOnce);

        for (final IElement e : this.model.getElements()) {
            final String baseName = e.getName();

            final String mangledName = wrangle(baseName);

            elementsByName.put(mangledName, e);
        }

        for (final String key : ImmutableSet.copyOf(elementsByName.keySet())) {
            final Collection<IElement> vals = ImmutableList.copyOf(elementsByName.get(key));
            if (vals.size() > 1) {
                System.out.println("Deduplicate category " + key);
                // collision, try dedupping with the category name
                elementsByName.removeAll(key);
                for (final IElement e : vals) {
                    final String category = wrangle(getCategoryForElement(e).name());
                    elementsByName.put(category + "." + key, e);
                }
            }
        }

        for (final String key : ImmutableSet.copyOf(elementsByName.keySet())) {
            final Collection<IElement> vals = ImmutableList.copyOf(elementsByName.get(key));
            if (vals.size() > 1) {
                System.out.println("Deduplicate class " + key);
                // collision, try dedupping with the classname
                elementsByName.removeAll(key);
                for (final IElement e : vals) {
                    final String category = STRIPCHARS.replaceFrom(e.getJavaType().getSimpleName(), '-').toLowerCase();
                    elementsByName.put(category + "." + key, e);
                }
            }
        }
        names = new IdentityHashMap<IModel.IElement, String>();

        for (final String key : ImmutableSet.copyOf(elementsByName.keySet())) {
            final Collection<IElement> vals = elementsByName.get(key);
            if (vals.size() > 1) {
                throw new RuntimeException("Unresolvable annoying duplicate names: " + key + " -> " + vals);
            } else {
                for (final IElement e : vals) {
                    names.put(e, "ref." + key);
                    if (key.isEmpty()) {
                        throw new RuntimeException("Empty key for " + e.getJavaType() + " abstract="
                                + String.valueOf(Modifier.isAbstract(e.getJavaType().getModifiers())) + " "
                                + e.getClass()
                        );
                    }
                }
            }
        }
    }

    /*
	 * See the pom.xml for how to use this main method.
     */
    public static void main(final String[] args) throws JAXBException, IOException {
        if (args.length < 4) {
            throw new IllegalArgumentException("Language Reference Generator expects:\n"
                    + "\tpath to examples\n"
                    + "\tpath to extra documentation\n"
                    + "\tpath to macro documentation\n"
                    + "\toutput docbook file");
        }

        final JASB jasb = Defaults.withExtraClasses(ExampleElementClass.class);
        Files.createDirectories(Paths.get(args[3]).getParent());
        new LanguageReferenceGenerator(
                jasb,
                new Exemplar(Paths.get(args[0]), jasb),
                loadExtraDocs(Paths.get(args[1])),
                loadMacroDocs(Paths.get(args[2])
                ))
                .writeDocumentation(args[3]);
    }

    private static Multimap<String, MacroExample> loadMacroDocs(final Path path) {
        final ImmutableMultimap.Builder<String, MacroExample> b = ImmutableMultimap.builder();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                    if (file.getFileName().toString().endsWith(".s")) {
                        try {
                            final Node n = Node.copy(Parser.source(file.toString(), Files.newBufferedReader(file, StandardCharsets.UTF_8), IErrorHandler.IGNORE));

                            final Invocation inv = Invocation.of(n, IErrorHandler.IGNORE);

                            if (inv != null) {
                                final String caption = ((Atom) inv.arguments.get("caption")).getValue();
                                final String description = ((Atom) inv.arguments.get("description")).getValue();

                                b.put(inv.name, new MacroExample(caption, description, inv.remainder.get(0)));
                            }
                        } catch (final UnfinishedExpressionException e) {

                        }
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (final IOException ex) {
        }
        return b.build();
    }

    @uk.org.cse.nhm.language.definition.Category(uk.org.cse.nhm.language.definition.Category.CategoryType.EXAMPLE)
    @Bind("example-element")
    @Doc("<emphasis>After the name of the element, there is a plain English description of what it does."
            + "Then there follows a list of arguments that the element can use.</emphasis>")
    public static class ExampleElementClass extends XElement {

        private List<XBoolean> positionalArgument = new ArrayList<>();

        private List<Double> remainder = new ArrayList<>();

        private String identity;

        public ExampleElementClass() {

        }

        @BindNamedArgument("a-named-argument")
        @Identity
        @Doc({"<emphasis>The first things listed are named arguments; they are broken down by name.",
            "This argument can also be used as a cross-referencing identity, so that fact is mentioned.",
            "The legal values statement describes what kinds of thing can be used, and how many of them.</emphasis>"})
        public String getIdentity() {
            return identity;
        }

        public void setIdentity(final String identity) {
            this.identity = identity;
        }

        @Doc({"<emphasis>After any named arguments, individual positional arguments are given.",
            "In this case, a sequence of boolean values can be provided in position zero (by enclosing them in square brackets)",
            "so all the boolean values are linked here.",
            "</emphasis>"})
        @BindPositionalArgument(0)
        public List<XBoolean> getPositionalArgument() {
            return positionalArgument;
        }

        public void setPositionalArgument(final List<XBoolean> positionalArgument) {
            this.positionalArgument = positionalArgument;
        }

        @Doc({"<emphasis>Finally, if there is a remainder argument it is documented; the remainder argument",
            "gets passed any things which are leftover after considering the previous arguments",
            "and is mainly used in things like choice, which can contain many actions to choose between.</emphasis>"
        })
        @BindRemainingArguments
        public List<Double> getRemainder() {
            return remainder;
        }

        public void setRemainder(final List<Double> remainder) {
            this.remainder = remainder;
        }
    }

    public void writeDocumentation(final String output) throws IOException {

        this.writer = new DocbookWriter(new PrintWriter(new File(output)));
        // first emit the chapter about all the elements
        writer.startPart("Language Reference");

        writeMacroDocumentation();

        writeLanguageElementDocumentation();

        writeTransactionTagsDocumentation();

        writer.endPart();
        writer.pw.flush();

        final Path resolve = Paths.get(output).getParent().resolve("xrefs.tsv");
        try (final PrintWriter pw = new PrintWriter(Files.newOutputStream(
                resolve
        ))) {
            pw.println("element\txref");
            for (final Map.Entry<IElement, String> e : names.entrySet()) {
                pw.println(String.format("%s\t%s", e.getKey().getName(), e.getValue()));
            }
        }

        System.err.println("xrefs generated in " + resolve);
    }

    private void writeTransactionTagsDocumentation() {
        writer.startChapter("Transaction Tags", "ch--Tags");

        writer.writeParagraph(
                "Several parts of the simulation generate transactions, "
                + "which have tags associated with them for reporting and analysis. "
                + "The following list identifies things which produce tagged transactions, by the tag produced.");

        writer.startVariableList("Sources of transactions, by tag");
        for (final String tag : tagsBySource.keySet()) {
            writer.startVarListEntry(tag);
            writer.startSimpleList();
            for (final String s : tagsBySource.get(tag)) {
                writer.writeSimpleListMember(writer.createXrefToKey(s));
            }
            writer.endSimpleList();
            writer.endVarListEntry();
        }

        writer.endVariableList();

        writer.endChapter(); // transaction tags
    }

    private void writeLanguageElementDocumentation() {
        writer.startChapter("Language Elements");

        writer.writeParagraph(
                "This section of the manual documents all of the terms in the NHM language, "
                + "and indexes them by transaction tag. First, here is some example documentation,"
                + " with explanation in italics.");

        // group by category
        final TreeMultimap<CategoryType, IElement> byCategory = TreeMultimap.create();

        for (final IElement invocation : model.getElements()) {
            final CategoryType type = getCategoryForElement(invocation);

            byCategory.put(type, invocation);
        }

        for (final CategoryType c : byCategory.keySet()) {
            writer.startSection(c.display, "cat--" + c.display, Optional.<String>absent());
            writer.writeParagraph(c.description);

            for (final IElement element : Sets.difference(byCategory.get(c), elementsUsedOnlyOnce)) {
                writeElement(element);
            }

            writer.endSection(); // for category
        }

        writer.endChapter(); //"Language Elements"
    }

    private static CategoryType getCategoryForElement(final IElement invocation) {
        if (invocation.getJavaType().isAnnotationPresent(Obsolete.class)) {
            return CategoryType.OBSOLETE;
        }
        final Category category = invocation.getJavaType().getAnnotation(Category.class);
        final CategoryType type = category == null ? CategoryType.MISC : category.value();
        return type;
    }

    private void writeMacroDocumentation() {
        writer.startChapter("Standard Macros");

        writer.writeParagraph(
                "This section of the manual documents all of the standard macros in the NHM language; "
                + "macros look like language elements, but actually transform the input in some way before it "
                + "is processed by the simulation. Most macros have names starting with tilde (~), to distinguish them."
        );

        for (final IMacro macro : ExtraMacros.DEFAULT) {
            if (macro.getName().startsWith("~")) {
                writeMacro(macro);
            }
        }

        writer.endChapter();
    }

    private void writeMacro(final IMacro macro) {
        writer.startSection(macro.getName(), "macro." + wrangle(macro.getName()), Optional.of(macro.getName()));

        final MacroModel model = macro.getModel();

        writer.writeParagraph(dot(model.description));

        writer.startVariableList("Arguments");

        for (final String s : model.requiredKeys.keySet()) {
            writer.startVarListEntry(s + ":");
            writer.writeParagraph(dot(model.requiredKeys.get(s)));
            writer.writeParagraph("This argument is required.");
            writer.endVarListEntry();
        }

        for (final String s : model.allowedKeys.keySet()) {
            writer.startVarListEntry(s + ":");
            writer.writeParagraph(dot(model.allowedKeys.get(s)));
            writer.endVarListEntry();
        }

        int counter = 0;
        for (final String s : model.requiredPos) {
            writer.startVarListEntry(String.format("%s unnamed argument", Ordinator.ordinal(counter)));

            writer.writeParagraph(dot(s));
            writer.writeParagraph("This argument is required.");

            writer.endVarListEntry();
            counter++;
        }

        for (final String s : model.allowedPos) {
            writer.startVarListEntry(String.format("%s unnamed argument", Ordinator.ordinal(counter)));
            writer.writeParagraph(dot(s));

            writer.endVarListEntry();
            counter++;
        }

        if (model.restNum.isPresent()) {
            writer.startVarListEntry("subsequent arguments");
            writer.writeParagraph(dot(model.restNum.get()));
            writer.endVarListEntry();
        }

        writer.endVariableList();

        for (final MacroExample example : macroExamples.get(macro.getName())) {
            final String before = PrettyPrinter.print(example.before);
            final String after = PrettyPrinter.print(MacroExpander.expand(ImmutableList.of(macro), example.before, IErrorHandler.IGNORE));

            writer.writeMacroExample(example.title, example.description, before, after);
        }

        writer.endSection();
    }

    private final HashSet<IElement> alreadyWritten = new HashSet<>();

    private void writeElement(final IElement element) {
        if (alreadyWritten.contains(element)) {
            return;
        }
        alreadyWritten.add(element);
        final String key = getKey(element);

        writer.startSection(
                cleanName(element.getName()),
                key,
                Optional.of(cleanName(element.getName())));

        writer.addIndexTerm(element.getName());

        if (element.getJavaType().isAnnotationPresent(Obsolete.class)) {
            writer.startFormalParagraph("Obsolete. This element will be removed in a future version of the NHM.");

            final Obsolete obsolete = element.getJavaType().getAnnotation(Obsolete.class);

            if (obsolete.reason().equals("")) {
                writer.writeParagraph(obsolete.reason());
            }

            if (obsolete.inFavourOf().length > 0) {
                writer.startFormalParagraph("Use these instead:");
                writer.startSimpleList();
                for (final Class<?> c : obsolete.inFavourOf()) {
                    for (final IElement other : model.getElements()) {
                        if (other.getJavaType().equals(c)) {
                            writer.writeSimpleListMember(writer.createXrefToKey(getKey(other)));
                        }
                    }
                }
                writer.endFormalParagraph();
                writer.endSimpleList();
            }

            if (!obsolete.compatibility().equals("")) {
                writer.writeParagraph(obsolete.compatibility());
            }
            writer.endFormalParagraph();
        }

        if (hasDoc(element.getJavaType())) {
            writer.writeParagraph(doc(element.getJavaType()));
        }

        if (element.getJavaType().isAnnotationPresent(Unsuitability.class)) {
            final Unsuitability unsuitability = element.getJavaType().getAnnotation(Unsuitability.class);
            writer.startFormalParagraph("Suitability");
            if (unsuitability.alwaysSuitable()) {
                writer.writeParagraph("This action is always suitable and will succeed on all houses.");

            } else {
                writer.writeParagraph("This action cannot affect a house where any of the following conditions are true:");

                writer.startItemizedList();
                for (final String v : unsuitability.value()) {
                    writer.startListItem();
                    writer.writeParagraph(v);
                    writer.endListItem();
                }
                writer.endItemizedList();
            }
            writer.endFormalParagraph();
        }

        final Comparator<IArgument> argumentComparator = new Comparator<IModel.IArgument>() {
            @Override
            public int compare(final IArgument o1, final IArgument o2) {
                final boolean fstNamed = o1.getName().isPresent(),
                        sndNamed = o2.getName().isPresent(),
                        fstPos = o1.getPosition().isPresent(),
                        sndPos = o2.getPosition().isPresent();

                if (fstNamed && sndNamed) {
                    return o1.getName().get().compareTo(o2.getName().get());
                } else if (fstPos && sndPos) {
                    return Integer.compare(o1.getPosition().get(),
                            o2.getPosition().get());
                } else if (fstNamed) {
                    return -1;
                } else if (sndNamed) {
                    return 1;
                } else if (fstPos) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        if (element instanceof IInvocationModel) {
            final IInvocationModel invocation = (IInvocationModel) element;

            if (!invocation.getArguments().isEmpty()) {
                writer.startVariableList("Arguments");

                for (final IArgument argument : ImmutableSortedSet.copyOf(
                        argumentComparator,
                        invocation.getNamedArguments())) {
                    writeArgument(argument);
                }

                for (final IArgument argument : ImmutableSortedSet.copyOf(
                        argumentComparator,
                        invocation.getPositionalArguments())) {
                    writeArgument(argument);
                }

                if (invocation.getRemainderArgument().isPresent()) {
                    writeArgument(invocation.getRemainderArgument().get());
                }

                writer.endVariableList();
            }

        } else if (element instanceof IAtomModel) {
            // not sure whether we want to do anything with atom
            final IAtomModel am = (IAtomModel) element;
            if (!am.isBounded()) {
                writer.startFormalParagraph("Example values");
                writer.startSimpleList();
                for (final String s : am.getLiterals()) {
                    writer.writeSimpleListMember(StringEscapeUtils.escapeXml(s));
                }
                writer.endSimpleList();
                writer.endFormalParagraph();
            }
        }

        if (hasTransactionTags(element.getJavaType())) {
            final ProducesTags pt = element.getJavaType().getAnnotation(ProducesTags.class);
            writer.startVariableList("Transaction Tags");
            for (final Tag tag : pt.value()) {
                writer.writeVarListEntry(
                        tag.value(),
                        Joiner.on("\n").join(tag.detail()));
                tagsBySource.put(tag.value(), key);
            }
            writer.endFormalParagraph();
        }

        if (element.getJavaType().isAnnotationPresent(SeeAlso.class)) {
            writer.startFormalParagraph("See Also");
            final Class<?>[] others = element.getJavaType().getAnnotation(SeeAlso.class).value();
            writer.startSimpleList();
            for (final Class<?> c : others) {
                for (final IElement other : model.getElements()) {
                    if (other.getJavaType().equals(c)) {
                        writer.writeSimpleListMember(writer.createXrefToKey(getKey(other)));
                    }
                }
            }
            writer.endSimpleList();
            writer.endFormalParagraph();
        }

        if (element.getJavaType().isAnnotationPresent(RequireParent.class)) {
            writeRestrictions(
                    element,
                    element.getJavaType().getAnnotation(RequireParent.class).value(),
                    "Usage Restrictions",
                    "This element can only be used somewhere within the following other elements:");
        }

        if (element.getJavaType().isAnnotationPresent(ForbidChild.class)) {
            writeRestrictions(
                    element,
                    element.getJavaType().getAnnotation(ForbidChild.class).value(),
                    "Content Restrictions",
                    "This element cannot contain any of the following elements:");
        }

        if (element.getJavaType().isAnnotationPresent(ReturnsEnum.class)) {
            writer.startFormalParagraph("Categorical Variable Values");
            writer.writeParagraph("This element returns a categorical variable - the possible values it can take are:");
            writer.startSimpleList();
            final Class<?> c = element.getJavaType().getAnnotation(ReturnsEnum.class).value();
            for (final Object o : c.getEnumConstants()) {
                writer.writeSimpleListMember(String.valueOf(o));
            }
            writer.endSimpleList();
            writer.endFormalParagraph();
        }

        final List<Exemplar.Example> examples = exemplar.getExamples(element.getJavaType());
        if (!examples.isEmpty()) {
            for (final Example e : examples) {
                writer.writeExample(e.name, e.description, e.source, ImmutableList.of(e.docbookXML));
            }
        }

        if (element instanceof IInvocationModel) {
            for (final IArgument arg : ((IInvocationModel) element).getArguments()) {
                for (final IElement e : arg.getLegalValues()) {
                    if (elementsUsedOnlyOnce.contains(e)) {
                        writeElement(e);
                    }
                }
            }
        }

        writer.endSection();
    }

    private void writeRestrictions(
            final IElement element,
            final Class<?>[] classes,
            final String title,
            final String description) {
        if (classes.length == 0) {
            writer.startFormalParagraph(title);
            writer.writeParagraph(description);
            final Set<Class<?>> others = ImmutableSet.copyOf(element.getJavaType().getAnnotation(RequireParent.class).value());
            writer.startSimpleList();

            for (final Class<?> c : others) {
                for (final IElement other : model.getElements()) {
                    if (c.isAssignableFrom(other.getJavaType())) {
                        writer.writeSimpleListMember(writer.createXrefToKey(getKey(other)));
                    }
                }
            }

            writer.endSimpleList();
            writer.endFormalParagraph();
        }
    }

    private void writeArgument(final IArgument argument) {
        if (argument.isNamedArgument()) {
            writer.startVarListEntry(argument.getName().get() + ":");
        } else if (argument.isPositionalArgument()) {
            writer.startVarListEntry(String.format("%s unnamed argument",
                    Ordinator.ordinal(argument.getPosition().get())));
        } else if (argument.isRemainderArgument()) {
            writer.startVarListEntry("subsequent arguments");
        } else {
            throw new IllegalArgumentException(argument + " is faulty");
        }
        if (hasDoc(argument.getReadMethod())) {
            writer.writeParagraph(doc(argument.getReadMethod()));
        }

        if (argument.isIdentity()) {
            writer.writeParagraph("If specified, this value can be used as a cross-referencing identifier.");
        }
        writeLegalValues(argument);
        writer.endVarListEntry();
    }

    private String cleanName(final String name) {
        if (name.startsWith("X ")) {
            return name.substring(2);
        } else {
            return name;
        }
    }

    private String getKey(final IElement element) {
        final String val = names.get(element);
        if (val == null) {
            throw new RuntimeException("No name for " + element);
        }
        return val;
    }

    private boolean hasTransactionTags(final Class<?> javaType) {
        return javaType.isAnnotationPresent(ProducesTags.class);
    }

    private void writeLegalValues(final IArgument argument) {
        writer.startFormalParagraph("Value");
        final String multiplicity;

        if (argument.isMultiple()) {
            if (argument.isRemainderArgument()) {
                if (argument.isListOfLists()) {
                    multiplicity = "A series of lists, each containing";
                } else {
                    multiplicity = "A series of";
                }
            } else {
                multiplicity
                        = "A list (in []) of";
            }
        } else {
            if (argument.getLegalValues().size() > 1) {
                multiplicity = "One of";
            } else {
                multiplicity = "Only";
            }
        }

        writer.writeParagraph(multiplicity);

        writer.startSimpleList();

        final List<IElement> inOrder = new ArrayList<>(argument.getLegalValues());

        Collections.sort(inOrder,
                new Comparator<IElement>() {
            @Override
            public int compare(final IElement arg0, final IElement arg1) {
                return arg0.getName().compareTo(arg1.getName());
            }
        });

        for (final IElement element : inOrder) {
            if (element.getJavaType().isAnnotationPresent(Obsolete.class)) {
                continue;
            }
            writer.writeSimpleListMember(
                    writer.createXrefToKey(
                            getKey(element)
                    ));
        }
        writer.endSimpleList();

        final Optional<String> defaultValue
                = formatValue(argument.getDefaultValue());

        if (defaultValue.isPresent()) {
            writer.writeFormalParagraph("Optional with default", defaultValue.get());
        } else if (argument.isMandatory()) {
            final NotNull notNull = argument.getReadMethod().getAnnotation(NotNull.class);
            writer.writeFormalParagraph("Required", notNull.message());
        } else {
            writer.writeFormalParagraph("Optional", "");
        }

        writer.endFormalParagraph();
    }

    @SuppressWarnings("unchecked")
    private Optional<String> formatValue(final Optional<Object> object) {
        if (object.isPresent()) {
            final Object val = object.get();
            if (val instanceof List) {
                final List<String> values = new ArrayList<>();
                for (final Object obj : ((List<Object>) val)) {
                    final String string = formatValue(Optional.fromNullable(obj)).orNull();
                    if (string != null) {
                        values.add(string);
                    }
                }
                if (values.size() == 1) {
                    return Optional.of(values.get(0));
                } else if (values.isEmpty()) {
                    return Optional.absent();
                } else {
                    return Optional.of("[" + Joiner.on(' ').join(values) + ']');
                }
            } else {
                final String s = PrettyPrinter.print(atomWriter.write(object.get())).replace("\n", "");
                if (!s.trim().equals("\"\"")) {
                    return Optional.of(s);
                }
            }
        }
        return Optional.absent();
    }

    private boolean hasDoc(final AnnotatedElement e) {
        if (e instanceof Class) {
            final Class<?> eClass = (Class<?>) e;
            if (eClass.isEnum()) {
                return true; // all enums get docs
            }
            if (specialCaseDocs.containsKey(eClass.getCanonicalName())) {
                return true; // some classes have special doc 
            }
        }
        return e.isAnnotationPresent(Doc.class);
    }

    private String doc(final AnnotatedElement e) {
        final StringBuffer out = new StringBuffer();

        if (e.isAnnotationPresent(Doc.class)) {
            final String[] docs = e.getAnnotation(Doc.class).value();

            out.append("<para>\n");
            for (final String s : docs) {
                if (s.isEmpty()) {
                    out.append("</para><para>\n");
                } else {
                    out.append(s);
                    out.append("\n");
                }
            }

            if (out.charAt(out.length() - 2) != '.') {
                out.append(".");
            }

            out.append("</para>");
        }

        if (e instanceof Class) {
            final Class<?> clazz = (Class<?>) e;

            if (specialCaseDocs.containsKey(clazz.getCanonicalName())) {
                out.append(specialCaseDocs.get(clazz.getCanonicalName()));
            }

            if (clazz.isEnum()) {
                out.append("<variablelist>\n");
                out.append("<title>Allowed Values</title>\n");

                for (final Field f : clazz.getFields()) {
                    if (f.isEnumConstant()) {
                        // it is an enum constant?		
                        if (hasDoc(e)) {
                            out.append("<varlistentry>");
                            out.append("<term>");
                            try {
                                out.append(f.get(null).toString());
                            } catch (IllegalArgumentException
                                    | IllegalAccessException e1) {
                                throw new IllegalArgumentException("Could not get enum field " + f, e1);
                            }
                            out.append("</term>");
                            out.append("<listitem>");
                            out.append(doc(f));
                            out.append("</listitem>");
                            out.append("</varlistentry>");
                        }
                    }
                }

                out.append("</variablelist>");
            }
        }
        return out.toString();
    }

    private static String dot(final String s) {
        if (s.endsWith(".")) {
            return s;
        } else {
            return s + ".";
        }
    }

}
