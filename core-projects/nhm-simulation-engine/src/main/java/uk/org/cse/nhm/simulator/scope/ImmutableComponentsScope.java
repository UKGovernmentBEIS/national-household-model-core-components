package uk.org.cse.nhm.simulator.scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;

import uk.org.cse.commons.names.Name;
import uk.org.cse.commons.scopes.impl.ImmutableScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.impl.GlobalTransactionHistory;

public class ImmutableComponentsScope extends ImmutableScope<IStateChangeSource> implements IComponentsScope {

    private static final class System implements IStateChangeSource {

        @Override
        public StateChangeSourceType getSourceType() {
            return StateChangeSourceType.INTERNAL;
        }

        @Override
        public Name getIdentifier() {
            return Name.of("System thinking - no change happened.");
        }
    }

    private final ScopeFactory factory;

    private static final System SYSTEM = new System();
    private final IState state;
    private final IDwelling dwelling;
    private final Map<String, Double> yieldedValues;

    /*
	 * For use by @{link ScopeFactory}
     */
    ImmutableComponentsScope(final ScopeFactory factory, final IState state, final IDwelling dwelling) {
        this(factory, state, dwelling, new HashMap<String, Double>());
    }

    ImmutableComponentsScope(final ScopeFactory factory, final IState state, final IDwelling dwelling, final Map<String, Double> yields) {
        super(SYSTEM);
        this.factory = factory;
        this.state = state;
        this.dwelling = dwelling;
        yieldedValues = yields;
    }

    @Override
    public <T> T get(final IDimension<T> dimension) {
        return state.get(dimension, dwelling);
    }

    @Override
    public int getDwellingID() {
        return dwelling.getID();
    }

    @Override
    public IHypotheticalComponentsScope createHypothesis() {
        return factory.createHypotheticalScope(this, getTag(), state.hypotheticalBranch(), dwelling, new HashMap<>(yieldedValues));
    }

    @Override
    public <T> Optional<T> getGlobalVariable(final String string, final Class<T> clazz) {
        return state.getGlobals().getVariable(string, clazz);
    }

    @Override
    public IDwelling getDwelling() {
        return dwelling;
    }

    @Override
    public GlobalTransactionHistory getGlobalAccount(final String accountName) {
        return state.getGlobals().getGlobalAccount(accountName);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && yieldedValues.isEmpty();
    }

    @Override
    public void yield(final String key, final double value) {
        yieldedValues.put(key, value);
    }

    @Override
    public void unYield(final String key) {
        yieldedValues.remove(key);
    }

    @Override
    public Optional<Double> getYielded(final String key) {
        return Optional.fromNullable(yieldedValues.get(key));
    }

    @Override
    public Map<String, Double> getYieldedValues() {
        return Collections.unmodifiableMap(yieldedValues);
    }

    @Override
    public void yieldAll(final Map<String, Double> values) {
        yieldedValues.putAll(values);
    }

    @Override
    public IState getState() {
        return state;
    }

    @Override
    public IComponentsScope getPriorScope() {
        return state.getPriorState().detachedScope(dwelling);
    }
}
