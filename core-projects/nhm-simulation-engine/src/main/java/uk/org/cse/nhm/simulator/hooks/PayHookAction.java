package uk.org.cse.nhm.simulator.hooks;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.Transaction;

public class PayHookAction extends AbstractNamed implements IHookRunnable, IStateChangeSource {

    private final String from;
    private final String to;
    private final IComponentsFunction<? extends Number> amount;
    private final Set<String> tags;

    @AssistedInject
    public PayHookAction(@Assisted("from") final String from,
            @Assisted("to") final String to,
            @Assisted final Set<String> tags,
            @Assisted final IComponentsFunction<? extends Number> amount) {
        this.from = from;
        this.to = to;
        this.tags = tags;
        this.amount = amount;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.TRIGGER;
    }

    @Override
    public void run(final IStateScope state, final DateTime date, final Set<IStateChangeSource> causes, final ILets lets) {
        state.apply(new IStateAction() {
            @Override
            public StateChangeSourceType getSourceType() {
                return PayHookAction.this.getSourceType();
            }

            @Override
            public Name getIdentifier() {
                return PayHookAction.this.getIdentifier();
            }

            @Override
            public Set<IDwelling> apply(final IStateScope scope, final Set<IDwelling> dwellings,
                    final ILets lets) throws NHMException {
                final ITransaction t = Transaction.global(
                        from,
                        to,
                        amount.compute(scope.getState().detachedScope(null), lets).doubleValue(),
                        date,
                        tags);

                scope.addNote(t);

                scope.getState().getGlobals().getGlobalAccount(from).pay(t);
                scope.getState().getGlobals().getGlobalAccount(to).receive(t);
                return Collections.emptySet();
            }

            @Override
            public Set<IDwelling> getSuitable(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) {
                return dwellings;
            }
        }, ImmutableSet.<IDwelling>of(), lets);
    }
}
