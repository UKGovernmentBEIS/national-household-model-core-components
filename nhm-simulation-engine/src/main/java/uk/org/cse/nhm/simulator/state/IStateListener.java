package uk.org.cse.nhm.simulator.state;

/**
 * An interface for things which want to know when the {@link ICanonicalState} has changed (this is a per-sim singleton)
 * @author hinton
 *
 */
public interface IStateListener {
	/**
	 * Raised when the state has just been updated by something.
	 * @param state
	 * @param notification
	 */
	public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification);
}
