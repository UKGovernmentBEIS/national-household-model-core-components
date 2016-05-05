package uk.org.cse.nhm.simulator.action.finance.obligations;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.common.base.Optional;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class PeriodicPaymentFactory implements IPaymentSchedule.IFactory {
	private final Period interval;
	private final Optional<Period> lifetime;
	private final Optional<IComponentsFunction<Boolean>> whileCondition;

	public PeriodicPaymentFactory(
		final Period interval,
		final Optional<Period> lifetime,
		final Optional<IComponentsFunction<Boolean>> whileCondition) {
		this.interval = interval;
		this.lifetime = lifetime;
		this.whileCondition = whileCondition;
	}

	@Override
	public IPaymentSchedule getSchedule(final ICanonicalState state, final DateTime currentDate, final ILets lets, final IDwelling dwelling) {
		final DateTime start = currentDate;
		final Optional<DateTime> until = lifetime.isPresent() ? Optional.of(start.plus(lifetime.get())) : Optional.<DateTime>absent(); 
		
		return new IPaymentSchedule(){

			@Override
			public Optional<DateTime> getNextTransactionDate(final DateTime currentDate) {
				if (whileConditionFailed()) {
					return Optional.absent();
				}
				
				DateTime nextTransaction = start;
				
				while (!nextTransaction.isAfter(currentDate)) {
					nextTransaction = nextTransaction.plus(interval);
				}
				
				if (until.isPresent() && nextTransaction.isAfter(until.get())) {
					return Optional.absent();
				}
				
				return Optional.of(nextTransaction);
			}

			private boolean whileConditionFailed() {
				
				if (whileCondition.isPresent()) {
					final IComponentsScope scope = state.detachedScope(dwelling);
					
					final boolean keepGoing = whileCondition.get().compute(scope, lets);
					
					return !keepGoing;
				}
				return false;
			}

			@Override
			public boolean transactionStillValid(final DateTime currentDate) {
				if (until.isPresent() && currentDate.isAfter(until.get())) {
					return false;
				}
				
				if (whileConditionFailed()) {
					return false;
				}
				
				if (!currentDate.isAfter(start)) {
					return false;
				}
				
				DateTime when = start;
				
				while (currentDate.isAfter(when)) {
					when = when.plus(interval);
				}
				
				return currentDate.equals(when);
			}
		};
	}
}