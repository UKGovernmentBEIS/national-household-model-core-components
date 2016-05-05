package uk.org.cse.nhm.language.builder.profiler;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class ProfiledComponentsAction implements IComponentsAction {
    private final IProfilingStack prof;
    private final IComponentsAction delegate;

    @AssistedInject
    public ProfiledComponentsAction(final IProfilingStack prof, @Assisted final IComponentsAction delegate) {
        this.prof = prof;
        this.delegate = delegate;
    }
    
    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets) {
        try {
            prof.push(this);
            return delegate.apply(scope, lets);
        } finally {
            prof.pop(this);
        }
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return delegate.isSuitable(scope, lets);
    }

    @Override
    public boolean isAlwaysSuitable() {
        return delegate.isAlwaysSuitable();
    }

    public StateChangeSourceType getSourceType() {
        return delegate.getSourceType();
    }

    public Name getIdentifier() {
        return delegate.getIdentifier();
    }

    
    @Override
    public String toString() {
        return delegate.toString();
    }
}


