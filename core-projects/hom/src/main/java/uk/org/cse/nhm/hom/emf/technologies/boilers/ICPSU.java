/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers;

import uk.org.cse.nhm.hom.emf.technologies.IStoreContainer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CPSU</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU#getStoreTemperature <em>Store Temperature</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getCPSU()
 * @model
 * @generated
 */
public interface ICPSU extends IBoiler, IStoreContainer {
	/**
	 * Returns the value of the '<em><b>Store Temperature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Store Temperature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Store Temperature</em>' attribute.
	 * @see #setStoreTemperature(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getCPSU_StoreTemperature()
	 * @model required="true"
	 * @generated
	 */
	double getStoreTemperature();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU#getStoreTemperature <em>Store Temperature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Store Temperature</em>' attribute.
	 * @see #getStoreTemperature()
	 * @generated
	 */
	void setStoreTemperature(double value);

} // ICPSU
