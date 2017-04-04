package uk.org.cse.nhm.simulator.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.Priority;
import uk.org.cse.nhm.simulator.profile.IProfiler;

/**
 * A simple implementation of an event queue, backed by a java priority queue.
 * 
 * We may want something more scalable later.
 * 
 * @author tomh
 *
 */
public class SimpleEventQueue extends AbstractEventQueue {
	final HashSet<DateTime> checkpoints = new HashSet<DateTime>();
	
	private final AtomicInteger sequence = new AtomicInteger();
	
	@AutoProperty
	private static class Event implements Comparable<Event> {
		private final DateTime dateTime;
		private final Priority priority;
		private final int sequence;
		private final IDateRunnable callback;
		
		Event(final DateTime dateTime, final Priority priority, final IDateRunnable callback, final int sequence) {
			super();
			this.dateTime = dateTime;
			this.priority = priority;
			this.callback = callback;
			this.sequence = sequence;
		}

		@Override
		public int compareTo(final Event arg0) {
			final int date = dateTime.compareTo(arg0.dateTime);
			if (date != 0) return date;
			final int pri = Priority.comparator().compare(priority, arg0.priority);
			if (pri != 0) return pri;
			return Integer.compare(sequence, arg0.sequence);
		}
		
		public void fire() {
			if (callback != null) {
				callback.run(dateTime);
			}
		}
		
		@Override
		public String toString() {
			return Pojomatic.toString(this);
		}

		public String getName() {
			if (callback == null) {
				return "checkpoint";
			} else {
				return String.valueOf(callback);
			}
		}
	}
	
    private final Queue<Event> backingQueue = new PriorityQueue<Event>(25);
	@Inject
	@Named(SimulatorConfigurationConstants.START_DATE)
	private DateTime clock = null;
	
	@Inject
	private IProfiler profiler;

	@Override
	public void schedule(final DateTime dateTime, final Priority priority, final IDateRunnable callback) {
		if (clock != null && clock.isAfter(dateTime)) {
			throw new IllegalArgumentException("An event was added before the current clock - please respect the laws of physics");
		}
		
		backingQueue.add(new Event(dateTime, priority, callback, sequence.getAndIncrement()));
	}
	
	@Override
	public boolean isEmpty() {
		return backingQueue.isEmpty();
	}

	@Override
	public List<String> despatch() throws NHMException {
		DateTime oldClock = null;
		final List<String> eventNames = new LinkedList<>();
		do {
			final Event next = backingQueue.remove();
			if (eventNames.size() < 10) {
				eventNames.add(next.getName());
			} else if (eventNames.size() == 10) {
				eventNames.add("...");
			}
			if (profiler != null) profiler.start(next.toString(), "EVENT");
			next.fire();
			if (profiler != null) profiler.stop();
			
			oldClock = clock;
			if (backingQueue.isEmpty() == false) {
				clock = backingQueue.element().dateTime;
			}
		} while (!backingQueue.isEmpty() && (clock.equals(oldClock)));
		return eventNames;
	}

    @Override
    public DateTime getTimeOfNextEvent() {
        return clock;
    }
    
    @Override
    public void addCheckpoint(final DateTime dt) {
    	if (checkpoints.contains(dt)) return;
    	checkpoints.add(dt);
    	schedule(dt, Priority.ofCheckpoints(), null);
    }
    
    @Override
    public int size() {
    	return backingQueue.size();
    }
}
