package uk.org.cse.nhm.simulation.reporting.probes;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.ProbeLogEntry;

/**
 * A probe collector which logs probe values to mongo using the logger gadget.
 *
 * @author hinton
 *
 */
public class ProbeCollector implements IProbeCollector {

    private final ILogEntryHandler loggingService;
    private final AtomicInteger probeSequence = new AtomicInteger(0);

    @Inject
    public ProbeCollector(
            final String executionID,
            final ILogEntryHandler loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public void collectProbe(final String probeName, final DateTime simulationTime, final int houseID, final float weight, final Map<String, Object> probedValues) {
        final ImmutableMap.Builder<String, Object> unDotted = ImmutableMap.builder();
        for (final Map.Entry<String, Object> e : probedValues.entrySet()) {
            unDotted.put(e.getKey().replace('.', '-'), e.getValue());
        }
        final ProbeLogEntry ple = new ProbeLogEntry(
                probeName,
                weight,
                simulationTime,
                probeSequence.getAndIncrement(),
                houseID,
                unDotted.build());

        loggingService.acceptLogEntry(ple);
    }
}
