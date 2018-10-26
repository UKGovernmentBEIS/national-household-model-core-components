package uk.org.cse.nhm.language.builder.convert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.language.adapt.IAdaptingScope;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.definition.two.selectors.ISetOfHouses;
import uk.org.cse.nhm.simulator.factories.IHookFactory;
import uk.org.cse.nhm.simulator.measure.sizing.impl.SizingFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Converts number functions into (size ...) so you can write less size
 */
public class NumToSizeConverter implements IConverter {

    protected static boolean isNumberComponentsFunction(final Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        if (!IComponentsFunction.class.isAssignableFrom(clazz)) {
            return false;
        }

        final Type[] faces = clazz.getGenericInterfaces();

        for (final Type parent : faces) {
            if (parent instanceof ParameterizedType) {
                final ParameterizedType pt = (ParameterizedType) parent;
                final Type raw = pt.getRawType();
                final Type[] ps = pt.getActualTypeArguments();
                if (raw instanceof Class<?>
                        && IComponentsFunction.class.isAssignableFrom((Class<?>) raw)
                        && ps != null && ps.length == 1) {
                    final Type p0 = ps[0];
                    if (p0 instanceof Class<?>
                            && Number.class.isAssignableFrom((Class<?>) p0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final IHookFactory factory;

    @Inject
    public NumToSizeConverter(IHookFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean adapts(Object from) {
        return isNumberComponentsFunction(from.getClass());
    }

    @Override
    public Class<?> getAdaptableSupertype(Class<?> clazz) {
        if (isNumberComponentsFunction(clazz)) {
            return IComponentsFunction.class;
        } else {
            return null;
        }
    }

    @Override
    public boolean adapts(Object from, Class<?> to) {
        return adaptsType(from.getClass(), to) && isNumberComponentsFunction(from.getClass());
    }

    @Override
    public boolean adaptsType(Class<?> from, Class<?> to) {
        return (IComponentsFunction.class.isAssignableFrom(from))
                && to.isAssignableFrom(SizingFunction.class);
    }

    @Override
    public <T> T adapt(Object from, Class<T> to, IAdaptingScope scope) {
        if (adapts(from, to)) {
            return to.cast(new SizingFunction( 
            		(IComponentsFunction<Number>) from, 
            		0, Double.MAX_VALUE));
        } else {
            return null;
        }
    }

    @Override
    public Set<Class<?>> getAdaptableClasses() {
        return ImmutableSet.<Class<?>>of(IComponentsFunction.class, ISetOfHouses.class);
    }
}
