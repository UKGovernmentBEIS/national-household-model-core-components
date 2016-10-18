package uk.org.cse.nhm.simulator.main;

import java.util.Date;
import java.util.concurrent.Callable;

import org.joda.time.DateTime;

import uk.org.cse.nhm.NHMException;

/**
 * ISimulator.
 *
 * @author richardt
 * @version $Id: ISimulator.java 94 2010-09-30 15:39:21Z richardt
 * @since 1.3.0-CONSTITUTION
 */
public interface ISimulator extends AutoCloseable {
    /**
     * Step the simulation; this runs a batch of events from the queue. If you want to see how the simulation is
     * getting along, you can use {@link #getCurrentDate()} to see what the simulation time is at the moment.
     * 
     * @return true if the simulation has more events to run before the end date ({@link #setEndDate(Date)})
     * @throws NHMException
     */
    public abstract int step() throws NHMException;
    /**
     * @return The date of the event at the front of the simulation event queue.
     */
    public abstract DateTime getCurrentDate();
    
    public void addSimulationStepListener(ISimulationStepListener listener);
    
    public void removeSimulationStepListener(ISimulationStepListener listener);
    
    /**
	 * Schedule the given event for despatch at the given date.
     * @param priority TODO
     * @param callback TODO
	 */
	public void schedule(final DateTime dateTime, Priority priority, IDateRunnable callback);
	
	/**
     * Add a checkpoint to the queue - this is an event which will wake the simulator, but not do anything.
     * @param dt
     */
	public void addCheckpoint(DateTime dt);
    
    @Override
	public void close();
	long getStartTime();

    public void dieIfStopped();

    public void addStoppingCriterion(final Callable<Boolean> value);
}
