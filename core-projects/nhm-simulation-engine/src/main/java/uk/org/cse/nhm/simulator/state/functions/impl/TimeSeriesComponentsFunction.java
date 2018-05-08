package uk.org.cse.nhm.simulator.state.functions.impl;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * TODO use ITimeSeries & etc
 *
 * @author hinton
 *
 * @param <T>
 */
public class TimeSeriesComponentsFunction<T> extends AbstractNamed implements IComponentsFunction<T> {

    /**
     * This is the sequence of date -> function maps that will pertain over
     * time.
     *
     * It is sorted in reverse order, to make
     * {@link #compute(IEvaluationContext, IComponents)} easier.
     */
    private final SortedMap<DateTime, T> sequence = new TreeMap<DateTime, T>(Collections.reverseOrder());
    private final T defaultValue;
    private final ITimeDimension time;
    private final Optional<XForesightLevel> foresight;

    @AssistedInject
    public TimeSeriesComponentsFunction(
            @Assisted final Optional<XForesightLevel> foresight,
            @Assisted("initial") final T initial,
            @Assisted("later") final Map<DateTime, T> later,
            final ITimeDimension time) {
        this.foresight = foresight;
        this.defaultValue = initial;
        sequence.putAll(later);
        this.time = time;
    }

    @Override
    public T compute(final IComponentsScope scope, final ILets lets) {
        final SortedMap<DateTime, T> lessOrEquals = sequence.tailMap(scope.get(time).get(foresight, lets));
        // this is in reverse order, so it will go now, day before now, day before that and so on.
        if (lessOrEquals.isEmpty()) {
            return defaultValue;
        }

        return lessOrEquals.get(lessOrEquals.firstKey());
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.<IDimension<?>>singleton(time);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return ImmutableSet.copyOf(sequence.keySet());
    }
}
