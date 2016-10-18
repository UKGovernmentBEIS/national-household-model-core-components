package uk.org.cse.nhm.simulation.reporting.aggregates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.IDwellingGroupListener;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * A grouping mechanism which finds cross products of a list of functions which it runs.
 * 
 * Presently this is not efficient
 * 
 * @author hinton
 *
 */
public class CrossGroups extends AbstractNamed implements IGroups, IDwellingGroupListener, IStateListener, ISimulationStepListener {
	private final IDwellingGroup source;
	private final List<IComponentsFunction<?>> divisions;
	private final List<String> names;
	private final IState state;
	private final List<IListener> listeners = new ArrayList<IGroups.IListener>();
	private Set<String> causes = new HashSet<String>();
	
	/**
	 * Stores all of the combinations we have seen so far, so that if they are not present in a particular sequence
	 * we can present them as the empty set.
	 */
	private final Set<List<Object>> existing = new HashSet<List<Object>>();
	private boolean upToDate = true;
	
	@Inject
	public CrossGroups(
			final ICanonicalState state,
			final ISimulator simulator,
			@Assisted final IDwellingGroup source,
			@Assisted final List<IComponentsFunction<?>> divisions) {
		this.source = source;
		this.divisions = divisions;
		this.state = state;
		
		state.addStateListener(this);
		simulator.addSimulationStepListener(this);
		source.addListener(this);
		
		final ImmutableList.Builder<String> nb = ImmutableList.builder();
		
		for (final IComponentsFunction<?> cf : divisions) {
			nb.add(cf.toString());
		}
		
		names = nb.build();
	}

	@Override
	public void addListener(final IListener listener) {
		listeners.add(listener);
	}

	@Override
	public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
		if(!Collections.disjoint(notification.getAllChangedDwellings(), source.getContents())) {
			upToDate = false;
			causes.add(notification.getRootScope().getTag().getIdentifier().getPath());
		}
	}

	@Override
	public void dwellingGroupChanged(final IStateChangeNotification cause,final IDwellingGroup source, final Set<IDwelling> added, final Set<IDwelling> removed) {
		upToDate = false;
		causes.add(cause.getRootScope().getTag().getIdentifier().getPath());
	}

	@Override
	public void simulationStepped(final DateTime dateOfStep, final DateTime nextDate, final boolean isFinalStep) throws NHMException {
		if(!upToDate) {
			final HashMultimap<List<Object>, IDwelling> groups = HashMultimap.create();
			for (final IDwelling d : source.getContents()) {
				final List<Object> v = evaluate(d);
				
				existing.add(v);
				
				groups.put(v, d);
			}
			
			for (final List<Object> key : existing) {
				final ImmutableMap.Builder<String, String> divisions = ImmutableMap.builder();
				
				final Iterator<String> ni = names.iterator();
				final Iterator<Object> vi = key.iterator();
				while (ni.hasNext() && vi.hasNext()) {
					Object k = vi.next();
					divisions.put(ni.next(), k == null ? "n/a" : k.toString());
				}
				
				for (final IListener l : listeners) {
					l.groupChanged(divisions.build(), groups.get(key), causes, isFinalStep);
				}
			}
			causes = new HashSet<String>();
			upToDate = true;
		}
	}

	private List<Object> evaluate(final IDwelling d) {
		final List<Object> values = new ArrayList<Object>();
		
		for (final IComponentsFunction<?> division: divisions) {
			values.add(division.compute(state.detachedScope(d), ILets.EMPTY));
		}
		
		return values;
	}

	@Override
	public void triggerManually() {
		upToDate = false;
	}
}
