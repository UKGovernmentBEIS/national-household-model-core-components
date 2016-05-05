package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetBuiltForm extends StructureFunction<BuiltFormType> {
	@Inject
	public GetBuiltForm(IDimension<StructureModel> structure) {
		super(structure);
	}

	@Override
	public BuiltFormType compute(IComponentsScope scope, ILets lets) {
		return getStructure(scope).getBuiltFormType();
	}
}
