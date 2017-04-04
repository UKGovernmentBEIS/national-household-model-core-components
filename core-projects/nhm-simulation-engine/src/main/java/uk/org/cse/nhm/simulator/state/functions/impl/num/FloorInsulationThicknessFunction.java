package uk.org.cse.nhm.simulator.state.functions.impl.num;

import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.impl.house.StructureFunction;

public class FloorInsulationThicknessFunction extends StructureFunction<Number> {

	@AssistedInject
	public FloorInsulationThicknessFunction(
			final IDimension<StructureModel> structure) {
		super(structure);
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		return getStructure(scope).getFloorInsulationThickness();
	}
}
