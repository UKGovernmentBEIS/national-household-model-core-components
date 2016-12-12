package uk.org.cse.nhm.simulator.reset.walls;

import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.simulator.reset.AbstractScopedThingFunction;
import uk.org.cse.nhm.simulator.state.IDimension;

public class WallInsulationFunction extends AbstractScopedThingFunction<Double, IWall> {
	private final Set<WallInsulationType> types;

	@AssistedInject
	WallInsulationFunction(final ILogEntryHandler log,
						   final IDimension<StructureModel> structureDimension,
						   @Assisted final Set<WallInsulationType> types) {
		super(ResetWallsAction.CURRENT_WALL_LET_IDENTITY, IWall.class, log, structureDimension);
		this.types = types;
	}

	@Override
	protected Double doFail() {
		return 0d;
	}

	@Override
	protected Double doCompute(final IWall wall) {
		double acc = 0;
		for (final WallInsulationType t : types) {
			acc += wall.getWallInsulationThickness(t);
		}
		return acc;
	}
}
