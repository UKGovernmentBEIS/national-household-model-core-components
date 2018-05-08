package org.nhm.simulation.probes.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.nhm.simulation.probes.reporting.aggregates.TimeUtil;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulation.reporting.probes.ActionProbe;
import uk.org.cse.nhm.simulation.reporting.probes.IProbeCollector;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class DwellingActionProbeTest {

    @Test
    public void testProbeForSuitableRecordsProbedValues() throws NHMException {
        final ITimeDimension time = mock(ITimeDimension.class);
        final IProbeCollector collector = mock(IProbeCollector.class);
        final IComponentsAction delegate = mock(IComponentsAction.class);
        @SuppressWarnings("unchecked")
        final IComponentsFunction<Object> probe = mock(IComponentsFunction.class, "test");

        final ISettableComponentsScope components = mock(ISettableComponentsScope.class);

        when(probe.compute(eq(components), eq(ILets.EMPTY))).thenReturn("value", "value after");
        when(components.get(time)).thenReturn(TimeUtil.mockTime(new DateTime(11)));

        when(components.apply(delegate, ILets.EMPTY)).thenReturn(true);
        when(components.getDwelling()).thenReturn(mock(IDwelling.class));

        when(delegate.isSuitable(components, ILets.EMPTY)).thenReturn(true);

        final ActionProbe p = new ActionProbe(time, collector, Optional.of(delegate), ImmutableList.<IComponentsFunction<?>>of(probe));

        p.setIdentifier(Name.of("test probe"));

        Assert.assertTrue("True should come out", p.apply(components, ILets.EMPTY));

        verify(collector).collectProbe("test probe", new DateTime(11), 0, 0f, ImmutableMap.<String, Object>of(ActionProbe.SUCCEEDED, true, "test (before)", "value", "test (after)", "value after"));
    }

    @Test
    public void testProbeForUnsuitables() throws NHMException {
        final ITimeDimension time = mock(ITimeDimension.class);
        final IProbeCollector collector = mock(IProbeCollector.class);
        final IComponentsAction delegate = mock(IComponentsAction.class);
        @SuppressWarnings("unchecked")
        final IComponentsFunction<Object> probe = mock(IComponentsFunction.class, "test");
        when(probe.compute(any(IComponentsScope.class), any(ILets.class))).thenReturn("value");
        final ISettableComponentsScope components = mock(ISettableComponentsScope.class);

        when(components.get(time)).thenReturn(TimeUtil.mockTime(new DateTime(11)));

        when(components.apply(any(IComponentsAction.class), any(ILets.class))).thenReturn(false);
        when(components.getDwelling()).thenReturn(mock(IDwelling.class));

        final ActionProbe p = new ActionProbe(time, collector, Optional.of(delegate), ImmutableList.<IComponentsFunction<?>>of(probe));

        p.setIdentifier(Name.of("test probe"));

        final boolean suitable = p.isSuitable(components, ILets.EMPTY);

        Assert.assertFalse(suitable);

        p.apply(components, ILets.EMPTY);

        verify(probe).compute(components, ILets.EMPTY);

        verify(collector).collectProbe("test probe", new DateTime(11), 0, 0f, ImmutableMap.<String, Object>of(ActionProbe.SUCCEEDED, false, "test (before)", "value", "test (after)", "n/a"));
    }
}
