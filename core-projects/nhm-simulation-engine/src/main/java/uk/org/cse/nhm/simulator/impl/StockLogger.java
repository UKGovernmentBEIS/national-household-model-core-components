package uk.org.cse.nhm.simulator.impl;

import javax.inject.Inject;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.SurveyCaseLogEntry;

public class StockLogger {

    private final ILogEntryHandler loggingService;
    private boolean log;

    @Inject
    public StockLogger(final ILogEntryHandler loggingService) {
        this.loggingService = loggingService;
        this.log = false;
    }

    public void shouldLog() {
        log = true;
    }

    public void acceptCase(final SurveyCaseLogEntry entry) {
        if (log) {
            loggingService.acceptLogEntry(entry);
        }
    }
}
