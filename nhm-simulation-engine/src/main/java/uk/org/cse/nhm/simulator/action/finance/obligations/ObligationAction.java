package uk.org.cse.nhm.simulator.action.finance.obligations;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.action.finance.obligations.IPaymentSchedule.IFactory;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ObligationAction extends AbstractNamed implements IComponentsAction {
	private final ITimeDimension time;
	private final ICanonicalState state;
	private final ISimulator sim;
	private final IDimension<IObligationHistory> history;
	
	private final Set<String> tags;
	private final IComponentsAction delegate;
	private final String counterparty;
	private final IComponentsFunction<? extends Number> amount;
	private final IFactory paymentScheduleFactory;
	
	@AssistedInject
	public ObligationAction(
			final ITimeDimension time, 
			final ICanonicalState state,
			final ISimulator sim, 
			final IDimension<IObligationHistory> history,
			@Assisted final Set<String> tags,
			@Assisted final IComponentsAction delegate,
			@Assisted final String counterparty,
			@Assisted final IComponentsFunction<? extends Number> amount,
			@Assisted final IPaymentSchedule.IFactory paymentScheduleFactory) {
				this.time = time;
				this.state = state;
				this.sim = sim;
				this.history = history;
				this.tags = tags;
				this.delegate = delegate;
				this.counterparty = counterparty;
				this.amount = amount;
				this.paymentScheduleFactory = paymentScheduleFactory;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return delegate.getSourceType();
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		if (delegate.apply(scope, lets)) {
			final DateTime currentDate = scope.get(time).get(lets);
			
			final ILets closure = lets.assignableTo(Number.class);
			
			final ILets withVars = closure;
			
			scope.addObligation(new UserDefinedObligation(
					time, 
					state, 
					sim, 
					scope.get(history).size(), 
					amount, 
					paymentScheduleFactory.getSchedule(
							state, 
							currentDate, 
							withVars, 
							scope.getDwelling()), 
					counterparty, 
					tags, 
					withVars));
			return true;
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
		return delegate.isAlwaysSuitable();
	}
}
