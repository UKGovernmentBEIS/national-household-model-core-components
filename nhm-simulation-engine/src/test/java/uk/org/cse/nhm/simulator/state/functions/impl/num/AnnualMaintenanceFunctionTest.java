package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.obligations.impl.AnnualMaintenanceObligation;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.util.TimeUtil;

public class AnnualMaintenanceFunctionTest {

	private static final double ERROR_DELTA = 0.00001;
	private ITimeDimension time;
	private IDimension<IObligationHistory> obligations;
	private AnnualMaintenanceFunction annualMaintenanceFunction;
	private IComponentsScope scope;
	private IObligationHistory obligationHistory;
	private AnnualMaintenanceObligation maintenanceObligation;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		time = mock(ITimeDimension.class);
		obligations = mock(IDimension.class);
		scope = mock(IComponentsScope.class);
		when(scope.get(time)).thenReturn(TimeUtil.mockTime(new DateTime()));
		obligationHistory = mock(IObligationHistory.class);
		when(scope.get(obligations)).thenReturn(obligationHistory);
		
		maintenanceObligation = mock(AnnualMaintenanceObligation.class);
		
		when(obligationHistory.getObligation(AnnualMaintenanceObligation.class)).thenReturn(Optional.of(maintenanceObligation));
		
		annualMaintenanceFunction = new AnnualMaintenanceFunction(obligations, time);
	}
	
	@Test
	public void shouldListDependencies() {
		Assert.assertEquals("Should list dependencies.", ImmutableSet.of(time, obligations), annualMaintenanceFunction.getDependencies());
	}
	
	@Test
	public void returnsZeroIfNoFurtherMaintenanceDates() {
		when(maintenanceObligation.getNextTransactionDate(any(DateTime.class))).thenReturn(Optional.<DateTime>absent());
		Assert.assertEquals("Should return 0 if no further maintenance dates.",  0.0, annualMaintenanceFunction.compute(scope, ILets.EMPTY), ERROR_DELTA);
	}

	@Test
	public void returnsSumOfMaintenancePaymentsOnNextDate() {
		final DateTime nextPayment = new DateTime();
		when(maintenanceObligation.getNextTransactionDate(any(DateTime.class))).thenReturn(Optional.of(nextPayment));
		
		final Set<IPayment> payments = generatePayments(1.0, 2.0);
		when(maintenanceObligation.generatePayments(nextPayment, scope)).thenReturn(payments);
		Assert.assertEquals("Should return the sum of the payments.", 3.0, annualMaintenanceFunction.compute(scope, ILets.EMPTY), ERROR_DELTA);
		
	}
	
	private Set<IPayment> generatePayments(final double...values) {
		final Set<IPayment> result = new HashSet<IPayment>();
		for(final double value : values) {
			final IPayment payment = mock(IPayment.class);
			when(payment.getAmount()).thenReturn(value);
			result.add(payment);
		}
		return result;
	}
	
	@Test(expected = IllegalStateException.class)
	public void shouldAlwaysFindMaintenanceObligation() {
		when(obligationHistory.getObligation(AnnualMaintenanceObligation.class)).thenReturn(Optional.<AnnualMaintenanceObligation>absent());
		
		annualMaintenanceFunction.compute(scope, ILets.EMPTY);
	}

}
