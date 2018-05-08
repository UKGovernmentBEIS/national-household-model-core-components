package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;
import uk.org.cse.nhm.language.builder.batch.inputs.RangeInput;
import uk.org.cse.nhm.language.builder.batch.inputs.TableInput;

public class RepetitionsInputTest {

    @Test
    public void withNoDelegateJustMakesSeed() {
        final RepetitionsInput i = new RepetitionsInput(new Random(), 10, Optional.<IBatchInputs>absent());
        Assert.assertEquals(10, (int) i.getBound().get());

        int counter = 10;
        for (final List<Object> o : i) {
            counter--;
            Assert.assertEquals(1, o.size());
            Assert.assertTrue(o.get(0) instanceof Long);
        }

        Assert.assertEquals(0, counter);

        Assert.assertEquals(ImmutableList.of("$seed"), i.getPlaceholders());
    }

    @Test
    public void withBoundedDelegateMakesBoundedAmount() {
        final RepetitionsInput i = new RepetitionsInput(new Random(), 10,
                Optional.<IBatchInputs>of(
                        new TableInput(ImmutableList.of("$thing"),
                                ImmutableList.<List<Object>>of(
                                        ImmutableList.<Object>of(0),
                                        ImmutableList.<Object>of(1),
                                        ImmutableList.<Object>of(2)
                                )
                        )));
        Assert.assertEquals(30, (int) i.getBound().get());

        int counter = 30;
        int nextThing = -1;
        for (final List<Object> o : i) {
            if (counter % 10 == 0) {
                nextThing++;
            }

            counter--;

            Assert.assertEquals(2, o.size());
            Assert.assertEquals(nextThing, o.get(0));

            Assert.assertTrue(o.get(1) instanceof Long);
        }

        Assert.assertEquals(0, counter);

        Assert.assertEquals(ImmutableList.of("$thing", "$seed"), i.getPlaceholders());
    }

    @Test
    public void withUnboundedDelegateMakesUnboundedAmount() {
        final RepetitionsInput i = new RepetitionsInput(new Random(), 10,
                Optional.<IBatchInputs>of(new RangeInput("$thing", 0, 1)));

        Assert.assertFalse(i.getBound().isPresent());

        int counter = 30;
        int nextThing = -1;
        for (final List<Object> o : i) {
            if (counter % 10 == 0) {
                nextThing++;
            }

            counter--;
            if (counter == 0) {
                break;
            }

            Assert.assertEquals(2, o.size());
            Assert.assertEquals(nextThing, ((Number) o.get(0)).intValue());

            Assert.assertTrue(o.get(1) instanceof Long);
        }

        Assert.assertEquals(0, counter);

        Assert.assertEquals(ImmutableList.of("$thing", "$seed"), i.getPlaceholders());
    }
}
