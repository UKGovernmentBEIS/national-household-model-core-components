package uk.org.cse.nhm.simulator.hooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.utility.DeduplicatingMap;

public class AggregateHookAction extends AbstractNamed implements IHookRunnable {
	private final ITimeDimension time;
	private final List<IDwellingSet> contents;
	private final List<IComponentsFunction<?>> cuts;
	private final List<String> cutNames;
	private final List<IAggregationFunction> values;
	private final ILogEntryHandler output;
	private String reportName = "unnamed";
	
	@AssistedInject
	AggregateHookAction(
			final ITimeDimension time, 
			final ILogEntryHandler output,
			@Assisted final List<IDwellingSet> contents, 
			@Assisted final List<IComponentsFunction<?>> cuts, 
			@Assisted final List<IAggregationFunction> values) {
		this.time = time;
		this.contents = contents;
		this.cuts = cuts;
		this.cutNames = getDistinctNames(cuts);
		this.values = values;
		this.output = output;
	}
	
	public static List<String> getDistinctNames(final List<IComponentsFunction<?>> cuts) {
		final Builder<String> names = ImmutableList.builder();
		final Multiset<String> nameCounter = HashMultiset.create();
		nameCounter.add("group");
		for (final IComponentsFunction<?> cut : cuts) {
			final String clean = clean(cut.getIdentifier().getName());
			nameCounter.add(clean);
			final int count = nameCounter.count(clean);
			names.add(clean + (count > 1 ? "_" + count : ""));
		}
		return names.build();
	}
	
	
	private static String clean(final String name) {
		return name.replace(".", "-");
	}
	
	@Override
	public void setIdentifier(final Name newName) {
		super.setIdentifier(newName);
		this.reportName = newName.getName();
	}

	@Override
	public void run(final IStateScope scope, final DateTime date, final Set<IStateChangeSource> causes, final ILets lets) {
		final ImmutableSet.Builder<String> causeNames = ImmutableSet.builder();
		for (final IStateChangeSource ssc : causes) {
			causeNames.add(ssc.getIdentifier().getName());
		}
		
		final Set<String> causeNames_ = causeNames.build();
		for (final IDwellingSet s : contents) {
			cut(scope.getState(), s.getIdentifier().getName(),
                s.get(scope.getState(), lets), causeNames_, lets);
		}
	}

	private void cut(final IState state, final String name, final Set<IDwelling> set, final Set<String> causeNames_, final ILets lets) {
		final Map<String, String> compoundKey = new HashMap<String, String>();
		compoundKey.put("group", name);
		
		if (cuts.isEmpty()) {
			final ImmutableMap<String, Double> values = evaluate(state, set, lets);
			
			final AggregateLogEntry ale = new AggregateLogEntry(
					reportName, 
					causeNames_,
					ImmutableMap.copyOf(compoundKey),
					state.get(time, null).get(lets), 
					values);
			
			output.acceptLogEntry(ale);
		} else {
			final HashMultimap<List<Object>, IDwelling> groups = HashMultimap.create();
			for (final IDwelling d : set) {
				final List<Object> cut = computeCuts(state, d);
				groups.put(cut, d);
			}
			
			for (final List<Object> subkey : groups.keySet()) {
				for (int i = 0; i<cutNames.size(); i++) {
					compoundKey.put(cutNames.get(i), String.valueOf(subkey.get(i)));
				}
				
				final ImmutableMap<String, Double> values = evaluate(state, groups.get(subkey), lets);
				
				final AggregateLogEntry ale = new AggregateLogEntry(
						reportName, 
						causeNames_,
						ImmutableMap.copyOf(compoundKey),
						state.get(time, null).get(lets), 
						values);
				
				output.acceptLogEntry(ale);	
			}
		}
	}

	

	private List<Object> computeCuts(final IState state, final IDwelling d) {
		final ArrayList<Object> al = new ArrayList<>(cuts.size());
		
		final IComponentsScope s = state.detachedScope(d);
		for (final IComponentsFunction<?> c : cuts) {
			al.add(c.compute(s, ILets.EMPTY));
		}
		
		return al;
	}

	private ImmutableMap<String, Double> evaluate(final IState state, final Set<IDwelling> set, final ILets lets) {
		final DeduplicatingMap.Builder<Double> builder = DeduplicatingMap.stringBuilder();
		for (final IAggregationFunction iaf : values) {
			final double value = iaf.evaluate(state, lets, set);
			builder.put(iaf.getIdentifier().getName(), value);
		}
		return builder.build();
	}
}
