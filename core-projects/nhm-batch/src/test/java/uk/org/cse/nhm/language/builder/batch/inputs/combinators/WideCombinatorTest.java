package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;

abstract public class WideCombinatorTest extends CombinatorTest {

    @Test
    public void placeholdersComeFromDelegates() {
        IBatchInputs combinator = getCombinator(makeDelegate(1000, "$a"), makeDelegate(500, "$b", "$c"));
        Assert.assertEquals("Combinator placeholders should come from delegate inputs.", ImmutableList.of("$a", "$b", "$c"), combinator.getPlaceholders());
    }
}
