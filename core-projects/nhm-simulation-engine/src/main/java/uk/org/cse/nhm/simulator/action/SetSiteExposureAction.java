package uk.org.cse.nhm.simulator.action;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.SiteExposureType;
import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class SetSiteExposureAction extends AbstractNamed implements IComponentsAction, IModifier<BasicCaseAttributes> {
	private final IDimension<BasicCaseAttributes> basicDimension;
	private final SiteExposureType siteExposure;
	
	@AssistedInject
	public SetSiteExposureAction(
			final IDimension<BasicCaseAttributes> basicDimension,
			@Assisted("siteExposure") SiteExposureType siteExposure
			) {
		this.basicDimension = basicDimension;
		this.siteExposure = siteExposure;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean modify(BasicCaseAttributes modifiable) {
		modifiable.setSiteExposure(siteExposure);
		return true;
	}

	@Override
	public boolean apply(ISettableComponentsScope scope, ILets lets) throws NHMException {
		scope.modify(basicDimension, this);
		return true;
	}

	@Override
	public boolean isSuitable(IComponentsScope scope, ILets lets) {
		return true;
	}

	@Override
	public boolean isAlwaysSuitable() {
		return true;
	}
}
