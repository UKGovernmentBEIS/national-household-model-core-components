package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IEnergyMeter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.energy.PretendEnergyMeter;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.SurrealTimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.IPayment;

public class AnnualCostFunction extends AbstractNamed implements IComponentsFunction<Number> {
	private final boolean includeYearEnd;

	private final Set<IDimension<?>> allDimensions;
	private final ITimeDimension time;
	private final IDimension<IEnergyMeter> meterDimension;
	private final IDimension<IPowerTable> powerDimension;
	private final IDimension<IObligationHistory> obligations;

	private final Predicate<Collection<String>> tagTest;
	
	@AssistedInject
	public AnnualCostFunction(
			final Set<IDimension<?>> allDimensions,
			final IDimension<IEnergyMeter> meterDimension,
			final IDimension<IPowerTable> powerDimension, 
			final IDimension<IObligationHistory> obligations,
			final ITimeDimension time,
			@Assisted final boolean includeYearEnd,
			@Assisted final Predicate<Collection<String>> tagTest) {
		super();
		this.allDimensions = allDimensions;
		this.meterDimension = meterDimension;
		this.powerDimension = powerDimension;
		this.obligations = obligations;
		this.time = time;
		this.includeYearEnd = includeYearEnd;
		this.tagTest = tagTest;
	}
	
	@Override
	public Number compute(final IComponentsScope scope, final ILets lets) {
		final ITime now = scope.get(time);
		final IHypotheticalComponentsScope fake = scope.createHypothesis();
		final SurrealTimeDimension surreal = 
				new SurrealTimeDimension(time.index(), 
						now.get(XForesightLevel.Never), 
						now.predictableLevels());
		final DateTime startDate = now.get(XForesightLevel.Always);
		final DateTime endDate = startDate.plusYears(1);
		// the surreal time should now look like the enclosing surreal time, or real time.
		fake.replace(time, surreal);
		// the fake energy meter is definitely wrong here; if we read the energy meter
		// outside the tariff, it would read the wrong value. however, integrating it
		// is about as hard as really running the model, which would be very expensive to do here.
		fake.imagine(meterDimension, PretendEnergyMeter.of(scope.get(powerDimension)));
		// we have to do and generate the transactions in the right order
		final PriorityQueue<DateTime> remain = new PriorityQueue<DateTime>();
		remain.add(startDate);
		double acc = 0;
		// so now the main grim bit; we run a queue of dates on which we expect transactions
		DateTime earliest = null;
		while (!remain.isEmpty()) {
			// this is the next date on which there might be transactions
			final DateTime dt = remain.poll();
			if (earliest != null && !dt.isAfter(earliest)) throw new RuntimeException("Date came out of queue all wrong");
			earliest = dt;
			surreal.setCurrentTime(dt);
			for (final IObligation ob : fake.get(obligations)) {
				// process the payments
				for (final IPayment p : ob.generatePayments(dt, fake)) {
					fake.addTransaction(p);
					if (includeYearEnd || dt.isAfter(startDate)) acc += filter(p);
				}
				
				// ask everyone when they will go off next
				final Optional<DateTime> nextDate = ob.getNextTransactionDate(dt);
				if (nextDate.isPresent()) {
					final DateTime nextDate2 = nextDate.get();
					if (endDate.isAfter(nextDate2) || includeYearEnd && endDate.equals(nextDate2)) {
						// add to queue unless already scheduled
						if (!remain.contains(nextDate2)) {
							remain.add(nextDate2);
							if (!nextDate2.isAfter(dt)) {
								throw new RuntimeException(ob + " produced an invalid next transaction date (" + dt + " => " + nextDate2 +" )");
							}
						}
					}
				}
			}
		}
		
		return acc;
	}
	
	private double filter(final IPayment p) {
		if (tagTest.apply(p.getTags())) 
			return p.getAmount();
		return 0;
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
    
	@Override
	public Set<IDimension<?>> getDependencies() {
		return allDimensions;
	}
}
