package uk.org.cse.nhm.simulator.state;

import uk.org.cse.commons.names.IIdentified;

/**
 * Associated with an {@link IStateChangeNotification} - this tells you what caused a state change.
 * @author hinton
 *
 */
public interface IStateChangeSource extends IIdentified {
	/**
	 * @return the kind of thing which caused a change
	 */
	public StateChangeSourceType getSourceType();
}
