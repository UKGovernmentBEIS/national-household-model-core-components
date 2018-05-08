package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.Random;

import org.junit.Test;

public class TriangularTest extends AbsDistributionTest {

    @Override
    protected Distribution getDistribution(final Random random, final String placeholder) {
        return new Triangular(random, placeholder, 0.0, 1.0, 0.5);
    }

    @Test
    public void assertMean() {
        assertMean(0.5);
    }

    @Test
    public void assertRange() {
        assertRange(0.0, 1.0);
    }
}
