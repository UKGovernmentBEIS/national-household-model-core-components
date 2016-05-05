package uk.org.cse.nhm.simulator.action;

import java.util.Collections;
import java.util.Set;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * Converts from {} XML element to itself.
 * 
 * Implements creating a state change which demolishes houses.
 * 
 * @author glenns
 */
public class DemolishHousesAction extends AbstractNamed implements IStateAction {
	@Override
	public Set<IDwelling> apply(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) throws NHMException {
		final IBranch branch = scope.getState();
		
		for (final IDwelling dwelling : dwellings) {
			branch.destroyDwelling(dwelling);
		}
		
		return Collections.<IDwelling>emptySet();
	}
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

    
    @Override
    public Set<IDwelling> getSuitable(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) {
        return dwellings;
    }
}
