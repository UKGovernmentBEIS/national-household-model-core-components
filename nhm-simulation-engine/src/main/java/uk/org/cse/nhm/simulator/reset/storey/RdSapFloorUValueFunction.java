package uk.org.cse.nhm.simulator.reset.storey;

import static java.lang.Math.PI;
import static java.lang.Math.log;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.components.fabric.types.FloorConstructionType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Computes a floor u-value according to RDSAP rules.
 * @author hinton
 *
 */
public class RdSapFloorUValueFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final IDimension<StructureModel> structureDimension;

	private final double Rsi;
	private final double Rse;
	private final double soilThermalConductivity;
	private final double deckThermalResistance;
												
	private final double openingsPerMeterOfExposedPerimeter;
	private final double heightAboveGroundLevel;
	private final double uValueOfWallsToUnderfloorSpace;
															// square kelvin
	private final double averageWindSpeedAt10m;
	private final double windShieldingFactor;
	private final double floorInsulationConductivity;
	
	@AssistedInject
	RdSapFloorUValueFunction(
			final IDimension<StructureModel> structureDimension,
			@Assisted("rsi") final double rsi, 
			@Assisted("rse") final double rse, 
			@Assisted("soilThermalConductivity") final double soilThermalConductivity,
			@Assisted("deckThermalResistance") final double deckThermalResistance,
			@Assisted("openingsPerMeterOfExposedPerimeter") final double openingsPerMeterOfExposedPerimeter,
			@Assisted("heightAboveGroundLevel") final double heightAboveGroundLevel,
			@Assisted("uValueOfWallsToUnderfloorSpace") final double uValueOfWallsToUnderfloorSpace,
			@Assisted("averageWindSpeedAt10m") final double averageWindSpeedAt10m, 
			@Assisted("windShieldingFactor") final double windShieldingFactor,
			@Assisted("floorInsulationConductivity") final double floorInsulationConductivity) {
		this.structureDimension = structureDimension;
		Rsi = rsi;
		Rse = rse;
		this.soilThermalConductivity = soilThermalConductivity;
		this.deckThermalResistance = deckThermalResistance;
		this.openingsPerMeterOfExposedPerimeter = openingsPerMeterOfExposedPerimeter;
		this.heightAboveGroundLevel = heightAboveGroundLevel;
		this.uValueOfWallsToUnderfloorSpace = uValueOfWallsToUnderfloorSpace;
		this.averageWindSpeedAt10m = averageWindSpeedAt10m;
		this.windShieldingFactor = windShieldingFactor;
		this.floorInsulationConductivity = floorInsulationConductivity;
	}

	private double getUValue(final FloorConstructionType constructionType,
			double wallThickness, final double insulationThickness,
			final double exposedPerimeter, final double area) {

		// convert wall thickness into M
		wallThickness /= 1000;
		
		final double B = 2 * area / exposedPerimeter;

		switch (constructionType) {
		case Solid:
			final double surfaceThermalResistance = 0.001
					* insulationThickness / floorInsulationConductivity;

			final double dt = wallThickness + soilThermalConductivity
					* (Rsi + surfaceThermalResistance + Rse);

			if (dt < B) {
				return 2 * soilThermalConductivity * log(PI * B / dt + 1)
						/ (PI * B + dt);
			} else {
				return soilThermalConductivity / (0.457 * B + dt);
			}
		case SuspendedTimber:
			final double dg = wallThickness + soilThermalConductivity
					* (Rsi + Rse);
			final double Ug = 2 * soilThermalConductivity
					* log(PI * B / dg + 1) / (PI * B + dg);
			final double Ux = (2 * heightAboveGroundLevel
					* uValueOfWallsToUnderfloorSpace / B)
					+ (1450 * openingsPerMeterOfExposedPerimeter
							* averageWindSpeedAt10m * windShieldingFactor / B);
			
			return 1 / (2 * Rsi + deckThermalResistance + 0.2 + 1 / (Ug + Ux));
		default:
			return 0;
		}
	}
	
	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final StructureModel structure = scope.get(structureDimension);
		final Optional<Storey> storey = lets.get(ResetFloorsAction.STOREY_SCOPE_KEY, Storey.class);
		final Optional<Double> areaBelow = lets.get(ResetFloorsAction.STOREY_GROUND_AREA_KEY, Double.class);
		
		if (storey.isPresent() && areaBelow.isPresent()) {
			return compute(structure, storey.get(), areaBelow.get());
		} else {
			// error
			return 0d;
		}
	}

	private double compute(final StructureModel structure, final Storey storey,
			final double areaBelow) {
		
		double maxWallThickness = 0;
		for (final IWall wall : storey.getImmutableWalls()) {
			maxWallThickness = Math.max(maxWallThickness, wall.getThicknessWithInsulation());
		}
		
		return getUValue(structure.getGroundFloorConstructionType(), 
				maxWallThickness, 
				structure.getFloorInsulationThickness(), 
				storey.getExposedPerimeter(), 
				Math.max(0, storey.getArea() - areaBelow)
				);
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(structureDimension);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}

}
