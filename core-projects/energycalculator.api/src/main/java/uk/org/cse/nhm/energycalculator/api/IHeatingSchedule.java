package uk.org.cse.nhm.energycalculator.api;

/**
 * A representation of a binary heating schedule - doesn't actually provide on/off times to the algorithm
 * because the algorithm doesn't want them anyway
 * 
 * @author hinton
 *
 */
public interface IHeatingSchedule {
	/**
	 * This takes the heating schedule, which looks like a binary signal (stepwise functions), smooths off the falling edges
	 * so that they take cutoffTime to happen, scales 1 and 0 to demand and background temperature, integrates the total area
	 * and divides by the length, to get average heat under this profile.
	 * 
	 * @param demandTemperature
	 * @param backgroundTemperature
	 * @param cutoffTime
	 * @return mean temperature.
	 */
	public double getMeanTemperature(final double demandTemperature, final double backgroundTemperature, final double cutoffTime);
	
	/**
	 * @return true if the heating is on at any point.
	 */
	public boolean isHeatingOn();
}
