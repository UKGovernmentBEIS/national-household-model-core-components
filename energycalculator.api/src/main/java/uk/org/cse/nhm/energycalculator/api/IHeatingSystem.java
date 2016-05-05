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

	/**
	 * The zone two control parameter, indicating how controllable heat demand in zone two is
	 * @return
	 */
	public double getZoneTwoControlParameter(final IInternalParameters parameters);
}
