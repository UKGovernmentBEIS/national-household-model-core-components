package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
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
	
	/*
	BEISDOC
	NAME: occupancy
	DESCRIPTION: Number of occupants in the dwelling
	TYPE: formula
	UNIT: people
	SAP: (42), Table 1b
	BREDEM: 1A
	DEPS: sap-occupancy
	GET: house.number-of-occupants
	STOCK: people.csv (number of rows)
	ID: occupancy
	NOTES: Use the number of people from the stock unless we are in a part of the scenario where sap.occupancy applies.
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
