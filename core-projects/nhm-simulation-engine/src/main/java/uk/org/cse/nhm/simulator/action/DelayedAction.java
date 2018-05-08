package uk.org.cse.nhm.simulator.action;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Priority;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.trigger.StateActionAdapter;

public class DelayedAction extends AbstractNamed implements IComponentsAction {

    private final IComponentsAction action;
    private final Period delay;
    private final ISimulator simulator;
    private final ICanonicalState state;

    @AssistedInject
    public DelayedAction(
            @Assisted final IComponentsAction action,
            @Assisted final Period delay,
            final ISimulator simulator,
            final ICanonicalState state
    ) {
        this.action = action;
        this.delay = delay;
        this.simulator = simulator;
        this.state = state;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        final IDwelling d = scope.getDwelling();
        final ILets captured = lets.assignableTo(Number.class);
        final Map<String, Double> yields = scope.getYieldedValues();

        scope.getState().schedule(
                simulator.getCurrentDate().plus(delay),
                Priority.ofIdentifier(action.getIdentifier()),
                new IDateRunnable() {
            @Override
            public void run(final DateTime date) {
                if (state.getDwellings().contains(d)) {
                    state.apply(DelayedAction.this,
                            new StateActionAdapter(new PreCalculatedLet(captured, yields, action)), ImmutableSet.of(d),
                            ILets.EMPTY
                    );
                }
            }
        });

        return true;
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return true;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return true;
    }

    static class PreCalculatedLet implements IComponentsAction {

        private final IComponentsAction delegate;
        private final ILets letParams;
        private final Map<String, Double> yields;

        PreCalculatedLet(final ILets letParams, final Map<String, Double> yields, final IComponentsAction delegate) {
            this.letParams = letParams;
            this.yields = yields;
            this.delegate = delegate;
        }

        @Override
        public StateChangeSourceType getSourceType() {
            return delegate.getSourceType();
        }

        @Override
        public Name getIdentifier() {
            return delegate.getIdentifier();
        }

        @Override
        public boolean apply(final ISettableComponentsScope scope, final ILets lets)
                throws NHMException {

            for (final Map.Entry<String, Double> e : yields.entrySet()) {
                scope.yield(e.getKey(), e.getValue());
            }

            return scope.apply(delegate, lets.withLets(letParams));
        }

        @Override
        public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
            return delegate.isSuitable(scope, lets);
        }

        @Override
        public boolean isAlwaysSuitable() {
            return delegate.isAlwaysSuitable();
        }
    }
}
