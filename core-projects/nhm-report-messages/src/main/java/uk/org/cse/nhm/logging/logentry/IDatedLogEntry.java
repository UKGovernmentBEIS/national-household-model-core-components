package uk.org.cse.nhm.logging.logentry;

import org.joda.time.DateTime;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;

public interface IDatedLogEntry extends ISimulationLogEntry {

    public DateTime getDate();
}
