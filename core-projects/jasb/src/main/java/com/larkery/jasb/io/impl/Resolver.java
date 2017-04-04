package com.larkery.jasb.io.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.larkery.jasb.sexp.Atom;

/**
 * Resolves a single big flat namespace
 * @author hinton
 *
 */
class Resolver  {
	private final Map<String, SettableFuture<?>> futures = new HashMap<>();
	private final Multimap<String, Class<?>> futureClasses = HashMultimap.create();
	private final Set<String> definedNames = new HashSet<>();

    public Set<String> getDefinedNames() {
        return definedNames;
    }
    
	@SuppressWarnings("unchecked")
	public <Q> ListenableFuture<Q> resolve(final Atom cause, final String id, final Class<Q> type) {
		if (!futures.containsKey(id)) {
			futures.put(id, SettableFuture.create());
			futureClasses.put(id, type);
		}
		
		if (futures.get(id).isDone()) {
			try {
				if (!type.isInstance(futures.get(id).get())) {
					return Futures.immediateFailedFuture(
							new IllegalArgumentException("The name " + id + " does not define an element of the correct type."));				
				}
			} catch (InterruptedException | ExecutionException e) {
				return Futures.immediateFailedFuture(
						new IllegalArgumentException("The name " + id + " does not define an element of the correct type."));								
			}
		}
		
		return (ListenableFuture<Q>) futures.get(id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void define(final String result, final Object o) {
		if (futures.containsKey(result)) {
			if (futures.get(result).isDone()) {
				throw new IllegalArgumentException("The name " + result + " was used for two different entities Names must be unique.");
			} else {
				for (final Class<?> clazz : futureClasses.get(result)) {
					if (!clazz.isInstance(o)) {
						//TODO provide a better type of error here
						throw new IllegalArgumentException("The name " + result + " does not define an element of the correct type.");
					}
				}
				
				((SettableFuture) futures.get(result)).set(o);
			}
		} else {
            definedNames.add(result);
			futures.put(result, SettableFuture.create());
			futureClasses.put(result, o.getClass());
			((SettableFuture) futures.get(result)).set(o);
		}
	}

	public Map<String, Object> getDefinitions() {
		final ImmutableMap.Builder<String, Object> b = ImmutableMap.builder();
		for (final Map.Entry<String, SettableFuture<?>> e : futures.entrySet()) {
			if (e.getValue().isDone()) {
				try {
					b.put(e.getKey(), e.getValue().get());
				} catch (final Exception e1) {
				}
			}
		}
		return b.build();
	}
}
