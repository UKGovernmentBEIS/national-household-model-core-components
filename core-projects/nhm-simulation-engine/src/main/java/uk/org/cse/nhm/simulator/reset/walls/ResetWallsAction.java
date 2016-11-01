package uk.org.cse.nhm.simulator.reset.walls;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.energycalculator.api.types.WallType;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ResetWallsAction extends AbstractNamed implements IComponentsAction {
	static class WallPropertyFunction extends AbstractNamed implements IComponentsFunction<double[]> {
		private final IComponentsFunction<Number> uValue;
		private final IComponentsFunction<Number> infiltration;
		private final IComponentsFunction<Number> thickness;
		
		private final IDimension<?> structureDimension;

		public WallPropertyFunction(final IComponentsFunction<Number> uValue,
									final IComponentsFunction<Number> infiltration,
									final IComponentsFunction<Number> thickness,
									final IDimension<?> structureDimension) {
			this.uValue = uValue;
			this.infiltration = infiltration;
			this.thickness = thickness;
			this.structureDimension = structureDimension;
		}

		@Override
		public double[] compute(final IComponentsScope scope, final ILets lets) {
			final double[] result = new double[3];

			final IWall wall = lets.get(CURRENT_WALL_LET_IDENTITY, IWall.class).get();
			
			if (wall.getWallConstructionType().getWallType() == WallType.External) {
				if (uValue != null) result[0] = uValue.compute(scope, lets).doubleValue();
				else result[0] = wall.getUValue();
				if (infiltration != null) result[2] = infiltration.compute(scope, lets).doubleValue();
				else result[1] = wall.getAirChangeRate();
				if (thickness != null) result[3] = thickness.compute(scope, lets).doubleValue();
				else result[2] = wall.getThicknessWithoutInsulation();
			} else {
				result[0] = wall.getUValue();
				result[1] = wall.getAirChangeRate();
				result[2] = wall.getThicknessWithoutInsulation();
			}
			
			return result;
		}

		@Override
		public Set<IDimension<?>> getDependencies() {
			return Collections.<IDimension<?>>singleton(structureDimension);
		}

		@Override
		public Set<DateTime> getChangeDates() {
			return Collections.emptySet();
		}
	}


	private final WallPropertyFunction allFunctionsTogether;
	private final IDimension<StructureModel> structureDimension;

	@AssistedInject
	public ResetWallsAction(final IDimension<StructureModel> structureDimension,
							@Assisted("uvalues") final Optional<IComponentsFunction<Number>> uValue,
							@Assisted("kvalues") final Optional<IComponentsFunction<Number>> kValue,
							@Assisted("infiltration") final Optional<IComponentsFunction<Number>> infiltration,
							@Assisted("thickness") final Optional<IComponentsFunction<Number>> thickness) {
		this.structureDimension = structureDimension;
		this.allFunctionsTogether =
			new WallPropertyFunction(uValue.orNull(),
									 infiltration.orNull(),
									 thickness.orNull(),
									 structureDimension);
	}
	/**
	 * I apologise for the grimness of this hack;
	 * this magic string is used to bind the wall into the let scope
	 * to allow special wall functions to read values out of the wall
	 * elsewhere in the system.
	 */
	final static Object CURRENT_WALL_LET_IDENTITY = new Object();

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) {
		scope.modify(structureDimension, new IModifier<StructureModel>() {
			@Override
			public boolean modify(final StructureModel sm) {
				for (final Storey storey : sm.getStoreys()) {
					for (final IMutableWall wall : storey.getWalls()) {
						final double[] results = allFunctionsTogether.compute(scope, 
								lets.withBinding(CURRENT_WALL_LET_IDENTITY, wall));

						wall.setUValue(results[0]);
						wall.setAirChangeRate(results[1]);
						wall.setThicknessWithExistingInsulation(results[2]);
					}
				}
				return true;
			}
		});
		
		return true;
	}
	
	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		return true;
	}
	@Override
	public boolean isAlwaysSuitable() {
		return true;
	}
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}
}
