package uk.org.cse.nhm.simulator.reset.doors;

import javax.inject.Inject;

import uk.org.cse.nhm.energycalculator.api.types.DoorType;
import uk.org.cse.nhm.hom.structure.Door;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.reset.AbstractScopedThingFunction;
import uk.org.cse.nhm.simulator.state.IDimension;

public class DoorTypeFunction extends AbstractScopedThingFunction<DoorType, Door> {

    @Inject
    protected DoorTypeFunction(
            final ILogEntryHandler log, final IDimension<StructureModel> structureDimension) {
        super(ResetDoorsAction.DOOR_SCOPE_KEY, Door.class, log, structureDimension);
    }

    @Override
    protected DoorType doFail() {
        return null;
    }

    @Override
    protected DoorType doCompute(final Door door) {
        return door.getDoorType();
    }

}
