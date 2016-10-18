package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchRegion extends BasicAttributesFunction<Boolean> implements IComponentsFunction<Boolean> {
	private final RegionType region;

	@Inject
	public MatchRegion(final IDimension<BasicCaseAttributes> bad, @Assisted final RegionType region) {
		super(bad);
		this.region = region;
	}

	@Override
	public Boolean compute(final IComponentsScope scope, final ILets lets) {
		return getAttributes(scope).getRegionType().equals(region);
	}
}
