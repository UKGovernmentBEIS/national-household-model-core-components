package uk.org.cse.nhm.simulator.hooks;

import static uk.org.cse.nhm.logging.logentry.ExplainArrow.OUTSIDE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Sets;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.ExplainArrow;
import uk.org.cse.nhm.logging.logentry.ExplainLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class TransitionsHookAction extends AbstractNamed implements IHookRunnable {
	private final IDwellingSet source;
	private final List<IComponentsFunction<?>> divisions;
	private Map<IDwelling, String> housesByDivision = ImmutableMap.of();
	private final ILogEntryHandler output;
	private final Seq seq;

	@AssistedInject
	public TransitionsHookAction(final ILogEntryHandler output,
                                 @Assisted final IDwellingSet source,
                                 @Assisted final List<IComponentsFunction<?>> divisions) {
		this.output = output;
		this.source = source;
		this.divisions = divisions;
		this.seq = new Seq();
	}

	@Override
	public void run(final IStateScope state, final DateTime date, final Set<IStateChangeSource> causes, final ILets lets) {
		final ImmutableMap.Builder<IDwelling, String> updatedHouseByDivision = ImmutableMap.builder();
		final Multiset<Change> changes = HashMultiset.create();
		
		final Set<IDwelling> dwellings = source.get(state.getState(), lets);

		for (final IDwelling d : Sets.difference(housesByDivision.keySet(), dwellings)) {
			changes.add(new Change(housesByDivision.get(d), OUTSIDE));
		}
		
		
		for (final IDwelling d : dwellings) {
			final String key = computeDivisions(state.getState(), d);
			updatedHouseByDivision.put(d, key);
			
			if (housesByDivision.containsKey(d)) {
				changes.add(new Change(housesByDivision.get(d), key));
			} else {
				changes.add(new Change(OUTSIDE, key));
			}
		}
		
		final ImmutableList.Builder<ExplainArrow> arrows = ImmutableList.builder();
		for (final Entry<Change> e : changes.entrySet()) {
			arrows.add(new ExplainArrow(e.getElement().getFrom(), e.getElement().getTo(), e.getCount()));
		}
		
		output.acceptLogEntry(
				new ExplainLogEntry(date, getIdentifier().getName(), Joiner.on('-').join(causes), isStockCreation(causes), seq.next(date), arrows.build()));
		
		housesByDivision = updatedHouseByDivision.build();
	}
	
	private boolean isStockCreation(final Set<IStateChangeSource> causes) {
		for (final IStateChangeSource s : causes) {
			if (s.getSourceType() == StateChangeSourceType.CREATION) {
				return true;
			}
		}
		return false;
	}

	private String computeDivisions(final IState state, final IDwelling d) {
		final List<Object> key = new ArrayList<>();
		for (final IComponentsFunction<?> f : divisions) {
			final Object keyPart = f.compute(state.detachedScope(d), ILets.EMPTY);
			key.add(keyPart == null ? "n/a" : keyPart);
		}
		return computeName(key);
	}
	
	private String computeName(final List<Object> things) {
		try {
		return Joiner.on("-").join(things);
		} catch (final Exception e) {
			System.out.println(e + e.getMessage());
			return "";
		}
	}
	
	@AutoProperty
	static class Change {
		private final String from;
		private final String to;

		Change (final String from, final String to) {
			this.from = from;
			this.to = to;
		}
		
		public String getFrom() {
			return from;
		}

		public String getTo() {
			return to;
		}
		
		@Override
		public int hashCode() {
			return Pojomatic.hashCode(this);
		}
		
		@Override
		public boolean equals(final Object o) {
			return Pojomatic.equals(this, o);
		}
	}
	
	static class Seq {
		int count = 0;
		DateTime d;
		
		int next(final DateTime d) {
			if (this.d != null && d.isAfter(this.d)) {
				this.d = d;
				count = 0;
			}
			return count++;
		}
	}
}
