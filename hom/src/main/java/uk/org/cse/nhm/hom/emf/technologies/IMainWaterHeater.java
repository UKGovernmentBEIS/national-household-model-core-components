/**
 */
package uk.org.cse.nhm.hom.emf.technologies;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Main Water Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater#getHeatSource <em>Heat Source</em>}</li>
 * </ul>
 * </p>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getMainWaterHeater()
 * @model
 * @generated
 */
public interface IMainWaterHeater extends ICentralWaterHeater {
	/**
	 * Returns the value of the '<em><b>Heat Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getWaterHeater <em>Water Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Heat Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Heat Source</em>' reference.
	 * @see #setHeatSource(IHeatSource)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getMainWaterHeater_HeatSource()
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getWaterHeater
	 * @model opposite="waterHeater" required="true"
	 * @generated
	 */
	IHeatSource getHeatSource();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater#getHeatSource <em>Heat Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Heat Source</em>' reference.
	 * @see #getHeatSource()
	 * @generated
	 */
	void setHeatSource(IHeatSource value);

} // IMainWaterHeater
