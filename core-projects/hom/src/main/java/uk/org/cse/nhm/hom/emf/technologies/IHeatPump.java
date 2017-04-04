/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import uk.org.cse.nhm.hom.emf.util.Efficiency;



/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Heat Pump</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getSourceType <em>Source Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getCoefficientOfPerformance <em>Coefficient Of Performance</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#isWeatherCompensated <em>Weather Compensated</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#isAuxiliaryPresent <em>Auxiliary Present</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getHybrid <em>Hybrid</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatPump()
 * @model
 * @generated
 */
public interface IHeatPump extends IIndividualHeatSource, IVisitorAccepter {

	/**
	 * Returns the value of the '<em><b>Source Type</b></em>' attribute.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType
	 * @see #setSourceType(HeatPumpSourceType)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatPump_SourceType()
	 * @model required="true"
	 * @generated
	 */
	HeatPumpSourceType getSourceType();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getSourceType <em>Source Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType
	 * @see #getSourceType()
	 * @generated
	 */
	void setSourceType(HeatPumpSourceType value);

	/**
	 * Returns the value of the '<em><b>Coefficient Of Performance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Coefficient Of Performance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Coefficient Of Performance</em>' attribute.
	 * @see #setCoefficientOfPerformance(Efficiency)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatPump_CoefficientOfPerformance()
	 * @model dataType="uk.org.cse.nhm.hom.emf.technologies.Efficiency" required="true"
	 * @generated
	 */
	Efficiency getCoefficientOfPerformance();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getCoefficientOfPerformance <em>Coefficient Of Performance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Coefficient Of Performance</em>' attribute.
	 * @see #getCoefficientOfPerformance()
	 * @generated
	 */
	void setCoefficientOfPerformance(Efficiency value);

	/**
	 * Returns the value of the '<em><b>Weather Compensated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Weather Compensated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Weather Compensated</em>' attribute.
	 * @see #setWeatherCompensated(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatPump_WeatherCompensated()
	 * @model required="true"
	 * @generated
	 */
	boolean isWeatherCompensated();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#isWeatherCompensated <em>Weather Compensated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Weather Compensated</em>' attribute.
	 * @see #isWeatherCompensated()
	 * @generated
	 */
	void setWeatherCompensated(boolean value);

	/**
	 * Returns the value of the '<em><b>Auxiliary Present</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Auxiliary Present</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Auxiliary Present</em>' attribute.
	 * @see #setAuxiliaryPresent(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatPump_AuxiliaryPresent()
	 * @model required="true"
	 * @generated
	 */
	boolean isAuxiliaryPresent();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#isAuxiliaryPresent <em>Auxiliary Present</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Auxiliary Present</em>' attribute.
	 * @see #isAuxiliaryPresent()
	 * @generated
	 */
	void setAuxiliaryPresent(boolean value);

	/**
	 * Returns the value of the '<em><b>Hybrid</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hybrid</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hybrid</em>' containment reference.
	 * @see #setHybrid(IHybridHeater)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatPump_Hybrid()
	 * @model containment="true"
	 * @generated
	 */
	IHybridHeater getHybrid();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getHybrid <em>Hybrid</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hybrid</em>' containment reference.
	 * @see #getHybrid()
	 * @generated
	 */
	void setHybrid(IHybridHeater value);
} // IHeatPump
