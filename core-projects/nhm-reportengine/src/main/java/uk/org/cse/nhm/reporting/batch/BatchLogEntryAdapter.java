package uk.org.cse.nhm.reporting.batch;

import java.io.IOException;
import java.util.UUID;

import com.google.common.base.Optional;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.SimulationProgressLogEntry;
import uk.org.cse.nhm.logging.logentry.batch.BatchLogEntryConverter;
import uk.org.cse.nhm.logging.logentry.batch.BatchOutputEntry;
import uk.org.cse.nhm.logging.logentry.errors.SystemErrorLogEntry;

public class BatchLogEntryAdapter implements ILogEntryHandler {
	private final ILogEntryHandler delegate;
	private final UUID taskID;
	
	public BatchLogEntryAdapter(final ILogEntryHandler delegate, final UUID taskID) {
		super();
		this.delegate = delegate;
		this.taskID = taskID;
	}

	@Override
	public void close() throws IOException {
		delegate.close();
	}

	@Override
	public void acceptLogEntry(final ISimulationLogEntry entry) {
		if (entry instanceof SimulationProgressLogEntry) {
			delegate.acceptLogEntry(entry);
		} else if (entry instanceof SystemErrorLogEntry) {
			delegate.acceptLogEntry(entry);
		} else {
			final Optional<BatchOutputEntry> converted = BatchLogEntryConverter.convert(entry, taskID);
			delegate.acceptLogEntry(converted.isPresent() ? converted.get() : entry);
		}
	}
}
