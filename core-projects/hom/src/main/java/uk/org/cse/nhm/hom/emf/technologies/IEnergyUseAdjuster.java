/**
 */
package uk.org.cse.nhm.hom.emf.technologies;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Energy Use Adjuster</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IEnergyUseAdjuster#getConstantTerm <em>Constant Term</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IEnergyUseAdjuster#getLinearTerm <em>Linear Term</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.IEnergyUseAdjuster#getAdjustmentType <em>Adjustment Type</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getEnergyUseAdjuster()
 * @model
 * @generated
 */
public interface IEnergyUseAdjuster extends INamed, IVisitorAccepter {
	/**
	 * Returns the value of the '<em><b>Constant Term</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constant Term</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constant Term</em>' attribute.
	 * @see #setConstantTerm(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getEnergyUseAdjuster_ConstantTerm()
	 * @model
	 * @generated
	 */
	double getConstantTerm();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IEnergyUseAdjuster#getConstantTerm <em>Constant Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constant Term</em>' attribute.
	 * @see #getConstantTerm()
	 * @generated
	 */
	void setConstantTerm(double value);

	/**
	 * Returns the value of the '<em><b>Linear Term</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Linear Term</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Linear Term</em>' attribute.
	 * @see #setLinearTerm(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getEnergyUseAdjuster_LinearTerm()
	 * @model
	 * @generated
	 */
	double getLinearTerm();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IEnergyUseAdjuster#getLinearTerm <em>Linear Term</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Linear Term</em>' attribute.
	 * @see #getLinearTerm()
	 * @generated
	 */
	void setLinearTerm(double value);

	/**
	 * Returns the value of the '<em><b>Adjustment Type</b></em>' attribute.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.AdjusterType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adjustment Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adjustment Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.AdjusterType
	 * @see #setAdjustmentType(AdjusterType)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getEnergyUseAdjuster_AdjustmentType()
	 * @model
	 * @generated
	 */
	AdjusterType getAdjustmentType();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.IEnergyUseAdjuster#getAdjustmentType <em>Adjustment Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adjustment Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.AdjusterType
	 * @see #getAdjustmentType()
	 * @generated
	 */
	void setAdjustmentType(AdjusterType value);

} // IEnergyUseAdjuster
