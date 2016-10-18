package uk.org.cse.nhm.language.builder.profiler;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ProfiledComponentsFunction<T> implements IComponentsFunction<T> {
    private final IProfilingStack prof;
    private final IComponentsFunction<T> delegate;

    public static class OfBoolean extends ProfiledComponentsFunction<Boolean> {
        @AssistedInject
        public OfBoolean(final IProfilingStack prof,
                         @Assisted final IComponentsFunction<Boolean> delegate) {
            super(prof, delegate);
        }
    }

    public static class OfDouble extends ProfiledComponentsFunction<Double> {
        @AssistedInject
        public OfDouble(final IProfilingStack prof,
                         @Assisted final IComponentsFunction<Double> delegate) {
            super(prof, delegate);
        }
    }

    public static class OfInteger extends ProfiledComponentsFunction<Integer> {
        @AssistedInject
        public OfInteger(final IProfilingStack prof,
                         @Assisted final IComponentsFunction<Integer> delegate) {
            super(prof, delegate);
        }
    }

    public static class OfNumber extends ProfiledComponentsFunction<Number> {
        @AssistedInject
        public OfNumber(final IProfilingStack prof,
                         @Assisted final IComponentsFunction<Number> delegate) {
            super(prof, delegate);
        }
    }

    
    public ProfiledComponentsFunction(final IProfilingStack prof, final IComponentsFunction<T> delegate) {
        this.prof = prof;
        this.delegate = delegate;
    }
    
	@Override
    public T compute(final IComponentsScope scope, final ILets lets) {
        try {
            prof.push(this);
            return delegate.compute(scope, lets);
        } finally {
            prof.pop(this);
        }
    }

	@Override
    public Set<IDimension<?>> getDependencies() {
        return delegate.getDependencies();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return delegate.getChangeDates();
    }
    
    @Override
    public Name getIdentifier() {
        return delegate.getIdentifier();
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}


