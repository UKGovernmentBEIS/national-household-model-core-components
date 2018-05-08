package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Adapts a number valued function into a boolean valued function by adding a
 * test to it.
 *
 * @author hinton
 *
 */
public class MatchNumberFunction extends AbstractNamed implements IComponentsFunction<Boolean> {

    private final IComponentsFunction<Number> delegate;
    private final Predicate<Double> predicate;

    @Inject
    public MatchNumberFunction(
            @Assisted final IComponentsFunction<Number> delegate,
            @Assisted final Predicate<Double> predicate) {
        this.delegate = delegate;
        this.predicate = predicate;
    }

    @Override
    public Boolean compute(final IComponentsScope scope, final ILets lets) {
        Double delegateValue = null;
        try {
            delegateValue = delegate.compute(scope, lets).doubleValue();
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return predicate.apply(delegateValue);
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
