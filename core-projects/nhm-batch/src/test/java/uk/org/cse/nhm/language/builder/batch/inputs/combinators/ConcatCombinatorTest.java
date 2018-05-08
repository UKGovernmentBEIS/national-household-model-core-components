package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import static java.util.Collections.singletonList;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;

public class ConcatCombinatorTest extends CombinatorTest {

    @Override
    protected IBatchInputs getCombinator(List<IBatchInputs> delegates) {
        return new ConcatCombinator(delegates);
    }

    @Test
    public void placeholdersShouldComeFromInputs() {
        IBatchInputs d1 = makeDelegate(1, "a", "b");
        IBatchInputs d2 = makeDelegate(1, "a", "b");
        Assert.assertEquals("Placeholders should be the same as delegate placeholders.", ImmutableList.of("a", "b"), getCombinator(d1, d2).getPlaceholders());

    }

    @Test(expected = IllegalArgumentException.class)
    public void placeholdersInInputsMustBeIdentical() {
        IBatchInputs d1 = makeDelegate(1, "a");
        IBatchInputs d2 = makeDelegate(1, "b");
        getCombinator(d1, d2);
    }

    @Test
    public void boundsShouldBeSumOfInputBounds() {
        IBatchInputs d1 = makeDelegate(1, "a");
        IBatchInputs d2 = makeDelegate(2, "a");
        Assert.assertEquals("Bound be the sum of delegate's bounds.", Optional.of(3), getCombinator(d1, d2).getBound());
    }

    @Test
    public void lastInputMayBeUnbounded() {
        IBatchInputs d1 = makeDelegate(1, "a");
        IBatchInputs d2 = makeDelegate(null, "a");
        Assert.assertEquals("Should be unbounded if last delegate is unbounded.", Optional.<Integer>absent(), getCombinator(d1, d2).getBound());
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputsBeforeLastMustBeBounded() {
        IBatchInputs d1 = makeDelegate(null, "a");
        IBatchInputs d2 = makeDelegate(1, "a");
        getCombinator(d1, d2);
    }

    @Test
    public void iteratorReturnsDelegatesInOrder() {

        IBatchInputs d1 = makeDelegate(1, "a");
        Object data1 = new Object();
        addDataToMock(d1, data1);

        IBatchInputs d2 = makeDelegate(1, "a");
        Object data2 = new Object();
        addDataToMock(d2, data2);

        List<List<Object>> concatenated = ImmutableList.copyOf(getCombinator(d1, d2).iterator());

        Assert.assertEquals("Data should have been returned in order delegates were added.",
                ImmutableList.of(
                        singletonList(data1),
                        singletonList(data2)),
                concatenated);
    }

}
