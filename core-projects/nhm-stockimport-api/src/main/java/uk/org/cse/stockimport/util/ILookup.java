package uk.org.cse.stockimport.util;

/**
 * Like a map, but where you can't put things into it, and with a correct type
 * parameter
 *
 * @author hinton
 *
 * @param <K>
 * @param <V>
 * @since 1.0
 */
public interface ILookup<K, V> {

    /**
     * @since 1.0
     */
    public V get(final K key);

    /**
     * @since 1.0
     */
    public boolean containsKey(final K key);
}
