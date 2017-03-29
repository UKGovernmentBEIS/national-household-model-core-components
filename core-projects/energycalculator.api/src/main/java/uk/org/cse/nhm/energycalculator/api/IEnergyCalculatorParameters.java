package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

/**
 * The control parameters for a run of a energy calculator.
 *
 * @author hinton
 *
 */
public interface IEnergyCalculatorParameters {
	/**
	 * @return the (possibly adjusted) demand temperature in zone one
	 */
	double getZoneOneDemandTemperature();

	/**
	 * @return True if the zone two demand temperature is set
	 */
	boolean isZoneTwoDemandTemperatureSpecified();

	/**
	 * @return the zone two demand temperature, if it has been set.
	 */
	double getZoneTwoDemandTemperature();

	/**
	 * @return the temperature difference between the zones, if the demand temperature for zone two is not specified.
	 */
	double getInterzoneTemperatureDifference();

	/**
	 * Used to switch between SAP 2012 and BREDEM 2012 code paths.
	 *
	 * @return The type of energy calculation this calculator should be performing.
	 */
	EnergyCalculatorType getCalculatorType();

	/*
	BEISDOC
	NAME: occupancy
	DESCRIPTION: Number of occupants in the dwelling
	TYPE: formula
	UNIT: people
	SAP: (42), Table 1b
        SAP_COMPLIANT: SAP mode only
	BREDEM: 1A
        BREDEM_COMPLIANT: N/A - value from stock
	DEPS: sap-occupancy
	GET: house.number-of-occupants
	STOCK: people.csv (number of rows)
	ID: occupancy
	NOTES: In SAP 2012 mode, this will always be derived based on the floor area.
	CODSIEB
	*/
	/**
	 * @return the number of people currently in the house
	 */
	double getNumberOfOccupants();

	/**
	 * @return the tarriff type the house is on TODO this maybe should be on the house
	 */
	ElectricityTariffType getTarrifType();

	public EnergyType getInternalEnergyType(final Object object);
}
