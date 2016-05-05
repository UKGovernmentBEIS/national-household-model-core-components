package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import javax.inject.Inject;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.ICarbonFactors;
import uk.org.cse.nhm.simulator.state.dimensions.impl.DerivedDimensionWithCache;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public class EmissionsDimension extends DerivedDimensionWithCache<IEmissions> {
	final IDimension<ICarbonFactors> fuelPrices;
	final IDimension<IPowerTable> energyResult;
	final IState state;
	
	@Inject
	public EmissionsDimension(final DimensionCounter dc, final IState state, final IDimension<ICarbonFactors> fuelPrices,
			final IDimension<IPowerTable> energyResult) {
		this(dc.next(), state, fuelPrices, energyResult, null, IInternalDimension.DEFAULT_CAPACITY);
	}

	private EmissionsDimension(final int index, final IState state,
			final IDimension<ICarbonFactors> fuelPrices,
			final IDimension<IPowerTable> energyResult, 
			final EmissionsDimension parent,
			final int capacity) {
		super(index, parent, capacity);
		this.state = state;
		this.fuelPrices = fuelPrices;
		this.energyResult = energyResult;
	}
	
	@Override
	protected IEmissions doGet(final IDwelling instance) {
		return new Emissions(state.get(fuelPrices, instance), state.get(energyResult, instance));
	}

	@Override
	public int getGeneration(final IDwelling instance) {
		final int priceGeneration = state.getGeneration(fuelPrices, instance);
		final int energyGeneration = state.getGeneration(energyResult, instance);
		
		return priceGeneration + energyGeneration;
	}

	@Override
	public IInternalDimension<IEmissions> branch(final IBranch forkingState, final int capacity) {
		return new EmissionsDimension(index, forkingState, fuelPrices, energyResult, this, capacity);
	}
	
	@Override
	public String toString() {
		return "Emissions";
	}
}
