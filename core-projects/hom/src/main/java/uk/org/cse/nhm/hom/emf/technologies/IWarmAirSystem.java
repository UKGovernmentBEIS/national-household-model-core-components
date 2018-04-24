/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.common.util.EList;

import uk.org.cse.nhm.hom.emf.util.Efficiency;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Warm Air System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Warm air systems are things which directly heat air and then blow it around
 * through ducts (as opposed to central heating systems, which use hot water
 * to transport the heat to a radiator).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getFuelType <em>Fuel Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getEfficiency <em>Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getCirculator <em>Circulator</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getControls <em>Controls</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWarmAirSystem()
 * @model
 * @generated
 */
public interface IWarmAirSystem extends IPrimarySpaceHeater, IVisitorAccepter {

	/**
	 * Returns the value of the '<em><b>Fuel Type</b></em>' attribute.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.FuelType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fuel Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fuel Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #setFuelType(FuelType)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWarmAirSystem_FuelType()
	 * @model required="true"
	 * @generated
	 */
	FuelType getFuelType();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getFuelType <em>Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fuel Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #getFuelType()
	 * @generated
	 */
	void setFuelType(FuelType value);

	/**
	 * Returns the value of the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Efficiency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Efficiency</em>' attribute.
	 * @see #setEfficiency(Efficiency)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWarmAirSystem_Efficiency()
	 * @model dataType="uk.org.cse.nhm.hom.emf.technologies.Efficiency" required="true"
	 * @generated
	 */
	Efficiency getEfficiency();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getEfficiency <em>Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Efficiency</em>' attribute.
	 * @see #getEfficiency()
	 * @generated
	 */
	void setEfficiency(Efficiency value);

	/**
	 * Returns the value of the '<em><b>Circulator</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator#getWarmAirSystem <em>Warm Air System</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Circulator</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Circulator</em>' reference.
	 * @see #setCirculator(IWarmAirCirculator)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWarmAirSystem_Circulator()
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator#getWarmAirSystem
	 * @model opposite="warmAirSystem"
	 * @generated
	 */
	IWarmAirCirculator getCirculator();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getCirculator <em>Circulator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Circulator</em>' reference.
	 * @see #getCirculator()
	 * @generated
	 */
	void setCirculator(IWarmAirCirculator value);

	/**
	 * Returns the value of the '<em><b>Controls</b></em>' attribute list.
	 * The list contents are of type {@link uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType}.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Controls</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Controls</em>' attribute list.
	 * @see uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWarmAirSystem_Controls()
	 * @model ordered="false"
	 * @generated
	 */
	EList<HeatingSystemControlType> getControls();
} // IWarmAirSystem
