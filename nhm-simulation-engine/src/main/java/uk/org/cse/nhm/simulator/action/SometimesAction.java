package uk.org.cse.nhm.simulator.action;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SometimesAction extends AbstractNamed implements IComponentsAction {
	private final IComponentsFunction<Number> chance;
	private final IComponentsAction delegate;

	@AssistedInject
	public SometimesAction(
			@Assisted final IComponentsFunction<Number> chance,
			@Assisted final IComponentsAction delegate) {
		this.chance = chance;
		this.delegate = delegate;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return delegate.getSourceType();
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets)
			throws NHMException {
		if (scope.getState().getRandom().nextDouble() < chance.compute(scope, lets).doubleValue()) {
			return scope.apply(delegate, lets);
		} else {
			return false;
		}
	}

	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		return delegate.isSuitable(scope, lets);
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}
}
