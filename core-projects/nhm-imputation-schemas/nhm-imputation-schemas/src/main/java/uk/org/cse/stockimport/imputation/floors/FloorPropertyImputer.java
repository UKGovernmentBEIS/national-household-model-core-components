package uk.org.cse.stockimport.imputation.floors;

import static java.lang.Math.PI;
import static java.lang.Math.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band;

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
				
				log.debug("Suspended U; dg = {}, Ug = {}, Ux = {}, B = {}", new Object[] {dg, Ug, Ux, B});
				
				return 1 / (2 * Rsi + deckThermalResistance + 0.2 + 1 / (Ug + Ux));
			default:
				log.error("Unknown floor construction type {}",
						constructionType);
				return 0;
			}
		}

	}

	private final UValues uValues;

	/**
	 * The infiltration rate for a sealed suspended timber floor
	 */
	private final double sealedSuspendedTimberInfiltration = 0.1;
	
	/**
	 * The infiltration rate for an unsealed suspended timber floor
	 */
	private final double unsealedSuspendedTimberInfiltration = 0.2;
	
	/**
	 * The latest SAP age band in which you are assumed to have an unsealed floor, if you have a suspended timber floor.
	 */
	private final SAPAgeBandValue.Band lastAgeBandForUnsealed = SAPAgeBandValue.Band.E;
	
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
	public double getFloorInfiltration(final Band ageBand, final FloorConstructionType constructionType) {
		switch (constructionType) {
		case Solid:
			// solid floors have no infiltration
			return 0;
		case SuspendedTimber:
			// suspended wood floors have 0.2 for unsealed or 0.1 for sealed.
			// sealed is a function of dwelling age.
			if (ageBand.compareTo(lastAgeBandForUnsealed) > 0) {
				return sealedSuspendedTimberInfiltration;
			} else {
				return unsealedSuspendedTimberInfiltration;
			}
		default:
			log.error("Unknown floor construction type {}", constructionType);
			return 0;
		}
	}
	
	/**
     * @since 1.0
     */
    @Override
	public double getFloorInsulationThickness(final Band sapAgeBand, final RegionType region, final FloorConstructionType constructionType) {
		// TODO switch on region here
		return floorPropertyTables.getEnglandInsulationBySapAgeBand()[sapAgeBand.ordinal()];
	}
	
	/**
     * @since 1.0
     */
    @Override
	public FloorConstructionType getFloorConstructionType(final Band ageBand) {
		if (ageBand.compareTo(floorPropertyTables.getLastAgeBandForSuspendedTimber()) > 0) {
			return FloorConstructionType.Solid;
		} else {
			return FloorConstructionType.SuspendedTimber;
		}
	}
	
	@Override
	public double getExposedFloorUValue(final Band ageBand, final boolean isInsulated) {
		return floorPropertyTables.getExposedFloorUValueBySapAgeBand()[isInsulated ? 1 : 0][ageBand.ordinal()];
	}

	@Override
	public double getExposedFloorKValue(final boolean isInsulated) {
		if (isInsulated) {
			return floorPropertyTables.getInsulatedExposedFloorKValue();
		} else {
			return floorPropertyTables.getUninsulatedExposedFloorKValue();
		}
	}

	@Override
	public double getGroundFloorKValue(final FloorConstructionType constructionType) {
		return floorPropertyTables.getGroundFloorKValues().get(constructionType);
	}

	@Override
	public double getPartyFloorKValue() {
		return floorPropertyTables.getPartyFloorKValue();
	}
}
