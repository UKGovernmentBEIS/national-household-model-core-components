package uk.org.cse.nhm.simulator.state.dimensions.impl;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

/**
 * A dimension manager for {@link CanonicalDimension} instances which is
 * settable, has no default value, and uses an internal deduplication mechanism
 * to ensure that redundant instances of objects are not kept around.
 *
 * @author hinton
 *
 * @param <T>
 */
public abstract class UniqueDimensionManager<T> implements IDimensionManager<T> {

    /**
     * A map used for deduplication - this maps objects to themselves, it is an
     * easy way to find something we already have which equals what we are
     * looking for. See {@link #internalise(Object)}
     *
     * Weak values doesn't work, because the keys are the values and the keys
     * are not weak.
     */
    private final Interner<T> interner = Interners.newWeakInterner();

    @Override
    public T internalise(final T instance) {
        return interner.intern(instance);
    }

    @Override
    public boolean isEqual(final T first, final T second) {
//		return first == second;

        return first == second
                || (first != null && first.equals(second));
    }
}
