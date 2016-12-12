/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import uk.org.cse.nhm.hom.emf.util.Efficiency;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Room Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A room heater is a direct room heating system; some room heaters
 * may also have a "back boiler" (the backBoiler reference), which can be
 * connected to a central heating system or a domestic hot water system
 * to produce some additional heating or hot water.
 * 
 * Typically this would look something like one of those old gas fireplaces,
 * placed in a chimney.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IRoomHeater#getEfficiency <em>Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IRoomHeater#isThermostatFitted <em>Thermostat Fitted</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getRoomHeater()
 * @model
 * @generated
 */
public interface IRoomHeater extends ISpaceHeater, IVisitorAccepter, IFuelAndFlue {
	/**
	 * Returns the value of the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This is the efficiency of the room heater as a space heater; back boilers will contain a boiler,
	 * which  may have a separate efficiency number on them.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Efficiency</em>' attribute.
	 * @see #setEfficiency(Efficiency)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getRoomHeater_Efficiency()
	 * @model dataType="uk.org.cse.nhm.hom.emf.technologies.Efficiency" required="true"
	 * @generated
	 */
	Efficiency getEfficiency();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IRoomHeater#getEfficiency <em>Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Efficiency</em>' attribute.
	 * @see #getEfficiency()
	 * @generated
	 */
	void setEfficiency(Efficiency value);

	/**
	 * Returns the value of the '<em><b>Thermostat Fitted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Thermostat Fitted</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Thermostat Fitted</em>' attribute.
	 * @see #setThermostatFitted(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getRoomHeater_ThermostatFitted()
	 * @model required="true"
	 * @generated
	 */
	boolean isThermostatFitted();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IRoomHeater#isThermostatFitted <em>Thermostat Fitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thermostat Fitted</em>' attribute.
	 * @see #isThermostatFitted()
	 * @generated
	 */
	void setThermostatFitted(boolean value);

} // IRoomHeater
