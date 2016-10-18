/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Has Installation Year</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear#getInstallationYear <em>Installation Year</em>}</li>
 * </ul>
 * </p>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHasInstallationYear()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IHasInstallationYear extends EObject {
	/**
	 * Returns the value of the '<em><b>Installation Year</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Installation Year</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Installation Year</em>' attribute.
	 * @see #isSetInstallationYear()
	 * @see #unsetInstallationYear()
	 * @see #setInstallationYear(int)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHasInstallationYear_InstallationYear()
	 * @model default="0" unsettable="true" required="true"
	 * @generated
	 */
	int getInstallationYear();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear#getInstallationYear <em>Installation Year</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Installation Year</em>' attribute.
	 * @see #isSetInstallationYear()
	 * @see #unsetInstallationYear()
	 * @see #getInstallationYear()
	 * @generated
	 */
	void setInstallationYear(int value);

	/**
	 * Unsets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear#getInstallationYear <em>Installation Year</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetInstallationYear()
	 * @see #getInstallationYear()
	 * @see #setInstallationYear(int)
	 * @generated
	 */
	void unsetInstallationYear();

	/**
	 * Returns whether the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear#getInstallationYear <em>Installation Year</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Installation Year</em>' attribute is set.
	 * @see #unsetInstallationYear()
	 * @see #getInstallationYear()
	 * @see #setInstallationYear(int)
	 * @generated
	 */
	boolean isSetInstallationYear();

} // IHasInstallationYear
