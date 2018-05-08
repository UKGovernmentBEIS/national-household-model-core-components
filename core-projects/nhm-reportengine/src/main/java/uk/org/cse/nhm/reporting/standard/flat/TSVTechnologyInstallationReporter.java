package uk.org.cse.nhm.reporting.standard.flat;

import java.text.DecimalFormat;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.logging.logentry.TechnologyInstallationLogEntry;
import uk.org.cse.nhm.logging.logentry.TechnologyInstallationRecord;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;

/**
 * Builds the installation log report using the data from the log provided by
 * InstallationDetailsLogger.
 *
 * @author tomw
 *
 */
public class TSVTechnologyInstallationReporter extends SimpleTabularReporter<TechnologyInstallationLogEntry> {

    public TSVTechnologyInstallationReporter(final IOutputStreamFactory factory) {
        super(factory, TechnologyInstallationLogEntry.class, TableType.DWELLING, "installationLog", ReportHeaderLogEntry.Type.InstallationLog);

        final ImmutableList.Builder<Field> builder = ImmutableList.builder();

        builder.add(Field.of("dwelling-id", "The unique ID of the dwelling in which a technology was installed", "ID"));
        builder.add(Field.of("weight", "The weight of the dwelling in which a technology was installed", "Number"));
        builder.add(Field.of("Date", "The date on which the technology was installed", "Date"));
        builder.add(Field.of("Technology", "The name of the technology which was installed", "String"));
        builder.add(Field.of("CAPEX", "The capital cost of purchasing the technology", "Double"));
        builder.add(Field.of("Size", "The installed size of the technology", "Double"));
        builder.add(Field.of("Units", "The units for the size column", "String"));

        start(builder.build());
    }

    @Override
    public String getDescription() {
        return "Each row of this table describes the installation of a technology in a particular dwelling.";
    }

    @Override
    protected void doHandle(final TechnologyInstallationLogEntry logEntry) {
        final DecimalFormat df = new DecimalFormat("#.####################");

        for (final TechnologyInstallationRecord record : logEntry.getRecords()) {
            final String[] row = new String[7];

            int col = 0;
            row[col++] = String.format("%d", logEntry.getDwellingID());
            row[col++] = Float.toString(logEntry.getWeight());
            row[col++] = logEntry.getDate().toString();
            row[col++] = record.getTechnologyName();
            row[col++] = df.format(record.getInstallationCost());
            row[col++] = df.format(record.getQuantityInstalled());
            row[col++] = record.getUnits().toString();

            write(row);
        }
    }
}
