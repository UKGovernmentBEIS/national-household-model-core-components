package uk.org.cse.nhm.commons;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.junit.Assert;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class CollectionsUtilTest {
	
	@Test
	public void testDrawOne() {
		final List<Integer> one = new ArrayList<>();
		CollectionsUtil.draw(ImmutableList.of(1, 2, 3), one, new Random(), 1);
		Assert.assertEquals(1, one.size());
	}
	
    /**
     * Strictly speaking this test is not correct, as it fails when a dramatically unlikely
     * outcome occurs rather than a truly incorrect one.
     */
    @Test
    public void testDraw() {
        final int SOURCE_COUNT = 100;
        final int DRAW_COUNT = 50;
        final int RUNS = 100000;
        final Random random = new Random(System.currentTimeMillis());
        final HashSet<Integer> source = new HashSet<Integer>();
        for (int i = 0; i<SOURCE_COUNT; i++) {
            source.add(i);
        }
        final int[] counters = new int[SOURCE_COUNT];
        for (int i = 0; i<RUNS; i++) {
            final HashSet<Integer> dest = new HashSet<Integer>();
            CollectionsUtil.draw(source, dest, random, DRAW_COUNT);
            Assert.assertEquals(DRAW_COUNT, dest.size());
            for (final int value : dest) {
                counters[value]++;
            }
        }
        
        // check for bias
        double mean = 0;
        double meansq = 0;
        
        for (final int i : counters) {
            mean += i;
            meansq += Math.pow(i, 2);
        }
        
        mean /= counters.length;
        meansq /= counters.length;
        
        final double cov = Math.sqrt(meansq - mean *mean) / mean;

        Assert.assertEquals("Coefficient of variation is small", 0, cov, 0.01);
        Assert.assertEquals("Mean is correct", RUNS * (DRAW_COUNT / (double) SOURCE_COUNT), mean, 0.1);
    }
    
    /**
     * Test the variable draw method with element probabilities
     * 
     * Note that it is not appropriate to check for a bias as in the test above.
     */
    @Test
    public void testDrawWithProbs() {
        final int SOURCE_COUNT = 100;
        final int DRAW_COUNT = 50;
        final int RUNS = 100000;
        final Random random = new Random(System.currentTimeMillis());
        final HashSet<Integer> source = new HashSet<Integer>();
        final double[] probs = new double[SOURCE_COUNT];
        for (int i = 0; i<SOURCE_COUNT; i++) {
            source.add(i);
            probs[i] = random.nextDouble();
        }
        final int[] counters = new int[SOURCE_COUNT];
        for (int i = 0; i<RUNS; i++) {
            final HashSet<Integer> dest = new HashSet<Integer>();
            CollectionsUtil.draw(source, dest, random, probs, DRAW_COUNT);
            Assert.assertTrue(DRAW_COUNT>=dest.size());
            for (final int value : dest) {
                counters[value]++;
            }
        }
   }
}
