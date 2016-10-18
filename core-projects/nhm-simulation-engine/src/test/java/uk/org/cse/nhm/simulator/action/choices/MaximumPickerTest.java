package uk.org.cse.nhm.simulator.action.choices;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.PickOption;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MaximumPickerTest extends LettingPickerTest {

	@Override
	protected void childSetup() {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected LettingPicker buildPicker(final List<ISequenceSpecialAction> ssas) {
		final IComponentsFunction<Number> objective = mock(IComponentsFunction.class);
		when(objective.compute(any(IComponentsScope.class), any(ILets.class))).thenReturn(0.0);
		return new MaximumPicker(ssas, objective);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void picksLargest() {
		final IComponentsFunction<Number> objective = mock(IComponentsFunction.class);
		
		final IComponentsScope a = mock(IComponentsScope.class);
		final IComponentsScope b = mock(IComponentsScope.class);
		
		when(objective.compute(a, ILets.EMPTY)).thenReturn(10.0);
		when(objective.compute(b, ILets.EMPTY)).thenReturn(100.0);
		
		final PickOption picked = new MaximumPicker(NO_VARS, objective)
			.pick(null, ImmutableSet.of(new PickOption(a, ILets.EMPTY), new PickOption(b, ILets.EMPTY)));
		
		Assert.assertEquals("Should pick the larger option.", b, picked.scope);
	}
}
