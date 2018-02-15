package uk.org.cse.nhm.energycalculator.impl.gains;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculationStep;
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

		final double usefulLightingGains = LIGHTING_GAIN_USEFULNESS * lightingGains;
		final double usefulHotWaterGains =
				/*
				BEISDOC
				NAME: Hot Water Usage Gains
				DESCRIPTION: Hot water gains due to energy content of hot water used, and combi losses.
				TYPE: formula
				UNIT: W
				SAP: (65)
				BREDEM: 6I
				DEPS: combi-losses,water-heating-power,hot-water-direct-gains-usefulness
				NOTES: Combi losses are included here because they model 'rejected' water, which came out of the tap at the wrong temperature.
				ID: hot-water-usage-gains
				CODSIEB
				*/
				HOT_WATER_USE_USEFULNESS * hotWaterGains +
				/*
				BEISDOC
				NAME: Hot Water System Gains
				DESCRIPTION: Hot water gains due to pipework, distribution and storage losses.
				TYPE: formula
				UNIT: W
				SAP: (65)
				BREDEM: 6I
				DEPS: central-hot-water-distribution-losses,point-of-use-distribution-losses,distribution-losses,water-storage-loss,hot-water-system-gains-usefulness
				ID: hot-water-system-gains
				CODSIEB
				*/
				HOT_WATER_PIPEWORK_USEFULNESS * hotWaterGains2;

		StepRecorder.recordStep(EnergyCalculationStep.Gains_HotWater_Monthly, usefulHotWaterGains);
		StepRecorder.recordStep(EnergyCalculationStep.Gains_Metabolic, metabolicGains);
		StepRecorder.recordStep(EnergyCalculationStep.Gains_Lighting, usefulLightingGains);
		StepRecorder.recordStep(EnergyCalculationStep.Gains_Appliances, applianceGains);
		StepRecorder.recordStep(EnergyCalculationStep.Gains_Cooking, cookingGains);
		StepRecorder.recordStep(EnergyCalculationStep.Gains_PumpsAndFans, pumpGains);
		StepRecorder.recordStep(EnergyCalculationStep.Gains_HotWater, usefulHotWaterGains);

		final double usefulInternalGains = metabolicGains +
				usefulLightingGains +
				applianceGains +
				cookingGains +
				pumpGains +
				usefulHotWaterGains;

		StepRecorder.recordStep(EnergyCalculationStep.Gains_Internal, usefulInternalGains);

		state.increaseDemand(EnergyType.GainsSOLAR_GAINS, solarGains);

		/*
		BEISDOC
		NAME: Useful Gains
		DESCRIPTION: Adds up all the gains we found, multiplying them by utilisation factors as necessary.
		TYPE: formula
		UNIT: W
		SAP: (73, 84)
		BREDEM: 6J, 6K
		DEPS: monthly-solar-gains,metabolic-gains,lighting-gains-utilisation,lighting-energy-demand,appliance-adjusted-demand,cooking-gains,pump-and-fan-gains,hot-water-usage-gains,hot-water-system-gains
		ID: useful-gains
		CODSIEB
		*/
		final double usefulGains = solarGains + usefulInternalGains;

		StepRecorder.recordStep(EnergyCalculationStep.Gains, usefulGains);

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
