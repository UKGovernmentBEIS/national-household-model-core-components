package uk.org.cse.nhm.simulator.sequence;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.sequence.ChangeValue.Variable;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ConsumeAction extends AbstractNamed implements IComponentsAction, ISequenceScopeAction {
	private final IDimension<IFlags> flags;
	private final IComponentsFunction<Number> amount;
	private final ChangeValue.Variable variable;
	
	@AssistedInject
	public ConsumeAction(
			final IDimension<IFlags> flags,
			@Assisted final IComponentsFunction<Number> amount, 
			@Assisted final Variable variable) {
		super();
		this.flags = flags;
		this.amount = amount;
		this.variable = variable;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		final Optional<Number> currentAmount = variable.get(scope, flags);
		if (currentAmount.isPresent()) {
			final double removeAmount = amount.compute(scope, lets).doubleValue();
			if (currentAmount.get().doubleValue() >= removeAmount) {
				// modify state appropriately
				variable.set(scope, flags, currentAmount.get().doubleValue() - removeAmount);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSuitable(IComponentsScope scope, ILets lets) {
		final Optional<Number> currentAmount = variable.get(scope, flags);
		if (currentAmount.isPresent()) {
			final double removeAmount = amount.compute(scope, lets).doubleValue();
			if (currentAmount.get().doubleValue() >= removeAmount) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}
}
