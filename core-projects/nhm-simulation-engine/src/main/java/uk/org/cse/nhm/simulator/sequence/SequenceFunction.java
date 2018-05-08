package uk.org.cse.nhm.simulator.sequence;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SequenceFunction extends AbstractNamed implements IComponentsFunction<Number> {

    private final IComponentsFunction<Number> delegate;
    private final List<ISequenceSpecialAction> variableActions;
    private ImmutableSet<DateTime> changeDates = null;
    private final ImmutableSet<IDimension<?>> deps;

    @AssistedInject
    public SequenceFunction(
            @Assisted final IComponentsFunction<Number> delegate,
            @Assisted final List<ISequenceSpecialAction> variableActions) {
        super();
        this.delegate = delegate;
        this.variableActions = variableActions;

        final ImmutableSet.Builder<IDimension<?>> deps = ImmutableSet.builder();

        deps.addAll(delegate.getDependencies());

        for (final ISequenceSpecialAction ssa : variableActions) {

            deps.addAll(ssa.getDependencies());
        }

        this.deps = deps.build();
    }

    @Override
    public Number compute(final IComponentsScope scope, ILets lets) {
        for (final ISequenceSpecialAction ssa : variableActions) {
            lets = ssa.reallyApply(scope, lets);
        }
        return delegate.compute(scope, lets);
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return deps;
    }

    private void computeChangeDates() {
        if (changeDates == null) {
            final ImmutableSet.Builder<DateTime> changeDates = ImmutableSet.builder();
            changeDates.addAll(delegate.getChangeDates());
            for (final ISequenceSpecialAction ssa : variableActions) {
                changeDates.addAll(ssa.getChangeDates());
            }
            this.changeDates = changeDates.build();
        }
    }

    @Override
    public Set<DateTime> getChangeDates() {
        computeChangeDates();
        return changeDates;
    }
}
