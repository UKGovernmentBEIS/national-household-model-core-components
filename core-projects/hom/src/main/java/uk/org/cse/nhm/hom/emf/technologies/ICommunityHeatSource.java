/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import uk.org.cse.nhm.hom.emf.util.Efficiency;


/**
 * <!-- begin-user-doc -->
 * Represents the heat source for a community heating system. This is distinct from a boiler.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource#isChargingUsageBased <em>Charging Usage Based</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource#getHeatEfficiency <em>Heat Efficiency</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCommunityHeatSource()
 * @model
 * @generated
 */
public interface ICommunityHeatSource extends IHeatSource {

	/**
	 * Returns the value of the '<em><b>Charging Usage Based</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Charging Usage Based</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Charging Usage Based</em>' attribute.
	 * @see #setChargingUsageBased(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCommunityHeatSource_ChargingUsageBased()
	 * @model required="true"
	 * @generated
	 */
	boolean isChargingUsageBased();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource#isChargingUsageBased <em>Charging Usage Based</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Charging Usage Based</em>' attribute.
	 * @see #isChargingUsageBased()
	 * @generated
	 */
	void setChargingUsageBased(boolean value);

	/**
	 * Returns the value of the '<em><b>Heat Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Heat Efficiency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Heat Efficiency</em>' attribute.
	 * @see #setHeatEfficiency(Efficiency)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCommunityHeatSource_HeatEfficiency()
	 * @model dataType="uk.org.cse.nhm.hom.emf.technologies.Efficiency" required="true"
	 * @generated
	 */
	Efficiency getHeatEfficiency();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource#getHeatEfficiency <em>Heat Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Heat Efficiency</em>' attribute.
	 * @see #getHeatEfficiency()
	 * @generated
	 */
	void setHeatEfficiency(Efficiency value);
} // ICommunityHeatSource
