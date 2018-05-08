package uk.org.cse.nhm.simulator.main;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.management.timer.Timer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.IEventQueue;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.StopRunningException;
import uk.org.cse.nhm.simulator.guice.SimpleScope;
import uk.org.cse.nhm.simulator.profile.IProfiler;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;

/**
 * An injector-based simulator; contains a module which configures stuff to the
 * standard interfaces.
 *
 * @author tomh
 * @version $Id: cse-eclipse-codetemplates.xml 94 2010-09-30 15:39:21Z tomh $
 * @since $
 */
public class Simulator implements ISimulator {

    private static final Logger log = LoggerFactory.getLogger(Simulator.class);
    private final IEventQueue eventQueue;
    final AtomicInteger uniqueInt = new AtomicInteger(100);

    private final List<ISimulationStepListener> listeners = new LinkedList<ISimulationStepListener>();

    private final List<Callable<Boolean>> stoppingCriteria = new LinkedList<>();

    private long simulationStart = -1;
    private DateTime currentDate;
    private final DateTime endDate;
    private final ICanonicalState state;
    private final ITimeDimension timeDimension;
    boolean isFirstStep = true;
    private boolean closed = false;
    private final SimpleScope scope;
    @Inject
    private IProfiler profiler;

    @Inject
    public Simulator(@Named(SimulatorConfigurationConstants.START_DATE) final DateTime startDate,
            @Named(SimulatorConfigurationConstants.END_DATE) final DateTime endDate, final IEventQueue eventQueue,
            final ICanonicalState state, final ITimeDimension timeDimension, final SimpleScope scope) {
        this.endDate = endDate;
        this.currentDate = startDate;
        this.eventQueue = eventQueue;
        this.state = state;
        this.timeDimension = timeDimension;
        this.scope = scope;
        eventQueue.addCheckpoint(startDate);
        eventQueue.addCheckpoint(endDate);
    }

    @Override
    public long getStartTime() {
        return simulationStart;
    }

    /**
     * @return @throws NHMException
     * @see uk.org.cse.nhm.simulator.main.ISimulator#step()
     */
    @Override
    public int step() throws NHMException {
        if (simulationStart == -1) {
            simulationStart = System.currentTimeMillis();
        }

        if (eventQueue.isEmpty()) {
            return 0;
        } else {
            profiler.start("Step Sim", "STEP");
            final DateTime before = getCurrentDate();

            if (timeDimension.setCurrentTime(before)) {
                state.checkForEndogenousChanges();
            }

            isFirstStep = false;

            final long start = System.currentTimeMillis();
            final List<String> ran = eventQueue.despatch();
            final long duration = System.currentTimeMillis() - start;

            // this is the time of the /next/ event
            final DateTime now = eventQueue.getTimeOfNextEvent();

            final long simTimeElapsed = now.getMillis() - before.getMillis();

            final String speed;
            if (duration > 0) {
                speed = String.format("%.2f", simTimeElapsed / (double) duration);
            } else {
                speed = "∞";
            }

            log.info("Reached {}, running at {} s/s; just executed {}",
                    DateTimeFormat.shortDate().print(now),
                    speed, ran);

            final boolean finished = eventQueue.isEmpty();

            for (final ISimulationStepListener listener : listeners.toArray(new ISimulationStepListener[0])) {
                profiler.start(listener.getClass().getSimpleName(), "LISTENER");
                listener.simulationStepped(before, now, finished);
                profiler.stop();
            }

            // should this happen here, or at the next step???
            currentDate = now;
            profiler.stop();
        }

        if (eventQueue.isEmpty()) {
            final long totalTime = System.currentTimeMillis() - simulationStart;
            log.info("Completed in {} minutes", totalTime / Timer.ONE_MINUTE);
        }

        return eventQueue.size();
    }

    /**
     * @return @see uk.org.cse.nhm.simulator.main.ISimulator#getCurrentDate()
     */
    @Override
    public DateTime getCurrentDate() {
        return currentDate;
    }

    @Override
    public void addSimulationStepListener(final ISimulationStepListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeSimulationStepListener(final ISimulationStepListener listener) {
        listeners.removeAll(Collections.singleton(listener));
    }

    @Override
    public void close() {
        if (closed) {
            log.warn("Simulator closed twice");
        } else {
            log.debug("Closing simulation scope");
            this.scope.exit();
            closed = true;
        }
    }

    private boolean canSchedule(final DateTime dt) {
        return !endDate.isBefore(dt);
    }

    @Override
    public void addCheckpoint(final DateTime dt) {
        if (canSchedule(dt)) {
            this.eventQueue.addCheckpoint(dt);
        } else {
            log.debug("Ignoring checkpoint at {} (later than {})", dt, endDate);
        }
    }

    @Override
    public void schedule(final DateTime dateTime, final Priority priority, final IDateRunnable callback) {
        if (canSchedule(dateTime)) {
            this.eventQueue.schedule(dateTime, priority, callback);
        } else {
            log.debug("Ignoring event scheduled for {} (later than {})", dateTime, endDate);
        }
    }

    @Override
    public void dieIfStopped() {
        for (final Callable<Boolean> b : stoppingCriteria) {
            try {
                final Boolean shouldStop = b.call();
                if (shouldStop) {
                    log.info("Terminating run early");
                    throw new StopRunningException();
                }
            } catch (final Exception ex) {
                throw new RuntimeException("Exception evaluating stopping criterion: " + ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void addStoppingCriterion(final Callable<Boolean> stop) {
        stoppingCriteria.add(stop);
    }
}
