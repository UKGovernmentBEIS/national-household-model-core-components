package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchAge extends AgeFunction<Boolean> implements IComponentsFunction<Boolean> {
	private final Predicate<Integer> test;
	
	@Inject
	public MatchAge(final ITimeDimension time, final IDimension<BasicCaseAttributes> basic, @Assisted final Predicate<Integer> test) {
		super(basic, time);
		this.test = test;
	}

	@Override
	public Boolean compute(final IComponentsScope scope, final ILets lets) {
		return test.apply(getAge(scope, lets));
	}
}
