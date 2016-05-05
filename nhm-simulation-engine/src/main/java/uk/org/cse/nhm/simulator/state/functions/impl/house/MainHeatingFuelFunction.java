package uk.org.cse.nhm.simulator.state.functions.impl.house;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;

public abstract class MainHeatingFuelFunction<T> extends TechnologyFunction<T> {
	private final ITechnologyOperations operations;

	public MainHeatingFuelFunction(ITechnologyOperations operations, IDimension<ITechnologyModel> bad) {
		super(bad);
		this.operations = operations;
	}

	protected FuelType getMainHeatingFuel(final IComponents components) {
		return operations.getMainHeatingFuel(components.get(bad));
	}
}
