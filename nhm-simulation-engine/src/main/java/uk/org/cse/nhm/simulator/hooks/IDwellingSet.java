package uk.org.cse.nhm.simulator.hooks;

import java.util.Set;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;

public interface IDwellingSet extends IIdentified {
	public Set<IDwelling> get(final IState state, ILets lets);
}
