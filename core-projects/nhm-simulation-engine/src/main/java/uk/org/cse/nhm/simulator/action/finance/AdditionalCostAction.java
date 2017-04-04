package uk.org.cse.nhm.simulator.action.finance;

import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class AdditionalCostAction extends AbsFinanceAction {
	private final IComponentsFunction<? extends Number> cost;

	@AssistedInject
	public AdditionalCostAction(
			@Assisted final Set<String> tags,
			@Assisted final IComponentsAction action,
			@Assisted final String counterparty,
			@Assisted final IComponentsFunction<? extends Number> cost) {
		super(tags, action, counterparty);
		this.cost = cost;
	}

	@Override
	protected double compute(final ISettableComponentsScope scope, final ILets lets) {
		return cost.compute(scope, lets).doubleValue();
	}
}
