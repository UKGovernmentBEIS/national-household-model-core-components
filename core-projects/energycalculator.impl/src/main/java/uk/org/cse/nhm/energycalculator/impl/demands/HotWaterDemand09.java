package uk.org.cse.nhm.energycalculator.impl.demands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.energycalculator.constants.HotWaterConstants09;

/**
 * hot water demand
 * @author hinton
 *
 */
public class HotWaterDemand09 implements IEnergyTransducer {
	private static final Logger log = LoggerFactory.getLogger(HotWaterDemand09.class);
	private final double CONSTANT;
	private final double PERSON;
	private final double FACTOR;

	public HotWaterDemand09(final IConstants constants) {
		this.CONSTANT = constants.get(HotWaterConstants09.BASE_VOLUME);
		this.PERSON = constants.get(HotWaterConstants09.PERSON_DEPENDENT_VOLUME);
		this.FACTOR = constants.get(HotWaterConstants09.ENERGY_PER_VOLUME);
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public ServiceType getServiceType() {
		return ServiceType.INTERNALS;
	}

	@Override
	public void generate(final IEnergyCalculatorHouseCase house,
			final IInternalParameters parameters, final ISpecificHeatLosses losses,
			final IEnergyState state) {
		final IConstants constants = parameters.getConstants();
		/**
		 * Zero-indexed month number
		 */
		final int monthNumber = parameters.getClimate().getMonthOfYear()-1;
		
		final double USAGE_FACTOR = constants.get(HotWaterConstants09.USAGE_FACTOR, monthNumber);
		final double RISE_TEMPERATURE = constants.get(HotWaterConstants09.RISE_TEMPERATURE, monthNumber);

		/*
		BEISDOC
		NAME: Water volume
		DESCRIPTION: The volume of hot water required by the house
		TYPE: formula
		UNIT: m^3
		SAP: (43,44)
		BREDEM: 2.1A to 2.1G
		DEPS: occupancy,monthly-water-usage-factor
		GET:
		SET:
		NOTES: This is the SAP calculation, which is simpler than the BREDEM calculation. The two calculations are equivalent if shower-type in BREDEM is set to "unknown". Since we don't have any information about showers, this is what we will do.
		NOTES: We omit the 5% reduction in hot water usage for dwellings with hot water targets. We have no information about this.   
		ID: water-volume
		CODSIEB
		*/
		final double volume = (CONSTANT + PERSON * parameters.getNumberOfOccupants()) * USAGE_FACTOR;
		
		/*
		BEISDOC
		NAME: Water heating power
		DESCRIPTION: The power required to provide hot water.
		TYPE: formula
		UNIT: W
		SAP: (45,46), Tables
		BREDEM: 2.1H, 2.1I
		DEPS: water-volume,monthly-water-temperature-factor
		NOTES: In the NHM this is expressed in Watts. In SAP and BREDEM it is expressed in kWh/month. These units are equivalent with a time conversion.
		ID: water-heating-power
		CODSIEB
		*/
		final double power = volume * RISE_TEMPERATURE * FACTOR;
		
		log.debug("Hot water demand: {} W, {} l", power, volume);
		
		state.increaseDemand(EnergyType.DemandsHOT_WATER_VOLUME, volume);
		state.increaseDemand(EnergyType.DemandsHOT_WATER, power);
	}
	
	@Override
	public String toString() {
		return "Hot Water Demand";
	}
	
	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.BeforeEverything;
	}
}
