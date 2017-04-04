package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class InterpolateFunctionTest {
	
	@Mock
	IComponentsFunction<Number> function;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void interpolatePositivelyWorks() {
		final InterpolateFunction f = 
				new InterpolateFunction(
						function,
                        false,
						ImmutableList.of(1d, 10d), 
						ImmutableList.of(0d, 1d));
		
		verify(f, 0, 0);
		verify(f, 10000, 1);
		verify(f, 1, 0);
		verify(f, 10, 1);
		
		verify(f, 5.5, 0.5);
	}

	@Test
	public void interpolatePiecewiseWorks() {
		final InterpolateFunction f = 
				new InterpolateFunction(
						function,
                        false,
						ImmutableList.of(1d, 10d, 20d), 
						ImmutableList.of(0d, 1d, 10d));
        verify(f, 15, 5.5d);
	}

    
	private void verify(final InterpolateFunction f, final double in, final double out) {
		when(function.compute(null, null)).thenReturn(in);
		final double result = f.compute(null, null);
		
		Assert.assertEquals(out, result, 0.001d);
	}

	@Test
	public void interpolateToPointWorks() {
		final InterpolateFunction f = 
				new InterpolateFunction(
						function,
                        false,
						ImmutableList.of(8d, 8d),
						ImmutableList.of(1d, 1d));
		
		verify(f, -10, 1);
		verify(f, 80, 1);
		verify(f, 0, 1);
	}
	
	@Test
	public void interpolateDownhillWorks() {
		final InterpolateFunction f = 
				new InterpolateFunction(
						function,
                        false,
						ImmutableList.of(0d, 1d), 
						ImmutableList.of(1d, 0d));
		
		verify(f, 0, 1);
		verify(f, 1, 0);
		verify(f, 0.5, 0.5);
		verify(f, 0.75, 0.25);
	}
}
