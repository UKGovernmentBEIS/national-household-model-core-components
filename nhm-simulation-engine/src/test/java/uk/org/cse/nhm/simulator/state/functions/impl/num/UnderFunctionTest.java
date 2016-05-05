package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class UnderFunctionTest extends DelegatingFunctionTest {

	private IComponentsAction action;
	private final String snapshotName = "snapshot";
	private IComponentsScope scope;
	private IHypotheticalComponentsScope snapshot;
	private IHypotheticalComponentsScope hypothesis;
	private List<IComponentsAction> actionList;
	private ILets lets;
	
	@Override
	@Before
	public void setup() {
		super.setup();
		
		action = mock(IComponentsAction.class);
		actionList = Collections.singletonList(action);
		snapshot = mock(IHypotheticalComponentsScope.class);
		hypothesis = mock(IHypotheticalComponentsScope.class);
		
		scope = mock(IComponentsScope.class);
		lets = mock(ILets.class);
		
		when(lets.get(snapshotName, IHypotheticalComponentsScope.class))
			.thenReturn(Optional.of(snapshot));
		
		when(scope.createHypothesis()).thenReturn(hypothesis);
		
		when(delegate.compute(snapshot, lets)).thenReturn(1.0);
		when(delegate.compute(hypothesis, lets)).thenReturn(2.0);
	}
	
	@Test
	public void computesInHypothesisAndAppliesAction() {
		final UnderFunction under = new UnderFunction(delegate, Optional.<String>absent(), actionList);
		
		Assert.assertEquals("Should take result from hypothetical scope.", 2.0, under.compute(scope, lets).doubleValue(), NO_ERROR);
		
		verify(hypothesis, times(1)).apply(action, lets);
	}
	
	@Test
	public void computesInLookedUpSnapshotAndAppliesAction() {
		final UnderFunction under = new UnderFunction(delegate, Optional.of(snapshotName), actionList);
		
		Assert.assertEquals("Should take result from snapshot scope.",  1.0, under.compute(scope, lets).doubleValue(), NO_ERROR);
		
		verify(snapshot, times(1)).apply(action, lets);
	}

	@Override
	protected IComponentsFunction<? extends Number> buildFun(final IComponentsFunction<? extends Number> delegate) {
		return new UnderFunction(delegate, Optional.of(snapshotName), actionList);
	}

}
