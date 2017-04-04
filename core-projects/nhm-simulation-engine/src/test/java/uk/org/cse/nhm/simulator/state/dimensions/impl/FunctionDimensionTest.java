package uk.org.cse.nhm.simulator.state.dimensions.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public class FunctionDimensionTest {
	DateTime startDate;
	ITimeDimension time;
	IState state;
	IDwelling instance;
	IDimension<Object> dim;
	private ISimulator simulator;
	ITime timeValue;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		startDate = new DateTime(0);
		simulator = mock(ISimulator.class);
		time = mock(ITimeDimension.class);
		state = mock(IState.class);
		instance = mock(IDwelling.class);
		dim = mock(IDimension.class);
		timeValue = mock(ITime.class);
		when(state.get(eq(time), any(IDwelling.class))).thenReturn(timeValue);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGenerationCounterWithoutTime() {
		final FunctionDimension<Object> fd = new FunctionDimension<Object>(new DimensionCounter(), "Test", state, time,simulator, startDate, XForesightLevel.Alpha, ConstantComponentsFunction.of(Name.of("test"), new Object()));
		
		Assert.assertEquals("With no function, gen = 0", 0, fd.getGeneration(instance));
		
		final IComponentsFunction<Object> fn = mock(IComponentsFunction.class);
		
		when(fn.getDependencies()).thenReturn(ImmutableSet.<IDimension<?>>of(dim));
		when(state.getGeneration(dim, instance)).thenReturn(35353);
		
		fd.setFunction(fn);

		Assert.assertEquals("With a function gen = dep gen", 35353, fd.getGeneration(instance));
		
		when(state.getGeneration(dim, instance)).thenReturn(35359);
		
		Assert.assertEquals("With a function gen = dep gen", 35359, fd.getGeneration(instance));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGenerationCounterWithTime() {
		final FunctionDimension<Object> fd = new FunctionDimension<Object>(new DimensionCounter(), "Test", state, time, simulator, startDate, XForesightLevel.Alpha, ConstantComponentsFunction.of(Name.of("test"), new Object()));
		
		Assert.assertEquals("With no function, gen = 0", 0, fd.getGeneration(instance));
		
		final IComponentsFunction<Object> fn = mock(IComponentsFunction.class);
		
		when(fn.getDependencies()).thenReturn(ImmutableSet.<IDimension<?>>of(dim, time));
		when(fn.getChangeDates()).thenReturn(ImmutableSet.of(new DateTime(5)));
		
		fd.setFunction(fn);
		
		when(state.getGeneration(dim, instance)).thenReturn(35353);
		when(state.getGeneration(time, null)).thenReturn(0);
		when(timeValue.get(XForesightLevel.Alpha)).thenReturn(new DateTime(0));
		final int oldGen = fd.getGeneration(instance);
		
		when(timeValue.get(XForesightLevel.Alpha)).thenReturn(new DateTime(11));
		when(state.getGeneration(time, null)).thenReturn(1);
		
		final int newGen = fd.getGeneration(instance);
		
		Assert.assertTrue("Generation should have advanced", newGen > oldGen);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testBranchGenerationCounter() {
		final FunctionDimension<Object> fd = new FunctionDimension<Object>(new DimensionCounter(), "Test", state, time, simulator, startDate, XForesightLevel.Alpha, ConstantComponentsFunction.of(Name.of("test"), new Object()));
		
		Assert.assertEquals("With no function, gen = 0", 0, fd.getGeneration(instance));
		
		final IComponentsFunction<Object> fn = mock(IComponentsFunction.class);
		
		when(fn.getDependencies()).thenReturn(ImmutableSet.<IDimension<?>>of(dim));
		when(state.getGeneration(dim, instance)).thenReturn(35353);
		
		fd.setFunction(fn);

		final IBranch branch = mock(IBranch.class);
		
		when(branch.getGeneration(dim, instance)).thenReturn(100000);
		
		final IInternalDimension<Object> fork = fd.branch(branch, 1);
		
		Assert.assertTrue("Generation counter increased in branch", fork.getGeneration(instance) > fd.getGeneration(instance));
	}
}
