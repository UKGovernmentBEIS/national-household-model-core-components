package uk.org.cse.stockimport.imputation.floors;

import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;

/**
 * @author richardt
 *
 */
public interface IFloorPropertyTables {

	 /**
	 * @return insulation thickness, element position in array is {@link SAPAgeBandValue#ordinal()}
	 * @since 3.0
	 */
	double[] getEnglandInsulationBySapAgeBand();
	/**
	 * @return the LastAgeBand where suspend timber floors were used
	 */
	SAPAgeBandValue.Band getLastAgeBandForSuspendedTimber();
	
	/* U-value */
	double getRsi(); 
	double getRse(); 
	double getSoilThermalConductivity();
	double getDeckThermalResistance();
										
	double getOpeningsPerMeterOfExposedPerimeter();
	double getHeightAboveGroundLevel();
	double getUValueOfWallsToUnderfloorSpace();
												
	double getAverageWindSpeedAt10m();
	double getWindShieldingFactor();
	double getFloorInsulationConductivity();	
	
	/**
	 * U value by exposed floor age, uninsulated / insulated
	 * @return
	 */
	double[][] getExposedFloorUValueBySapAgeBand();
}
