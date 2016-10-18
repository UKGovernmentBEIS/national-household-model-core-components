package uk.org.cse.nhm.simulator.state.functions.impl.house;

import com.google.inject.Inject;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetTotalFloorArea extends StructureFunction<Double> {

	@Inject
	public GetTotalFloorArea(IDimension<StructureModel> structure) {
		super(structure);
	}
	
	@Override
	public Double compute(IComponentsScope scope, ILets lets) {
		return getStructure(scope).getFloorArea();
	}
}
