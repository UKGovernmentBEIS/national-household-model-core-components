package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;

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
	/*
	BEISDOC
	NAME: Heating Schedule
	DESCRIPTION: The heating schedule for the dwelling
	TYPE: formula
	UNIT: N/A
	SAP_COMPLIANT: Yes
	BREDEM_COMPLIANT: No, see note on bredem-heating-schedule
	DEPS: sap-heating-schedule,bredem-heating-schedule
	ID: heating-schedule
	CODSIEB
	*/
	IHeatingSchedule getZone1HeatingSchedule();
	
	IHeatingSchedule getZone2HeatingSchedule(final Zone2ControlParameter control);

	/**
	 * is the heating on in any zone?
	 * @return
	 */
	boolean isHeatingOn();

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
