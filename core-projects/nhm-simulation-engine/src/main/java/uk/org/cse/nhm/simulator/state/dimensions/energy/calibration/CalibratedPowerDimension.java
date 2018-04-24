package uk.org.cse.nhm.simulator.state.dimensions.energy.calibration;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType.ECCalibration;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.impl.DerivedDimensionWithCache;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

/**
 * A dimension which computes energy results within the simulator,
 * possibly using the other dimension which uses the energy calculator.
 * 
 * @author hinton
 *
 */
public class CalibratedPowerDimension extends DerivedDimensionWithCache<IPowerTable> implements IEnergyCalibrations {
    private final IProfilingStack profiler;
	private final IDimension<IPowerTable> uncalibrated;
	private final IState state;
	private final CalibrationCache cache;
	private final IDimension<IHeatingBehaviour> heatingBehaviour;
	
	@Inject
	public CalibratedPowerDimension(final IProfilingStack profiler,
			final DimensionCounter dc,
			final ICanonicalState state, 
			@Named("uncalibrated") final IDimension<IPowerTable> uncalibrated,
			final IDimension<IHeatingBehaviour> heatingBehaviour) {
		this(profiler, dc.next(), null, state, uncalibrated, heatingBehaviour, new CalibrationCache(dc, state), IInternalDimension.DEFAULT_CAPACITY);
	}
	
	public CalibratedPowerDimension(final IProfilingStack profiler,
			final int index, final CalibratedPowerDimension parent,
			final IState forkingState, 
			final IDimension<IPowerTable> uncalibrated, 
			final IDimension<IHeatingBehaviour> heatingBehaviour,
			final CalibrationCache calibrationCache, final int capacity) {
		super(index, parent, capacity);
        this.profiler = profiler;
		this.state = forkingState;
		this.uncalibrated = uncalibrated;
		this.heatingBehaviour = heatingBehaviour;
		this.cache = calibrationCache;
	}
	
	@Override
	public void addRuleForFuels(final ICalibrationRule rule, final Set<FuelType> fuels) {
		cache.addCalibrationRule(rule, fuels.toArray(new FuelType[0]));
	}
	
	@Override
	public int getGeneration(final IDwelling instance) {
		if (cache.isEmpty()) {
			return state.getGeneration(uncalibrated, instance);
		}
		
		final int cacheGen = cache.getGeneration(instance);
		final int uncalGen = state.getGeneration(uncalibrated, instance);
		
		return cacheGen + uncalGen;
	}

	@Override
	public IInternalDimension<IPowerTable> branch(final IBranch forkingState, final int capacity) {
		return new CalibratedPowerDimension(profiler, index, this, forkingState, uncalibrated, heatingBehaviour, cache.branch(forkingState, capacity), capacity);
	}

    @Override
    public IPowerTable get(final IDwelling instance) {
    	if (cache.isEmpty() || (state.get(heatingBehaviour, instance).getEnergyCalculatorType().calibration == ECCalibration.DISABLED)) {
    		// In SAP 2012 mode, we don't apply the calibrations.
    		return state.get(uncalibrated, instance);
        } else {
            return super.get(instance);
        }
    }
    
	@Override
	protected IPowerTable doGet(final IDwelling instance) {
		if (cache.isEmpty()) {
			return state.get(uncalibrated, instance);
		} else {
            final IPowerTable pt = state.get(uncalibrated, instance);
			final Calibrations cal =  cache.get(instance);
            if (cal == null) return pt;
            return cal.applyTo(state, profiler, instance, pt);
		}
	}
	
	@Override
	public void merge(final IDwelling instance, final IInternalDimension<IPowerTable> branch_) {
		final CalibratedPowerDimension branch = (CalibratedPowerDimension) branch_;
		
		this.cache.merge(instance, branch.cache);
		
		super.merge(instance, branch_);
	}
}
