package uk.org.cse.hom.housepropertystore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

public class HouseProperties implements IHouseProperties {
	private Map<String, String> properties;
	public static final String EMPTY_VALUE = "null";

	public HouseProperties(Map<String, String> properties) {
		Map<String,String> cleansedMap = new HashMap<>();
		
		if(properties == null){
			this.properties = ImmutableMap.of();
		} else {
			for (String key : properties.keySet()){
				String value = properties.get(key) == null ? EMPTY_VALUE : properties.get(key);
				cleansedMap.put(key, value);			
			}
			this.properties = ImmutableMap.copyOf(cleansedMap);
		}
	}

	@Override
	public int size() {
		return properties.size();
	}

	@Override
	public boolean isEmpty() {
		return properties.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return properties.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return properties.containsValue(value);
	}

	@Override
	public String get(Object key) {
		try {
			return properties.get(key);
		} catch (NullPointerException e) {
			throw new RuntimeException("Could not find additional house propery " + key, e);
		}
	}

	@Override
	public String put(String key, String value) {
		throw new UnsupportedOperationException("Cannot alter the static house properties.");
	}

	@Override
	public String remove(Object key) {
		throw new UnsupportedOperationException("Cannot alter the static house properties.");
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		throw new UnsupportedOperationException("Cannot alter the static house properties.");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Cannot alter the static house properties.");
	}

	@Override
	public Set<String> keySet() {
		return properties.keySet();
	}

	@Override
	public Collection<String> values() {
		return properties.values();
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return properties.entrySet();
	}

	@Override
	public IHouseProperties copy() {
		return this;
	}

}
