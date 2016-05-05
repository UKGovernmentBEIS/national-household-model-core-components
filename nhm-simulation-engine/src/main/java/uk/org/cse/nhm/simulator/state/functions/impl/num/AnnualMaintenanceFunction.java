package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.obligations.impl.AnnualMaintenanceObligation;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.IPayment;

public class AnnualMaintenanceFunction extends AbstractNamed implements IComponentsFunction<Double> {

	private final IDimension<IObligationHistory> obligationsDimension;
	private final ITimeDimension timeDimension;

	@Inject
	public AnnualMaintenanceFunction(final IDimension<IObligationHistory> obligationsDimension, final ITimeDimension timeDimension) {
		this.obligationsDimension = obligationsDimension;
		this.timeDimension = timeDimension;
	}
	
	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final IObligationHistory obligations = scope.get(obligationsDimension);
		final DateTime now = scope.get(timeDimension).get(lets);
		final Optional<AnnualMaintenanceObligation> obligation = obligations.getObligation(AnnualMaintenanceObligation.class);
		
		if(obligation.isPresent()) {
			final AnnualMaintenanceObligation maintenance = obligation.get();
			final Optional<DateTime> maintenanceDate = maintenance.getNextTransactionDate(now);
			if(!maintenanceDate.isPresent()) {
				return 0.0;
			} else {
				final Collection<IPayment> payments = maintenance.generatePayments(maintenanceDate.get(), scope);
				return sumOverPayments(payments);
			}
		} else {
			throw new IllegalStateException("Dwelling had no maintenance obligation: " + scope.getDwellingID()); 
		}
	}

	private double sumOverPayments(final Collection<IPayment> payments) {
		double accum = 0.0;
		for(final IPayment payment : payments) {
			accum += payment.getAmount();
		}
		return accum;
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.of(obligationsDimension, timeDimension);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
