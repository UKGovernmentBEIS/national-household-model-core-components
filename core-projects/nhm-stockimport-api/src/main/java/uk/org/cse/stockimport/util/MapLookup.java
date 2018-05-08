package uk.org.cse.stockimport.util;

import java.util.Map;

/**
 * @since 1.0
 */
public class MapLookup<K, V> implements ILookup<K, V> {

    private final Map<K, V> map;

    private MapLookup(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    /**
     * @since 1.0
     */
    public static <K, V> MapLookup<K, V> of(final Map<K, V> map) {
        return new MapLookup<K, V>(map);
    }
}
