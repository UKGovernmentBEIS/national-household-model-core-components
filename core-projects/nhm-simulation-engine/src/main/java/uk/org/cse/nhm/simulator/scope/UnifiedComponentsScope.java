package uk.org.cse.nhm.simulator.scope;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.commons.scopes.IScopeVisitor;
import uk.org.cse.commons.scopes.impl.Scope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.impl.GlobalTransactionHistory;

class UnifiedComponentsScope implements IComponentsScope {
    private final Scope<IStateChangeSource> scope;
    private final IComponentsScope components;

    public UnifiedComponentsScope(final Scope<IStateChangeSource> scope, final IComponentsScope components) {
        this.scope = scope;
        this.components = components;
    }
    
    @Override public <T> T get(final IDimension<T> dimension) {                            return components.get(dimension);    }
	@Override public int getDwellingID() {                                                 return components.getDwellingID();    }
    @Override public IDwelling getDwelling() {                                             return components.getDwelling();    }
    @Override public IState getState() {                                                   return components.getState();    }
	@Override public IHypotheticalComponentsScope createHypothesis() {                     return components.createHypothesis();    }
    @Override public <T> Optional<T> getGlobalVariable(String string, Class<T> clazz) {    return components.getGlobalVariable(string, clazz);    }
    @Override public GlobalTransactionHistory getGlobalAccount(final String accountName) { return components.getGlobalAccount(accountName);    }
    @Override public void yield(final String key, final double value) {                    components.yield(key, value);    }
    @Override public void unYield(String key) {                                            components.unYield(key);    }
    @Override public Optional<Double> getYielded(final String key) {                       return components.getYielded(key);    }
    @Override public Map<String, Double> getYieldedValues() {                              return components.getYieldedValues();    }
    @Override public void yieldAll(final Map<String,Double> values) {                      components.yieldAll(values);    }

	@Override public IStateChangeSource getTag() {
        return scope.getTag();
    }
	@Override public void addNote(final Object note) {
        scope.addNote(note);
    }
	@Override public <T> List<T> getLocalNotes(final Class<T> noteClass) {
        return scope.getLocalNotes(noteClass);
    }
	@Override public <T> Optional<T> getLocalNote(final Class<T> noteClass) {
        return scope.getLocalNote(noteClass);
    }
	@Override public void accept(final IScopeVisitor<? super IStateChangeSource> visitor) {
        scope.accept(visitor);
    }
	@Override public <T> Optional<T> getNearestNote(final Class<T> noteClass) {
        return scope.getNearestNote(noteClass);
    }
	@Override public <T> List<T> getAllNotes(final Class<T> noteClass) {
        return scope.getAllNotes(noteClass);
    }
	@Override public List<? extends IScope<? extends IStateChangeSource>> getSubScopes() {
        return scope.getSubScopes();
    }
	@Override public boolean isClosed() {
        return scope.isClosed();
    }
	@Override public boolean isEmpty() {
        return scope.isEmpty();
    }
    @Override public IComponentsScope getPriorScope() {
        return components.getPriorScope();
    }
}
