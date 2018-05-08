package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * A function which implements rounding another function to a bunch of steps
 *
 * @author hinton
 *
 */
public class SteppedFunction extends AbstractNamed implements IComponentsFunction<Number> {

    private final SortedSet<Double> steps = new TreeSet<Double>();
    private final IComponentsFunction<Number> delegate;
    private final Direction direction;

    public enum Direction {
        UP, DOWN, NEAREST
    }

    @Inject
    public SteppedFunction(@Assisted final List<Double> steps,
            @Assisted final IComponentsFunction<Number> delegate,
            @Assisted final Direction direction) {
        this.delegate = delegate;
        this.direction = direction;
        this.steps.addAll(steps);
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final Double delegateValue = delegate.compute(scope, lets).doubleValue();
        return getValue(delegateValue);
    }

    private Double getValue(final double value) {
        if (value > steps.last() && direction == Direction.UP) {
            return value;
        } else if (value < steps.first() && direction == Direction.DOWN) {
            return value;
        } else {
            final SortedSet<Double> headSet = steps.headSet(value);

            // because we have already checked we are inside the set of values,
            // if headset is empty that means that value = the min value, in which case
            // we return the min value exactly.
            if (headSet.isEmpty()) {
                return steps.first();
            }

            // otherwise, we are between the last element of the headset and
            // the first element of the tailset
            if (direction == Direction.DOWN) {
                final double below = headSet.last();
                return below;
            } else if (direction == Direction.UP) {
                final double above = steps.tailSet(value).first();
                return above;
            } else {
                final SortedSet<Double> tailSet = steps.tailSet(value);
                final double below;
                final double above;

                if (headSet.isEmpty()) {
                    below = above = tailSet.first();
                } else if (tailSet.isEmpty()) {
                    below = above = headSet.last();
                } else {
                    below = headSet.last();
                    above = tailSet.first();
                }

                if (value - below >= above - value) {
                    return above;
                } else {
                    return below;
                }
            }
        }
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return delegate.getDependencies();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return delegate.getChangeDates();
    }
}
