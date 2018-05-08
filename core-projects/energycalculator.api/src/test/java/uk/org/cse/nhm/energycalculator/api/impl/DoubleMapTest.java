package uk.org.cse.nhm.energycalculator.api.impl;

import org.junit.Assert;
import org.junit.Test;

public class DoubleMapTest {

    enum TestE {
        One,
        Two,
        Three
    }

    @Test
    public void testInitialisedToZero() {
        final DoubleMap<TestE> dm = new DoubleMap<TestE>(TestE.class, 3);

        for (int i = 0; i < 3; i++) {
            for (final TestE o : TestE.values()) {
                Assert.assertEquals(0, dm.get(o, i), 0);
            }
        }
    }

    @Test
    public void testIncrementAndDelta() {
        final DoubleMap<TestE> dm = new DoubleMap<TestE>(TestE.class, 2);
        final TestE o1 = TestE.One;
        Assert.assertEquals(0d, dm.getDifference(o1, 0, 1), 0);
        dm.increment(o1, 0, 10);
        Assert.assertEquals(10d, dm.getDifference(o1, 0, 1), 0);
        Assert.assertEquals(10d, dm.get(o1, 0), 0);
        dm.increment(o1, 1, 20);
        Assert.assertEquals(-10d, dm.getDifference(o1, 0, 1), 0);
        dm.increment(o1, 0, 3);
        Assert.assertEquals(13d, dm.get(o1, 0), 0);
        Assert.assertEquals(20d, dm.get(o1, 1), 0);
        Assert.assertEquals(-7d, dm.getDifference(o1, 0, 1), 0);
    }
}
