package uk.org.cse.nhm.simulator.groups.impl;

import java.util.LinkedHashSet;

import javax.inject.Inject;

import uk.org.cse.nhm.simulator.guice.SimulationScoped;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;

@SimulationScoped
public class AllHousesDwellingGroup extends BaseDwellingGroup implements IStateListener {	
	@Inject
	public AllHousesDwellingGroup(final ICanonicalState state) {
		state.addStateListener(this);
	}

	@Override
	public void stateChanged(ICanonicalState state, IStateChangeNotification notification) {
		update(notification, new LinkedHashSet<IDwelling>(notification.getCreatedDwellings()), new LinkedHashSet<IDwelling>(notification.getDestroyedDwellings()));
	}
}
