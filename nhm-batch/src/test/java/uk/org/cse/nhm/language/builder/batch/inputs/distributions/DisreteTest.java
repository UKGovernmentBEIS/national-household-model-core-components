package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Assert;

import org.junit.Test;

import uk.org.cse.nhm.language.builder.batch.inputs.distributions.Discrete.WeightedChoice;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;

public class DisreteTest extends AbsDistributionTest {

	@Override
	protected Distribution getDistribution(Random random, String placeholder) {
		return new Discrete(random, placeholder, ImmutableList.of(
				new WeightedChoice("A", 1),
				new WeightedChoice("B", 1),
				new WeightedChoice("C", 2)
				));
	}
	
	@Test
	public void assertDistribution() {
		Iterator<List<Object>> it = distribution.iterator();
		
		Multiset<String> resultCounts = HashMultiset.create();
		for(int i = 0; i < REPETITIONS; i++) {
			resultCounts.add(it.next().get(0).toString());
		}
		
		Assert.assertEquals("Results should contain only the elements from the chocies.", ImmutableSet.of("A", "B", "C"), resultCounts.elementSet());
		
		int aCount = resultCounts.count("A");
		int bCount = resultCounts.count("B");
		int cCount = resultCounts.count("C");
		
		int threshold = REPETITIONS / 100;
		int diffAB = aCount - bCount;
		int diffCAB = cCount - (aCount + bCount);

		Assert.assertTrue(String.format("A and B should have been drawn roughly the same number of times. A %d B %d, threshold +-%d.", aCount, bCount, threshold), Math.abs(diffAB) < threshold);
		Assert.assertTrue(String.format("C should have been drawn roughly twice as many times as A or B. A %d B %d C %d, threshold +-%d.", aCount, bCount, cCount, threshold), Math.abs(diffCAB) < threshold);
	}
}
