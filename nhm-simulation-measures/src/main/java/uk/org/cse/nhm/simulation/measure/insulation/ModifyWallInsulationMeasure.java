package uk.org.cse.nhm.simulation.measure.insulation;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Action to enable users to set the thickness of wall insulation
 * 
 * @since 4.2.0
 */
public class ModifyWallInsulationMeasure extends
		AbstractModifyInsulationMeasure {

	@Inject
	public ModifyWallInsulationMeasure(
			final IDimension<StructureModel> structureDimension,
			@Assisted("insulationthickness") final IComponentsFunction<? extends Number> insulationThickness) {
		super(structureDimension, insulationThickness);
	}
	
	@Override
	protected IModifier<StructureModel> modifier(
			final ISettableComponentsScope scope, final ILets lets) {
		final double insulationThickness = getInsulationThickness(scope, lets);
		
		return new IModifier<StructureModel>() {
			@Override
			public boolean modify(final StructureModel modifiable) {
				boolean modified  = false ;
				
				for(final Storey s: modifiable.getStoreys()) {
					for(final IMutableWall w: s.getWalls()) {
						final Set<WallInsulationType> wallInsulationTypes = ImmutableSet.copyOf(w.getWallInsulationTypes()); // TODO This could be passed as an optional
						for(final WallInsulationType t : ImmutableSet.copyOf(wallInsulationTypes)) {
							if (w.getWallInsulationThickness(t) != insulationThickness) {
								w.setWallInsulationThicknessAndAddOrRemoveInsulation(t, insulationThickness);
								modified = true;
							}
						}
					}
				}
				return modified;
			}
		};
	}
}
