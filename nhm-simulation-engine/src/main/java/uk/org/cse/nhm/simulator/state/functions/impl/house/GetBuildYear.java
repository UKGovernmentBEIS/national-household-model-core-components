package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetBuildYear extends BasicAttributesFunction<Integer> {
	@Inject
	public GetBuildYear(
			IDimension<BasicCaseAttributes> bad) {
		super(bad);
	}

	@Override
	public Integer compute(IComponentsScope scope, ILets lets) {
		return getAttributes(scope).getBuildYear();
	}
}
