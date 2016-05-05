package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.Random;

import org.junit.Test;

public class GaussianTest extends AbsDistributionTest {

	@Override
	protected Distribution getDistribution(Random random, String placeholder) {
		return new Gaussian(random, placeholder, 0.0, 1.0);
	}
	
	@Test
	public void assertMean() {
		assertMean(0.0);
	}
}
