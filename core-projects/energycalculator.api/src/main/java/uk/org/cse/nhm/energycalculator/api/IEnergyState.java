package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;

/*
BEISDOC
NAME: Total fuel energy demand
DESCRIPTION: The amount of fuel supplied to the house for all uses.
TYPE: table
UNIT: W
SAP: (231, 238)
SAP_COMPLIANT: Yes
BREDEM_COMPLIANT: N/A - out of scope
DEPS: lighting-energy-demand,all-space-heating-fuel-energy-demand,central-direct-hot-water-fuel-demand,central-system-hot-water-fuel-demand,point-of-use-fuel-energy-demand,pv-useful-electricity,pv-exported-electricity,warm-air-fan-electricity,central-heating-pump-power,oil-boiler-pump-power,gas-boiler-pump-power,combi-losses-instant-keep-hot,solar-water-pump-power,appliance-adjusted-demand,cooking-demand,electricity-demand
GET: house.energy-use
NOTES: Appliances and cooking are included here because they are made available to the NHM for reporting. However, they aren't included in the final SAP ratings.
CONVERSION: When getting this using house.energy-use, it will be converted into kWh.
ID: total-fuel-energy-demand
CODSIEB
*/
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

	/*
	BEISDOC
	NAME: Electricity Demand
	DESCRIPTION: Whenever we use electricity in the NHM, we record it using this function which also requires you to pass the high rate fraction. This will split the electricity into separate high-rate and low-rate electricity types.
	TYPE: interface
	UNIT: W
	SAP: (243, 244, other parts of table 10a)
        SAP_COMPLIANT: Yes
        BREDEM_COMPLIANT: NA - out of scope
	DEPS: ashp-split-rate,default-split-rate,direct-electric-split-rate,electric-boiler-split-rate,gas-heat-pump-pump-power,gshp-split-rate,heat-pump-water-with-immersion-split-rate,heat-pump-water-without-immersion-split-rate,immersion-split-rate,integrated-storage-split-rate
	ID: electricity-demand
	CODSIEB
	*/
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
