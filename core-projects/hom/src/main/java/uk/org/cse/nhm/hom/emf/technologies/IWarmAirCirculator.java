/**
 */
package uk.org.cse.nhm.hom.emf.technologies;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Warm Air Circulator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator#getWarmAirSystem <em>Warm Air System</em>}</li>
 * </ul>
 * </p>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWarmAirCirculator()
 * @model
 * @generated
 */
public interface IWarmAirCirculator extends ICentralWaterHeater {
	/**
	 * Returns the value of the '<em><b>Warm Air System</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getCirculator <em>Circulator</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Warm Air System</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Warm Air System</em>' reference.
	 * @see #setWarmAirSystem(IWarmAirSystem)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWarmAirCirculator_WarmAirSystem()
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getCirculator
	 * @model opposite="circulator" required="true"
	 * @generated
	 */
	IWarmAirSystem getWarmAirSystem();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator#getWarmAirSystem <em>Warm Air System</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Warm Air System</em>' reference.
	 * @see #getWarmAirSystem()
	 * @generated
	 */
	void setWarmAirSystem(IWarmAirSystem value);

} // IWarmAirCirculator
