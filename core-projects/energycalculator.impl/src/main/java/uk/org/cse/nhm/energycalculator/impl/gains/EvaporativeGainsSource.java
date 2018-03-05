package uk.org.cse.nhm.energycalculator.impl.gains;

import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.energycalculator.constants.EnergyCalculatorConstants;

/**
 * A gizmo which provides metabolic gains.
 *
 * @author hinton
 *
 */
public class EvaporativeGainsSource implements IEnergyTransducer {
	private final double EVAPORATION_GAINS_PER_PERSON;

	public EvaporativeGainsSource(final IConstants constants) {
		EVAPORATION_GAINS_PER_PERSON = constants.get(EnergyCalculatorConstants.EVAPORATION_GAINS_PER_PERSON);
	}

	@Override
	public ServiceType getServiceType() {
		return ServiceType.INTERNALS;
	}

	@Override
	public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters,final ISpecificHeatLosses losses, final IEnergyState state) {
		/*
		BEISDOC
		NAME: Evaporation loss
		DESCRIPTION: The total energy lost to evaporation.
		TYPE: formula
		UNIT: W
		SAP: (71), Table 5
                SAP_COMPLIANT: Yes
		BREDEM: 6F
                BREDEM_COMPLIANT: Yes
		DEPS: evaporation-loss-per-person,occupancy
		ID: evaporation-loss
		CODSIEB
		*/
		final double evaporationGains = EVAPORATION_GAINS_PER_PERSON * parameters.getNumberOfOccupants();

		StepRecorder.recordStep(EnergyCalculationStep.Gains_Evaporation, evaporationGains);

		state.increaseDemand(EnergyType.GainsUSEFUL_GAINS, evaporationGains);
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public String toString() {
		return "Evaporation";
	}

	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.Gains;
	}
}
