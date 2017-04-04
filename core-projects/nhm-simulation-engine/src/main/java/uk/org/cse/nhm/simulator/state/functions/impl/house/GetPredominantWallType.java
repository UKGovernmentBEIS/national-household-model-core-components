package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetPredominantWallType extends StructureFunction<WallConstructionType> {
	private final ILogEntryHandler log;

	@Inject
	public GetPredominantWallType(final IDimension<StructureModel> structure, final ILogEntryHandler log) {
		super(structure);
		this.log = log;
	}

	@Override
	public WallConstructionType compute(final IComponentsScope scope, final ILets lets) {
		final StructureModel structure = getStructure(scope);

		final double[] accumulators = new double[WallConstructionType.values().length];
		double maximum = Double.NEGATIVE_INFINITY;
		WallConstructionType maxType = null;

		for (final Storey storey : structure.getStoreys()) {
			for (final IWall wall : storey.getImmutableWalls()) {
				final WallConstructionType constructionTypeOfThisWall = wall.getWallConstructionType();
				if (constructionTypeOfThisWall.getWallType() == WallType.External) {
					final int ord = constructionTypeOfThisWall.ordinal();
					accumulators[ord] += wall.getArea();
					if (accumulators[ord] > maximum) {
						maxType = constructionTypeOfThisWall;
						maximum = accumulators[ord];
					}
				}
			}
		}

		if (maxType == null) {
			log.acceptLogEntry(new WarningLogEntry("Dwelling has no external walls. Predominant wall type will be Internal.",
					ImmutableMap.of("dwelling-id", String.valueOf(scope.getDwellingID()))));
			return WallConstructionType.Internal_Any;
		}

		return maxType;
	}
}
