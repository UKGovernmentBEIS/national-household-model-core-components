package uk.org.cse.nhm.reporting.standard.dwellings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.dwellings.DwellingLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;
import uk.org.cse.nhm.reporting.standard.flat.MultiplexedReporter;
import uk.org.cse.nhm.reporting.standard.flat.ReportFormats;
import uk.org.cse.nhm.reporting.standard.flat.SimpleTabularReporter;
import uk.org.cse.nhm.reporting.standard.flat.TableType;

public class TSVDwellingsReporter extends MultiplexedReporter<DwellingLogEntry> {

    public TSVDwellingsReporter(final IOutputStreamFactory factory) {
        super(DwellingLogEntry.class, factory);
    }

    @Override
    protected String getName(final DwellingLogEntry entry) {
        return entry.getReportName();
    }

    @Override
    protected IReporter createDelegate(final String name, final IOutputStreamFactory factory) {
        return new SimpleTabularReporter<DwellingLogEntry>(factory, DwellingLogEntry.class, TableType.DWELLING, name) {
            boolean builtHeader = false;

            @Override
            protected void doHandle(final DwellingLogEntry entry) {
                if (!builtHeader) {
                    buildHeader(entry.getChangedDwellingData().keySet());
                }
                write(buildRow(entry));
            }

            private String[] buildRow(final DwellingLogEntry logEntry) {
                final List<String> data = new ArrayList<String>();

                data.add(ReportFormats.getDateAsDay(logEntry.getDate()));
                data.add(Integer.toString(logEntry.getDwellingID()));
                data.add(Float.toString(logEntry.getWeight()));

                for (final Entry<String, Object> datum : logEntry.getChangedDwellingData().entrySet()) {
                    final Object value = datum.getValue();
                    if (value instanceof DateTime) {
                        data.add(ReportFormats.getDateAsDay((DateTime) value));
                    } else {
                        data.add(value.toString());
                    }
                }

                return data.toArray(new String[0]);
            }

            private void buildHeader(final Set<String> fields) {
                final ImmutableList.Builder<Field> builder = ImmutableList.builder();

                builder.add(Field.of("date", "The date on which the group's membership changed", "Date"));
                builder.add(Field.of("dwelling-id", "The unique ID of the dwelling which has changed", "Number"));
                builder.add(Field.of("weight", "The number of houses represented by this dwelling", "ID"));

                for (final String field : fields) {
                    if (field.equals(DwellingLogEntry.GROUP_CHANGE)) {
                        builder.add(Field.of(field, "Whether the dwelling has entered or exited the group, or remained within it.", "String"));
                    } else {
                        builder.add(Field.of(field, "The value of " + field + " for the dwelling when the change occurred", "String"));
                    }
                }

                start(builder.build());
            }

            @Override
            protected String getDescription() {
                return "Each row of this table denotes a date when a dwelling change, left the group or entered the group denoted in this table.";
            }
        };
    }
}
