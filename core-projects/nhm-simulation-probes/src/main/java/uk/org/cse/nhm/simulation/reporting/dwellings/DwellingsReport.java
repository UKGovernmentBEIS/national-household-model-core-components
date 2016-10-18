package uk.org.cse.nhm.simulation.reporting.dwellings;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.dwellings.DwellingLogEntry;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.IReportMode;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.IDwellingGroupListener;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.utility.DeduplicatingMap;

public class DwellingsReport extends AbstractNamed implements
        IDwellingGroupListener, ISimulationStepListener {

    public enum GroupChange {
        Entered,
        Exit,
        Remained;
    }

    private final ICanonicalState state;
    private final ILogEntryHandler loggingService;

    private final IDwellingGroup group;
    private final IReportMode mode;
    private final List<IComponentsFunction<?>> fields;

    private Set<IDwelling> added = new HashSet<>();
    private Set<IDwelling> removed = new HashSet<>();

    @Inject
    public DwellingsReport(@Assisted final IDwellingGroup group,
            @Assisted final IReportMode mode,
            @Assisted final List<IComponentsFunction<?>> fields,
            final ICanonicalState state, final ILogEntryHandler loggingService,
            final ISimulator simulator) {
        this.group = group;
        this.mode = mode;
        this.fields = fields;
        this.state = state;
        this.loggingService = loggingService;
        group.addListener(this);
        simulator.addSimulationStepListener(this);
    }

    @Override
    public void dwellingGroupChanged(final IStateChangeNotification cause,
            final IDwellingGroup source, final Set<IDwelling> added,
            final Set<IDwelling> removed) {

        if (added.isEmpty() && removed.isEmpty()) {
            return;
        }

        this.added.addAll(added);
        this.removed.addAll(removed);

        final Set<IDwelling> removedAndAdded = Sets.intersection(this.added,
                this.removed).immutableCopy();
        this.added.removeAll(removedAndAdded);
        this.removed.removeAll(removedAndAdded);
    }

    @Override
    public void simulationStepped(final DateTime dateOfStep,
            final DateTime nextDate, final boolean isFinalStep)
            throws NHMException {

        if (mode.shouldCalculateRow(dateOfStep)) {

            for (final IDwelling d : group.getContents()) {
                if (added.contains(d)) {
                    calculateAndMaybeLog(GroupChange.Entered, d, isFinalStep,
                            dateOfStep);
                } else {
                    calculateAndMaybeLog(GroupChange.Remained, d, isFinalStep,
                            dateOfStep);
                }
            }

            for (final IDwelling d : removed) {
                calculateAndMaybeLog(GroupChange.Exit, d, isFinalStep,
                        dateOfStep);
            }

            added = new HashSet<>();
            removed = new HashSet<>();
        }
    }

    private void calculateAndMaybeLog(final GroupChange change,
            final IDwelling d, final boolean isFinalStep, final DateTime now) {
        final ImmutableMap<String, Object> data = evaluateFieldsForDwelling(
                change, d);
        final Optional<ImmutableMap<String, Object>> maybeRow = mode
                .updateAndMaybeReturn(Integer.toString(d.getID()), data,
                        isFinalStep, now);

        if (maybeRow.isPresent()) {
            loggingService.acceptLogEntry(new DwellingLogEntry(getIdentifier().getName(), now, d.getID(), d.getWeight(), maybeRow.get()));
        }
    }

    private ImmutableMap<String, Object> evaluateFieldsForDwelling(
            final GroupChange change, final IDwelling d) {
        final DeduplicatingMap.Builder<Object> data = DeduplicatingMap.stringBuilder();

        data.put(DwellingLogEntry.GROUP_CHANGE, change);
        final IComponentsScope s = state.detachedScope(d);

        for (final IComponentsFunction<?> field : fields) {
            final String fieldName = field.getIdentifier().getName();
            try {
                data.put(fieldName, field.compute(s, ILets.EMPTY));
            } catch (final Exception e) {
                data.put(fieldName, "NULL");
            }
        }

        return data.build();
    }
}
