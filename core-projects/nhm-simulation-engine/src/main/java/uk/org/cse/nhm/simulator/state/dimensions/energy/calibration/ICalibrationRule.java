package uk.org.cse.nhm.simulator.state.dimensions.energy.calibration;

import uk.org.cse.commons.names.IIdentified;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;

public interface ICalibrationRule {

    public interface ICalibration extends IIdentified {

        public float compute(
                final IPowerTable uncalibrated,
                final IState state,
                final IDwelling dwelling,
                final FuelType fuelType);
    }

    public int getGeneration(final IState state, final IDwelling dwelling);

    public ICalibration getCalibration(final IState state, final IDwelling dwelling);
}
