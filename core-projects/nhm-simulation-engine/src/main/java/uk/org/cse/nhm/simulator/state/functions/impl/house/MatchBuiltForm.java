package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.energycalculator.api.types.BuiltFormType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchBuiltForm extends StructureFunction<Boolean> implements IComponentsFunction<Boolean> {

    private final BuiltFormType builtForm;

    @Inject
    public MatchBuiltForm(final IDimension<StructureModel> structure, @Assisted final BuiltFormType builtForm) {
        super(structure);
        this.builtForm = builtForm;
    }

    @Override
    public Boolean compute(final IComponentsScope scope, final ILets lets) {
        return getStructure(scope).getBuiltFormType().equals(builtForm);
    }
}
