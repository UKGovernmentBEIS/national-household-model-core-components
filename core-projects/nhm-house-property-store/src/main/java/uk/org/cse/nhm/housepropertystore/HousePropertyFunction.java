package uk.org.cse.nhm.housepropertystore;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.hom.housepropertystore.IHouseProperties;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class HousePropertyFunction extends AbstractNamed implements IComponentsFunction<Boolean> {
	private final IDimension<IHouseProperties> constants;
	private final String variableName;
	private final Predicate<String> test;

	@Inject
	public HousePropertyFunction(
			final IDimension<IHouseProperties> constants, 
			@Assisted final String variableName,
			@Assisted final Predicate<String> test) {
		this.constants = constants;
		this.variableName = variableName;
		this.test = test;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.<IDimension<?>> singleton(constants);
	}

	@Override
	public Boolean compute(final IComponentsScope scope, final ILets lets) {
		return test.apply(scope.get(constants).get(variableName));
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}