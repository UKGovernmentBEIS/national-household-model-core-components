package uk.org.cse.nhm.simulator.state;

import uk.org.cse.nhm.simulator.state.IBranch.IModifier;


/**
 * This is a wrapper for an {@link IDwelling} (or potentially multiple identical {@link IDwelling}s) 
 * inside a particular {@link IBranch}. Typically acquired through {@link IBranch#wrap(IDwelling)}
 * 
 * @author hinton
 *
 */
public interface ISettableComponents extends IComponents {
	/**
	 * @param dimension
	 * @return a safe-to-modify copy of the dimension for the captured dwelling/dwellings
	 */
	public <T> void modify(final IDimension<T> dimension, final IModifier<T> operation);
}
