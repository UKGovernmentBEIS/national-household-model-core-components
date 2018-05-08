/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers;

import uk.org.cse.nhm.hom.emf.technologies.IStoreContainer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Storage Combi Boiler</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler#isStoreInPrimaryCircuit
 * <em>Store In Primary Circuit</em>}</li>
 * </ul>
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getStorageCombiBoiler()
 * @model
 * @generated
 */
public interface IStorageCombiBoiler extends ICombiBoiler, IStoreContainer {

    /**
     * Returns the value of the '<em><b>Store In Primary Circuit</b></em>'
     * attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Store In Primary Circuit</em>' attribute isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Store In Primary Circuit</em>' attribute.
     * @see #setStoreInPrimaryCircuit(boolean)
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getStorageCombiBoiler_StoreInPrimaryCircuit()
     * @model required="true"
     * @generated
     */
    boolean isStoreInPrimaryCircuit();

    /**
     * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler#isStoreInPrimaryCircuit
     * <em>Store In Primary Circuit</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Store In Primary Circuit</em>'
     * attribute.
     * @see #isStoreInPrimaryCircuit()
     * @generated
     */
    void setStoreInPrimaryCircuit(boolean value);

} // IStorageCombiBoiler
