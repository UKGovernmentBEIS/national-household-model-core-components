package uk.org.cse.nhm.simulator.action.finance.obligations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class PeriodicPaymentFactoryTest {
	private static final ILets NO_VARS = ILets.EMPTY;
	private static final DateTime NOW = new DateTime(0);
	private ICanonicalState state;
	private IDwelling dwelling;
	private IComponentsScope scope;

	@Before
	public void setup() {
		state = mock(ICanonicalState.class);
		scope = mock(IComponentsScope.class);
		
		dwelling = mock(IDwelling.class);
		
		when(state.detachedScope(dwelling)).thenReturn(scope);
		
	}
	
	@Test
	public void infiniteSchedule() {
		final IPaymentSchedule schedule = new PeriodicPaymentFactory(Period.years(1), Optional.<Period>absent(), Optional.<IComponentsFunction<Boolean>>absent())
			.getSchedule(state, NOW, NO_VARS, dwelling);
		
		Optional<DateTime> current = Optional.of(NOW);
		int counter = 0;
		while ((current = schedule.getNextTransactionDate(current.get())).isPresent()) {
			counter++;
			if (counter > 1000) {
				return;
			}
			
			Assert.assertTrue("Should have transactions on the dates it told us about " + current.get(), schedule.transactionStillValid(current.get()));
		}
		
		Assert.fail("Infinite schedule should never end.");
	}
	
	@Test
	public void timeLimitedSchedule() {
		final IPaymentSchedule schedule = new PeriodicPaymentFactory(Period.years(1), Optional.of(Period.years(3)), Optional.<IComponentsFunction<Boolean>>absent())
		.getSchedule(state, NOW, NO_VARS, dwelling);
		
		Optional<DateTime> current = Optional.of(NOW);
		int counter = 0;
		
		while ((current = schedule.getNextTransactionDate(current.get())).isPresent()) {
			counter++;
			
			Assert.assertTrue("Should have transactions on the dates it told us about " + current.get(), schedule.transactionStillValid(current.get()));
		}
		
		Assert.assertEquals("Should have generated 1 transaction after each year.", 3, counter);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void conditionLimitedSchedule() {
		final IComponentsFunction<Boolean> test = mock (IComponentsFunction.class);
		when(test.compute(scope, ILets.EMPTY)).thenReturn(true);
		
		final IPaymentSchedule schedule = new PeriodicPaymentFactory(Period.years(1), Optional.<Period>absent(), Optional.of(test))
		.getSchedule(state, NOW, NO_VARS, dwelling);
		
		Optional<DateTime> current = Optional.of(NOW);
		current = schedule.getNextTransactionDate(current.get());
		Assert.assertTrue("Should continue to generate transactions while the test is true.", schedule.getNextTransactionDate(current.get()).isPresent());
		Assert.assertTrue("Should have transactions on the dates it told us about.", schedule.transactionStillValid(current.get()));
		
		when(test.compute(scope, ILets.EMPTY)).thenReturn(false);
		
		Assert.assertFalse("Should cease generating transactions once the test has failed.", schedule.getNextTransactionDate(current.get()).isPresent());
	}
}
