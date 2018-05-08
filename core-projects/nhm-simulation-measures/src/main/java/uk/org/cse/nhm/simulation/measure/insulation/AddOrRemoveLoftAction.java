package uk.org.cse.nhm.simulation.measure.insulation;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class AddOrRemoveLoftAction extends AbstractNamed implements IComponentsAction {

    private final IDimension<StructureModel> structure;
    private final boolean add;

    @AssistedInject
    AddOrRemoveLoftAction(
            final IDimension<StructureModel> structure,
            @Assisted final boolean add) {
        this.structure = structure;
        this.add = add;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets)
            throws NHMException {
        scope.modify(structure, new IModifier<StructureModel>() {

            @Override
            public boolean modify(final StructureModel modifiable) {
                if (modifiable.getHasLoft() == add) {
                    return false;
                }
                if (!add) {
                    modifiable.setRoofInsulationThickness(0);
                }
                modifiable.setHasLoft(add);
                return true;
            }

        });
        return true;
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return true;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return true;
    }
}
