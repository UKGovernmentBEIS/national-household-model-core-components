package uk.org.cse.nhm.simulation.reporting.probes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.hooks.AggregateHookAction;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.utility.DeduplicatingMap;

/**
 * This is similar to {@link AggregateHookAction} but with a few key changes related to the fact that it has to work on a scope
 * rather than the raw state.
 * 
 * @author hinton
 *
 */
public class AggregateActionProbe extends AbstractNamed implements IStateAction {
	private final IStateAction delegate;
	private final List<IComponentsFunction<?>> cuts;
	private final List<IAggregationFunction> values;
	private final ILogEntryHandler output;
	private final ITimeDimension time;
	private final List<String> cutNames;
	private String reportName;
	
	@AssistedInject
	AggregateActionProbe(
			@Assisted final IStateAction delegate, 
			@Assisted final List<IComponentsFunction<?>> cuts, 
			@Assisted final List<IAggregationFunction> values, 
			final ILogEntryHandler output, 
			final ITimeDimension time) {
		super();
		this.delegate = delegate;
		this.cuts = cuts;
		this.values = values;
		this.output = output;
		this.time = time;
		
		this.cutNames = AggregateHookAction.getDistinctNames(cuts);
	}
	
	@Override
	public void setIdentifier(final Name newName) {
		super.setIdentifier(newName);
		this.reportName = newName.getName();
	}
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public Set<IDwelling> apply(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) throws NHMException {
        final Set<IDwelling> suitable = delegate.getSuitable(scope, dwellings, lets);

		cut(scope, "suitable", suitable, lets);
		cut(scope, "unsuitable", Sets.difference(dwellings, suitable), lets);
        
		final Set<IDwelling> successful = scope.apply(delegate, dwellings, lets);
		
		cut(scope, "successful", successful, lets);
		cut(scope, "unsuccessful", Sets.difference(dwellings, successful), lets);
        
		return suitable;
	}

    @Override
    public Set<IDwelling> getSuitable(final IStateScope scope, final Set<IDwelling> dwellings, final ILets lets) {
        return delegate.getSuitable(scope, dwellings, lets);
    }
    
	private List<Object> computeCuts(final IStateScope scope, final IDwelling d) {
		final ArrayList<Object> al = new ArrayList<>(cuts.size());
		
		final Optional<IComponentsScope> s_ = scope.getComponentsScope(d);
		final IComponentsScope s;
		
		if (s_.isPresent()) {
			s = s_.get();
		} else {
			s = scope.getState().detachedScope(d);
		}
		
		for (final IComponentsFunction<?> c : cuts) {
			al.add(c.compute(s, ILets.EMPTY));
		}
		
		return al;
	}

	private ImmutableMap<String, Double> evaluate(final IStateScope scope, final Set<IDwelling> set, final ILets lets) {
		final DeduplicatingMap.Builder<Double> builder = DeduplicatingMap.stringBuilder();
		for (final IAggregationFunction iaf : values) {
			final double value = iaf.evaluate(scope, lets, set);
			builder.put(iaf.getIdentifier().getName(), value);
		}
		return builder.build();
	}
	
	private void cut(final IStateScope scope, final String name, final Set<IDwelling> set, final ILets lets) {
		final Map<String, String> compoundKey = new HashMap<String, String>();
		compoundKey.put("group", name);
		
		if (cuts.isEmpty()) {
			final ImmutableMap<String, Double> values = evaluate(scope, set, lets);
			
			final AggregateLogEntry ale = new AggregateLogEntry(
					reportName, 
					Collections.<String>emptySet(),
					ImmutableMap.copyOf(compoundKey),
					scope.getState().get(time, null).get(lets), 
					values);
			
			output.acceptLogEntry(ale);
		} else {
			final HashMultimap<List<Object>, IDwelling> groups = HashMultimap.create();
			for (final IDwelling d : set) {
				final List<Object> cut = computeCuts(scope, d);
				groups.put(cut, d);
			}
			
			for (final List<Object> subkey : groups.keySet()) {
				for (int i = 0; i<cutNames.size(); i++) {
					compoundKey.put(cutNames.get(i), String.valueOf(subkey.get(i)));
				}
				
				final ImmutableMap<String, Double> values = evaluate(scope, groups.get(subkey), lets);
				
				final AggregateLogEntry ale = new AggregateLogEntry(
						reportName, 
						Collections.<String>emptySet(),
						ImmutableMap.copyOf(compoundKey),
						scope.getState().get(time, null).get(lets),
						values);
				
				output.acceptLogEntry(ale);	
			}
		}
	}
}
