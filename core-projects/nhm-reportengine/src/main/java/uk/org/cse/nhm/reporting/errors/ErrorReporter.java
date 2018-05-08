package uk.org.cse.nhm.reporting.errors;

import uk.org.cse.nhm.logging.logentry.errors.SystemErrorLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;

public class ErrorReporter extends AbsErrorReporter<SystemErrorLogEntry> {

    public ErrorReporter(final IOutputStreamFactory streamFactory) {
        super(streamFactory, "errors.txt", SystemErrorLogEntry.class);
    }

    @Override
    protected String output(final SystemErrorLogEntry entry) {
        return entry.getMessage() + "\n" + entry.getStackTrace() + "\n\n=============================================\n";
    }
}
