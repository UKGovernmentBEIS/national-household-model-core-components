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
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SetInterzoneSpecificHeatTransferAction extends AbstractNamed implements IComponentsAction {

    private final IComponentsFunction<Number> interzoneSpecificHeatTransfer;
    private final IDimension<StructureModel> structureDimension;

    @AssistedInject
    public SetInterzoneSpecificHeatTransferAction(
            final IDimension<StructureModel> structureDimension,
            @Assisted("interzoneSpecificHeatTransfer") final IComponentsFunction<Number> interzoneSpecificHeatTransfer
    ) {
        this.structureDimension = structureDimension;
        this.interzoneSpecificHeatTransfer = interzoneSpecificHeatTransfer;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean apply(ISettableComponentsScope scope, ILets lets) throws NHMException {
        final Number value = interzoneSpecificHeatTransfer.compute(scope, lets);

        if (value.doubleValue() < 0) {
            throw new RuntimeException("Interzone specific heat transfer must be at least 0 " + value.doubleValue());
        }

        scope.modify(
                structureDimension,
                new SetInterzoneSpecificHeatTransferAction.Modifier(value)
        );
        return true;
    }

    @Override
    public boolean isSuitable(IComponentsScope scope, ILets lets) {
        return true;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return true;
    }

    private static final class Modifier implements IModifier<StructureModel> {

        private final Number interzoneSpecificHeatTransfer;

        public Modifier(final Number interzoneSpecificHeatTransfer) {
            this.interzoneSpecificHeatTransfer = interzoneSpecificHeatTransfer;
        }

        @Override
        public boolean modify(StructureModel modifiable) {
            modifiable.setInterzoneSpecificHeatLoss(interzoneSpecificHeatTransfer.doubleValue());
            return true;
        }
    }
}
