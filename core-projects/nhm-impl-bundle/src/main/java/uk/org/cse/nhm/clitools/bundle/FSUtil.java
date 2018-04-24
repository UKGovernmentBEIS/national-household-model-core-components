package uk.org.cse.nhm.clitools.bundle;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.ErrorCollector;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;
import com.larkery.jasb.sexp.parse.DataSourceSnapshot;
import com.larkery.jasb.sexp.parse.IDataSource;
import com.larkery.jasb.sexp.parse.IMacro;

import uk.org.cse.nhm.bundle.api.IArgument;
import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.IDefinition.DefinitionType;
import uk.org.cse.nhm.bundle.api.IFS;
import uk.org.cse.nhm.bundle.api.ILocation;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem.ProblemLevel;
import uk.org.cse.nhm.ipc.api.tasks.impl.ScenarioSnapshot;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.action.XAction;
import uk.org.cse.nhm.language.definition.function.bool.XBoolean;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.sequence.XNumberDeclaration;

class FSUtil {
    static class FSDataSource<P> implements IDataSource<P> {
        private final IFS<P> fs;
        private final P top;
        
        public FSDataSource(IFS<P> fs, P top) {
			super();
			this.fs = fs;
			this.top = top;
		}
        
		@Override
        public P root() {
            return top;
        }
		
        @Override
        public P resolve(final Seq relation, IErrorHandler errors) {
            final StringBuffer pathName = new StringBuffer();
            for (final Node n : relation.getTail()) {
                if (pathName.length() > 0) pathName.append(" ");
                pathName.append(String.valueOf(n));
            }
            return fs.resolve(relation.getLocation().name,
                              pathName.toString());
        }
        
        @Override
        public Reader open(final P resolved) throws IOException {
            return fs.open(resolved);
        }
    }

    public static class SnapshotAndGraph<P> {
        public final ScenarioSnapshot snapshot;
        public final IncludeGraph<P> graph;

        public SnapshotAndGraph(final ScenarioSnapshot snapshot, final IncludeGraph<P> graph) {
            this.snapshot = snapshot;
            this.graph = graph;
        }
    }
    
    public static SnapshotAndGraph<String> load(final DataSourceSnapshot dss) {
    	final ErrorCollector ec = new ErrorCollector();

        final SetMultimap<String, String> deps = HashMultimap.create();
        
        final DataSourceSnapshot.IDependencyCollector dc = new
                DataSourceSnapshot.IDependencyCollector() {
                    @Override
                    public void add(final String from, final String to) {
                        deps.put(from, to);
                    }
                };
            
        DataSourceSnapshot.copy(dss,ec, dc);
        return new SnapshotAndGraph<String>(new ScenarioSnapshot(dss, ec.getErrors()), new IncludeGraph<String>(deps));
    }
    
    public static <P> SnapshotAndGraph<P> load(final IFS<P> fs, final P top) {
        final ErrorCollector ec = new ErrorCollector();

        final SetMultimap<P, P> deps = HashMultimap.create();
        
        final DataSourceSnapshot.IDependencyCollector dc = new
            DataSourceSnapshot.IDependencyCollector() {
                @Override
                public void add(final String from, final String to) {
                    deps.put(fs.deserialize(from), fs.deserialize(to));
                }
            };
        
        final DataSourceSnapshot dss = DataSourceSnapshot.copy(
            new FSDataSource<P>(fs, top),
            ec, dc);
        
        return new SnapshotAndGraph<P>(new ScenarioSnapshot(dss, ec.getErrors()),
                                       new IncludeGraph<P>(deps));
    }

    public static <P> List<IValidationProblem<P>> translateE(final IFS<P> fs, final P top,
                                                            final List<IError> errors,
                                                            final ProblemLevel errorLevel) {
        final ImmutableList.Builder<IValidationProblem<P>> builder = ImmutableList.builder();
        for (final IError e : errors) {
            builder.add(translateE(fs, top, e, errorLevel));
        }
        return builder.build();
    }

    public static <P> List<ILocation<P>> convertLocations(final IFS<P> fs,
                                                          final P top,
                                                          com.larkery.jasb.sexp.Location loc) {
        return convertLocations(fs, top, loc, 0);
    }

    public static <P> List<ILocation<P>> convertLocations(final IFS<P> fs,
                                                          final P top, com.larkery.jasb.sexp.Location loc,
                                                          final int firstLength) {
        boolean first = true;
    	final ImmutableList.Builder<ILocation<P>> b = ImmutableList.builder();
    	while (loc != null) {
    		if (loc == com.larkery.jasb.sexp.Location.NOWHERE) {
    			b.add(new Location<P>(top));
    		} else if (first) {
    			b.add(new Location<P>(fs.deserialize(loc.name), loc, firstLength));
    		} else {
                b.add(new Location<P>(fs.deserialize(loc.name), loc));
            }
    		loc = loc.via.isPresent() ? loc.via.get().location : null;
    	}
    	return b.build();
    }
    
    public static <P> IValidationProblem<P> translateE(final IFS<P> fs, final P top, IError error, final ProblemLevel errorLevel) {
        return new ValidationProblem<P>(convertLocations(fs, top, error.getLocation()), error.getMessage(), 
        		error.getType().isFatal() ? errorLevel : ProblemLevel.SemanticWarning
        		);
    }

	public static <P> List<IDefinition<P>> translateM(final IFS<P> fs, final P top, final List<IMacro> macros) {
        final ImmutableList.Builder<IDefinition<P>> builder = ImmutableList.builder();
        for (final IMacro m : macros) {
        	if (m.getDefiningNode().isPresent()) {
                builder.add(translateM(fs, top, m));
            }
        }
        return builder.build();
    }

    public static <P> IDefinition<P> translateM(final IFS<P> fs, final P top, final IMacro macro) {
        // convert macro
    	final ImmutableSet.Builder<IArgument> arguments = ImmutableSet.builder();

    	for (final String s : macro.getModel().allowedKeys.keySet()) {
			arguments.add(new IArgument() {
				@Override
				public Optional<String> name() {
					return Optional.of(s);
				}
				@Override
				public Optional<Integer> position() {
					return Optional.absent();
				}
			});
		}
    	
    	for (final String s : macro.getModel().requiredKeys.keySet()) {
			arguments.add(new IArgument() {
				@Override
				public Optional<String> name() {
					return Optional.of(s);
				}
				@Override
				public Optional<Integer> position() {
					return Optional.absent();
				}
			});
		}

        // the defining node location may be through some includes I guess
        // so we want its tail location to get the length field out

        final Node n = macro.getDefiningNode().get();
        final int length;
        if (n instanceof Seq) {
            final Seq s = (Seq) n;
            // every Seq has start and end locations
            // both of these first locations will be where it really came from
            // these have offsets which will let us create a length for the first location
            final com.larkery.jasb.sexp.Location loc = s.getLocation();
            final com.larkery.jasb.sexp.Location end = s.getEndLocation();
            
            // these are not source locations so we are OK to use them directly
            length = (int)  (1 + end.offset - loc.offset);
        } else {
            length = 0;
        }
        
    	return new Definition<P>(
            convertLocations(fs, top, macro.getDefiningNode().get().getLocation(), length),
            arguments.build(), 
            macro.getName(), 
            DefinitionType.Template);
    }

    public static <P> List<IDefinition<P>> translateD(final IFS<P> fs, final P top, final Map<String, Object> defs) {
        final ImmutableList.Builder<IDefinition<P>> builder = ImmutableList.builder();
        for (final Map.Entry<String, Object> d : defs.entrySet()) {
        	final Object o = d.getValue();
        	if (o instanceof XElement) {
        		builder.add(translateD(fs, top, d.getKey(), (XElement) o));
        	}
        }
        return builder.build();
    }

    public static <P> IDefinition<P> translateD(final IFS<P> fs, final P top, String name, XElement element) {
    	return new Definition<P>(
    			convertLocations(fs, top, element.getLocation()), 
				Collections.<IArgument>emptySet(), 
				name, 
				typeOf(element));
    }

	private static DefinitionType typeOf(XElement element) {
		if (element instanceof XNumberDeclaration) {
			return DefinitionType.Variable;
		} else if (element instanceof XAction) {
			return DefinitionType.Action;
		} else if (element instanceof XNumber) {
			return DefinitionType.Function;
		} else if (element instanceof XBoolean) {
			return DefinitionType.Test;
		} else {
			return DefinitionType.Entity;
		}
	}

}
