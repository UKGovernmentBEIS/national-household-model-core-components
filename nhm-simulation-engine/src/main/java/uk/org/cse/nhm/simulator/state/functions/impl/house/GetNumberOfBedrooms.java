package uk.org.cse.nhm.simulator.state.functions.impl.house;

import com.google.inject.Inject;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetNumberOfBedrooms extends StructureFunction<Integer> {

	@Inject
	protected GetNumberOfBedrooms(IDimension<StructureModel> structure) {
		super(structure);
	}

	@Override
	public Integer compute(IComponentsScope scope, ILets lets) {
		return super.getStructure(scope).getNumberOfBedrooms();
	}
}
