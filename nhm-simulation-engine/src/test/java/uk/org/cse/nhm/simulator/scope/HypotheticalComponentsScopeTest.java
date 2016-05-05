package uk.org.cse.nhm.simulator.scope;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IHypotheticalBranch;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;

public class HypotheticalComponentsScopeTest {
	private ScopeFactory factory;
	private IStateChangeSource tag;
	private IHypotheticalBranch branch;
	private IDwelling dwelling;
	private IDimension<Object> dimension;
	private Object reality;
	private HypotheticalComponentsScope scope;
	private Object figment;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		reality = new Object();
		dimension = mock(IDimension.class);
		tag = mock(IStateChangeSource.class);
		branch = mock(IHypotheticalBranch.class);
		dwelling = mock(IDwelling.class);
		when(branch.get(dimension, dwelling)).thenReturn(reality);
		figment = new Object();
		
		factory = new ScopeFactory(mock(IDimension.class), mock(IDimension.class), mock(ITimeDimension.class));
		
        scope = factory.createHypotheticalScope(null, tag, branch, dwelling, new HashMap<String, Double>());
	}
	
	@Test
	public void imagineIsPut() {
		scope.imagine(dimension, figment);
		Assert.assertSame("Imagine should replace an object for that dimension.",  figment, scope.get(dimension));
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = IllegalArgumentException.class)
	public void imaginationIsPermenant() {
		scope.imagine(dimension, figment);
		scope.modify(dimension, mock(IModifier.class));
	}
	
	@Test
	public void createHypothesisBranches() {
		scope.createHypothesis();
		verify(branch, times(1)).hypotheticalBranch();
	}
	
	@Test
	public void createHypothesisPropagatesImagination() {
		scope.imagine(dimension, figment);
		Assert.assertSame("create hypothesis should propagate any imagined objects.", figment, scope.createHypothesis().get(dimension));
	}
	
	@Test
	public void standardApplyDoesNotBranch() {
		scope.apply(mock(IComponentsAction.class), ILets.EMPTY);
		verify(branch, never()).branch(any(int.class));
	}
	
	@Test
	public void standardApplyPropagatesImagination() {
		scope.imagine(dimension, figment);
		final IComponentsAction action = mock(IComponentsAction.class);
		final ArgumentCaptor<ISettableComponentsScope> scopeArg = ArgumentCaptor.forClass(ISettableComponentsScope.class);
		scope.apply(action, ILets.EMPTY);
		verify(action, times(1)).apply(scopeArg.capture(), eq(ILets.EMPTY));
		Assert.assertSame("apply should propagate any imagined objects.", figment, scopeArg.getValue().get(dimension));
	}
}
