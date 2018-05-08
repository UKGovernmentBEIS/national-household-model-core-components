package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * A function which computes peak heat load for a house, using the standard
 * demand temperature and coldest day temperature.
 *
 * @author hinton
 *
 */
public class MeanInternalTemperatureFunction extends AbstractNamed implements IComponentsFunction<Number> {

    private final IDimension<IPowerTable> energy;

    @Inject
    public MeanInternalTemperatureFunction(
            @Named("uncalibrated") final IDimension<IPowerTable> energy
    ) {
        this.energy = energy;
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        return (double) scope.get(energy).getMeanInternalTemperature();
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.<IDimension<?>>singleton(energy);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }
}
