package uk.org.cse.stockimport.imputation.floors;

import java.util.Map;

import uk.org.cse.nhm.hom.components.fabric.types.FloorConstructionType;
import uk.org.cse.nhm.hom.types.SAPAgeBandValue;

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
	
	Map<FloorConstructionType, Double> getGroundFloorKValues();
	double getInsulatedExposedFloorKValue();
	double getUninsulatedExposedFloorKValue();
	double getPartyFloorKValue();
	
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
