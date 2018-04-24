package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;

public interface IExtraCharge {
	Optional<FuelType> getFuel();
	void apply(ISettableComponentsScope scope, ILets lets);
	Set<IExtraCharge> getDependencies();

	public static class CircularDependencyException extends Exception {
		private final IExtraCharge head;

		CircularDependencyException(IExtraCharge head) {
			this.head = head;
		}
	}

	public static class DependencyWrongFuelTypeException extends RuntimeException {
		private final IExtraCharge parent;
		private final Set<IExtraCharge> deps;

		public DependencyWrongFuelTypeException(IExtraCharge parent, Set<IExtraCharge> deps) {
			this.parent = parent;
			this.deps = deps;
		}
	}
}
