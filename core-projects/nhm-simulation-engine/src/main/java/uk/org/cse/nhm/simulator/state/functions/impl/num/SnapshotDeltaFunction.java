package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Used for doing before and after comparisons of a function.
 *
 * Looks up the before and after snapshots. Evaluates the delegate function
 * under each of them. Returns f(after) - f(before).
 */
public class SnapshotDeltaFunction extends AbstractNamed implements IComponentsFunction<Double> {

    private final IComponentsFunction<? extends Number> delegate;
    private final String before;
    private final String after;

    public SnapshotDeltaFunction(final IComponentsFunction<? extends Number> delegate, final String before, final String after) {
        this.delegate = delegate;
        this.before = before;
        this.after = after;
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final IHypotheticalComponentsScope snapshotBefore = lets.get(before, IHypotheticalComponentsScope.class).get();
        final IHypotheticalComponentsScope snapshotAfter = lets.get(after, IHypotheticalComponentsScope.class).get();

        return delegate.compute(snapshotAfter, lets).doubleValue() - delegate.compute(snapshotBefore, lets).doubleValue();
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
