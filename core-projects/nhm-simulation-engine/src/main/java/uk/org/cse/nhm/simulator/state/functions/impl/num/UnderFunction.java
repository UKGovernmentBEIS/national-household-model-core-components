package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * A function which is evaluated under some hypothetical conditions: 1. If a
 * snapshot name was passed in, we lookup that snapshot and use it. Otherwise we
 * start out hypothesis from the current state. 2. Apply all the hypothesis
 * actions which were passed in. 3. Evaluate the delegate function. 4. Throw
 * away all the changes we made.
 */
public class UnderFunction extends AbstractNamed implements IComponentsFunction<Number> {

    private final IComponentsFunction<? extends Number> delegate;
    private final Optional<String> snapshot;
    private final List<IComponentsAction> hypotheses;

    public UnderFunction(final IComponentsFunction<? extends Number> delegate, final Optional<String> snapshot, final List<IComponentsAction> hypotheses) {
        this.delegate = delegate;
        this.snapshot = snapshot;
        this.hypotheses = hypotheses;
    }

    @Override
    public Number compute(final IComponentsScope scope, final ILets lets) {
        ISettableComponentsScope hypothesis;

        if (snapshot.isPresent()) {
            hypothesis = lets.get(snapshot.get(), IHypotheticalComponentsScope.class).get();
        } else {
            hypothesis = scope.createHypothesis();
        }

        for (final IComponentsAction h : hypotheses) {
            hypothesis.apply(h, lets);
        }

        return delegate.compute(hypothesis, lets);
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
