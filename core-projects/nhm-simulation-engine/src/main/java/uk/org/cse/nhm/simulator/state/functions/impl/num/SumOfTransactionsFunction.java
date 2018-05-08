package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collection;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.ITransactionRunningTotal;
import uk.org.cse.nhm.simulator.transactions.ITransactionRunningTotal.ITransactionAccumulator;

public class SumOfTransactionsFunction extends AbstractNamed implements
        IComponentsFunction<Double> {

    private final ITransactionRunningTotal runningTotal;
    private ITransactionAccumulator accumulator;

    @AssistedInject
    public SumOfTransactionsFunction(
            final ITransactionRunningTotal runningTotal,
            @Assisted final Optional<Glob> counterparty,
            @Assisted final Predicate<Collection<String>> requiredTags) {

        this.runningTotal = runningTotal;

        accumulator = new ITransactionRunningTotal.ITransactionAccumulator() {
            @Override
            public double accum(final double existingValue,
                    final ITransaction transaction) {
                final boolean counterpartyMatches
                        = counterparty.isPresent()
                        ? counterparty.get().matches(transaction.getPayee()) : true;
                if (counterpartyMatches) {
                    if (requiredTags.apply(transaction.getTags())) {
                        return existingValue + transaction.getAmount();
                    }
                }

                return existingValue;
            }
        };

        runningTotal.registerAccumulator(accumulator);
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        return runningTotal.getAccumulatedValue(accumulator, scope);
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return runningTotal.getDependencies();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return ImmutableSet.of();
    }
}
