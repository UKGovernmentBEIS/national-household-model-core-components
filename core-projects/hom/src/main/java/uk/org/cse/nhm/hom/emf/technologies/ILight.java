/** */
package uk.org.cse.nhm.hom.emf.technologies;

import uk.org.cse.nhm.energycalculator.api.types.LightType;

/**
 *
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Light</b></em>'.
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Lights are things in a house which satisfy the lighting demand produced by the algorithm
 * <!-- end-model-doc -->
 *
 * <p>The following features are supported:
 *
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ILight#getEfficiency <em>Efficiency</em>}
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ILight#getProportion <em>Proportion</em>}
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ILight#getType <em>Type</em>}
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getLight()
 * @model
 * @generated
 */
public interface ILight extends INamed, IVisitorAccepter {

  /*
  BEISDOC
  NAME: Incandescent Energy Consumption
  DESCRIPTION: The number of watts of electricity an incandescent bulb must consume to emit a watt of light.
  TYPE: value
  UNIT: Dimensionless
  SAP: (L1)
         SAP_COMPLIANT: Yes
  BREDEM: 1B
         BREDEM_COMPLIANT: Yes
  CONVERSION: From kWh/year to W (1000 / (365.25 * 24))
         NOTES: Derived from SAP formulas, also supplied from Roger Lampert as corresponding to 11.2 lumens per watt
  ID: incandescent-energy-consumption
  CODSIEB
  */
  public static final double INCANDESCENT_EFFICIENCY = 6.8139; // watts

  /*
  BEISDOC
  NAME: CFL Energy Consumption
  DESCRIPTION: The number of watts of electricity a CFL bulb must consume to emit a watt of light.
  TYPE: value
  UNIT: Dimensionless
  SAP: (L2)
         SAP_COMPLIANT: Yes
  BREDEM: 1C
         BREDEM_COMPLIANT: Yes
  DEPS: incandescent-energy-consumption
  NOTES: Worked out that this was half the incandescent efficiency by working backwards through the formula.
  ID: cfl-energy-consumption
  CODSIEB
  */
  public static final double CFL_EFFICIENCY = INCANDESCENT_EFFICIENCY / 2.0;

  public static final double CFL_SPLIT_POINT = (CFL_EFFICIENCY + INCANDESCENT_EFFICIENCY) / 2.0;

  /**
   * Returns the value of the '<em><b>Proportion</b></em>' attribute.
   * <!-- begin-user-doc -->
   *
   * <p>If the meaning of the '<em>Proportion</em>' attribute isn't clear, there really should be
   * more of a description here...
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * The fraction of total light demand that this light will emit
   * <!-- end-model-doc -->
   *
   * @return the value of the '<em>Proportion</em>' attribute.
   * @see #setProportion(double)
   * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getLight_Proportion()
   * @model required="true"
   * @generated
   */
  double getProportion();

  /**
   * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ILight#getProportion
   * <em>Proportion</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   *
   * @param value the new value of the '<em>Proportion</em>' attribute.
   * @see #getProportion()
   * @generated
   */
  void setProportion(double value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   *
   * <p>If the meaning of the '<em>Type</em>' attribute isn't clear, there really should be more of
   * a description here...
   * <!-- end-user-doc -->
   *
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(LightType)
   * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getLight_Type()
   * @model dataType="uk.org.cse.nhm.hom.emf.technologies.LightType" required="true"
   * @generated
   */
  LightType getType();

  /**
   * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ILight#getType
   * <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   *
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #getType()
   * @generated
   */
  void setType(LightType value);
} // ILight
