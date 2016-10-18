package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Tests whether a dwelling is suitable for a choice.
 *
 * @author glenns
 * @since 3.4.0
 */
public class SuitabilityTest extends AbstractNamed implements IComponentsFunction<Boolean> {
	private final IComponentsAction action;

	@Inject
	public SuitabilityTest(@Assisted final IComponentsAction action) {
		this.action = action;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.emptySet(); // TODO: get the dependencies for the measure
	}

	@Override
	public Set<DateTime> getChangeDates() {
		throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public Boolean compute(final IComponentsScope scope, final ILets lets) {
		if (action != null) {
			return action.isSuitable(scope, lets);
		} else {
			return true;
		}
	}
}
