package uk.org.cse.nhm.energycalculator.api.impl;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

/**
 * Wraps a map from key type K to arrays of doubles of a certain dimension
 * (dimension set by constructor {@link #DoubleMap(int)})
 *
 * @author hinton
 *
 * @param <K>
 */
class DoubleMap<E extends Enum<E>> {

    private final double[][] indices;

    final int dimension;

    private final Class<E> ec;

    public DoubleMap(final Class<E> ec, final int dimension) {
        this.ec = ec;
        this.dimension = dimension;
        this.indices = new double[ec.getEnumConstants().length][];
    }

    public Set<E> keySet() {
        return EnumSet.allOf(ec);
    }

    /**
     * Get the difference between two dimensions for the given key.
     *
     * @param key
     * @param i1
     * @param i2
     * @return
     */
    public double getDifference(final E key, final int i1, final int i2) {
        final double[] arr = indices[key.ordinal()];
        if (arr == null) {
            return 0;
        } else {
            return arr[i1] - arr[i2];
        }
    }

    /**
     * Get the value for the given key at the given index
     *
     * @param key
     * @param index
     * @return
     */
    public double get(final E key, final int index) {
        final double[] arr = indices[key.ordinal()];
        if (arr == null) {
            return 0;
        } else {
            return arr[index];
        }
    }

    /**
     * Increase the given dimension for the given key by the specified amount
     *
     * @param key
     * @param index
     * @param amount
     */
    public void increment(final E key, final int index, final double amount) {
        if (Double.isInfinite(amount) || Double.isNaN(amount)) {
            throw new RuntimeException("Bad value: " + amount);
        }
        double[] arr = indices[key.ordinal()];

        if (arr == null) {
            arr = new double[dimension];
            indices[key.ordinal()] = arr;
        }

        arr[index] += amount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        for (final E e : ec.getEnumConstants()) {
            if (indices[e.ordinal()] == null) {
                continue;
            }
            sb.append(e);
            sb.append(": ");
            sb.append(Arrays.toString(indices[e.ordinal()]));
            sb.append("\n");
        }
        return sb.toString();
    }
}
