/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import uk.org.cse.nhm.energycalculator.api.IConstants;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Primary Space Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getPrimarySpaceHeater()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IPrimarySpaceHeater extends ISpaceHeater {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model constantsDataType="uk.org.cse.nhm.hom.emf.technologies.IConstants"
	 * @generated
	 */
	double getDerivedResponsiveness(IConstants constants);
} // IPrimarySpaceHeater
