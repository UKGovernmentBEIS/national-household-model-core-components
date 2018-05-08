package uk.org.cse.nhm.simulator.state.impl;

import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.commons.scopes.impl.ImmutableScope;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class TimeScope extends ImmutableScope<IStateChangeSource> implements IStateScope {

    protected TimeScope() {
        super(TIME);

    }

    private static final class Time implements IStateChangeSource {

        @Override
        public StateChangeSourceType getSourceType() {
            return StateChangeSourceType.TIME;
        }

        @Override
        public Name getIdentifier() {
            return Name.of("The passage of time");
        }
    }

    protected static final Time TIME = new Time();

    @Override
    public Set<IDwelling> apply(final IStateAction action, final Set<IDwelling> dwellings, final ILets lets) {
        throw new UnsupportedOperationException("Cannot change time.");
    }

    @Override
    public Set<IDwelling> apply(final IComponentsAction action, final Set<IDwelling> dwellings, final ILets lets) {
        throw new UnsupportedOperationException("Cannot change time.");
    }

    @Override
    public IBranch getState() {
        throw new UnsupportedOperationException("Time has no branches.");
    }

    @Override
    public Optional<IComponentsScope> getComponentsScope(final IDwelling dwelling) {
        return Optional.absent();
    }

    @Override
    public void merge(final IStateScope ss) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IStateScope branch(final IStateChangeSource t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IHypotheticalComponentsScope createHypothesis(IDwelling dwelling) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IComponentsScope getPriorScope(final IDwelling dwelling) {
        throw new UnsupportedOperationException("Time should not be offering you a prior vision of things.");
    }
}
