/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Instantaneous Combi
 * Boiler</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler#getKeepHotFacility
 * <em>Keep Hot Facility</em>}</li>
 * </ul>
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getInstantaneousCombiBoiler()
 * @model
 * @generated
 */
public interface IInstantaneousCombiBoiler extends ICombiBoiler {

    /**
     * Returns the value of the '<em><b>Keep Hot Facility</b></em>' containment
     * reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Keep Hot Facility</em>' containment reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Keep Hot Facility</em>' containment
     * reference.
     * @see #setKeepHotFacility(IKeepHotFacility)
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getInstantaneousCombiBoiler_KeepHotFacility()
     * @model containment="true"
     * @generated
     */
    IKeepHotFacility getKeepHotFacility();

    /**
     * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler#getKeepHotFacility
     * <em>Keep Hot Facility</em>}' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Keep Hot Facility</em>'
     * containment reference.
     * @see #getKeepHotFacility()
     * @generated
     */
    void setKeepHotFacility(IKeepHotFacility value);
} // IInstantaneousCombiBoiler
