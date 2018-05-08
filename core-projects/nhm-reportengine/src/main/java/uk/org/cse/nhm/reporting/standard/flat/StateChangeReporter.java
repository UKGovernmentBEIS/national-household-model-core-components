package uk.org.cse.nhm.reporting.standard.flat;

import java.util.List;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.logging.logentry.StateChangeLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;

abstract class StateChangeReporter extends SimpleTabularReporter<StateChangeLogEntry> {

    private final int fieldCount;

    public StateChangeReporter(final IOutputStreamFactory factory, final String name,
            final List<Field> fields
    ) {
        super(factory, StateChangeLogEntry.class, TableType.DWELLING, name, ReportHeaderLogEntry.Type.State);

        final ImmutableList.Builder<Field> fb = ImmutableList.builder();

        fb.add(Field.of("dwelling-id",
                "The unique identifier of the dwelling whose state has changed",
                "ID"));

        fb.add(Field.of("weight",
                "The number of houses represented by this dwelling",
                "Number"));

        fb.add(Field.of("date",
                "The date on which the dwelling's state changed",
                "Date"));

        fb.addAll(fields);

        fieldCount = fields.size() + 3;
        start(fb.build());
    }

    @Override
    protected void doHandle(final StateChangeLogEntry entry) {
        if (interesting(entry)) {
            final String[] row = new String[fieldCount];
            row[0] = entry.getDwellingID() + "";
            row[1] = Float.toString(entry.getWeight());
            row[2] = ReportFormats.getDateAsDay(entry.getDate());
            fill(row, 3, entry);
            write(row);
        }
    }

    protected abstract void fill(final String[] row, final int offset, final StateChangeLogEntry entry);

    protected abstract boolean interesting(final StateChangeLogEntry entry);
}
