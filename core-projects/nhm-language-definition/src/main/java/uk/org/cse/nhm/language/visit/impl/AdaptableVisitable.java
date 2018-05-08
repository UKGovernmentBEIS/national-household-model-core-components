package uk.org.cse.nhm.language.visit.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.reflect.TypeUtils;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.language.adapt.IAdaptable;
import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.adapt.IAdapterDelegator;
import uk.org.cse.nhm.language.adapt.IAdaptingScope;
import uk.org.cse.nhm.language.visit.IPropertyVisitor;
import uk.org.cse.nhm.language.visit.IVisitable;
import uk.org.cse.nhm.language.visit.IVisitor;

/**
 * A useful base class for things which need to be adaptable and visitable.
 *
 * @author hinton
 *
 */
public abstract class AdaptableVisitable<T extends AdaptableVisitable<T>> implements IAdaptable, IAdapterDelegator, IVisitable<T> {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AdaptableVisitable.class);

    private final List<IAdapter> adapters = new ArrayList<IAdapter>();

    private final Methods methods;

    static class Methods {

        private final List<Method> childMethods;
        private final Set<Method> iterableChildren;

        public Methods(final List<Method> childMethods, final Set<Method> iterableChildren) {
            super();
            this.childMethods = ImmutableList.copyOf(childMethods);
            this.iterableChildren = ImmutableSet.copyOf(iterableChildren);
        }
    }

    private static final LoadingCache<Class<?>, Methods> methodsByClass
            = CacheBuilder.newBuilder()
                    .build(new CacheLoader<Class<?>, Methods>() {

                        @Override
                        public Methods load(final Class<?> key) throws Exception {
                            final List<Method> childMethods = new ArrayList<>();
                            final Set<Method> iterableChildren = new HashSet<>();
                            for (final Method m : key.getMethods()) {
                                if (m.getReturnType() != Void.TYPE && m.getParameterTypes().length == 0 && notStatic(m)) {
                                    final Type t = m.getGenericReturnType();

                                    if (TypeUtils.isAssignable(t, IVisitable.class)) {
                                        childMethods.add(m);
                                    } else if (TypeUtils.isAssignable(t, Iterable.class)) {
                                        if (t instanceof ParameterizedType) {
                                            final Type iterableType = ((ParameterizedType) t).getActualTypeArguments()[0];
                                            if (TypeUtils.isAssignable(iterableType, IVisitable.class)) {
                                                childMethods.add(m);
                                                iterableChildren.add(m);
                                            }
                                        }
                                    }
                                }
                            }

                            Collections.sort(childMethods,
                                    new Comparator<Method>() {
                                int o(final Method m) {
                                    if (m.isAnnotationPresent(VisitOrder.class)) {
                                        return m.getAnnotation(VisitOrder.class).value();
                                    } else {
                                        return Integer.MAX_VALUE;
                                    }
                                }

                                @Override
                                public int compare(final Method arg0, final Method arg1) {
                                    return Integer.compare(o(arg0), o(arg1));
                                }
                            });

                            return new Methods(childMethods, iterableChildren);
                        }

                    });

    protected AdaptableVisitable() {
        try {
            this.methods = methodsByClass.get(getClass());
        } catch (final ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean notStatic(final Method m) {
        return !Modifier.isStatic(m.getModifiers());
    }

    @SuppressWarnings("unchecked")
    /* Because Java has no concept of 'self' in its generics. */
    @Override
    public void accept(final IVisitor<T> visitor) {
        final IPropertyVisitor<T> pv = visitor instanceof IPropertyVisitor ? (IPropertyVisitor<T>) visitor : null;

        final T me = (T) this;

        if (visitor.enter(me)) {
            visitor.visit(me);
            for (final Method m : methods.childMethods) {
                if (pv != null) {
                    pv.enterProperty(me, m);
                }

                if (methods.iterableChildren.contains(m)) {
                    final Iterable<IVisitable<T>> vs = getIterableVisitable(m);
                    if (vs != null) {
                        for (final IVisitable<T> v : vs) {
                            v.accept(visitor);
                        }
                    }
                } else {
                    final IVisitable<T> v = getVisitable(m);
                    if (v != null) {
                        v.accept(visitor);
                    }
                }

                if (pv != null) {
                    pv.leaveProperty(me, m);
                }
            }
        }

        visitor.leave(me);
    }

    private Object invoke(final Method m) {
        try {
            return m.invoke(this);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Iterable<IVisitable<T>> getIterableVisitable(final Method m) {
        return (Iterable<IVisitable<T>>) invoke(m);
    }

    @SuppressWarnings("unchecked")
    private IVisitable<T> getVisitable(final Method m) {
        return (IVisitable<T>) invoke(m);
    }

    @Override
    public <Q> Q adapt(final Class<Q> to, final IAdaptingScope scope) {
        final Optional<Q> fromCache = scope.getFromCache(this, to);

        if (to.isInstance(this)) {
            return to.cast(this);
        }

        if (fromCache.isPresent()) {
            log.trace("reusing cached value for {} -> {}", getClass().getSimpleName(), to.getSimpleName());
            return fromCache.get();
        }

        log.trace("adapting from {} to {}", getClass().getSimpleName(), to.getSimpleName());
        for (final IAdapter a : adapters) {
            if (a.adapts(this, to)) {
                final Object o = a.adapt(this, to, scope);
                if (o != null) {
                    return to.cast(o);
                } else {
                    log.trace("adapting from {} to {}, {} failed", new Object[]{getClass().getSimpleName(), to.getSimpleName(), a});
                }
            }
        }
        log.trace("adapting {} to {}, no working adapter was found", getClass().getSimpleName(), to.getSimpleName());

        return null;
    }

    @Override
    public boolean adapts(final Class<?> to) {
        if (to.isInstance(this)) {
            return true;
        }

        log.trace("looking for adapter from {} to {}", getClass().getSimpleName(), to.getSimpleName());
        for (final IAdapter a : adapters) {
            log.trace("consider : {}", a);
            if (a.adapts(this, to)) {
                return true;
            }
        }
        log.debug("no adapter found from {} to {}", getClass().getSimpleName(), to.getSimpleName());
        return false;
    }

    @Override
    public void addAdapter(final IAdapter adapter) {
        if (adapter.adapts(this)) {
            log.trace("Added {} to {}", adapter, this);
            adapters.add(adapter);
        }
    }
}
