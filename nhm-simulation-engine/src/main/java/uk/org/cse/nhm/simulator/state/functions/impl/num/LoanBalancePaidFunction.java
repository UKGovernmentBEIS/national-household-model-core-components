package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.ILoan;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.transactions.ITransactionRunningTotal;

public class LoanBalancePaidFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final String creditor;
	private final String tagged;
	private final IDimension<DwellingTransactionHistory> transactionsDimension;
	private final ITransactionRunningTotal transactionRunningTotals;
	private final Accumulator accumulator;

	@AssistedInject
	public LoanBalancePaidFunction(
			final ITransactionRunningTotal transactionRunningTotals,
			final IDimension<DwellingTransactionHistory> transactionsDimension,
			@Assisted("creditor") @Nullable final String creditor, 
			@Assisted("tagged") @Nullable final String tagged) {
		this.transactionRunningTotals = transactionRunningTotals;
		this.transactionsDimension = transactionsDimension;
		this.creditor = creditor;
		this.tagged = tagged;
		
		accumulator = new Accumulator();
		transactionRunningTotals.registerAccumulator(accumulator);
	}
	
	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		return transactionRunningTotals.getAccumulatedValue(accumulator, scope);
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(transactionsDimension);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
	
	class Accumulator implements ITransactionRunningTotal.ITransactionAccumulator {

		@Override
		public double accum(final double existingValue, final ITransaction transaction) {
			if(isLoan(transaction)) {
				if(passesFilter(transaction)) {
					return existingValue + transaction.getAmount();
				}
			}
			return existingValue;
		}
		
		private boolean passesFilter(final ITransaction tran) {
			return (creditor == null || creditor.equals(tran.getPayee()))
					&& (tagged == null || tran.getTags().contains(tagged));
		}

		private boolean isLoan(final ITransaction tran) {
			return tran.getTags().contains(ILoan.Tags.Repayment);
		}
	}
}
