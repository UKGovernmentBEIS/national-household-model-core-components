package uk.org.cse.nhm.simulation.reporting.state;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry.Type;
import uk.org.cse.nhm.logging.logentry.StateChangeLogEntry;
import uk.org.cse.nhm.logging.logentry.StateChangeLogEntry.EntryType;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;

/**
 * A gadget which listens to the state and logs the updated state of dwellings in response to state change notifications.
 * 
 * @author hinton
 *
 */
public class StateChangeLogger {
	private static final Logger log = LoggerFactory.getLogger(StateChangeLogger.class);
	private final ILogEntryHandler loggingService;
	
	/**
	 * These are used to convert those parts of the dwelling which are interesting into things which can be written to mongo.
	 */
	private final Set<IComponentFlattener> flatteners = new HashSet<IComponentFlattener>();
	
	/**
	 * These are the components our flatteners care about
	 */
	private final Set<IDimension<?>> componentsOfInterest = new HashSet<IDimension<?>>();
	
	private final Multimap<IDimension<?>, IComponentFlattener> flattenersByComponent = 
			ArrayListMultimap.create();
	
	/**
	 * All the dwellings which have been created in the current instant
	 */
	private final HashSet<IDwelling> newDwellings = new HashSet<IDwelling>();
	/**
	 * All the changes which are due to be saved from the current instant.
	 */
	private final HashMap<IDwelling, Set<IDimension<?>>> pendingChanges = new HashMap<IDwelling, Set<IDimension<?>>>();
	private final ICanonicalState state;
	
	@Inject
	public StateChangeLogger(
			final ICanonicalState state, 
			final ISimulator simulator,
			final ILogEntryHandler loggingService,
			final Set<IComponentFlattener> flatteners) {
		super();
		this.loggingService = loggingService;
		
		loggingService.acceptLogEntry(new ReportHeaderLogEntry(Type.State));
		
		for (final IComponentFlattener f : flatteners) {
			this.flatteners.add(f);
			for (final IDimension<?> dc : f.getComponents()) {
				componentsOfInterest.add(dc);
				flattenersByComponent.put(dc, f);
			}
		}
		
		log.debug("StateChangeLogger(flatteners = {})", flatteners);
		
		// we add a listener to the state because we want to know when things change
		state.addStateListener(new IStateListener() {			
			@Override
			public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
				handle(notification);
			}
		});
		
		// we add a listener to the simulator to find out when a slice of time has been finished (because several events can happen
		// contemporaneously, and if measure 1 makes a change and then immediately after measure 2 clobbers it we don't need to record
		// the in-between state)
		simulator.addSimulationStepListener(new ISimulationStepListener() {
			@Override
			public void simulationStepped(final DateTime dateOfStep, final DateTime nextDate, final boolean isFinalStep) throws NHMException {
				logPending(dateOfStep);
			}
		});
		
		this.state = state;
	}

	/**
	 * Log all the things that happened in the last simulator tick
	 * 
	 * @param when
	 */
	protected void logPending(final DateTime when) {
		log.debug("Logging creation of {} dwellings and modification of {} dwellings", newDwellings.size(), pendingChanges.size());
		
		int toLog = newDwellings.size();
		int logged = 0;
		int percentage = 0;
		
		for (final IDwelling d : newDwellings) {
			final ImmutableList.Builder<Object> objects = ImmutableList.builder();
			for (final IComponentFlattener flattener : flatteners) {
				final Object flatten = flattener.flatten(state, d);
				if (flatten != null) objects.add(flatten);
			}
			
			
			logged++;
			final int newpercentage = 10 * logged / toLog;
			if (newpercentage != percentage) {
				log.debug("Logged {}% of creations", newpercentage * 10);
				percentage = newpercentage;
			}
			
			log(new StateChangeLogEntry(
					d.getID(), 
					d.getWeight(),
					EntryType.CREATION, when,
					objects.build()));
		}
		percentage = 0;
		toLog = pendingChanges.size();
		logged = 0;
		for (final Map.Entry<IDwelling, Set<IDimension<?>>> change : pendingChanges.entrySet()) {
			final ImmutableList.Builder<Object> objects = ImmutableList.builder();
			
			boolean allChangesWereNull = true;
			for (final IDimension<?> dc : change.getValue()) {
				for (final IComponentFlattener f : flattenersByComponent.get(dc)) {
					final Object flatten = f.flatten(state, change.getKey());
					if (flatten != null) {
						objects.add(flatten);
						allChangesWereNull = false;
					}
				}
			}
			
			if (allChangesWereNull == false) log(
					new StateChangeLogEntry(
							change.getKey().getID(), 
							change.getKey().getWeight(),
							EntryType.MODIFICATION, when,
							objects.build())
					);
			
			logged++;
			final int newpercentage = 10 * logged / toLog;
			if (newpercentage != percentage) {
				log.debug("Logged {}% of modifications", newpercentage * 10);
				percentage = newpercentage;
			}
		}
		
		newDwellings.clear();
		pendingChanges.clear();
	}

	/**
	 * Collect up changes from the state, for emission in the {@link #logPending(DateTime)} method
	 * @param notification
	 */
	protected void handle(final IStateChangeNotification notification) {
		for (final IDwelling destroyed : notification.getDestroyedDwellings()) {
			// since destruction is the end of life for a dwelling, we can log that immediately
			log(new StateChangeLogEntry(
					destroyed.getID(), 
					destroyed.getWeight(),
					EntryType.DESTRUCTION, notification.getDate(),
					Collections.emptySet()));
		}
		
		// creations and modifications get batched up to the end of the tick
		for (final IDwelling created : notification.getCreatedDwellings()) {
			newDwellings.add(created);
		}
		
		for (final IDimension<?> component : componentsOfInterest) {
			final Set<IDwelling> changedDwellings = notification.getChangedDwellings(component);
			log.trace("Flagging {} dwellings as modified on {} by {}", new Object[] {changedDwellings.size(), component, notification.getRootScope().getTag().getIdentifier()});
			for (final IDwelling modified : changedDwellings) {
				if (newDwellings.contains(modified)) continue;
				Set<IDimension<?>> a = pendingChanges.get(modified);
				if (a == null) {
					a = new HashSet<IDimension<?>>();
					pendingChanges.put(modified, a);
				}
				a.add(component);
			}
		}
	}
	
	/**
	 * Convenience to log a log entry into the repository
	 * @param entry
	 */
	private void log(final StateChangeLogEntry entry) {
		if (entry.isEmpty()) {
			if (entry.getType() == EntryType.CREATION) {
				log.warn("House created with no associated component values");
			} else {
//				log.warn("Skipping state change entry with no values");
				return;
			}
		}
		loggingService.acceptLogEntry(entry);
	}
}
