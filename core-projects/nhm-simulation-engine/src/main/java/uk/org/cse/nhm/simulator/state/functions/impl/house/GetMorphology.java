package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetMorphology extends BasicAttributesFunction<MorphologyType> {
	@Inject
	public GetMorphology(IDimension<BasicCaseAttributes> bad) {
		super(bad);
	}

	@Override
	public MorphologyType compute(IComponentsScope scope, ILets lets) {
		return getAttributes(scope).getMorphologyType();
	}
}
