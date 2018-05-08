/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Heating System
 * Control Type</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatingSystemControlType()
 * @model
 * @generated
 */
public enum HeatingSystemControlType implements Enumerator {
    /**
     * The '<em><b>Programmer</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #PROGRAMMER_VALUE
     * @generated
     * @ordered
     */
    PROGRAMMER(0, "Programmer", "Programmer"),
    /**
     * The '<em><b>Room Thermostat</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #ROOM_THERMOSTAT_VALUE
     * @generated
     * @ordered
     */
    ROOM_THERMOSTAT(1, "RoomThermostat", "RoomThermostat"),
    /**
     * The '<em><b>Thermostatic Radiator Valve</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #THERMOSTATIC_RADIATOR_VALVE_VALUE
     * @generated
     * @ordered
     */
    THERMOSTATIC_RADIATOR_VALVE(2, "ThermostaticRadiatorValve", "ThermostaticRadiatorValve"),
    /**
     * The '<em><b>Time Temperature Zone Control</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #TIME_TEMPERATURE_ZONE_CONTROL_VALUE
     * @generated
     * @ordered
     */
    TIME_TEMPERATURE_ZONE_CONTROL(3, "TimeTemperatureZoneControl", "TimeTemperatureZoneControl"),
    /**
     * The '<em><b>Appliance Thermostat</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #APPLIANCE_THERMOSTAT_VALUE
     * @generated
     * @ordered
     */
    APPLIANCE_THERMOSTAT(4, "ApplianceThermostat", "ApplianceThermostat"),
    /**
     * The '<em><b>Bypass</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #BYPASS_VALUE
     * @generated
     * @ordered
     */
    BYPASS(5, "Bypass", "Bypass"),
    /**
     * The '<em><b>Flow Switch</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #FLOW_SWITCH_VALUE
     * @generated
     * @ordered
     */
    FLOW_SWITCH(6, "FlowSwitch", "FlowSwitch"),
    /**
     * The '<em><b>Boiler Energy Manager</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #BOILER_ENERGY_MANAGER_VALUE
     * @generated
     * @ordered
     */
    BOILER_ENERGY_MANAGER(7, "BoilerEnergyManager", "BoilerEnergyManager"),
    /**
     * The '<em><b>Boiler Interlock</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #BOILER_INTERLOCK_VALUE
     * @generated
     * @ordered
     */
    BOILER_INTERLOCK(8, "BoilerInterlock", "BoilerInterlock"), /**
     * The '<em><b>Delayed Start Thermostat</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #DELAYED_START_THERMOSTAT_VALUE
     * @generated
     * @ordered
     */
    DELAYED_START_THERMOSTAT(9, "DelayedStartThermostat", "DelayedStartThermostat");

    /**
     * The '<em><b>Programmer</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Programmer</b></em>' literal object isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #PROGRAMMER
     * @model name="Programmer"
     * @generated
     * @ordered
     */
    public static final int PROGRAMMER_VALUE = 0;

    /**
     * The '<em><b>Room Thermostat</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Room Thermostat</b></em>' literal object isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #ROOM_THERMOSTAT
     * @model name="RoomThermostat"
     * @generated
     * @ordered
     */
    public static final int ROOM_THERMOSTAT_VALUE = 1;

    /**
     * The '<em><b>Thermostatic Radiator Valve</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Thermostatic Radiator Valve</b></em>' literal
     * object isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #THERMOSTATIC_RADIATOR_VALVE
     * @model name="ThermostaticRadiatorValve"
     * @generated
     * @ordered
     */
    public static final int THERMOSTATIC_RADIATOR_VALVE_VALUE = 2;

    /**
     * The '<em><b>Time Temperature Zone Control</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Time Temperature Zone Control</b></em>' literal
     * object isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #TIME_TEMPERATURE_ZONE_CONTROL
     * @model name="TimeTemperatureZoneControl"
     * @generated
     * @ordered
     */
    public static final int TIME_TEMPERATURE_ZONE_CONTROL_VALUE = 3;

    /**
     * The '<em><b>Appliance Thermostat</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Appliance Thermostat</b></em>' literal object
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #APPLIANCE_THERMOSTAT
     * @model name="ApplianceThermostat"
     * @generated
     * @ordered
     */
    public static final int APPLIANCE_THERMOSTAT_VALUE = 4;

    /**
     * The '<em><b>Bypass</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Bypass</b></em>' literal object isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #BYPASS
     * @model name="Bypass"
     * @generated
     * @ordered
     */
    public static final int BYPASS_VALUE = 5;

    /**
     * The '<em><b>Flow Switch</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Flow Switch</b></em>' literal object isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #FLOW_SWITCH
     * @model name="FlowSwitch"
     * @generated
     * @ordered
     */
    public static final int FLOW_SWITCH_VALUE = 6;

    /**
     * The '<em><b>Boiler Energy Manager</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Boiler Energy Manager</b></em>' literal object
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #BOILER_ENERGY_MANAGER
     * @model name="BoilerEnergyManager"
     * @generated
     * @ordered
     */
    public static final int BOILER_ENERGY_MANAGER_VALUE = 7;

    /**
     * The '<em><b>Boiler Interlock</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Boiler Interlock</b></em>' literal object isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #BOILER_INTERLOCK
     * @model name="BoilerInterlock"
     * @generated
     * @ordered
     */
    public static final int BOILER_INTERLOCK_VALUE = 8;

    /**
     * The '<em><b>Delayed Start Thermostat</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>Delayed Start Thermostat</b></em>' literal
     * object isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @see #DELAYED_START_THERMOSTAT
     * @model name="DelayedStartThermostat"
     * @generated
     * @ordered
     */
    public static final int DELAYED_START_THERMOSTAT_VALUE = 9;

    /**
     * An array of all the '<em><b>Heating System Control Type</b></em>'
     * enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private static final HeatingSystemControlType[] VALUES_ARRAY
            = new HeatingSystemControlType[]{
                PROGRAMMER,
                ROOM_THERMOSTAT,
                THERMOSTATIC_RADIATOR_VALVE,
                TIME_TEMPERATURE_ZONE_CONTROL,
                APPLIANCE_THERMOSTAT,
                BYPASS,
                FLOW_SWITCH,
                BOILER_ENERGY_MANAGER,
                BOILER_INTERLOCK,
                DELAYED_START_THERMOSTAT,};

    /**
     * A public read-only list of all the '<em><b>Heating System Control
     * Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<HeatingSystemControlType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Heating System Control Type</b></em>' literal with
     * the specified literal value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static HeatingSystemControlType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            HeatingSystemControlType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Heating System Control Type</b></em>' literal with
     * the specified name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static HeatingSystemControlType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            HeatingSystemControlType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Heating System Control Type</b></em>' literal with
     * the specified integer value.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static HeatingSystemControlType get(int value) {
        switch (value) {
            case PROGRAMMER_VALUE:
                return PROGRAMMER;
            case ROOM_THERMOSTAT_VALUE:
                return ROOM_THERMOSTAT;
            case THERMOSTATIC_RADIATOR_VALVE_VALUE:
                return THERMOSTATIC_RADIATOR_VALVE;
            case TIME_TEMPERATURE_ZONE_CONTROL_VALUE:
                return TIME_TEMPERATURE_ZONE_CONTROL;
            case APPLIANCE_THERMOSTAT_VALUE:
                return APPLIANCE_THERMOSTAT;
            case BYPASS_VALUE:
                return BYPASS;
            case FLOW_SWITCH_VALUE:
                return FLOW_SWITCH;
            case BOILER_ENERGY_MANAGER_VALUE:
                return BOILER_ENERGY_MANAGER;
            case BOILER_INTERLOCK_VALUE:
                return BOILER_INTERLOCK;
            case DELAYED_START_THERMOSTAT_VALUE:
                return DELAYED_START_THERMOSTAT;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    private HeatingSystemControlType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public int getValue() {
        return value;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string
     * representation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }

} //HeatingSystemControlType
