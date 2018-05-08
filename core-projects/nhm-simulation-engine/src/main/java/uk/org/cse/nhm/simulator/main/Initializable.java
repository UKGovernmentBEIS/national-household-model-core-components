package uk.org.cse.nhm.simulator.main;

import uk.org.cse.nhm.NHMException;

/**
 * This class is a marker which is used primarily during the simulator build to
 * tell simulation objects that the simulation is about to be kicked off. In
 * their lifecycle, this should be invoked after depedency injection has
 * happened, and after any necessary other references have been set up.
 *
 * The typical use of this method is to add listeners to the event queue or
 * notification broker, and to post initial events to the queue.
 *
 * @author hinton
 *
 */
public interface Initializable {

    Initializable NOP = new Initializable() {
        @Override
        public void initialize() throws NHMException {

        }

        public String toString() {
            return "Initializable.NOP";
        }
    ;

    };

	/**
	 * Invoked just before the start of the simulation; use this method to do any late setup you need,
	 * post start events to the event queue, and so on.
	 * 
	 * @throws NHMException
	 */
    public void initialize() throws NHMException;
}
