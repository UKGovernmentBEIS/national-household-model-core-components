package uk.org.cse.nhm.reporting.standard.flat;

import java.text.DecimalFormat;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.logging.logentry.MeasureCostLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;

/**
 * Builds the measure cost report using the data from the log provided by
 * uk.org.cse.nhm.simulator.logger.loggers.impl.MeasureCostLogger
 * 
 * @author tomw
 *
 */
public class TSVMeasureCostReporter extends SimpleTabularReporter<MeasureCostLogEntry> {
	
	private final int columnCount;

	public TSVMeasureCostReporter(final IOutputStreamFactory factory) {
		super(factory, MeasureCostLogEntry.class, TableType.AGGREGATE, "measureCosts",
				ReportHeaderLogEntry.Type.MeasureCosts
				);

		final ImmutableList.Builder<Field> builder = ImmutableList.builder();
		
		 builder.add(Field.of("Date", "The date of change", "Date"));
		 builder.add(Field.of("Technology", "The technology that is installed", "String"));
		 builder.add(Field.of("Source", "The cause of installation of the technology", "String"));
		 builder.add(Field.of("Units", "The units for the size columns", "String"));
		 builder.add(Field.of("Count", "The number of installs", "Integer"));

		addVariableColumns(builder, "OPEX", "operational cost");
		addVariableColumns(builder, "CAPEX", "capital cost");
		addVariableColumns(builder, "Size", "size");
		
		final ImmutableList<Field> build = builder.build();
		
		this.columnCount = build.size();
		start(build);
	}
	
	private static void addVariableColumns(
			final ImmutableList.Builder<Field> builder,
			final String shortName,
			final String longName) {
		builder.add(Field.of(shortName + " Mean", "The mean " + longName
				+ " of all installations", "Double"));
		builder.add(Field.of(shortName + "" + " Variance", "The variance in "
				+ longName + " of all installations", "Double"));
		builder.add(Field.of(shortName + " Max", "The maximum " + longName
				+ " of all installations", "Double"));
		builder.add(Field.of(shortName + " Min", "The minimum " + longName
				+ " of all installations", "Double"));
		builder.add(Field.of(shortName + " Total", "The total " + longName
				+ " of all installations", "Double"));
	}
	
	@Override
	protected void doHandle(final MeasureCostLogEntry logEntry) {
		final DecimalFormat df = new DecimalFormat( "#.####################" );
        final String[] row = new String[columnCount];

        int col = 0;
        row[col++] = logEntry.getDate().toString();
        row[col++] = logEntry.getTechnology().toString();
        row[col++] = logEntry.getSource().toString();
        row[col++] = logEntry.getUnits();
        row[col++] = df.format(logEntry.getCount());
        
        row[col++] = df.format(logEntry.getOpex().getMean());
        row[col++] = df.format(logEntry.getOpex().getVariance());
        row[col++] = df.format(logEntry.getOpex().getMax());
        row[col++] = df.format(logEntry.getOpex().getMin());
        row[col++] = df.format(logEntry.getOpex().getSum());

        row[col++] = df.format(logEntry.getCapex().getMean());
        row[col++] = df.format(logEntry.getCapex().getVariance());
        row[col++] = df.format(logEntry.getCapex().getMax());
        row[col++] = df.format(logEntry.getCapex().getMin());
        row[col++] = df.format(logEntry.getCapex().getSum());

        row[col++] = df.format(logEntry.getSizeInstalled().getMean());
        row[col++] = df.format(logEntry.getSizeInstalled().getVariance());
        row[col++] = df.format(logEntry.getSizeInstalled().getMax());
        row[col++] = df.format(logEntry.getSizeInstalled().getMin());
        row[col++] = df.format(logEntry.getSizeInstalled().getSum());

        write(row);
	}

	@Override
	public String getDescription() {
		return "This table summarises costs, sizing and installation counts for different technologies.";
	}
}
