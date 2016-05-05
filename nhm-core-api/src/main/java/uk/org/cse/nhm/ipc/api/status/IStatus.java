package uk.org.cse.nhm.ipc.api.status;

import org.joda.time.DateTime;

/**
 * Provides a class containing the status of something
 * <br />
 * @see {@link IStatusUpdate}
 * 
 * @since 3.7.0
 */
public interface IStatus {
	/**
	 * @return the time the machine last provided a status to the status service
	 * @since 3.7.0
	 */
	public DateTime getLastUpdateTime();
}
