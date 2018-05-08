package uk.org.cse.nhm.simulator.reset.storey;

import javax.inject.Inject;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.reset.AbstractScopedThingFunction;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class FloorIsNotExposedFunction extends AbstractScopedThingFunction<Boolean, Storey> implements IComponentsFunction<Boolean> {

    @Inject
    protected FloorIsNotExposedFunction(
            final ILogEntryHandler log, final IDimension<StructureModel> structureDimension) {
        super(ResetFloorsAction.STOREY_SCOPE_KEY, Storey.class, log, structureDimension);
    }

    @Override
    protected Boolean doCompute(final Storey wall) {
        return wall.getFloorLocationType().isInContactWithGround();
    }

    @Override
    protected Boolean doFail() {
        return false;
    }
}
