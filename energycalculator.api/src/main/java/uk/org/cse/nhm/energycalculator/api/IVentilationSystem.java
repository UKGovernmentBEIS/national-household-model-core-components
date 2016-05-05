package uk.org.cse.nhm.energycalculator.api;

/**
 * A ventilation system - this will be present when there is full mechanical ventilation, or MVHR
 * 
 * @author hinton
 *
 */
public interface IVentilationSystem {
	/**
	 * Get the new air change rate, given that there is this ventilation system
	 * @param houseAirChangeRate the air change rate otherwise (e.g. the ventilation rate computed from walls, windows etc)
	 * @return the new air change rate, given this ventilation system 
	 */
	public double getAirChangeRate(double houseAirChangeRate);
}
