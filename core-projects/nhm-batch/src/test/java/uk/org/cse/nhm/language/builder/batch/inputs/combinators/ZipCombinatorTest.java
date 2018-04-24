package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;

public class ZipCombinatorTest extends WideCombinatorTest {

	@Override
	protected WideInputCombinator getCombinator(List<IBatchInputs> delegates) {
		return new ZipCombinator(delegates);
	}
	
	@Test
	public void boundIsMinimumOfAvailableBounds() {
		ZipCombinator zip = new ZipCombinator(ImmutableList.of(
				makeDelegate(1, "$a"), 
				makeDelegate(10, "$b"), 
				makeDelegate(null, "$c")));
		Assert.assertEquals("Zip combinator should use the smallest bound used by one of its delegates.", Optional.of(1), zip.getBound());
	}
	
	@Test
	public void combinesRowsFromDelegates() {
		IBatchInputs delegateA = makeDelegate(1, "$a");
		Object a1 = new Object();
		addDataToMock(delegateA, a1);
		
		
		IBatchInputs delegateB = makeDelegate(2, "$b");
		Object b1 = new Object();
		Object b2 = new Object();
		addDataToMock(delegateB, b1, b2);
		
		ZipCombinator zip = new ZipCombinator(ImmutableList.of(delegateA, delegateB));
		
		Assert.assertEquals("Zip combinator should take elements from each of its delegates.", ImmutableList.of(ImmutableList.of(a1, b1)), ImmutableList.copyOf(zip.iterator()));
	}
}
