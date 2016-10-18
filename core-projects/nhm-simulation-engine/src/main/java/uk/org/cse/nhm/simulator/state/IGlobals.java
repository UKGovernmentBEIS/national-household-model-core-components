package uk.org.cse.nhm.simulator.state;

import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.nhm.simulator.state.impl.GlobalTransactionHistory;

public interface IGlobals {
	public Set<String> getGlobalAccountNames();
	public GlobalTransactionHistory getGlobalAccount(final String accountName);
	public <T> void setVariable(final String global, final T value);
	public <T> Optional<T> getVariable(final String global, final Class<T> type);
	public int getGeneration();
    public boolean copyValues(final Set<String> names, final IGlobals other);
}
