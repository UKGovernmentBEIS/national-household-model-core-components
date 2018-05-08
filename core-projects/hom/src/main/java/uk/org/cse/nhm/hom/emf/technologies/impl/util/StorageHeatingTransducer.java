package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.impl.EnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;

/**
 * Storage heaters are 100% efficient, and have a fixed electricity high rate
 * fraction, apparently.
 * 
 * @author hinton
 * @assumption BREDEM 9.2.2 is sufficient to capture SAP & CHM's model of
 *             background temperature (complex storage heaters and solid fuel
 *             heaters can be omitted for now).
 */
public class StorageHeatingTransducer extends EnergyTransducer {
	private final double proportion;
	private final boolean integrated;

	public StorageHeatingTransducer(final double proportion, final int priority, final boolean integrated) {
		super(ServiceType.PRIMARY_SPACE_HEATING, priority);
		this.proportion = proportion;
		this.integrated = integrated;
	}
	
	@Override
	public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses,final IEnergyState state) {
		final double heat = state.getBoundedTotalHeatDemand(proportion);
		state.increaseSupply(EnergyType.DemandsHEAT, heat);
		if (integrated) {
			state.increaseElectricityDemand(parameters.getConstants().get(SplitRateConstants.INTEGRATED_STORAGE_HEATER_FRACTIONS, parameters.getTarrifType()), heat);			
		} else {
			state.increaseElectricityDemand(0, heat);
		}
	}
	
	@Override
	public String toString() {
		return "Storage Heater";
	}
	
	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.Heat;
	}
}
