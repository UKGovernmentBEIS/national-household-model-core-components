package uk.org.cse.nhm.energycalculator.impl.gains;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.energycalculator.constants.GainsConstants;

public class GainsTransducer implements IEnergyTransducer {
	private final double LIGHTING_GAIN_USEFULNESS;
	private final double HOT_WATER_USE_USEFULNESS;
	private final double HOT_WATER_PIPEWORK_USEFULNESS;

	public GainsTransducer(final IConstants constants) {
		LIGHTING_GAIN_USEFULNESS = constants.get(GainsConstants.LIGHTING_GAIN_USEFULNESS);
		HOT_WATER_USE_USEFULNESS = constants.get(GainsConstants.HOT_WATER_DIRECT_GAINS);
		HOT_WATER_PIPEWORK_USEFULNESS = constants.get(GainsConstants.HOT_WATER_SYSTEM_GAINS);
	}
	
	@Override
	public ServiceType getServiceType() {
		return ServiceType.INTERNALS;
	}

	@Override
	public void generate(final IEnergyCalculatorHouseCase house,
			final IInternalParameters parameters, final ISpecificHeatLosses losses,
			final IEnergyState state) {
		final double metabolicGains = state.getTotalSupply(EnergyType.GainsMETABOLIC_GAINS);
		final double lightingGains = state.getTotalSupply(EnergyType.GainsLIGHTING_GAINS);
		final double applianceGains = state.getTotalSupply(EnergyType.GainsAPPLIANCE_GAINS);
		final double cookingGains = state.getTotalSupply(EnergyType.GainsCOOKING_GAINS);
		
		/*
		BEISDOC
		NAME: Pump and fan gains
		DESCRIPTION: The total internal heat gains from pumps and fans.
		TYPE: formula
		UNIT: W
		SAP: (70), Table 5a
		BREDEM: 6G, Table 26
		DEPS: warm-air-fan-electricity,central-heating-pump-gains,oil-boiler-pump-gains
		ID: pump-and-fan-gains
		CODSIEB
		*/
		final double pumpGains = state.getTotalSupply(EnergyType.GainsPUMP_AND_FAN_GAINS);
		
		final double hotWaterGains = state.getTotalSupply(EnergyType.GainsHOT_WATER_USAGE_GAINS);
		final double hotWaterGains2 = state.getTotalSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS);
		final double solarGains = state.getTotalSupply(EnergyType.GainsSOLAR_GAINS);
		
		state.increaseDemand(EnergyType.GainsMETABOLIC_GAINS, metabolicGains);
		state.increaseDemand(EnergyType.GainsLIGHTING_GAINS, lightingGains);
		state.increaseDemand(EnergyType.GainsAPPLIANCE_GAINS, applianceGains);
		state.increaseDemand(EnergyType.GainsCOOKING_GAINS, cookingGains);
		state.increaseDemand(EnergyType.GainsPUMP_AND_FAN_GAINS, pumpGains);
		state.increaseDemand(EnergyType.GainsHOT_WATER_USAGE_GAINS, hotWaterGains);
		state.increaseDemand(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, hotWaterGains2);
		state.increaseDemand(EnergyType.GainsSOLAR_GAINS, solarGains);
		
		/*
		BEISDOC
		NAME: Useful Gains
		DESCRIPTION: Adds up all the gains we found, multiplying them by utilisation factors as necessary.
		TYPE: formula
		UNIT: W
		SAP: (73, 84)
		BREDEM: 6J, 6K
		DEPS: monthly-solar-gains,metabolic-gains,lighting-gains-utilisation,lighting-energy-demand,appliance-adjusted-demand,pump-and-fan-gains,TODO
		ID: useful-gains
		CODSIEB
		*/
		final double usefulGains = 
				solarGains + 
				metabolicGains + 
				LIGHTING_GAIN_USEFULNESS * lightingGains +
				applianceGains +
				cookingGains + 
				pumpGains + 
				HOT_WATER_USE_USEFULNESS * hotWaterGains + 
				HOT_WATER_PIPEWORK_USEFULNESS * hotWaterGains2;
		
		state.increaseSupply(EnergyType.GainsUSEFUL_GAINS, usefulGains);
	}

	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.Gains;
	}
	
	@Override
	public int getPriority() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "Gains (internals)";
	}
}
