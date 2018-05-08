package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SimYearFunction extends AbstractNamed implements
        IComponentsFunction<Number> {

    private final ITimeDimension time;
    private final Set<DateTime> changeDates;
    private final Optional<XForesightLevel> foresight;

    @AssistedInject
    public SimYearFunction(
            final ITimeDimension time,
            @Named(SimulatorConfigurationConstants.START_DATE) final DateTime start,
            @Named(SimulatorConfigurationConstants.END_DATE) final DateTime end,
            @Assisted final Optional<XForesightLevel> foresight) {
        this.time = time;
        this.foresight = foresight;

        DateTime current = new DateTime(start.getYear(), 1, 1, 0, 0);
        final int endYear = end.getYear();

        final Builder<DateTime> builder = ImmutableSet.<DateTime>builder();
        while (current.getYear() <= endYear) {
            builder.add(current);
            current = current.plusYears(1);
        }
        changeDates = builder.build();
    }

    @Override
    public Number compute(final IComponentsScope scope, final ILets lets) {
        return scope.get(time).get(foresight, lets).getYear();
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.<IDimension<?>>singleton(time);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return changeDates;
    }
}
