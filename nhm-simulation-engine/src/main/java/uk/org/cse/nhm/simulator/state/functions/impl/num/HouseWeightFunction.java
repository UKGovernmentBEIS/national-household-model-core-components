package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class HouseWeightFunction extends AbstractNamed implements IComponentsFunction<Number> {
	@AssistedInject
	public HouseWeightFunction() {
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		return Double.valueOf(scope.getDwelling().getWeight());
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of();
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return ImmutableSet.of();
	}
}
