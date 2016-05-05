package uk.org.cse.nhm.language.builder.batch.inputs.combinators;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.language.builder.batch.inputs.IBatchInputs;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class ProductCombinatorTest extends WideCombinatorTest {

	@Override
	protected WideInputCombinator getCombinator(List<IBatchInputs> delegates) {
		return new ProductCombinator(delegates);
	}
	
	@Test
	public void boundIsProductOfInputs() {
		ProductCombinator zip = new ProductCombinator(ImmutableList.of(
				makeDelegate(10, "$a"), 
				makeDelegate(20, "$b")));
		Assert.assertEquals("Product combinator should multiply together the bounds of any bounded delegates in contains.", Optional.of(200), zip.getBound());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void unboundedInputsNotAllowed() {
		new ProductCombinator(ImmutableList.of(makeDelegate(null, "$a")));
	}
	
	@Test
	public void producesAllCombinationsOfItsDelegates() {
		IBatchInputs delegateA = makeDelegate(2, "$a");
		Object a1 = new Object();
		Object a2 = new Object();
		addDataToMock(delegateA, a1, a2);
		
		
		IBatchInputs delegateB = makeDelegate(2, "$b");
		Object b1 = new Object();
		Object b2 = new Object();
		addDataToMock(delegateB, b1, b2);
		
		ProductCombinator product = new ProductCombinator(ImmutableList.of(delegateA, delegateB));
		
		Assert.assertEquals("Product combinator should produce all combinations of values from each of its delegates.", 
				ImmutableList.of(
						ImmutableList.of(a1, b1),
						ImmutableList.of(a2, b1),
						ImmutableList.of(a1, b2),
						ImmutableList.of(a2, b2)), 
				ImmutableList.copyOf(product.iterator()));
	}
}
