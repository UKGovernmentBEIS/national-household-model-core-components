package uk.org.cse.nhm.simulator.transactions;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;

/**
 * Does not keep track of any running totals.
 */
public class DummyTransactionRunningTotal implements ITransactionRunningTotal {
	private static final ITransactionRunningTotal INSTANCE = new DummyTransactionRunningTotal(); 
	
	private DummyTransactionRunningTotal(){}
	
	@Override
	public void registerAccumulator(final ITransactionAccumulator accumulator) {
		// No-op
	}

	@Override
	public double getAccumulatedValue(final ITransactionAccumulator accumulator,
			final IComponents components) {
		return 0;
	}

	@Override
	public double[] update(final double[] accumulatedValues, final ITransaction item) {
		return accumulatedValues;
	}
	
	public static final ITransactionRunningTotal instance() {
		return INSTANCE;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.of();
	}
}
