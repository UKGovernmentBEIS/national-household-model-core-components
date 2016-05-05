/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import uk.org.cse.nhm.hom.emf.util.Efficiency;


/**
 * <!-- begin-user-doc -->
 * A point of use water heater is a thing like the Hydroboil in the CSE kitchen; it is effectively a boiling
 * water tap, which demand-heats the water at the point of use.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#getFuelType <em>Fuel Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#isMultipoint <em>Multipoint</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#getEfficiency <em>Efficiency</em>}</li>
 * </ul>
 * </p>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getPointOfUseWaterHeater()
 * @model
 * @generated
 */
public interface IPointOfUseWaterHeater extends IWaterHeater, IVisitorAccepter {
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
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getPointOfUseWaterHeater_FuelType()
	 * @model required="true"
	 * @generated
	 */
	FuelType getFuelType();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#getFuelType <em>Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fuel Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #getFuelType()
	 * @generated
	 */
	void setFuelType(FuelType value);

	/**
	 * Returns the value of the '<em><b>Multipoint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multipoint</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multipoint</em>' attribute.
	 * @see #setMultipoint(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getPointOfUseWaterHeater_Multipoint()
	 * @model required="true"
	 * @generated
	 */
	boolean isMultipoint();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#isMultipoint <em>Multipoint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multipoint</em>' attribute.
	 * @see #isMultipoint()
	 * @generated
	 */
	void setMultipoint(boolean value);

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
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getPointOfUseWaterHeater_Efficiency()
	 * @model dataType="uk.org.cse.nhm.hom.emf.technologies.Efficiency" required="true"
	 * @generated
	 */
	Efficiency getEfficiency();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#getEfficiency <em>Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Efficiency</em>' attribute.
	 * @see #getEfficiency()
	 * @generated
	 */
	void setEfficiency(Efficiency value);

} // IPointOfUseWaterHeater
