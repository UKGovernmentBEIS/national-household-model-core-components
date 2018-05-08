package uk.org.cse.nhm.simulator.state.functions.impl;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class WarningFunction implements IComponentsFunction<Number> {

    final ILogEntryHandler log;
    final Name owner;
    final IComponentsFunction<Number> delegate;
    final double lowerBound, upperBound;
    final boolean lowerClamp, upperClamp;
    final String description;

    @AssistedInject
    public WarningFunction(final ILogEntryHandler log,
            @Assisted final Name owner,
            @Assisted final String description,
            @Assisted final IComponentsFunction<Number> delegate,
            @Assisted("lowerBound") final double lowerBound,
            @Assisted("upperBound") final double upperBound,
            @Assisted("lowerClamp") final boolean lowerClamp,
            @Assisted("upperClamp") final boolean upperClamp) {
        this.log = log;
        this.owner = owner;
        this.delegate = delegate;
        this.description = description;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.lowerClamp = lowerClamp;
        this.upperClamp = upperClamp;
    }

    private void warn(final double d, final boolean clamped, final boolean bound) {
        log.acceptLogEntry(new WarningLogEntry(description + " out of bounds",
                ImmutableMap.of("location", String.valueOf(owner),
                        "value", String.valueOf(d),
                        "clamped", String.valueOf(clamped),
                        "bound", bound ? "upper" : "lower")));
    }

    private double checkUpper(final double d) {
        if (d > upperBound) {
            if (upperClamp) {
                warn(d, true, true);
                return upperBound;
            }
            warn(d, false, true);
        }

        return d;
    }

    private double checkLower(final double d) {
        if (d < lowerBound) {
            if (lowerClamp) {
                warn(d, true, false);
                return lowerBound;
            }
            warn(d, false, false);
        }

        return d;
    }

    @Override
    public Number compute(IComponentsScope scope, ILets lets) {
        final Number value = delegate.compute(scope, lets);

        if (value == null) {
            return value; // this ought never happen
        } else {
            return checkUpper(checkLower(value.doubleValue()));
        }
    }

    @Override
    public Name getIdentifier() {
        return delegate.getIdentifier();
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
