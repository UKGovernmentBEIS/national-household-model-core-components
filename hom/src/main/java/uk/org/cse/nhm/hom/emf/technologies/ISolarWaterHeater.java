/**
 */
package uk.org.cse.nhm.hom.emf.technologies;



/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Solar Water Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getPitch <em>Pitch</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getOrientation <em>Orientation</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getArea <em>Area</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getUsefulAreaRatio <em>Useful Area Ratio</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getZeroLossEfficiency <em>Zero Loss Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getLinearHeatLossCoefficient <em>Linear Heat Loss Coefficient</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getPreHeatTankVolume <em>Pre Heat Tank Volume</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#isPumpPhotovolatic <em>Pump Photovolatic</em>}</li>
 * </ul>
 * </p>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarWaterHeater()
 * @model
 * @generated
 */
public interface ISolarWaterHeater extends ICentralWaterHeater, IVisitorAccepter, IOperationalCost {

	/**
	 * Returns the value of the '<em><b>Pitch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pitch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pitch</em>' attribute.
	 * @see #setPitch(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarWaterHeater_Pitch()
	 * @model required="true"
	 * @generated
	 */
	double getPitch();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getPitch <em>Pitch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pitch</em>' attribute.
	 * @see #getPitch()
	 * @generated
	 */
	void setPitch(double value);

	/**
	 * Returns the value of the '<em><b>Orientation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orientation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orientation</em>' attribute.
	 * @see #setOrientation(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarWaterHeater_Orientation()
	 * @model
	 * @generated
	 */
	double getOrientation();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getOrientation <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orientation</em>' attribute.
	 * @see #getOrientation()
	 * @generated
	 */
	void setOrientation(double value);

	/**
	 * Returns the value of the '<em><b>Area</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Area</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Area</em>' attribute.
	 * @see #setArea(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarWaterHeater_Area()
	 * @model required="true"
	 * @generated
	 */
	double getArea();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getArea <em>Area</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Area</em>' attribute.
	 * @see #getArea()
	 * @generated
	 */
	void setArea(double value);

	/**
	 * Returns the value of the '<em><b>Useful Area Ratio</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Useful Area Ratio</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Useful Area Ratio</em>' attribute.
	 * @see #setUsefulAreaRatio(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarWaterHeater_UsefulAreaRatio()
	 * @model required="true"
	 * @generated
	 */
	double getUsefulAreaRatio();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getUsefulAreaRatio <em>Useful Area Ratio</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Useful Area Ratio</em>' attribute.
	 * @see #getUsefulAreaRatio()
	 * @generated
	 */
	void setUsefulAreaRatio(double value);

	/**
	 * Returns the value of the '<em><b>Zero Loss Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Zero Loss Efficiency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Zero Loss Efficiency</em>' attribute.
	 * @see #setZeroLossEfficiency(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarWaterHeater_ZeroLossEfficiency()
	 * @model required="true"
	 * @generated
	 */
	double getZeroLossEfficiency();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getZeroLossEfficiency <em>Zero Loss Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Zero Loss Efficiency</em>' attribute.
	 * @see #getZeroLossEfficiency()
	 * @generated
	 */
	void setZeroLossEfficiency(double value);

	/**
	 * Returns the value of the '<em><b>Linear Heat Loss Coefficient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Linear Heat Loss Coefficient</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Linear Heat Loss Coefficient</em>' attribute.
	 * @see #setLinearHeatLossCoefficient(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarWaterHeater_LinearHeatLossCoefficient()
	 * @model required="true"
	 * @generated
	 */
	double getLinearHeatLossCoefficient();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getLinearHeatLossCoefficient <em>Linear Heat Loss Coefficient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Linear Heat Loss Coefficient</em>' attribute.
	 * @see #getLinearHeatLossCoefficient()
	 * @generated
	 */
	void setLinearHeatLossCoefficient(double value);

	/**
	 * Returns the value of the '<em><b>Pre Heat Tank Volume</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Heat Tank Volume</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Heat Tank Volume</em>' attribute.
	 * @see #setPreHeatTankVolume(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarWaterHeater_PreHeatTankVolume()
	 * @model required="true"
	 * @generated
	 */
	double getPreHeatTankVolume();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getPreHeatTankVolume <em>Pre Heat Tank Volume</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Heat Tank Volume</em>' attribute.
	 * @see #getPreHeatTankVolume()
	 * @generated
	 */
	void setPreHeatTankVolume(double value);

	/**
	 * Returns the value of the '<em><b>Pump Photovolatic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pump Photovolatic</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pump Photovolatic</em>' attribute.
	 * @see #setPumpPhotovolatic(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#getSolarWaterHeater_PumpPhotovolatic()
	 * @model required="true"
	 * @generated
	 */
	boolean isPumpPhotovolatic();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#isPumpPhotovolatic <em>Pump Photovolatic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pump Photovolatic</em>' attribute.
	 * @see #isPumpPhotovolatic()
	 * @generated
	 */
	void setPumpPhotovolatic(boolean value);
} // ISolarWaterHeater
