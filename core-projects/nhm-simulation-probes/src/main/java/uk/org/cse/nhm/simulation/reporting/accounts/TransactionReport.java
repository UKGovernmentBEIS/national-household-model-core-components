package uk.org.cse.nhm.simulation.reporting.accounts;

import java.util.Set;
import java.util.Stack;

import org.joda.time.DateTime;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.commons.scopes.IScopeVisitor;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.TransactionLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.hooks.IDwellingSet;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

/**
 * A report which dumps the transaction history for groups of houses
 *
 * <report.log below="a/b/c">
 * <transactions />
 * <installations />
 * <obligations />
 * </report.log>
 *
 *
 * id, value, payee, blah, addr 0 100 bob x /my-policy/my-target/package/boiler
 * 0 40 jim y /my-policy/my-target/package/insulation
 *
 *
 * id, value, payee, blah, depth, addr 0 100	bob	x 0 100	bob	x
 *
 *
 *
 * @since 3.7.0
 *
 */
public class TransactionReport extends AbstractNamed implements IStateListener {

    final IDwellingSet group;
    final Set<String> requiredTags;
    final IDimension<DwellingTransactionHistory> transactions;
    final ILogEntryHandler loggingService;

    @AssistedInject
    public TransactionReport(
            final IDimension<DwellingTransactionHistory> transactions,
            final ICanonicalState state,
            final ILogEntryHandler loggingService,
            @Assisted final IDwellingSet group,
            @Assisted final Set<String> requiredTags
    ) {
        this.transactions = transactions;
        this.loggingService = loggingService;
        this.group = group;
        this.requiredTags = requiredTags;
        // there is a guarantee here that we are registered to listen to state after our group,
        // because our group registered itself in its constructor as well.
        // consequently the group will be up-to-date once we are notified of a state change
        state.addStateListener(this);
    }

    @Override
    public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
        final IStateScope rootScope = notification.getRootScope();

        final Set<IDwelling> changedDwellings = notification.getChangedDwellings(transactions);
        final Set<IDwelling> inGroup = group.get(state, ILets.EMPTY);
        final Set<IDwelling> iterate;
        final Set<IDwelling> test;

        if (inGroup.size() > changedDwellings.size()) {
            iterate = changedDwellings;
            test = inGroup;
        } else {
            iterate = inGroup;
            test = changedDwellings;
        }

        if (iterate.isEmpty()) {
            /* Check for any non-dwelling transactions. */
            for (final ITransaction t : rootScope.getAllNotes(ITransaction.class)) {
                log(notification.getDate(),
                        t.getPayer(),
                        1f,
                        t.getAmount(),
                        rootScope.getTag().getIdentifier().getName(),
                        t.getPayee(),
                        Joiner.on(',').join(t.getTags()));
            }
        }

        for (final IDwelling d : iterate) {
            if (!test.contains(d)) {
                continue;
            }
            final Optional<IComponentsScope> componentsScope = rootScope.getComponentsScope(d);
            if (componentsScope.isPresent()) {
                logMatchingTransactions(notification.getDate(), componentsScope.get());
            }
        }
    }

    private void logMatchingTransactions(final DateTime date, final IComponentsScope componentsScope) {
        componentsScope.accept(new IScopeVisitor<IStateChangeSource>() {
            final Stack<Name> names = new Stack<>();

            @Override
            public void enterScope(final IStateChangeSource tag) {
                names.push(tag.getIdentifier());
            }

            @Override
            public void visit(final Object note) {
                if (note instanceof ITransaction) {
                    final ITransaction t = (ITransaction) note;
                    if (t.getTags().containsAll(requiredTags) == false) {
                        return;
                    }

                    log(date, t.getPayer(),
                            componentsScope.getDwelling().getWeight(),
                            t.getAmount() * componentsScope.getDwelling().getWeight(), /* this is a dwelling transaction */
                            names.peek().getPath(),
                            t.getPayee(),
                            Joiner.on(',').join(t.getTags()));
                }
            }

            @Override
            public void exitScope() {
                names.pop();
            }
        });
    }

    private void log(final DateTime dateTime, final String payer, final float weight, final double amount, final String path, final String payee, final String tags) {
        loggingService.acceptLogEntry(
                new TransactionLogEntry(dateTime, payer, weight, amount, path, payee, getIdentifier().getName(), tags));
    }
}
