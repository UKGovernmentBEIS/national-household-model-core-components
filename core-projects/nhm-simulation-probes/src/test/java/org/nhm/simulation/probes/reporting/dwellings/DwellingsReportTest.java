package org.nhm.simulation.probes.reporting.dwellings;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableList;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.dwellings.DwellingLogEntry;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.IReportMode;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.OnChangeMode;
import uk.org.cse.nhm.simulation.reporting.dwellings.DwellingsReport;
import uk.org.cse.nhm.simulation.reporting.dwellings.DwellingsReport.GroupChange;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class DwellingsReportTest {

    private static final double VALUE = 0.0;
    private static final String NAME = "name";
    private static final DateTime NOW = new DateTime(0);

    private static final Set<IDwelling> EMPTY = Collections.emptySet();
    private static final String CHANGE_SOURCE_NAME = "change source name";

    private IDwelling dwelling;
    private Set<IDwelling> dwellings;
    private IDwellingGroup group;
    private IReportMode mode;
    private IComponentsFunction<Number> field;
    private IComponentsScope scope;
    private ICanonicalState state;
    private ILogEntryHandler loggingService;
    private ISimulator sim;
    private DwellingsReport report;
    private IStateChangeNotification cause;
    private ArgumentCaptor<DwellingLogEntry> logCaptor;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        dwelling = mock(IDwelling.class);
        dwellings = Collections.singleton(dwelling);
        group = mock(IDwellingGroup.class);

        mode = new OnChangeMode();
        scope = mock(IComponentsScope.class);
        state = mock(ICanonicalState.class);
        when(state.detachedScope(dwelling)).thenReturn(scope);

        field = mock(IComponentsFunction.class);
        final List<IComponentsFunction<?>> fields = ImmutableList.<IComponentsFunction<?>>of(field);
        when(field.compute(scope, ILets.EMPTY)).thenReturn(VALUE);

        when(field.getIdentifier()).thenReturn(Name.of("something"));

        loggingService = mock(ILogEntryHandler.class);
        sim = mock(ISimulator.class);
        report = new DwellingsReport(group, mode, fields, state, loggingService, sim);
        report.setIdentifier(Name.of(NAME));

        final IStateChangeSource changeSource = mock(IStateChangeSource.class);
        when(changeSource.getIdentifier()).thenReturn(Name.of(CHANGE_SOURCE_NAME));
        final IStateScope scope = mock(IStateScope.class);
        when(scope.getTag()).thenReturn(changeSource);
        cause = mock(IStateChangeNotification.class);
        when(cause.getRootScope()).thenReturn(scope);

        logCaptor = ArgumentCaptor.forClass(DwellingLogEntry.class);
    }

    @Test
    public void logsNothingIfGroupEmpty() {
        run(EMPTY);
        verifyLogEvents(0);
    }

    @Test
    public void logsNothingIfGroupContentsUnchanged() {
        run(dwellings);
        run(dwellings);

        verifyLogEvents(1);
        assertGroupChange(GroupChange.Remained);
    }

    @Test
    public void logsAddedDwellings() {
        addDwellings(dwellings);
        run(dwellings);

        verifyLogEvents(1);
        assertGroupChange(GroupChange.Entered);

    }

    @Test
    public void ignoresAddedThenRemovedDwellings() {
        addDwellings(dwellings);
        removeDwellings(dwellings);
        run(EMPTY);

        verifyLogEvents(0);
    }

    @Test
    public void logsRemovedDwellings() {
        removeDwellings(dwellings);
        run(EMPTY);

        verifyLogEvents(1);
        assertGroupChange(GroupChange.Exit);
    }

    @Test
    public void ignoredRemovedThenAddedDwellings() {
        // Setup the report with 1 dwelling in it.
        run(dwellings);
        verifyLogEvents(1);

        // Remove then re-add this dwelling.
        removeDwellings(dwellings);
        addDwellings(dwellings);

        // This time there shouldn't be any more logs..
        run(dwellings);
        verifyLogEvents(1);
    }

    @Test
    public void logsDwellingsWithChangedFields() {
        run(dwellings);
        when(field.compute(scope, ILets.EMPTY)).thenReturn(1.0);
        run(dwellings);

        verifyLogEvents(2);
        assertGroupChange(GroupChange.Remained);
    }

    @Test
    public void steppingResetsAddRemoveAndCauses() {

    }

    private void run(final Set<IDwelling> dwellings) {
        when(group.getContents()).thenReturn(dwellings);
        report.simulationStepped(NOW, NOW, false);
    }

    private void verifyLogEvents(final int times) {
        verify(loggingService, times(times)).acceptLogEntry(logCaptor.capture());
    }

    private void assertGroupChange(final GroupChange change) {
        Assert.assertEquals("Should recorded one change: " + change, change, logCaptor.getValue().getChangedDwellingData().get(DwellingLogEntry.GROUP_CHANGE));
    }

    private void removeDwellings(final Set<IDwelling> dwellings) {
        report.dwellingGroupChanged(cause, group, EMPTY, dwellings);
    }

    private void addDwellings(final Set<IDwelling> dwellings) {
        report.dwellingGroupChanged(cause, group, dwellings, EMPTY);
    }
}
