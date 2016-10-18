/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Adjuster</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IAdjuster#getFuelTypes <em>Fuel Types</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IAdjuster#getDeltas <em>Deltas</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IAdjuster#getGains <em>Gains</em>}</li>
 * </ul>
 * </p>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getAdjuster()
 * @model
 * @generated
 */
public interface IAdjuster extends INamed, IVisitorAccepter {
	/**
	 * Returns the value of the '<em><b>Fuel Types</b></em>' attribute list.
	 * The list contents are of type {@link uk.org.cse.nhm.hom.emf.technologies.FuelType}.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.FuelType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fuel Types</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fuel Types</em>' attribute list.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getAdjuster_FuelTypes()
	 * @model
	 * @generated
	 */
	EList<FuelType> getFuelTypes();

	/**
	 * Returns the value of the '<em><b>Deltas</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Double}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deltas</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deltas</em>' attribute list.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getAdjuster_Deltas()
	 * @model
	 * @generated
	 */
	EList<Double> getDeltas();

	/**
	 * Returns the value of the '<em><b>Gains</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gains</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gains</em>' attribute.
	 * @see #setGains(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getAdjuster_Gains()
	 * @model required="true"
	 * @generated
	 */
	double getGains();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IAdjuster#getGains <em>Gains</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gains</em>' attribute.
	 * @see #getGains()
	 * @generated
	 */
	void setGains(double value);

} // IAdjuster
