package uk.org.cse.stockimport.imputation.floors;

import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.nhm.energycalculator.impl.GroundFloorUValues;

/**
 * A floor property imputer which implements the RDSAP 2009 rules.
 *
 * @author hinton
 * @since 1.0
 */
public class FloorPropertyImputer implements IFloorPropertyImputer {
	private final IFloorPropertyTables floorPropertyTables;

	public FloorPropertyImputer(final IFloorPropertyTables floorPropertyTables){
		super();
		this.floorPropertyTables = floorPropertyTables;
		this.uValues = new GroundFloorUValues()
				.setRsi(floorPropertyTables.getRsi())
				.setRse(floorPropertyTables.getRse())
				.setSoilThermalConductivity(floorPropertyTables.getSoilThermalConductivity())
				.setDeckThermalResistance(floorPropertyTables.getDeckThermalResistance())
				.setOpeningsPerMeterOfExposedPerimeter(floorPropertyTables.getOpeningsPerMeterOfExposedPerimeter())
				.setHeightAboveGroundLevel(floorPropertyTables.getHeightAboveGroundLevel())
				.setuValueOfWallsToUnderfloorSpace(floorPropertyTables.getUValueOfWallsToUnderfloorSpace())
				.setAverageWindSpeedAt10m(floorPropertyTables.getAverageWindSpeedAt10m())
				.setWindShieldingFactor(floorPropertyTables.getWindShieldingFactor())
				.setFloorInsulationConductivity(floorPropertyTables.getFloorInsulationConductivity());
	}

	private final GroundFloorUValues uValues;

	@Override
	public double getGroundFloorUValue(
			final FloorConstructionType constructionType,
			final double wallThickness,
			final double insulationThickness,
			final double exposedPerimeter,
			final double area) {
		return uValues.getU(
				wallThickness,
				area,
				exposedPerimeter,
				constructionType,
				insulationThickness
			);
	}

	/**
     * @since 1.0
     */
    @Override
	public double getFloorInsulationThickness(final Band sapAgeBand, final Country country, final FloorConstructionType constructionType) {
		// TODO switch on country here
		return floorPropertyTables.getEnglandInsulationBySapAgeBand()[sapAgeBand.ordinal()];
	}

	@Override
	public double getExposedFloorUValue(final Band ageBand, final boolean isInsulated) {
		return floorPropertyTables.getExposedFloorUValueBySapAgeBand()[isInsulated ? 1 : 0][ageBand.ordinal()];
	}
}
