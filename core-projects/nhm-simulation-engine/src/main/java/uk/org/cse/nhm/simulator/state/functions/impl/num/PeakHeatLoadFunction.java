package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;

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
public class PeakHeatLoadFunction extends AbstractNamed implements IComponentsFunction<Double> {

    private final double demandTemperature;
    private final double coldestDay;
    private final IDimension<IPowerTable> energy;
    private final double scale;

    @Inject
    public PeakHeatLoadFunction(
            @Named("uncalibrated") final IDimension<IPowerTable> energy,
            @Assisted("internal") final double internalTemperature,
            @Assisted("external") final double externalTemperature,
            @Assisted("scale") final double scale
    ) {
        this.demandTemperature = internalTemperature;
        this.coldestDay = externalTemperature;
        this.energy = energy;
        this.scale = scale;
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        return compute(scope.get(energy).getSpecificHeatLoss());
    }

    private double compute(final double heatLoss) {
        final double largestHeatDifference = demandTemperature - coldestDay;
        return (largestHeatDifference * heatLoss) / scale;
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
