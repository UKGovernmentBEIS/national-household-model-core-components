/**
 */
package uk.org.cse.nhm.hom.emf.technologies;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Storage Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A storage heater is a kind of heating system which stores electricity in some kind of thermal store during the night time and emits the heat during the daytime.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getResponsivenessOverride <em>Responsiveness Override</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getControlType <em>Control Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getStorageHeater()
 * @model
 * @generated
 */
public interface IStorageHeater extends IPrimarySpaceHeater, IVisitorAccepter, IOperationalCost {
	/**
	 * Returns the value of the '<em><b>Responsiveness Override</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Responsiveness Override</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Responsiveness Override</em>' attribute.
	 * @see #setResponsivenessOverride(Double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getStorageHeater_ResponsivenessOverride()
	 * @model required="true"
	 * @generated
	 */
	Double getResponsivenessOverride();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getResponsivenessOverride <em>Responsiveness Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Responsiveness Override</em>' attribute.
	 * @see #getResponsivenessOverride()
	 * @generated
	 */
	void setResponsivenessOverride(Double value);

	/**
	 * Returns the value of the '<em><b>Control Type</b></em>' attribute.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Control Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Control Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType
	 * @see #setControlType(StorageHeaterControlType)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getStorageHeater_ControlType()
	 * @model required="true"
	 * @generated
	 */
	StorageHeaterControlType getControlType();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getControlType <em>Control Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Control Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType
	 * @see #getControlType()
	 * @generated
	 */
	void setControlType(StorageHeaterControlType value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType
	 * @see #setType(StorageHeaterType)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getStorageHeater_Type()
	 * @model required="true"
	 * @generated
	 */
	StorageHeaterType getType();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType
	 * @see #getType()
	 * @generated
	 */
	void setType(StorageHeaterType value);

} // IStorageHeater
