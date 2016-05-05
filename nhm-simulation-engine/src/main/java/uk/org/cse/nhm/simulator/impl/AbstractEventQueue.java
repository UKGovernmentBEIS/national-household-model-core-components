package uk.org.cse.nhm.simulator.impl;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.IEventQueue;

/**
 * Abstract base class for event queue implementations which provides listeners.
 * @author tomh
 *
 */
public abstract class AbstractEventQueue implements IEventQueue {
	public void drain() throws NHMException {
		while (!isEmpty()) despatch();
	}
}
