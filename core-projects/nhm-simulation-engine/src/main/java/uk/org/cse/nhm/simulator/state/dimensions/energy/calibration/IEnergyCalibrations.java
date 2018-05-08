package uk.org.cse.nhm.simulator.state.dimensions.energy.calibration;

import java.util.Set;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public interface IEnergyCalibrations {

    void addRuleForFuels(ICalibrationRule rule, Set<FuelType> fuels);

}
