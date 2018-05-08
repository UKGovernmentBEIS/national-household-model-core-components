package uk.org.cse.stockimport.util;

import java.util.Map;

/**
 * @since 1.0
 */
public class DefaultLookup<K, V> implements ILookup<K, V> {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DefaultLookup.class);

    private final ILookup<K, V> delegate;
    private final V defaultValue;

    private DefaultLookup(ILookup<K, V> delegate, V defaultValue) {
        this.delegate = delegate;
        this.defaultValue = defaultValue;
    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            log.warn("Using default value {} when looking up {}", defaultValue, key);
            return defaultValue;
        } else {
            return delegate.get(key);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return delegate.containsKey(key);
    }

    /**
     * @since 1.0
     */
    public static <K, V> DefaultLookup<K, V> of(final ILookup<K, V> lookup, final V defaultValue) {
        return new DefaultLookup<K, V>(lookup, defaultValue);
    }

    /**
     * @since 1.0
     */
    public static <K, V> DefaultLookup<K, V> of(final Map<K, V> map, final V defaultValue) {
        return new DefaultLookup<K, V>(MapLookup.of(map), defaultValue);
    }
}
