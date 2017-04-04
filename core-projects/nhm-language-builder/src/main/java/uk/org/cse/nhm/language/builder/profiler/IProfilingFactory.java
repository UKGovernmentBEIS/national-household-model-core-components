package uk.org.cse.nhm.language.builder.profiler;

import uk.org.cse.nhm.simulator.hooks.IDwellingSet;
import uk.org.cse.nhm.simulator.hooks.IHookRunnable;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public interface IProfilingFactory {
    public ProfiledAggregation aggregation(final IAggregationFunction delegate);
    public ProfiledComponentsAction componentsAction(final IComponentsAction delegate);
    public ProfiledStateAction stateAction(final IStateAction delegate);
    public ProfiledDwellingSet dwellingSet(final IDwellingSet delegate);
    public ProfiledHookAction hookAction(final IHookRunnable delegate);

    public ProfiledComponentsFunction.OfBoolean bool(final IComponentsFunction<Boolean> delegate);
    public ProfiledComponentsFunction.OfDouble dbl(final IComponentsFunction<Double> delegate);
    public ProfiledComponentsFunction.OfInteger integer(final IComponentsFunction<Integer> delegate);
    public ProfiledComponentsFunction.OfNumber number(final IComponentsFunction<Number> delegate);

    public ProfiledDateRunnable dateRunnable(final Object delegate);
}


