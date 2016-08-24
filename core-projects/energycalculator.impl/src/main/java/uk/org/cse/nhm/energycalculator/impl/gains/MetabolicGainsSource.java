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
 * A gizmo which provides metabolic gains.)
 * 
 * @author hinton
 *
 */
public class MetabolicGainsSource implements IEnergyTransducer {
	private final double METABOLIC_GAINS_PER_PERSON;

	public MetabolicGainsSource(final IConstants constants) {
		METABOLIC_GAINS_PER_PERSON = constants.get(EnergyCalculatorConstants.METABOLIC_GAINS_PER_PERSON);
	}
	
	@Override
	public ServiceType getServiceType() {
		return ServiceType.METABOLIC_GAINS;
	}

	@Override
	public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters,final ISpecificHeatLosses losses, final IEnergyState state) {
		/*
		BEISDOC
		NAME: Metabolic gains
		DESCRIPTION: The total heat gain due to human metabolism
		TYPE: formula
		UNIT: W
		SAP: (66), Table 5
		BREDEM: 6F
		DEPS: metabolic-gains-per-person, occupancy
		ID: metabolic-gains
		CODSIEB
		*/
		final double metabolicGains = METABOLIC_GAINS_PER_PERSON * parameters.getNumberOfOccupants();
		
		state.increaseSupply(EnergyType.GainsMETABOLIC_GAINS, metabolicGains);
	}

	@Override
	public int getPriority() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "Metabolism";
	}
	
	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.BeforeEverything;
	}
}
