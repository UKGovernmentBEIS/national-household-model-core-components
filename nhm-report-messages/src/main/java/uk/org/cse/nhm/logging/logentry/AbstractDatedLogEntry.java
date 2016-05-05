package uk.org.cse.nhm.logging.logentry;

import org.joda.time.DateTime;

abstract class AbstractDatedLogEntry extends AbstractLogEntry implements IDatedLogEntry {
	private final DateTime date;

	protected AbstractDatedLogEntry(DateTime date) {
		this.date = date;
	}
	
	@Override
	public final DateTime getDate() {
		return date;
	}
}
