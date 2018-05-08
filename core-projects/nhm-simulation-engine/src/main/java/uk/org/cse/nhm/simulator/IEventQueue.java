package uk.org.cse.nhm.simulator;

import java.util.List;

import org.joda.time.DateTime;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.Priority;

/**
 * The event queue at the heart of the simulator; this is a callback driven
 * system, so you register a callback to be executed using
 * {@link #addEvent(ICallback)}, and then {@link ICallback#run()} will execute
 * when we get to that point in time
 *
 * @author hinton
 *
 */
public interface IEventQueue {

    /**
     * Schedule the given event for despatch at the given date.
     *
     * @param priority TODO
     * @param callback TODO
     */
    public void schedule(final DateTime dateTime, Priority priority, IDateRunnable callback);

    /**
     * @return True iff there are no unprocessed events in the queue
     */
    public boolean isEmpty();

    /**
     * Run all the events at the front of the queue until the time of next event
     * from {@link #getTimeOfNextEvent()} has changed, or there are no events
     * left to run.
     *
     * @return TODO
     * @throws NHMException
     */
    public List<String> despatch() throws NHMException;

    /**
     * Equivalent to <code>while(!isEmpty()) despatch();</code> - run the
     * simulation until there's nothing left to do.
     *
     * @throws NHMException
     */
    public void drain() throws NHMException;

    /**
     * Get the time of the event at the head of the queue
     *
     * @return
     */
    public DateTime getTimeOfNextEvent();

    /**
     * Add a checkpoint to the queue - this is an event which will wake the
     * simulator, but not do anything.
     *
     * @param dt
     */
    public void addCheckpoint(DateTime dt);

    public int size();
}
