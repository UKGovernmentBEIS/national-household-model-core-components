package uk.org.cse.nhm.reporting.standard.flat;

import java.util.ArrayList;

import org.joda.time.Period;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.logging.logentry.NationalEnergyLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.fuel.BiomassFuels;

/**
 * Builder which outputs national energy log details.
 * 
 * @since 3.1.0
 * @author hinton
 *
 */
public class TSVNationalPowerReporter extends SimpleTabularReporter<NationalEnergyLogEntry> {
	public TSVNationalPowerReporter(final IOutputStreamFactory factory) {
		super(factory, NationalEnergyLogEntry.class, TableType.AGGREGATE, "national-power",
				ReportHeaderLogEntry.Type.NationalPower
				);
		
		final ImmutableList.Builder<Field> builder = ImmutableList.builder();
		
		builder.add(Field.of("From", "The start date of the period", "Date"));
		builder.add(Field.of("To", "The end date of the period", "Date"));
		
		for (final FuelType ft : BiomassFuels.getNonBiomassFuels()) {
			builder.add(Field.of(ft.toString(), "The national consumption rate, in kWh/year, of " + ft.toString(), "Double"));
		}
		
		builder.add(Field.of("BIOMASS", "The national consumption rate, in kWh/year, of BIOMASS", "Double"));
		
		for (final ServiceType st : ServiceType.values()) {
			builder.add(Field.of(st.toString(), "The national consumption rate, in kWh/year, of primary energy for " + st.toString(), "Double"));
		}
		
		start(builder.build());
	}

	@Override
	protected void doHandle(final NationalEnergyLogEntry logEntry) {
		final ArrayList<String> columns = new ArrayList<String>();
		columns.add(ReportFormats.getDateAsDay(logEntry.getDate()));
		columns.add(ReportFormats.getDateAsDay(logEntry.getUntil()));

		
		final Period period = new Period(logEntry.getDate(), logEntry.getUntil());
		final double hours = period.getHours();
		
		// We output the value/house for most fuels, but merge all the biomass fuel types into one column. https://cseresearch.atlassian.net/browse/NHM-567
		if(hours == 0) {
			for(int i = 0; i < BiomassFuels.getNonBiomassFuels().size() + 1; i++) {
				columns.add("0");
			}
		} else {
			for (final FuelType ft : BiomassFuels.getNonBiomassFuels()) {
				columns.add(String.format("%.1f", logEntry.getEnergyUsedByFuel(ft) / hours));
			}
			columns.add(String.format("%.1f", BiomassFuels.getBiomassEnergyUsed(logEntry) / hours));
		}

		

		for (final ServiceType st : ServiceType.values()) {
			if (hours == 0) {
				columns.add("0");
			} else {
				columns.add(String.format("%.1f", logEntry.getEnergyUsedByService(st) / hours));
			}
		}

		write(columns.toArray(new String[columns.size()]));
	}
	
	@Override
	public String getDescription() {
		return "Each row of this table shows a period of time, and the total consumption rate in kWh/year " +
				"for each fuel and service during that period.";
	}
}
