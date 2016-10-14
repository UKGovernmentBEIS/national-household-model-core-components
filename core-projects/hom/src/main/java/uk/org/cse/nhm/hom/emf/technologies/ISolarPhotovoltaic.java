/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import uk.org.cse.nhm.hom.emf.util.Efficiency;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Solar Photovoltaic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getArea <em>Area</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getEfficiency <em>Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getOwnUseProportion <em>Own Use Proportion</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarPhotovoltaic()
 * @model
 * @generated
 */
public interface ISolarPhotovoltaic extends IVisitorAccepter {
	/**
	 * Returns the value of the '<em><b>Area</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Area</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Area</em>' attribute.
	 * @see #setArea(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarPhotovoltaic_Area()
	 * @model required="true"
	 * @generated
	 */
	double getArea();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getArea <em>Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Area</em>' attribute.
	 * @see #getArea()
	 * @generated
	 */
	void setArea(double value);

	/**
	 * Returns the value of the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Efficiency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Efficiency</em>' attribute.
	 * @see #setEfficiency(Efficiency)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarPhotovoltaic_Efficiency()
	 * @model dataType="uk.org.cse.nhm.hom.emf.technologies.Efficiency" required="true"
	 * @generated
	 */
	Efficiency getEfficiency();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getEfficiency <em>Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Efficiency</em>' attribute.
	 * @see #getEfficiency()
	 * @generated
	 */
	void setEfficiency(Efficiency value);

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
