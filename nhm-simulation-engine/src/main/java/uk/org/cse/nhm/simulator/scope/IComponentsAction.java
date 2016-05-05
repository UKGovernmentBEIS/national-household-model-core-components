package uk.org.cse.nhm.simulator.scope;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;

/**
 * Operate on a house.
 * 
 * @since 3.7.0
 */
public interface IComponentsAction extends IStateChangeSource {
	boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException;
	boolean isSuitable(final IComponentsScope scope, final ILets lets);
	boolean isAlwaysSuitable();
}
