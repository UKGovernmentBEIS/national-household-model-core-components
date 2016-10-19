package uk.org.cse.nhm.simulator.scope;

import java.util.Map;

import com.google.common.collect.Maps;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IHypotheticalBranch;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;

public class HypotheticalComponentsScope extends ComponentsScope implements IHypotheticalComponentsScope {
	private final IHypotheticalBranch hypotheticalBranch;

    HypotheticalComponentsScope(
        final IScope<?> parentScope,
			final IDimension<DwellingTransactionHistory> transactionDimension,
			final IDimension<IObligationHistory> obligationDimension,
			final ITimeDimension time, final ScopeFactory scopeFactory,
			final IStateChangeSource action,
			final IHypotheticalBranch branch, 
			final IDwelling dwelling,
			final Map<String, Double> yields) {
        super(parentScope, transactionDimension, obligationDimension, time, scopeFactory,
				action, branch, dwelling, yields);
		hypotheticalBranch = branch;
	}

	private final Map<IDimension<? extends Object>, Object> imagination = Maps.newHashMap();
	
	@Override
	public IHypotheticalBranch getHypotheticalBranch() {
		return hypotheticalBranch;
	}
	
	@Override
	public <T> void imagine(final IDimension<T> dimension, final T value) {
		imagination.put(dimension, value);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(final IDimension<T> dimension) {
		if (imagination.containsKey(dimension)) {
			return (T) imagination.get(dimension);
		} else {
			return super.get(dimension);
		}
	}
	
	@Override
	public <T> void modify(final IDimension<T> dimension, final IModifier<T> mod) {
		if (imagination.containsKey(dimension)) {
			throw new IllegalArgumentException("Have already imagined " + dimension);
		}
		super.modify(dimension, mod);
	}
	
	@Override
    protected ComponentsScope doCreateChild(final IScope<?> parentScope,
                                            final IStateChangeSource action,
                                            final IBranch branch,
                                            final Map<String, Double> yieldedValues) {
		final HypotheticalComponentsScope result = scopeFactory.
            createHypotheticalScope(parentScope,
                                    action,
                                    (IHypotheticalBranch) branch,
                                    dwelling,
                                    yieldedValues);
		for (final IDimension<?> d : imagination.keySet()) {
			put(d, result);
		}
		return result;
	}
	
	@Override
	public IHypotheticalComponentsScope createHypothesis() {
		final IHypotheticalComponentsScope scope = super.createHypothesis();
		
		for (final IDimension<?> d : imagination.keySet()) {
			put(d, scope);
		}
		
		return scope;
	}
	
	@SuppressWarnings("unchecked")
	private final <T> void put(final IDimension<T> dimension, final IHypotheticalComponentsScope result) {
		final T object = (T) imagination.get(dimension);
		result.imagine(dimension, object);
	}
	
	@Override
	public <T> void replace(
			final IDimension<T> dimension,
			final IInternalDimension<T> internal) {
		//TODO support checking for whether this has already happened
		hypotheticalBranch.replaceDimension(
				dimension, 
				ShimDimension.of(
						branch.getGeneration(dimension, dwelling)+1, 
						dwelling, 
						internal));
	}
}
