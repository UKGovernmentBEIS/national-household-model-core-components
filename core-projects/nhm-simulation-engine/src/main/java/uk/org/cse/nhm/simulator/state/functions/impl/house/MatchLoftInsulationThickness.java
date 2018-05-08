package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchLoftInsulationThickness extends StructureFunction<Boolean> implements IComponentsFunction<Boolean> {

    private final Predicate<Integer> test;

    @Inject
    public MatchLoftInsulationThickness(final IDimension<StructureModel> structure, @Assisted final Predicate<Integer> test) {
        super(structure);
        this.test = test;
    }

    @Override
    public Boolean compute(final IComponentsScope scope, final ILets lets) {
        return test.apply((int) getStructure(scope).getRoofInsulationThickness());
    }
}
