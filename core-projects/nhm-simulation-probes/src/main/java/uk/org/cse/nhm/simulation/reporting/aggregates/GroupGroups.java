package uk.org.cse.nhm.simulation.reporting.aggregates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.IDwellingGroupListener;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;

public class GroupGroups extends AbstractNamed implements IGroups, IDwellingGroupListener, IStateListener, ISimulationStepListener {
	private final List<IDwellingGroup> groups;
	private final List<IListener> listeners = new ArrayList<IGroups.IListener>();
	
	private final HashSet<IDwellingGroup> goodGroups = new HashSet<IDwellingGroup>();
	private final HashSet<IDwellingGroup> badGroups = new HashSet<IDwellingGroup>();
	
	private Set<String> causes = new HashSet<String>();
	
	@Inject
	public GroupGroups(
			final ICanonicalState state,
			final ISimulator simulator,
			@Assisted final List<IDwellingGroup> groups) {
		this.groups = groups;
		
		state.addStateListener(this);
		simulator.addSimulationStepListener(this);
		
		for (final IDwellingGroup g : groups) {
			g.addListener(this);
		}
		
		goodGroups.addAll(groups);
	}

	@Override
	public void addListener(IListener listener) {
		listeners.add(listener);
	}

	@Override
	public void simulationStepped(final DateTime dateOfStep, final DateTime nextDate, final boolean isFinalStep) throws NHMException {
		for (final IListener l : listeners) {
			for (final IDwellingGroup g : badGroups) {
				l.groupChanged(ImmutableMap.of("group", g.toString()), g.getContents(), causes, isFinalStep);
			}
		}
		
		badGroups.clear();
		causes = new HashSet<String>();
		goodGroups.addAll(groups);
	}

	@Override
	public void stateChanged(final ICanonicalState state,final IStateChangeNotification notification) {
		boolean anyChanges = false;
		for (final IDwellingGroup g : goodGroups) {
			if (!Collections.disjoint(notification.getAllChangedDwellings(), g.getContents())) {
				badGroups.add(g);
				anyChanges = true;
			}
		}
		
		goodGroups.removeAll(badGroups);
		
		if(anyChanges) {
			causes.add(notification.getRootScope().getTag().getIdentifier().getPath());
		}
	}

	@Override
	public void dwellingGroupChanged(IStateChangeNotification cause, IDwellingGroup source, Set<IDwelling> added, Set<IDwelling> removed) {
		badGroups.add(source);
		goodGroups.remove(source);
		causes.add(cause.getRootScope().getTag().getIdentifier().getPath());
	}
	
	@Override
	public void triggerManually() {
		goodGroups.removeAll(groups);
		badGroups.addAll(groups);
	}
}
