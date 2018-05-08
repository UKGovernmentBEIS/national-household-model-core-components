/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * Implementors are parts of a house which have a fixed operational cost every
 * year.
 *
 * @since 1.1.0
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.IOperationalCost#getAnnualOperationalCost
 * <em>Annual Operational Cost</em>}</li>
 * </ul>
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getOperationalCost()
 * @model
 * @generated
 */
public interface IOperationalCost extends EObject {

    /**
     * Returns the value of the '<em><b>Annual Operational Cost</b></em>'
     * attribute.
     * <!-- begin-user-doc -->
     * This is the annual cost of operating this part of the technology model.
     *
     * @since 1.1.0
     * <!-- end-user-doc -->
     * @return the value of the '<em>Annual Operational Cost</em>' attribute.
     * @see #setAnnualOperationalCost(double)
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getOperationalCost_AnnualOperationalCost()
     * @model required="true"
     * @generated
     */
    double getAnnualOperationalCost();

    /**
     * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IOperationalCost#getAnnualOperationalCost
     * <em>Annual Operational Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     *
     * @since 1.1.0
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Annual Operational Cost</em>'
     * attribute.
     * @see #getAnnualOperationalCost()
     * @generated
     */
    void setAnnualOperationalCost(double value);

} // IOperationalCost
