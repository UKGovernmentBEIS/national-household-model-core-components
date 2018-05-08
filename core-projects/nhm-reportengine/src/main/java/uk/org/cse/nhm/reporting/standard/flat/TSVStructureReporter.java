package uk.org.cse.nhm.reporting.standard.flat;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.StateChangeLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;

public class TSVStructureReporter extends StateChangeReporter {

    public TSVStructureReporter(final IOutputStreamFactory factory) {
        super(factory, "structure",
                ImmutableList.of(
                        Field.of("ID", "The id in the attributes table that describes the condition of this dwelling on this date", "ID")
                ));
    }

    @Override
    protected boolean interesting(StateChangeLogEntry entry) {
        return entry.getBasicAttributes() != null;
    }

    @Override
    protected void fill(String[] row, int offset, StateChangeLogEntry entry) {
        row[offset] = "" + entry.getBasicAttributes().getId();
    }

    @Override
    protected String getDescription() {
        return "Describes the change in the state of houses over time as those houses are changed. "
                + "Joining this to the 'housing-stock-attributes' table on attributes-id produces a table with "
                + "dwelling-id, date and a lot of attributes. Each row indicates that the condition of the dwelling with that id "
                + "changed on that date to match the associated attributes.";
    }
}
