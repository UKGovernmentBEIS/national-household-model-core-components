package uk.org.cse.nhm.logging.logentry.batch;

import java.util.UUID;

import com.google.common.base.Optional;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;

public class BatchLogEntryConverter {

    public static Optional<BatchOutputEntry> convert(ISimulationLogEntry original, UUID taskID) {
        if (original instanceof IKeyValueLogEntry) {
            final IKeyValueLogEntry grouped = (IKeyValueLogEntry) original;
            return Optional.of(new BatchOutputEntry(taskID, grouped.getFullRowKey(), grouped.getColumns()));
        } else {
            return Optional.absent();
        }
    }
}
