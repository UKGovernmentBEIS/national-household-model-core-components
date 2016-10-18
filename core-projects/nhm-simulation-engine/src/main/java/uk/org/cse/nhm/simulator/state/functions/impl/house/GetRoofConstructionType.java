package uk.org.cse.nhm.simulator.state.functions.impl.house;

import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.components.fabric.types.RoofConstructionType;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetRoofConstructionType extends StructureFunction<RoofConstructionType> {

	@AssistedInject
	public GetRoofConstructionType(
			final IDimension<StructureModel> structureDimension) {
		super(structureDimension);
	}

	@Override
	public RoofConstructionType compute(final IComponentsScope scope, ILets lets) {
		return getStructure(scope).getRoofConstructionType();
	}
}
