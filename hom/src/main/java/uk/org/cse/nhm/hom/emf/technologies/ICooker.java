/**
 */
package uk.org.cse.nhm.hom.emf.technologies;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cooker</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#isHob <em>Hob</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#isOven <em>Oven</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getFuelType <em>Fuel Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getBaseLoad <em>Base Load</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getOccupancyFactor <em>Occupancy Factor</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getGainsFactor <em>Gains Factor</em>}</li>
 * </ul>
 * </p>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker()
 * @model
 * @generated
 */
public interface ICooker extends IVisitorAccepter {
	
	final double GAS_BASE_LOAD = 54.986;
	final double GAS_OCCUPANCY_FACTOR = 10.99;
	final double GAS_GAINS_FACTOR = 0.24917;
	final double GAS_TO_ELECTRIC_RATIO = 0.572509;
	final double ELECTRIC_BASE_LOAD = GAS_BASE_LOAD * GAS_TO_ELECTRIC_RATIO;
	final double ELECTRIC_OCCUPANCY_FACTOR = GAS_OCCUPANCY_FACTOR * GAS_TO_ELECTRIC_RATIO;
	final double ELECTRIC_GAINS_FACTOR = 0.1;

	/**
	 * Returns the value of the '<em><b>Hob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hob</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hob</em>' attribute.
	 * @see #setHob(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_Hob()
	 * @model required="true"
	 * @generated
	 */
	boolean isHob();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#isHob <em>Hob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hob</em>' attribute.
	 * @see #isHob()
	 * @generated
	 */
	void setHob(boolean value);

	/**
	 * Returns the value of the '<em><b>Oven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Oven</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Oven</em>' attribute.
	 * @see #setOven(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_Oven()
	 * @model required="true"
	 * @generated
	 */
	boolean isOven();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#isOven <em>Oven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Oven</em>' attribute.
	 * @see #isOven()
	 * @generated
	 */
	void setOven(boolean value);

	/**
	 * Returns the value of the '<em><b>Fuel Type</b></em>' attribute.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.FuelType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fuel Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fuel Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #setFuelType(FuelType)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_FuelType()
	 * @model required="true"
	 * @generated
	 */
	FuelType getFuelType();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getFuelType <em>Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fuel Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #getFuelType()
	 * @generated
	 */
	void setFuelType(FuelType value);

	/**
	 * Returns the value of the '<em><b>Base Load</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Load</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Load</em>' attribute.
	 * @see #setBaseLoad(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_BaseLoad()
	 * @model required="true"
	 * @generated
	 */
	double getBaseLoad();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getBaseLoad <em>Base Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Load</em>' attribute.
	 * @see #getBaseLoad()
	 * @generated
	 */
	void setBaseLoad(double value);

	/**
	 * Returns the value of the '<em><b>Occupancy Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Occupancy Factor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Occupancy Factor</em>' attribute.
	 * @see #setOccupancyFactor(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_OccupancyFactor()
	 * @model required="true"
	 * @generated
	 */
	double getOccupancyFactor();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getOccupancyFactor <em>Occupancy Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Occupancy Factor</em>' attribute.
	 * @see #getOccupancyFactor()
	 * @generated
	 */
	void setOccupancyFactor(double value);

	/**
	 * Returns the value of the '<em><b>Gains Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gains Factor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gains Factor</em>' attribute.
	 * @see #setGainsFactor(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_GainsFactor()
	 * @model required="true"
	 * @generated
	 */
	double getGainsFactor();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getGainsFactor <em>Gains Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gains Factor</em>' attribute.
	 * @see #getGainsFactor()
	 * @generated
	 */
	void setGainsFactor(double value);

} // ICooker
