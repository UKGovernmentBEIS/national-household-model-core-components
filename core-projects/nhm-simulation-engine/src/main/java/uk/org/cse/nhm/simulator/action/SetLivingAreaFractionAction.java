package uk.org.cse.nhm.simulator.action;

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

/**
 * Implementation of an action which sets the living area fraction of a
 * dwelling.
 *
 * @author hinton
 *
 */
public class SetLivingAreaFractionAction extends AbstractNamed implements IComponentsAction, IModifier<StructureModel> {

    private final double fraction;
    private final IDimension<StructureModel> structure;

    @AssistedInject
    public SetLivingAreaFractionAction(
            final IDimension<StructureModel> structure,
            @Assisted final double fraction) {
        this.fraction = fraction;
        this.structure = structure;
        if (fraction < 0 || fraction > 1) {
            throw new IllegalArgumentException(
                    "Living area fraction should be between 0 and 1 inclusive, not "
                    + fraction);
        }
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets)
            throws NHMException {
        scope.modify(structure, this);
        return true;
    }

    @Override
    public boolean modify(final StructureModel modifiable) {
        modify(modifiable, fraction);
        return true;
    }

    private static StructureModel modify(final StructureModel copy, final double fraction) {
        copy.setLivingAreaProportionOfFloorArea(fraction);
        return copy;
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
