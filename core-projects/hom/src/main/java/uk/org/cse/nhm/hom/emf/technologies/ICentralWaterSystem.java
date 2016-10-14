/**
 */
package uk.org.cse.nhm.hom.emf.technologies;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Central Water System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isStoreInPrimaryCircuit <em>Store In Primary Circuit</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isPrimaryPipeworkInsulated <em>Primary Pipework Insulated</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isSeparatelyTimeControlled <em>Separately Time Controlled</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getSolarWaterHeater <em>Solar Water Heater</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getPrimaryWaterHeater <em>Primary Water Heater</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getSecondaryWaterHeater <em>Secondary Water Heater</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralWaterSystem()
 * @model
 * @generated
 */
public interface ICentralWaterSystem extends IWaterHeater, IVisitorAccepter, IStoreContainer {
	/**
	 * Returns the value of the '<em><b>Store In Primary Circuit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Store In Primary Circuit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Store In Primary Circuit</em>' attribute.
	 * @see #setStoreInPrimaryCircuit(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralWaterSystem_StoreInPrimaryCircuit()
	 * @model required="true"
	 * @generated
	 */
	boolean isStoreInPrimaryCircuit();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isStoreInPrimaryCircuit <em>Store In Primary Circuit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Store In Primary Circuit</em>' attribute.
	 * @see #isStoreInPrimaryCircuit()
	 * @generated
	 */
	void setStoreInPrimaryCircuit(boolean value);

	/**
	 * Returns the value of the '<em><b>Primary Pipework Insulated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Pipework Insulated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Pipework Insulated</em>' attribute.
	 * @see #setPrimaryPipeworkInsulated(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralWaterSystem_PrimaryPipeworkInsulated()
	 * @model required="true"
	 * @generated
	 */
	boolean isPrimaryPipeworkInsulated();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isPrimaryPipeworkInsulated <em>Primary Pipework Insulated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Pipework Insulated</em>' attribute.
	 * @see #isPrimaryPipeworkInsulated()
	 * @generated
	 */
	void setPrimaryPipeworkInsulated(boolean value);

	/**
	 * Returns the value of the '<em><b>Separately Time Controlled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Separately Time Controlled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Separately Time Controlled</em>' attribute.
	 * @see #setSeparatelyTimeControlled(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralWaterSystem_SeparatelyTimeControlled()
	 * @model required="true"
	 * @generated
	 */
	boolean isSeparatelyTimeControlled();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isSeparatelyTimeControlled <em>Separately Time Controlled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Separately Time Controlled</em>' attribute.
	 * @see #isSeparatelyTimeControlled()
	 * @generated
	 */
	void setSeparatelyTimeControlled(boolean value);

	/**
	 * Returns the value of the '<em><b>Solar Water Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Solar Water Heater</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solar Water Heater</em>' containment reference.
	 * @see #setSolarWaterHeater(ISolarWaterHeater)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralWaterSystem_SolarWaterHeater()
	 * @model containment="true"
	 * @generated
	 */
	ISolarWaterHeater getSolarWaterHeater();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getSolarWaterHeater <em>Solar Water Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Solar Water Heater</em>' containment reference.
	 * @see #getSolarWaterHeater()
	 * @generated
	 */
	void setSolarWaterHeater(ISolarWaterHeater value);

	/**
	 * Returns the value of the '<em><b>Primary Water Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Water Heater</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Water Heater</em>' containment reference.
	 * @see #setPrimaryWaterHeater(ICentralWaterHeater)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralWaterSystem_PrimaryWaterHeater()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ICentralWaterHeater getPrimaryWaterHeater();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getPrimaryWaterHeater <em>Primary Water Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Water Heater</em>' containment reference.
	 * @see #getPrimaryWaterHeater()
	 * @generated
	 */
	void setPrimaryWaterHeater(ICentralWaterHeater value);

	/**
	 * Returns the value of the '<em><b>Secondary Water Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Secondary Water Heater</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Water Heater</em>' containment reference.
	 * @see #setSecondaryWaterHeater(ICentralWaterHeater)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralWaterSystem_SecondaryWaterHeater()
	 * @model containment="true"
	 * @generated
	 */
	ICentralWaterHeater getSecondaryWaterHeater();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getSecondaryWaterHeater <em>Secondary Water Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary Water Heater</em>' containment reference.
	 * @see #getSecondaryWaterHeater()
	 * @generated
	 */
	void setSecondaryWaterHeater(ICentralWaterHeater value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns true if the proportion should be ignored
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return getPrimaryWaterHeater() == null && getSecondaryWaterHeater() == null && \n\t\t\t\t(getStore() == null || getStore().getImmersionHeater() == null);'"
	 * @generated
	 */
	boolean isBroken();

	public boolean hasImmersionHeater();

} // ICentralWaterSystem
