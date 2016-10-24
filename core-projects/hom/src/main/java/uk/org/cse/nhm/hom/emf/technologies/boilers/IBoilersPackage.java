/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory
 * @model kind="package"
 * @generated
 */
public interface IBoilersPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "boilers";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.cse.org.uk/nhm/schema/emf/technology/boilers/1/";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "boilers";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IBoilersPackage eINSTANCE = uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl.init();

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl <em>Boiler</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getBoiler()
	 * @generated
	 */
	int BOILER = 0;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER__FUEL = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE__FUEL;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER__FLUE_TYPE = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE__FLUE_TYPE;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER__ANNUAL_OPERATIONAL_COST = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE__ANNUAL_OPERATIONAL_COST;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER__INSTALLATION_YEAR = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE__INSTALLATION_YEAR;

	/**
	 * The feature id for the '<em><b>Water Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER__WATER_HEATER = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE__WATER_HEATER;

	/**
	 * The feature id for the '<em><b>Space Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER__SPACE_HEATER = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE__SPACE_HEATER;

	/**
	 * The feature id for the '<em><b>Summer Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER__SUMMER_EFFICIENCY = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Winter Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER__WINTER_EFFICIENCY = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Condensing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER__CONDENSING = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Weather Compensated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER__WEATHER_COMPENSATED = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Pump In Heated Space</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER__PUMP_IN_HEATED_SPACE = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Boiler</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOILER_FEATURE_COUNT = ITechnologiesPackage.INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.CPSUImpl <em>CPSU</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.CPSUImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getCPSU()
	 * @generated
	 */
	int CPSU = 1;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__FUEL = BOILER__FUEL;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__FLUE_TYPE = BOILER__FLUE_TYPE;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__ANNUAL_OPERATIONAL_COST = BOILER__ANNUAL_OPERATIONAL_COST;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__INSTALLATION_YEAR = BOILER__INSTALLATION_YEAR;

	/**
	 * The feature id for the '<em><b>Water Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__WATER_HEATER = BOILER__WATER_HEATER;

	/**
	 * The feature id for the '<em><b>Space Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__SPACE_HEATER = BOILER__SPACE_HEATER;

	/**
	 * The feature id for the '<em><b>Summer Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__SUMMER_EFFICIENCY = BOILER__SUMMER_EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Winter Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__WINTER_EFFICIENCY = BOILER__WINTER_EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Condensing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__CONDENSING = BOILER__CONDENSING;

	/**
	 * The feature id for the '<em><b>Weather Compensated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__WEATHER_COMPENSATED = BOILER__WEATHER_COMPENSATED;

	/**
	 * The feature id for the '<em><b>Pump In Heated Space</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__PUMP_IN_HEATED_SPACE = BOILER__PUMP_IN_HEATED_SPACE;

	/**
	 * The feature id for the '<em><b>Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__STORE = BOILER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Store Temperature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU__STORE_TEMPERATURE = BOILER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>CPSU</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPSU_FEATURE_COUNT = BOILER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.KeepHotFacilityImpl <em>Keep Hot Facility</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.KeepHotFacilityImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getKeepHotFacility()
	 * @generated
	 */
	int KEEP_HOT_FACILITY = 2;

	/**
	 * The feature id for the '<em><b>Time Clock</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEEP_HOT_FACILITY__TIME_CLOCK = 0;

	/**
	 * The number of structural features of the '<em>Keep Hot Facility</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KEEP_HOT_FACILITY_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.CombiBoilerImpl <em>Combi Boiler</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.CombiBoilerImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getCombiBoiler()
	 * @generated
	 */
	int COMBI_BOILER = 5;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER__FUEL = BOILER__FUEL;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER__FLUE_TYPE = BOILER__FLUE_TYPE;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER__ANNUAL_OPERATIONAL_COST = BOILER__ANNUAL_OPERATIONAL_COST;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER__INSTALLATION_YEAR = BOILER__INSTALLATION_YEAR;

	/**
	 * The feature id for the '<em><b>Water Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER__WATER_HEATER = BOILER__WATER_HEATER;

	/**
	 * The feature id for the '<em><b>Space Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER__SPACE_HEATER = BOILER__SPACE_HEATER;

	/**
	 * The feature id for the '<em><b>Summer Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER__SUMMER_EFFICIENCY = BOILER__SUMMER_EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Winter Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER__WINTER_EFFICIENCY = BOILER__WINTER_EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Condensing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER__CONDENSING = BOILER__CONDENSING;

	/**
	 * The feature id for the '<em><b>Weather Compensated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER__WEATHER_COMPENSATED = BOILER__WEATHER_COMPENSATED;

	/**
	 * The feature id for the '<em><b>Pump In Heated Space</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER__PUMP_IN_HEATED_SPACE = BOILER__PUMP_IN_HEATED_SPACE;

	/**
	 * The number of structural features of the '<em>Combi Boiler</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMBI_BOILER_FEATURE_COUNT = BOILER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.StorageCombiBoilerImpl <em>Storage Combi Boiler</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.StorageCombiBoilerImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getStorageCombiBoiler()
	 * @generated
	 */
	int STORAGE_COMBI_BOILER = 3;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__FUEL = COMBI_BOILER__FUEL;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__FLUE_TYPE = COMBI_BOILER__FLUE_TYPE;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__ANNUAL_OPERATIONAL_COST = COMBI_BOILER__ANNUAL_OPERATIONAL_COST;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__INSTALLATION_YEAR = COMBI_BOILER__INSTALLATION_YEAR;

	/**
	 * The feature id for the '<em><b>Water Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__WATER_HEATER = COMBI_BOILER__WATER_HEATER;

	/**
	 * The feature id for the '<em><b>Space Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__SPACE_HEATER = COMBI_BOILER__SPACE_HEATER;

	/**
	 * The feature id for the '<em><b>Summer Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__SUMMER_EFFICIENCY = COMBI_BOILER__SUMMER_EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Winter Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__WINTER_EFFICIENCY = COMBI_BOILER__WINTER_EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Condensing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__CONDENSING = COMBI_BOILER__CONDENSING;

	/**
	 * The feature id for the '<em><b>Weather Compensated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__WEATHER_COMPENSATED = COMBI_BOILER__WEATHER_COMPENSATED;

	/**
	 * The feature id for the '<em><b>Pump In Heated Space</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__PUMP_IN_HEATED_SPACE = COMBI_BOILER__PUMP_IN_HEATED_SPACE;

	/**
	 * The feature id for the '<em><b>Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__STORE = COMBI_BOILER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Store In Primary Circuit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER__STORE_IN_PRIMARY_CIRCUIT = COMBI_BOILER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Storage Combi Boiler</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_COMBI_BOILER_FEATURE_COUNT = COMBI_BOILER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.InstantaneousCombiBoilerImpl <em>Instantaneous Combi Boiler</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.InstantaneousCombiBoilerImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getInstantaneousCombiBoiler()
	 * @generated
	 */
	int INSTANTANEOUS_COMBI_BOILER = 4;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__FUEL = COMBI_BOILER__FUEL;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__FLUE_TYPE = COMBI_BOILER__FLUE_TYPE;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__ANNUAL_OPERATIONAL_COST = COMBI_BOILER__ANNUAL_OPERATIONAL_COST;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__INSTALLATION_YEAR = COMBI_BOILER__INSTALLATION_YEAR;

	/**
	 * The feature id for the '<em><b>Water Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__WATER_HEATER = COMBI_BOILER__WATER_HEATER;

	/**
	 * The feature id for the '<em><b>Space Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__SPACE_HEATER = COMBI_BOILER__SPACE_HEATER;

	/**
	 * The feature id for the '<em><b>Summer Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__SUMMER_EFFICIENCY = COMBI_BOILER__SUMMER_EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Winter Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__WINTER_EFFICIENCY = COMBI_BOILER__WINTER_EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Condensing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__CONDENSING = COMBI_BOILER__CONDENSING;

	/**
	 * The feature id for the '<em><b>Weather Compensated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__WEATHER_COMPENSATED = COMBI_BOILER__WEATHER_COMPENSATED;

	/**
	 * The feature id for the '<em><b>Pump In Heated Space</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__PUMP_IN_HEATED_SPACE = COMBI_BOILER__PUMP_IN_HEATED_SPACE;

	/**
	 * The feature id for the '<em><b>Keep Hot Facility</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY = COMBI_BOILER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Instantaneous Combi Boiler</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANTANEOUS_COMBI_BOILER_FEATURE_COUNT = COMBI_BOILER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType <em>Flue Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getFlueType()
	 * @generated
	 */
	int FLUE_TYPE = 6;

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler <em>Boiler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boiler</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler
	 * @generated
	 */
	EClass getBoiler();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#getSummerEfficiency <em>Summer Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Summer Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#getSummerEfficiency()
	 * @see #getBoiler()
	 * @generated
	 */
	EAttribute getBoiler_SummerEfficiency();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#getWinterEfficiency <em>Winter Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Winter Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#getWinterEfficiency()
	 * @see #getBoiler()
	 * @generated
	 */
	EAttribute getBoiler_WinterEfficiency();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isCondensing <em>Condensing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condensing</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isCondensing()
	 * @see #getBoiler()
	 * @generated
	 */
	EAttribute getBoiler_Condensing();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isWeatherCompensated <em>Weather Compensated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weather Compensated</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isWeatherCompensated()
	 * @see #getBoiler()
	 * @generated
	 */
	EAttribute getBoiler_WeatherCompensated();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isPumpInHeatedSpace <em>Pump In Heated Space</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pump In Heated Space</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler#isPumpInHeatedSpace()
	 * @see #getBoiler()
	 * @generated
	 */
	EAttribute getBoiler_PumpInHeatedSpace();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU <em>CPSU</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CPSU</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU
	 * @generated
	 */
	EClass getCPSU();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU#getStoreTemperature <em>Store Temperature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Store Temperature</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU#getStoreTemperature()
	 * @see #getCPSU()
	 * @generated
	 */
	EAttribute getCPSU_StoreTemperature();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility <em>Keep Hot Facility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Keep Hot Facility</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility
	 * @generated
	 */
	EClass getKeepHotFacility();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility#isTimeClock <em>Time Clock</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Clock</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility#isTimeClock()
	 * @see #getKeepHotFacility()
	 * @generated
	 */
	EAttribute getKeepHotFacility_TimeClock();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler <em>Storage Combi Boiler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Storage Combi Boiler</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler
	 * @generated
	 */
	EClass getStorageCombiBoiler();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler#isStoreInPrimaryCircuit <em>Store In Primary Circuit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Store In Primary Circuit</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler#isStoreInPrimaryCircuit()
	 * @see #getStorageCombiBoiler()
	 * @generated
	 */
	EAttribute getStorageCombiBoiler_StoreInPrimaryCircuit();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler <em>Instantaneous Combi Boiler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instantaneous Combi Boiler</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler
	 * @generated
	 */
	EClass getInstantaneousCombiBoiler();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler#getKeepHotFacility <em>Keep Hot Facility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Keep Hot Facility</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler#getKeepHotFacility()
	 * @see #getInstantaneousCombiBoiler()
	 * @generated
	 */
	EReference getInstantaneousCombiBoiler_KeepHotFacility();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler <em>Combi Boiler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Combi Boiler</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler
	 * @generated
	 */
	EClass getCombiBoiler();

	/**
	 * Returns the meta object for enum '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType <em>Flue Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Flue Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType
	 * @generated
	 */
	EEnum getFlueType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IBoilersFactory getBoilersFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl <em>Boiler</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getBoiler()
		 * @generated
		 */
		EClass BOILER = eINSTANCE.getBoiler();

		/**
		 * The meta object literal for the '<em><b>Summer Efficiency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOILER__SUMMER_EFFICIENCY = eINSTANCE.getBoiler_SummerEfficiency();

		/**
		 * The meta object literal for the '<em><b>Winter Efficiency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOILER__WINTER_EFFICIENCY = eINSTANCE.getBoiler_WinterEfficiency();

		/**
		 * The meta object literal for the '<em><b>Condensing</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOILER__CONDENSING = eINSTANCE.getBoiler_Condensing();

		/**
		 * The meta object literal for the '<em><b>Weather Compensated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOILER__WEATHER_COMPENSATED = eINSTANCE.getBoiler_WeatherCompensated();

		/**
		 * The meta object literal for the '<em><b>Pump In Heated Space</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOILER__PUMP_IN_HEATED_SPACE = eINSTANCE.getBoiler_PumpInHeatedSpace();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.CPSUImpl <em>CPSU</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.CPSUImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getCPSU()
		 * @generated
		 */
		EClass CPSU = eINSTANCE.getCPSU();

		/**
		 * The meta object literal for the '<em><b>Store Temperature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CPSU__STORE_TEMPERATURE = eINSTANCE.getCPSU_StoreTemperature();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.KeepHotFacilityImpl <em>Keep Hot Facility</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.KeepHotFacilityImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getKeepHotFacility()
		 * @generated
		 */
		EClass KEEP_HOT_FACILITY = eINSTANCE.getKeepHotFacility();

		/**
		 * The meta object literal for the '<em><b>Time Clock</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute KEEP_HOT_FACILITY__TIME_CLOCK = eINSTANCE.getKeepHotFacility_TimeClock();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.StorageCombiBoilerImpl <em>Storage Combi Boiler</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.StorageCombiBoilerImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getStorageCombiBoiler()
		 * @generated
		 */
		EClass STORAGE_COMBI_BOILER = eINSTANCE.getStorageCombiBoiler();

		/**
		 * The meta object literal for the '<em><b>Store In Primary Circuit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STORAGE_COMBI_BOILER__STORE_IN_PRIMARY_CIRCUIT = eINSTANCE.getStorageCombiBoiler_StoreInPrimaryCircuit();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.InstantaneousCombiBoilerImpl <em>Instantaneous Combi Boiler</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.InstantaneousCombiBoilerImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getInstantaneousCombiBoiler()
		 * @generated
		 */
		EClass INSTANTANEOUS_COMBI_BOILER = eINSTANCE.getInstantaneousCombiBoiler();

		/**
		 * The meta object literal for the '<em><b>Keep Hot Facility</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY = eINSTANCE.getInstantaneousCombiBoiler_KeepHotFacility();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.CombiBoilerImpl <em>Combi Boiler</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.CombiBoilerImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getCombiBoiler()
		 * @generated
		 */
		EClass COMBI_BOILER = eINSTANCE.getCombiBoiler();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType <em>Flue Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType
		 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl#getFlueType()
		 * @generated
		 */
		EEnum FLUE_TYPE = eINSTANCE.getFlueType();

	}

} //IBoilersPackage
