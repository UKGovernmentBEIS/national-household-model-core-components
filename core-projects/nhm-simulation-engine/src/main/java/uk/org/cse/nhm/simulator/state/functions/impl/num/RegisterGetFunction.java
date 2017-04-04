package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class RegisterGetFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final IDimension<IFlags> flagsDimension;
	private final String name;
	private final Double defaultVal;

	@AssistedInject
	public RegisterGetFunction(
			final IDimension<IFlags> flagsDimension,
			@Assisted final String name,
			@Assisted final Double defaultVal
			) {
				this.flagsDimension = flagsDimension;
				this.name = name;
				this.defaultVal = defaultVal;
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		return scope.get(flagsDimension).getRegister(name).or(defaultVal);
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.<IDimension<?>>singleton(flagsDimension);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
