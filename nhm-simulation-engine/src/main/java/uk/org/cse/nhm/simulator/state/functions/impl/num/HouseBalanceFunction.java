package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;

public class HouseBalanceFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final IDimension<DwellingTransactionHistory> transactionDimension;

	@Inject
	public HouseBalanceFunction(final IDimension<DwellingTransactionHistory> transactionDimension) {
		this.transactionDimension = transactionDimension;
	}
	
	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		return scope.get(transactionDimension).getBalance();
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(transactionDimension);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
