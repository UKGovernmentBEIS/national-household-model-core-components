package uk.org.cse.nhm.simulation.reporting.aggregates;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.hooks.IDwellingSet;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.functions.IAggregationFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Use this to supply a group for one of the aggregate functions to work on.
 * computing this repeatedly seems to be extraordinarily expensive, which is not
 * a huge suprise.
 */
public class GroupSummary extends AbstractNamed implements IComponentsFunction<Number> {

    private final IDwellingSet group;
    private final IAggregationFunction aggregate;

    @AssistedInject
    public GroupSummary(
            @Assisted final IDwellingSet group,
            @Assisted final IAggregationFunction aggregate) {
        this.group = group;
        this.aggregate = aggregate;
    }

    @Override
    public Number compute(final IComponentsScope scope, final ILets lets) {
        final IState state = scope.getState();
        return aggregate.evaluate(state, lets, group.get(state, lets));
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return ImmutableSet.of();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return ImmutableSet.of();
    }
}
