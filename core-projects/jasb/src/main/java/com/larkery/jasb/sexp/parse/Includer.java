package com.larkery.jasb.sexp.parse;

import java.io.IOException;
import java.util.Stack;

import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.SExpressions;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.BasicError;
import com.larkery.jasb.sexp.errors.IErrorHandler;

public class Includer {
//	/**
//	 * Given a resolver and a root address, construct a map which contains all of the included
//	 * things by URI that were found in the root document or any of its includes, and so on.
//	 * 
//	 * @param resolver
//	 * @param root
//	 * @param errors
//	 * @param dependencyGraph if not null, build a dependency graph in here
//	 * @return
//	 */
//	public static <T> Map<String, String> collect(final IDataSource<T> root, final IErrorHandler errors) {
//		return collect(root, errors, null);
//	}
//	
//	/**
//	 * Given a resolver and a root address, construct a map which contains all of the included
//	 * things by URI that were found in the root document or any of its includes, and so on.
//	 * 
//	 * @param resolver
//	 * @param data
//	 * @param errors
//	 * @param dependencyGraph if not null, build a dependency graph in here
//	 * @return
//	 */
//	public static <T> Map<String, String> collect(final IDataSource<T> data, final IErrorHandler errors, final Multimap<String, String> dependencyGraph) {
//		final HashMap<T, String> builder = new HashMap<>();
//		
//		final Deque<T> addrs = new LinkedList<>();
//		final Deque<Location> includeLocation = new LinkedList<>();
//
//		addrs.add(data.root());
//		includeLocation.add(null);
//		T addr;
//		
//		/**
//		 * Holds a single boolean, which indicates whether we want to recurse into no-includes when
//		 * looking for includes to stick in the output.
//		 */
//		final boolean[] shouldLookWithinNoInclude = new boolean[] {true};
//		
//		/**
//		 * A visitor which pulls out includes and sticks them on the queue to process
//		 */
//		final INodeVisitor addressCollector = new INodeVisitor(){
//				@Override
//				public boolean seq(final Seq seq) {
//					if (seq.size() >= 1) {
//						if (seq.getHead() instanceof Atom) {
//							final String nameOfHead = ((Atom)seq.getHead()).getValue(); 
//							if (nameOfHead.equals("include") || nameOfHead.equals("include-modules")) {
//								T addr = data.resolve(seq);
//							
//								if (dependencyGraph != null) {
//									dependencyGraph.put(seq.getLocation().name, String.valueOf(addr));
//								}
//								
//								if (builder.containsKey(addr)) {
//								} else {
//									addrs.push(addr);
//									includeLocation.push(seq.getLocation());
//								}
//								return false;
//							} else if (nameOfHead.equals("no-include")) {
//								return shouldLookWithinNoInclude[0];
//							}
//						}
//					}
//				
//					return true;
//				}
//		
//				@Override
//				public void comment(final Comment comment) {}
//		
//				@Override
//				public void atom(final Atom atom) {}
//			};
//		
//		while ((addr = addrs.poll()) != null) {
//			try {
//				final Location iloc = includeLocation.poll();
//				String stringValue;
//				stringValue = IOUtils.toString(data.open(addr));
//				builder.put(addr, stringValue);
//		
//				final List<Node> nodes = 
//						Node.copyAll(Parser.source(
//								iloc, String.valueOf(addr), new StringReader(stringValue), errors));
//			
//				for (final Node n : nodes) {
//					n.accept(addressCollector);
//				}
//				
//				shouldLookWithinNoInclude[0] = false;
//			} catch (final IOException|UnsupportedOperationException|UnfinishedExpressionException e) {
//				errors.handle(BasicError.nowhere(e.getMessage()));
//			}
//		}
//		
//		return ImmutableMap.copyOf(builder);
//		// this function is useless.
//	}
//    
	/**
	 * Given a resolver and a root address, make an S-Expression by following all the includes.
	 * @param resolver
	 * @param root
	 * @param errors
	 * @return
	 */
	public static <T> ISExpression source(final IDataSource<T> resolver, final T root, final IErrorHandler errors) {
		return new ISExpression() {
			@Override
			public void accept(final ISExpressionVisitor visitor) {
				try {
					final ISExpression real = Parser.source(String.valueOf(root), resolver.open(root), errors);
					real.accept(new IncludingVisitor<T>(resolver, new ModuleFilteringVisitor(visitor), errors));
				} catch (final IOException nse) {
					errors.handle(BasicError.nowhere("Unable to resolve " + root + " (" + nse.getMessage() + ")"));
				}
			}
			
			@Override
			public String toString() {
				return root.toString();
			}
		};
	}
	
	public static <T> ISExpression source(final IDataSource<T> resolver, final IErrorHandler errors) {
		return source(resolver, resolver.root(), errors);
	}

	static class ModuleFilteringVisitor extends Editor {
		private boolean enableFilter = false;
		public ModuleFilteringVisitor(final ISExpressionVisitor visitor) {
			super(visitor);
		}

		public void setEnableFilter(final boolean enableFilter) {
			this.enableFilter = enableFilter;
		}

		@Override
		public void atom(final String name) {
			if (editing() ||
				afterOpen() ||
				!enableFilter) {
				super.atom(name);
			}
		}
		
		@Override
		protected Action act(final String name) {
			if (enableFilter) {
				switch (name) {
				case "~module":
					return Action.Ignore; // ignore it but pass it through
				default:
					return Action.Remove; // throw it away entirely, it is bad
				}
			} else {
				return Action.Pass;
			}
		}

		@Override
		protected ISExpression edit(final Seq cut) {
			return cut;
		}
	}
	
	static class IncludingVisitor<T> extends Editor {
		private final IDataSource<T> resolver;
		private final IErrorHandler errors;
		private final Stack<T> stack = new Stack<>();
		private final ModuleFilteringVisitor delegate;
		private int filterModules = 0;

		private IncludingVisitor(final IDataSource<T> resolver, final ModuleFilteringVisitor visitor, final IErrorHandler errors) {
			super(visitor);
			this.delegate = visitor;
			this.resolver = resolver;
			this.errors = errors;
		}

		protected boolean isFilteringModules() {
			return filterModules > 0;
		}
		
		@Override
		protected Action act(final String name) {
			switch (name) {
			case "include":
			case "include-modules":
				return Action.RecursiveEdit;
			case "no-include":
				if (stack.isEmpty()) {
					return Action.RecursiveEdit;
				} else {
					return Action.Remove;
				}
			default:
				return Action.Pass;
			}
		}
		
		@Override
		protected ISExpression edit(final Seq cut) {
			boolean filterModules = false;
			final Atom head = (Atom) cut.getHead();
			switch (head.getValue()) {
			case "no-include":
				return new ISExpression() {
					@Override
					public void accept(final ISExpressionVisitor visitor) {
						for (final Node n : cut.getTail()) {
							n.accept(visitor);
						}
					}
				};
			case "include-modules":
				filterModules = true;
			case "include":
			default:
				try {
					final T addr = resolver.resolve(cut, errors);
					
					if (stack.contains(addr)) {
						if (!isFilteringModules()) {
							errors.handle(BasicError.at(cut, addr + " recursively includes itself"));
						}
                    } else {
						final ISExpression real = 
							Parser.source(cut.getLocation(),
										  String.valueOf(addr),
										  resolver.open(addr),
										  errors);
						
						// this is a bit hacky
						stack.push(addr);
						if (filterModules) {
							this.filterModules++;
							delegate.setEnableFilter(isFilteringModules());
						}
						real.accept(this);
						if (filterModules) {
							this.filterModules--;
							delegate.setEnableFilter(isFilteringModules());
						}
						stack.pop();
					}
					locate(cut.getEndLocation());
					
                } catch (final Throwable e) {
                    final String message = e.getMessage();
                    final String errorMessage;
                    if (message != null) {
                        errorMessage = String.format("Unable to resolve include %s: %s", cut, message);
                    } else {
                        errorMessage = String.format("Unable to resolve include %s, because of an %s", cut, e.getClass().getSimpleName());
                    }
					errors.handle(BasicError.at(cut, errorMessage));
				}
				break;
			}
			
			return SExpressions.empty();
		}
	}
	
//	/**
//	 * Recursively collect all the includes starting from the given root provided as a string 
//	 * @param scenarioService
//	 * @param scenarioXML
//	 * @param slf4j
//	 * @return
//	 */
//	public static Map<String, String> collectFromRoot(final IDataSource resolver, 
//												   final String scenarioXML,
//												   final IErrorHandler errors) {
//		final String root = new String();
//		return collect(new IDataSource() {
//
//			@Override
//			public String root() {
//				return root;
//			}
//
//			@Override
//			public String resolve(Seq relation) {
//				return resolver.resolve(relation);
//			}
//
//			@Override
//			public Reader open(String resolved) {
//				if (resolved == root) {
//					return new StringReader(scenarioXML);
//				}
//				return resolver.open(resolved);
//			}
//			
//		}, errors);
//	}
}
