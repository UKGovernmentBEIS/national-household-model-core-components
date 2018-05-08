package uk.org.cse.nhm.simulator.reset.walls;

import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.reset.AbstractScopedThingFunction;
import uk.org.cse.nhm.simulator.state.IDimension;

public class WallUValueFunction extends AbstractScopedThingFunction<Double, IWall> {

    @AssistedInject
    WallUValueFunction(final ILogEntryHandler log,
            final IDimension<StructureModel> structureDimension) {
        super(ResetWallsAction.CURRENT_WALL_LET_IDENTITY, IWall.class, log, structureDimension);
    }

    @Override
    protected Double doFail() {
        return 0d;
    }

    @Override
    protected Double doCompute(final IWall wall) {
        return wall.getUValue();
    }
}
