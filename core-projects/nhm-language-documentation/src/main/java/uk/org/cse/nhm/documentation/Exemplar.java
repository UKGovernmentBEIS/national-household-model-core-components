package uk.org.cse.nhm.documentation;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.larkery.jasb.io.impl.JASB;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Comment;
import com.larkery.jasb.sexp.INodeVisitor;
import com.larkery.jasb.sexp.ISExpressionSource;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.NodeBuilder;
import com.larkery.jasb.sexp.PrettyPrinter;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.UnfinishedExpressionException;
import com.larkery.jasb.sexp.parse.IDataSource;
import com.larkery.jasb.sexp.parse.StandardSource;

import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.sexp.Defaults;
import uk.org.cse.nhm.language.visit.IVisitor;
import uk.org.cse.nhm.language.visit.SinglyVisitingVisitor;

public class Exemplar {
	public static class Example {
		public final String name;
		public final String description;
		public final String docbookXML;
		public final Class<?> clazz;
		public final String source;
		Example(final String name, final String description, final String docbookXML,
				final Class<?> clazz, final String source) {
			super();
			this.name = name;
			this.description = description;
			this.docbookXML = docbookXML;
			this.clazz = clazz;
			this.source = source;
		}
	}

	public static void main(final String[] args) throws IOException {
		final Exemplar self = new Exemplar(Paths.get(args[0]), Defaults.CONTEXT);
		for (final Example e : self.getExamples(Object.class)) {
			System.out.println(e.name);
			System.out.println(e.source);
			System.out.println();
		}
	}
	
	private ImmutableList<Example> allExamples;
	private final JASB jasb;
	
	public Exemplar(final Path path, final JASB jasb) throws IOException {
		this.jasb = jasb;
		final ImmutableList.Builder<Example> builder = ImmutableList.builder();
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(final Path file,
					final BasicFileAttributes attrs) {
				if (file.getFileName().toString().endsWith(".s")) {
					try {
						try {
							builder.addAll(getExamples(file));
						} catch (final UnfinishedExpressionException e) {
							throw new RuntimeException(e.getMessage(), e);
						}
					} catch (final Exception e) {
						throw new RuntimeException("In " + file + ", " + e.getMessage(), e);
					}
				}
				return FileVisitResult.CONTINUE;
			}

		});
		this.allExamples = builder.build();
	}
	
	private final ISExpressionSource<Path> source = StandardSource.create(
			new IDataSource<Path>() {
				@Override
				public Path root() {
					return null;
				}

				@Override
				public Path resolve(Seq relation, final IErrorHandler eh) {
					throw new UnsupportedOperationException();
				}

				@Override
				public Reader open(Path resolved) {
					try {
						return Files.newBufferedReader(resolved, StandardCharsets.UTF_8);
					} catch (IOException e) {
						throw new RuntimeException("Opening " + resolved + ": " + e.getMessage(), e);
					}
				}
				
			});
	
	private class ExampleScissor extends SinglyVisitingVisitor<XElement> implements INodeVisitor, IVisitor<XElement> {
		public ExampleScissor() {
			super(XElement.class);
		}

		private final NodeBuilder builder = NodeBuilder.create();
		
		private Node rootNode;
		private String rootType;
		
		private final Map<Node, Node> valueToExample = new IdentityHashMap<>();
		private final List<Example> examples = new ArrayList<>();
		
		@Override
		public boolean seq(final Seq seq) {
			if (Invocation.isInvocation(seq)) {
				final Invocation inv = Invocation.of(seq, IErrorHandler.RAISE);
				if (inv.name.equals("example")) {
					final Node argument = inv.remainder.get(0);
					
					argument.accept(this);
					final Node lastNode = builder.getLastNode();
					// that is the Node which will parse out to the given thing
					rootNode = lastNode;
					valueToExample.put(lastNode, seq);
					if (inv.arguments.containsKey("class")) {
						rootType = ((Atom)inv.arguments.get("class")).toString();
					}
					
					return false;
				}
			}
			
			builder.open(seq.getDelimeter());
			for (final Node node : seq) {
				node.accept(this);
			}
			builder.close(seq.getDelimeter());
			return false;
		}

		@Override
		public void atom(final Atom atom) {
			atom.accept(builder);
		}

		@Override
		public void comment(final Comment comment) {
			comment.accept(builder);
		}

		public Node getRootNode() {
			return rootNode;
		}

		@Override
		public void visit(final XElement v) {
			final Node eg = valueToExample.get(v.getSourceNode());
			
			if (eg != null) {
				final Invocation inv = Invocation.of(eg, IErrorHandler.RAISE);
				
				examples.add(new Example(
						((Atom)inv.arguments.get("caption")).getValue(),
						((Atom)inv.arguments.get("description")).getValue(), 
						inv.arguments.containsKey("additional") ? 
								((Atom)inv.arguments.get("description")).getValue() : "",
						v.getClass(), 
						PrettyPrinter.print(v.getSourceNode())));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<Example> getExamples(final Path file) throws UnfinishedExpressionException {
		final Node node = Node.copy(source.get(file, IErrorHandler.RAISE));
		final ExampleScissor scissor = new ExampleScissor();
		final ObsolescenceTest obsTest = new ObsolescenceTest();
		node.accept(scissor);
		
		
		final Set<Class<?>> classes = jasb.getClassesBoundTo(
				Invocation.of(scissor.getRootNode(), IErrorHandler.RAISE).name
				);
		
		final Class<? extends XElement> clazzToRead;
		if (classes.size() == 1) {
			clazzToRead = (Class<? extends XElement>) classes.iterator().next();
		} else if (scissor.rootType != null){
			try {
				clazzToRead = (Class<? extends XElement>) Class.forName(scissor.rootType);
			} catch (final ClassNotFoundException e) {
				throw new IllegalArgumentException("Can't get root type " + scissor.rootType, e);
			}
		} else {
			throw new IllegalArgumentException(file + " contains an example which is insufficiently well-specified to parse unambiguously; you can add a class argument to force a particular class, one of "+ classes);
		}
		
		final Optional<? extends XElement> element = 
				jasb.getReader().readNode(clazzToRead, scissor.getRootNode(), IErrorHandler.RAISE);
		try {
			element.get().accept(obsTest);
		} catch (final Exception e) {
			throw new RuntimeException(file + " " + e.getMessage());
		}
		element.get().accept(scissor);
		
		return scissor.examples;
	}
	
	public List<Example> getExamples(final Class<?> clazz) {
		final ImmutableList.Builder<Example> b = ImmutableList.builder();
		for (final Example e : allExamples) {
			if (clazz.isAssignableFrom(e.clazz)) {
				b.add(e);
			}
		}
		return b.build();
	}
	
    private class ObsolescenceTest extends SinglyVisitingVisitor<XElement> implements IVisitor<XElement> {

		public ObsolescenceTest() {
		    super(XElement.class);
		}
		
		@Override
		public void visit(final XElement v) {
		    if (v.getClass().isAnnotationPresent(Obsolete.class)) {
		            throw new RuntimeException("example contained obsolete element " + v + " with class " + v.getClass());
		    }
		
		    super.visit(v);
		}
	}
}
