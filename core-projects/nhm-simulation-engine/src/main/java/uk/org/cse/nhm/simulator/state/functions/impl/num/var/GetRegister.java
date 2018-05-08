package uk.org.cse.nhm.simulator.state.functions.impl.num.var;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Gets a yielded or let variable out of the scope using
 * {@link IComponentsScope#getYieldedNumber(String)}
 */
public class GetRegister extends AbstractNamed implements IComponentsFunction<Number> {

    private final IProfilingStack profiler;
    private final String var;
    private final Optional<Double> defaultValue;
    private final IDimension<IFlags> flags;

    @AssistedInject
    public GetRegister(final IProfilingStack profiler, final IDimension<IFlags> flags, @Assisted final String var, @Assisted final Optional<Double> defaultValue) {
        this.profiler = profiler;
        this.flags = flags;
        this.var = var;
        this.defaultValue = defaultValue;
    }

    @Override
    public Number compute(final IComponentsScope scope, final ILets lets) {
        // this defines the shadowing order for variables
        final Optional<Double> result = scope.get(flags).getRegister(var).or(defaultValue);

        if (result.isPresent()) {
            return result.get();
        } else {
            throw profiler.die("Undefined value " + this, this, scope);
        }
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.<IDimension<?>>singleton(flags);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        throw new UnsupportedOperationException("get should not be used within a time-sensitive location like weather or carbon");
    }
}
