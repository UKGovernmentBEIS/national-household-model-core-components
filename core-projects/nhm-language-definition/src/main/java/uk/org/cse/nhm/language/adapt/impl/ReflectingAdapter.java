package uk.org.cse.nhm.language.adapt.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.reflect.TypeUtils;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.commons.names.ISettableIdentified;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.language.adapt.IAdaptable;
import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IAdaptingScope;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.definition.XElement;

/**
 * An {@link IAdapter} which works by reflecting on its subclasses' methods; any
 * method annotated with {@link Adapt} will be used to determine a type that
 * this can adapt.
 * <p>
 * The main work is done by {@link AdapterMethod}, which wraps one of the
 * subclass' methods, picking out the various annotations on them to work out
 * what to do.
 *
 *
 * @author hinton
 *
 */
public class ReflectingAdapter implements IAdapter {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReflectingAdapter.class);

    private final Multimap<Class<?>, AdapterMethod> exactAdaptables
            = HashMultimap.create();
    private final Multimap<Class<?>, AdapterMethod> inheritableAdaptables
            = HashMultimap.create();

    private final Set<IConverter> delegates;

    private final Set<IAdapterInterceptor> interceptors;

    private final Object target;

    private static final Object LEGAL_NULL = new Object();

    @Inject
    public ReflectingAdapter(final Set<IConverter> delegates,
            final Set<IAdapterInterceptor> interceptors,
            @Named("CLASS") final Class<?> targetClass,
            @Named("TARGET") final Object target) {
        this.target = target == null ? this : target;
        this.delegates = delegates;
        this.interceptors = interceptors;

        for (final Method m : (targetClass == null ? getClass() : targetClass).getMethods()) {
            if (m.getAnnotation(Adapt.class) != null) {
                log.trace("adapter method : {}", m);
                addAdapterMethod(m);
            }
        }

        log.trace("{} adapts {} & {}", this, exactAdaptables);
    }

    protected ReflectingAdapter(final Set<IConverter> delegates, final Set<IAdapterInterceptor> interceptors) {
        this(delegates, interceptors, null, null);
    }

    /**
     * This wraps a single {@link Method} on its containing class; in the
     * constructor, it looks at the method's arguments and annotations to work
     * out the following:
     * <ol>
     * <li> what kind of thing does the method adapt </li>
     * <li> is the adaptation inheritable, or just for the one type </li>
     * <li> what properties of the thing being adapted does the method consume
     * </li>
     * <li> for those properties, do they themselves need any adapting (see
     * {@link ParameterWrapper} and subtypes for this) </li>
     * </ol>
     *
     * The {@link #adapt(Object, IAdaptingScope)} is used to actually do the
     * adapting.
     *
     * This processes each {@link ParameterWrapper} in turn, and then invokes
     * the method when it's finished.
     * <p>
     * For more about the behaviours available, see the annotations used:
     * <ul>
     * <li> {@link Adapt} </li>
     * <li> {@link Prop} </li>
     * <li> {@link FromScope} </li>
     * <li> {@link PutScope} </li>
     * </ul>
     *
     * @author hinton
     *
     */
    private class AdapterMethod {

        /**
         * Handles parameters of adapt methods which are annotated with
         * {@link FromScope}
         *
         * @author hinton
         *
         */
        private class ScopeParameter extends ParameterWrapper {

            private final String scopeKey;

            public ScopeParameter(final int index, final String scopeKey) {
                super(index);
                this.scopeKey = scopeKey;
            }

            @Override
            public Object get(final Object o, final IAdaptingScope scope) {
                return scope.getLocal(scopeKey);
            }

            @Override
            public String toString() {
                return super.toString() + " (from scope " + scopeKey + ")";
            }
        }

        /**
         * Makes a name
         *
         * @author hinton
         */
        private class NameParameter extends ParameterWrapper {

            public NameParameter(final int index) {
                super(index);
            }

            @Override
            public Object get(final Object beingAdapted, final IAdaptingScope scope) {
                if (beingAdapted instanceof IIdentified) {
                    return ((IIdentified) beingAdapted).getIdentifier();
                } else {
                    return Name.of("unknown");
                }
            }

            @Override
            public String toString() {
                return super.toString() + " (name)";
            }
        }

        /**
         * Handles parameters of adapt methods which are adaptable
         *
         * @author hinton
         *
         */
        private class AdaptParameterWrapper extends MethodParameterWrapper {

            private final Class<?> methodRequiredType;

            public AdaptParameterWrapper(final int index, final Method method, final Class<?> methodRequiredType) {
                super(index, method);
                this.methodRequiredType = methodRequiredType;
            }

            @Override
            public Object get(final Object from, final IAdaptingScope scope) {
                final Object value = super.get(from, scope);
                if (value == LEGAL_NULL) {
                    return value;
                }

                final IAdaptable a = (IAdaptable) value;
                if (a.adapts(methodRequiredType)) {
                    return a.adapt(methodRequiredType, scope);
                } else {
                    return null;
                }
            }

            @Override
            public String toString() {
                return super.toString() + " (adapt to " + methodRequiredType.getSimpleName() + ")";
            }
        }

        /**
         * Handles parameters which are lists of adaptable things
         *
         * @author hinton
         *
         */
        private class ListAdaptParameterWrapper extends MethodParameterWrapper {

            private final Class<?> methodRequiredType;

            public ListAdaptParameterWrapper(final int i, final Method getter, final Class<?> methodRequiredType) {
                super(i, getter);
                this.methodRequiredType = methodRequiredType;
            }

            @Override
            public Object get(final Object beingAdapted, final IAdaptingScope scope) {
                final List<?> values = (List<?>) super.get(beingAdapted, scope);

                if (values == LEGAL_NULL) {
                    return LEGAL_NULL;
                }

                final List<Object> result = new ArrayList<Object>();

                for (final Object o : values) {
                    if (methodRequiredType.isInstance(o)) {
                        result.add(o);
                    } else if (o instanceof IAdaptable) {
                        final Object a = ((IAdaptable) o).adapt(methodRequiredType, scope);
                        if (a == null) {
                            log.warn("{} failed to adapt to {}", o, methodRequiredType);
                            return null;
                        }
                        result.add(a);
                    } else {
                        log.warn("{} is not adaptable to {}", o, methodRequiredType);
                        return null;
                    }
                }
                return result;
            }

            @Override
            public String toString() {
                return super.toString() + " (adapt to List<" + methodRequiredType.getSimpleName() + ">)";
            }
        }

        /**
         * Handles parameters of adapt methods which need no conversion (e.g.
         * primitive type -> primitive type)
         *
         * @author hinton
         *
         */
        private class MethodParameterWrapper extends ParameterWrapper {

            private final Method method;

            public MethodParameterWrapper(final int index, final Method method) {
                super(index);
                this.method = method;
            }

            @Override
            public Object get(final Object beingAdapted, final IAdaptingScope scope) {
                try {
                    final Object value = method.invoke(beingAdapted);
                    if (value == null) {
                        return LEGAL_NULL;
                    } else {
                        return value;
                    }
                } catch (final Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String toString() {
                return super.toString() + " (getter " + method.getName() + ")";
            }
        }

        /**
         * Handles parameters which expect just the object which is being
         * adapted
         *
         * @author hinton
         *
         */
        private class ValueParameterWrapper extends ParameterWrapper {

            protected ValueParameterWrapper(final int index) {
                super(index);
            }

            @Override
            public Object get(final Object beingAdapted, final IAdaptingScope scope) {
                return beingAdapted;
            }

            @Override
            public String toString() {
                return super.toString() + " (value itself)";
            }
        }

        /**
         * Handles parameters which are taking the {@link IAdaptingScope}
         *
         * @author hinton
         *
         */
        private class ScopeItselfParameterWrapper extends ParameterWrapper {

            protected ScopeItselfParameterWrapper(final int index) {
                super(index);
            }

            @Override
            public Object get(final Object beingAdapted, final IAdaptingScope scope) {
                return scope;
            }

            @Override
            public String toString() {
                return super.toString() + " (scope itself)";
            }
        }

        /**
         * Wraps another parameter wrapper so that if it gives a null or
         * LEGAL_NULL, the value is Optional.absent(), and otherwise it is
         * Optional.of(value);
         *
         * @author hinton
         *
         */
        private class OptionalParameterWrapper extends ParameterWrapper {

            private final ParameterWrapper delegate;

            public OptionalParameterWrapper(final ParameterWrapper delegate) {
                super(delegate.getIndex());
                this.delegate = delegate;
            }

            @Override
            public Object get(final Object beingAdapted, final IAdaptingScope scope) {
                final Object value = delegate.get(beingAdapted, scope);
                if (value == null || value == LEGAL_NULL) {
                    return Optional.absent();
                } else {
                    return Optional.of(value);
                }
            }
        }

        /**
         * Base class for the various parameter wrappers above; each parameter
         * wrapper is used by the containing {@link AdapterMethod} to work out
         * the value of an argument to its method.
         *
         * @author hinton
         *
         */
        private abstract class ParameterWrapper {

            private final int index;

            protected ParameterWrapper(final int index) {
                this.index = index;
            }

            public int getIndex() {
                return index;
            }

            public abstract Object get(final Object beingAdapted, final IAdaptingScope scope);

            @Override
            public String toString() {
                return String.format("%d", index);
            }
        }

        /**
         * The method annotated with {@link Adapt} that this instance is
         * managing
         */
        private final Method method;
        /**
         * The type of thing that this method adapts (parameter to the
         * {@link Adapt} annotation)
         */
        private final Class<?> adaptedType;
        /**
         * The return type of the method
         */
        private final Class<?> outputType;
        /**
         * {@link ParameterWrapper}s for each argument to {@link #method}; these
         * are used to get / adapt the arguments before execution
         */
        private final List<ParameterWrapper> parameters = new ArrayList<ParameterWrapper>();
        /**
         * A list of keys, indexed by parameter index; if putScope[i] is not
         * null, the value of the ith parameter is placed in the adapting scope
         * under key putScope[i]
         * <em>before</em> the method is invoked.
         */
        private final String[] putScope;
        /**
         * If the method itself is annotated with {@link PutScope}, this is not
         * null, and the value being adapted is put into the scope.
         *
         * This would be the same as having a parameter for the type being
         * adapted, and marking it with {@link PutScope}
         */
        private final String outerPutScope;
        /**
         * Number of parameters
         */
        private final int parameterCount;
        /**
         * Whether this adapter method can adapt subtypes.
         */
        private final boolean inheritable;

        /**
         * If true, store the result in the global cache against the adapted
         * element. This is taken off the {@link Adapt} annotation.
         */
        private final boolean cache;

        protected boolean shouldBeCached() {
            return cache;
        }

        public Class<?> getOutputType() {
            return outputType;
        }

        public String toString() {
            return "AdapterMethod{" + method + "}";
        }

        public AdapterMethod(final Method method) {
            super();
            try {
                this.method = method;

                {
                    final Adapt a = method.getAnnotation(Adapt.class);
                    cache = a.cache();
                    adaptedType = a.value();
                    inheritable = a.inheritable();
                }

                outputType = method.getReturnType();

                final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                final Class<?>[] parameterTypes = method.getParameterTypes();
                final Type[] genericParameterTypes = method.getGenericParameterTypes();

                final int count = parameterTypes.length;
                putScope = new String[count];
                parameterCount = count;

                if (method.getAnnotation(PutScope.class) != null) {
                    outerPutScope = method.getAnnotation(PutScope.class).value();
                } else {
                    outerPutScope = null;
                }

                for (int i = 0; i < count; i++) {
                    final Prop p = getAnnotation(Prop.class, parameterAnnotations[i]);
                    final FromScope fs = getAnnotation(FromScope.class, parameterAnnotations[i]);
                    final PutScope ps = getAnnotation(PutScope.class, parameterAnnotations[i]);

                    if (ps != null) {
                        putScope[i] = ps.value();
                    }

                    if (p != null) {
                        if (fs != null) {
                            throw new RuntimeException(String.format("parameter %d (type %s) has both %s and %s, which cannot occur together",
                                    i, genericParameterTypes[i], fs, p));
                        }
                        final Method getter = getGetter(adaptedType, p.value());
                        // check that there is a property of the right name on the adapted type.
                        if (getter == null) {
                            throw new RuntimeException(
                                    String.format("parameter %d (type %s) requests property %s, but %s has no such property",
                                            i, genericParameterTypes[i], p,
                                            adaptedType.getSimpleName()));
                        }

                        // check that the property is either adaptable, or that it is convertible
                        final Class<?> getterType = getter.getReturnType();
                        final Type genericGetterType = getter.getGenericReturnType();

                        final Class<?> methodRequiredType;
                        final Type genericMethodRequiredType;
                        final boolean addOptionalWrapper;

                        if (Optional.class.isAssignableFrom(parameterTypes[i])) {
                            // given an optional parameter, we do a special thing
                            final Type optionalType = genericParameterTypes[i];
                            if (optionalType instanceof ParameterizedType) {
                                final Type innerOptionalType = ((ParameterizedType) optionalType).getActualTypeArguments()[0];
                                if (innerOptionalType instanceof ParameterizedType) {
                                    genericMethodRequiredType = innerOptionalType;
                                    methodRequiredType = (Class<?>) ((ParameterizedType) innerOptionalType).getRawType();
                                } else if (innerOptionalType instanceof Class<?>) {
                                    genericMethodRequiredType = innerOptionalType;
                                    methodRequiredType = (Class<?>) innerOptionalType;
                                } else {
                                    throw new RuntimeException(
                                            String.format("parameter %s has optional type %s, which has an unbound type in it. Only fully specified types are supported.",
                                                    i, optionalType));
                                }
                            } else {
                                throw new RuntimeException(
                                        String.format("parameter %s has optional type %s, which has no type argument. A type argument is required.",
                                                i, optionalType));
                            }
                            addOptionalWrapper = true;
                        } else {
                            methodRequiredType = parameterTypes[i];
                            genericMethodRequiredType = genericParameterTypes[i];
                            addOptionalWrapper = false;
                        }

                        if (IAdaptable.class.isAssignableFrom(getterType)) {
                            // the property is adaptable - later I guess we will find out if it is adaptable to the required type.
                            parameters.add(new AdaptParameterWrapper(i, getter, methodRequiredType));
                        } else if (List.class.isAssignableFrom(getterType)
                                && List.class.isAssignableFrom(methodRequiredType)
                                && genericGetterType instanceof ParameterizedType
                                && TypeUtils.isAssignable(
                                        ((ParameterizedType) genericGetterType).getActualTypeArguments()[0],
                                        IAdaptable.class)
                                && genericMethodRequiredType instanceof ParameterizedType) {
                            parameters.add(new ListAdaptParameterWrapper(i, getter,
                                    TypeUtils.getRawType(((ParameterizedType) genericMethodRequiredType).getActualTypeArguments()[0],
                                            genericMethodRequiredType)
                            ));
                        } else {
                            // the property must be assignable directly
                            if (TypeUtils.isAssignable(
                                    // we use getRawType here so that we can handle methods defined in a type-parameter
                                    // having base class - this fills in the type paramter if possible, so if we have
                                    // class <T> A { T getThing(); } and then class B extends A<Concrete> {},
                                    // we see Concrete getThing() as the method type here
                                    TypeUtils.getRawType(genericGetterType, adaptedType),
                                    genericMethodRequiredType)) {
                                parameters.add(new MethodParameterWrapper(i, getter));
                            } else {
                                throw new RuntimeException(
                                        String.format("parameter %d (annotated with %s) requires a value of type %s, but the property %s.%s has a return type of %s. Did you mean to implement IAdaptable?",
                                                i, p, genericMethodRequiredType, adaptedType.getSimpleName(), getter.getName(), genericGetterType));
                            }
                        }

                        if (addOptionalWrapper) {
                            parameters.set(i,
                                    new OptionalParameterWrapper(parameters.get(i)));
                        }
                    } else if (fs != null) {
                        final ScopeParameter scopeParameter = new ScopeParameter(i, fs.value());

                        if (Optional.class.isAssignableFrom(parameterTypes[i])) {
                            // given an optional parameter, we do a special thing
                            parameters.add(new OptionalParameterWrapper(scopeParameter));
                        } else {
                            parameters.add(scopeParameter);
                        }
                    } else if (TypeUtils.isAssignable(parameterTypes[i], adaptedType)) {
                        // it's the adaptable thing itself, which we pass straight in
                        parameters.add(new ValueParameterWrapper(i));
                    } else if (TypeUtils.isAssignable(parameterTypes[i], IAdaptingScope.class)) {
                        parameters.add(new ScopeItselfParameterWrapper(i));
                    } else if (TypeUtils.isAssignable(parameterTypes[i], Name.class)) {
                        parameters.add(new NameParameter(i));
                    } else {
                        throw new RuntimeException(
                                String.format("parameter %d (type %s) has no annotations that ReflectingAdapter can process.", i, genericParameterTypes[i]));
                    }
                }
            } catch (final RuntimeException re) {
                throw new RuntimeException(
                        String.format("In adapter method %s.%s, %s", ReflectingAdapter.this.getClass().getSimpleName(), method.getName(),
                                re.getMessage()), re);
            }
        }

        private Method getGetter(final Class<?> clazz, final String value) {
            for (final Method m : clazz.getMethods()) {
                if (m.getReturnType() != Void.TYPE && m.getParameterTypes().length == 0) {
                    if (m.getName().startsWith("get")) {
                        if (m.getName().substring(3).equalsIgnoreCase(value)) {
                            return m;
                        }
                    }
                    if (m.getName().startsWith("is")) {
                        if (m.getName().substring(2).equalsIgnoreCase(value)) {
                            return m;
                        }
                    }
                    if (m.isAnnotationPresent(Prop.class)) {
                        if (m.getAnnotation(Prop.class).value().equalsIgnoreCase(value)) {
                            return m;
                        }
                    }
                }
            }

            return null;
        }

        private <A extends Annotation> A getAnnotation(final Class<A> ac, final Annotation[] annotations) {
            for (final Annotation a : annotations) {
                if (ac.isInstance(a)) {
                    return ac.cast(a);
                }
            }
            return null;
        }

        public Object adapt(final Object from, final IAdaptingScope scope) {
            log.trace("adapting {} to {} in {}", new Object[]{from.getClass().getSimpleName(), outputType.getSimpleName(), scope});
            final Object[] args = new Object[parameterCount];
            // create a new scope to work in
            final IAdaptingScope innerScope = scope.createChildScope();

            // first things first; if the thing is meant to be in the scope, put it in directly
            if (outerPutScope != null) {
                innerScope.putLocal(outerPutScope, from);
            }

            // next, adapt all the parameters (or do whatever needs doing with them)
            for (final ParameterWrapper pw : parameters) {
                log.trace("processing argument {}", pw);
                Object object = pw.get(from, innerScope);
                if (object == null) {
                    throw new RuntimeException(String.format("result was null adapting %s in %s", pw,
                            (from instanceof XElement ? ((XElement) from).getSourceNode() : from)
                    ));
                }
                // legal null is the special marker which tells us
                // that we got null not because an adapter failed, but
                // because the value we're adapting from or getting 
                // was actually a null
                if (object == LEGAL_NULL) {
                    object = null;
                }
                args[pw.getIndex()] = object;

                // if the value is supposed to be scoped, scope it
                if (putScope[pw.getIndex()] != null) {
                    innerScope.putLocal(putScope[pw.getIndex()], object);
                }
            }

            try {
                log.trace("about to execute {} on {}", method.getName(), args);
                final Object result = method.invoke(ReflectingAdapter.this.target, args);

                if (result instanceof ISettableIdentified && from instanceof IIdentified) {
                    ((ISettableIdentified) result).setIdentifier(((IIdentified) from).getIdentifier());
                }

                return result;
            } catch (final InvocationTargetException ite) {
                final Throwable e = ite.getCause();
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                } else {
                    throw new RuntimeException("When executing " + method + " to make a " + outputType, e);
                }
            } catch (final Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private void addAdapterMethod(final Method m) {
        final AdapterMethod am = new AdapterMethod(m);
        exactAdaptables.put(am.adaptedType, am);
        if (am.inheritable) {
            inheritableAdaptables.put(am.adaptedType, am);
        }
    }

    @Override
    public boolean adapts(final Object from, final Class<?> to) {
        return adaptsType(from.getClass(), to);
    }

    /**
     * Check whether the adapter method can produce output of type to, OR
     * whether (for each converter) the adapter method can produce an output for
     * which the converter can produce an output of type to
     *
     * @param am
     * @param to
     * @return true if the adapter method can (with possible conversion) make a
     * to
     */
    private boolean methodAdaptsType(final AdapterMethod am, final Class<?> to) {
        if (TypeUtils.isAssignable(am.getOutputType(), to)) {
            return true;
        } else {
            for (final IAdapter converter : delegates) {
                if (converter.adaptsType(am.getOutputType(), to)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean adaptsType(final Class<?> from, final Class<?> to) {
        for (final AdapterMethod am : exactAdaptables.get(from)) {
            if (methodAdaptsType(am, to)) {
                return true;
            }
        }

        for (final java.util.Map.Entry<Class<?>, AdapterMethod> e : inheritableAdaptables.entries()) {
            if (e.getKey().isAssignableFrom(from)) {
                if (methodAdaptsType(e.getValue(), to)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static <T> T cache(final Object input, final T output, final IAdaptingScope cache, final boolean storeInCache) {
        if (storeInCache && output != null) {
            /**
             * the inverse of this is found in {@link AdaptableVisitable}
             */
            cache.putInCache(input, output);
        }

        return output;
    }

    /**
     * Using the given method, try and adapt from to an instanceof to; this may
     * use a converter from {@link #delegates}.
     *
     * @param am
     * @param from
     * @param to
     * @param scope
     * @return
     */
    private <T> T adaptWithMethod(final AdapterMethod am, final Object from, final Class<T> to, final IAdaptingScope scope) {
        try {
            final Object adapterOutput = am.adapt(from, scope);

            if (adapterOutput == null) {
                return null;
            }

            if (to.isInstance(adapterOutput)) {
                return cache(from, applyInterceptors(from, to.cast(adapterOutput), to, scope),
                        scope, am.shouldBeCached());
            }

            for (final IConverter c : delegates) {
                if (c.adapts(adapterOutput, to)) {
                    @SuppressWarnings({"unchecked", "rawtypes"})
                    final Object intercepted = applyInterceptors(from, adapterOutput,
                            (Class) c.getAdaptableSupertype(adapterOutput.getClass()), scope);

                    final T adapted = c.adapt(intercepted, to, scope);
                    if (adapted != null) {
                        return cache(from, adapted, scope, am.shouldBeCached());
                    }
                }
            }

            return null;
        } catch (Throwable th) {
            throw new RuntimeException(String.format("Adapting %s to %s:\n %s", from, to, th.getMessage()), th);
        }
    }

    /**
     * Turn from into a to in the scope. First we try all the exact adapters we
     * know. Then we try all the inheritable adapters.
     */
    @Override
    public <T> T adapt(final Object from, final Class<T> to, final IAdaptingScope scope) {
        for (final AdapterMethod am : exactAdaptables.get(from.getClass())) {
            if (methodAdaptsType(am, to)) {
                final T o = adaptWithMethod(am, from, to, scope);
                return o;
            }
        }

        for (final java.util.Map.Entry<Class<?>, AdapterMethod> e : inheritableAdaptables.entries()) {
            if (e.getKey().isAssignableFrom(from.getClass())) {
                if (methodAdaptsType(e.getValue(), to)) {
                    final T o = adaptWithMethod(e.getValue(), from, to, scope);
                    return o;
                }
            }
        }

        return null;
    }

    private <T> T applyInterceptors(final Object input, T value, final Class<T> clazz, IAdaptingScope scope) {
        for (final IAdapterInterceptor interceptor : interceptors) {
            if (interceptor.transforms(input, value, clazz)) {
                value = interceptor.transform(input, value, clazz, scope);
            }
        }

        return value;
    }

    /**
     * To see whether can can adapt this object to anything, we check whether
     * (a) we can exactly adapt its type or (b) we can inheritably adapt its
     * type
     */
    @Override
    public boolean adapts(final Object from) {
        if (exactAdaptables.containsKey(from.getClass())) {
            return true;
        } else {
            for (final Class<?> c : inheritableAdaptables.keySet()) {
                if (c.isInstance(from)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public Set<Class<?>> getAdaptableClasses() {
        final HashSet<Class<?>> result = new HashSet<Class<?>>();

        for (final AdapterMethod am : exactAdaptables.values()) {
            result.add(am.adaptedType);
        }

        return result;
    }
}
