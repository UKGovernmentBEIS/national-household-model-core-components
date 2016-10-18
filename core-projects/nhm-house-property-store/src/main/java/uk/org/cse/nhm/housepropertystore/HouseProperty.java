package uk.org.cse.nhm.housepropertystore;

import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.hom.housepropertystore.IHouseProperties;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class HouseProperty extends AbstractNamed implements IComponentsFunction<Object> {
	private final String prop;
	private final IDimension<IHouseProperties> constants;

	@AssistedInject
	public HouseProperty(@Assisted final String prop, final IDimension<IHouseProperties> constants) {
		this.prop = prop;
		this.constants = constants;
	}
	
	
	@Override
	public Object compute(final IComponentsScope scope, final ILets lets) {
		return scope.get(constants).get(prop);
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.of();
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return ImmutableSet.of();
	}
}
