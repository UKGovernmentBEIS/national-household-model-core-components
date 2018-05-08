package uk.org.cse.nhm.ipc.api.tasks.report;

import java.io.Closeable;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;

public interface ILogEntryHandler extends Closeable {

    public void acceptLogEntry(ISimulationLogEntry entry);
}
