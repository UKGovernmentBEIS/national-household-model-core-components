package uk.org.cse.nhm.simulator.state.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.util.TimeUtil;

/**
 * Includes tests for {@link BranchState} and {@link CanonicalState} as they are tightly related.
 * @author hinton
 *
 */
public class StateDwellingCreationDestructionTest {
	private static final int QUANTUM = 400;
	private IStateScope scope;
	
	@Before
	public void setup() {
		scope = mock(IStateScope.class);
		when(scope.getTag()).thenReturn(mock(IStateChangeSource.class));
	}
	
	
	@Test
	public void testCreatingAndDestroyingDwellings() {
		final CanonicalState state = new CanonicalState(new DimensionCounter(5), null, QUANTUM, 0);
		final ITimeDimension t = createTimeDimension();
		state.setTimeDimension(t);
		state.setDimensions(ImmutableSet.<IInternalDimension<?>>of());
		createAndDestroyHouses(state);
	}


	private ITimeDimension createTimeDimension() {
		final ITimeDimension t = mock(ITimeDimension.class);
		when(t.get(any(IDwelling.class))).thenReturn(TimeUtil.mockTime(new DateTime(0)));
		return t;
	}

	private void createAndDestroyHouses(final IState state) {
		final ArrayList<IDwelling> dwells = new ArrayList<IDwelling>();
		final int odc = state.getDwellings().size();
		final IBranch branch = state.branch(1);
		for (int i = 0; i<10; i++) {
			dwells.add(branch.createDwelling(1f));
		}
		
		Assert.assertTrue(Collections.disjoint(state.getDwellings(), dwells));
		
		merge(state, branch);
		
		Assert.assertTrue(state.getDwellings().containsAll(dwells));
		
		final IBranch branch2 = state.branch(1);
		for (int i = 0; i<5; i++) {
			branch2.destroyDwelling(dwells.get(i));
		}
		
		Assert.assertEquals(odc+5, branch2.getDwellings().size());
		
		merge(state, branch2);
		
		Assert.assertEquals(odc+5, state.getDwellings().size());
	}

	private void merge(final IState state, final IBranch branch) {
		if (state instanceof CanonicalState) {
			((CanonicalState) state).merge(branch, scope, Collections.<IStateChangeSource>emptySet());
		} else if (state instanceof BranchState) {
			((BranchState) state).merge(branch);
		} else {
			throw new RuntimeException("Bork! You have made a new kind of state which I do not know how to merge.");
		}
	}
	
	@Test
	public void testCreatingAndDestroyingDwellingsInBranch() {
		final CanonicalState state = new CanonicalState(new DimensionCounter(4),null, QUANTUM, 0);
		state.setDimensions(ImmutableSet.<IInternalDimension<?>>of());
		final ITimeDimension t = createTimeDimension();
		state.setTimeDimension(t);
		final IBranch branch = state.branch(1);
		createAndDestroyHouses(branch);
		Assert.assertTrue("State is not affected", state.getDwellings().isEmpty());
		Assert.assertEquals("Branch has 5 houses", 5, branch.getDwellings().size());
		
		state.merge(branch, scope, Collections.<IStateChangeSource>emptySet());
		Assert.assertEquals("State has 5 houses", 5, state.getDwellings().size());
	}
	
	@Test
	public void testCreatingAndDestroyingDwellingsInBranch2() {
		final CanonicalState state = new CanonicalState(new DimensionCounter(4), null, QUANTUM, 0);
		state.setDimensions(ImmutableSet.<IInternalDimension<?>>of());
		final ITimeDimension t = createTimeDimension();
		state.setTimeDimension(t);
		final IBranch branch = state.branch(1);
		createAndDestroyHouses(branch);
		createAndDestroyHouses(branch);
		Assert.assertTrue("State is not affected", state.getDwellings().isEmpty());
		Assert.assertEquals("Branch has 5 houses", 10, branch.getDwellings().size());
		state.merge(branch, scope, Collections.<IStateChangeSource>emptySet());
		Assert.assertEquals("State has 5 houses", 10, state.getDwellings().size());
	}
}
