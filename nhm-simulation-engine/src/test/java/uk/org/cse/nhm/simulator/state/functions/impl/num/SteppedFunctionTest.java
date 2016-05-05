package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SteppedFunctionTest {	
	private IComponentsFunction<Number> function;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		function = mock(IComponentsFunction.class);
	}
	
	private void check(final SteppedFunction sf, final double in, final double out) {
		when(function.compute(null, ILets.EMPTY)).thenReturn(in);
		Assert.assertEquals(String.format("%f should round to %f", in, out), out, sf.compute(null, ILets.EMPTY), 0.01);
	}
	
	@Test
	public void steppedFunctionRoundsUp() {
		final SteppedFunction sf = new SteppedFunction(ImmutableList.of(10d, 20d, 30d), function, SteppedFunction.Direction.UP);
		
		check(sf, 5, 10);
		check(sf, 20, 20);
		check(sf, 25, 30);
		check(sf, 30, 30);
		check(sf, 33, 33);
	}
	
	@Test
	public void steppedFunctionRoundsDown() {
		final SteppedFunction sf = new SteppedFunction(ImmutableList.of(10d, 20d, 30d), function, SteppedFunction.Direction.DOWN);
		
		check(sf, 5, 5);
		check(sf, 20, 10);
		check(sf, 25, 20);
		check(sf, 30, 20);
		check(sf, 33, 30);
	}
	
	@Test
	public void steppedFunctionRoundsToClosest() {
		final SteppedFunction sf = new SteppedFunction(ImmutableList.of(10d, 20d, 30d), function, SteppedFunction.Direction.NEAREST);
		
		check(sf, 5, 10);
		check(sf, 9, 10);
		check(sf, 14, 10);
		check(sf, 16, 20);
		check(sf, 20, 20);
		check(sf, 25, 30);
		check(sf, 30, 30);
		check(sf, 33, 30);
	}
}
