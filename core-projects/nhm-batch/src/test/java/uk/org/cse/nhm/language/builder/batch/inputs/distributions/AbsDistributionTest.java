package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.language.builder.batch.inputs.SingleInput;
import uk.org.cse.nhm.language.builder.batch.inputs.SingleInputTest;

abstract public class AbsDistributionTest extends SingleInputTest {

	protected Distribution distribution;
	protected static final int REPETITIONS = 1000000;

	@Before
	public void setup() {
		super.setup();
		this.distribution = getDistribution(new Random(), placeholder);
	}
	
	@Override
	protected final SingleInput getInput(String placeholder) {
		return getDistribution(new Random(), placeholder);
	}
	
	@Test
	public void assertUnbounded() {
		Assert.assertFalse("Distributions are always unbounded, in the sense that they may yield an infinite number of results.", distribution.getBound().isPresent());
	}
	
	abstract protected Distribution getDistribution(Random random, String placeholder);
	
	protected void assertRange(double min, double max) {
		Iterator<List<Object>> it = distribution.iterator();
		for(int i = 0; i < REPETITIONS; i++) {
			double currentValue = nextDouble(it);
			Assert.assertTrue("Distribution should not produce values below its minimum.", currentValue >= min);
			Assert.assertTrue("Distribution should not produce values above or equal to its maximum.", currentValue < max);
		}
	}

	protected double nextDouble(Iterator<List<Object>> it) throws NumberFormatException {
		List<Object> current = it.next();
		double currentValue = Double.parseDouble(current.get(0).toString());
		return currentValue;
	}
	
	protected void assertMean(double mean) {
		Iterator<List<Object>> it = distribution.iterator();
		double accum = 0.0;
		for(int i = 0; i < REPETITIONS; i++) {
			double currentValue = nextDouble(it);
			accum += currentValue;
		}
		
		Assert.assertEquals("Mean should be close to the specified mean.", mean, accum / REPETITIONS, 0.5);
	}
}
