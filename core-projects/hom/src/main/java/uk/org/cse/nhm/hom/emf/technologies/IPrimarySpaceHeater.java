/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Primary Space Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see
 * uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getPrimarySpaceHeater()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IPrimarySpaceHeater extends ISpaceHeater {
    FuelType getFuel();
    public List<HeatingSystemControlType> getControls();
} // IPrimarySpaceHeater
