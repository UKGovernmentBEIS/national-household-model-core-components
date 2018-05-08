/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Central Heating
 * System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getHeatSource
 * <em>Heat Source</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getControls
 * <em>Controls</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getEmitterType
 * <em>Emitter Type</em>}</li>
 * </ul>
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralHeatingSystem()
 * @model
 * @generated
 */
public interface ICentralHeatingSystem extends IPrimarySpaceHeater, IVisitorAccepter {

    /**
     * Returns the value of the '<em><b>Heat Source</b></em>' reference. It is
     * bidirectional and its opposite is '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getSpaceHeater
     * <em>Space Heater</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Heat Source</em>' reference isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Heat Source</em>' reference.
     * @see #setHeatSource(IHeatSource)
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralHeatingSystem_HeatSource()
     * @see uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getSpaceHeater
     * @model opposite="spaceHeater" required="true"
     * @generated
     */
    IHeatSource getHeatSource();

    /**
     * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getHeatSource
     * <em>Heat Source</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Heat Source</em>' reference.
     * @see #getHeatSource()
     * @generated
     */
    void setHeatSource(IHeatSource value);

    /**
     * Returns the value of the '<em><b>Controls</b></em>' attribute list. The
     * list contents are of type
     * {@link uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType}. The
     * literals are from the enumeration
     * {@link uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Controls</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Controls</em>' attribute list.
     * @see uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralHeatingSystem_Controls()
     * @model ordered="false"
     * @generated
     */
    EList<HeatingSystemControlType> getControls();

    /**
     * Returns the value of the '<em><b>Emitter Type</b></em>' attribute. The
     * literals are from the enumeration
     * {@link uk.org.cse.nhm.hom.emf.technologies.EmitterType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Emitter Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Emitter Type</em>' attribute.
     * @see uk.org.cse.nhm.hom.emf.technologies.EmitterType
     * @see #setEmitterType(EmitterType)
     * @see
     * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCentralHeatingSystem_EmitterType()
     * @model required="true"
     * @generated
     */
    EmitterType getEmitterType();

    /**
     * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getEmitterType
     * <em>Emitter Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the new value of the '<em>Emitter Type</em>' attribute.
     * @see uk.org.cse.nhm.hom.emf.technologies.EmitterType
     * @see #getEmitterType()
     * @generated
     */
    void setEmitterType(EmitterType value);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @model kind="operation" required="true"
     *
     * @generated
     */
    boolean isThermostaticallyControlled();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * <!-- begin-model-doc -->
     * Returns true if the proportion should be ignored
     * <!-- end-model-doc -->
     *
     * @model kind="operation" required="true"
     * annotation="http://www.eclipse.org/emf/2002/GenModel body='return
     * getHeatSource() == null;'"
     * @generated
     */
    boolean isBroken();

} // ICentralHeatingSystem
