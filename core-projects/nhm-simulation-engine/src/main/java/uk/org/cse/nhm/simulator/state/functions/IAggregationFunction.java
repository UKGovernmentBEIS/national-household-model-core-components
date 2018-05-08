package uk.org.cse.nhm.simulator.state.functions;

import java.util.Set;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;

/**
 * A function which takes a bunch of dwellings in a state, and makes a number
 * out of them.
 *
 * It may be beneficial to incorporate some change driven behaviour if this is a
 * bit slow.
 *
 * @author hinton
 *
 */
public interface IAggregationFunction extends IIdentified {

    public double evaluate(final IState state, final ILets lets, final Set<IDwelling> dwellings);

    public double evaluate(final IStateScope scope, final ILets lets, final Set<IDwelling> dwellings);
}
