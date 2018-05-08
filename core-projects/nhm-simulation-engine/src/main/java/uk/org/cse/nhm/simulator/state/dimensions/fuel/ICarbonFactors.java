package uk.org.cse.nhm.simulator.state.dimensions.fuel;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public interface ICarbonFactors {

    double getCarbonFactor(FuelType ft);
}
