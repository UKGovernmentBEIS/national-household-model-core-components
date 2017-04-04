package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetRegion extends BasicAttributesFunction<RegionType> {
	@Inject
	public GetRegion(IDimension<BasicCaseAttributes> bad) {
		super(bad);
	}

	@Override
	public RegionType compute(IComponentsScope scope, ILets lets) {
		return getAttributes(scope).getRegionType();
	}
}
