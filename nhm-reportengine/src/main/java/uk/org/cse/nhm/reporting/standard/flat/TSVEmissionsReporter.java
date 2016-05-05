package uk.org.cse.nhm.reporting.standard.flat;

import java.util.List;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.logging.logentry.StateChangeLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;

public class TSVEmissionsReporter extends StateChangeReporter {
	public TSVEmissionsReporter(final IOutputStreamFactory factory) {
		super(factory, "emissions", fields());
	}

	private static List<Field> fields() {
		final ImmutableList.Builder<Field> builder = ImmutableList.builder();
		
		for (final FuelType ft : FuelType.values()) {
			builder.add(Field.of(ft.toString(),
					"The emissions rate of this dwelling associated with use of " + ft.toString() + ", in kg/year CO2 equivalent",
					"Decimal"
					));
		}
		
		return builder.build();
	}

	@Override
	protected boolean interesting(final StateChangeLogEntry entry) {
		return entry.getEmissions() != null;
	}
	
	@Override
	protected void fill(final String[] row, final int offset, final StateChangeLogEntry entry) {
		for (final FuelType ft : FuelType.values()) {
			row[offset+ft.ordinal()] = String.format("%f", entry.getEmissions().getEmissions(ft));
		}
	}
	
	@Override
	protected String getDescription() {
		return "This table gives the emissions experienced by each dwelling as a rate emission, broken down by the type of fuel and service. " +
				"Each row determines the emission rate for a dwelling from the associated date until the next entry for that " +
				"dwelling in the table. To get the emissinos for a period of time for a dwelling, these values need integrating.";
	}
}
