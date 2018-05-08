package uk.org.cse.nhm.simulator.state.functions.impl.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Boolean components function which implements Any, All and None
 *
 * @author hinton
 *
 */
public class LogicalCombination extends AbstractNamed implements IComponentsFunction<Boolean> {

    public enum Mode {
        ANY, ALL, NONE
    }

    private final Mode mode;
    private final List<? extends IComponentsFunction<Boolean>> inputs;

    @Inject
    public LogicalCombination(@Assisted final Mode mode,
            @Assisted final List<? extends IComponentsFunction<Boolean>> inputs) {
        this.mode = mode;
        this.inputs = inputs;
    }

    @Override
    public Boolean compute(final IComponentsScope scope, final ILets lets) {
        switch (mode) {
            case ALL:
                return and(scope, lets);
            case ANY:
                return or(scope, lets);
            case NONE:
                return !or(scope, lets);
            default:
                return null;
        }
    }

    private Boolean or(final IComponentsScope scope, final ILets lets) {
        for (final IComponentsFunction<Boolean> fn : inputs) {
            final Boolean b = fn.compute(scope, lets);
            if (b == null) {
                return null;
            } else if (b) {
                return true;
            }
        }
        return false;
    }

    private Boolean and(final IComponentsScope scope, final ILets lets) {
        for (final IComponentsFunction<Boolean> fn : inputs) {
            final Boolean b = fn.compute(scope, lets);
            if (b == null) {
                return null;
            } else if (!b) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        final HashSet<IDimension<?>> dependencies = new HashSet<IDimension<?>>();
        for (final IComponentsFunction<?> cf : inputs) {
            dependencies.addAll(cf.getDependencies());
        }
        return dependencies;
    }

    @Override
    public Set<DateTime> getChangeDates() {
        final HashSet<DateTime> changeDates = new HashSet<DateTime>();
        for (final IComponentsFunction<?> cf : inputs) {
            changeDates.addAll(cf.getChangeDates());
        }
        return changeDates;
    }
}
