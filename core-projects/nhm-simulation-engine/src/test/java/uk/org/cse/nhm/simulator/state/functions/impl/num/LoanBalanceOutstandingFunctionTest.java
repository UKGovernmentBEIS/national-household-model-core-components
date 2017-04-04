package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.ILoan;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.util.TimeUtil;

public class LoanBalanceOutstandingFunctionTest {

	private static final double ERROR_DELTA = 0.000001;
	private IDimension<IObligationHistory> obligationsDim;
	private ITimeDimension timeDim;
	private IComponentsScope scope;
	private IObligationHistory obligations;
	private LoanBalanceOutstandingFunction function;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		obligationsDim = mock(IDimension.class);
		timeDim = mock(ITimeDimension.class);
		scope = mock(IComponentsScope.class);
		obligations = mock(IObligationHistory.class);
		when(scope.get(obligationsDim)).thenReturn(obligations);
		when(scope.get(timeDim)).thenReturn(TimeUtil.mockTime(new DateTime()));
		function = new LoanBalanceOutstandingFunction(obligationsDim, timeDim, null, null);
	}
	
	@Test
	public void shouldDeclareDependencies() {
		Assert.assertEquals("Function should return its dependencies.", ImmutableSet.of(obligationsDim, timeDim), function.getDependencies());
	}
	
	@Test
	public void sumsAcrossLoans() {
		final List<ILoan> loans = ImmutableList.of(
				mockLoan(2.0, null), 
				mockLoan(3.0, null));
		
		when(obligations.getObligations(ILoan.class)).thenReturn(loans);
		Assert.assertEquals("Returns sum of outstanding loan balances.", 5.0, function.compute(scope, ILets.EMPTY), ERROR_DELTA);
	}
	
	@Test
	public void filtersBasedOnPayeeAndTagged() {
		final List<ILoan> loans = ImmutableList.of(
				mockLoan(1.0, "wrong payee", "tag", "another tag"),
				mockLoan(2.0, "payee", "wrong tag", "another tag"),
				mockLoan(4.0, "payee", "tag", "another tag"));
		
		function = new LoanBalanceOutstandingFunction(obligationsDim, timeDim, "payee", "tag");
		
		when(obligations.getObligations(ILoan.class)).thenReturn(loans);
		Assert.assertEquals("Returns sum of outstanding loan balances.", 4.0, function.compute(scope, ILets.EMPTY), ERROR_DELTA);
	}
	
	private ILoan mockLoan(final double value, final String payee, final String...tags) {
		final ILoan loan = mock(ILoan.class);
		when(loan.getOutstandingBalance(any(DateTime.class))).thenReturn(value);
		when(loan.getPayee()).thenReturn(payee);
		when(loan.getTags()).thenReturn(ImmutableSet.copyOf(tags));
		return loan;
	}
}
