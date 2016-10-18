package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;

/**
 * An abstract class for things which just consume energy of some sort, but do nothing else.
 * @author hinton
 *
 */
public abstract class Sink implements IEnergyTransducer {
	private final EnergyType sunk;

	protected Sink(final EnergyType sunk) {
		this.sunk = sunk;
	}
	
	@Override
	public ServiceType getServiceType() {
		return ServiceType.SINKS;
	}
	
	@Override
	public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters,
			final ISpecificHeatLosses losses, final IEnergyState state) {
		state.increaseDemand(sunk, getDemand(house, parameters, losses, state));
	}

	protected abstract double getDemand(IEnergyCalculatorHouseCase house,
			IInternalParameters parameters, ISpecificHeatLosses losses,
			IEnergyState state);
	
	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.BeforeEverything;
	}
}
