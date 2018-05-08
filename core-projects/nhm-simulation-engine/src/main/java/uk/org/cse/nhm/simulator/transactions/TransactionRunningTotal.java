package uk.org.cse.nhm.simulator.transactions;

import java.util.LinkedList;
import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;

public class TransactionRunningTotal implements ITransactionRunningTotal {

    private final IDimension<DwellingTransactionHistory> transactionsDimension;

    LinkedList<ITransactionRunningTotal.ITransactionAccumulator> accumulators = new LinkedList<>();

    private boolean finishedRegistering = false;

    @Inject
    public TransactionRunningTotal(final IDimension<DwellingTransactionHistory> transactionsDimension) {
        this.transactionsDimension = transactionsDimension;
    }

    @Override
    public void registerAccumulator(final ITransactionAccumulator accumulator) {
        if (finishedRegistering) {
            throw new IllegalStateException("Cannot add any more transaction accumulators after getting values out. Saw: " + accumulator);
        }

        if (!accumulators.contains(accumulator)) {
            accumulators.add(accumulator);
        }
    }

    @Override
    public double getAccumulatedValue(final ITransactionAccumulator accumulator,
            final IComponents components) {
        finishedRegistering = true;

        if (!accumulators.contains(accumulator)) {
            throw new IllegalArgumentException("Asked for the value of a transaction accumulator which was not registered " + accumulator);
        }

        final double[] cache = components.get(transactionsDimension).accumulatorCache();
        final int i = accumulators.indexOf(accumulator);
        if (cache == null) {
            return 0.0;
        } else {
            return cache[i];
        }
    }

    @Override
    public double[] update(final double[] accumulatedValues, final ITransaction item) {

        final double[] result = new double[accumulators.size()];

        for (int i = 0; i < accumulators.size(); i++) {
            if (accumulatedValues == null) {
                result[i] = accumulators.get(i).accum(0.0, item);
            } else {
                result[i] = accumulators.get(i).accum(accumulatedValues[i], item);
            }
        }

        return result;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return ImmutableSet.<IDimension<?>>of(transactionsDimension);
    }
}
