package cse.nhm.ide.ui.models;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.IFS;
import uk.org.cse.nhm.bundle.api.IIncludeGraph;
import uk.org.cse.nhm.bundle.api.ILanguage;
import uk.org.cse.nhm.bundle.api.ILocation;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;
import uk.org.cse.nhm.bundle.api.IRunInformation;
import uk.org.cse.nhm.bundle.api.ISimulationCallback;
import uk.org.cse.nhm.bundle.api.IStockImportCallback;
import uk.org.cse.nhm.bundle.api.IValidationResult;
import uk.org.cse.nhm.bundle.api.IValidationResult.IValidationProblem;

public class MissingModel implements INationalHouseholdModel {
	public static final INationalHouseholdModel INSTANCE = new MissingModel();

	private static final IIncludeGraph<?> EMPTY_INCLUDE_GRAPH = new IIncludeGraph<Object>() {

		@Override
		public Set<Object> getOutputs(final Object arg0, final boolean arg1) {
			return Collections.emptySet();
		}

		@Override
		public Set<Object> getInputs(final Object arg0, final boolean arg1) {
			return Collections.emptySet();
		}
	};

    private static final IRunInformation<?> EMPTY_RUN_INFORMATION = new IRunInformation<Object>() {
            @Override
            public Map<String, Object> stocks() {
                return Collections.emptyMap();
            }
            @Override
            public Iterable<String> snapshots() {
                return Collections.emptySet();
            }
            @Override
            public boolean isBatch() {
            	return false;
            }
        };
    
	private static final ILanguage EMPTY_LANGUAGE = new ILanguage() {

		@Override
		public List<? extends ISuggestion> suggest(final ICursor arg0, final Set<? extends IDefinition<?>> arg1) {
			return Collections.emptyList();
		}

		@Override
		public Set<String> macros() {
			return Collections.emptySet();
		}

		@Override
		public Set<String> elements() {
			return Collections.emptySet();
		}

		@Override
		public Optional<String> describe(final ICursor arg0) {
			return Optional.absent();
		}
	};

	class Invalid<T> implements IValidationResult<T>, IValidationProblem<T> {
		private final T path;

		public Invalid(final T path) {
			super();
			this.path = path;
		}

		@Override
		public Set<IDefinition<T>> definitions() {
			return Collections.emptySet();
		}

		@SuppressWarnings("unchecked")
		@Override
		public IIncludeGraph<T> includes() {
			return (IIncludeGraph<T>) EMPTY_INCLUDE_GRAPH;
		}

		@Override
		public boolean isScenario() {
			return false;
		}

		@Override
		public List<IValidationProblem<T>> problems() {
			return Collections.<IValidationProblem<T>> singletonList(this);
		}

		@Override
		public List<ILocation<T>> locations() {
			return Collections.singletonList(sourceLocation());
		}

		@Override
		public ILocation<T> sourceLocation() {
			return new ILocation<T>() {

				@Override
				public LocationType type() {
					return LocationType.Source;
				}

				@Override
				public T path() {
					return Invalid.this.path;
				}

				@Override
				public int offset() {
					return 0;
				}

				@Override
				public int line() {
					return 1;
				}

				@Override
				public int length() {
					return 0;
				}

				@Override
				public int column() {
					return 0;
				}
			};
		}

		@Override
		public ProblemLevel level() {
			return ProblemLevel.SyntacticError;
		}

		@Override
		public String message() {
			return errorMessage();
		}
	}
    
	@Override
	public <T> Set<IDefinition<T>> getDefinitions(final IFS<T> fs, final T arg0) {
		return Collections.emptySet();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IIncludeGraph<T> getIncludeGraph(final IFS<T> T, final T arg0) {
		return (IIncludeGraph<T>) EMPTY_INCLUDE_GRAPH;
	}

	@Override
	public ILanguage language() {
		return EMPTY_LANGUAGE;

	}

	protected String errorMessage() {
		return String.format("Target NHM version is not currently loaded");
	}

	@Override
	public <T> void simulate(final IFS<T> fs, final T arg0, final ISimulationCallback<T> arg1) {
		arg1.log("ERROR", errorMessage());
		arg1.failed();
	}

	@Override
	public <T> IValidationResult<T> validate(final IFS<T>fs, final T arg0) {
		return new Invalid<T>(arg0);

	}

	@Override
	public String version() {
		return "no model available";
	}

    @SuppressWarnings("unchecked")
	@Override
    public <T> IRunInformation<T> getRunInformation(final IFS<T>fs, final T p) {
        return (IRunInformation<T>) EMPTY_RUN_INFORMATION;
    }

    @Override
    public <T> void simulate(final IFS<T> fs, final IRunInformation<T> run, final ISimulationCallback<String> cb) {
        cb.log("ERROR", errorMessage());
		cb.failed();
    }

    @Override
    public <T> void importStock(final IFS<T> fs, final T path, final IStockImportCallback cb) {
        cb.log("ERROR", errorMessage());
        cb.failed();
    }
}
