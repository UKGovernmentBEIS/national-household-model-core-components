package uk.org.cse.nhm.simulator.measure.sizing.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ISizingResult;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SizingFunctionTest {

	private static final double ERROR_DELTA = 0.00001;
	private IComponentsScope scope;
	private IComponentsFunction<Number> function;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		scope = mock(IComponentsScope.class);
		function = mock(IComponentsFunction.class);
		when(function.compute(any(IComponentsScope.class), any(ILets.class))).thenReturn(0.0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void maxShouldBeGreaterThanMin() {
		new SizingFunction(function, 0, 0);
		new SizingFunction(function, 1, -1);
	}
	
	@Test
	public void returnsSuitableSizingResult() {
		final ISizingResult result = new SizingFunction(function, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY).computeSize(scope, ILets.EMPTY, Units.KILOWATTS);
		Assert.assertTrue("Result should be suitable.", result.isSuitable());
		Assert.assertEquals("Result size should be taken from the function.", 0.0, result.getSize(), ERROR_DELTA);
		Assert.assertEquals("Result units should be correct.", Units.KILOWATTS, result.getUnits());
	}
	
	@Test
	public void returnsUnsuitableSizingResult() {
		final ISizingResult result = new SizingFunction(function, Double.MAX_VALUE, Double.POSITIVE_INFINITY).computeSize(scope, ILets.EMPTY, Units.SQUARE_METRES);
		Assert.assertFalse("Result should be unsuitable.", result.isSuitable());
		Assert.assertEquals("Result size should be taken from the function.", 0.0, result.getSize(), ERROR_DELTA);
		Assert.assertEquals("Result units should be correct.", Units.SQUARE_METRES, result.getUnits());
	}
}
