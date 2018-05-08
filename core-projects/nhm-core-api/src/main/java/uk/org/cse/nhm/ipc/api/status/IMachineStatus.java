package uk.org.cse.nhm.ipc.api.status;

import java.util.UUID;

/**
 * Provides information about the status of a machine connected to the
 * communications service.
 *
 * @since 3.7.0
 *
 */
public interface IMachineStatus extends IStatus {

    /**
     * @since 3.7.0
     * @return the friendly name for the machine (maybe hostname?)
     */
    public String getMachineLabel();

    /**
     * @return the globally unique identifier for the machine
     * @since 3.7.0
     */
    public UUID getMachineID();

    /**
     * @return false iff the last message the status service transmitted was a
     * disconnection message. Note that if this returns false, it does not mean
     * that the machine is definitely alive, just that it has not cleanly
     * shutdown OR it is alive. Examine {@link #getLastUpdateTime()} to check
     * whether this is also a very old status to get an idea.
     */
    public boolean isConnected();
}
