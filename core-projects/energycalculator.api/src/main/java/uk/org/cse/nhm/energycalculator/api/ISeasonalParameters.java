package uk.org.cse.nhm.energycalculator.api;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;
import uk.org.cse.nhm.energycalculator.api.types.ZoneType;

public interface ISeasonalParameters {
	/**
	 * @return the exterior temperature
	 */
	double getExternalTemperature();

	/**
	 * @return the wind speed at the place where the algorithm is being evaluated
	 */
	double getSiteWindSpeed();

	/**
	 * @return the current solar declination, in radians
	 */
	double getSolarDeclination();

	/**
	 * Return the mean solar flux in watts/m2 onto a plane at the given angle from the horizontal
	 * (so angleFromHorizontal == 0 is a degenerate case where the plane is pointing at the sky;
	 * angleFromHorizontal == 90 is the extreme where the plane is pointing at the horizon;
	 * angleFromNorth is the angle between the vertical of the plane and the north vector).
	 *
	 * @return the mean solar flux on a plane defined by the angles
	 */
	double getSolarFlux(final double angleFromHorizontal, final double angleFromNorth);

	/**
	 * TODO use month type here
	 * @return the current month, from 1 to 12
	 */
	int getMonthOfYear();

//	/**
//	 * @return the hot water factor for the current month; this is a SAP variable which adjusts the amount of hot water used.
//	 */
//	double getHotWaterFactor();
//
//	/**
//	 * @return the hot water rise for the current month, in degrees. This is the amount of heat that has to be put into the hot water.
//	 */
//	double getHotWaterRise();

	/**
	 * @param zone
	 * @param zone2ControlParameter switches the zone 2 heating schedule based on SAP 2012 Table 9. Not applicable in BREDEM 2012 mode.
	 * @return the heating schedule for the given zone
	 */
	IHeatingSchedule getHeatingSchedule(ZoneType zone, Optional<Zone2ControlParameter> zone2ControlParameter);

	/**
	 * is the heating on in any zone?
	 * @return
	 */
	boolean isHeatingOn();

	/**
	 * is the cooling on in any zone?
	 * @return
	 */
	boolean isCoolingOn();

	/**
	 * A convenience method which turns our TRUE/FALSE into a 1/0.
	 *
	 * This is useful because BREDEM uses a number between 0 and 1 (inclusive)
	 * to determine how much of the month the heating is on for, so we can fit
	 * into the same code.
	 * return the proportion of the month for which the heating is on.
	 * @param demandTemperature
	 * @param revisedGains
	 * @param losses
	 * @param parameters
	 */
	double getHeatingOnFactor(IInternalParameters parameters, ISpecificHeatLosses losses, double revisedGains, double[] demandTemperature);
}
