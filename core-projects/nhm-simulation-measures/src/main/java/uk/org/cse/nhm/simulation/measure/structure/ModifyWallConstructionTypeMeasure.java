package uk.org.cse.nhm.simulation.measure.structure;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.IStorey;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulation.measure.AbstractMeasure;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class ModifyWallConstructionTypeMeasure extends AbstractMeasure
		implements IModifier<StructureModel> {
	private final IDimension<StructureModel> structureDimension;
	private final WallConstructionType wallType;

	@AssistedInject
	public ModifyWallConstructionTypeMeasure(
			final IDimension<StructureModel> structureDimension,
			@Assisted final WallConstructionType wallType) {
		this.structureDimension = structureDimension;
		this.wallType = wallType;
	}

	@Override
	public boolean doApply(final ISettableComponentsScope components, final ILets lets)
			throws NHMException {

        components.modify(structureDimension, this);
        return true;
    }

	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}
	
	/**
	 * Only houses with external walls qualify for this measure.
	 * 
	 * @see uk.org.cse.nhm.simulator.scope.IComponentsAction#isSuitable(uk.org.cse.nhm.simulator.scope.IComponentsScope, ILets)
	 */
	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		final StructureModel structure = scope.get(structureDimension);
		for (final IStorey story : structure.getStoreys()) {
			for (final IMutableWall wall : story.getWalls()) {
				if(WallConstructionType.getExternalWallTypes().contains(wall.getWallConstructionType())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean modify(final StructureModel modifiable) {
		for (final IStorey story : modifiable.getStoreys()) {
			for (final IMutableWall wall : story.getWalls()) {
				if(WallConstructionType.getExternalWallTypes().contains(wall.getWallConstructionType())) {
					wall.setWallConstructionType(wallType);
				}
			}
		}
		return true;
	}

	/**
	 * Returns the wall type for test purposes.
	 * 
	 * @since 4.2.0
	 * @return
	 */
	protected WallConstructionType getWallConstructionType() {
		return wallType;
	}

}
