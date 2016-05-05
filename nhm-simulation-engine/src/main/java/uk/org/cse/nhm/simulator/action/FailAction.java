package uk.org.cse.nhm.simulator.action;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class FailAction extends AbstractNamed implements IComponentsAction {
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		return false;
	}

	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		return false;
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}
}
