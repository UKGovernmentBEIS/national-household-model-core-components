/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Solar Photovoltaic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getPeakPower <em>Peak Power</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getOwnUseProportion <em>Own Use Proportion</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarPhotovoltaic()
 * @model
 * @generated
 */
public interface ISolarPhotovoltaic extends IVisitorAccepter {
	/**
	 * Returns the value of the '<em><b>Peak Power</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Peak Power</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Peak Power</em>' attribute.
	 * @see #setPeakPower(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarPhotovoltaic_PeakPower()
	 * @model required="true"
	 * @generated
	 */
	double getPeakPower();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getPeakPower <em>Peak Power</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Peak Power</em>' attribute.
	 * @see #getPeakPower()
	 * @generated
	 */
	void setPeakPower(double value);

	/**
	 * Returns the value of the '<em><b>Own Use Proportion</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Own Use Proportion</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Own Use Proportion</em>' attribute.
	 * @see #setOwnUseProportion(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarPhotovoltaic_OwnUseProportion()
	 * @model required="true"
	 * @generated
	 */
	double getOwnUseProportion();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getOwnUseProportion <em>Own Use Proportion</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Own Use Proportion</em>' attribute.
	 * @see #getOwnUseProportion()
	 * @generated
	 */
	void setOwnUseProportion(double value);

} // ISolarPhotovoltaic
