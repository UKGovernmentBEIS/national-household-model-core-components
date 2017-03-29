package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;
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

	public RoomHeaterHeatingSystem(final IRoomHeater roomHeater) {
		this.roomHeater = roomHeater;
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
	 */
	@Override
	public Zone2ControlParameter getZoneTwoControlParameter(final IInternalParameters parameters) {
		return roomHeater.isThermostatFitted() ? Zone2ControlParameter.Three : Zone2ControlParameter.Two;
	}

	@Override
	public double getResponsiveness(final IConstants parameters, final EnergyCalculatorType energyCalculatorType, final ElectricityTariffType electricityTariffType) {
		/*
		BEISDOC
		NAME: Room heater responsiveness.
		DESCRIPTION: description
		TYPE: type
		UNIT: unit
		SAP: Table 4a (Category 10)
                SAP_COMPLIANT: Yes
		BREDEM: Defers to SAP
                BREDEM_COMPLIANT: N/A - out of scope
		NOTES: This will never actually get used, since we only care about the responsiveness of *primary* space heating systems.
		NOTES: We don't have any way to identify a solid-fuel stove here, so we always use the lower responsiveness when the room heater uses solid fuel.
		ID: room-heater-responsiveness
		CODSIEB
		*/
		switch (roomHeater.getFuel()) {
		case OIL:
		case ELECTRICITY:
			/*
			 * Electric room heaters currently also encompass portable electric heaters.
			 *
			 * This should not really be the case, but works ok.
			 */
		case MAINS_GAS:
		case BOTTLED_LPG:
		case BULK_LPG:
			return 1;

		case BIOMASS_PELLETS:
		case BIOMASS_WOOD:
		case BIOMASS_WOODCHIP:
		case HOUSE_COAL:
			return 0.50;

		case COMMUNITY_HEAT:
		default:
			throw new UnsupportedOperationException("Invalid or unknown fuel type for room heater " + roomHeater.getFuel());
		}
	}
}
