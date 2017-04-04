package uk.org.cse.nhm.simulator.action.finance.obligations;

import java.util.Collection;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.obligations.impl.BaseObligation;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.transactions.Payment;

class UserDefinedObligation extends BaseObligation {

	private final IComponentsFunction<? extends Number> amount;
	private final IPaymentSchedule schedule;
	private final String payee;
	private final Set<String> tags;
	private final ILets lets;

	protected UserDefinedObligation(
			final ITimeDimension time, 
			final ICanonicalState state,
			final ISimulator simulator, 
			final int index,
			final IComponentsFunction<? extends Number> amount,
			final IPaymentSchedule schedule,
			final String payee,
			final Set<String> tags, 
			final ILets lets
			) {
		super(time, state, simulator, index);
		
		this.amount = amount;
		this.schedule = schedule;
		this.payee = payee;
		this.tags = tags;
		this.lets = lets;
	}

	@Override
	public Optional<DateTime> getNextTransactionDate(final DateTime currentDate) {
		return schedule.getNextTransactionDate(currentDate);
	}

	@Override
	public Collection<IPayment> generatePayments(final DateTime date, final IComponentsScope state) {
		if (schedule.transactionStillValid(date)) {
			return ImmutableSet.of(
					Payment.of(payee, 
							amount.compute(state, lets).doubleValue(),
							tags
							));
		} else {
			return ImmutableSet.of();
		}
	}

}
