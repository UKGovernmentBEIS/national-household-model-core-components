package uk.org.cse.nhm.simulator.scope;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;

public class StateScopeTest {

	private IStateAction rootAction;
	private IBranch rootState;
	private StateScope scope;
	private Set<Integer> dwellingIds;
	private Set<IDwelling> dwellings;
	private Set<IDwelling> oneDwelling;
	
	private ArgumentCaptor<IStateScope> scopeArg;
	private ArgumentCaptor<ISettableComponentsScope> componentsScopeArg;
	@SuppressWarnings("rawtypes")
	private ArgumentCaptor<Set> dwellingsArg;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		rootAction = mock(IStateAction.class);
		rootState = mock(IBranch.class);
		
		final ScopeFactory factory = new ScopeFactory(mock(IDimension.class), mock(IDimension.class), mock(ITimeDimension.class));
		
		scope = factory.createStateScope(rootAction, rootState);
		dwellingIds = ImmutableSet.of(1, 2, 3);
		dwellings = makeMockDwellings(dwellingIds);
		oneDwelling = makeMockDwellings(0);
		
		scopeArg = ArgumentCaptor.forClass(IStateScope.class);
		componentsScopeArg = ArgumentCaptor.forClass(ISettableComponentsScope.class);
		dwellingsArg = ArgumentCaptor.forClass(Set.class);
	}
	
	private Set<IDwelling> makeMockDwellings(final Integer...dwellingIds) {
		return makeMockDwellings(ImmutableSet.copyOf(dwellingIds));
	}
	
	private Set<IDwelling> makeMockDwellings(final Set<Integer> dwellingIds) {
		final Set<IDwelling> mockDwellings = new HashSet<IDwelling>();
		for(final Integer id : dwellingIds) {
			final IDwelling d = mock(IDwelling.class);
			when(d.getID()).thenReturn(id);
			mockDwellings.add(d);
		}
		return mockDwellings;
	}
	
	@Test
	public void getComponentsScopeReturnsAbsentIfNoComponentsActionsApplied() {
		Assert.assertEquals("Components scope should be absent", Optional.<IComponentsScope>absent(), scope.getComponentsScope(mock(IDwelling.class)));
	}
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void applyStateActionUsesNewScope() {
		final IStateAction action = mock(IStateAction.class);
		
		scope.apply(action, dwellings, ILets.EMPTY);
		
		verify(action).apply(scopeArg.capture(), dwellingsArg.capture(), org.mockito.Matchers.eq(ILets.EMPTY));

		final IStateScope childScope = scopeArg.getValue();
		Assert.assertNotSame("Should not have invoked the action on the root scope.", scope, childScope);
		Assert.assertEquals("Action scope should be tagged with action", action, childScope.getTag());
		Assert.assertTrue("Child scope should have been close.", childScope.isClosed());
		
		Assert.assertEquals("Dwellings collection should have been passed to action.", dwellings, dwellingsArg.getValue());
	}
	
	@Test
	public void applyComponentsActionUsesNewScopeAndAppliesActionToEachDwelling() {
		final IComponentsAction action = mock(IComponentsAction.class);
				
		scope.apply(action, dwellings, ILets.EMPTY);
		
		verify(action, times(dwellings.size())).apply(componentsScopeArg.capture(), any(ILets.class));
		
		final List<ISettableComponentsScope> childScopes = componentsScopeArg.getAllValues();
		final Set<Integer> dwellingIdsActedOn = new HashSet<Integer>();
		for(final ISettableComponentsScope cScope : childScopes) {
			dwellingIdsActedOn.add(cScope.getDwellingID());
		}
		
		Assert.assertEquals("Action should have been called on different scopes for each dwelling id.", dwellingIds,  dwellingIdsActedOn);
	}
    
    @Test
	public void getsRootState() {
		final IStateAction stateAction = mock(IStateAction.class);
		final IComponentsAction componentsAction = mock(IComponentsAction.class);
		
		scope.apply(stateAction, dwellings, ILets.EMPTY);
		scope.apply(componentsAction, dwellings, ILets.EMPTY);
		
		Assert.assertEquals("StateScope never branches: getState always returns the root state.", rootState, scope.getState());
	}
	
	@Test
	public void scopesForDwellingsAreUnifiedCorrectly() {
		final ComponentsActionWhichAddsNotes a1 = new ComponentsActionWhichAddsNotes("hello");
		final ComponentsActionWhichAddsNotes a2 = new ComponentsActionWhichAddsNotes("goodbye");
		final ComponentsActionWhichAddsNotes a3 = new ComponentsActionWhichAddsNotes("onion");
		
		scope.apply(new StateActionWhichCallsComponentsAction(a1), dwellings, ILets.EMPTY);
		scope.apply(new StateActionWhichCallsComponentsAction(a2), dwellings, ILets.EMPTY);
		scope.apply(new StateActionWhichCallsComponentsAction(a3), oneDwelling, ILets.EMPTY);
		
		for (final IDwelling d : dwellings) {
			Optional<IComponentsScope> unified = scope.getComponentsScope(d);
			Assert.assertTrue("A unified components scope should exist for " + d, unified.isPresent());
			final IComponentsScope scope = unified.get();
			final List<String> notes = scope.getAllNotes(String.class);
			if (oneDwelling.contains(d)) {
				Assert.assertEquals("One dwelling should get hello, goodbye, and onion as notes",
					ImmutableList.of("hello", "goodbye", "onion"), notes);
			} else {
				Assert.assertEquals("Each dwelling should get hello and goodbye as notes",
						ImmutableList.of("hello", "goodbye"), notes);
			}
		}
	}
	
	class ComponentsActionWhichAddsNotes implements IComponentsAction {

		private Object value;

		public ComponentsActionWhichAddsNotes(Object value) {
			super();
			this.value = value;
		}

		@Override
		public StateChangeSourceType getSourceType() {
			return StateChangeSourceType.ACTION;
		}

		@Override
		public Name getIdentifier() {
			return Name.of("test");
		}

		@Override
		public boolean apply(ISettableComponentsScope scope, ILets lets)
				throws NHMException {
			scope.addNote(value);
			return true;
		}

		@Override
		public boolean isSuitable(IComponentsScope scope, ILets lets) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isAlwaysSuitable() {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	class StateActionWhichCallsComponentsAction implements IStateAction {
		private final IComponentsAction componentsAction;

		public StateActionWhichCallsComponentsAction(final IComponentsAction componentsAction) {
			this.componentsAction = componentsAction;
		}
		
		@Override
		public StateChangeSourceType getSourceType() {
			return null;
		}

		@Override
		public Name getIdentifier() {
			return Name.of("test");
		}

		@Override
		public Set<IDwelling> apply(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) throws NHMException {
			return scope.apply(componentsAction, dwellings, lets);
		}

        @Override
        public Set<IDwelling> getSuitable(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) {
            return dwellings;
        }
	}
}
