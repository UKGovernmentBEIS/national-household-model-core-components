package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;

public interface IExtraCharge {
	Optional<FuelType> getFuel();
	void apply(ISettableComponentsScope scope, ILets lets);
	
	/**
	 * The number of dependencies this extra charge has if you count everything in its tree. 
	 * This is used to determine the order in which extra charges should be run.
	 * A larger number means it will be run later. 
	 */
	int getOrder();
}
