package uk.org.cse.nhm.simulator.scope;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

abstract class TestComponentsAction implements IComponentsAction {

    @Override
    public StateChangeSourceType getSourceType() {
        return null;
    }

    @Override
    public Name getIdentifier() {
        return Name.of("test");
    }

    @Override
    public abstract boolean apply(ISettableComponentsScope scope, ILets lets) throws NHMException;

    @Override
    public boolean isSuitable(IComponentsScope scope, ILets lets) {
        return false;
    }
}
