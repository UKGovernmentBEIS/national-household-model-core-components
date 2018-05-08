/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import uk.org.cse.nhm.energycalculator.impl.demands.IBredemShower;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shower</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.IShower#getTechnologyModel
 * <em>Technology Model</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getShower()
 * @model abstract="true"
 * @generated NOT
 */
public interface IShower extends IVisitorAccepter, IBredemShower {

    /**
     * Returns the value of the '<em><b>Technology Model</b></em>' container
     * reference. It is bidirectional and its opposite is '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getShower
     * <em>Shower</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Technology Model</em>' container reference
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Technology Model</em>' container reference.
     * @see #setTechnologyModel(ITechnologyModel)
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getShower_TechnologyModel()
     * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getShower
     * @model opposite="shower" transient="false"
     * @generated
     */
    ITechnologyModel getTechnologyModel();

    /**
     * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IShower#getTechnologyModel
     * <em>Technology Model</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Technology Model</em>' container
     * reference.
     * @see #getTechnologyModel()
     * @generated
     */
    void setTechnologyModel(ITechnologyModel value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @model required="true"
     *
     * @generated
     */
    double solarAdjustment();

} // IShower
