package uk.org.cse.nhm.simulator.trigger;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * This is a class which adapts an {@link IDwellingAction} into an
 * {@link IStateAction}, by invoking the {@link IDwellingAction} on every
 * {@link IDwellingAction} passed to the {@link #apply(IState, Set, ILets)}
 * method one after another.
 *
 * @author hinton
 *
 */
public class StateActionAdapter implements IStateAction {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(StateActionAdapter.class);

    private final IComponentsAction delegate;

    public StateActionAdapter(final IComponentsAction delegate) {
        this.delegate = delegate;
    }

    @Override
    public Set<IDwelling> apply(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) throws NHMException {
        return scope.apply(delegate, dwellings, lets);
    }

    @Override
    public Set<IDwelling> getSuitable(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) {
        final ImmutableSet.Builder<IDwelling> suitables = ImmutableSet.builder();
        for (final IDwelling d : dwellings) {
            if (delegate.isSuitable(scope.getState().detachedScope(d), lets)) {
                suitables.add(d);
            }
        }
        return suitables.build();
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return delegate.getSourceType();
    }

    public IComponentsAction getDelegate() {
        return delegate;
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
