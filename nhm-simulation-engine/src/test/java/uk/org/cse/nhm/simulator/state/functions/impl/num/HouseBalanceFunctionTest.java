package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;

public class HouseBalanceFunctionTest {

	private static final double ERROR_DELTA = 0.000001;
	private IDimension<DwellingTransactionHistory> transactionDimension;
	private HouseBalanceFunction function;
	private IComponentsScope scope;
	private DwellingTransactionHistory transactions;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		transactionDimension = mock(IDimension.class);
		function = new HouseBalanceFunction(transactionDimension);
		scope = mock(IComponentsScope.class);
		transactions = mock(DwellingTransactionHistory.class);
		when(scope.get(transactionDimension)).thenReturn(transactions);
	}
	
	@Test
	public void shouldDeclareDependencies() {
		Assert.assertEquals("Should declare dependencies.", ImmutableSet.of(transactionDimension), function.getDependencies());
	}
	
	@Test
	public void returnsHouseBalance() {
		when(transactions.getBalance()).thenReturn(5.0);
		Assert.assertEquals("Should get the balance from the transaction history.", 5.0, function.compute(scope, ILets.EMPTY), ERROR_DELTA);
	}

}
