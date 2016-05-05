package uk.org.cse.nhm.simulator.scope;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IGlobals;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.impl.GlobalTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.ITransaction;
import uk.org.cse.nhm.simulator.util.RandomSource;
import uk.org.cse.nhm.simulator.util.TimeUtil;

/*
 * ComponentsScopeTest is somewhat convoluted, since we only get access to ComponentsScopes by first applying an IComponentsAction in an IStateScope.
 */
public class ComponentsScopeTest {

	private static final double ERROR_DELTA = 0.00001;
	protected final IBranch branch = mock(IBranch.class, "main branch");
	private IDwelling dwelling;
	private int dwellingId;
	private IComponentsAction tagAction;
	protected TestScope scope;
	private IDimension<DwellingTransactionHistory> transactionDimension;
	private DwellingTransactionHistory transactionHistoryForDwelling;
	private GlobalTransactionHistory transactionHistoryForAccount;
	private IGlobals globals;
	private ITimeDimension time;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		dwellingId = 1;
		dwelling = mock(IDwelling.class);
		when(dwelling.getID()).thenReturn(dwellingId);
		tagAction = mock(IComponentsAction.class);
		transactionDimension = mock(IDimension.class);
		time = mock(ITimeDimension.class);
		transactionHistoryForDwelling = mock(DwellingTransactionHistory.class);
		when(branch.get(time, dwelling)).thenReturn(TimeUtil.mockTime(new DateTime(0)));
		when(branch.get(transactionDimension, dwelling)).thenReturn(transactionHistoryForDwelling);
		
		transactionHistoryForAccount = mock(GlobalTransactionHistory.class);
		
		globals = mock(IGlobals.class);
		when(branch.getRandom()).thenReturn(new RandomSource(0));
		when(branch.getGlobals()).thenReturn(globals);
		
		when(globals.getGlobalAccount("test")).thenReturn(transactionHistoryForAccount);
		
		scope = new TestScope(transactionDimension,time, tagAction, branch, dwelling, new HashMap<String, Double>());
	}

	@Test
	public void getDwellingIdShouldComeFromScopeDwelling() {
		Assert.assertEquals("Dwelling id should be correct", dwellingId, scope.getDwellingID());
	}
	
	@Test
	public void getTagShouldComeFromAction() {
		Assert.assertSame("Tag should come from action", tagAction, scope.getTag());
	}
	
	@Test
	public void branchShouldBePassedInBranch() {
		Assert.assertSame("Branch should be passed in branch.", branch, scope.getBranch());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void failsToAddTransactionAsNote() {
		scope.addNote(mock(ITransaction.class));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void addsTransactionWhereNeeded() {
		final IPayment payment = mock(IPayment.class);
		
		when(payment.getPayee()).thenReturn("test");
		final Set<String> tags = Collections.singleton("test tag");
		when(payment.getTags()).thenReturn(tags);
		when(payment.getAmount()).thenReturn(1.0);
		
		scope.addTransaction(payment);
		
		final ArgumentCaptor<IModifier> captor = ArgumentCaptor.forClass(IModifier.class);
		verify(branch).modify(eq(transactionDimension), eq(dwelling), captor.capture());
		
		captor.getValue().modify(transactionHistoryForDwelling);
		
		final ArgumentCaptor<ITransaction> transactionArg = ArgumentCaptor.forClass(ITransaction.class);
		verify(transactionHistoryForDwelling, times(1)).pay(transactionArg.capture());
		final ITransaction transaction = transactionArg.getValue();
		
		Assert.assertEquals("Transaction should have correct payee.", "test", transaction.getPayee());
		Assert.assertEquals("Transaction should use dwelling to create payer name.", "dwelling " + dwelling.getID(), transaction.getPayer());
		Assert.assertEquals("Transaction should have correct tags.", tags, transaction.getTags());
		Assert.assertEquals("Transaction should have correct amount.", 1.0, transaction.getAmount(), ERROR_DELTA);
	}
	
	@Test
	public void directlyYieldedValuesAreReturnedWithNoChildren() {
		scope.yield("test", 1434d);
		Assert.assertEquals(1434d, scope.getYielded("test").get(), 0d);
	}
	
	@Test
	public void valuesYieldedIntoChildrenBubbleUpUnlessReplaced() {
		Assert.assertFalse("No value is present to start with", scope.getYielded("test").isPresent());
		
		scope.apply(new IComponentsAction() {
			
			@Override
			public StateChangeSourceType getSourceType() {
				return null;
			}
			
			@Override
			public boolean isAlwaysSuitable() {
				return false;
			}
			
			@Override
			public Name getIdentifier() {
				return Name.of("test");
			}
			
			@Override
			public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
				return true;
			}
			
			@Override
			public boolean apply(final ISettableComponentsScope scope, final ILets lets)
					throws NHMException {
				scope.yield("test", 8d);
				return true;
			}
		}, ILets.EMPTY);
		
		Assert.assertEquals("After making 8 in a child scope, we get 8", 8d, scope.getYielded("test").get(), 0d);
		
		scope.yield("test", 1234d);
		
		Assert.assertEquals("After putting 1234 in top scope we get that", 1234d, scope.getYielded("test").get(), 0d);
	}
	
	@Test
	public void returnsFalseIfAllActionsFiltered() {
		final IComponentsAction mockAction = mock(IComponentsAction.class);
		when(mockAction.apply(any(ISettableComponentsScope.class), any(ILets.class))).thenReturn(true);
		scope.apply(ImmutableSet.of(mockAction), ILets.EMPTY, new IPicker(){
			@Override
			public PickOption pick(final RandomSource source, final Set<PickOption> options) {
				return null;
			}});
	}
}
