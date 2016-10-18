package uk.org.cse.nhm.simulator.obligations.impl;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.util.TimeUtil;

public class FixedRepaymentLoanObligationTest {
	private static final double ERROR_DELTA = 0.00001;

	@Test
	public void testAnnualRepaymentWithoutTilt() {
		double annualRepayment = FixedInterestLoanObligation.getInitialAnnualRepayment(10, 0.01,0, 1);
		Assert.assertEquals("In one year, you just pay the interest and principal at the end", 10 * (1+0.01), annualRepayment, 0.001);
		
		annualRepayment = FixedInterestLoanObligation.getInitialAnnualRepayment(10, 0.01,0, 2);
		Assert.assertEquals("In two years, you pay slightly less than half of 1.01^2 times the principal", 
				5.075, annualRepayment, 0.001);
	}
	
	@Test
	public void testAnnualRepaymentWithTilt() {
		/**
		 * These are the first year repayments for a loan of 1 with a term of (array index+1) taken
		 * from the spreadsheet provided by DECC.
		 * 
		 * Since our implementation is independently derived, the fact that this test passes is indicative
		 * of it being right; note that the spreadsheet contains an error due to the failure to apply
		 * l'hopital's rule when interest rate = tilt rate.
		 */
		final double[] numbersFromSpreadsheet = new double[]
				{
				1.0696,
				0.5475,
				0.3736,
				0.2867,
				0.2347,
				0.2001,
				0.1754,
				0.1569,
				0.1426,
				0.1312,
				0.1219,
				0.1142,
				0.1077,
				0.1021,
				0.0974,
				0.0932,
				0.0895,
				0.0863,
				0.0835,
				0.0809
				};
		
		for (int term = 1; term <= numbersFromSpreadsheet.length; term++) {
			final double annualRepayment = 
					FixedInterestLoanObligation.getInitialAnnualRepayment(1, 0.0696, 0.02, term);
			Assert.assertEquals("term = " + term, numbersFromSpreadsheet[term-1], annualRepayment, 0.001);
		}
	}
	
	@Test
	public void testObligationGeneratesTiltedPayments() {
		for (final double tilt : new double[] {-0.1, 0, 0.1, 0.06}) {
			final List<IPayment> payments = generatePayments(tilt);
			IPayment previous = null;
			for (final IPayment p : payments) {
				Assert.assertEquals("Payee is test (constructor argument 3)", "test", p.getPayee());
				Assert.assertEquals("Tags are hello (constructor arg 4)", ImmutableSet.of("hello"), p.getTags());
				if (previous != null) {
					Assert.assertEquals("tilt is applied", previous.getAmount() * (1+tilt), p.getAmount(), 0.001);
				}
				previous = p;
			}
		}
	}
	
	@Test
	public void testObligationGeneratesPayments() {
		final List<IPayment> payments = generatePayments(0);
		
		// OK, now let's check payments are OK
		Assert.assertEquals("A ten year loan is paid in ten installments", 10, payments.size());
		
		for (final IPayment p : payments) {
			Assert.assertEquals("Payee is test (constructor argument 3)", "test", p.getPayee());
			Assert.assertEquals("Tags are hello (constructor arg 4)", ImmutableSet.of("hello"), p.getTags());
			Assert.assertEquals("Amount is correct, and constant", 679.33, p.getAmount(), 0.01);
		}
	}

	private List<IPayment> generatePayments(final double tilt) {
		final ITimeDimension time = mock(ITimeDimension.class);
		final DateTime birthday = new DateTime();
		final FixedInterestLoanObligation loan = new FixedInterestLoanObligation(time,
				mock(ICanonicalState.class), 
				mock(ISimulator.class), 
				birthday, "test", ImmutableSet.of("hello"), 10, 0, 5000, 0.06, tilt);
		final List<DateTime> allTransactionDates = new ArrayList<DateTime>();
		Optional<DateTime> next = Optional.of(birthday);
		final IComponentsScope state = mock(IComponentsScope.class);
		final List<IPayment> payments = new ArrayList<IPayment>();
		when(state.get(time)).thenReturn(TimeUtil.mockTime(birthday));
		while ((next = loan.getNextTransactionDate(next.get())).isPresent()) {
			allTransactionDates.add(next.get());
			when(state.get(time)).thenReturn(TimeUtil.mockTime(next.get()));
			payments.addAll(loan.generatePayments(next.get(), state));
		}
		Assert.assertEquals("A ten year loan has ten payment dates", 10, allTransactionDates.size());
		
		// insert birthday for next bit of test
		allTransactionDates.add(0, birthday);
		
		for (int i = 0; i<allTransactionDates.size() -1; i++) {
			final DateTime fst = allTransactionDates.get(i);
			final DateTime snd = allTransactionDates.get(i+1);

            Assert.assertEquals("Dates are 1 year apart", fst.plusYears(1), snd);
		}
		return payments;
	}
	
	@Test
	public void outstandingIncludesInterestAndDropsToZeroOverPaymentTime() {
		final ITimeDimension time = mock(ITimeDimension.class);
		
		final FixedInterestLoanObligation loan = new FixedInterestLoanObligation(
				time, 
				mock(ICanonicalState.class), 
				mock(ISimulator.class), 
				year(0), "payee", ImmutableSet.of("tag"), 2, 0, 1.0, 1.0, 0);
		
		// Loan principal of 1.0.
		// 1st year: interest of 100% increases to 2.0; payment of 4/3 remaining 2/3.
		// 2nd year: interest of 100% increases to 4/3; payment of 4/3 remaining 0.0.
		// Total amount paid 8/3.
		
		Assert.assertEquals("Full balance plus interest outstanding before any repayments.", 8.0/3.0, loan.getOutstandingBalance(year(0)), ERROR_DELTA);
		Assert.assertEquals("Half balance outstanding after half the repayments.", 4.0/3.0, loan.getOutstandingBalance(year(1)), ERROR_DELTA);
		Assert.assertEquals("No outstanding balance once loan has been paid off.", 0.0, loan.getOutstandingBalance(year(2)), ERROR_DELTA);
		Assert.assertEquals("No outstanding balance in future after loan is paid.", 0.0, loan.getOutstandingBalance(year(3)), ERROR_DELTA);
	}
	
	private DateTime year(final int years) {
		return new DateTime(0).plusYears(years);
	}
}
