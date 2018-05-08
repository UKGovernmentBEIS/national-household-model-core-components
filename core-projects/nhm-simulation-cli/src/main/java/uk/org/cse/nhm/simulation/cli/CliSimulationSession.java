package uk.org.cse.nhm.simulation.cli;

import java.io.IOException;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationSession;
import uk.org.cse.nhm.reporting.standard.IReportingSession;

public class CliSimulationSession implements ISimulationSession {

    IReportingSession reportSession;

    public CliSimulationSession(final IReportingSession reportSession) {
        this.reportSession = reportSession;
    }

    @Override
    public void close() throws IOException {
        reportSession.close();
    }

    @Override
    public void acceptLogEntry(final ISimulationLogEntry entry) {
        reportSession.acceptLogEntry(entry);
    }

    @Override
    public void progress(final String message, final double proportion, Optional<DateTime> estimate) {

    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}
