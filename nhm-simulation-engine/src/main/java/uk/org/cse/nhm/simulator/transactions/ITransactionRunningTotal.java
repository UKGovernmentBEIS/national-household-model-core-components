package uk.org.cse.nhm.simulator.transactions;

import java.util.Set;

import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;

/**
 * We don't want to keep all the historical transactions for performance reasons. Instead, ask this class for a particular calculation at the start of the simulation, and it will keep it up to date.
 */
public interface ITransactionRunningTotal {
	/**
	 * @param accumulator a function which updates a running total. 
	 */
	void registerAccumulator(ITransactionAccumulator accumulator);
	
	/**
	 * @param token the token which was returned by registerAccumulator.
	 * @param components the state of a dwelling to get the value from.
	 * @return the accumulated value. 
	 */
	double getAccumulatedValue(ITransactionAccumulator accumulator, IComponents components);
	
	/**
	 * @param runningTotals the current running totals.
	 * @param item the new transaction.
	 * @return a new array containing the updated totals.
	 */
	double[] update(double[] accumulatedValues, ITransaction item);
	
	public interface ITransactionAccumulator {
		double accum(double existingValue, ITransaction transaction);
	}

	Set<IDimension<?>> getDependencies();
}
