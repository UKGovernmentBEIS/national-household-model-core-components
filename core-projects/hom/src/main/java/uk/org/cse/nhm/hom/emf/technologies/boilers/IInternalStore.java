/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Internal Store</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IInternalStore#getVolume
 * <em>Volume</em>}</li>
 * </ul>
 * </p>
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getInternalStore()
 * @model
 * @generated
 */
public interface IInternalStore extends ICombiAddition {

    /**
     * Returns the value of the '<em><b>Volume</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Volume</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Volume</em>' attribute.
     * @see #setVolume(double)
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getInternalStore_Volume()
     * @model required="true"
     * @generated
     */
    double getVolume();

    /**
     * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IInternalStore#getVolume
     * <em>Volume</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Volume</em>' attribute.
     * @see #getVolume()
     * @generated
     */
    void setVolume(double value);

} // IInternalStore
