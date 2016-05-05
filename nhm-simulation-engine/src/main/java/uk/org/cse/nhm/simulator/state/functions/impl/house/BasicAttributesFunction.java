package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public abstract class BasicAttributesFunction<T> extends AbstractNamed implements IComponentsFunction<T> {
	final IDimension<BasicCaseAttributes> bad;

	public BasicAttributesFunction(IDimension<BasicCaseAttributes> bad) {
		this.bad = bad;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.<IDimension<?>>singleton(bad);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
	
	protected BasicCaseAttributes getAttributes(final IComponents components) {
		return components.get(bad);
	}
}
