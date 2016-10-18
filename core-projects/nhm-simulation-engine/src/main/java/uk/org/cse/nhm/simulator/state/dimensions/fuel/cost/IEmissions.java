package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.List;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public interface IEmissions {

	float getAnnualEmissions(FuelType ft, ServiceType es);
	float getAnnualEmissions(FuelType ft, List<ServiceType> es);

}
