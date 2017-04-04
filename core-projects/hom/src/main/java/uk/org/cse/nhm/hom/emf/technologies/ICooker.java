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
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobBaseLoad <em>Hob Base Load</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobOccupancyFactor <em>Hob Occupancy Factor</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobFuelType <em>Hob Fuel Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenBaseLoad <em>Oven Base Load</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenOccupancyFactor <em>Oven Occupancy Factor</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenFuelType <em>Oven Fuel Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getGainsFactor <em>Gains Factor</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker()
 * @model
 * @generated
 */
public interface ICooker extends IVisitorAccepter {
	
	/*
	BEISDOC
	NAME: Gas hob base load
	DESCRIPTION: The base amount of power consumed by a gas cooking hob.
	TYPE: value
	UNIT: W
	BREDEM: Table 5 ("Normal size cooker: electric / gas" EC2A)
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24))
	ID: gas-hob-base-load
	CODSIEB
	*/
	final double GAS_HOB_BASE_LOAD = 27.49;
	
	/*
	BEISDOC
	NAME: Gas hob occupancy factor
	DESCRIPTION: The amount of extra power consumed by a gas cooking hob per occupant of the dwelling
	TYPE: value
	UNIT: W / person
	BREDEM: Table 5 ("Normal size cooker: electric / gas" EC2B)
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24)) 
	ID: gas-hob-occupancy-factor
	CODSIEB
	*/
	final double GAS_HOB_OCCUPANCY_FACTOR = 5.48;
	
	/*
	BEISDOC
	NAME: Electric hob base load
	DESCRIPTION: The base amount of power consumed by an electric cooking hob.
	TYPE: value
	UNIT: W
	BREDEM: Table 5 (Subtract "Normal size cooker: electric / gas" from "Normal size cooker: electric" for column EC1A)
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24)) 
	ID: electric-hob-base-load
	CODSIEB
	*/
	final double ELECTRIC_HOB_BASE_LOAD = 15.63;
	
	/*
	BEISDOC
	NAME: Electric hob occupancy factor
	DESCRIPTION: The amount of extra power consumed by an electric cooking hob per occupant of the dwelling.
	TYPE: value
	UNIT: W / person
	BREDEM: Table 5 (Subtract "Normal size cooker: electric / gas" from "Normal size cooker: electric" for column EC1B)
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24)) 
	ID: electric-hob-occupancy-factor
	CODSIEB
	*/
	final double ELECTRIC_HOB_OCCUPANCY_FACTOR = 3.08;
	
	/*
	BEISDOC
	NAME: Electric oven base load
	DESCRIPTION: The base amount of power consumed by an electric oven.
	TYPE: value
	UNIT: W
	BREDEM: Table 5 ("Normal size cooker: electric / gas" EC1A)
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24))
	ID: electric-oven-base-load
	CODSIEB
	*/
	final double ELECTRIC_OVEN_BASE_LOAD = 15.74;
	
	/*
	BEISDOC
	NAME: Electric oven occupancy factor
	DESCRIPTION: The amount of extra power consumed by an electric oven per occupant of the dwelling.
	TYPE: value
	UNIT: W / person
	BREDEM: Table 5 ("Normal size cooker: electric / gas" EC1B)
	CONVERSION: From kWh/year to W (1000 / (365.25 * 24))
	ID: electric-oven-occupancy-factor
	CODSIEB
	*/
	final double ELECTRIC_OVEN_OCCUPANCY_FACTOR = 3.19;

	/*
	BEISDOC
	NAME: Electric cooking gains factor
	DESCRIPTION: The amount of heat gains emitted per Joule of electricity consumed by a dedicated electric oven and hob.
	TYPE: value
	UNIT: Dimensionless
	BREDEM: Table 25
	ID: electric-cooking-gains-factor
	CODSIEB
	*/
	final double ELECTRIC_GAINS_FACTOR = 0.9;
	
	/*
	BEISDOC
	NAME: Gas and Electric cooking gains factor
	DESCRIPTION: The amount of heat gains emitted per Joule of gas consumed by a combined electric oven and gas hob.
	TYPE: value
	UNIT: Dimensionless
	BREDEM: Table 25
	ID: gas-and-electric-cooking-gains-factor
	CODSIEB
	*/
	final double GAS_AND_ELECTRIC_GAINS_FACTOR = 0.825;
	
	/*
	BEISDOC
	NAME: SAP Base Cooking Gains
	DESCRIPTION: The base amount of heat gains emitted by cooking under SAP 2012.
	TYPE: value
	UNIT: W
	SAP: Table 5
	ID: sap-base-cooking-gains
	CODSIEB
	*/
	final double SAP_BASE_GAINS = 35;
	
	/*
	BEISDOC
	NAME: SAP Gains Occupancy Factor
	DESCRIPTION: The amount of extra heat gains per person emitted by cooking under SAP 2012.
	TYPE: value
	UNIT: W / person
	SAP: Table 5
	ID: sap-cooking-gains-occupancy-factor
	CODSIEB
	*/
	final double SAP_GAINS_OCCUPANCY_FACTOR = 7;
	
	/**
	 * Returns the value of the '<em><b>Hob Base Load</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hob Base Load</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hob Base Load</em>' attribute.
	 * @see #setHobBaseLoad(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_HobBaseLoad()
	 * @model required="true"
	 * @generated
	 */
	double getHobBaseLoad();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobBaseLoad <em>Hob Base Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hob Base Load</em>' attribute.
	 * @see #getHobBaseLoad()
	 * @generated
	 */
	void setHobBaseLoad(double value);

	/**
	 * Returns the value of the '<em><b>Hob Occupancy Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hob Occupancy Factor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hob Occupancy Factor</em>' attribute.
	 * @see #setHobOccupancyFactor(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_HobOccupancyFactor()
	 * @model required="true"
	 * @generated
	 */
	double getHobOccupancyFactor();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobOccupancyFactor <em>Hob Occupancy Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hob Occupancy Factor</em>' attribute.
	 * @see #getHobOccupancyFactor()
	 * @generated
	 */
	void setHobOccupancyFactor(double value);

	/**
	 * Returns the value of the '<em><b>Hob Fuel Type</b></em>' attribute.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.FuelType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hob Fuel Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hob Fuel Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #setHobFuelType(FuelType)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_HobFuelType()
	 * @model required="true"
	 * @generated
	 */
	FuelType getHobFuelType();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobFuelType <em>Hob Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hob Fuel Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #getHobFuelType()
	 * @generated
	 */
	void setHobFuelType(FuelType value);

	/**
	 * Returns the value of the '<em><b>Oven Base Load</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Oven Base Load</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Oven Base Load</em>' attribute.
	 * @see #setOvenBaseLoad(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_OvenBaseLoad()
	 * @model required="true"
	 * @generated
	 */
	double getOvenBaseLoad();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenBaseLoad <em>Oven Base Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Oven Base Load</em>' attribute.
	 * @see #getOvenBaseLoad()
	 * @generated
	 */
	void setOvenBaseLoad(double value);

	/**
	 * Returns the value of the '<em><b>Oven Occupancy Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Oven Occupancy Factor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Oven Occupancy Factor</em>' attribute.
	 * @see #setOvenOccupancyFactor(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_OvenOccupancyFactor()
	 * @model required="true"
	 * @generated
	 */
	double getOvenOccupancyFactor();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenOccupancyFactor <em>Oven Occupancy Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Oven Occupancy Factor</em>' attribute.
	 * @see #getOvenOccupancyFactor()
	 * @generated
	 */
	void setOvenOccupancyFactor(double value);

	/**
	 * Returns the value of the '<em><b>Oven Fuel Type</b></em>' attribute.
	 * The literals are from the enumeration {@link uk.org.cse.nhm.hom.emf.technologies.FuelType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Oven Fuel Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Oven Fuel Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #setOvenFuelType(FuelType)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getCooker_OvenFuelType()
	 * @model required="true"
	 * @generated
	 */
	FuelType getOvenFuelType();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenFuelType <em>Oven Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Oven Fuel Type</em>' attribute.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see #getOvenFuelType()
	 * @generated
	 */
	void setOvenFuelType(FuelType value);

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
