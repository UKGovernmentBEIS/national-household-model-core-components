package uk.org.cse.nhm.simulation.reporting.aggregates;

import java.util.Arrays;
import java.util.Comparator;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class NTile extends AggregationWithArgument {
	private final double p;

	@AssistedInject
	public NTile(@Assisted final IComponentsFunction<Number> valueFunction, @Assisted final double p) {
		super(valueFunction);
		this.p = p;
	}

	public static double evaluate(final double p, final double[][] values) {
		Arrays.sort(values, new Comparator<double[]>() {
			@Override
			public int compare(final double[] fst, final double[] snd) {
				return Double.compare(fst[0], snd[0]);
			}
		});
		
		if (values.length == 0) {
			return Double.NaN;
		} else {
            double totalWeight = 0;
            for (final double[] value : values) {
                totalWeight += value[1];
            }
			final double target = p * totalWeight;
			double acc = 0;
			for (final double[] value : values) {
				if (acc <= target && acc + value[1] >= target) {
					return value[0];
				}
				acc += value[1];
			}
			return values[values.length][0];
		}
	}
	
    @Override
	public double evaluate(final double[][] values) {
        return evaluate(p, values);
	}
}
