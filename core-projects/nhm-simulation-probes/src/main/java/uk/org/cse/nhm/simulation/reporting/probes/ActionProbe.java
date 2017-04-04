package uk.org.cse.nhm.simulation.reporting.probes;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.utility.DeduplicatingMap;
import uk.org.cse.nhm.utility.DeduplicatingMap.Builder;

public class ActionProbe extends AbstractNamed implements IComponentsAction {
	private static final String UNDEFINED = "undefined";
	public static final String SUCCEEDED = "Succeeded";
	private static final String BEFORE = " (before)";
	private static final String AFTER = " (after)";

	private final List<IComponentsFunction<? extends Object>> probes;
	private final Optional<IComponentsAction> delegate;
	private final IProbeCollector collector;
	private final ITimeDimension time;

	@Inject
	public ActionProbe(
			final ITimeDimension time,
			final IProbeCollector collector,
			@Assisted final Optional<IComponentsAction> delegate,
			@Assisted final List<IComponentsFunction<? extends Object>> probes
			) {
		this.probes = probes;
		this.delegate = delegate;
		this.collector = collector;
		this.time = time;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	private void collectValuesOfProbes(final ISettableComponentsScope scope, final ILets lets, final String suffix, final Builder<Object> no) {
		for (final IComponentsFunction<?> p : probes) {
			if (p instanceof IProbingFunction) {
				@SuppressWarnings("unchecked")
				final Map<String, Object> subValues = (Map<String, Object>) p.compute(scope, lets);
				for (final String k : subValues.keySet()) {
					no.put(k + suffix, subValues.get(k));
				}
			} else {
				try {
					final Object value = p.compute(scope, lets);
					no.put(p.toString() + suffix, value == null ? UNDEFINED : value);
				} catch (final NHMException uve) {
					no.put(p.toString() + suffix, UNDEFINED);
				}
			}
		}
	}
	
	private void collectBlankValues(final String suffix, final Builder<Object> no) {
		for (final IComponentsFunction<?> p : probes) {
			if (p instanceof IProbingFunction) {

				for (final String k : ((IProbingFunction) p).getHeaders()) {
					no.put(k + suffix, "n/a");
				}
				
			} else {
				no.put(p.toString() + suffix, "n/a");
			}
		}
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		if (delegate.isPresent()) {
			return delegate.get().isAlwaysSuitable();
		} else {
			return true;
		}
	}
	
	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		if (delegate.isPresent()) {
			return delegate.get().isSuitable(scope, lets);
		} else {
			return true;
		}
	}
	
	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		final Builder<Object> no = DeduplicatingMap.stringBuilder();
		
		final boolean success;
		if (delegate.isPresent()) {
			collectValuesOfProbes(scope, lets, BEFORE, no);
			success = scope.apply(delegate.get(), lets);
			no.put(SUCCEEDED, success);
			if(success) {
				collectValuesOfProbes(scope, lets, AFTER, no);
			} else {
				collectBlankValues(AFTER, no);
			}
		} else {
			collectValuesOfProbes(scope, lets, "", no);
			success = true;
		}
		
		collector.collectProbe(
				getIdentifier().getName(),  
				scope.get(time).get(lets), 
				scope.getDwellingID(), 
				scope.getDwelling().getWeight(), 
				no.build());			
		return success;
	}
}
