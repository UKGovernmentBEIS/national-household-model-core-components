package uk.org.cse.nhm.simulation.construction;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.impl.TechnologyOperations;
import uk.org.cse.nhm.simulator.action.ConstructHousesAction;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.util.TimeUtil;

public class ConstructHousesActionTest {

	ConstructHousesAction houseConstructor;
	IStateScope scope;
	ICanonicalState state;
	IBranch change;
	IDwelling built;
	ISimulator sim;
	IDwelling existing;
	ITechnologyModel existingTech;
	DateTime now = new DateTime();
	private Set<IDimension<?>> dimensions;
	private IDimension<BasicCaseAttributes> basicAttributes;
	private IDimension<ITechnologyModel> technologies;
	private IDimension<Object> unsettable;
	private IDimension<Object> settable;
	final Object originalObject = new Object();
	

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		state = mock(ICanonicalState.class);
		scope = mock(IStateScope.class);
		change = mock(IBranch.class);
		built = mock(IDwelling.class);
		sim = mock(ISimulator.class);

		basicAttributes = mock(IDimension.class);
		technologies = mock(IDimension.class);
		unsettable = mock(IDimension.class);
		settable=  mock(IDimension.class);
		when(unsettable.isSettable()).thenReturn(false);
		when(settable.isSettable()).thenReturn(true);
		
		dimensions = ImmutableSet.<IDimension<?>>of(basicAttributes, technologies, unsettable, settable);
		//when(state.getDimensions()).thenReturn(dimensions);
		when(scope.getState()).thenReturn(change);
		
		existing = mock(IDwelling.class);
		
		when(change.get(basicAttributes, existing)).thenReturn(mock(BasicCaseAttributes.class));
		final ITechnologiesFactory techFactory = ITechnologiesFactory.eINSTANCE;
		existingTech = techFactory.createTechnologyModel();
		final TechnologyOperations ops = new TechnologyOperations();
		ops.installHeatSource(existingTech, techFactory.createCommunityHeatSource(), 0.0, 0.0);
		when(change.get(technologies, existing)).thenReturn(existingTech);
		
		when(change.get(settable, existing)).thenReturn(originalObject);

		final ITimeDimension time = mock(ITimeDimension.class);
		
		when(state.branch(any(int.class))).thenReturn(change);
		when(state.get(time, null)).thenReturn(TimeUtil.mockTime(now));
		when(scope.getState()).thenReturn(change);
		when(change.get(time, null)).thenReturn(TimeUtil.mockTime(now));
		when(change.createDwelling(any(Float.class))).thenReturn(built);

		houseConstructor = new ConstructHousesAction(time, technologies, basicAttributes, dimensions);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreatesNewHousesWithAllTheComponents() throws NHMException {
		houseConstructor.apply(scope, Collections.singleton(existing), ILets.EMPTY);
		for (@SuppressWarnings("rawtypes") final IDimension dim : dimensions) {
			if (dim.isSettable()) {
				verify(change).set(same(dim), same(built), notNull());
			} else {
				verify(change, atLeastOnce()).set(not(same(dim)), any(IDwelling.class), any());
			}
		}
	}

	@Test
	public void testConstructionDateChangedOnBuiltButNotOnExisting() throws NHMException {
		houseConstructor.apply(scope, Collections.singleton(existing), ILets.EMPTY);

		final ArgumentCaptor<BasicCaseAttributes> argument = ArgumentCaptor.forClass(BasicCaseAttributes.class);
		verify(change).set(same(basicAttributes), same(built), argument.capture());
		assertEquals("New houses should have the simulation's current year as their construction year.", now.getYear(), argument.getValue().getBuildYear());
		final BasicCaseAttributes existingBasic = change.get(basicAttributes, existing);
		
		verify(change).set(same(basicAttributes), same(built), not(same(existingBasic)));
	}
	
	@Test
	public void testHeatingInstallationDateChangedOnBuiltButNotOnExisting() throws NHMException {
		houseConstructor.apply(scope, Collections.singleton(existing), ILets.EMPTY);
		final ArgumentCaptor<ITechnologyModel> argument = ArgumentCaptor.forClass(ITechnologyModel.class);
		verify(change).set(same(technologies),same(built), argument.capture());
		final ITechnologyModel newTech = argument.getValue();
		
		Assert.assertEquals("Heat source installation year should have been updated.", now.getYear(), newTech.getCommunityHeatSource().getInstallationYear());

		verify(change).set(same(technologies), same(built), not(same(existingTech)));
	}
	
	@Test
	public void testUnchangedDwellingComponentsReused() throws NHMException {
		houseConstructor.apply(scope, Collections.singleton(existing), ILets.EMPTY);
		final Object existingObject = change.get(settable, existing);
		verify(change).set(same(settable), same(built), same(existingObject));
	}
}
