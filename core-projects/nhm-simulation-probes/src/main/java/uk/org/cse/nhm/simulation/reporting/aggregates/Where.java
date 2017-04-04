package uk.org.cse.nhm.simulation.reporting.aggregates;

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class Where extends AggregationFunction {
	private final IComponentsFunction<Boolean> test;
	private final IAggregationFunction delegate;

	@AssistedInject
	public Where(
			@Assisted final IComponentsFunction<Boolean> test, 
			@Assisted final IAggregationFunction delegate) {
		super();
		this.test = test;
		this.delegate = delegate;
	}


	@Override
	public double evaluate(final IState state, final ILets lets, final Set<IDwelling> dwellings) {
		final Set<IDwelling> reduced = new LinkedHashSet<>();
		for (final IDwelling d : dwellings) {
			if (test.compute(state.detachedScope(d), lets)) {
				reduced.add(d);
			}
		}
		return delegate.evaluate(state, lets, reduced);
	}

    @Override
	public double evaluate(final IStateScope scope, final ILets lets, final Set<IDwelling> dwellings) {
		final Set<IDwelling> reduced = new LinkedHashSet<>();
        final IState state = scope.getState();
		for (final IDwelling d : dwellings) {
			if (test.compute(scope.getComponentsScope(d).or(state.detachedScope(d)), lets)) {
				reduced.add(d);
			}
		}
		return delegate.evaluate(state, lets, reduced);
	}
}
