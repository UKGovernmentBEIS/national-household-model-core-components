package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;

/**
 * This is the BREDEM-8 definition of a cooker - we may need a SAP2009 cooker which works differently?
 * 
 * BREDEM-8 has a different cooking model than SAP09 or the CHM.
 * 
 * @author hinton
 *
 */
public abstract class AbstractCooker implements IEnergyTransducer {
	/**
	 * The base load required in watts
	 */
	private double baseLoad;
	/**
	 * Additional load due to people, in watts / person
	 */
	private double personSensitivity;

	private double efficiency;
	
	/**
	 * True if this cooker includes a hob.
	 */
	private boolean hob;
	/**
	 * True if this cooker includes an oven.
	 */
	private boolean oven;
	
	public boolean isHob() {
		return hob;
	}

	public void setHob(final boolean hob) {
		this.hob = hob;
	}

	public boolean isOven() {
		return oven;
	}

	public void setOven(final boolean oven) {
		this.oven = oven;
	}

	public boolean isRange() {
		return false;
	}
	
	/**
	 * @return The base load from this cooker, in watts - this is the mean power consumption for the cooker, which doesn't change by how many people there are in the house
	 */
	public double getBaseLoad() {
		return baseLoad;
	}

	public void setBaseLoad(final double baseLoad) {
		this.baseLoad = baseLoad;
	}

	/**
	 * @return The person sensitivity of this cooker, in watts / person - this is the mean power consumption per person above the base load for this cooker
	 */
	public double getPersonSensitivity() {
		return personSensitivity;
	}

	public void setPersonSensitivity(final double personSensitivity) {
		this.personSensitivity = personSensitivity;
	}

	/**
	 * @return 
	 */
	public double getEfficiency() {
		return efficiency;
	}

	public void setEfficiency(final double efficiency) {
		this.efficiency = efficiency;
	}

	@Override
	public ServiceType getServiceType() {
		return ServiceType.COOKING;
	}

	/**
	 * Implements a simple model of cooking
	 * 
	 * 
	 * ({@link #baseLoad} + (num occupants) * {@link #personSensitivity} = I) watts of {@link #sourceEnergyType} are input to 
	 * <pre>
	 *  [I watts of source]
	 *       ||
	 *       \/
	 *    [cooker]
	 *    ||     ||
	 *    \/     \/
	 * [gains]  [cooking]
	 * </pre>
	 * Where cooking output = {@link #efficiency} * input, and gains output + cooking output = input
	 * <p>
	 * @assumption where num occupants = 0, no energy is used for cooking.
	 */
	@Override
	public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state) {
		if (parameters.getNumberOfOccupants() == 0) return;
		
		/*
		BEISDOC
		NAME: Cooker Demand
		DESCRIPTION: The fuel energy demand for cooking.
		TYPE: formula
		UNIT: W
		SAP: (L15)
		DEPS: 
		NOTES: This value is halved here, because we create two separate 'cooker objects' for the hob and the oven.
		NOTES: TODO BREDEM has an entirely different implementation for cooking.
		NOTES: TODO efficiency mess
		ID: cooking-demand
		CODSIEB
		*/
		final double demand = (baseLoad + personSensitivity * parameters.getNumberOfOccupants()) / 2;
		
		final double output = 
				demand * ((isHob() ? 1 : 0) + (isOven() ? 1 : 0));
		
		final double gains = output * (1 - efficiency);
		
		increaseFuelDemand(parameters, state, output);
		
		state.increaseSupply(EnergyType.GainsCOOKING_GAINS , gains);
	}
	
	protected abstract void increaseFuelDemand(final IInternalParameters parameters, final IEnergyState state, final double demand);

	@Override
	public int getPriority() {
		return 0;
	}
	
	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.BeforeEverything;
	}
}
