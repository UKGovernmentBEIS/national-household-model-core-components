package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class GetPredominantWallType extends StructureFunction<WallConstructionType> {
	@Inject
	public GetPredominantWallType(final IDimension<StructureModel> structure) {
		super(structure);
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
			throw new IllegalStateException("Dwelling has no predominant wallt type. This probably means it has no external walls. " + scope.getDwellingID());
		}

		return maxType;
	}
}
