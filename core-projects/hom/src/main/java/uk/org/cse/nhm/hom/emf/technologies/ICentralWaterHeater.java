/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.ecore.EObject;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Central Water Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A central water heater is something which can provide heat into a central
 * water system. For example, this would be an immersion heater in the hot water
 * tank,a solar system, or a connection to a heat source like a boiler.
 * <!-- end-model-doc -->
 *
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralWaterHeater()
 * @model abstract="true"
 * @generated
 */
public interface ICentralWaterHeater extends EObject {

    /**
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>System</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @model kind="operation" required="true"
     * annotation="http://www.eclipse.org/emf/2002/GenModel body='return
     * (ICentralWaterSystem) eContainer();'"
     * @generated
     */
    ICentralWaterSystem getSystem();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @model required="true"
     *
     * @generated
     */
    boolean causesPipeworkLosses();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @model kind="operation" required="true"
     *
     * @generated
     */
    boolean isCommunityHeating();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @model kind="operation" required="true"
     *
     * @generated
     */
    boolean isSolar();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @model required="true"
     * parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IInternalParameters"
     * parametersRequired="true"
     * stateDataType="uk.org.cse.nhm.hom.emf.technologies.IEnergyState"
     * stateRequired="true" storeIsPrimaryRequired="true"
     * primaryLossesRequired="true" distributionLossFactorRequired="true"
     * systemProportionRequired="true"
     *
     * @generated
     */
    double generateHotWaterAndPrimaryGains(IInternalParameters parameters, IEnergyState state, IWaterTank store, boolean storeIsPrimary, double primaryLosses, double distributionLossFactor, double systemProportion);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @model
     * parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IInternalParameters"
     * parametersRequired="true"
     * stateDataType="uk.org.cse.nhm.hom.emf.technologies.IEnergyState"
     * stateRequired="true" storeIsPrimaryRequired="true"
     * systemLossesRequired="true"
     *
     * @generated
     */
    void generateSystemGains(IInternalParameters parameters, IEnergyState state, IWaterTank store, boolean storeIsPrimary, double systemLosses);

    FuelType getFuel();
} // ICentralWaterHeater
