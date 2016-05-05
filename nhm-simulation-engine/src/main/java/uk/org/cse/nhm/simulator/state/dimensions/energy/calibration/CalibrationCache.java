package uk.org.cse.nhm.simulator.state.dimensions.energy.calibration;

import java.util.HashMap;
import java.util.Map;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.calibration.ICalibrationRule.ICalibration;
import uk.org.cse.nhm.simulator.state.dimensions.impl.DerivedDimensionWithCache;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;

public class CalibrationCache extends DerivedDimensionWithCache<Calibrations> {
	private final IState state;
	
	private final Map<ICalibrationRule, FuelType[]> calibrations;
	
	public boolean isEmpty() {
		return calibrations.isEmpty();
	}

	public CalibrationCache(final DimensionCounter dc, final IState state) {
		this(dc.next(), null, state, IInternalDimension.DEFAULT_CAPACITY);
	}
	
	protected CalibrationCache(final int index, final CalibrationCache parent, final IState state, final int capacity) {
		super(index, parent, capacity);
		this.state = state;
		this.calibrations = parent == null ? new HashMap<ICalibrationRule, FuelType[]>(capacity) : parent.calibrations;
	}

	public void addCalibrationRule(final ICalibrationRule key, final FuelType[] value) {
		calibrations.put(key, value);
	}

	@Override
	public int getGeneration(final IDwelling instance) {
        if (isEmpty()) {
            return 0;
        }
		int acc = 0;
		for (final ICalibrationRule rule : calibrations.keySet()) {
			acc+=rule.getGeneration(state, instance);
		}
		return acc;
	}

    @Override
    public Calibrations get(final IDwelling instance) {
        if (isEmpty()) {
            return null; //hax
        }
        return super.get(instance);
    }

	@Override
	public CalibrationCache branch(final IBranch forkingState, final int capacity) {
		return new CalibrationCache(index, this, forkingState, capacity);
	}

	@Override
	protected Calibrations doGet(final IDwelling instance) {
		final Calibrations result = new Calibrations();
		
		for (final ICalibrationRule rule : calibrations.keySet()) {
			final ICalibration cal = rule.getCalibration(state, instance);
			result.addCalibration(cal, calibrations.get(rule));
		}
		
		return result;
	}
}
