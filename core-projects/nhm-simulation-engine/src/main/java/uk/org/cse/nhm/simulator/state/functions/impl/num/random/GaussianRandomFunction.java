package uk.org.cse.nhm.simulator.state.functions.impl.num.random;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.util.RandomSource;

public class GaussianRandomFunction extends RandomFunction {
	private final double mean;
	private final double standardDeviation;

	@AssistedInject
	public GaussianRandomFunction(
			@Assisted("mean") final double mean,
			@Assisted("standardDeviation") final double standardDeviation) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
	}

	@Override
	protected double doCompute(final RandomSource random) {
		return random.nextGaussian(mean, standardDeviation);
	}
}
