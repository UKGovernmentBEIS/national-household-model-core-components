package uk.org.cse.nhm.simulator.state;

import uk.org.cse.nhm.simulator.trigger.ITrigger;

/**
 * Indicates the kind of thing that caused a change to the state
 * @author hinton
 *
 */
public enum StateChangeSourceType {
	/**
	 * This is used when the simulation is booted up only
	 */
	CREATION, 
	
	/**
	 * Used when obligations are fulfilled
	 */
	OBLIGATION,
	/**
	 * This is used for special internal mechanisms
	 */
	INTERNAL, 
	/**
	 * This is used for the extra special internal mechanism of the passage of time
	 */
	TIME, 
	/**
	 * This is used for {@link ITrigger} implementations, which make branches canonical
	 */
	TRIGGER, 
	/**
	 * This is used by the various kinds of action which modify branches but
	 * don't make them canonical
	 */
	ACTION
}
