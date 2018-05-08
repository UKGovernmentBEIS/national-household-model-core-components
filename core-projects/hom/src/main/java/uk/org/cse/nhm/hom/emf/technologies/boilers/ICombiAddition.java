/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers;

import org.eclipse.emf.ecore.EObject;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Combi Addition</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getCombiAddition()
 * @model abstract="true"
 * @generated
 */
public interface ICombiAddition extends EObject {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @model required="true"
     * parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IInternalParameters"
     * parametersRequired="true"
     * stateDataType="uk.org.cse.nhm.hom.emf.technologies.IEnergyState"
     * stateRequired="true"
     *
     * @generated
     */
    double getAdditionalUsageLosses(IInternalParameters parameters, IEnergyState state);
} // ICombiAddition
