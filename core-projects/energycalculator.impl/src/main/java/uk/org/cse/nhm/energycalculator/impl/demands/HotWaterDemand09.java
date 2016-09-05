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
		UNIT: litres
		SAP: (43)
		DEPS: base-hot-water-demand,person-hot-water-demand,occupancy
		NOTES: We omit the 5% reduction in hot water usage for dwellings with hot water targets. We have no information about this.   
		ID: sap-water-volume
		CODSIEB
		*/
		final double volume = (CONSTANT + PERSON * parameters.getNumberOfOccupants()) * USAGE_FACTOR;
		
		/*
		BEISDOC
		NAME: Usage Adjusted Water Volume
		DESCRIPTION: description
		TYPE: type
		UNIT: litres
		SAP: (44)
		BREDEM: 2.1G
		DEPS: sap-water-volume,bredem-water-volume,monthly-water-usage-factor
		ID: usage-adjusted-water-volume
		CODSIEB
		*/
		final double usageAdjustedVolume = volume * USAGE_FACTOR;
		
		/*
		BEISDOC
		NAME: Water heating power
		DESCRIPTION: The power required to provide hot water.
		TYPE: formula
		UNIT: W
		SAP: (45,46), Tables
		BREDEM: 2.1H, 2.1I,2.2A
		DEPS: usage-adjusted-water-volume,monthly-water-temperature-factor,energy-per-hot-water
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
