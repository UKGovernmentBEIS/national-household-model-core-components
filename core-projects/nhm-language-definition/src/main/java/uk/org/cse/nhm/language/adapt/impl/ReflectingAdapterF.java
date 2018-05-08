package uk.org.cse.nhm.language.adapt.impl;

import java.util.Set;

import javax.inject.Inject;

import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;

public class ReflectingAdapterF<T> extends ReflectingAdapter {

    @Inject
    public ReflectingAdapterF(final Set<IConverter> delegates,
            final Set<IAdapterInterceptor> interceptors,
            final Class<T> clazz,
            final T target) {
        super(delegates,
                interceptors,
                clazz,
                target);
    }
}
