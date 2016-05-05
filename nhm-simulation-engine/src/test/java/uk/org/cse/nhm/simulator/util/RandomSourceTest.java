package uk.org.cse.nhm.simulator.util;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class RandomSourceTest {
	final RandomSource toTest = new RandomSource(3);
	
	@Test
	public void chooseNothingIsNull() {
		Assert.assertNull(toTest.chooseOne(ImmutableList.<Object>of(), ImmutableList.<Double>of()));
	}
	
	@Test
	public void chooseCertainIsRight() {
		Object o1 = new Object();
		Object o2 = new Object();
		Assert.assertSame(o1, toTest.chooseOne(ImmutableList.of(o1), ImmutableList.of(1d)));
		Assert.assertSame(o1, toTest.chooseOne(ImmutableList.of(o1, o2), ImmutableList.of(1d, 0d)));
		Assert.assertSame(o1, toTest.chooseOne(ImmutableList.of(o1, o2), ImmutableList.of(1d, 0d)));
		Assert.assertSame(o1, toTest.chooseOne(ImmutableList.of(o1, o2), ImmutableList.of(1d, 0d)));
		Assert.assertSame(o1, toTest.chooseOne(ImmutableList.of(o1, o2), ImmutableList.of(1d, 0d)));
		Assert.assertSame(o1, toTest.chooseOne(ImmutableList.of(o1, o2), ImmutableList.of(1d, 0d)));
	}
	
	@Test
	public void syncWorks() {
		final RandomSource a = new RandomSource(10);
		a.sync(toTest);
		final double[] ds = new double[11];
		for (int i = 0; i<ds.length; i++) {
			ds[i] = a.nextDouble();
		}
		a.sync(toTest);
		for (int i = 0; i<ds.length; i++) {
			Assert.assertEquals(ds[i], a.nextDouble(), 0.01);
		}
	}

    @Test
    public void consumingRandomnessPreservesDistribution() {
        System.out.println("DUP");
        final RandomSource a = new RandomSource(33);
        int BINS = 20;
        int[] histogram = new int[BINS];
        int maxrow = 0;
        for (int i = 0; i<50000; i++) {
            final RandomSource b = a.dup();
            b.consumeRandomness(i);
            double d = b.nextDouble() * BINS;
            maxrow = Math.max(++histogram[(int) d], maxrow);
        }
        for (int i = 0; i<BINS; i++) {
            for (int j = 0; j<20 * histogram[i] / maxrow; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println("SEQ");
        java.util.Arrays.fill(histogram, 0);
        for (int i = 0; i<50000; i++) {
            final RandomSource b = a;
            //b.consumeRandomness(i);
            double d = b.nextDouble() * BINS;
            maxrow = Math.max(++histogram[(int) d], maxrow);
        }
        for (int i = 0; i<BINS; i++) {
            for (int j = 0; j<20 * histogram[i] / maxrow; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
