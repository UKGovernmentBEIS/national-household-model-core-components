package uk.org.cse.nhm.simulator.scope;

import java.util.Map;

import com.google.common.base.Optional;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.impl.GlobalTransactionHistory;

public interface IComponentsScope extends IScope<IStateChangeSource>, IComponents {
    /**
     * Creates a free-floating hypothetical state about this house, which can never become
     * the truth but can be used to work stuff out
     */
	IHypotheticalComponentsScope createHypothesis();
    /**
     * Get the value of a global variable of the given type
     */
	<T> Optional<T> getGlobalVariable(String string, Class<T> clazz);
    /**
     * Get the bank account for a market / policy / etc
     */
	public GlobalTransactionHistory getGlobalAccount(final String accountName);
    /**
     * Yield a value; yielded values are where "event" scoped variables live.
     */
	public void yield(final String key, final double value);
    /**
     * delete a yielded value.
     */
	void unYield(String key);
    /**
     * Get a yield value (absent if no yield has put one in)
     */
	public Optional<Double> getYielded(final String key);
    /**
     * Get all the yielded values
     */
	public Map<String, Double> getYieldedValues();
    /**
     * Insert many yielded values at once (like calling yield in a loop)
     */
    public void yieldAll(final Map<String,Double> values);

    /**
     * Find the 'prior scope' for this scope.
     * This is a scope for the same house, which is in the parent branch.
     */
    public IComponentsScope getPriorScope();
}
