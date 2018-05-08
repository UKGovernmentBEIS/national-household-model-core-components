package uk.org.cse.nhm.simulator.guice;

import java.lang.reflect.ParameterizedType;

import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.Provider;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import com.google.inject.util.Providers;
import com.google.inject.util.Types;

import uk.org.cse.nhm.hom.ICopyable;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.impl.CanonicalDimension;
import uk.org.cse.nhm.simulator.state.dimensions.impl.IDimensionManager;
import uk.org.cse.nhm.simulator.state.dimensions.impl.UniqueCopyableDimensionManager;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public abstract class StandardDimensionModule<T extends ICopyable<T>> extends PrivateModule {

    private final String name;
    private final T defaultValue;
    private final Provider<T> defaultValueProvider;
    private final Class<? extends Provider<T>> providerClass;
    private final Class<T> valueClass;

    public StandardDimensionModule(final String name, final T defaultValue) {
        this(name, defaultValue, null, null);
    }

    public StandardDimensionModule(final String name, final Provider<T> defaultValueProvider) {
        this(name, null, defaultValueProvider, null);
    }

    public StandardDimensionModule(final String name, final Class<? extends Provider<T>> providerClass) {
        this(name, null, null, providerClass);
    }

    @SuppressWarnings("unchecked")
    public StandardDimensionModule(
            final String name,
            final T defaultValue,
            final Provider<T> provider,
            final Class<? extends Provider<T>> providerClass) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.defaultValueProvider = provider;
        this.providerClass = providerClass;

        final ParameterizedType pt = (ParameterizedType) (getClass().getGenericSuperclass());

        valueClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public StandardDimensionModule(final String name) {
        this(name, null, null, null);
    }

    @Override
    protected void configure() {
        if (providerClass != null) {
            bind(valueClass).toProvider(providerClass);
        } else if (defaultValueProvider != null) {
            bind(valueClass).toProvider(defaultValueProvider);
        } else {
            bind(valueClass).toProvider(Providers.of(defaultValue));
        }

        bind(String.class).annotatedWith(Names.named(CanonicalDimension.DIMENSION_NAME)).toInstance(name);

        bindManagerType();

        bindImplementationType();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void bindImplementationType() {
        final ParameterizedType dimensionInterfaceType
                = Types.newParameterizedType(IDimension.class, valueClass);

        final ParameterizedType implType
                = Types.newParameterizedType(CanonicalDimension.class, valueClass);

        bind(Key.get(implType)).in(SimulationScoped.class);

        bind(Key.get(dimensionInterfaceType))
                .to((Key) Key.get(implType)).in(SimulationScoped.class);

        expose(Key.get(dimensionInterfaceType));
        expose(Key.get(implType));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void bindManagerType() {
        final ParameterizedType managerType = Types.newParameterizedType(IDimensionManager.class, valueClass);
        final Key<?> managerImpl;
        managerImpl = getManagerType();

        bind(Key.get(managerType)).to((Key) managerImpl).in(SimulationScoped.class);
    }

    protected Key<?> getManagerType() {
        return Key.get(Types.newParameterizedType(UniqueCopyableDimensionManager.class, valueClass));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public StandardDimensionModule<T> bindDimension(final Multibinder<IDimension<?>> dimensions) {
        final ParameterizedType dimensionInterfaceType
                = Types.newParameterizedType(IDimension.class, valueClass);
        dimensions.addBinding().to((Key) Key.get(dimensionInterfaceType));
        return this;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public StandardDimensionModule<T> bindInternal(final Multibinder<IInternalDimension<?>> internalDimensions) {
        final ParameterizedType implType
                = Types.newParameterizedType(CanonicalDimension.class, valueClass);
        internalDimensions.addBinding().to((Key) Key.get(implType));
        return this;
    }
}
