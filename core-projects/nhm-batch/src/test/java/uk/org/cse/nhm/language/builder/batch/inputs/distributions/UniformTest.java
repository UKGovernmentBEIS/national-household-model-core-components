package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.Random;

import org.junit.Test;

public class UniformTest extends AbsDistributionTest {
	@Override
	protected Distribution getDistribution(Random random, String placeholder) {
		return new Uniform(random, placeholder, 0.0, 1.0);
	}
	
	@Test
	public void assertRange() {
		assertRange(0.0, 1.0);
	}
	
	@Test
	public void assertMean() {
		assertMean(0.5);
	}
}
