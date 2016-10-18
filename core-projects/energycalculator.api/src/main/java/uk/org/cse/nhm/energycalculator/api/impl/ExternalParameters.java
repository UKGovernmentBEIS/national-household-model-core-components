package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

public class ExternalParameters implements IEnergyCalculatorParameters {
	private double zoneOneDemandTemperature;
	private Double zoneTwoDemandTemperature;
	private double interzoneTemperatureDifference;
	
	private double numberOfOccupants;
	private ElectricityTariffType tarrifType;
	private Object internal1 = null, internal2 = null, internal3 = null;
	
	@Override
	public double getZoneOneDemandTemperature() {
		return zoneOneDemandTemperature;
	}


	@Override
	public boolean isZoneTwoDemandTemperatureSpecified() {
		return zoneTwoDemandTemperature != null;
	}

	@Override
	public double getZoneTwoDemandTemperature() {
		if (isZoneTwoDemandTemperatureSpecified()) {
			return zoneTwoDemandTemperature;
		} else {
			throw new RuntimeException("Zone two demand temperature is not specified");
		}
	}

	@Override
	public double getInterzoneTemperatureDifference() {
		return interzoneTemperatureDifference;
	}


	@Override
	public double getNumberOfOccupants() {
		return numberOfOccupants;
	}
	
	@Override
	public ElectricityTariffType getTarrifType() {
		return tarrifType;
	}

	public void setZoneOneDemandTemperature(final double zoneOneDemandTemperature) {
		this.zoneOneDemandTemperature = zoneOneDemandTemperature;
	}

	public void setZoneTwoDemandTemperature(final Double zoneTwoDemandTemperature) {
		this.zoneTwoDemandTemperature = zoneTwoDemandTemperature;
	}

	public void setInterzoneTemperatureDifference(
			final double interzoneTemperatureDifference) {
		this.interzoneTemperatureDifference = interzoneTemperatureDifference;
	}
	
	public void setNumberOfOccupants(final double d) {
		this.numberOfOccupants = d;
	}

	public void setTarrifType(final ElectricityTariffType tarrifType) {
		this.tarrifType = tarrifType;
	}
	
	@Override
	public EnergyType getInternalEnergyType(final Object object) {
		if (internal1 == null) {
			internal1 = object;
			return EnergyType.FuelINTERNAL1;
		} else if (internal1 == object) {
			return EnergyType.FuelINTERNAL1;
		} else if (internal2 == null) {
			internal2 = object;
			return EnergyType.FuelINTERNAL2;
		} else if (internal2 == object) {
			return EnergyType.FuelINTERNAL2;
		} else if (internal3 == null) {
			internal3 = object;
			return EnergyType.FuelINTERNAL3;
		} else if (internal3 == object) {
			return EnergyType.FuelINTERNAL3;
		} else {
			throw new IllegalArgumentException("Cannot do an energy calculation with more than three kinds of internal energy");
		}
	}
}
