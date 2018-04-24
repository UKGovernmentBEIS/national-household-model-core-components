package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;

public class SAPExternalParameters extends ExternalParameters {

	private final double occupancy;
	private EnergyCalculatorType calcType;

	public SAPExternalParameters(final EnergyCalculatorType calcType, final ElectricityTariffType tariffType, final double floorArea) {
		super(tariffType);
		this.calcType = calcType;

		this.occupancy = SAPOccupancy.calculate(floorArea);
	}

	/*
	BEISDOC
	NAME: SAP zone 1 demand temperature
	DESCRIPTION: The zone 1 demand temperature under SAP 2012
	TYPE: constant (21)
	UNIT: ℃
	SAP: Table 9
    SAP_COMPLIANT: Yes
    BREDEM_COMPLIANT: N/A - not used
    NOTES: 21℃
	ID: sap-zone-1-demand-temperature
	CODSIEB
	*/
	@Override
	public double getZoneOneDemandTemperature() {
		return 21;
	}

	@Override
	public boolean isZoneTwoDemandTemperatureSpecified() {
		return false;
	}

	@Override
	public double getZoneTwoDemandTemperature() {
		throw new UnsupportedOperationException("In SAP 2012, the zone two demand temperature must be computed using the heat loss parameter.");
	}

	/*
	BEISDOC
	NAME: SAP interzone temperature difference
	DESCRIPTION: The difference in demand temperature between the living area and the rest of the dwelling.
	TYPE: constant
	UNIT: ℃
	SAP: Table 9
    SAP_COMPLIANT: Yes
    BREDEM_COMPLIANT: N/A - not used
    NOTES: By comparing BREDEM 2012 Section 7 Mean Internal Temperature with SAP 2012 Table 9,
    NOTES: we can see that SAP uses the BREDEM 2012 formula with 3℃ plugged in for the temperature difference.
	ID: sap-interzone-temperature-difference
	CODSIEB
	*/
	@Override
	public double getInterzoneTemperatureDifference() {
		return 3;
	}

	@Override
	public EnergyCalculatorType getCalculatorType() {
		return calcType;
	}

	@Override
	public double getNumberOfOccupants() {
		return occupancy;
	}
}
