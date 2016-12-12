/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Hybrid Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHybridHeater#getFuel <em>Fuel</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHybridHeater#getEfficiency <em>Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHybridHeater#getFraction <em>Fraction</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHybridHeater()
 * @model
 * @generated
 */
public interface IHybridHeater extends EObject {
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
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHybridHeater_Fuel()
	 * @model required="true"
	 * @generated
	 */
	FuelType getFuel();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHybridHeater#getFuel <em>Fuel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fuel</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #getFuel()
	 * @generated
	 */
	void setFuel(FuelType value);

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
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHybridHeater_Efficiency()
	 * @model dataType="uk.org.cse.nhm.hom.emf.technologies.Efficiency" required="true"
	 * @generated
	 */
	Efficiency getEfficiency();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHybridHeater#getEfficiency <em>Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Efficiency</em>' attribute.
	 * @see #getEfficiency()
	 * @generated
	 */
	void setEfficiency(Efficiency value);

	/**
	 * Returns the value of the '<em><b>Fraction</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Double}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fraction</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fraction</em>' attribute list.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHybridHeater_Fraction()
	 * @model unique="false" lower="12" upper="12"
	 * @generated
	 */
	EList<Double> getFraction();

} // IHybridHeater
