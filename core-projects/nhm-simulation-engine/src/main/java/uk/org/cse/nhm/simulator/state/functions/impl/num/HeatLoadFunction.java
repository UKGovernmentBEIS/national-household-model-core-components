package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class HeatLoadFunction extends AbstractNamed implements IComponentsFunction<Double> {

    private final IDimension<IPowerTable> energy;
    private final double[] weights;
    private final boolean space, water;

    @Inject
    public HeatLoadFunction(@Named("uncalibrated") final IDimension<IPowerTable> energy,
            @Assisted List<Double> weights,
            @Assisted("space") boolean space,
            @Assisted("water") boolean water) {
        this.energy = energy;
        this.weights = new double[12];
        Arrays.fill(this.weights, 1);
        for (int i = 0; i < weights.size(); i++) {
            final Double d = weights.get(i);
            this.weights[i] = Math.min(1, Math.max(0, d == null ? 0 : d.doubleValue()));
        }
        this.space = space;
        this.water = water;
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final IPowerTable calc = scope.get(energy);
        return (double) calc.getWeightedHeatLoad(weights, space, water);
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
