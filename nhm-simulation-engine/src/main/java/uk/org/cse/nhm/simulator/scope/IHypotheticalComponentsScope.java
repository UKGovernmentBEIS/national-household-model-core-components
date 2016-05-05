package uk.org.cse.nhm.simulator.scope;

import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IHypotheticalBranch;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

/**
 * A special kind of components scope for imagining things in; designed for use by
 * NPV and other hallucinations
 *
 * @since 3.8.0
 */
public interface IHypotheticalComponentsScope extends ISettableComponentsScope {
	/**
	 * Pretend that the value of the given dimension is the given value; this
	 * does <i>not</i> cause any consistent change in other derived dimensions;
	 * if you want that behaviour, modify the dimension's value instead (via
	 * {@link #modify(IDimension, uk.org.cse.nhm.simulator.state.IBranch.IModifier)}
	 * )
	 * 
	 * @param dimension
	 * @param value
	 */
	public <T> void imagine(final IDimension<T> dimension, final T value);
	
	/**
	 * Directly replace one dimension with another. This will create a shim internally
	 * which ensures that the generation numbers for things do not end up decreasing,
	 * so you don't have to worry about that.
	 * 
	 * @param dimension
	 * @param internal
	 */
	public <T> void replace(final IDimension<T> dimension, final IInternalDimension<T> internal);
	
	/**
	 * @return the branch backing this dimension
	 */
	public IHypotheticalBranch getHypotheticalBranch();
}
