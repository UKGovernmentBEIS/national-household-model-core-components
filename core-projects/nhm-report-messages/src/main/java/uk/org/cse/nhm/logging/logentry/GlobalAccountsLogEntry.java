package uk.org.cse.nhm.logging.logentry;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@AutoProperty
public class GlobalAccountsLogEntry extends AbstractLogEntry {
	private final Map<String, Double> accountBalances;
	private final DateTime date;
	
	@JsonCreator
	public GlobalAccountsLogEntry(
			@JsonProperty("date") DateTime date, 
			@JsonProperty("accountBalances") Map<String, Double> accountBalances) {
		this.date = date;
		this.accountBalances = new HashMap<String, Double>(accountBalances);
	}
	
	public DateTime getDate() {
		return date;
	}
	
	public Map<String, Double> getAccountBalances() {
		return accountBalances;
	}
	

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public boolean equals(Object obj) {
		return Pojomatic.equals(this, obj);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
}
