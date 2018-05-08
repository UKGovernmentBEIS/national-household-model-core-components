package uk.org.cse.nhm.ipc.api.tasks.sim;

import java.io.Closeable;

import uk.org.cse.nhm.ipc.api.tasks.ITaskSession;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;

/**
 * A callbacky handle for {@link ISimulationRunner} to log status and reporting
 * information into.
 *
 * NOTE: The use of Json annotations here enables us to (de)serialise the
 * concrete implementations of this class (when the type is known ahead of time)
 * without the need to have any ugly casting.
 *
 * @since 3.7.0
 *
 */
public interface ISimulationSession extends Closeable, ILogEntryHandler, ITaskSession {

}
