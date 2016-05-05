package uk.org.cse.nhm.simulator.action.finance;

import java.util.Set;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.transactions.Payment;

abstract class AbsFinanceAction extends AbstractNamed implements IComponentsAction {
	protected final Set<String> tags;
	protected final IComponentsAction action;
	protected final String counterparty;

	protected AbsFinanceAction (final Set<String> tags, final IComponentsAction action, final String counterparty) {
		this.tags = tags;
		this.action = action;
		this.counterparty = counterparty;
	}

	@Override
	public final StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public final boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		if (scope.apply(action, lets)) {
			final double value = compute(scope, lets);
			scope.addTransaction(Payment.of(counterparty, value, tags));
			return true;
		} else {
			return false;
		}
	}

	abstract protected double compute(final ISettableComponentsScope scope, final ILets lets);

	@Override
	public final boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		return action.isSuitable(scope, lets);
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return action.isAlwaysSuitable();
	}
}
