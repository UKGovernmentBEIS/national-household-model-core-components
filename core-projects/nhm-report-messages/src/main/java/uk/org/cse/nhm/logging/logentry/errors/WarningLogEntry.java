package uk.org.cse.nhm.logging.logentry.errors;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;

public class WarningLogEntry implements ISimulationLogEntry {
	private final String warning;
	private final Map<String, String> data;

	@JsonCreator
	public WarningLogEntry(
			@JsonProperty("warning") final String warning,
			@JsonProperty("data") final Map<String, String> data
			) {
		this.warning = warning;
		this.data = data;
	}
	
	public String getWarning() {
		return this.warning;
	}
	
	public Map<String, String> getData() {
		return this.data;
	}
}
