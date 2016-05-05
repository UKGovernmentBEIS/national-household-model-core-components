package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import org.junit.Assert;

import org.junit.Test;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;

import com.google.common.collect.ImmutableList;

abstract public class WideCombinatorTest extends CombinatorTest {

	@Test
	public void placeholdersComeFromDelegates() {
		IBatchInputs combinator = getCombinator(makeDelegate(1000, "$a"), makeDelegate(500, "$b", "$c"));
		Assert.assertEquals("Combinator placeholders should come from delegate inputs.", ImmutableList.of("$a", "$b", "$c"), combinator.getPlaceholders());
	}
}
