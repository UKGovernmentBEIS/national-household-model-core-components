package uk.org.cse.nhm.simulator.state;

import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public interface IHypotheticalBranch extends IBranch {
	/**
	 * Fully replace a dimension within this branch with another dimension; once this has been done,
	 * you may never merge the branch. It is intended for working with the {@link IHypotheticalComponentsScope}
	 * to allow access to uncalibrated energy use.
	 * 
	 * @param dimension
	 * @param replacement
	 */
	public <T> void replaceDimension(final IDimension<T> dimension, final IInternalDimension<T> replacement);
}
