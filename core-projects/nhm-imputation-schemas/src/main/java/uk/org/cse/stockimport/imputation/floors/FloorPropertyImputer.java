package uk.org.cse.stockimport.imputation.floors;

import static java.lang.Math.PI;
import static java.lang.Math.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;

/**
 * A floor property imputer which implements the RDSAP 2009 rules.
 * 
 * TODO magic numbers!
 * 
 * @author hinton
 * @since 1.0
 */
public class FloorPropertyImputer implements IFloorPropertyImputer {
	private static final Logger log = LoggerFactory.getLogger(FloorPropertyImputer.class);
	private final IFloorPropertyTables floorPropertyTables;
	
	public FloorPropertyImputer(final IFloorPropertyTables floorPropertyTables){
		super();
		this.floorPropertyTables = floorPropertyTables;
		this.uValues = new UValues();
	}
	
	private class UValues {
		private final double Rsi = floorPropertyTables.getRsi();
		private final double Rse = floorPropertyTables.getRse();
		private final double soilThermalConductivity = floorPropertyTables.getSoilThermalConductivity();
		private final double deckThermalResistance = floorPropertyTables.getDeckThermalResistance();
		private final double openingsPerMeterOfExposedPerimeter = floorPropertyTables.getOpeningsPerMeterOfExposedPerimeter();
		private final double heightAboveGroundLevel = floorPropertyTables.getHeightAboveGroundLevel();
		private final double uValueOfWallsToUnderfloorSpace = floorPropertyTables.getUValueOfWallsToUnderfloorSpace();
		private final double averageWindSpeedAt10m = floorPropertyTables.getAverageWindSpeedAt10m();
		private final double windShieldingFactor = floorPropertyTables.getWindShieldingFactor();
		private final double floorInsulationConductivity = floorPropertyTables.getFloorInsulationConductivity();

		public double getUValue(final FloorConstructionType constructionType,
				double wallThickness, final double insulationThickness,
				final double exposedPerimeter, final double area) {

			// convert wall thickness into M
			wallThickness /= 1000;
			
			final double B = 2 * area / exposedPerimeter;

			switch (constructionType) {
			case Solid:
				final double surfaceThermalResistance = 0.001
						* insulationThickness / floorInsulationConductivity;

				final double dt = wallThickness + (soilThermalConductivity
						* (Rsi + surfaceThermalResistance + Rse));

				if (dt < B) {
					return 2 * soilThermalConductivity * log((PI * B / dt) + 1)
							/ ((PI * B) + dt);
				} else {
					return soilThermalConductivity / ((0.457 * B) + dt);
				}
			case SuspendedTimberSealed:
			case SuspendedTimberUnsealed:
				final double dg = wallThickness + soilThermalConductivity
						* (Rsi + Rse);
				final double Ug = 2 * soilThermalConductivity
						* log((PI * B / dg) + 1) / ((PI * B) + dg);
				final double Ux = (2 * heightAboveGroundLevel
						* uValueOfWallsToUnderfloorSpace / B)
						+ (1450 * openingsPerMeterOfExposedPerimeter
								* averageWindSpeedAt10m * windShieldingFactor / B);
				
				log.debug("Suspended U; dg = {}, Ug = {}, Ux = {}, B = {}", new Object[] {dg, Ug, Ux, B});
				
				return 1 / ((2 * Rsi) + deckThermalResistance + 0.2 + (1 / (Ug + Ux)));
			default:
				log.error("Unknown floor construction type {}",
						constructionType);
				return 0;
			}
		}

	}

	private final UValues uValues;

	@Override
	public double getGroundFloorUValue(
			final FloorConstructionType constructionType, 
			final double wallThickness,
			final double insulationThickness,
			final double exposedPerimeter, 
			final double area) {
		return uValues.getUValue(constructionType, wallThickness, insulationThickness, exposedPerimeter, area);
	}

	/**
     * @since 1.0
     */
    @Override
	public double getFloorInsulationThickness(final Band sapAgeBand, final RegionType region, final FloorConstructionType constructionType) {
		// TODO switch on region here
		return floorPropertyTables.getEnglandInsulationBySapAgeBand()[sapAgeBand.ordinal()];
	}
	
	@Override
	public double getExposedFloorUValue(final Band ageBand, final boolean isInsulated) {
		return floorPropertyTables.getExposedFloorUValueBySapAgeBand()[isInsulated ? 1 : 0][ageBand.ordinal()];
	}
}
