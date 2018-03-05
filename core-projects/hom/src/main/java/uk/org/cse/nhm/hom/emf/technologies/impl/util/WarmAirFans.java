package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.impl.EnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.hom.constants.PumpAndFanConstants;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;

public class WarmAirFans extends EnergyTransducer {
	/**
	 * Warm air fans which depend on volume of house
	 */
	public WarmAirFans() {
		super(ServiceType.PRIMARY_SPACE_HEATING, 0);
	}

	@Override
	public void generate(final IEnergyCalculatorHouseCase house,final IInternalParameters parameters, final ISpecificHeatLosses losses,final IEnergyState state) {
		/*
		BEISDOC
		NAME: Warm air fan electricity
		DESCRIPTION: The average power use of a warm air system's fan.
		TYPE: formula
		UNIT: W
		SAP: Table 5a (warm air heating system fans)
                SAP_COMPLIANT: Yes
		BREDEM: Table4, Table 26 (warm air heating system fans)
                BREDEM_COMPLIANT: Yes
		DEPS: warm-air-system-volume-multiplier
		ID: warm-air-fan-electricity
		CODSIEB
		*/
		final double power = parameters.getConstants().get(PumpAndFanConstants.WARM_AIR_SYSTEM_VOLUME_MULTIPLIER) *
				house.getHouseVolume();

		StepRecorder.recordStep(EnergyCalculationStep.PumpsFansAndKeepHot_WarmAirFans, power);

		state.increaseElectricityDemand(parameters.getConstants().get(SplitRateConstants.DEFAULT_FRACTIONS, parameters.getTarrifType()),
				power);
		state.increaseSupply(EnergyType.GainsPUMP_AND_FAN_GAINS, power);
	}

	@Override
	public String toString() {
		return "Warm Air Fans";
	}

	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.BeforeGains;
	}
}
