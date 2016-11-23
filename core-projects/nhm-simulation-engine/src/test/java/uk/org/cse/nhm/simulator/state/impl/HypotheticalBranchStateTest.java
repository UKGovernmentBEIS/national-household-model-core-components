package uk.org.cse.nhm.simulator.state.impl;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.scope.ScopeFactory;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IHypotheticalBranch;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class HypotheticalBranchStateTest {

	private IDimension<IObligationHistory> obligationDimension;
	private IDimension<DwellingTransactionHistory> transactionDimension;
	private ITimeDimension timeDimension;
	private ScopeFactory scopeFactory;

	private IDwelling dwelling;
	private Object thing;
	private IInternalDimension<Object> dimension;

	private CanonicalState canonicalState;

	private HypotheticalBranchState hypothesisBranch;

	private IModifier<Object> modifier;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		obligationDimension = mock(IDimension.class);
		transactionDimension = mock(IDimension.class);
		timeDimension = mock(ITimeDimension.class);

		scopeFactory = new ScopeFactory(obligationDimension, transactionDimension, timeDimension);

		dwelling = mock(IDwelling.class);
		thing = mock(Object.class);
		dimension = mock(IInternalDimension.class);

		when(dimension.copy(dwelling)).thenReturn(thing);
		when(dimension.set(dwelling, thing)).thenReturn(true);
		when(dimension.branch(
				(IBranch)any(),
				anyInt()
			)
		).thenReturn(dimension);

		canonicalState = new CanonicalState(new DimensionCounter(), scopeFactory, 0, 0);
		canonicalState.setDimensions(ImmutableSet.<IInternalDimension<?>>of(dimension));
		canonicalState.dimensionCounter.next();

		hypothesisBranch = new HypotheticalBranchState(canonicalState);



		modifier = mock(IModifier.class);
		when(modifier.modify(any())).thenReturn(true);
	}

	@Test
	/*
	 * Example of how to trigger:
		(under
           (counterfactual.carbon)
           (do
               (measure.storage-heater))
           evaluate: 1)
	 */
	public void replaceThenBranchThenMerge() {
		hypothesisBranch.replaceDimension(dimension, dimension);
		final IHypotheticalBranch childHypothesis = hypothesisBranch.hypotheticalBranch();

		childHypothesis.modify(dimension, dwelling, modifier);

		hypothesisBranch.merge(childHypothesis);

		verify(dimension, never()).merge(eq(dwelling), eq(dimension));
	}
}
