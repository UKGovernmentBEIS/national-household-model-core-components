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
		BREDEM: 6F
		DEPS: evaporation-loss-per-person,occupancy
		ID: evaporation-loss
		CODSIEB
		*/
		final double evaporationGains = EVAPORATION_GAINS_PER_PERSON * parameters.getNumberOfOccupants();
		
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
