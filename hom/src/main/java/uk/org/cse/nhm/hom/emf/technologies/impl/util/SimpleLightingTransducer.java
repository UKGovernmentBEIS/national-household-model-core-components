package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;

/**
 * A lighting transducer
 * 
 * @author hinton
 *
 */
public class SimpleLightingTransducer implements IEnergyTransducer {
	private static final Logger log = LoggerFactory.getLogger(SimpleLightingTransducer.class);
		
	private double multiplier;
	private double proportion;
	private final String name;
	
	public SimpleLightingTransducer(final String name, final double proportion, final double multiplier) {
		this.name = name;
		setProportion(proportion);
		setMultiplier(multiplier);
	}

	@Override
	public ServiceType getServiceType() {
		return ServiceType.LIGHTING;
	}
	
	public double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(final double multiplier) {
		this.multiplier = multiplier;
	}

	public double getProportion() {
		return proportion;
	}

	public void setProportion(final double proportion) {
		this.proportion = proportion;
	}

	@Override
	public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state) {
		final double lightingDemand = state.getBoundedTotalDemand(EnergyType.DemandsVISIBLE_LIGHT, proportion);
		final double powerDemand = lightingDemand * multiplier;
		
		log.debug("Satisfying demand for {} W of light with {} W of electricity ({})", lightingDemand, powerDemand, proportion);
		state.increaseElectricityDemand(getHighRateFraction(parameters), powerDemand);		
		state.increaseSupply(EnergyType.DemandsVISIBLE_LIGHT, lightingDemand);
		state.increaseSupply(EnergyType.GainsLIGHTING_GAINS, powerDemand);
	}

	private double getHighRateFraction(final IInternalParameters parameters) {
		return parameters.getConstants().get(SplitRateConstants.DEFAULT_FRACTIONS, double[].class)
				[parameters.getTarrifType().ordinal()];
	}

	@Override
	public int getPriority() {
		return 0;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.BeforeGains;
	}
}
