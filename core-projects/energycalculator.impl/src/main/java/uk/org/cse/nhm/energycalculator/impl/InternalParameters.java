package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

public class InternalParameters implements IInternalParameters {
	private final IEnergyCalculatorParameters externalParameters;
	
	private final IConstants constants;
	private final ISeasonalParameters climate;
	
	public InternalParameters(final IEnergyCalculatorParameters externalParameters, final IConstants constants, final ISeasonalParameters climate) {
		this.externalParameters = externalParameters;
		this.constants = constants;
		this.climate = climate;
	}

	@Override
	public double getTemperatureAdjustment() {
		return 0;
	}
	
	@Override
	public ISeasonalParameters getClimate() {
		return climate;
	}

	@Override
	public double getZoneOneDemandTemperature() {
		return externalParameters.getZoneOneDemandTemperature();
	}

	@Override
	public boolean isZoneTwoDemandTemperatureSpecified() {
		return externalParameters.isZoneTwoDemandTemperatureSpecified();
	}

	@Override
	public double getZoneTwoDemandTemperature() {
		return externalParameters.getZoneTwoDemandTemperature();
	}

	@Override
	public double getInterzoneTemperatureDifference() {
		return externalParameters.getInterzoneTemperatureDifference();
	}

	@Override
	public double getNumberOfOccupants() {
		return externalParameters.getNumberOfOccupants();
	}
	
	@Override
	public ElectricityTariffType getTarrifType() {
		return externalParameters.getTarrifType();
	}

	@Override
	public IConstants getConstants() {
		return constants;
	}
	
	@Override
	public EnergyType getInternalEnergyType(final Object object) {
		return externalParameters.getInternalEnergyType(object);
	}

	@Override
	public EnergyCalculatorType getCalculatorType() {
		return externalParameters.getCalculatorType();
	}
}
