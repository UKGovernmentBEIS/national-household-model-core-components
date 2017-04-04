/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import uk.org.cse.nhm.hom.emf.util.Efficiency;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Community CHP</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP#getElectricalEfficiency <em>Electrical Efficiency</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCommunityCHP()
 * @model
 * @generated
 */
public interface ICommunityCHP extends ICommunityHeatSource {

	/**
	 * Returns the value of the '<em><b>Electrical Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Electrical Efficiency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Electrical Efficiency</em>' attribute.
	 * @see #setElectricalEfficiency(Efficiency)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCommunityCHP_ElectricalEfficiency()
	 * @model dataType="uk.org.cse.nhm.hom.emf.technologies.Efficiency" required="true"
	 * @generated
	 */
	Efficiency getElectricalEfficiency();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP#getElectricalEfficiency <em>Electrical Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Electrical Efficiency</em>' attribute.
	 * @see #getElectricalEfficiency()
	 * @generated
	 */
	void setElectricalEfficiency(Efficiency value);
} // ICommunityCHP
