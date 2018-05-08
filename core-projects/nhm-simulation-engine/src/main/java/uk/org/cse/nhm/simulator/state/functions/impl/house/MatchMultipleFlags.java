package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchMultipleFlags extends AbstractNamed implements IComponentsFunction<Boolean> {

    private final IDimension<IFlags> flags;
    private final List<Glob> preconditions;

    @AssistedInject
    public MatchMultipleFlags(
            final IDimension<IFlags> flags,
            @Assisted final List<Glob> preconditions) {
        super();
        this.flags = flags;
        this.preconditions = preconditions;
    }

    @Override
    public Boolean compute(final IComponentsScope scope, final ILets lets) {
        return scope.get(flags).flagsMatch(preconditions);
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.<IDimension<?>>singleton(flags);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.<DateTime>emptySet();
    }
}
