package uk.org.cse.nhm.simulator.transactions;

import javax.inject.Inject;

import com.google.inject.Provider;

public class DefaultTransactionHistoryProvider implements Provider<DwellingTransactionHistory>{
	private final ITransactionRunningTotal runningTotals;

	@Inject
	public DefaultTransactionHistoryProvider(final ITransactionRunningTotal runningTotals) {
		this.runningTotals = runningTotals;
	}

	@Override
	public DwellingTransactionHistory get() {
		return new DwellingTransactionHistory(runningTotals);
	}
}
