package uk.org.cse.nhm.simulation.cli;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.larkery.jasb.io.IModel.IInvocationModel;
import com.larkery.jasb.io.impl.JASB;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Comment;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.INodeVisitor;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;

import uk.org.cse.nhm.language.sexp.Defaults;

public class FindUsedElements {
	static class CollectElementNames implements INodeVisitor {
		private final Multiset<String> names = HashMultiset.create();
		@Override
		public boolean seq(final Seq seq) {
			if (seq.getDelimeter() == Delim.Paren) {
				final Optional<Node> head = seq.exceptComments(0);
				if (head.isPresent()) {
					final Node n = head.get();
					if (n instanceof Atom) {
						names.add(((Atom) n).getValue());
					}
				}
			}
			
			return true;
		}

		@Override
		public void atom(final Atom atom) {
		}

		@Override
		public void comment(final Comment comment) {
		}
		
	}
	
	/**
	 * Usage: 
	 * main(<somewhere with .s files>, [somewhere to resolve includes])
	 * 
	 * Behaviour:
	 * recursively traverses the directory, loads each .s file, expands any templates,
	 * collects up what elements are used in the scenario.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		final CollectElementNames names = new CollectElementNames();
		
		final Map<Path, Multiset<String>> byFile = new HashMap<>();
		
		final JASB jasb = Defaults.CONTEXT;
		
		for (final IInvocationModel m : jasb.getModel().getInvocations()) {
			names.names.add(m.getName());
		}
		
		final Path scenarioRootDirectory = FileSystems.getDefault().getPath(args[0]);
		final Path includeDirectory = args.length > 1 ? FileSystems.getDefault().getPath(args[1]) : null;
		
		Files.walkFileTree(scenarioRootDirectory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
				if (file.getFileName().toString().endsWith(".s")) {
					try {
						final List<Node> nodes = Node.copyAll(SimulationCommandLineInterface.loadScenario(
								file.getParent(), //hax 
								includeDirectory, 
								file.getFileName().toString()));
						final CollectElementNames inFile = new CollectElementNames();
						for (final Node n : nodes) {
							n.accept(names);
							n.accept(inFile);
						}
						
						byFile.put(file, inFile.names);
					} catch (final Exception e) {
					}
				}
				return FileVisitResult.CONTINUE;
			}
		});
		
		final List<Path> files = new ArrayList<>(byFile.keySet());
		
		Collections.sort(files);
		
		final List<String> parts = new ArrayList<>();
		
		parts.add("element");
		
		for (final Path p : files) {
			parts.add(scenarioRootDirectory.relativize(p).toString());	
		}
		
		parts.add("total");

		System.out.println(Joiner.on("\t").join(parts));
		
		for (final Entry<String> n : names.names.entrySet()) {
			parts.clear();
			parts.add(n.getElement());
			
			for (final Path p : files) {
				parts.add(Integer.toString(byFile.get(p).count(n.getElement())));
			}
			
			parts.add(Integer.toString(n.getCount() - 1));
			
			System.out.println(Joiner.on("\t").join(parts));
		}
	}
}
