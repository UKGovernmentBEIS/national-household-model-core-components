package uk.org.cse.nhm.reporting.standard.probes;

import java.util.LinkedHashSet;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.ProbeLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;
import uk.org.cse.nhm.reporting.standard.flat.MultiplexedReporter;
import uk.org.cse.nhm.reporting.standard.flat.SimpleTabularReporter;
import uk.org.cse.nhm.reporting.standard.flat.TableType;

public class ProbeReporter extends MultiplexedReporter<ProbeLogEntry> {

    public ProbeReporter(final IOutputStreamFactory factory) {
        super(ProbeLogEntry.class, factory);
    }

    private static class NamedProbeReporter extends SimpleTabularReporter<ProbeLogEntry> {

        private boolean started = false;
        private final LinkedHashSet<String> fields = new LinkedHashSet<String>();

        public NamedProbeReporter(final IOutputStreamFactory factory, final String name) {
            super(factory, ProbeLogEntry.class, TableType.DWELLING, "probe-" + name);
        }

        @Override
        protected void doHandle(final ProbeLogEntry entry) {
            if (!started) {
                started = true;

                final ImmutableList.Builder<Field> builder = ImmutableList.builder();

                fields.addAll(entry.getFields().keySet());

                builder.add(Field.of("Sequence", "The sequence number - this is the real order in which things happen", "Integer"));
                builder.add(Field.of("weight", "The weight of this dwelling", "Number"));
                builder.add(Field.of("Date", "The simulation date", "Date"));
                builder.add(Field.of("dwelling-id", "The unique identifier for the probed dwelling", "ID"));

                for (final String s : fields) {
                    builder.add(Field.of(s, "A captured value", "Misc"));
                }

                start(builder.build());
            }

            final String[] row = new String[4 + fields.size()];
            row[0] = "" + entry.getSequence();
            row[1] = String.format("%f", entry.getWeight());
            row[2] = formatDate(entry.getDate());
            row[3] = "" + entry.getHouseID();

            int i = 4;
            for (final String s : fields) {
                final Object v = entry.getFields().get(s);
                if (v == null) {
                    row[i++] = "n/a";
                } else {
                    row[i++] = format(v);
                }
            }

            write(row);
        }

        @Override
        protected String getDescription() {
            return "Logs the output of a probe in the scenario";
        }

        private String format(final Object v) {
            if (v instanceof DateTime) {
                return formatDate((DateTime) v);
            } else if (v instanceof Double) {
                return String.format("%.3f", v);
            } else {
                return "" + v;
            }
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd");

        private String formatDate(final DateTime date) {
            return fmt.print(date);
        }
    }

    @Override
    protected IReporter createDelegate(final String name,
            final IOutputStreamFactory factory) {
        return new NamedProbeReporter(factory, name);
    }

    @Override
    protected String getName(final ProbeLogEntry entry) {
        return entry.getName();
    }
}
