/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Water Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A WaterHeater is anything which meets the need for hot water in the house, for example a central DHW system, or a point-of-use heater.
 * <!-- end-model-doc -->
 *
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getWaterHeater()
 * @model abstract="true"
 * @generated
 */
public interface IWaterHeater extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns true if the proportion should be ignored
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isBroken();

	FuelType getFuel();

} // IWaterHeater
