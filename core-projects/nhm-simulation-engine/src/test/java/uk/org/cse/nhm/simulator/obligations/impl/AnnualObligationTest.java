package uk.org.cse.nhm.simulator.obligations.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Random;
import java.util.Set;

import javax.management.timer.Timer;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.Payment;
import uk.org.cse.nhm.simulator.util.TimeUtil;

public class AnnualObligationTest {
	private AnnualObligationT obligation;
	private ITimeDimension time;
	private ICanonicalState state;
	private ISimulator sim;

	/**
	 * Special testable version of annual obligation
	 * @author hinton
	 *
	 */
	private static class AnnualObligationT extends AnnualObligation {
		

		protected AnnualObligationT(final ITimeDimension time, 
				final ICanonicalState state, final ISimulator simulator) {
			super(time, state, simulator, 0);
		}


		@Override
		protected Set<IPayment> doGeneratePayments(final IComponentsScope state, final ILets lets) {
			return ImmutableSet.of(Payment.of("test", 101, ImmutableSet.of("A", "B")));
		}
	}
	
	@Before
	public void setup() {
		time = mock(ITimeDimension.class);
		state = mock(ICanonicalState.class);
		sim = mock(ISimulator.class);
		obligation = new AnnualObligationT(time, state, sim);
	}
	
	/**
	 * This test cannot be comprehensive, as the obligation will generate an infinite sequence of firing dates
	 */
	@Test
	public void obligationTriggersEachYearForManyYears() {
		final Random r = new Random();
		for (int i = 0; i < 1000; i++) {
			final DateTime date = new DateTime(r.nextInt(1000) + 2000, DateTimeConstants.JANUARY, 4, 4, 5, 6, 7);
			final Optional<DateTime> after = obligation.getNextTransactionDate(date);
			Assert.assertTrue("An annual obligation is due every year", after.isPresent());
			final DateTime theDate = after.get();
			Assert.assertEquals("The year of the next firing should be the current year (in most cases)",
					date.getYear(), theDate.getYear());
			
			Assert.assertEquals("The firing should be at the very end of that year",
					DateTimeConstants.DECEMBER, theDate.getMonthOfYear());
			
			Assert.assertEquals("The firing should be at the very end of that year",
					31, theDate.getDayOfMonth());
			
			Assert.assertEquals("The firing should be at the very end of that year",
					Timer.ONE_DAY - 1, theDate.getMillisOfDay());
		}
	}
	
	@Test
	public void obligationCallsSubclassGeneratePaymentsMethodOnItsTriggerDate() {
		final Random r = new Random();
		final IComponentsScope componentsScope = mock(IComponentsScope.class);
		final DateTime date = new DateTime(r.nextLong());
		
		final DateTime nextDate = obligation.getNextTransactionDate(date).get();
		when(componentsScope.get(time)).thenReturn(TimeUtil.mockTime(nextDate));
		
		final Collection<IPayment> payments = obligation.generatePayments(nextDate, componentsScope);
		
		Assert.assertEquals("We generate one payment per year in the subclass",
				1, payments.size());
		
		Assert.assertEquals("The payment should be as returned from subclass", "test", payments.iterator().next().getPayee());
		Assert.assertEquals("The payment should be as returned from subclass", 101d, payments.iterator().next().getAmount(), 0.001);
		Assert.assertEquals("The payment should be as returned from subclass", ImmutableSet.of("A", "B"), payments.iterator().next().getTags());
	}
	
	@Test
	public void obligationSkipsSubclassGeneratePaymentsOnOtherDates() {
		final IComponentsScope componentsScope = mock(IComponentsScope.class);
		final DateTime date = new DateTime(2001, 3, 4, 5, 1, 2, 3);
		when(componentsScope.get(time)).thenReturn(TimeUtil.mockTime(date));
		
		Assert.assertTrue("Payments should be empty on other dates",
				obligation.generatePayments(date, componentsScope).isEmpty());
	}
}
