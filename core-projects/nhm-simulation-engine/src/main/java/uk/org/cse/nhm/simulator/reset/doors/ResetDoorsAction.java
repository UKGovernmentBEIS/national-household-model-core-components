package uk.org.cse.nhm.simulator.reset.doors;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.WallType;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.structure.Door;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
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

public class ResetDoorsAction extends AbstractNamed implements IComponentsAction {
	final IComponentsFunction<Number> doorAreaFunction;
	final IComponentsFunction<Number> doorUValueFunction;
	
	final IDimension<StructureModel> structureDimension;
	private final DoorPropertiesFunction propertiesFunction;
	
	final static Object DOOR_SCOPE_KEY = new Object();
	protected static final double MAXIMUM_WRANGLED_AREA = 0.5;
	private final boolean wrangle;
	
	@AssistedInject
	public ResetDoorsAction(
			@Assisted("area") final Optional<IComponentsFunction<Number>> area, 
			@Assisted("uvalue") final Optional<IComponentsFunction<Number>> uValue, 
			@Assisted final boolean wrangle,
			final IDimension<StructureModel> structureDimension) {
		this.wrangle = wrangle;
		this.structureDimension = structureDimension;
		this.doorAreaFunction = area.orNull();
		this.doorUValueFunction = uValue.orNull();
		this.propertiesFunction = new DoorPropertiesFunction();
	}
	
	class DoorPropertiesFunction extends AbstractNamed implements IComponentsFunction<double[]> {
		@Override
		public double[] compute(final IComponentsScope scope, final ILets lets) {
			final Door door = lets.get(DOOR_SCOPE_KEY, Door.class).get();
			return new double[] {
				doorAreaFunction == null ? door.getArea() : doorAreaFunction.compute(scope, lets).doubleValue(),
				doorUValueFunction == null ? door.getuValue() : doorUValueFunction.compute(scope, lets).doubleValue()
			};
		}

		@Override
		public Set<IDimension<?>> getDependencies() {
			return Collections.emptySet();
		}

		@Override
		public Set<DateTime> getChangeDates() {
			return Collections.emptySet();
		}
	}
	
	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets)
			throws NHMException {
	
		scope.modify(structureDimension, new IModifier<StructureModel>() {
			@Override
			public boolean modify(final StructureModel modifiable) {
				for (final Elevation e : modifiable.getElevations().values()) {
					for (final Door door : e.getDoors()) {
						final double[] newProperties = propertiesFunction.compute(scope, lets.withBinding(DOOR_SCOPE_KEY, door));
						
						door.setArea(newProperties[0]);
						door.setuValue(newProperties[1]);
					}
				}
				
				if (wrangle) {
					wrangle(modifiable);
				}
				
				return true;
			}

			private void wrangle(final StructureModel structure) {
				for (final Map.Entry<ElevationType, Elevation> e : structure.getElevations().entrySet()) {
					double externalAreaInElevation = 0;
					for (final Storey s : structure.getStoreys()) {
						for (final IWall wall : s.getImmutableWalls()) {
							if (wall.getElevationType() == e.getKey() && 
									wall.getWallConstructionType().getWallType() == WallType.External) {
								externalAreaInElevation += wall.getArea();
							}
						}
					}
					
					final double openAreaInElevation = externalAreaInElevation * e.getValue().getOpeningProportion();
					
					double totalDoorArea = 0;
					for (final Door door : e.getValue().getDoors()) {
						totalDoorArea += door.getArea();
					}
					
					if (totalDoorArea > MAXIMUM_WRANGLED_AREA * openAreaInElevation) {
						final double requiredDoorArea = MAXIMUM_WRANGLED_AREA * openAreaInElevation;
						final double scalingFactor = requiredDoorArea / totalDoorArea;
						
						for (final Door door : e.getValue().getDoors()) {
							door.setArea(door.getArea() * scalingFactor);
						}
					}
				}
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
}
