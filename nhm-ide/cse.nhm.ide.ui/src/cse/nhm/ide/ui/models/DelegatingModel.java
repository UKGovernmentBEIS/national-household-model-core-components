package cse.nhm.ide.ui.models;

import java.util.Set;

import uk.org.cse.nhm.bundle.api.IDefinition;
import uk.org.cse.nhm.bundle.api.IFS;
import uk.org.cse.nhm.bundle.api.IIncludeGraph;
import uk.org.cse.nhm.bundle.api.ILanguage;
import uk.org.cse.nhm.bundle.api.INationalHouseholdModel;
import uk.org.cse.nhm.bundle.api.IRunInformation;
import uk.org.cse.nhm.bundle.api.ISimulationCallback;
import uk.org.cse.nhm.bundle.api.IStockImportCallback;
import uk.org.cse.nhm.bundle.api.IValidationResult;

class DelegatingModel implements INationalHouseholdModel {
	private INationalHouseholdModel MISSING = new MissingModel() {
		protected String errorMessage() {
			return DelegatingModel.this.errorMessage();
		}
	};
	
	private final INationalHouseholdModel _getDelegate() {
		final INationalHouseholdModel result = getDelegate();
		if (result == null) {
			return MISSING;
		} else {
			return result;
		}
	}

	protected String errorMessage() {
		return String.format("Target NHM version is not currently loaded");
	}

	public INationalHouseholdModel getDelegate() {
		return MissingModel.INSTANCE;
	}

	@Override
	public <T> Set<IDefinition<T>> getDefinitions(IFS<T> fs, T arg0) {
		return _getDelegate().getDefinitions(fs, arg0);
	}

	@Override
	public <T> IIncludeGraph<T> getIncludeGraph(IFS<T> fs, T arg0) {
		return _getDelegate().getIncludeGraph(fs, arg0);
	}

	@Override
	public ILanguage language() {
		return _getDelegate().language();
	}

	@Override
	public <T> void simulate(IFS<T> fs, T arg0, ISimulationCallback<T> arg1) {
		_getDelegate().simulate(fs, arg0, arg1);
	}

	@Override
	public <T> IValidationResult<T> validate(IFS<T> fs, T arg0) {
		return _getDelegate().validate(fs, arg0);
	}

	@Override
	public String version() {
		return _getDelegate().version();
	}

    @Override
    public <T> IRunInformation<T> getRunInformation(IFS<T> fs, final T p) {
        return _getDelegate().getRunInformation(fs, p);
    }

    @Override
    public <T> void simulate(IFS<T> fs, IRunInformation<T> run, ISimulationCallback<String> cb) {
    	_getDelegate().simulate(fs, run, cb);
    }
    
    @Override
    public <T> void importStock(final IFS<T> fs, final T path, final IStockImportCallback cb) {
        _getDelegate().importStock(fs, path, cb);
    }
}
