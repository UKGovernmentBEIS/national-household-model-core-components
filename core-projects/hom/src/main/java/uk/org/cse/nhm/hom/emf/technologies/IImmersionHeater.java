/**
 */
package uk.org.cse.nhm.hom.emf.technologies;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Immersion Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater#isDualCoil <em>Dual Coil</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getImmersionHeater()
 * @model
 * @generated
 */
public interface IImmersionHeater extends ICentralWaterHeater {
	/**
	 * Returns the value of the '<em><b>Dual Coil</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dual Coil</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dual Coil</em>' attribute.
	 * @see #setDualCoil(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getImmersionHeater_DualCoil()
	 * @model required="true"
	 * @generated
	 */
	boolean isDualCoil();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater#isDualCoil <em>Dual Coil</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dual Coil</em>' attribute.
	 * @see #isDualCoil()
	 * @generated
	 */
	void setDualCoil(boolean value);

} // IImmersionHeater
