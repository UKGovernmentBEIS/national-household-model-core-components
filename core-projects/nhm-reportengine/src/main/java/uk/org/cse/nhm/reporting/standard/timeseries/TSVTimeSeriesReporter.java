package uk.org.cse.nhm.reporting.standard.timeseries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;
import uk.org.cse.nhm.reporting.standard.flat.MultiplexedReporter;
import uk.org.cse.nhm.reporting.standard.flat.ReportFormats;
import uk.org.cse.nhm.reporting.standard.flat.SimpleTabularReporter;
import uk.org.cse.nhm.reporting.standard.flat.TableType;

public class TSVTimeSeriesReporter extends MultiplexedReporter<AggregateLogEntry> {

    private static final Joiner CAUSE_JOINER = Joiner.on("; ");
    private static final String DATE = "Date";
    private static final String CAUSES = "Causes";

    public TSVTimeSeriesReporter(IOutputStreamFactory factory) {
        super(AggregateLogEntry.class, factory);
    }

    @Override
    protected IReporter createDelegate(final String reportName, IOutputStreamFactory factory) {
        return new SimpleTabularReporter<AggregateLogEntry>(
                factory,
                AggregateLogEntry.class,
                TableType.AGGREGATE,
                reportName) {

            boolean builtHeader = false;

            @Override
            protected void doHandle(AggregateLogEntry entry) {
                if (!builtHeader) {
                    buildHeader(entry);
                }

                write(buildRow(entry));

            }

            private String[] buildRow(AggregateLogEntry entry) {
                List<String> data = new ArrayList<>();

                data.add(ReportFormats.getDateAsDay(entry.getDate()));

                for (Entry<String, String> e : entry.getReducedRowKey().entrySet()) {
                    data.add(e.getValue());
                }

                for (Entry<String, Double> e : entry.getColumns().entrySet()) {
                    data.add(Double.toString(e.getValue()));
                }

                data.add(CAUSE_JOINER.join(entry.getCauses()));

                return data.toArray(new String[0]);
            }

            private void buildHeader(AggregateLogEntry example) {
                ImmutableList.Builder<Field> columns = ImmutableList.builder();

                columns.add(Field.of(DATE, "The date from which each series had the given values", "Date"));

                for (Entry<String, String> e : example.getReducedRowKey().entrySet()) {
                    columns.add(Field.of(e.getKey(), "A category which dwellings have been grouped by.", "String"));
                }

                for (Entry<String, Double> e : example.getColumns().entrySet()) {
                    columns.add(Field.of(e.getKey(), "A number which was produced by aggregating over many dwellings.", "Decimal"));
                }
                example.getColumns();

                columns.add(Field.of(CAUSES, "The names of things which have caused changes to the given values", "String"));

                start(columns.build());
                builtHeader = true;
            }

            @Override
            protected String getDescription() {
                return "The tabular data from the user-defined aggregate report " + reportName;
            }
        };
    }

    @Override
    protected String getName(AggregateLogEntry entry) {
        return entry.getName();
    }

}
