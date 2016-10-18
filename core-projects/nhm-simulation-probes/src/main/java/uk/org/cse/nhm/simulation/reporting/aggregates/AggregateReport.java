package uk.org.cse.nhm.simulation.reporting.aggregates;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.simulation.reporting.aggregates.modes.IReportMode;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.utility.DeduplicatingMap;

public class AggregateReport extends AbstractNamed implements IGroups.IListener {
    private final List<IAggregationFunction> aggregations;
    private final IState state;
    private final ITimeDimension time;
    private final ILogEntryHandler loggingService;
    private final IReportMode mode;
    private ImmutableSet.Builder<String> longTermCauses = ImmutableSet.builder();

    @Inject
    public AggregateReport(
            final String executionID,
            final ILogEntryHandler loggingService,
            final IState state, final ITimeDimension time,
            @Assisted final IReportMode mode,
            @Assisted final List<IAggregationFunction> aggregations) {
        this.loggingService = loggingService;
        this.state = state;
        this.time = time;
        this.mode = mode;
        this.aggregations = aggregations;
    }

    @Override
    public void groupChanged(final ImmutableMap<String, String> divisions, final Set<IDwelling> contents,
            final Set<String> newCauses, final boolean isFinalStep) {
        final DateTime now = state.get(time, null).get(ILets.EMPTY);
        if (mode.shouldCalculateRow(now)) {
            final ImmutableMap<String, Object> row = computeRow(contents);

            longTermCauses.addAll(newCauses);

            final Optional<ImmutableMap<String, Object>> modeResultRow = mode.updateAndMaybeReturn(divisions, row,
                    isFinalStep, now);
            if (modeResultRow.isPresent()) {
                log(now, modeResultRow.get(), divisions, longTermCauses.build());
                longTermCauses = ImmutableSet.builder();
            }
        }
    }

    private ImmutableMap<String, Object> computeRow(final Set<IDwelling> contents) {
        final DeduplicatingMap.Builder<Object> builder = DeduplicatingMap.stringBuilder();

        for (final IAggregationFunction af : aggregations) {
            final double value = af.evaluate(state, ILets.EMPTY, contents);
            final String fieldName = af.getIdentifier().getName();
            builder.put(fieldName.replace('.', '-'), value);
        }

        return builder.build();
    }

    private void log(final DateTime dateTime, final Map<String, Object> row,
            final ImmutableMap<String, String> divisions, final Set<String> causes) {
        final ImmutableSortedMap.Builder<String, Double> numbers = ImmutableSortedMap.naturalOrder();

        for (final Entry<String, Object> e : row.entrySet()) {
            if (e.getValue() instanceof Double) {
                numbers.put(e.getKey(), (Double) e.getValue());
            } else {
                throw new IllegalArgumentException(
                        "This should never happen, but if it has, someone has passed a non-Double to an aggregate report");
            }
        }

        loggingService.acceptLogEntry(new AggregateLogEntry(getIdentifier().getName(), causes, divisions, dateTime,
                numbers.build()));
    }
}
