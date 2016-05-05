package uk.org.cse.nhm.simulator.state.functions.impl.num.random;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.util.RandomSource;

abstract class RandomFunction extends AbstractNamed implements IComponentsFunction<Double> {
	@Override
	public final Set<IDimension<?>> getDependencies() {
		return ImmutableSet.of();
	}

	@Override
	public final Set<DateTime> getChangeDates() {
		return ImmutableSet.of();
	}
	
	@Override
	public final Double compute(final IComponentsScope scope, final ILets lets) {
		scope.getState().getRandom().consumeRandomness(scope.getDwellingID());
		return doCompute(scope.getState().getRandom());
	}

	protected abstract double doCompute(RandomSource random);
}
