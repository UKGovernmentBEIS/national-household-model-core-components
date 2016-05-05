package uk.org.cse.nhm.simulator.state.dimensions.energy;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.carrotsearch.hppc.IntIntOpenHashMap;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.impl.DerivedDimensionWithCache;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public class EnergyMeterDimension extends DerivedDimensionWithCache<IEnergyMeter> {
	private final ITimeDimension time;
	private final IDimension<IPowerTable> power;
	private final IState state;
	private final IntIntOpenHashMap resetCounter;
	
	
	@Inject
	public EnergyMeterDimension(
			final DimensionCounter dc,
			final IState state, final ITimeDimension time, final IDimension<IPowerTable> power) {
		this(dc.next(), null, state, time, power, new IntIntOpenHashMap(IInternalDimension.DEFAULT_CAPACITY), IInternalDimension.DEFAULT_CAPACITY);
	}
	
	private EnergyMeterDimension(
			final int index,
			final EnergyMeterDimension parent, 
			final IState state, final ITimeDimension time, final IDimension<IPowerTable> power,
			final IntIntOpenHashMap resetCounter,
			final int capacity
			) {
		super(index, parent, capacity);
		
		this.state = state;
		this.time = time;
		this.power = power;
		this.resetCounter = resetCounter;
	}
	
	@Override
	public int getGeneration(final IDwelling instance) {
		return state.getGeneration(time, instance)
				+ state.getGeneration(power, instance)
				+ resetCounter.get(instance.getID());
	}

	@Override
	public IInternalDimension<IEnergyMeter> branch(final IBranch forkingState, final int capacity) {
		return new EnergyMeterDimension(index, this, state, time, power, resetCounter, capacity);
	}

	@Override
	protected IEnergyMeter doGet(final IDwelling instance) {
		final IPowerTable powerTable = state.get(power, instance);
		final DateTime now = state.get(time, instance).get(XForesightLevel.Default);
		
		// get previous energy meter reading
		final EnergyMeter oldMeter = (EnergyMeter) super.getMostRecentValue(instance);

		if (oldMeter != null) {
			final int dwellingResetCounter = resetCounter.get(instance.getID());
			if(oldMeter.getResetCounter() != dwellingResetCounter) {
				return oldMeter.reset(now, powerTable, dwellingResetCounter);
			}
			
			return oldMeter.integrateAndUpdate(now, powerTable);
		} else {
			// create new blank power table
			return EnergyMeter.start(now, powerTable);
		}
	}
	
	/**
	 * DO NOT CALL THIS METHOD unless you are {@link AnnualFuelObligation} and you are in the apply() method.
	 * 
	 * Seriously.
	 * 
	 * @param dwellingID
	 */
	protected void reset(final int dwellingID) {
		if (hasParent()) {
			throw new UnsupportedOperationException("The energy meter for a dwelling can only be reset in the canonical state");
		}
		
		resetCounter.putOrAdd(dwellingID, 1, 1);
	}
}
