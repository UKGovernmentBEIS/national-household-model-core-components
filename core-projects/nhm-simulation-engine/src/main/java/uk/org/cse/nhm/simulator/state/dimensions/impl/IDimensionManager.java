package uk.org.cse.nhm.simulator.state.dimensions.impl;

/**
 * A helper for IDimension which knows about the details of how to manage the
 * dimension's types
 *
 * @author hinton
 *
 */
public interface IDimensionManager<T> {

    public T copy(final T instance);

    public T internalise(final T instance);

    public boolean isEqual(final T first, final T second);

    public T getDefaultValue();
}
