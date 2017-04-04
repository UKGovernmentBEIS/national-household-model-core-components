package uk.org.cse.nhm.simulator.hooks;

import java.util.Collections;
import java.util.Set;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;

public class AffectedDwellings extends AbstractNamed implements IDwellingSet {
	public static final Object AFFECTED_DWELLINGS_LET_KEY = new Object();
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<IDwelling> get(final IState state, final ILets lets) {
		return lets.get(AFFECTED_DWELLINGS_LET_KEY, Set.class).or(Collections.emptySet());
	}
}
