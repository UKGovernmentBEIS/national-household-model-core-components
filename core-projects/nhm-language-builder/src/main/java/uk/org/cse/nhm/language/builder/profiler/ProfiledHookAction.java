package uk.org.cse.nhm.language.builder.profiler;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.hooks.IHookRunnable;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;

public class ProfiledHookAction implements IHookRunnable {
    private final IProfilingStack prof;
    private final IHookRunnable delegate;

    @AssistedInject
    public ProfiledHookAction(final IProfilingStack prof, @Assisted final IHookRunnable delegate) {
        this.prof = prof;
        this.delegate = delegate;
    }

    @Override
    public Name getIdentifier() {
        return delegate.getIdentifier();
    }

    @Override
    public void run(final IStateScope state,
                    final DateTime date, 
                    final Set<IStateChangeSource> causes, 
                    final ILets lets) {
        try {
            prof.push(this);
            delegate.run(state, date, causes, lets);
        } finally {
            prof.pop(this);
        }
    }

    
    @Override
    public String toString() {
        return delegate.toString();
    }
}
