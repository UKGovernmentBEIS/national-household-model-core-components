package uk.org.cse.nhm.logging.logentry;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A log entry which is generated for each simulation step of state change notification.
 */
public class SequenceLogEntry implements IDatedLogEntry {
	private final DateTime date;
	private final String cause;
	private final int created;
	private final int destroyed;
	private final int modified;
	private final long duration;
	private final long randomState;

	@JsonCreator
	public SequenceLogEntry(
			@JsonProperty("randomState") final long randomState,
			@JsonProperty("date") final DateTime date,
			@JsonProperty("duration") final long duration,
			@JsonProperty("cause") final String cause,
			@JsonProperty("created") final int created,
			@JsonProperty("destroyed") final int destroyed,
			@JsonProperty("modified") final int modified) {
		this.randomState = randomState;
		this.date = date;
		this.duration = duration;
		this.cause = cause;
		this.created = created;
		this.destroyed = destroyed;
		this.modified = modified;
	}

	@Override
	public DateTime getDate() {
		return date;
	}

	public String getCause() {
		return cause;
	}

	public int getCreated() {
		return created;
	}

	public int getDestroyed() {
		return destroyed;
	}

	public int getModified() {
		return modified;
	}

	public long getDuration() {
		return duration;
	}
	
	public long getRandomState() {
		return randomState;
	}
}
