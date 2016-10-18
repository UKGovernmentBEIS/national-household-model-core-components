package uk.org.cse.nhm.language.adapt.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.org.cse.nhm.language.adapt.IAdaptingScope;
import uk.org.cse.nhm.language.adapt.IConverter;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class AdaptingScope implements IAdaptingScope {
	private final Map<String, Object> scopeVariables = new HashMap<String, Object>();
	private final Map<String, Object> globalVariables;
	private final IAdaptingScope parent;
	private final ListMultimap<Object, Object> cache;
	private final Set<IConverter> converters;

	private AdaptingScope(final AdaptingScope scope) {
		parent = scope;
		cache = scope.cache;
		converters = scope.converters;
		globalVariables = scope.globalVariables;
	}

	public AdaptingScope(final Set<IConverter> converters) {
		this.converters = converters;
		parent = null;
		cache = ArrayListMultimap.create();
		globalVariables = new HashMap<String, Object>();
	}

	@Override
	public Object getLocal(final String scopeKey) {
		if (scopeVariables.containsKey(scopeKey)) {			
			return scopeVariables.get(scopeKey);
		} else {
			return parent == null ? getGlobal(scopeKey) : parent.getLocal(scopeKey);
		}
	}
	
	@Override
	public void putLocal(final String scopeKey, final Object object) {
		scopeVariables.put(scopeKey, object);
	}
	
	@Override
	public List<Object> getFromCache(final Object from) {
		return cache.get(from);
	}
	
	@Override
	public void putInCache(final Object from, final Object result) {
		cache.put(from, result);
	}
	
	@Override
	public IAdaptingScope createChildScope() {
		return new AdaptingScope(this);
	}
	
	@Override
	public <T> Optional<T> getFromCache(final Object from, final Class<T> as) {
		for (final Object cached : getFromCache(from)) {
			if (as.isInstance(cached)) {
				return Optional.of(as.cast(cached));
			} else {
				// try converting it
				for (final IConverter converter : converters) {
					if (converter.adapts(cached, as)) {
						final Object converted = converter.adapt(from, as, this);
						if (converted != null) return Optional.of(as.cast(converted));
					}
				}
			}
		}
		return Optional.absent();
	}

	@Override
	public void putGlobal(final String scopeKey, final Object from) {
		globalVariables.put(scopeKey, from);
	}

	@Override
	public Object getGlobal(final String scopeKey) {
		return globalVariables.get(scopeKey);
	}
}
