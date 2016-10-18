package uk.org.cse.nhm.simulator.supplychain;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;

public class Supply extends AbstractNamed {
	private DateTime on;
	private Optional<Integer> set;
	private Optional<Integer> increment;
	private Optional<Period> interval;

	@AssistedInject
	public Supply(
			@Assisted final DateTime on,
			@Assisted final Optional<Period> interval,
			@Assisted("set") final Optional<Integer> set,
			@Assisted("increment") final Optional<Integer> increment) {
		this.on = on;
		this.interval = interval;
		this.set = set;
		this.increment = increment;
	}
	
	public Optional<Integer> getIncrement() {
		return increment;
	}
	public Optional<Integer> getSet() {
		return set;
	}
	public DateTime getOn() {
		return on;
	}
	public Optional<Period> getInterval() {
		return interval;
	}
	
	public String description() {
		if (set.isPresent()) {
			return String.format("= %d", set.get() + increment.or(0));
		} else {
			return String.format("+ %d", increment.or(0));
		}
	}
}
