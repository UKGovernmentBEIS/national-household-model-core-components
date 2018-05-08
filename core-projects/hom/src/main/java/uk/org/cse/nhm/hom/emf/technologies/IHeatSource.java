/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.common.util.EList;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Heat Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A heat source is a device (like a boiler, or community heating heat
 * exchanger) which can provide heat to wet heating and hot water systems. On
 * its own, the device does nothing - it needs to be connected to a central
 * heating system and/or a central hot water system to work.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getWaterHeater
 * <em>Water Heater</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getSpaceHeater
 * <em>Space Heater</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatSource()
 * @model abstract="true"
 * @generated NOT
 */
public interface IHeatSource extends IFuelAndFlue, IOperationalCost, IHasInstallationYear, IHasZone2ControlParameter {

    /**
     * Returns the value of the '<em><b>Water Heater</b></em>' reference. It is
     * bidirectional and its opposite is '{@link uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater#getHeatSource
     * <em>Heat Source</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Water Heater</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Water Heater</em>' reference.
     * @see #setWaterHeater(IMainWaterHeater)
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatSource_WaterHeater()
     * @see uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater#getHeatSource
     * @model opposite="heatSource"
     * @generated
     */
    IMainWaterHeater getWaterHeater();

    /**
     * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getWaterHeater
     * <em>Water Heater</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Water Heater</em>' reference.
     * @see #getWaterHeater()
     * @generated
     */
    void setWaterHeater(IMainWaterHeater value);

    /**
     * Returns the value of the '<em><b>Space Heater</b></em>' reference. It is
     * bidirectional and its opposite is '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getHeatSource
     * <em>Heat Source</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Space Heater</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Space Heater</em>' reference.
     * @see #setSpaceHeater(ICentralHeatingSystem)
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatSource_SpaceHeater()
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getHeatSource
     * @model opposite="heatSource"
     * @generated
     */
    ICentralHeatingSystem getSpaceHeater();

    /**
     * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getSpaceHeater
     * <em>Space Heater</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Space Heater</em>' reference.
     * @see #getSpaceHeater()
     * @generated
     */
    void setSpaceHeater(ICentralHeatingSystem value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * This method will be invoked by the central heating system, if this heat
     * source is connected to the central heating system. The heat source should
     * use it to provide a heat transducer to the visitor.
     * <!-- end-model-doc -->
     *
     * @model constantsDataType="uk.org.cse.nhm.hom.emf.technologies.IConstants"
     * constantsRequired="true"
     * parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IEnergyCalculatorParameters"
     * parametersRequired="true"
     * visitorDataType="uk.org.cse.nhm.hom.emf.technologies.IEnergyCalculatorVisitor"
     * visitorRequired="true" proportionRequired="true" priorityRequired="true"
     * @generated
     */
    void acceptFromHeating(IConstants constants, IEnergyCalculatorParameters parameters, IEnergyCalculatorVisitor visitor, double proportion, int priority);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * The responsiveness value returned from this method is used by a connected
     * heating system to determine the background temperature in the house
     * <!-- end-model-doc -->
     *
     * @model required="true"
     * parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IConstants"
     * parametersRequired="true" controlsMany="true" controlsOrdered="false"
     * emitterTypeRequired="true"
     * @generated
     */
    double getResponsiveness(IConstants parameters, EList<HeatingSystemControlType> controls, EmitterType emitterType);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * A connected central heating system will use this method to adjust the
     * house's demand temperature
     * <!-- end-model-doc -->
     *
     * @model required="true"
     * parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IInternalParameters"
     * parametersRequired="true" controlTypesMany="true"
     * controlTypesOrdered="false"
     * @generated
     */
    double getDemandTemperatureAdjustment(IInternalParameters parameters, EList<HeatingSystemControlType> controlTypes);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * If connected to a central hot water system, the central hot water system
     * will invoke this method to ask the heat source to provide a certain
     * proportion of the hot water demand.
     * <!-- end-model-doc -->
     *
     * @model required="true"
     * parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IInternalParameters"
     * parametersRequired="true"
     * stateDataType="uk.org.cse.nhm.hom.emf.technologies.IEnergyState"
     * stateRequired="true" storeIsPrimaryRequired="true"
     * primaryCorrectionFactorRequired="true"
     * distributionLossFactorRequired="true" proportionRequired="true"
     * @generated
     */
    double generateHotWaterAndPrimaryGains(IInternalParameters parameters, IEnergyState state, IWaterTank store, boolean storeIsPrimary, double primaryCorrectionFactor, double distributionLossFactor, double proportion);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * If connected to central hot water, this method will be invoked by the
     * central hot water system to account for the distribution losses in that
     * system; distribution losses are allocated to water heaters <em>pro
     * rata</em> the amount of hot water generated
     * <!-- end-model-doc -->
     *
     * @model
     * parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IInternalParameters"
     * parametersRequired="true"
     * stateDataType="uk.org.cse.nhm.hom.emf.technologies.IEnergyState"
     * stateRequired="true" storeIsPrimaryRequired="true"
     * systemLossesRequired="true"
     * @generated
     */
    void generateHotWaterSystemGains(IInternalParameters parameters, IEnergyState state, IWaterTank store, boolean storeIsPrimary, double systemLosses);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @model required="true"
     * parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IInternalParameters"
     * parametersRequired="true" storeRequired="true"
     * storeInPrimaryCircuitRequired="true"
     *
     * @generated
     */
    double getStorageTemperatureFactor(IInternalParameters parameters, IWaterTank store, boolean storeInPrimaryCircuit);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Some heat sources contain a storage tank of some kind (like CPSUs) - the
     * hot water system will invoke this method to get the tank losses
     * associated with this tank.
     * <!-- end-model-doc -->
     *
     * @model required="true"
     * parametersDataType="uk.org.cse.nhm.hom.emf.technologies.IInternalParameters"
     * parametersRequired="true"
     * @generated
     */
    double getContainedTankLosses(IInternalParameters parameters);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @model kind="operation" required="true"
     *
     * @generated
     */
    boolean isCommunityHeating();

} // IHeatSource
