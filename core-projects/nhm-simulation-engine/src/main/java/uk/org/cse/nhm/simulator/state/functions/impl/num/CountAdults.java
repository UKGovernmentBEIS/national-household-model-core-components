package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class CountAdults extends AbstractNamed implements IComponentsFunction<Number> {

    private final IDimension<People> people;

    @Inject
    public CountAdults(final IDimension<People> people) {
        this.people = people;
    }

    @Override
    public Number compute(final IComponentsScope scope, final ILets lets) {
        return scope.get(people).getAdults();
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return ImmutableSet.<IDimension<?>>of(people);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }

}
