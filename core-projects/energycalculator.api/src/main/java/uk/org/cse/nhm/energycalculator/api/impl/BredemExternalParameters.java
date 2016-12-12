package uk.org.cse.nhm.energycalculator.api.impl;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;

public class BredemExternalParameters extends ExternalParameters {

	private final double zoneOneDemandTemperature;
	private final Optional<Double> zoneTwoDemandTemperature;
	private final Optional<Double> interzoneTemperatureDifference;
	private final double numberOfOccupants;

	public BredemExternalParameters(
			final ElectricityTariffType tariff,
			final double zoneOneDemandTemperature,
			final Optional<Double> zoneTwoDemandTemperature,
			final Optional<Double> interzoneTemperatureDifference,
			final double numberOfOccupants) {
		super(tariff);

		if (zoneTwoDemandTemperature.isPresent() == interzoneTemperatureDifference.isPresent()) {
			throw new IllegalArgumentException("Either the zone two demand temperature, or the interzone temperature difference must be specified (but not both).");
		}

		this.zoneOneDemandTemperature = zoneOneDemandTemperature;
		this.zoneTwoDemandTemperature = zoneTwoDemandTemperature;
		this.interzoneTemperatureDifference = interzoneTemperatureDifference;

		this.numberOfOccupants = numberOfOccupants;
	}

	@Override
	public double getZoneOneDemandTemperature() {
		return zoneOneDemandTemperature;
	}


	@Override
	public boolean isZoneTwoDemandTemperatureSpecified() {
		return zoneTwoDemandTemperature.isPresent();
	}

	@Override
	public double getZoneTwoDemandTemperature() {
		if (isZoneTwoDemandTemperatureSpecified()) {
			return zoneTwoDemandTemperature.get();
		} else {
			throw new RuntimeException("Zone two demand temperature is not specified");
		}
	}

	@Override
	public double getInterzoneTemperatureDifference() {
		if (interzoneTemperatureDifference.isPresent()) {
			return interzoneTemperatureDifference.get();
		} else {
			throw new RuntimeException("Interzone temperature difference is not specified");
		}
	}

	@Override
	public double getNumberOfOccupants() {
		return numberOfOccupants;
	}

	@Override
	public EnergyCalculatorType getCalculatorType() {
		return EnergyCalculatorType.BREDEM2012;
	}
}
