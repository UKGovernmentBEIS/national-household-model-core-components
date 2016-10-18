package uk.org.cse.nhm.simulator.state.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.simulator.transactions.ITransaction;

/**
 * Checks that global accounts branch and merge correctly with states
 * 
 * @author hinton
 *
 */
public class BranchingAccountsTest {
	private Globals globals;
	private static final int QUANTUM = 400;

	@Before
	public void setup() {
		globals = new Globals(QUANTUM);
	}
	
	
	@Test
	public void accountsAreCreatedIdempotently() {
		final GlobalTransactionHistory globalAccount = globals.getGlobalAccount("test");
		final GlobalTransactionHistory globalAccount2 = globals.getGlobalAccount("test");
		
		Assert.assertSame("method call should be idempotent", globalAccount, globalAccount2);
		
		// check same in branch state
		
		final Globals branch = globals.branch();
		
		final GlobalTransactionHistory globalAccount3 = branch.getGlobalAccount("test2");
		final GlobalTransactionHistory globalAccount4 = branch.getGlobalAccount("test2");
		
		Assert.assertSame("method call should be idempotent in branch", globalAccount3, globalAccount4);
		
		Assert.assertNotSame("however, distinct accounts should be unrelated", globalAccount, globalAccount3);
		
		Assert.assertNotSame("furthermore, a branched account should be distinct, even with the same key", globalAccount, branch.getGlobalAccount("test"));
	}
	
	@Test
	public void branchedAccountsReadParentsHistoryButParentsDoNotReadBranch() {
		// create a state and put a transaction in it
		final GlobalTransactionHistory globalAccount = globals.getGlobalAccount("test");
		
		final ITransaction transaction = mock(ITransaction.class, "transaction in root");
		when(transaction.getAmount()).thenReturn(1.0);
		when(transaction.isForDwelling()).thenReturn(true);
		globalAccount.receive(transaction);
		
		
		// branch the state
		final Globals branch = globals.branch();
		
		final GlobalTransactionHistory globalAccountInBranch = branch.getGlobalAccount("test");
		
		// check the historical transaction is present
		Assert.assertEquals("the old transaction should be in the branch", 1.0 * QUANTUM, globalAccountInBranch.getBalance(), 0.0);
		
		// add a non-historical transaction
		final ITransaction transaction2 = mock(ITransaction.class, "transaction in branch");
		when(transaction2.getAmount()).thenReturn(2.0);
		when(transaction2.isForDwelling()).thenReturn(true);
		globalAccountInBranch.receive(transaction2);
		
		// check that the non-historical transaction is present in the branch but not history
		Assert.assertEquals("Still the same amount in the root account", 1.0 * QUANTUM, globalAccount.getBalance(), 0.0);
		Assert.assertEquals("Amount in branched account has changed.", 3.0 * QUANTUM, globalAccountInBranch.getBalance(), 0.0);
	}
	
	@Test
	public void mergedStatesHaveTransactionsFromBranch() {	
		final Globals branch = globals.branch();
		final GlobalTransactionHistory globalAccountInBranch = branch.getGlobalAccount("test");
		final ITransaction transaction = mock(ITransaction.class);
		when(transaction.getAmount()).thenReturn(1.0);
		when(transaction.isForDwelling()).thenReturn(true);
		globalAccountInBranch.receive(transaction);
		
		globals.merge(branch);
		
		Assert.assertEquals("The root state is updated with the branch balance after merge.", 1.0 * QUANTUM, globals.getGlobalAccount("test").getBalance(), 0.0);
	}
}
