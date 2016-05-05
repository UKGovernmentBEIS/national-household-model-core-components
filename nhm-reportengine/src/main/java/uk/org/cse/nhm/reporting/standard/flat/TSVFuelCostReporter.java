package uk.org.cse.nhm.reporting.standard.flat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.logging.logentry.FuelCostsLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.fuel.BiomassFuels;

public class TSVFuelCostReporter extends SimpleTabularReporter<FuelCostsLogEntry> {
	public TSVFuelCostReporter(final IOutputStreamFactory factory) {
		super(factory, FuelCostsLogEntry.class, TableType.DWELLING, "fuel-costs");
		start(fields());
	}

	private static List<Field> fields() {
		final ImmutableList.Builder<Field> builder = ImmutableList.builder();
		
		builder.add(Field.of("dwelling-id", "The unique identifier of the dwelling whose fuel-cost has changed", "ID"));
		builder.add(Field.of("date", "The date on which the dwelling's fuel-cost changed", "Date"));
	
		for (final FuelType ft : BiomassFuels.getNonBiomassFuels()) {
			builder.add(Field.of(ft.toString(), "The cost/year this dwelling spends on " + ft.toString() + " from this date", "Double"));
		}
		builder.add(Field.of("BIOMASS", "The cost/year this dwelling spends on BIOMASS from this date", "Double"));
		return builder.build();
	}


	@Override
	protected void doHandle(final FuelCostsLogEntry logEntry) {
		final List<String> row = new ArrayList<>();
		row.add(logEntry.getDwellingId() + "");
		row.add(ReportFormats.getDateAsYear(logEntry.getDate()));

		final Map<FuelType, Double> costs = logEntry.getFuelCosts();
		for (final FuelType ft : BiomassFuels.getNonBiomassFuels()) {
			row.add(String.format("%.0f", costs.get(ft)));
		}
		row.add(String.format("%.0f", BiomassFuels.getBiomassCost(costs)));
		write(row.toArray(new String[row.size()]));
	}
	
	@Override
	public String getDescription() {
		return "This table gives the fuel costs experienced by each dwelling as a rate of payment, broken down by the type of fuel and service. " +
				"Each row determines the annual cost for a dwelling from the associated date until the next entry for that " +
				"dwelling in the table. To get the total cost for a period of time for a dwelling, these values need integrating.";
	}
}
