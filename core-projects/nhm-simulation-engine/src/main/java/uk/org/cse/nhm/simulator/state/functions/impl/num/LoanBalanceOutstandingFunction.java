package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.ILoan;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class LoanBalanceOutstandingFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final IDimension<IObligationHistory> obligationsDimension;
	private final ITimeDimension timeDimension;
	private final String creditor;
	private final String tagged;

	@AssistedInject
	public LoanBalanceOutstandingFunction(
			final IDimension<IObligationHistory> obligationsDimension,
			final ITimeDimension timeDimension,
			@Assisted("creditor") @Nullable final String creditor, 
			@Assisted("tagged") @Nullable final String tagged) {
				this.obligationsDimension = obligationsDimension;
				this.timeDimension = timeDimension;
				this.creditor = creditor;
				this.tagged = tagged;
	}
	
	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final IObligationHistory obligations = scope.get(obligationsDimension);
		final DateTime now = scope.get(timeDimension).get(lets);
		double accum = 0.0;
		for(final ILoan loan : obligations.getObligations(ILoan.class)) {
			if(passesFilter(loan)) {
				accum += loan.getOutstandingBalance(now);
			}
		}
		
		return accum;
	}

	private boolean passesFilter(final ILoan loan) {
		return (creditor == null || loan.getPayee().equals(creditor)) 
				&& (tagged == null || loan.getTags().contains(tagged));
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(obligationsDimension, timeDimension);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
