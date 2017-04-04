package uk.org.cse.nhm.logging.logentry;

import java.util.UUID;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;

/**
 * A special log entry which is published by the system to itself.
 * @author hinton
 *
 */
public class SimulationProgressLogEntry implements ISimulationLogEntry {
	private final UUID uuid;
	private final String message;
	private final double progress;
	private final DateTime completion;
	
	@JsonCreator
	public SimulationProgressLogEntry(
			@JsonProperty("uuid") final UUID uuid,
			@JsonProperty("message") final String message, 
			@JsonProperty("progress") final double progress,
			@JsonProperty("completion") final DateTime completion) {
		super();
		this.message = message;
		this.progress = progress;
		this.uuid = uuid;
		this.completion = completion;
	}
	
	public String getMessage() {
		return message;
	}
	
	public double getProgress() {
		return progress;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public DateTime getCompletion() {
		return completion;
	}
}
