package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;

/**
 * Tracks the state of an in-progress energy calculation
 * @author hinton
 *
 */
public interface IEnergyState {
	/**
	 * A special case, which subtracts heat provided by internals before doing what {@link #getBoundedTotalDemand(EnergyType, double)} does.
	 * @param energy
	 * @param proportionOfTotal
	 * @return
	 */
	public double getBoundedTotalHeatDemand(final double proportionOfTotal);
	
	/**
	 * Equivalent to 
	 * Math.min(getUnsatisfiedDemand(energy), getTotalDemand(energy) * proportionOfTotal);
	 * @param energy
	 * @param proportionOfTotal
	 * @return
	 */
	public double getBoundedTotalDemand(final EnergyType energy, final double proportionOfTotal);
	/**
	 * How much demand for energy in the given form is unsatisfied (watts)
	 * @param energy
	 * @return
	 */
	public double getUnsatisfiedDemand(final EnergyType energy);
	/**
	 * Satisfy some of the demand for the given type of energy - if there is not this much demand, there is an oversupply.
	 * @param energy
	 * @param amount
	 */
	public void increaseSupply(final EnergyType energy, final double amount);
	/**
	 * Increase the demand for the given type of energy
	 * @param energy
	 * @param amount
	 */
	public void increaseDemand(final EnergyType energy, final double amount);
	/**
	 * Get the amount of energy of a given type that is being generated
	 * @param energy
	 * @return
	 */
	public double getTotalSupply(final EnergyType energy);
	
	/**
	 * Get the amount of energy being satisfied for the given demand by the given service.
	 * 
	 * @param energy
	 * @param service
	 * @return
	 */
	public double getTotalSupply(final EnergyType energy, final ServiceType service);

	/**
	 * Get the total demand by the given service for the given energy type
	 * @param energyType
	 * @param serviceType
	 * @return
	 */
	public double getTotalDemand(final EnergyType energyType, final ServiceType serviceType);
	
	/**
	 * Get the amount of supply over and above demand required
	 * @param energyType
	 * @return
	 */
	public double getExcessSupply(final EnergyType energyType);
	
	/**
	 * get the total demand for the given energy type across all service types
	 * @param 
	 * @return
	 */
	public double getTotalDemand(final EnergyType energyType);
	
	/**
	 * convenience for using electricity
	 * @param highRateFraction
	 * @param amount
	 */
	public void increaseElectricityDemand(final double highRateFraction, final double amount);

	/**
	 * Set the service type for subsequent calls to increase/satisfy demands.
	 * 
	 * @param serviceType
	 */
	public void setCurrentServiceType(final ServiceType serviceType, final String name);

	/**
	 * Increase supply by at most delta, without causing oversupply
	 * @param et
	 * @param delta
	 */
	public void meetSupply(final EnergyType et, final double delta);
}
