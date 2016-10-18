package uk.org.cse.nhm.simulator.state.functions.impl.num.random;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.util.RandomSource;

public class UniformRandomFunction extends RandomFunction {
	
	private final double start;
	private final double range;

	@AssistedInject
	public UniformRandomFunction(
			@Assisted("start") final double start,
			@Assisted("end") final double end) {
        this.start = start;
        this.range = end - start;
	}

	@Override
	protected double doCompute(final RandomSource random) {
		return (random.nextDouble() * range) + start;
	}
}
