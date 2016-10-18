package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;

/**
 * This is a special {@link IVentilationSystem} which implements BREDEM 7.2.3, Occupant Ventilation.
 * 
 * It modifies the house air change rate so that it exceeds 1.0 air changes per hour.
 * 
 * @author hinton
 *
 */
public class HumanVentilationSystem implements IVentilationSystem {
	public HumanVentilationSystem(final IConstants constants) {
		
	}

	@Override
	public double getAirChangeRate(double houseAirChangeRate) {
		if (houseAirChangeRate < 1.0) {
			return houseAirChangeRate + (0.5 - houseAirChangeRate  + 0.5 * houseAirChangeRate * houseAirChangeRate);
		} else {
			return houseAirChangeRate;
		}
	}
}
