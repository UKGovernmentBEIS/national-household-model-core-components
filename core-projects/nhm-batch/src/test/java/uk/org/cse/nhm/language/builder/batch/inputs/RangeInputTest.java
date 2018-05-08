package uk.org.cse.nhm.language.builder.batch.inputs;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

public class RangeInputTest extends SingleInputTest {

    @Test
    public void testUnbounded() {
        final RangeInput unbounded = new RangeInput(placeholder, 0.0, 1.0);

        Assert.assertEquals("Range without end should not be bounded.", Optional.<Double>absent(), unbounded.getBound());

        final int attempts = 10000;
        Double accum = 0.0;
        final Iterator<List<Object>> iterator = unbounded.iterator();
        for (int i = 0; i < attempts; i++) {
            Assert.assertTrue("Unbounded should never run out.", iterator.hasNext());
            Assert.assertEquals("Range should increment by step every time.", accum, iterator.next().get(0));
            accum += 1.0;
        }
    }

    @Test
    public void testBoundedIncludesBeginningAndStepsButExcludesEnd() {
        final RangeInput bounded = new RangeInput(placeholder, 0.0, 1.0, 2.0);

        Assert.assertEquals("Range should be bounded.", Optional.of(2), bounded.getBound());

        assertValues(bounded, "Range should produce correct values.", 0.0, 1.0);
    }

    @Test
    public void testBoundedWithInconvenientNumbers() {
        testBound(4, 20.0, 20.0, 80.1);
        testBound(30, 0.6, 0.01, 0.9);
    }

    private void testBound(final int expected, final double start, final double step, final double end) {
        final RangeInput range = new RangeInput(placeholder, start, step, end);
        Assert.assertEquals("Bound should be correct.", expected, (long) range.getBound().get());
    }

    @Override
    protected SingleInput getInput(final String placeholder) {
        return new RangeInput("placeholder", 0.0, 1.0, 0.1);
    }
}
