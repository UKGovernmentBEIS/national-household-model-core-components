package uk.org.cse.nhm.simulation.reporting.aggregates;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class Sum extends AggregationWithArgument {

    @Inject
    public Sum(@Assisted final IComponentsFunction<Number> functionToSum) {
        super(functionToSum);
    }

    @Override
    protected double evaluate(final double[][] values) {
        double acc = 0;

        for (final double[] p : values) {
            acc += p[0] * p[1];
        }

        return acc;
    }
}
