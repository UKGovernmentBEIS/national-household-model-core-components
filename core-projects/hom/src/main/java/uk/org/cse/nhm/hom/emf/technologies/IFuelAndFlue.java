/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.ecore.EObject;

import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fuel And Flue</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue#getFuel <em>Fuel</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue#getFlueType <em>Flue Type</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getFuelAndFlue()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IFuelAndFlue extends EObject {
	/**
	 * Returns the value of the '<em><b>Fuel</b></em>' attribute.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.FuelType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fuel</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fuel</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #setFuel(FuelType)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getFuelAndFlue_Fuel()
	 * @model required="true"
	 * @generated
	 */
	FuelType getFuel();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue#getFuel <em>Fuel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fuel</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #getFuel()
	 * @generated
	 */
	void setFuel(FuelType value);

	/**
	 * Returns the value of the '<em><b>Flue Type</b></em>' attribute.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Flue Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flue Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType
	 * @see #setFlueType(FlueType)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getFuelAndFlue_FlueType()
	 * @model required="true"
	 * @generated
	 */
	FlueType getFlueType();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue#getFlueType <em>Flue Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Flue Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType
	 * @see #getFlueType()
	 * @generated
	 */
	void setFlueType(FlueType value);

} // IFuelAndFlue
