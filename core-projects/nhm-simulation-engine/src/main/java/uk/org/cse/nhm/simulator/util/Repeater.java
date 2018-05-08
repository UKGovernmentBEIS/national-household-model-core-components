package uk.org.cse.nhm.simulator.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Priority;

/**
 * This is a utility class for things which want to be invoked repeatedly at a
 * fixed interval.
 *
 * Subclasses define {@link #fire(Date)} to take action when the clock comes
 * around.
 *
 * @author hinton
 *
 */
public abstract class Repeater implements IDateRunnable {

    private final Priority priority;
    private final Period period;
    private final ISimulator simulator;

    public Repeater(final ISimulator queue, Priority priority, final int interval) {
        this(queue, priority, Period.days(interval));
    }

    public Repeater(final ISimulator simulator, Priority priority, Period wakeInterval) {
        this.priority = priority;
        this.period = wakeInterval;
        this.simulator = simulator;
    }

    public void start(final DateTime startDate) {
        schedule(startDate);
    }

    private void schedule(DateTime startDate) {
        simulator.schedule(startDate, priority, this);
    }

    @Override
    public void run(DateTime date) throws NHMException {
        if (fire(date)) {
            schedule(date.plus(period));
        }
    }

    /**
     * Take whatever action is necessary on the given day
     *
     * @param date
     * @return true if this repeater should keep repeating, false if this is the
     * end for it.
     */
    protected abstract boolean fire(final DateTime date) throws NHMException;
}
