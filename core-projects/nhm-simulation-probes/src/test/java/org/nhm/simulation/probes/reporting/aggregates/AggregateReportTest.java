package org.nhm.simulation.probes.reporting.aggregates;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.simulation.reporting.aggregates.AggregateReport;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.OnChangeMode;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;

public class AggregateReportTest {

    private static final String GROUP = "group";
    private static Set<String> empty = Collections.<String>emptySet();

    @Test
    public void testAggregateReport() {
        final ILets lets = ILets.EMPTY;
        final IAggregationFunction af = mock(IAggregationFunction.class, "S1");
        final IAggregationFunction af2 = mock(IAggregationFunction.class, "S2");
        final List<IAggregationFunction> aggregations = ImmutableList.of(af, af2);
        final ITimeDimension time = mock(ITimeDimension.class);
        final IDwelling d1 = mock(IDwelling.class);
        final IDwelling d2 = mock(IDwelling.class);

        final IState state = mock(IState.class);
        when(af.evaluate(state, lets, ImmutableSet.of(d1))).thenReturn(1d);
        when(af.getIdentifier()).thenReturn(Name.of("S1"));
        when(af2.evaluate(state, lets, ImmutableSet.of(d1))).thenReturn(2d);
        when(af2.getIdentifier()).thenReturn(Name.of("S2"));
        when(af.evaluate(state, lets, ImmutableSet.of(d2))).thenReturn(3d);
        when(af2.evaluate(state, lets, ImmutableSet.of(d2))).thenReturn(4d);

        final ILogEntryHandler loggingService = mock(ILogEntryHandler.class);
        final AggregateReport r = new AggregateReport("test-execution", loggingService, state, time, new OnChangeMode(), aggregations);
        r.setIdentifier(Name.of("test"));

        final ArgumentCaptor<ISimulationLogEntry> requestCaptor = ArgumentCaptor.forClass(ISimulationLogEntry.class);
        when(state.get(time, null)).thenReturn(TimeUtil.mockTime(new DateTime(0)));

        r.groupChanged(groupOf("Banana"), ImmutableSet.of(d1), empty, false);
        r.groupChanged(groupOf("Apple"), ImmutableSet.of(d2), empty, false);

        verify(loggingService, times(2)).acceptLogEntry(requestCaptor.capture());
        final List<ISimulationLogEntry> values = requestCaptor.getAllValues();

        verifyLogEntry(
                (AggregateLogEntry) values.get(0), "Banana", ImmutableMap.of("S1", 1d, "S2", 2d));

        verifyLogEntry(
                (AggregateLogEntry) values.get(1), "Apple", ImmutableMap.of("S1", 3d, "S2", 4d));
    }

    private ImmutableMap<String, String> groupOf(final String text) {
        return ImmutableMap.of(GROUP, text);
    }

    private void verifyLogEntry(
            final AggregateLogEntry e,
            final String string,
            final ImmutableMap<String, Double> of) {
        Assert.assertEquals(string, e.getReducedRowKey().get(GROUP));
        Assert.assertEquals("test", e.getName());

        Assert.assertEquals(of, e.getColumns());
    }
}
