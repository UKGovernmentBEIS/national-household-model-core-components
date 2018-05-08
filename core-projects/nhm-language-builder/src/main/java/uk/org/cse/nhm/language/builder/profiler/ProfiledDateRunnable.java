package uk.org.cse.nhm.language.builder.profiler;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.Initializable;

public class ProfiledDateRunnable implements IIdentified, IDateRunnable, Initializable {

    private final Object delegate;
    private final IProfilingStack prof;

    @AssistedInject
    public ProfiledDateRunnable(final IProfilingStack prof, @Assisted final Object delegate) {
        this.prof = prof;
        this.delegate = delegate;
    }

    @Override
    public void initialize() throws NHMException {
        // never delegate initializability
    }

    @Override
    public void run(final DateTime date) {
        if (delegate instanceof IDateRunnable) {
            try {
                prof.push(this);
                ((IDateRunnable) delegate).run(date);
            } finally {
                prof.pop(this);
            }
        }
    }

    @Override
    public Name getIdentifier() {
        return ((IIdentified) delegate).getIdentifier();
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}
