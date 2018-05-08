/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Heat Pump Warm Air
 * System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This warm air system is distinct from the plain HeatPump model object,
 * because the HeatPump represents a to-water system, which can be plumbed in to
 * central hot water or central heat, whereas this is describing a to-air heat
 * pump which is quite different.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem#getSourceType
 * <em>Source Type</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem#isAuxiliaryPresent
 * <em>Auxiliary Present</em>}</li>
 * </ul>
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatPumpWarmAirSystem()
 * @model
 * @generated
 */
public interface IHeatPumpWarmAirSystem extends IWarmAirSystem {

    /**
     * Returns the value of the '<em><b>Source Type</b></em>' attribute. The
     * literals are from the enumeration
     * {@link uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Type</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source Type</em>' attribute.
     * @see uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType
     * @see #setSourceType(HeatPumpSourceType)
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatPumpWarmAirSystem_SourceType()
     * @model required="true"
     * @generated
     */
    HeatPumpSourceType getSourceType();

    /**
     * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem#getSourceType
     * <em>Source Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Source Type</em>' attribute.
     * @see uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType
     * @see #getSourceType()
     * @generated
     */
    void setSourceType(HeatPumpSourceType value);

    /**
     * Returns the value of the '<em><b>Auxiliary Present</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Auxiliary Present</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Auxiliary Present</em>' attribute.
     * @see #setAuxiliaryPresent(boolean)
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getHeatPumpWarmAirSystem_AuxiliaryPresent()
     * @model required="true"
     * @generated
     */
    boolean isAuxiliaryPresent();

    /**
     * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem#isAuxiliaryPresent
     * <em>Auxiliary Present</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Auxiliary Present</em>' attribute.
     * @see #isAuxiliaryPresent()
     * @generated
     */
    void setAuxiliaryPresent(boolean value);

} // IHeatPumpWarmAirSystem
