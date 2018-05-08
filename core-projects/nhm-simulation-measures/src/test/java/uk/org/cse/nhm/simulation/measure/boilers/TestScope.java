package uk.org.cse.nhm.simulation.measure.boilers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.commons.scopes.IScopeVisitor;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.impl.GlobalTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.IPayment;

public class TestScope implements ISettableComponentsScope {

    @Override
    public IHypotheticalComponentsScope createHypothesis() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void yield(final String key, final double value) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public Optional<Double> getYielded(final String var) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public IStateChangeSource getTag() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void addNote(final Object note) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public <T> List<T> getLocalNotes(final Class<T> noteClass) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public <T> Optional<T> getLocalNote(final Class<T> noteClass) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void accept(final IScopeVisitor<? super IStateChangeSource> visitor) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public <T> Optional<T> getNearestNote(final Class<T> noteClass) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public <T> List<T> getAllNotes(final Class<T> noteClass) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public List<? extends IScope<? extends IStateChangeSource>> getSubScopes() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public boolean isClosed() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public <T> T get(final IDimension<T> dimension) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public int getDwellingID() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public <T> void modify(final IDimension<T> dimension, final IModifier<T> operation) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public boolean apply(final IComponentsAction action, final ILets lets) throws NHMException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public boolean apply(final Set<IComponentsAction> actions, final ILets lets, final IPicker picker) throws NHMException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public boolean applyInSequence(final List<IComponentsAction> actions, final ILets lets, final boolean requireSuccess) throws NHMException {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void addObligation(final IObligation obligation) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public void addTransaction(final IPayment payment) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public <T> Optional<T> getGlobalVariable(final String string, final Class<T> clazz) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public <T> void setGlobalVariable(final String string, final T value) {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public IDwelling getDwelling() {
        throw new UnsupportedOperationException("Not Implemented");
    }

    @Override
    public GlobalTransactionHistory getGlobalAccount(final String accountName) {
        throw new UnsupportedOperationException("no");
    }

    @Override
    public Map<String, Double> getYieldedValues() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unYield(final String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void yieldAll(final Map<String, Double> values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IBranch getState() {
        throw new UnsupportedOperationException();
    }

    @Override
    public IComponentsScope getPriorScope() {
        throw new UnsupportedOperationException();
    }
}
