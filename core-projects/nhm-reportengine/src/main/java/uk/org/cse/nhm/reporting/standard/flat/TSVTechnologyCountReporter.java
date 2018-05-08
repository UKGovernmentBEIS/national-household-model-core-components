package uk.org.cse.nhm.reporting.standard.flat;

import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.TechnologyDistributionLog;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;

public class TSVTechnologyCountReporter extends SimpleTabularReporter<TechnologyDistributionLog> {

    private boolean started;

    public TSVTechnologyCountReporter(final IOutputStreamFactory factory) {
        super(factory, TechnologyDistributionLog.class, TableType.AGGREGATE, "technology-distribution");
        this.started = false;
    }

    private final SortedSet<String> header = new TreeSet<String>();

    @Override
    protected void doHandle(final TechnologyDistributionLog logEntry) {
        if (!started) {
            header.addAll(logEntry.getCaseWeightByTechnology().keySet());

            final ImmutableList.Builder<Field> builder = ImmutableList.builder();

            builder.add(Field.of("Date", "The date for the given technology counts", "Date"));

            for (final String s : header) {
                builder.add(Field.of(s, "The count of technology " + s + " in the housing stock from the given date", "Integer"));
            }

            start(builder.build());
            started = true;
        }

        final String[] result = new String[header.size() + 1];

        result[0] = ReportFormats.getDateAsYear(logEntry.getDate());
        int i = 1;
        for (final String s : header) {
            final Integer value = logEntry.getCaseWeightByTechnology().get(s);
            if (value != null) {
                result[i++] = value.toString();
            } else {
                result[i++] = "0";
            }
        }

        write(result);
    }

    @Override
    public String getDescription() {
        return "Each row of this table shows the coutn of each kind of technology that are present in the stock from a given year until the next year shown";
    }
}
