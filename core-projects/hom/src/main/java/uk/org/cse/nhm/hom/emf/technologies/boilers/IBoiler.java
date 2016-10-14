/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers;

import uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue;
import uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boiler</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Represents a "standard" boiler (i.e. not a combi or a CPSU).
 * 
 * The implementation class contains some slightly tangled logic, to avoid having to use multiple inheritance or
 * re-creation of logic (due to cross-cutting concerns). The main tangles are:
 * 
 *  - having summer & winter efficiency, when some boilers have a single efficiency value
 *  - having fuel type, which allows for electric systems that usually work slightly differently, and where efficiency is not relevant
 *  - having pump in heated space, which only makes sense for oil as a fuel type
 *  - having condensing, which is only relevant for gas & oil combusion
 *  - having weather compensation, which is only relevant for condensing boilers.
 * 
 * Although these are a bit messy, it is easier than having Boiler, ElectricBoiler, GasBoiler, ElectricCPSU, ElectricCombi, ElectricStorageCombi, GasStorageCombi and so on and so on.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#getSummerEfficiency <em>Summer Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#getWinterEfficiency <em>Winter Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isCondensing <em>Condensing</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isWeatherCompensated <em>Weather Compensated</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#getBasicResponsiveness <em>Basic Responsiveness</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isPumpInHeatedSpace <em>Pump In Heated Space</em>}</li>
 * </ul>
 *
 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getBoiler()
 * @model
 * @generated
 */
public interface IBoiler extends IIndividualHeatSource, IVisitorAccepter, IFuelAndFlue {
	/**
	 * Returns the value of the '<em><b>Summer Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Summer Efficiency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Summer Efficiency</em>' attribute.
	 * @see #setSummerEfficiency(Efficiency)
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getBoiler_SummerEfficiency()
	 * @model dataType="uk.org.cse.nhm.hom.emf.technologies.Efficiency" required="true"
	 * @generated
	 */
	Efficiency getSummerEfficiency();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#getSummerEfficiency <em>Summer Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Summer Efficiency</em>' attribute.
	 * @see #getSummerEfficiency()
	 * @generated
	 */
	void setSummerEfficiency(Efficiency value);

	/**
	 * Returns the value of the '<em><b>Winter Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Winter Efficiency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Winter Efficiency</em>' attribute.
	 * @see #setWinterEfficiency(Efficiency)
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getBoiler_WinterEfficiency()
	 * @model dataType="uk.org.cse.nhm.hom.emf.technologies.Efficiency" required="true"
	 * @generated
	 */
	Efficiency getWinterEfficiency();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#getWinterEfficiency <em>Winter Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Winter Efficiency</em>' attribute.
	 * @see #getWinterEfficiency()
	 * @generated
	 */
	void setWinterEfficiency(Efficiency value);

	/**
	 * Returns the value of the '<em><b>Condensing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condensing</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Only relevant for gas or oil fuels. Affects whether weatherCompensated matters
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Condensing</em>' attribute.
	 * @see #setCondensing(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getBoiler_Condensing()
	 * @model required="true"
	 * @generated
	 */
	boolean isCondensing();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isCondensing <em>Condensing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condensing</em>' attribute.
	 * @see #isCondensing()
	 * @generated
	 */
	void setCondensing(boolean value);

	/**
	 * Returns the value of the '<em><b>Weather Compensated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Weather Compensated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Only relevant for condensing boilers - if there is a weather compensator, this affects the efficiency according to table 4c
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Weather Compensated</em>' attribute.
	 * @see #setWeatherCompensated(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getBoiler_WeatherCompensated()
	 * @model required="true"
	 * @generated
	 */
	boolean isWeatherCompensated();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isWeatherCompensated <em>Weather Compensated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Weather Compensated</em>' attribute.
	 * @see #isWeatherCompensated()
	 * @generated
	 */
	void setWeatherCompensated(boolean value);

	/**
	 * Returns the value of the '<em><b>Basic Responsiveness</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Basic Responsiveness</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * For most boilers, this defines the responsiveness; various responsiveness tweak factors are applied depending on things in the various SAP tables 4a,b,c, etc.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Basic Responsiveness</em>' attribute.
	 * @see #setBasicResponsiveness(double)
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getBoiler_BasicResponsiveness()
	 * @model default="1" required="true"
	 * @generated
	 */
	double getBasicResponsiveness();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#getBasicResponsiveness <em>Basic Responsiveness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Basic Responsiveness</em>' attribute.
	 * @see #getBasicResponsiveness()
	 * @generated
	 */
	void setBasicResponsiveness(double value);

	/**
	 * Returns the value of the '<em><b>Pump In Heated Space</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pump In Heated Space</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If fuel == oil, this determines whether the oil circulating pump produces internal heat gains
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Pump In Heated Space</em>' attribute.
	 * @see #setPumpInHeatedSpace(boolean)
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#getBoiler_PumpInHeatedSpace()
	 * @model required="true"
	 * @generated
	 */
	boolean isPumpInHeatedSpace();

	/**
	 * Sets the value of the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isPumpInHeatedSpace <em>Pump In Heated Space</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pump In Heated Space</em>' attribute.
	 * @see #isPumpInHeatedSpace()
	 * @generated
	 */
	void setPumpInHeatedSpace(boolean value);

} // IBoiler
