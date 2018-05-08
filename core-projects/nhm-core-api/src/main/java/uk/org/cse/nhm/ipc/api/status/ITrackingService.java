package uk.org.cse.nhm.ipc.api.status;

import java.io.Closeable;
import java.util.Set;

/**
 * The status service provides information about the status of different things
 * machines and services within the NHM.
 *
 * @since 3.7.0
 *
 */
public interface ITrackingService<Key, T> extends Closeable {

    public interface IListener<Key, T> {

        public void onStatusChange(final Key key, final T thing);
    }

    /**
     * @return the latest set of statuses for every thing the status service is
     * keeping track of.
     * @since 3.7.0
     */
    public Set<? extends T> getStatus();

    public T getStatus(final Key key);

    /**
     * Adds a callback to execute when a new status update occurs
     *
     * @since 3.7.0
     * @param callback
     */
    boolean registerUpdateListener(IListener<Key, T> l);

    /**
     * Removes a callback
     *
     * @since 3.7.0
     * @param callback
     */
    boolean unregisterUpdateListener(IListener<Key, T> l);
}
