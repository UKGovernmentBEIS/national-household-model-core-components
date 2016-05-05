package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.Random;

public class Gaussian extends Distribution {

	private final double mean;
	private final double standardDeviation;

	public Gaussian(Random random, String placeholder, double mean, double standardDeviation) {
		super(random, placeholder);
		this.mean = mean;
		this.standardDeviation = standardDeviation;
	}

	@Override
	protected Double nextRandom(Random random) {
		return (random.nextGaussian() * standardDeviation) + mean;
	}
}
