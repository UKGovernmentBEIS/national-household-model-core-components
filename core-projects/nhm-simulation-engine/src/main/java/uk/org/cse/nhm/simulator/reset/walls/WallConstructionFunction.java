package uk.org.cse.nhm.simulator.reset.walls;

import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.reset.AbstractScopedThingFunction;
import uk.org.cse.nhm.simulator.state.IDimension;

public class WallConstructionFunction extends AbstractScopedThingFunction<WallConstructionType, IWall> {
	@AssistedInject
	WallConstructionFunction(final ILogEntryHandler log,
						   final IDimension<StructureModel> structureDimension) {
		super(ResetWallsAction.CURRENT_WALL_LET_IDENTITY, IWall.class, log, structureDimension);
	}
	
	@Override
	protected WallConstructionType doFail() {
		return null;
	}
	
	@Override
	protected WallConstructionType doCompute(final IWall wall) {
		return wall.getWallConstructionType();
	}
}
