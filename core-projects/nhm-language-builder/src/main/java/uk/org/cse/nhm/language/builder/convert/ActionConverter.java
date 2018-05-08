package uk.org.cse.nhm.language.builder.convert;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.language.adapt.IAdaptingScope;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.trigger.StateActionAdapter;

/**
 * Performs the following helpful type conversions:
 *
 * <ul>
 * <li> {@link IComponentsAction} -> {@link IStateAction} </li>
 * </ul>
 *
 * @author hinton
 *
 */
public class ActionConverter implements IConverter {

    @SuppressWarnings("unused")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
            .getLogger(ActionConverter.class);

    @Override
    public boolean adapts(Object from) {
        return (from instanceof IComponentsAction);
    }

    @Override
    public Class<?> getAdaptableSupertype(Class<?> clazz) {
        if (IComponentsAction.class.isAssignableFrom(clazz)) {
            return IComponentsAction.class;
        } else {
            return null;
        }
    }

    @Override
    public boolean adapts(Object from, Class<?> to) {
        return adaptsType(from.getClass(), to);
    }

    @Override
    public boolean adaptsType(Class<?> from, Class<?> to) {
        return IComponentsAction.class.isAssignableFrom(from)
                && to.isAssignableFrom(IStateAction.class);
    }

    @Override
    public <T> T adapt(Object from, Class<T> to, IAdaptingScope scope) {
        if (from instanceof IComponentsAction && to.isAssignableFrom(IStateAction.class)) {
            return to.cast(new StateActionAdapter((IComponentsAction) from));
        } else {
            return null;
        }
    }

    @Override
    public Set<Class<?>> getAdaptableClasses() {
        return ImmutableSet.<Class<?>>of(IComponentsAction.class);
    }
}
