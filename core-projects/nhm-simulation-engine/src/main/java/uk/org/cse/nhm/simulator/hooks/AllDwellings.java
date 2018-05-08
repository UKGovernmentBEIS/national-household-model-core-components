package uk.org.cse.nhm.simulator.hooks;

import java.util.Collections;
import java.util.Set;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;

public class AllDwellings extends AbstractNamed implements IDwellingSet {

    @Override
    public Set<IDwelling> get(final IState state, ILets lets) {
        return Collections.unmodifiableSet(state.getDwellings());
    }
}
