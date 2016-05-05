package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.Random;

public class UniformInteger extends Distribution {

	private final int start;
	private final int range;

	public UniformInteger(Random random, String placeholder, int start, int end) {
		super(random, placeholder);
		this.start = start;
		this.range = end - start;
	}

	@Override
	protected Long nextRandom(Random random) {
		return (long) random.nextInt(range) + start;
	}
}
