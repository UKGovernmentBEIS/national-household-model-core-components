package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;

/**
 * A wrapper for an {@link IEnergyCalculatorParameters} which applies the little tweaks that BREDEM specifies
 * @author hinton
 *
 */
public class ParameterAdjustment implements IInternalParameters {
	private final IInternalParameters delegate;
	private final double temperatureAdjustment;
	private final double heatingSystemZoneTwoTemperature;
	
	/**
	 * Adjust the given parameters
	 * 
	 * @param parameters
	 * @param totalTemperatureAdjustment add this to the temperature in zone 1
	 * @param heatingPeriodAdjustment add this to the start time of each heating period
	 * @param heatingSystemZoneTwoTemperature set the zone two temperature to this, overriding the unset value.
	 */
	public ParameterAdjustment(final IInternalParameters parameters, 
			final double totalTemperatureAdjustment,final double heatingSystemZoneTwoTemperature) {
		this.delegate = parameters;
		this.temperatureAdjustment = totalTemperatureAdjustment;
		this.heatingSystemZoneTwoTemperature = heatingSystemZoneTwoTemperature;
	}

	@Override
	public double getZoneOneDemandTemperature() {
		return delegate.getZoneOneDemandTemperature();// + temperatureAdjustment;
	}

	

	@Override
	public ISeasonalParameters getClimate() {
		return delegate.getClimate();
	}

	@Override
	public boolean isZoneTwoDemandTemperatureSpecified() {
		return true;
	}

	@Override
	public double getZoneTwoDemandTemperature() {
		return heatingSystemZoneTwoTemperature;
	}

	@Override
	public double getInterzoneTemperatureDifference() {
		return delegate.getInterzoneTemperatureDifference();
	}
	
	@Override
	public double getTemperatureAdjustment() {
		return temperatureAdjustment;
	}


	@Override
	public double getNumberOfOccupants() {
		return delegate.getNumberOfOccupants();
	}


	@Override
	public ElectricityTariffType getTarrifType() {
		return delegate.getTarrifType();
	}

	@Override
	public IConstants getConstants() {
		return delegate.getConstants();
	}
	
	@Override
	public EnergyType getInternalEnergyType(final Object object) {
		return delegate.getInternalEnergyType(object);
	}
}
