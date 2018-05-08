package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class UniformIntegerTest extends AbsDistributionTest {

    @Override
    protected Distribution getDistribution(Random random, String placeholder) {
        return new UniformInteger(random, placeholder, -100, 101);
    }

    @Test
    public void assertRange() {
        assertRange(-100.0, 101.0);
    }

    @Test
    public void assertMean() {
        assertMean(0.0);
    }

    @Test
    public void shouldProduceWholeNumbers() {
        Iterator<List<Object>> it = distribution.iterator();
        for (int i = 0; i < REPETITIONS; i++) {
            double current = nextDouble(it);
            Assert.assertEquals("Should produce whole numbers.", current, (double) Math.round(current), 0d);
        }
    }
}
