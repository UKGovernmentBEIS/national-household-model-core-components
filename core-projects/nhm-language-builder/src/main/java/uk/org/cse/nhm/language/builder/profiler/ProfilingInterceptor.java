package uk.org.cse.nhm.language.builder.profiler;

import javax.inject.Inject;

import com.google.common.reflect.TypeToken;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IAdaptingScope;
import uk.org.cse.nhm.simulator.action.ConstructHousesAction;
import uk.org.cse.nhm.simulator.hooks.IDwellingSet;
import uk.org.cse.nhm.simulator.hooks.IHookRunnable;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ProfilingInterceptor implements IAdapterInterceptor {

    private final IProfilingFactory profile;
    private boolean enabled = false;

    @Inject
    public ProfilingInterceptor(final IProfilingFactory profile) {
        this.profile = profile;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public <T> boolean transforms(final Object input, final T adapted, final Class<T> clazz) {
        if (enabled) {
            if (adapted instanceof IIdentified && isProfilable(adapted, clazz)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T transform(final Object input, final T adapted, final Class<T> clazz, final IAdaptingScope scope) {
        if (adapted instanceof IComponentsAction && clazz.isAssignableFrom(ProfiledComponentsAction.class)) {
            return clazz.cast(profile.componentsAction((IComponentsAction) adapted));
        } else if (adapted instanceof IStateAction && clazz.isAssignableFrom(ProfiledStateAction.class)) {
            return clazz.cast(profile.stateAction((IStateAction) adapted));
        } else if (adapted instanceof IDwellingSet && clazz.isAssignableFrom(ProfiledDwellingSet.class)) {
            return clazz.cast(profile.dwellingSet((IDwellingSet) adapted));
        } else if (isFn(adapted, Boolean.class) && clazz.isAssignableFrom(ProfiledComponentsFunction.OfBoolean.class)) {
            return clazz.cast(profile.bool((IComponentsFunction<Boolean>) adapted));
        } else if (isFn(adapted, Double.class) && clazz.isAssignableFrom(ProfiledComponentsFunction.OfDouble.class)) {
            return clazz.cast(profile.dbl((IComponentsFunction<Double>) adapted));
        } else if (isFn(adapted, Integer.class) && clazz.isAssignableFrom(ProfiledComponentsFunction.OfInteger.class)) {
            return clazz.cast(profile.integer((IComponentsFunction<Integer>) adapted));
        } else if (isFn(adapted, Number.class) && clazz.isAssignableFrom(ProfiledComponentsFunction.OfNumber.class)) {
            return clazz.cast(profile.number((IComponentsFunction<Number>) adapted));
        } else if (adapted instanceof IHookRunnable && clazz.isAssignableFrom(ProfiledHookAction.class)) {
            return clazz.cast(profile.hookAction((IHookRunnable) adapted));
        } else if (adapted instanceof IAggregationFunction && clazz.isAssignableFrom(ProfiledAggregation.class)) {
            return clazz.cast(profile.aggregation((IAggregationFunction) adapted));
        } else if (adapted instanceof IDateRunnable
                && adapted instanceof IIdentified
                && adapted instanceof Initializable
                && clazz.isAssignableFrom(ProfiledDateRunnable.class)) {
            return clazz.cast(dateRunnable(adapted));
        }
        return adapted;
    }

    private <Q extends IDateRunnable & IIdentified & Initializable>
            ProfiledDateRunnable dateRunnable(final Object delegate) {
        // hack types
        return profile.dateRunnable(delegate);
    }

    @SuppressWarnings("rawtypes")
    private boolean isFn(final Object fn, final Class<?> param) {
        if (fn instanceof IComponentsFunction) {
            final TypeToken<? extends IComponentsFunction> tt = TypeToken.of(((IComponentsFunction<?>) fn).getClass());
            final TypeToken<?> resolvedType = tt.resolveType(IComponentsFunction.class.getTypeParameters()[0]);
            return param.isAssignableFrom(resolvedType.getRawType());
        } else {
            return false;
        }
    }

    private <T> boolean isProfilable(final T adapted, final Class<T> clazz) {
        if (adapted instanceof ISequenceSpecialAction || adapted instanceof ConstructHousesAction) {
            // both of these have some special handling and break stuff if we proxy them.
            return false;
        }
        if (adapted instanceof IComponentsAction && clazz.isAssignableFrom(ProfiledComponentsAction.class)) {
            return true;
        } else if (adapted instanceof IStateAction && clazz.isAssignableFrom(ProfiledStateAction.class)) {
            return true;
        } else if (adapted instanceof IDwellingSet && clazz.isAssignableFrom(ProfiledDwellingSet.class)) {
            return true;
        } else if (isFn(adapted, Boolean.class) && clazz.isAssignableFrom(ProfiledComponentsFunction.OfBoolean.class)) {
            return true;
        } else if (isFn(adapted, Double.class) && clazz.isAssignableFrom(ProfiledComponentsFunction.OfDouble.class)) {
            return true;
        } else if (isFn(adapted, Integer.class) && clazz.isAssignableFrom(ProfiledComponentsFunction.OfInteger.class)) {
            return true;
        } else if (isFn(adapted, Number.class) && clazz.isAssignableFrom(ProfiledComponentsFunction.OfNumber.class)) {
            return true;
        } else if (adapted instanceof IHookRunnable && clazz.isAssignableFrom(ProfiledHookAction.class)) {
            return true;
        } else if (adapted instanceof IAggregationFunction && clazz.isAssignableFrom(ProfiledAggregation.class)) {
            return true;
        } else if (adapted instanceof IDateRunnable
                && adapted instanceof IIdentified
                && adapted instanceof Initializable
                && clazz.isAssignableFrom(ProfiledDateRunnable.class)) {
            return true;
        }
        return false;
    }
}
