package uk.org.cse.nhm.reporting.standard;

import java.io.Closeable;
import java.nio.file.Path;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;

public interface IReportingSession extends ILogEntryHandler, Closeable {
	public Path getResultPath();
}
