package uk.org.cse.nhm.simulator.state.functions.impl.num.random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.util.RandomSource;

public class GaussianRandomFunctionTest {

	private static final int REPETITIONS = 100000;
	private static final double ERROR_DELTA = 0.1;
	
	@Test
	public void test() {
		double mean = 0;
		double errorSquared = 0;
		
		final IComponentsScope scope = mock(IComponentsScope.class);
        when(scope.getState()).thenReturn(mock(IState.class));
        when(scope.getState().getRandom()).thenReturn(new RandomSource(0));
		final GaussianRandomFunction fun = new GaussianRandomFunction(0, 1);
		
		for (int i = 0; i < REPETITIONS; i++) {
			final Double rand = fun.compute(scope, ILets.EMPTY);
			mean += rand;
			errorSquared += (rand * rand);
		}
		
		Assert.assertEquals("Should be centred on 0.", 0.0, mean / REPETITIONS, ERROR_DELTA);
		Assert.assertEquals("Standard deviation should be 1.", 1.0, Math.sqrt(errorSquared / REPETITIONS), ERROR_DELTA);
	}

}
