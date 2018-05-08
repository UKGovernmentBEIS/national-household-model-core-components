/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Space Heater</b></em>'.
 *
 * Implementations of this should consider inheriting from IHeatingSystem.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A space heater is anything which can meet some of the heat demand in the
 * house, so either a central heating system or a direct heater like a fireplace
 * or electric heater.
 * <!-- end-model-doc -->
 *
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSpaceHeater()
 * @model abstract="true"
 * @generated
 */
public interface ISpaceHeater extends EObject {

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Returns true if the proportion should be ignored
     * <!-- end-model-doc -->
     *
     * @model kind="operation" required="true"
     * annotation="http://www.eclipse.org/emf/2002/GenModel body='return
     * false;'"
     * @generated
     */
    boolean isBroken();

} // ISpaceHeater
