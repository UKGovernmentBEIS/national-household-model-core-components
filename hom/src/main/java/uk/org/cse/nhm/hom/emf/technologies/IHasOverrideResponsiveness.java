/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Has Override Responsiveness</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHasOverrideResponsiveness#getOverrideResponsiveness <em>Override Responsiveness</em>}</li>
 * </ul>
 * </p>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHasOverrideResponsiveness()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IHasOverrideResponsiveness extends EObject {
	/**
	 * Returns the value of the '<em><b>Override Responsiveness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Override Responsiveness</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Override Responsiveness</em>' attribute.
	 * @see #isSetOverrideResponsiveness()
	 * @see #unsetOverrideResponsiveness()
	 * @see #setOverrideResponsiveness(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHasOverrideResponsiveness_OverrideResponsiveness()
	 * @model unsettable="true"
	 * @generated
	 */
	double getOverrideResponsiveness();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHasOverrideResponsiveness#getOverrideResponsiveness <em>Override Responsiveness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Override Responsiveness</em>' attribute.
	 * @see #isSetOverrideResponsiveness()
	 * @see #unsetOverrideResponsiveness()
	 * @see #getOverrideResponsiveness()
	 * @generated
	 */
	void setOverrideResponsiveness(double value);

	/**
	 * Unsets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHasOverrideResponsiveness#getOverrideResponsiveness <em>Override Responsiveness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOverrideResponsiveness()
	 * @see #getOverrideResponsiveness()
	 * @see #setOverrideResponsiveness(double)
	 * @generated
	 */
	void unsetOverrideResponsiveness();

	/**
	 * Returns whether the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHasOverrideResponsiveness#getOverrideResponsiveness <em>Override Responsiveness</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Override Responsiveness</em>' attribute is set.
	 * @see #unsetOverrideResponsiveness()
	 * @see #getOverrideResponsiveness()
	 * @see #setOverrideResponsiveness(double)
	 * @generated
	 */
	boolean isSetOverrideResponsiveness();

} // IHasOverrideResponsiveness
