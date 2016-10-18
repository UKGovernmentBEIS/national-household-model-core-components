package uk.org.cse.nhm.utility;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;

/* An immutable map for strings which allows duplicate column names to be put in, but automatically deduplicates them. */
public class DeduplicatingMap {
	public interface Deduplicator<K> {
		public String add(final K key, final String name);
		public String get(final K key);
	}
	
	public static class StandardDeduplicator<K> implements Deduplicator<K> {
		private final Multiset<String> usedNames = HashMultiset.create();
		private final Map<K, String> names = Maps.newHashMap();
		
		@Override
		public String add(K key, String name) {
			final String name2 = deduplicate(name);
			usedNames.add(name);
			if (!name2.equals(name)) usedNames.add(name2);
			names.put(key, name2);
			return name2;
		}

		private String deduplicate(String name) {
			while (true) {
				if (usedNames.contains(name)) {
					name = name + "_" + usedNames.count(name);
				}
				if (!usedNames.contains(name)) {
					return name;
				}
			}
		}

		@Override
		public String get(K key) {
			return names.get(key);
		}
	}
	
	public static <V> Builder<V> stringBuilder () {
		return new Builder<V>();
	}
	
	public static class Builder<V> {
		private final ImmutableMap.Builder<String, V> builder = ImmutableMap.builder();
		private final Deduplicator<String> names = new StandardDeduplicator<>();
		
		public Builder<V> put(final String key, final V value) {
			builder.put(names.add(key, key), value);			
			return this;
		}
		
		public Builder<V> putAll(final Map<String, ? extends V> map) {
			for (final Entry<String, ? extends V> e : map.entrySet()) {
				put(e.getKey(), e.getValue());
			}
			return this;
		}
		
		public ImmutableMap<String, V> build() {
			return builder.build();
		}
	}
}
