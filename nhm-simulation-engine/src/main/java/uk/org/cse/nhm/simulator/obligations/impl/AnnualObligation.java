package uk.org.cse.nhm.simulator.obligations.impl;

import java.util.Collection;
import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.google.common.base.Optional;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.IPayment;

/**
 * A base class for obligations which go every year
 * 
 * @author hinton
 *
 */
public abstract class AnnualObligation extends BaseObligation {
	protected AnnualObligation(
			final ITimeDimension time,
			final ICanonicalState state, 
			final ISimulator simulator,
			final int index) {
		super(time, state, simulator, index);
	}

	@Override
	public Optional<DateTime> getNextTransactionDate(final DateTime now) {
		final int year;
		if (isFiringDate(now)) {
			year = now.getYear() + 1;
		} else {
			year = now.getYear();
		}
		final DateTime next = new DateTime(year, DateTimeConstants.DECEMBER, 31, 23, 59, 59, 999);
		return Optional.of(next);
	}

	protected boolean isFiringDate(final DateTime now) {
		return now.getMonthOfYear() == DateTimeConstants.DECEMBER &&
				now.getDayOfMonth() == 31 &&
				now.getHourOfDay() == 23 &&
				now.getMinuteOfHour() == 59 &&
				now.getSecondOfMinute() == 59 &&
				now.getMillisOfSecond() == 999;
	}

	@Override
	public Collection<IPayment> generatePayments(final DateTime now, final IComponentsScope state) {
		if (isFiringDate(now)) {
			return doGeneratePayments(state, ILets.EMPTY);
		} else {
			return Collections.emptySet();
		}
	}

	protected abstract Collection<IPayment> doGeneratePayments(final IComponentsScope state, ILets lets);
}
