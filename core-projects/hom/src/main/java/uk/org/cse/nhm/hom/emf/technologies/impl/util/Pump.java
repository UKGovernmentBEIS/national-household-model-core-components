package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.EnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;

/**
 * A pump just does some pumps and fans gains & consumption
 * @author hinton
 *
 */
public class Pump extends EnergyTransducer {
	private final double wattage;
	private final double gains;
	private final String name;
	
	/**
	 * Construct a new pump / fan for some part of a house
	 * @param name the name of the thing that this fan/pump belongs to
	 * @param serviceType the service of the thing that this fan/pump belongs to
	 * @param gains the wattage of gains to produce
	 * @param wattage the wattage of electricity this pump will consume.
	 */
	public Pump(final String name, final ServiceType serviceType, final double wattage, final double gains) {
		super(serviceType, 0);
		this.name = name;
		this.gains = gains;
		this.wattage = wattage;
	}



	@Override
	public void generate(final IEnergyCalculatorHouseCase house,final IInternalParameters parameters, final ISpecificHeatLosses losses,final IEnergyState state) {
		state.increaseElectricityDemand(parameters.getConstants().get(SplitRateConstants.DEFAULT_FRACTIONS, parameters.getTarrifType()), wattage);
		if (gains > 0) {
			state.increaseSupply(EnergyType.GainsPUMP_AND_FAN_GAINS, gains);
		}
	}
	
	@Override
	public String toString() {
		return "Pump/Fan (" + name + ")";
	}
	
	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.BeforeGains;
	}
}
