package uk.org.cse.nhm.ipc.api.status;

import java.util.UUID;

/**
 * Provides an update for a particular status
 * <br />
 * e.g. This would be the heartbeat for a machine status
 * <br />
 *
 * @see {@link IStatus}
 *
 * @since 3.7.0
 * @param <T>
 */
public interface IStatusUpdate {

    Long getTime();

    UUID getObjectID();
}
