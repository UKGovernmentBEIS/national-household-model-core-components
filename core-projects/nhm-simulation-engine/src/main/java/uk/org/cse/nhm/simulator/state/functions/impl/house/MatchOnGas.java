package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchOnGas extends AbstractNamed implements IComponentsFunction<Boolean> {

    private final IDimension<StructureModel> structDimension;

    @AssistedInject
    public MatchOnGas(
            final IDimension<StructureModel> structDimension
    ) {
        this.structDimension = structDimension;
    }

    @Override
    public Boolean compute(final IComponentsScope scope, final ILets lets) {
        return scope.get(structDimension).isOnGasGrid();
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
