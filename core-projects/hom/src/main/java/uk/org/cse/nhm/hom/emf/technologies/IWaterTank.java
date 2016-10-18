/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.ecore.EObject;

import uk.org.cse.nhm.energycalculator.api.IInternalParameters;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Water Tank</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getInsulation <em>Insulation</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#isFactoryInsulation <em>Factory Insulation</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getVolume <em>Volume</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#isThermostatFitted <em>Thermostat Fitted</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getSolarStorageVolume <em>Solar Storage Volume</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getImmersionHeater <em>Immersion Heater</em>}</li>
 * </ul>
 * </p>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWaterTank()
 * @model
 * @generated
 */
public interface IWaterTank extends EObject {
	/**
	 * Returns the value of the '<em><b>Insulation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Insulation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Insulation</em>' attribute.
	 * @see #setInsulation(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWaterTank_Insulation()
	 * @model required="true"
	 * @generated
	 */
	double getInsulation();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getInsulation <em>Insulation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Insulation</em>' attribute.
	 * @see #getInsulation()
	 * @generated
	 */
	void setInsulation(double value);

	/**
	 * Returns the value of the '<em><b>Factory Insulation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Factory Insulation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Factory Insulation</em>' attribute.
	 * @see #setFactoryInsulation(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWaterTank_FactoryInsulation()
	 * @model required="true"
	 * @generated
	 */
	boolean isFactoryInsulation();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#isFactoryInsulation <em>Factory Insulation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Factory Insulation</em>' attribute.
	 * @see #isFactoryInsulation()
	 * @generated
	 */
	void setFactoryInsulation(boolean value);

	/**
	 * Returns the value of the '<em><b>Volume</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Volume</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Volume</em>' attribute.
	 * @see #setVolume(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWaterTank_Volume()
	 * @model required="true"
	 * @generated
	 */
	double getVolume();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getVolume <em>Volume</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Volume</em>' attribute.
	 * @see #getVolume()
	 * @generated
	 */
	void setVolume(double value);

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
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWaterTank_ThermostatFitted()
	 * @model required="true"
	 * @generated
	 */
	boolean isThermostatFitted();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#isThermostatFitted <em>Thermostat Fitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thermostat Fitted</em>' attribute.
	 * @see #isThermostatFitted()
	 * @generated
	 */
	void setThermostatFitted(boolean value);

	/**
	 * Returns the value of the '<em><b>Solar Storage Volume</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Solar Storage Volume</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solar Storage Volume</em>' attribute.
	 * @see #setSolarStorageVolume(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWaterTank_SolarStorageVolume()
	 * @model required="true"
	 * @generated
	 */
	double getSolarStorageVolume();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getSolarStorageVolume <em>Solar Storage Volume</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Solar Storage Volume</em>' attribute.
	 * @see #getSolarStorageVolume()
	 * @generated
	 */
	void setSolarStorageVolume(double value);

	/**
	 * Returns the value of the '<em><b>Immersion Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Immersion Heater</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Immersion Heater</em>' containment reference.
	 * @see #setImmersionHeater(IImmersionHeater)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWaterTank_ImmersionHeater()
	 * @model containment="true"
	 * @generated
	 */
	IImmersionHeater getImmersionHeater();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getImmersionHeater <em>Immersion Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Immersion Heater</em>' containment reference.
	 * @see #getImmersionHeater()
	 * @generated
	 */
	void setImmersionHeater(IImmersionHeater value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IInternalParameters" parametersRequired="true"
	 * @generated
	 */
	double getStandingLosses(IInternalParameters parameters);

} // IWaterTank
