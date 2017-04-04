package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import uk.org.cse.nhm.simulator.obligations.ILoan;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.ITransactionRunningTotal;
import uk.org.cse.nhm.simulator.transactions.ITransactionRunningTotal.ITransactionAccumulator;

public class LoanBalancePaidFunctionTest {

	private static final double ERROR_DELTA = 0.000001;
	private IDimension<DwellingTransactionHistory> transactionsDimension;
	private IComponentsScope scope;
	private DwellingTransactionHistory transactions;
	private ITransactionRunningTotal runningTotals;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		transactionsDimension = mock(IDimension.class);
		scope = mock(IComponentsScope.class);
		transactions = mock(DwellingTransactionHistory.class);
		when(scope.get(transactionsDimension)).thenReturn(transactions);
		
		runningTotals = mock(ITransactionRunningTotal.class);
	}
	
	@Test
	public void shouldDeclareDependencies() {
		final LoanBalancePaidFunction function = new LoanBalancePaidFunction(runningTotals, transactionsDimension, null, null);
		Assert.assertEquals("Function should return its dependencies.", ImmutableSet.of(transactionsDimension), function.getDependencies());
	}
	
	@Test
	public void sumsAcrossTransactionsFromLoans() {
		final List<ITransaction> transactionList = ImmutableList.of(
				mockTransaction(1.0, null),
				mockTransaction(2.0, null),
				mockTransaction(4.0, null)
				);
		
		final ArgumentCaptor<ITransactionAccumulator> accumulatorCaptor = ArgumentCaptor.forClass(ITransactionRunningTotal.ITransactionAccumulator.class);
		new LoanBalancePaidFunction(runningTotals, transactionsDimension, null, null);
		
		verify(runningTotals, times(1)).registerAccumulator(accumulatorCaptor.capture());
		final ITransactionAccumulator accumulator = accumulatorCaptor.getValue();
		
		double accum = 0.0;
		for (final ITransaction t : transactionList) {
			accum = accumulator.accum(accum, t);
		}
		
		Assert.assertEquals("Should sum loan transactions from transaction history.", 7.0, accum, ERROR_DELTA);
	}
	
	@Test
	public void filtersBasedOnPayeeAndTagged() {
		final List<ITransaction> transactionList = ImmutableList.of(
				mockTransaction(1.0, "wrong payee", "tag", "another tag"),
				mockTransaction(2.0, "payee", "wrong tag", "another tag"),
				mockTransaction(4.0, "payee", "tag", "another tag")
				);
		
		new LoanBalancePaidFunction(runningTotals, transactionsDimension, "payee", "tag");
		
		final ArgumentCaptor<ITransactionAccumulator> accumulatorCaptor = ArgumentCaptor.forClass(ITransactionRunningTotal.ITransactionAccumulator.class);
		verify(runningTotals, times(1)).registerAccumulator(accumulatorCaptor.capture());
		final ITransactionAccumulator accumulator = accumulatorCaptor.getValue();
		
		double accum = 0.0;
		for (final ITransaction t : transactionList) {
			accum = accumulator.accum(accum, t);
		}
		
		Assert.assertEquals("Should sum loan transactions from transaction history.", 4.0, accum, ERROR_DELTA);
	}
	
	private ITransaction mockTransaction(final double value, final String payee, final String...tags) {
		final ITransaction tran = mock(ITransaction.class);
		when(tran.getAmount()).thenReturn(value);
		when(tran.getPayee()).thenReturn(payee);
		final Builder<String> builder = ImmutableSet.builder();
		for(final String tag : tags) {
			builder.add(tag);
		}
		builder.add(ILoan.Tags.Repayment);
		when(tran.getTags()).thenReturn(builder.build());
		return tran;
	}
}
