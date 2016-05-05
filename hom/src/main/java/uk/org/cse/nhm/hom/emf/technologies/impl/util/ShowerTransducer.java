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
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;

public class ShowerTransducer implements IEnergyTransducer {
	private static final Logger log = LoggerFactory.getLogger(ShowerTransducer.class);
	public static final double DEFAULT_POWER = 1.262499;
	private final double power;

	public ShowerTransducer(final double power) {
		this.power = power;
	}
	
	@Override
	public ServiceType getServiceType() {
		return ServiceType.WATER_HEATING;
	}

	@Override
	public void generate(final IEnergyCalculatorHouseCase house,
			final IInternalParameters parameters, final ISpecificHeatLosses losses,
			final IEnergyState state) {
		final double showerDemand = parameters.getConstants().get(
				HeatingSystemConstants.SHOWER_DEMAND_PROPORTION)
				* state.getTotalDemand(EnergyType.DemandsHOT_WATER);
		final double showerVolume = state
				.getTotalDemand(EnergyType.DemandsHOT_WATER_VOLUME)
				* parameters.getConstants().get(
						HeatingSystemConstants.SHOWER_VOLUME_PROPORTION);

		final double showerPower = showerVolume * power;

		state.increaseElectricityDemand(
				parameters.getConstants().get(
						SplitRateConstants.DEFAULT_FRACTIONS, double[].class)[parameters
						.getTarrifType().ordinal()], showerPower);

		state.increaseSupply(EnergyType.DemandsHOT_WATER, showerDemand);
		state.increaseSupply(EnergyType.GainsHOT_WATER_USAGE_GAINS,
				showerPower);
		log.debug("Electric shower: {} vol, {} demand, {} electricity & gains",
				showerVolume, showerDemand, showerPower);
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public String toString() {
		return "Electric Shower";
	}
	
	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.HotWater;
	}
}