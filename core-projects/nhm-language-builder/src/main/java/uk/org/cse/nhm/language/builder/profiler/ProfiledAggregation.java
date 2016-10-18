package uk.org.cse.nhm.language.builder.profiler;

import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;

public class ProfiledAggregation implements IAggregationFunction {
    final IProfilingStack prof;
    final IAggregationFunction delegate;

    @AssistedInject
    public ProfiledAggregation(final IProfilingStack prof,@Assisted final IAggregationFunction delegate) {
        this.prof = prof;
        this.delegate = delegate;
    }
    
    @Override
    public double evaluate(final IState state, final ILets lets, final Set<IDwelling> dwellings) {
        try {
            prof.push(this);
            return delegate.evaluate(state, lets, dwellings);
        } finally {
            prof.pop(this);
        }
    }

    @Override
    public double evaluate(final IStateScope state, final ILets lets, final Set<IDwelling> dwellings) {
        try {
            prof.push(this);
            return delegate.evaluate(state, lets, dwellings);
        } finally {
            prof.pop(this);
        }
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


