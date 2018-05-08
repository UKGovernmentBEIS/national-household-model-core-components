package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

import uk.org.cse.nhm.hom.people.People;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class GetNumberOfOccupants extends AbstractNamed implements IComponentsFunction<Integer> {

    private final IDimension<People> peopleDimension;

    @Inject
    public GetNumberOfOccupants(final IDimension<People> peopleDimension) {
        this.peopleDimension = peopleDimension;
    }

    @Override
    public Integer compute(IComponentsScope scope, ILets lets) {
        return scope.get(peopleDimension).getNumberOfPeople();
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return ImmutableSet.<IDimension<?>>of(peopleDimension);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return ImmutableSet.of();
    }
}
