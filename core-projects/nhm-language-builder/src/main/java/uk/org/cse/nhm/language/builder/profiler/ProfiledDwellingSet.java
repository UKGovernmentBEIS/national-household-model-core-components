package uk.org.cse.nhm.language.builder.profiler;

import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.hooks.IDwellingSet;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;

public class ProfiledDwellingSet implements IDwellingSet {

    private final IProfilingStack prof;
    private final IDwellingSet delegate;

    @AssistedInject
    public ProfiledDwellingSet(final IProfilingStack prof, @Assisted final IDwellingSet delegate) {
        this.prof = prof;
        this.delegate = delegate;
    }

    @Override
    public Set<IDwelling> get(final IState state, ILets lets) {
        try {
            prof.push(this);
            return delegate.get(state, lets);
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
