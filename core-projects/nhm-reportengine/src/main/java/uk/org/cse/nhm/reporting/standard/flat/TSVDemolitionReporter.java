package uk.org.cse.nhm.reporting.standard.flat;

import java.util.Map;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.logging.logentry.ConstructAndDemoLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;

/**
 * CSEDemolitionReportBuilder.
 *
 * @author richardt
 * @version $Id: CSEDemolitionReportBuilder.java 94 2010-09-30 15:39:21Z
 * richardt
 * @since 0.0.1-SNAPSHOT
 */
public class TSVDemolitionReporter extends SimpleTabularReporter<ConstructAndDemoLogEntry> {

    public TSVDemolitionReporter(final IOutputStreamFactory factory) {
        super(factory, ConstructAndDemoLogEntry.class, TableType.AGGREGATE, "demolition", ReportHeaderLogEntry.Type.HouseCount);

        final ImmutableList.Builder<Field> fields = ImmutableList.builder();

        fields.add(Field.of("Date", "The date at which the number of houses changed", "Date"));

        for (final RegionType t : RegionType.values()) {
            fields.add(Field.of(t.toString(),
                    "The number of houses in " + t.toString() + " on the given date",
                    "Integer"
            ));
        }

        start(fields.build());
    }

    @Override
    protected void doHandle(final ConstructAndDemoLogEntry entry) {
        final String[] row = new String[RegionType.values().length + 1];

        int col = 0;
        row[col++] = ReportFormats.getDateAsYear(entry.getDate());

        final Map<RegionType, Integer> weights = entry.getDwellingWeightByRegion();
        for (final RegionType t : RegionType.values()) {
            row[col++] = "" + weights.get(t);
        }

        write(row);
    }

    @Override
    public String getDescription() {
        return "This report gives the number of houses by region by date. Each row shows how many houses "
                + "there were in each region from a particular date until the next row.";
    }
}
