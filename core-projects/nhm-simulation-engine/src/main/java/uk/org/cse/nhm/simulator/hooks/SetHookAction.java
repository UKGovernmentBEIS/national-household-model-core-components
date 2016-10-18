package uk.org.cse.nhm.simulator.hooks;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SetHookAction extends AbstractNamed implements IHookRunnable, IStateChangeSource {
	private final String variable;
	private final IComponentsFunction<Number> function;

	@AssistedInject
	public SetHookAction(
			@Assisted final String variable,
			@Assisted final IComponentsFunction<Number> function) {
		this.variable = variable;
		this.function = function;
	}

	@Override
	public void run(final IStateScope state, final DateTime date, final Set<IStateChangeSource> causes, final ILets lets) {
        // TODO: is this a hack of the utmost badness?
		state.getState().getGlobals().setVariable(variable, function.compute(state.getState().detachedScope(null), lets));
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.TRIGGER;
	}
}
