package com.larkery.jasb.io.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.larkery.jasb.bind.AfterReading;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.io.IAtomReader;
import com.larkery.jasb.io.IReadContext;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.errors.UnexpectedTermError;

class MultiAtomReader<T> {

    private final Class<T> clazz;
    private final ImmutableSet<IAtomReader> delegates;
    private final Set<String> legalValues;
    private final Set<String> allLegalValues;
    private final ImmutableMap<String, Class<?>> fallbacks;

    public MultiAtomReader(final Class<T> clazz, final ImmutableSet<IAtomReader> build, final ImmutableSet<Class<?>> fallbacks) {
        this.clazz = clazz;
        this.delegates = build;

        final ImmutableMap.Builder<String, Class<?>> fallbackBuilder = ImmutableMap.builder();

        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final IAtomReader delegate : delegates) {
            builder.addAll(delegate.getLegalValues(clazz));
        }
        legalValues = builder.build();

        for (final Class<?> c : fallbacks) {
            if (c.isAnnotationPresent(Bind.class)) {
                fallbackBuilder.put(c.getAnnotation(Bind.class).value(), c);
            }
        }

        this.fallbacks = fallbackBuilder.build();

        this.allLegalValues = Sets.union(legalValues,
                this.fallbacks.keySet());
    }

    protected ListenableFuture<T> read(final IReadContext context, final Atom atom) {
        for (final IAtomReader reader : delegates) {
            final Optional<T> value = reader.read(atom.getValue(), clazz);
            if (value.isPresent()) {
                return Futures.immediateFuture(value.get());
            }
        }

        if (fallbacks.containsKey(atom.getValue())) {
            try {
                final T fallbackValue = clazz.cast(fallbacks.get(atom.getValue()).getConstructor().newInstance());
                for (final Method m : clazz.getMethods()) {
                    if (m.isAnnotationPresent(AfterReading.class)) {
                        if (m.getParameterTypes().length == 1 && m.getParameterTypes()[0].isAssignableFrom(Atom.class)) {
                            m.invoke(fallbackValue, atom);
                        }
                    }
                }
                return Futures.immediateFuture(fallbackValue);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            }
        }

        if (!atom.getValue().endsWith(":")) {
            return context.getCrossReference(clazz, atom, atom.getValue(),
                    allLegalValues);
        } else {
            context.handle(new UnexpectedTermError(atom,
                    atom.getValue().endsWith(":") ? "keyword" : "word",
                    allLegalValues,
                    atom.getValue()));

            return Futures.immediateFailedFuture(new RuntimeException("Could not read " + atom + " as " + clazz));
        }

    }

}
