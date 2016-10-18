package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class AirChangeRateFunction extends AbstractNamed implements IComponentsFunction<Number> {
    private final IDimension<IPowerTable> energy;

    @Inject
	public AirChangeRateFunction(final IDimension<IPowerTable> energy) {
        this.energy = energy;
    }

    @Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
        return (double) scope.get(energy).getAirChangeRate();
    }

    @Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(energy);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}

