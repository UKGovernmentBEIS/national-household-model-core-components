package uk.org.cse.nhm.reporting.standard;

import org.joda.time.format.DateTimeFormat;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.SequenceLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.flat.SimpleTabularReporter;
import uk.org.cse.nhm.reporting.standard.flat.TableType;

public class TSVSequenceReporter extends SimpleTabularReporter<SequenceLogEntry> {
	public TSVSequenceReporter(final IOutputStreamFactory factory) {
		super(factory, SequenceLogEntry.class, TableType.META, "sequence");
		start(ImmutableList.of(
				Field.of("Date", "The simulation date on which the event happened.", "Date"),
				Field.of("Duration", "The real time in seconds which this event took.", "Integer"),
				Field.of("Cause", "The action which took place.", "String"),
				Field.of("Created", "The number of dwellings which were created.", "Integer"),
				Field.of("Destroyed", "The number of dwellings which were demolished.", "Integer"),
				Field.of("Changed", "The number of dwellings which were changed.", "Integer"),
				Field.of("Random", "The state of the random number generator.", "Integer")
				));
	}

	@Override
	protected void doHandle(final SequenceLogEntry entry) {
		write(
				DateTimeFormat.shortDate().print(entry.getDate()),
				Long.toString(entry.getDuration()),
				entry.getCause(),
				Integer.toString(entry.getCreated()),
				Integer.toString(entry.getDestroyed()),
				Integer.toString(entry.getModified()),
				Long.toString(entry.getRandomState()));
	}

	@Override
	protected String getDescription() {
		return "Lists the events which occurred during the simulation.";
	}
}
