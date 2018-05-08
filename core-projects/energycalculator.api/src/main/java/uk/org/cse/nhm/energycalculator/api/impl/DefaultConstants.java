package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.IConstant;
import uk.org.cse.nhm.energycalculator.api.IConstants;

/**
 * An implementation of {@link IConstants} which delegates to the constants
 * themselves to get their default values.
 *
 * Access through the {@link #INSTANCE} singleton.
 *
 * @since 1.0.0
 * @author hinton
 */
public class DefaultConstants implements IConstants {

    /**
     * The default constants instance.
     *
     * @since 1.0.0
     */
    public static final IConstants INSTANCE = new DefaultConstants();

    private DefaultConstants() {

    }

    @Override
    public <T, Q extends Enum<Q> & IConstant> T get(Q key, Class<T> clazz) {
        return key.getValue(clazz);
    }

    @Override
    public <Q extends Enum<Q> & IConstant> double get(Q key) {
        return get(key, Double.class);
    }

    @Override
    public <Q extends Enum<Q> & IConstant> double get(Q key, int index) {
        return get(key, double[].class)[index];
    }

    @Override
    public <Q extends Enum<Q> & IConstant> double get(Q key, Enum<?> index) {
        return get(key, index.ordinal());
    }
}
