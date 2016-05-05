package uk.org.cse.nhm.simulator.transactions;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionHistoryTest {
	private static final double ERROR_DELTA = 0.000001;
	private ITransactionRunningTotal runningTotals;

	@Before
	public void setup() {
		runningTotals = mock(ITransactionRunningTotal.class);
	}
	
	@Test
	public void keepsTrackOfBalance() {
		final DwellingTransactionHistory t = new DwellingTransactionHistory(runningTotals);
		
		t.pay(mockTransaction(0.0));
		assertBalance(t, 0.0);
		
		t.pay(mockTransaction(1.0));
		assertBalance(t, -1.0);
		
		t.pay(mockTransaction(-2.0));
		assertBalance(t, 1.0);
	}

	@Test
	public void balancePreservedByBranch() {
		DwellingTransactionHistory t = new DwellingTransactionHistory(runningTotals);
		t.pay(mockTransaction(1.0));
		assertBalance(t, -1.0);
		
		t = t.branch();
		assertBalance(t, -1.0);
	}
	
	@Test
	public void updatesCacheFromRunningTotals() {
		final ITransaction t = mockTransaction(1.0);
		when(runningTotals.update(null, t)).thenReturn(new double[]{1.0});
		
		final DwellingTransactionHistory history = new DwellingTransactionHistory(runningTotals);
		history.pay(t);
		Assert.assertEquals("Should have asked for and stored the value from the running totals.", 1.0, history.accumulatorCache()[0], 0.0);
		
	}

	private void assertBalance(final DwellingTransactionHistory transactions, final double amount) {
		Assert.assertEquals("Transaction history balance should match " + amount, amount, transactions.getBalance(), ERROR_DELTA);
	}

	private ITransaction mockTransaction(final double amount) {
		final ITransaction t = mock(ITransaction.class);
		when(t.getAmount()).thenReturn(amount);
		return t;
	}
}
