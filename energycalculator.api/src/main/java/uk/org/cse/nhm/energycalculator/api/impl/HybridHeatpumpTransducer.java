package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;

public class HybridHeatpumpTransducer extends EnergyTransducer {
	final EnergyType primaryEnergyType;
	final EnergyType secondaryEnergyType;
	
	final double heatpumpEfficiency;
	final double secondaryEfficiency;
	
	final double overallProportion;
	final double[] monthlyProportion;
	
	final double highRateFraction;
	
	public HybridHeatpumpTransducer(
			final int priority,
			final double highRateFraction,
			final EnergyType secondary,
			final double cop, final double e, final double proportion, final double[] monthly
			) {
		super(ServiceType.PRIMARY_SPACE_HEATING, priority);
		this.primaryEnergyType = null;
		this.secondaryEnergyType = secondary;
		this.highRateFraction = highRateFraction;
		this.heatpumpEfficiency = cop;
		this.secondaryEfficiency = e;
		this.overallProportion = proportion;
		this.monthlyProportion = monthly;
	}
	
	public HybridHeatpumpTransducer(
			final int priority,
			final EnergyType primary,
			final EnergyType secondary,
			final double cop, final double e, final double proportion, final double[] monthly
			) {
		super(ServiceType.PRIMARY_SPACE_HEATING, priority);
		this.primaryEnergyType = primary;
		this.secondaryEnergyType = secondary;
		this.highRateFraction = 1;
		this.heatpumpEfficiency = cop;
		this.secondaryEfficiency = e;
		this.overallProportion = proportion;
		this.monthlyProportion = monthly;
	}
	
	@Override
	public void generate(
			final IEnergyCalculatorHouseCase house,
			final IInternalParameters parameters, 
			final ISpecificHeatLosses losses,
			final IEnergyState state) {
		final double gen = state.getBoundedTotalHeatDemand(overallProportion);
		
		state.increaseSupply(EnergyType.DemandsHEAT, gen);
		final double proportionGeneratedByHeatPump = monthlyProportion[parameters.getClimate().getMonthOfYear()-1];
		
		final double energyUsedByHeatPump = (gen * proportionGeneratedByHeatPump) / heatpumpEfficiency;
		final double energyUsedBySecondary = (gen * (1 - proportionGeneratedByHeatPump)) / secondaryEfficiency;
		
		if (primaryEnergyType == null) {
			state.increaseElectricityDemand(highRateFraction, energyUsedByHeatPump);
		} else {
			state.increaseDemand(primaryEnergyType, energyUsedByHeatPump);
		}
		if (energyUsedBySecondary > 0) {
			state.increaseDemand(secondaryEnergyType, energyUsedBySecondary);
		}
	}

	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.Heat;
	}
}
