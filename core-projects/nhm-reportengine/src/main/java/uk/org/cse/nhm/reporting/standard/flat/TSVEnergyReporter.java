package uk.org.cse.nhm.reporting.standard.flat;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.definition.enums.XServiceType;
import uk.org.cse.nhm.logging.logentry.StateChangeLogEntry;
import uk.org.cse.nhm.logging.logentry.components.EnergyLogComponent;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.fuel.BiomassFuels;

public class TSVEnergyReporter extends StateChangeReporter {
	public TSVEnergyReporter(final IOutputStreamFactory factory) {
		super(factory, "energy", fields());
	}

	private static List<Field> fields() {
		final ImmutableList.Builder<Field> builder = ImmutableList.builder();
		
		for (final XServiceType service : desiredServices) {
			builder.add(Field.of(service.toString(), "The primary energy used by this house from the given date to provide " + service.toString() + ", in kWh/year", "Double"));
		}
		
		for (final FuelType ft : BiomassFuels.getNonBiomassFuels()) {
			builder.add(Field.of(ft.toString(), "The " + ft.toString()  + " energy used by this house from the given date, in kWh/year", "Double"));
		}
		builder.add(Field.of("BIOMASS", "The " + "BIOMASS"  + " energy used by this house from the given date, in kWh/year", "Double"));
		
		return builder.build();
	}

	private static Set<XServiceType> desiredServices = ImmutableSet.of(XServiceType.Appliances, XServiceType.Cooking, XServiceType.Lighting, XServiceType.SpaceHeating,
			XServiceType.WaterHeating);
	
	@Override
	protected boolean interesting(final StateChangeLogEntry entry) {
		return entry.getEnergy() != null;
	}
	
	@Override
	protected void fill(final String[] row, int column, final StateChangeLogEntry logEntry) {
		final EnergyLogComponent energy = logEntry.getEnergy();
		for (final XServiceType service : desiredServices) {
			float accum = 0f;
			for (ServiceType internalService : service.getInternal()) {
				accum += energy.getEnergyUsedByService(internalService);
			}
			row[column++] = String.format("%f", accum);
		}
		// We output the value/house for most fuels, but merge all the biomass fuel types into one column. https://cseresearch.atlassian.net/browse/NHM-567
		for (final FuelType ft : BiomassFuels.getNonBiomassFuels()) {
			row[column++] = String.format("%f", energy.getEnergyUsedByFuel(ft));
		}
		row[column++] = String.format("%f", BiomassFuels.getBiomassEnergyUsed(energy));
	}

	
	@Override
	protected String getDescription() {
		return "This table gives the energy consumption experienced by each dwelling in power terms, broken down by the type of fuel and service. " +
				"Each row determines the kWh/year for a dwelling from the associated date until the next entry for that " +
				"dwelling in the table. To get the consumption for a period of time for a dwelling, these values need integrating. " +
				"An aggregated table at a national level is shown in 'national-power'."
				;
	}
}
