package uk.org.cse.stockimport.imputation.floors;

import java.util.EnumMap;
import java.util.Map;

import uk.org.cse.nhm.hom.components.fabric.types.FloorConstructionType;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue.Band;

import com.google.common.collect.ImmutableMap;

public class RdSAPFloorPropertyTables implements IFloorPropertyTables {
	
	private final double[] englandInsulationBySapAgeBand = new double[]
			{0, 0, 0, 0, 0, 0, 0, 0, 25, 75, 100};

	/**
	 * The latest SAP Age Band in which {@link #getFloorConstructionType(Band)} will be suspended timber rather than solid.
	 */
	private final SAPAgeBandValue.Band lastAgeBandForSuspendedTimber = SAPAgeBandValue.Band.B;
	
	
	private final double Rsi = 0.17; // square meter kelvin per watt
	private final double Rse = 0.04; // square meter kelvin per watt
	private final double soilThermalConductivity = 1.5;// watts / meter kelvin
	private final double deckThermalResistance = 0.2; // square meter kelvin per
												// watt
	private final double openingsPerMeterOfExposedPerimeter = 0.003; // meters
	private final double heightAboveGroundLevel = 0.3; // meters
	private final double uValueOfWallsToUnderfloorSpace = 1.5; // wats per meter
															// square kelvin
	private final double averageWindSpeedAt10m = 5;// meters per second
	private final double windShieldingFactor = 0.05; // dimensionless
	private final double floorInsulationConductivity = 0.035; // watts per meter
														// kelvin
	
	 /**
	   * U value by exposed floor age, uninsulated / insulated
	   */
	  
	  private final double[][] exposedFloorUValueBySapAgeBand = new double[][]
	      {
	        { /// uninsulated
	          1.20, 1.20, 1.20, 1.20, 1.20, 1.20, 1.20, 0.51, 0.51, 0.25, 0.22
	        }, 
	        { // insulated
	          0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.25, 0.22
	        }
	      };
	
	@Override
	public double[] getEnglandInsulationBySapAgeBand() {
		return englandInsulationBySapAgeBand;
	}

	@Override
	public SAPAgeBandValue.Band getLastAgeBandForSuspendedTimber() {
		return lastAgeBandForSuspendedTimber;
	}

	@Override
	public double getRsi() {
		return Rsi;
	}

	@Override
	public double getRse() {
		return Rse;
	}

	@Override
	public double getSoilThermalConductivity() {
		return soilThermalConductivity;
	}

	@Override
	public double getDeckThermalResistance() {
		return deckThermalResistance;
	}

	@Override
	public double getOpeningsPerMeterOfExposedPerimeter() {
		return openingsPerMeterOfExposedPerimeter;
	}

	@Override
	public double getHeightAboveGroundLevel() {
		return heightAboveGroundLevel;
	}

	@Override
	public double getUValueOfWallsToUnderfloorSpace() {
		return uValueOfWallsToUnderfloorSpace;
	}

	@Override
	public double getAverageWindSpeedAt10m() {
		return averageWindSpeedAt10m;
	}

	@Override
	public double getWindShieldingFactor() {
		return windShieldingFactor;
	}

	@Override
	public double getFloorInsulationConductivity() {
		return floorInsulationConductivity;
	}

	@Override
	public double[][] getExposedFloorUValueBySapAgeBand() {
		return exposedFloorUValueBySapAgeBand;
	}
}
