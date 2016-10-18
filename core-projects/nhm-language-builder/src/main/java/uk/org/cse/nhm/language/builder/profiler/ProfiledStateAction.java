package uk.org.cse.nhm.language.builder.profiler;

import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class ProfiledStateAction implements IStateAction {
    private final IProfilingStack prof;
    private final IStateAction delegate;

    @AssistedInject
    public ProfiledStateAction(final IProfilingStack prof, @Assisted final IStateAction delegate) {
        this.prof = prof;
        this.delegate = delegate;
    }

    @Override
    public Name getIdentifier() {
        return delegate.getIdentifier();
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return delegate.getSourceType();
    }

    @Override
    public Set<IDwelling> apply(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) throws NHMException {
        try {
            prof.push(this);
            return delegate.apply(scope, dwellings, lets);
        } finally {
            prof.pop(this);
        }
    }

    @Override
    public Set<IDwelling> getSuitable(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) {
        return delegate.getSuitable(scope, dwellings, lets);
    }
    
    @Override
    public String toString() {
        return delegate.toString();
    }
}


