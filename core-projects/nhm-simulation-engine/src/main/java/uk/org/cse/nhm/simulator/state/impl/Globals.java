package uk.org.cse.nhm.simulator.state.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

import uk.org.cse.nhm.simulator.state.IGlobals;

class Globals implements IGlobals {
	private final Map<String, Object> variables = new HashMap<>();
	private final Map<String, GlobalTransactionHistory> accounts = new HashMap<>();
	
	private final int quantum;
	
	private int variableGeneration;
	
	private static void mergeAccounts(
			final Map<String, GlobalTransactionHistory> roots,
			final Map<String, GlobalTransactionHistory> children) {
		for (final Map.Entry<String, GlobalTransactionHistory> entry : children.entrySet()) {
			final GlobalTransactionHistory rootForEntry = roots.get(entry.getKey());
			if (rootForEntry == null) {
				roots.put(entry.getKey(), entry.getValue());
			} else {
				rootForEntry.merge(entry.getValue());
			}
		}
	}
	
	Globals(final int quantum) {
		this(quantum, 0);
	}
	
	private Globals(final int quantum, final int variableGeneration) {
		this.quantum = quantum;
		this.variableGeneration = variableGeneration;
	}
	
	Globals branch() {
		final Globals copy = new Globals(quantum, variableGeneration);
		
		for (final Map.Entry<String, GlobalTransactionHistory> e : accounts.entrySet()) {
			copy.accounts.put(e.getKey(), e.getValue().branch());
		}
		
		copy.variables.putAll(variables);
		
		return copy;
	}

    public boolean copyValues(final Set<String> names, final IGlobals other) {
        boolean changed = false;
    	for (final String s : names) {
    		final Object newValue = other.getVariable(s, Object.class).orNull();
            if (!Objects.equal(newValue, variables.put(s, newValue))) changed = true;
        }
    	if (changed) {
    		variableGeneration++;
    	}
    	return changed;
    }
	
	void merge(final IGlobals child) {
		final Globals _child = (Globals) child;
		mergeAccounts(accounts, _child.accounts);
		variables.putAll(_child.variables);
		variableGeneration = _child.variableGeneration;
	}
	
	@Override
	public GlobalTransactionHistory getGlobalAccount(final String accountName) {
		if(!accounts.containsKey(accountName)) {
			accounts.put(accountName, new GlobalTransactionHistory());
		}
		return accounts.get(accountName);
	}

	@Override
	public <T> void setVariable(final String global, final T value) {
		variables.put(global, value);
		variableGeneration++;
	}

	@Override
	public <T> Optional<T> getVariable(final String global, final Class<T> type) {
		return Optional.fromNullable(type.cast(variables.get(global)));
	}

	@Override
	public Set<String> getGlobalAccountNames() {
		return accounts.keySet();
	}

	@Override
	public int getGeneration() {
		int total = variableGeneration;
		for (final GlobalTransactionHistory a : accounts.values()) { 
			total += a.getGeneration();
		}
		return total;
	}

	@Override
	public String toString() {
		return String.format("Variables : %s Accounts: %s", variables, accounts);
	}
}
