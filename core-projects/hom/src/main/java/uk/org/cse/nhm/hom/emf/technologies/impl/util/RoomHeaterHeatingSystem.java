package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.hom.constants.adjustments.TemperatureAdjustments;
import uk.org.cse.nhm.hom.emf.technologies.IBackBoiler;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;

/**
 * Wraps an {@link IRoomHeater} and presents it as an {@link IHeatingSystem}.
 * 
 * This is to avoid code duplication between {@link IBackBoiler} and {@link IRoomHeater}.
 * 
 * @author hinton
 *
 */
public class RoomHeaterHeatingSystem implements IHeatingSystem {
	final IRoomHeater roomHeater;
	
	public RoomHeaterHeatingSystem(IRoomHeater roomHeater) {
		this.roomHeater = roomHeater;
	}
	
	@Override
	public double[] getBackgroundTemperatures(double[] demandTemperature,
			double[] responsiveBackgroundTemperature,
			double[] unresponsiveBackgroundTemperature,
			IInternalParameters parameters,
			IEnergyState state, ISpecificHeatLosses losses) {
		final double responsiveness = roomHeater.getResponsiveness();
		
		final double unresponsiveness = 1 - responsiveness;
		
		return new double[] {
			responsiveBackgroundTemperature[0] * responsiveness + unresponsiveBackgroundTemperature[0] * unresponsiveness,
			responsiveBackgroundTemperature[1] * responsiveness + unresponsiveBackgroundTemperature[1] * unresponsiveness
		};
	}

	/**
	 * SAP Table 4e Group 6 explains room heater systems, and details that there is a +0.3 deg demand temperature
	 * adjustment if the heater does not have a thermostat fitted.
	 */
	@Override
	public double getDemandTemperatureAdjustment(final IInternalParameters parameters) {
		if (roomHeater.isThermostatFitted()) {
			return 0;
		} else {
			return parameters.getConstants().get(TemperatureAdjustments.ROOM_HEATER_NO_THERMOSTAT);
		}
	}

	/**
	 * SAP Table 4e Group 6 says that room heaters have a control type of 2 if they are not thermostatic, or 3 otherwise.
	 * These both correspond to a Z2 control parameters of 1
	 */
	@Override
	public double getZoneTwoControlParameter(final IInternalParameters parameters) {
		return 1;
	}
}
