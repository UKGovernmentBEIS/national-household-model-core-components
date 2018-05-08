package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.ITransactionRunningTotal;
import uk.org.cse.nhm.simulator.transactions.ITransactionRunningTotal.ITransactionAccumulator;

public class SumOfTransactionsFunctionTest {

    private ITransactionRunningTotal runningTotals;
    private static final Predicate<Collection<String>> NONE = Predicates.alwaysTrue();

    @Before
    public void setup() {
        runningTotals = mock(ITransactionRunningTotal.class);
    }

    @Test
    public void takesDependenciesFromRunningTotal() {
        final ImmutableSet<IDimension<?>> dependencies = ImmutableSet.<IDimension<?>>of(mock(IDimension.class));
        when(runningTotals.getDependencies()).thenReturn(dependencies);
        Assert.assertEquals("Should take dependencies from running totals.", dependencies,
                new SumOfTransactionsFunction(runningTotals, Optional.<Glob>absent(), NONE).getDependencies());
    }

    @Test
    public void registersAccumulator() {
        new SumOfTransactionsFunction(runningTotals, Optional.of(Glob.of("counterparty")),
                Glob.requireAndForbid(ImmutableSet.of(Glob.of("required"), Glob.of("!forbidden"))));
        final ArgumentCaptor<ITransactionAccumulator> accumulatorCaptor = ArgumentCaptor.forClass(ITransactionRunningTotal.ITransactionAccumulator.class);
        verify(runningTotals, times(1)).registerAccumulator(accumulatorCaptor.capture());

        final ITransactionAccumulator accumulator = accumulatorCaptor.getValue();

        Assert.assertEquals("Should ignore transaction with wrong counterparty.", 0.0, accumulator.accum(0.0, mockTransaction("wrong", "required")), 0.0);
        Assert.assertEquals("Should ignore transaction without required tag.", 0.0, accumulator.accum(0.0, mockTransaction("counterparty")), 0.0);
        Assert.assertEquals("Should ignore transaction with forbidden tag.", 0.0, accumulator.accum(0.0, mockTransaction("counterparty", "required", "forbidden")), 0.0);
        Assert.assertEquals("Should accumulate transaction.", 1.0, accumulator.accum(0.0, mockTransaction("counterparty", "required")), 0.0);
    }

    private ITransaction mockTransaction(final String counterparty, final String... tags) {
        final ITransaction t = mock(ITransaction.class);
        when(t.getAmount()).thenReturn(1.0);
        when(t.getPayee()).thenReturn(counterparty);
        when(t.getTags()).thenReturn(ImmutableSet.copyOf(tags));
        return t;
    }

    @Test
    public void getsValueFromRunningTotal() {
        final IComponentsScope scope = mock(IComponentsScope.class);
        when(runningTotals.getAccumulatedValue(any(ITransactionAccumulator.class), any(IComponents.class))).thenReturn(1.0);
        Assert.assertEquals("Should have looked up the value from the running totals", 1.0, new SumOfTransactionsFunction(runningTotals, Optional.<Glob>absent(), NONE).compute(scope, ILets.EMPTY), 0.0);
    }
}
