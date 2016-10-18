package uk.org.cse.nhm.simulation.reporting.aggregates;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class Mean extends AggregationWithArgument {
	@Inject
	public Mean(
			@Assisted final IComponentsFunction<Number> functionToMean) {
		super(functionToMean);
	}

    @Override
    protected double evaluate(final double[][] values) {
		double acc = 0;
		double wacc = 0;

        for (final double[] p : values) {
            acc += p[0] * p[1];
            wacc += p[1];
        }

        return acc / wacc;
    }
}
