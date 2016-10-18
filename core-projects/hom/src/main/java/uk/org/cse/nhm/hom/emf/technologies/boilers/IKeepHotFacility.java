/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers;

import org.eclipse.emf.ecore.EObject;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Keep Hot Facility</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility#isTimeClock <em>Time Clock</em>}</li>
 * </ul>
 * </p>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getKeepHotFacility()
 * @model
 * @generated
 */
public interface IKeepHotFacility extends EObject {
	/**
	 * Returns the value of the '<em><b>Time Clock</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Clock</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Clock</em>' attribute.
	 * @see #setTimeClock(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getKeepHotFacility_TimeClock()
	 * @model required="true"
	 * @generated
	 */
	boolean isTimeClock();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility#isTimeClock <em>Time Clock</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Clock</em>' attribute.
	 * @see #isTimeClock()
	 * @generated
	 */
	void setTimeClock(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IInternalParameters" parametersRequired="true" stateDataType="uk.org.cse.nhm.hom.emf.technologies.IEnergyState" stateRequired="true"
	 * @generated
	 */
	double getAdditionalUsageLosses(IInternalParameters parameters, IEnergyState state);

} // IKeepHotFacility
