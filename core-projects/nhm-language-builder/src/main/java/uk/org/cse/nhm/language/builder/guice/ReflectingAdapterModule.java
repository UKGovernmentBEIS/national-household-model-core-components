package uk.org.cse.nhm.language.builder.guice;

import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.util.Types;

import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapterF;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ReflectingAdapterModule<F> extends PrivateModule {
    private final Class<F> factoryClass;
	private final Key key;

    public ReflectingAdapterModule(final Class<F> factoryClass) {
        this.factoryClass = factoryClass;
         key = Key.get(Types.newParameterizedType(ReflectingAdapterF.class,
                                                           factoryClass));
    }

	@Override
    protected void configure() {
        bind((Key) Key.get(Types.newParameterizedType(Class.class, factoryClass)))
            .toInstance(factoryClass);
        bind(key);

        expose(key);
    }

    public Key<IAdapter> getBinding() {
        return (Key) key;
    }
}
