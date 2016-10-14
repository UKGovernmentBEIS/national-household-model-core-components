/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Store Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IStoreContainer#getStore <em>Store</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getStoreContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IStoreContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Store</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Store</em>' containment reference.
	 * @see #setStore(IWaterTank)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getStoreContainer_Store()
	 * @model containment="true"
	 * @generated
	 */
	IWaterTank getStore();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IStoreContainer#getStore <em>Store</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Store</em>' containment reference.
	 * @see #getStore()
	 * @generated
	 */
	void setStore(IWaterTank value);

} // IStoreContainer
