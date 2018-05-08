package uk.org.cse.nhm.simulator.state.dimensions.impl;

import javax.inject.Inject;
import javax.inject.Provider;

import uk.org.cse.nhm.hom.ICopyable;

/**
 * A unique dimension manager for things which are {@link ICopyable}
 *
 * @author hinton
 *
 * @param <T>
 */
public class UniqueCopyableDimensionManager<T extends ICopyable<T>> extends UniqueDimensionManager<T> {

    private Provider<T> defaultValue;

    @Inject
    public UniqueCopyableDimensionManager(final Provider<T> defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public T copy(T instance) {
        return instance.copy();
    }

    @Override
    public T getDefaultValue() {
        return defaultValue.get();
    }
}
