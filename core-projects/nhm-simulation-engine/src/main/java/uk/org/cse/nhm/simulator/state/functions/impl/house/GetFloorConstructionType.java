package uk.org.cse.nhm.simulator.state.functions.impl.house;

import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetFloorConstructionType extends StructureFunction<FloorConstructionType> {
	@AssistedInject
	public GetFloorConstructionType(
			final IDimension<StructureModel> dim) {
		super(dim);
	}

	@Override
	public FloorConstructionType compute(final IComponentsScope scope, ILets lets) {
		return getStructure(scope).getGroundFloorConstructionType();
	}
}
