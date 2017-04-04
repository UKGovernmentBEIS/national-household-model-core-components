package uk.org.cse.nhm.simulator.scope;

import java.util.Set;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;

/**
 * An action which alters multiple dwellings in a state. 
 * @since 3.7.0
 */
public interface IStateAction extends IStateChangeSource {
	Set<IDwelling> apply(IStateScope scope, Set<IDwelling> dwellings, ILets lets) throws NHMException;
    Set<IDwelling> getSuitable(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets);
}
