package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IEnergyMeter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.energy.PretendEnergyMeter;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.IPayment;

abstract class ObligationForecastingFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final Set<IDimension<?>> allDimensions;
	private final IDimension<IEnergyMeter> meterDimension;
	private final IDimension<IPowerTable> powerDimension;
    private final Predicate<Collection<String>> allowedTags;
	
	@Inject
	public ObligationForecastingFunction(
			final Set<IDimension<?>> allDimensions,
			final IDimension<IEnergyMeter> meterDimension,
			final IDimension<IPowerTable> powerDimension,
            final Predicate<Collection<String>> allowedTags) {
		super();
		this.allDimensions = allDimensions;
		this.meterDimension = meterDimension;
		this.powerDimension = powerDimension;
        this.allowedTags = allowedTags;
	}
	
	protected Multimap<DateTime, IPayment> predictObligations(
			final IComponentsScope scope,
			final Iterable<IObligation> obligations,
			final DateTime now, 
			final DateTime then) {
 		final IHypotheticalComponentsScope hypothesis = scope.createHypothesis();
		
		hypothesis.imagine(meterDimension, PretendEnergyMeter.of(scope.get(powerDimension)));
		
		final Multimap<DateTime, IPayment> payments = HashMultimap.create();
		
		for (final IObligation ob : obligations) {
			Optional<DateTime> winder = Optional.of(now);
			while ((winder = ob.getNextTransactionDate(winder.get())).isPresent()
					&& !then.isBefore(winder.get())) {
				final DateTime next = winder.get();
				if (next.equals(now)) continue;
                for (final IPayment payment : ob.generatePayments(next, hypothesis)) {
                    if (includePayment(payment)) payments.put(next, payment);
                }
			}
		}
		
		return payments;
	}
	
	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}

	protected boolean includePayment(final IPayment payment) {
		return allowedTags.apply(payment.getTags());
	}
    
	/**
	 * TODO This is grim; since NPV depends on evaluating obligations, it could depend on any of the dimensions on a house
	 *      so for correctness we must report that we depend on all dimensions. Hopefully NPV will not be used in any performance
	 *      critical change-driven setting (like a group defn.)
	 */
	@Override
	public Set<IDimension<?>> getDependencies() {
		return allDimensions;
	}
}
