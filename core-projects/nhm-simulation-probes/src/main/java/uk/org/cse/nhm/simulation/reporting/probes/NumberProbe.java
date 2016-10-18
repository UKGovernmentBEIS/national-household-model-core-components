package uk.org.cse.nhm.simulation.reporting.probes;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.utility.DeduplicatingMap;

public class NumberProbe extends AbstractNamed implements IComponentsFunction<Number> {
    private static final String UNDEFINED = "undefined";
    public static final String VALUE_KEY = "Value";
    public static final String BRANCH_KEY = "Branch";

    private final List<IComponentsFunction<? extends Object>> probes;
    private final IComponentsFunction<Number> delegate;

    private final IProbeCollector collector;
    private final ITimeDimension time;
    private final Set<IDimension<?>> dependencies;

    @Inject
    public NumberProbe(
            final ITimeDimension time,
            final IProbeCollector collector,

            @Assisted final IComponentsFunction<Number> delegate,
            @Assisted final List<IComponentsFunction<?>> probes) {
        this.time = time;
        this.collector = collector;

        this.delegate = delegate;
        this.probes = probes;

        final Builder<IDimension<?>> builder = ImmutableSet.<IDimension<?>> builder();

        builder.addAll(delegate.getDependencies());

        for (final IComponentsFunction<?> p : probes) {
            builder.addAll(p.getDependencies());
        }

        dependencies = builder.build();
    }

    @Override
    public Number compute(final IComponentsScope scope, final ILets lets) {
        final Number result = delegate.compute(scope, lets);

        final DeduplicatingMap.Builder<Object> captured = DeduplicatingMap.stringBuilder();

        for (final IComponentsFunction<? extends Object> probe : probes) {
            if (probe instanceof IProbingFunction) {
            	final Map<String, Object> compute = ((IProbingFunction) probe).compute(scope, lets);
            	if (compute != null) {
            		for (final Map.Entry<String, Object> e : compute.entrySet()) {
            			captured.put(e.getKey(), undefined(e.getValue()));
            		}
            	}
            } else {
                try {
                    final Object value = probe.compute(scope, lets);
                    captured.put(probe.toString(), undefined(value));
                } catch (final NHMException uve) {
                    captured.put(probe.toString(), UNDEFINED);
                }
            }
        }

        captured.put(VALUE_KEY, result);

        captured.put(BRANCH_KEY, scope.getTag().getIdentifier().getPath());

        collector.collectProbe(getIdentifier().getName(),
        		scope.get(time).get(lets),
                scope.getDwellingID(),
                scope.getDwelling().getWeight(),
                captured.build());

        return result;
    }

    private Object undefined(final Object value) {
		return value == null ? UNDEFINED : value;
	}

	@Override
    public Set<IDimension<?>> getDependencies() {
        return dependencies;
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return delegate.getChangeDates();
    }
}
