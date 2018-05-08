package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class PriorFunction extends AbstractNamed implements IComponentsFunction<Double> {

    private final IComponentsFunction<? extends Number> delegate;
    private final IProfilingStack profiler;

    @AssistedInject
    public PriorFunction(
            final IProfilingStack profiler,
            @Assisted final IComponentsFunction<? extends Number> delegate) {
        this.delegate = delegate;
        this.profiler = profiler;
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final IComponentsScope before = scope.getPriorScope();
        // what lets do we use? - if we use the current lets, we have some total weirdness
        // let's use them for now. argh.
        final Number valueBefore = delegate.compute(before, lets);
        return valueBefore.doubleValue();
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return delegate.getDependencies();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return delegate.getChangeDates();
    }
}
