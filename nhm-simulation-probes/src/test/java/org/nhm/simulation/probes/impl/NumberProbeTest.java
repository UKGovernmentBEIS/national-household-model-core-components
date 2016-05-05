package org.nhm.simulation.probes.impl;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.nhm.simulation.probes.reporting.aggregates.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulation.reporting.probes.IProbeCollector;
import uk.org.cse.nhm.simulation.reporting.probes.IProbingFunction;
import uk.org.cse.nhm.simulation.reporting.probes.NumberProbe;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class NumberProbeTest {
	@Test
	public void testNumberProbeDelegatesAndCapturesValue() {
		final IProbeCollector collector = mock(IProbeCollector.class);
		final ITimeDimension time = mock(ITimeDimension.class);
		
		@SuppressWarnings("unchecked")
		final IComponentsFunction<Number> delegate = mock(IComponentsFunction.class);
		final NumberProbe probe = new NumberProbe(time, collector,
				delegate, Collections.<IComponentsFunction<?>>emptyList());
		probe.setIdentifier(Name.of("test probe"));
		final IComponentsScope blah = mock(IComponentsScope.class);
		when(blah.get(time)).thenReturn(TimeUtil.mockTime(new DateTime(1234)));
		when(blah.getDwelling()).thenReturn(mock(IDwelling.class));
		
		
		when(blah.getDwellingID()).thenReturn(44);

		final IStateChangeSource currentCause = mock(IStateChangeSource.class);
		when(currentCause.getIdentifier()).thenReturn(Name.of("a"));
		when(blah.getTag()).thenReturn(currentCause);
		
		when(delegate.compute(eq(blah), eq(ILets.EMPTY))).thenReturn(35353d);
		
		final Double output = probe.compute(blah, ILets.EMPTY).doubleValue();
		
		Assert.assertEquals("The delegate return value should have been computed", 35353d, output, 0);
		
		verify(delegate).compute(eq(blah), eq(ILets.EMPTY));
		
		verify(collector).collectProbe("test probe", new DateTime(1234), 44, 0f,
				ImmutableMap.of(NumberProbe.BRANCH_KEY, "/a", NumberProbe.VALUE_KEY, (Object) 35353d));
	}
	
	@Test
	public void testNumberProbeDelegatesAndCapturesProbedValues() {
		final IProbeCollector collector = mock(IProbeCollector.class);
		final ITimeDimension time = mock(ITimeDimension.class);
		
		final IProbingFunction pf = mock(IProbingFunction.class);
		@SuppressWarnings("unchecked")
		final IComponentsFunction<Object> of = mock(IComponentsFunction.class, "X");
		
		@SuppressWarnings("unchecked")
		final IComponentsFunction<Number> delegate = mock(IComponentsFunction.class);
		final NumberProbe probe = new NumberProbe(time, collector,
				 delegate, ImmutableList.of(pf, of));
		probe.setIdentifier(Name.of("test probe"));
		
		final IComponentsScope blah = mock(IComponentsScope.class);
		when(blah.getDwellingID()).thenReturn(44);
		when(blah.get(time)).thenReturn(TimeUtil.mockTime(new DateTime(1234)));
		when(blah.getDwelling()).thenReturn(mock(IDwelling.class));
		
		
		final IStateChangeSource currentCause = mock(IStateChangeSource.class);
		when(currentCause.getIdentifier()).thenReturn(Name.of("a"));
		when(blah.getTag()).thenReturn(currentCause);

		when(delegate.compute(eq(blah), eq(ILets.EMPTY))).thenReturn(35353d);
		
		when(of.compute(eq(blah), eq(ILets.EMPTY))).thenReturn("Value for X");
		when(pf.compute(eq(blah), eq(ILets.EMPTY))).thenReturn(ImmutableMap.<String, Object>
			of("K1", "V1", "K2", "V2"));
		
		final Double output = probe.compute(blah, ILets.EMPTY).doubleValue();
		
		Assert.assertEquals("The delegate return value should have been computed", 35353d, output, 0);
		
		verify(delegate).compute(eq(blah), eq(ILets.EMPTY));
		
		verify(collector).collectProbe("test probe", new DateTime(1234), 44, 0f,
				ImmutableMap.of(NumberProbe.BRANCH_KEY, "/a", 
						NumberProbe.VALUE_KEY, (Object) 35353d,
								"X", "Value for X",
								"K1", "V1", 
								"K2", "V2"));
	}
	
	@Test
	public void testNumberProbeDependenciesAndChangeDateFromDelegate() {
		final IProbeCollector collector = mock(IProbeCollector.class);
		final ITimeDimension time = mock(ITimeDimension.class);
		
		@SuppressWarnings("unchecked")
		final IComponentsFunction<Number> delegate = mock(IComponentsFunction.class);
		final IDimension<?> d1 = mock(IDimension.class);
		final IDimension<?> d2 = mock(IDimension.class);
		when(delegate.getDependencies()).thenReturn(ImmutableSet.<IDimension<?>>of(d1, d2));
		when(delegate.getChangeDates()).thenReturn(ImmutableSet.<DateTime>of(new DateTime(0), new DateTime(1)));
		
		final NumberProbe probe = new NumberProbe(time, collector,
				delegate, Collections.<IComponentsFunction<?>>emptyList());
		probe.setIdentifier(Name.of("test probe"));
		
		Assert.assertEquals("Dependencies delegated", ImmutableSet.of(d1, d2), probe.getDependencies());
		Assert.assertEquals("Change dates delegated", ImmutableSet.of(new DateTime(0), new DateTime(1)), probe.getChangeDates());
	}
	
	@Test
	public void testNumberProbeDependenciesFromDelegateAndProbesButChangeDateOnlyFromDelegate() {
		final IProbeCollector collector = mock(IProbeCollector.class);
		final ITimeDimension time = mock(ITimeDimension.class);
		
		@SuppressWarnings("unchecked")
		final IComponentsFunction<Object> prober = mock(IComponentsFunction.class);
		
		@SuppressWarnings("unchecked")
		final IComponentsFunction<Number> delegate = mock(IComponentsFunction.class);
		final IDimension<?> d1 = mock(IDimension.class);
		final IDimension<?> d2 = mock(IDimension.class);
		final IDimension<?> d3 = mock(IDimension.class);
		when(delegate.getDependencies()).thenReturn(ImmutableSet.<IDimension<?>>of(d1, d2));
		when(delegate.getChangeDates()).thenReturn(ImmutableSet.<DateTime>of(new DateTime(0), new DateTime(1)));
		
		when(prober.getDependencies()).thenReturn(ImmutableSet.<IDimension<?>>of(d3));
		when(prober.getChangeDates()).thenReturn(ImmutableSet.of(new DateTime(99)));
		
		final NumberProbe probe = new NumberProbe(time, collector,
				delegate, Collections.<IComponentsFunction<?>>singletonList(prober));
		probe.setIdentifier(Name.of("test probe"));
		
		Assert.assertEquals("Dependencies from probed & delegate", ImmutableSet.of(d1, d2, d3), probe.getDependencies());
		Assert.assertEquals("Change dates only from delegate", ImmutableSet.of(new DateTime(0), new DateTime(1)), probe.getChangeDates());
	}
}
