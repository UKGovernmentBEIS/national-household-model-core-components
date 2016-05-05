package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchFlag extends AbstractNamed implements IComponentsFunction<Boolean> {
	private final IDimension<IFlags> flags;
	private final String flag;
	private final boolean requiredState;
	
	@Inject
	public MatchFlag(final IDimension<IFlags> flags, 
			@Assisted final String flag,
			@Assisted final boolean requiredState) {
		this.flags = flags;
		this.flag = flag;
		this.requiredState = requiredState;
	}

	@Override
	public Boolean compute(final IComponentsScope scope, final ILets lets) {
		return scope.get(flags).testFlag(flag) == requiredState;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(flags);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
