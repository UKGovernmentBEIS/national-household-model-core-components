package uk.org.cse.nhm.hom.constants.adjustments;

import uk.org.cse.nhm.energycalculator.api.ConstantDescription;
import uk.org.cse.nhm.energycalculator.api.IConstant;

/**
 * These are constants from SAP table 4e
 *
 * @author hinton
 *
 */
@ConstantDescription("Mean internal temperature adjustments, from SAP table 4e")
public enum TemperatureAdjustments implements IConstant {
    /*
	BEISDOC
	NAME: Temperature Adjustments Per Heating System
	DESCRIPTION: Adjustment to the mean internal temperature, based on the heating systems and heating controls present.
	TYPE: table
	UNIT: ℃
	SAP: Table 4e (Temperature adjustment column)
        SAP_COMPLIANT: Yes
        BREDEM_COMPLIANT: No
	SET: context.energy-constants
	ID: temperature-adjustments
	NOTES: Does not apply to Bredem
	CODSIEB
     */
    @ConstantDescription("The demand temperature adjustment for boiler systems with no thermostatic controls (top two rows of T4e Group 1)")
    BOILER_NO_THERMOSTAT(0.6),
    @ConstantDescription("The demand temperature adjustment for boiler systems with a delayed start thermostat")
    BOILER_DELAYED_START_THERMOSTAT(-0.15),
    @ConstantDescription("The demand temperature adjustment for systems with a CPSU or integrated thermal store")
    CPSU_OR_INTEGRATED_THERMAL_STORE(-0.1),
    @ConstantDescription("Demand temperature adjustment by storage heater control type (manual, automatic, celect)")
    STORAGE_HEATER(0.7, 0.4, 0.4),
    @ConstantDescription("Demand temperature adjustment for non-thermostatic room heaters")
    ROOM_HEATER_NO_THERMOSTAT(0.3),
    @ConstantDescription("Demand temperature adjustment for non-thermostatic warm air systems")
    WARM_AIR_SYSTEM_NO_THERMOSTAT(0.3),
    @ConstantDescription("Demand temperature adjustment for non-thermostatic warm air systems")
    HEAT_PUMP_NO_THERMOSTAT(0.3);

    private final double[] values;

    TemperatureAdjustments(final double... values) {
        this.values = values;
    }

    @Override
    public <T> T getValue(Class<T> clazz) {
        if (clazz.isAssignableFrom(double[].class)) {
            if (values == null) {
                throw new RuntimeException(this + " cannot be read as a double[]");
            }
            return clazz.cast(values);
        } else if (clazz.isAssignableFrom(Double.class)) {
            if (values.length != 1) {
                throw new RuntimeException(this + " cannot be read as a double");
            }
            return clazz.cast(values[0]);
        } else {
            throw new RuntimeException(this + " cannot be read as a " + clazz.getSimpleName());
        }
    }
}
