package uk.org.cse.nhm.energycalculator.api;



/**
 * The interface for a heating system (which may have some things inside it)
 * 
 * @author hinton
 *
 */
public interface IHeatingSystem {
	/**
	 * Compute the background temperature from this heating system see (BREDEM 9.2).
	 * @param demandTemperature the demand temperature by zone 
	 * @param responsiveBackgroundTemperature the fully-responsive background temperature by zone (BREDEM 9.2.1)
	 * @param unresponsiveBackgroundTemperature the fully unresponsive background temperature by zone (BREDEM 9.2.2)
	 * @param state the current energy state, solved up to gains (so gains are present in HEAT1 and HEAT2)
	 * @param losses the heat loss parameters for the house
	 * 
	 * @return background temperatures by zone
	 */
	public double[] getBackgroundTemperatures(
			double[] demandTemperature, 
			double[] responsiveBackgroundTemperature, 
			
			double[] unresponsiveBackgroundTemperature,
			IInternalParameters parameters,
			
			IEnergyState state,
			ISpecificHeatLosses losses);

	/**
	 * The amount in degrees c to increase the demand temperature
	 * @return
	 */
	public double getDemandTemperatureAdjustment(final IInternalParameters parameters);

	/*
	BEISDOC
	NAME: Zone 2 control parameter
	DESCRIPTION: The zone two control parameter, indicating how controllable heat demand in zone two is
	TYPE: value
	UNIT: 0 or 1
	SAP: Table 4e (Control)
	NOTES: BREDEM instead uses Zone 2 Control Fraction, which we do not implement because of lack of information.
	CONVERSION: Type 1 maps to 0, while Types 2 and 3 map to 1
	ID: zone-2-control-parameter
	CODSIEB
	*/
	/**
	 * The zone two control parameter, indicating how controllable heat demand in zone two is
	 * @return
	 */
	public double getZoneTwoControlParameter(final IInternalParameters parameters);
}
