package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;

public class SAPExternalParameters extends ExternalParameters {

	private final double occupancy;

	public SAPExternalParameters(final ElectricityTariffType tariffType, final double floorArea) {
		super(tariffType);

		this.occupancy = SAPOccupancy.calculate(floorArea);
	}

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

	@Override
	public double getInterzoneTemperatureDifference() {
		return 3;
	}

	@Override
	public EnergyCalculatorType getCalculatorType() {
		return EnergyCalculatorType.SAP2012;
	}

	@Override
	public double getNumberOfOccupants() {
		return occupancy;
	}
}
