package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchMorphology extends BasicAttributesFunction<Boolean> implements IComponentsFunction<Boolean> {
	final MorphologyType morphologyType;
	
	@Inject
	public MatchMorphology(final IDimension<BasicCaseAttributes> bad, @Assisted final MorphologyType morphologyType) {
		super(bad);
		this.morphologyType = morphologyType;
	}

	@Override
	public Boolean compute(final IComponentsScope scope, final ILets lets) {
		return getAttributes(scope).getMorphologyType().equals(morphologyType);
	}
}
