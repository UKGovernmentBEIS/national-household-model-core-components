package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.types.TenureType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetTenure extends BasicAttributesFunction<TenureType> {
	@Inject
	public GetTenure(IDimension<BasicCaseAttributes> bad) {
		super(bad);
	}

	@Override
	public TenureType compute(IComponentsScope scope, ILets lets) {
		return getAttributes(scope).getTenureType();
	}
}
