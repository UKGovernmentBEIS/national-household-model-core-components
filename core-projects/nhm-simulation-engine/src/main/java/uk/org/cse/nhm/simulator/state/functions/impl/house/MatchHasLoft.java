package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchHasLoft extends AbstractNamed implements
        IComponentsFunction<Boolean> {

    private final IDimension<StructureModel> structDimension;

    @Inject
    public MatchHasLoft(
            final IDimension<StructureModel> structDimension
    ) {
        this.structDimension = structDimension;
    }

    @Override
    public Boolean compute(final IComponentsScope scope, final ILets lets) {
        return scope.get(structDimension).getHasLoft();
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return ImmutableSet.<IDimension<?>>of(structDimension);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return ImmutableSet.of();
    }

}
