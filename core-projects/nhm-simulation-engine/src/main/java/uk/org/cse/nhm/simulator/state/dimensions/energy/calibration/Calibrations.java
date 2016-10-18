package uk.org.cse.nhm.simulator.state.dimensions.energy.calibration;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.energy.calibration.ICalibrationRule.ICalibration;

public class Calibrations {
	int index = 0;
	final ICalibration[] cals = new ICalibration[FuelType.values().length];
	final FuelType[][] types = new FuelType[FuelType.values().length][];
	
	public CalibratedPowerTable applyTo(final IState state, final IProfilingStack profiler, final IDwelling instance, final IPowerTable uncalibrated) {
		final float[] result = new float[FuelType.values().length];
		for (final FuelType ft : FuelType.values()) {
			result[ft.ordinal()] = uncalibrated.getPowerByFuel(ft);
		}
		for (int i = 0; i<index; i++) {
			for (final FuelType ft : types[i]) {
				result[ft.ordinal()] = 
						cals[i].compute(uncalibrated, state, instance, ft);

                if (Double.isNaN(result[ft.ordinal()])) {
                    throw profiler.die("Energy calibration rule produced NaN for "+ ft, cals[i], null);
                }
			}
		}
		return new CalibratedPowerTable(result, uncalibrated);
	}

	public void addCalibration(final ICalibration cal, final FuelType[] fuelTypes) {
		cals[index] = cal;
		types[index] = fuelTypes;
		index++;
	}
}
