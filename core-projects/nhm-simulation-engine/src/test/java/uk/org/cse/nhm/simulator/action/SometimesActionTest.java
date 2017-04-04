package uk.org.cse.nhm.simulator.action;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.util.RandomSource;

public class SometimesActionTest {

	private IComponentsAction delegate;
	private ISettableComponentsScope scope;
	private IComponentsFunction<Number> chance;
	private SometimesAction action;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		delegate = mock(IComponentsAction.class);
		scope = mock(ISettableComponentsScope.class);
		when(scope.apply(delegate, ILets.EMPTY)).thenReturn(true);
		chance = mock(IComponentsFunction.class);
        final RandomSource rs = new RandomSource(0);
        final IBranch state = mock(IBranch.class);
        when(state.getRandom()).thenReturn(rs);
        when(scope.getState()).thenReturn(state);
        
		action = new SometimesAction(chance, delegate);
	}
	
	@Test
	public void testNone() {
		testSucceedsWithCorrectProbability(0.0);
	}
	
	@Test
	public void testHalf() {
		testSucceedsWithCorrectProbability(0.5);
	}
	
	@Test
	public void testAlways() {
		testSucceedsWithCorrectProbability(1.0);		
	}
	
	private void testSucceedsWithCorrectProbability(final double successProportion) {
		when(chance.compute(scope, ILets.EMPTY)).thenReturn(successProportion);
		
		final int tests = 100000;
		double success = 0;
		double fail = 0;
		
		for (int i = 0; i < tests; i++) {
			if (action.apply(scope, ILets.EMPTY)) {
				success++;
			} else {
				fail++;
			}
		}
		
		verify(scope, times((int) success)).apply(delegate, ILets.EMPTY);
		
		Assert.assertEquals("Expected proportion of successes was not met.", successProportion, success / (success + fail), 0.01);
	}

}
