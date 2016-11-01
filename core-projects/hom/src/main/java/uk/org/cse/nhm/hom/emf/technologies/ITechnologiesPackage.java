/**
 */
package uk.org.cse.nhm.hom.emf.technologies;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;

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
 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory
 * @model kind="package"
 * @generated
 */
public interface ITechnologiesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "technologies";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.cse.org.uk/nhm/schema/emf/technologies/1/";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tech";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ITechnologiesPackage eINSTANCE = uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl.init();

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter <em>Visitor Accepter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getVisitorAccepter()
	 * @generated
	 */
	int VISITOR_ACCEPTER = 12;

	/**
	 * The number of structural features of the '<em>Visitor Accepter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISITOR_ACCEPTER_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl <em>Technology Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getTechnologyModel()
	 * @generated
	 */
	int TECHNOLOGY_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Appliances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__APPLIANCES = VISITOR_ACCEPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lights</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__LIGHTS = VISITOR_ACCEPTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Individual Heat Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE = VISITOR_ACCEPTER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Cookers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__COOKERS = VISITOR_ACCEPTER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Primary Space Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER = VISITOR_ACCEPTER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Secondary Space Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER = VISITOR_ACCEPTER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Central Water System</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM = VISITOR_ACCEPTER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Secondary Water Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER = VISITOR_ACCEPTER_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Community Heat Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE = VISITOR_ACCEPTER_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Solar Photovoltaic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC = VISITOR_ACCEPTER_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Adjusters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__ADJUSTERS = VISITOR_ACCEPTER_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Shower</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL__SHOWER = VISITOR_ACCEPTER_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Technology Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TECHNOLOGY_MODEL_FEATURE_COUNT = VISITOR_ACCEPTER_FEATURE_COUNT + 12;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.INamed <em>Named</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.INamed
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getNamed()
	 * @generated
	 */
	int NAMED = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.ApplianceImpl <em>Appliance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.ApplianceImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getAppliance()
	 * @generated
	 */
	int APPLIANCE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIANCE__NAME = NAMED__NAME;

	/**
	 * The feature id for the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIANCE__EFFICIENCY = NAMED_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Appliance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLIANCE_FEATURE_COUNT = NAMED_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.LightImpl <em>Light</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.LightImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getLight()
	 * @generated
	 */
	int LIGHT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIGHT__NAME = NAMED__NAME;

	/**
	 * The feature id for the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIGHT__EFFICIENCY = NAMED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Proportion</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIGHT__PROPORTION = NAMED_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Light</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIGHT_FEATURE_COUNT = NAMED_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.SpaceHeaterImpl <em>Space Heater</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.SpaceHeaterImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getSpaceHeater()
	 * @generated
	 */
	int SPACE_HEATER = 3;

	/**
	 * The number of structural features of the '<em>Space Heater</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SPACE_HEATER_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.WaterHeaterImpl <em>Water Heater</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.WaterHeaterImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getWaterHeater()
	 * @generated
	 */
	int WATER_HEATER = 4;

	/**
	 * The number of structural features of the '<em>Water Heater</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WATER_HEATER_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl <em>Heat Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatSource()
	 * @generated
	 */
	int HEAT_SOURCE = 5;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl <em>Central Water System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCentralWaterSystem()
	 * @generated
	 */
	int CENTRAL_WATER_SYSTEM = 7;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater <em>Primary Space Heater</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getPrimarySpaceHeater()
	 * @generated
	 */
	int PRIMARY_SPACE_HEATER = 26;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralHeatingSystemImpl <em>Central Heating System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CentralHeatingSystemImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCentralHeatingSystem()
	 * @generated
	 */
	int CENTRAL_HEATING_SYSTEM = 8;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterHeaterImpl <em>Central Water Heater</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterHeaterImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCentralWaterHeater()
	 * @generated
	 */
	int CENTRAL_WATER_HEATER = 9;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.MainWaterHeaterImpl <em>Main Water Heater</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.MainWaterHeaterImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getMainWaterHeater()
	 * @generated
	 */
	int MAIN_WATER_HEATER = 10;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl <em>Water Tank</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getWaterTank()
	 * @generated
	 */
	int WATER_TANK = 11;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl <em>Solar Water Heater</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getSolarWaterHeater()
	 * @generated
	 */
	int SOLAR_WATER_HEATER = 13;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.IStoreContainer <em>Store Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IStoreContainer
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getStoreContainer()
	 * @generated
	 */
	int STORE_CONTAINER = 14;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.ImmersionHeaterImpl <em>Immersion Heater</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.ImmersionHeaterImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getImmersionHeater()
	 * @generated
	 */
	int IMMERSION_HEATER = 15;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl <em>Cooker</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCooker()
	 * @generated
	 */
	int COOKER = 16;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.StorageHeaterImpl <em>Storage Heater</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.StorageHeaterImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getStorageHeater()
	 * @generated
	 */
	int STORAGE_HEATER = 17;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CommunityHeatSourceImpl <em>Community Heat Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CommunityHeatSourceImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCommunityHeatSource()
	 * @generated
	 */
	int COMMUNITY_HEAT_SOURCE = 18;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CommunityCHPImpl <em>Community CHP</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CommunityCHPImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCommunityCHP()
	 * @generated
	 */
	int COMMUNITY_CHP = 19;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.RoomHeaterImpl <em>Room Heater</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.RoomHeaterImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getRoomHeater()
	 * @generated
	 */
	int ROOM_HEATER = 20;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource <em>Individual Heat Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIndividualHeatSource()
	 * @generated
	 */
	int INDIVIDUAL_HEAT_SOURCE = 29;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl <em>Heat Pump</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatPump()
	 * @generated
	 */
	int HEAT_PUMP = 21;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirSystemImpl <em>Warm Air System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirSystemImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getWarmAirSystem()
	 * @generated
	 */
	int WARM_AIR_SYSTEM = 22;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.PointOfUseWaterHeaterImpl <em>Point Of Use Water Heater</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.PointOfUseWaterHeaterImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getPointOfUseWaterHeater()
	 * @generated
	 */
	int POINT_OF_USE_WATER_HEATER = 23;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpWarmAirSystemImpl <em>Heat Pump Warm Air System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpWarmAirSystemImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatPumpWarmAirSystem()
	 * @generated
	 */
	int HEAT_PUMP_WARM_AIR_SYSTEM = 24;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirCirculatorImpl <em>Warm Air Circulator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirCirculatorImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getWarmAirCirculator()
	 * @generated
	 */
	int WARM_AIR_CIRCULATOR = 25;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.BackBoilerImpl <em>Back Boiler</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.BackBoilerImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getBackBoiler()
	 * @generated
	 */
	int BACK_BOILER = 27;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue <em>Fuel And Flue</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getFuelAndFlue()
	 * @generated
	 */
	int FUEL_AND_FLUE = 28;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUEL_AND_FLUE__FUEL = 0;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUEL_AND_FLUE__FLUE_TYPE = 1;

	/**
	 * The number of structural features of the '<em>Fuel And Flue</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUEL_AND_FLUE_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_SOURCE__FUEL = FUEL_AND_FLUE__FUEL;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_SOURCE__FLUE_TYPE = FUEL_AND_FLUE__FLUE_TYPE;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_SOURCE__ANNUAL_OPERATIONAL_COST = FUEL_AND_FLUE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_SOURCE__INSTALLATION_YEAR = FUEL_AND_FLUE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Water Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_SOURCE__WATER_HEATER = FUEL_AND_FLUE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Space Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_SOURCE__SPACE_HEATER = FUEL_AND_FLUE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Heat Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_SOURCE_FEATURE_COUNT = FUEL_AND_FLUE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_WATER_SYSTEM__STORE = WATER_HEATER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Store In Primary Circuit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_WATER_SYSTEM__STORE_IN_PRIMARY_CIRCUIT = WATER_HEATER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Primary Pipework Insulated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_WATER_SYSTEM__PRIMARY_PIPEWORK_INSULATED = WATER_HEATER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Separately Time Controlled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_WATER_SYSTEM__SEPARATELY_TIME_CONTROLLED = WATER_HEATER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Solar Water Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER = WATER_HEATER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Primary Water Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER = WATER_HEATER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Secondary Water Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER = WATER_HEATER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Technology Model</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_WATER_SYSTEM__TECHNOLOGY_MODEL = WATER_HEATER_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Central Water System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_WATER_SYSTEM_FEATURE_COUNT = WATER_HEATER_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Primary Space Heater</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_SPACE_HEATER_FEATURE_COUNT = SPACE_HEATER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Heat Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_HEATING_SYSTEM__HEAT_SOURCE = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Controls</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_HEATING_SYSTEM__CONTROLS = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Emitter Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_HEATING_SYSTEM__EMITTER_TYPE = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Central Heating System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_HEATING_SYSTEM_FEATURE_COUNT = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Central Water Heater</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CENTRAL_WATER_HEATER_FEATURE_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Heat Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_WATER_HEATER__HEAT_SOURCE = CENTRAL_WATER_HEATER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Main Water Heater</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAIN_WATER_HEATER_FEATURE_COUNT = CENTRAL_WATER_HEATER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Insulation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WATER_TANK__INSULATION = 0;

	/**
	 * The feature id for the '<em><b>Factory Insulation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WATER_TANK__FACTORY_INSULATION = 1;

	/**
	 * The feature id for the '<em><b>Volume</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WATER_TANK__VOLUME = 2;

	/**
	 * The feature id for the '<em><b>Thermostat Fitted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WATER_TANK__THERMOSTAT_FITTED = 3;

	/**
	 * The feature id for the '<em><b>Solar Storage Volume</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WATER_TANK__SOLAR_STORAGE_VOLUME = 4;

	/**
	 * The feature id for the '<em><b>Immersion Heater</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WATER_TANK__IMMERSION_HEATER = 5;

	/**
	 * The number of structural features of the '<em>Water Tank</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WATER_TANK_FEATURE_COUNT = 6;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_WATER_HEATER__ANNUAL_OPERATIONAL_COST = CENTRAL_WATER_HEATER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pitch</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_WATER_HEATER__PITCH = CENTRAL_WATER_HEATER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Orientation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_WATER_HEATER__ORIENTATION = CENTRAL_WATER_HEATER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Area</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_WATER_HEATER__AREA = CENTRAL_WATER_HEATER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Useful Area Ratio</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_WATER_HEATER__USEFUL_AREA_RATIO = CENTRAL_WATER_HEATER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Zero Loss Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_WATER_HEATER__ZERO_LOSS_EFFICIENCY = CENTRAL_WATER_HEATER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Linear Heat Loss Coefficient</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_WATER_HEATER__LINEAR_HEAT_LOSS_COEFFICIENT = CENTRAL_WATER_HEATER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Pre Heat Tank Volume</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_WATER_HEATER__PRE_HEAT_TANK_VOLUME = CENTRAL_WATER_HEATER_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Pump Photovolatic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_WATER_HEATER__PUMP_PHOTOVOLATIC = CENTRAL_WATER_HEATER_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Solar Water Heater</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_WATER_HEATER_FEATURE_COUNT = CENTRAL_WATER_HEATER_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORE_CONTAINER__STORE = 0;

	/**
	 * The number of structural features of the '<em>Store Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORE_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Dual Coil</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMERSION_HEATER__DUAL_COIL = CENTRAL_WATER_HEATER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Immersion Heater</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMMERSION_HEATER_FEATURE_COUNT = CENTRAL_WATER_HEATER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Hob Base Load</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COOKER__HOB_BASE_LOAD = VISITOR_ACCEPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Hob Occupancy Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COOKER__HOB_OCCUPANCY_FACTOR = VISITOR_ACCEPTER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Hob Fuel Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COOKER__HOB_FUEL_TYPE = VISITOR_ACCEPTER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Oven Base Load</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COOKER__OVEN_BASE_LOAD = VISITOR_ACCEPTER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Oven Occupancy Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COOKER__OVEN_OCCUPANCY_FACTOR = VISITOR_ACCEPTER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Oven Fuel Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COOKER__OVEN_FUEL_TYPE = VISITOR_ACCEPTER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Gains Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COOKER__GAINS_FACTOR = VISITOR_ACCEPTER_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Cooker</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COOKER_FEATURE_COUNT = VISITOR_ACCEPTER_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_HEATER__ANNUAL_OPERATIONAL_COST = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Responsiveness Override</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_HEATER__RESPONSIVENESS_OVERRIDE = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Control Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_HEATER__CONTROL_TYPE = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_HEATER__TYPE = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Storage Heater</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_HEATER_FEATURE_COUNT = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_HEAT_SOURCE__FUEL = HEAT_SOURCE__FUEL;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_HEAT_SOURCE__FLUE_TYPE = HEAT_SOURCE__FLUE_TYPE;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_HEAT_SOURCE__ANNUAL_OPERATIONAL_COST = HEAT_SOURCE__ANNUAL_OPERATIONAL_COST;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_HEAT_SOURCE__INSTALLATION_YEAR = HEAT_SOURCE__INSTALLATION_YEAR;

	/**
	 * The feature id for the '<em><b>Water Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_HEAT_SOURCE__WATER_HEATER = HEAT_SOURCE__WATER_HEATER;

	/**
	 * The feature id for the '<em><b>Space Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_HEAT_SOURCE__SPACE_HEATER = HEAT_SOURCE__SPACE_HEATER;

	/**
	 * The feature id for the '<em><b>Charging Usage Based</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_HEAT_SOURCE__CHARGING_USAGE_BASED = HEAT_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Heat Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_HEAT_SOURCE__HEAT_EFFICIENCY = HEAT_SOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Community Heat Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_HEAT_SOURCE_FEATURE_COUNT = HEAT_SOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_CHP__FUEL = COMMUNITY_HEAT_SOURCE__FUEL;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_CHP__FLUE_TYPE = COMMUNITY_HEAT_SOURCE__FLUE_TYPE;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_CHP__ANNUAL_OPERATIONAL_COST = COMMUNITY_HEAT_SOURCE__ANNUAL_OPERATIONAL_COST;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_CHP__INSTALLATION_YEAR = COMMUNITY_HEAT_SOURCE__INSTALLATION_YEAR;

	/**
	 * The feature id for the '<em><b>Water Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_CHP__WATER_HEATER = COMMUNITY_HEAT_SOURCE__WATER_HEATER;

	/**
	 * The feature id for the '<em><b>Space Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_CHP__SPACE_HEATER = COMMUNITY_HEAT_SOURCE__SPACE_HEATER;

	/**
	 * The feature id for the '<em><b>Charging Usage Based</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_CHP__CHARGING_USAGE_BASED = COMMUNITY_HEAT_SOURCE__CHARGING_USAGE_BASED;

	/**
	 * The feature id for the '<em><b>Heat Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_CHP__HEAT_EFFICIENCY = COMMUNITY_HEAT_SOURCE__HEAT_EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Electrical Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_CHP__ELECTRICAL_EFFICIENCY = COMMUNITY_HEAT_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Community CHP</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNITY_CHP_FEATURE_COUNT = COMMUNITY_HEAT_SOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_HEATER__FUEL = SPACE_HEATER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_HEATER__FLUE_TYPE = SPACE_HEATER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_HEATER__EFFICIENCY = SPACE_HEATER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Thermostat Fitted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_HEATER__THERMOSTAT_FITTED = SPACE_HEATER_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Room Heater</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_HEATER_FEATURE_COUNT = SPACE_HEATER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDIVIDUAL_HEAT_SOURCE__FUEL = HEAT_SOURCE__FUEL;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDIVIDUAL_HEAT_SOURCE__FLUE_TYPE = HEAT_SOURCE__FLUE_TYPE;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDIVIDUAL_HEAT_SOURCE__ANNUAL_OPERATIONAL_COST = HEAT_SOURCE__ANNUAL_OPERATIONAL_COST;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDIVIDUAL_HEAT_SOURCE__INSTALLATION_YEAR = HEAT_SOURCE__INSTALLATION_YEAR;

	/**
	 * The feature id for the '<em><b>Water Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDIVIDUAL_HEAT_SOURCE__WATER_HEATER = HEAT_SOURCE__WATER_HEATER;

	/**
	 * The feature id for the '<em><b>Space Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDIVIDUAL_HEAT_SOURCE__SPACE_HEATER = HEAT_SOURCE__SPACE_HEATER;

	/**
	 * The number of structural features of the '<em>Individual Heat Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT = HEAT_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP__FUEL = INDIVIDUAL_HEAT_SOURCE__FUEL;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP__FLUE_TYPE = INDIVIDUAL_HEAT_SOURCE__FLUE_TYPE;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP__ANNUAL_OPERATIONAL_COST = INDIVIDUAL_HEAT_SOURCE__ANNUAL_OPERATIONAL_COST;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP__INSTALLATION_YEAR = INDIVIDUAL_HEAT_SOURCE__INSTALLATION_YEAR;

	/**
	 * The feature id for the '<em><b>Water Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP__WATER_HEATER = INDIVIDUAL_HEAT_SOURCE__WATER_HEATER;

	/**
	 * The feature id for the '<em><b>Space Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP__SPACE_HEATER = INDIVIDUAL_HEAT_SOURCE__SPACE_HEATER;

	/**
	 * The feature id for the '<em><b>Source Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP__SOURCE_TYPE = INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Coefficient Of Performance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE = INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Weather Compensated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP__WEATHER_COMPENSATED = INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Auxiliary Present</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP__AUXILIARY_PRESENT = INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Hybrid</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP__HYBRID = INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Heat Pump</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP_FEATURE_COUNT = INDIVIDUAL_HEAT_SOURCE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Fuel Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WARM_AIR_SYSTEM__FUEL_TYPE = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WARM_AIR_SYSTEM__EFFICIENCY = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Circulator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WARM_AIR_SYSTEM__CIRCULATOR = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Controls</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WARM_AIR_SYSTEM__CONTROLS = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Warm Air System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WARM_AIR_SYSTEM_FEATURE_COUNT = PRIMARY_SPACE_HEATER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Fuel Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POINT_OF_USE_WATER_HEATER__FUEL_TYPE = WATER_HEATER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Multipoint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POINT_OF_USE_WATER_HEATER__MULTIPOINT = WATER_HEATER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POINT_OF_USE_WATER_HEATER__EFFICIENCY = WATER_HEATER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Point Of Use Water Heater</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POINT_OF_USE_WATER_HEATER_FEATURE_COUNT = WATER_HEATER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Fuel Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP_WARM_AIR_SYSTEM__FUEL_TYPE = WARM_AIR_SYSTEM__FUEL_TYPE;

	/**
	 * The feature id for the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP_WARM_AIR_SYSTEM__EFFICIENCY = WARM_AIR_SYSTEM__EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Circulator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP_WARM_AIR_SYSTEM__CIRCULATOR = WARM_AIR_SYSTEM__CIRCULATOR;

	/**
	 * The feature id for the '<em><b>Controls</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP_WARM_AIR_SYSTEM__CONTROLS = WARM_AIR_SYSTEM__CONTROLS;

	/**
	 * The feature id for the '<em><b>Source Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP_WARM_AIR_SYSTEM__SOURCE_TYPE = WARM_AIR_SYSTEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Auxiliary Present</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP_WARM_AIR_SYSTEM__AUXILIARY_PRESENT = WARM_AIR_SYSTEM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Heat Pump Warm Air System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEAT_PUMP_WARM_AIR_SYSTEM_FEATURE_COUNT = WARM_AIR_SYSTEM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Warm Air System</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM = CENTRAL_WATER_HEATER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Warm Air Circulator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WARM_AIR_CIRCULATOR_FEATURE_COUNT = CENTRAL_WATER_HEATER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__FUEL = IBoilersPackage.BOILER__FUEL;

	/**
	 * The feature id for the '<em><b>Flue Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__FLUE_TYPE = IBoilersPackage.BOILER__FLUE_TYPE;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__ANNUAL_OPERATIONAL_COST = IBoilersPackage.BOILER__ANNUAL_OPERATIONAL_COST;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__INSTALLATION_YEAR = IBoilersPackage.BOILER__INSTALLATION_YEAR;

	/**
	 * The feature id for the '<em><b>Water Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__WATER_HEATER = IBoilersPackage.BOILER__WATER_HEATER;

	/**
	 * The feature id for the '<em><b>Space Heater</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__SPACE_HEATER = IBoilersPackage.BOILER__SPACE_HEATER;

	/**
	 * The feature id for the '<em><b>Summer Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__SUMMER_EFFICIENCY = IBoilersPackage.BOILER__SUMMER_EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Winter Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__WINTER_EFFICIENCY = IBoilersPackage.BOILER__WINTER_EFFICIENCY;

	/**
	 * The feature id for the '<em><b>Condensing</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__CONDENSING = IBoilersPackage.BOILER__CONDENSING;

	/**
	 * The feature id for the '<em><b>Weather Compensated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__WEATHER_COMPENSATED = IBoilersPackage.BOILER__WEATHER_COMPENSATED;

	/**
	 * The feature id for the '<em><b>Pump In Heated Space</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__PUMP_IN_HEATED_SPACE = IBoilersPackage.BOILER__PUMP_IN_HEATED_SPACE;

	/**
	 * The feature id for the '<em><b>Efficiency Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__EFFICIENCY_SOURCE = IBoilersPackage.BOILER__EFFICIENCY_SOURCE;

	/**
	 * The feature id for the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__EFFICIENCY = IBoilersPackage.BOILER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Thermostat Fitted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER__THERMOSTAT_FITTED = IBoilersPackage.BOILER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Back Boiler</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BACK_BOILER_FEATURE_COUNT = IBoilersPackage.BOILER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.OperationalCostImpl <em>Operational Cost</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.OperationalCostImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getOperationalCost()
	 * @generated
	 */
	int OPERATIONAL_COST = 30;

	/**
	 * The feature id for the '<em><b>Annual Operational Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST = 0;

	/**
	 * The number of structural features of the '<em>Operational Cost</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATIONAL_COST_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarPhotovoltaicImpl <em>Solar Photovoltaic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.SolarPhotovoltaicImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getSolarPhotovoltaic()
	 * @generated
	 */
	int SOLAR_PHOTOVOLTAIC = 31;

	/**
	 * The feature id for the '<em><b>Peak Power</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_PHOTOVOLTAIC__PEAK_POWER = VISITOR_ACCEPTER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Own Use Proportion</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION = VISITOR_ACCEPTER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Solar Photovoltaic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLAR_PHOTOVOLTAIC_FEATURE_COUNT = VISITOR_ACCEPTER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.AdjusterImpl <em>Adjuster</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.AdjusterImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getAdjuster()
	 * @generated
	 */
	int ADJUSTER = 32;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADJUSTER__NAME = NAMED__NAME;

	/**
	 * The feature id for the '<em><b>Fuel Types</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADJUSTER__FUEL_TYPES = NAMED_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deltas</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADJUSTER__DELTAS = NAMED_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Gains</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADJUSTER__GAINS = NAMED_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Adjuster</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADJUSTER_FEATURE_COUNT = NAMED_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.HybridHeaterImpl <em>Hybrid Heater</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.HybridHeaterImpl
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHybridHeater()
	 * @generated
	 */
	int HYBRID_HEATER = 33;

	/**
	 * The feature id for the '<em><b>Fuel</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYBRID_HEATER__FUEL = 0;

	/**
	 * The feature id for the '<em><b>Efficiency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYBRID_HEATER__EFFICIENCY = 1;

	/**
	 * The feature id for the '<em><b>Fraction</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYBRID_HEATER__FRACTION = 2;

	/**
	 * The number of structural features of the '<em>Hybrid Heater</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HYBRID_HEATER_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear <em>Has Installation Year</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHasInstallationYear()
	 * @generated
	 */
	int HAS_INSTALLATION_YEAR = 34;

	/**
	 * The feature id for the '<em><b>Installation Year</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HAS_INSTALLATION_YEAR__INSTALLATION_YEAR = 0;

	/**
	 * The number of structural features of the '<em>Has Installation Year</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HAS_INSTALLATION_YEAR_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.FuelType <em>Fuel Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getFuelType()
	 * @generated
	 */
	int FUEL_TYPE = 35;


	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType <em>Heating System Control Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatingSystemControlType()
	 * @generated
	 */
	int HEATING_SYSTEM_CONTROL_TYPE = 36;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.EmitterType <em>Emitter Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.EmitterType
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getEmitterType()
	 * @generated
	 */
	int EMITTER_TYPE = 37;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType <em>Storage Heater Control Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getStorageHeaterControlType()
	 * @generated
	 */
	int STORAGE_HEATER_CONTROL_TYPE = 38;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType <em>Storage Heater Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getStorageHeaterType()
	 * @generated
	 */
	int STORAGE_HEATER_TYPE = 39;

	/**
	 * The meta object id for the '{@link uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType <em>Heat Pump Source Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatPumpSourceType()
	 * @generated
	 */
	int HEAT_PUMP_SOURCE_TYPE = 40;

	/**
	 * The meta object id for the '<em>IEnergy Calculator Visitor</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIEnergyCalculatorVisitor()
	 * @generated
	 */
	int IENERGY_CALCULATOR_VISITOR = 41;

	/**
	 * The meta object id for the '<em>IInternal Parameters</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.energycalculator.api.IInternalParameters
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIInternalParameters()
	 * @generated
	 */
	int IINTERNAL_PARAMETERS = 42;


	/**
	 * The meta object id for the '<em>IEnergy State</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.energycalculator.api.IEnergyState
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIEnergyState()
	 * @generated
	 */
	int IENERGY_STATE = 43;


	/**
	 * The meta object id for the '<em>Energy Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.energycalculator.api.types.EnergyType
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getEnergyType()
	 * @generated
	 */
	int ENERGY_TYPE = 44;


	/**
	 * The meta object id for the '<em>Atomic Integer</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.concurrent.atomic.AtomicInteger
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getAtomicInteger()
	 * @generated
	 */
	int ATOMIC_INTEGER = 45;


	/**
	 * The meta object id for the '<em>IConstants</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.energycalculator.api.IConstants
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIConstants()
	 * @generated
	 */
	int ICONSTANTS = 46;

	/**
	 * The meta object id for the '<em>IEnergy Calculator Parameters</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIEnergyCalculatorParameters()
	 * @generated
	 */
	int IENERGY_CALCULATOR_PARAMETERS = 47;

	/**
	 * The meta object id for the '<em>Efficiency</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.emf.util.Efficiency
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getEfficiency()
	 * @generated
	 */
	int EFFICIENCY = 48;


	/**
	 * The meta object id for the '<em>Heat Proportions</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uk.org.cse.nhm.hom.IHeatProportions
	 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatProportions()
	 * @generated
	 */
	int HEAT_PROPORTIONS = 49;


	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel <em>Technology Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Technology Model</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel
	 * @generated
	 */
	EClass getTechnologyModel();

	/**
	 * Returns the meta object for the containment reference list '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getAppliances <em>Appliances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Appliances</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getAppliances()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_Appliances();

	/**
	 * Returns the meta object for the containment reference list '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getLights <em>Lights</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Lights</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getLights()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_Lights();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getIndividualHeatSource <em>Individual Heat Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Individual Heat Source</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getIndividualHeatSource()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_IndividualHeatSource();

	/**
	 * Returns the meta object for the containment reference list '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getCookers <em>Cookers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cookers</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getCookers()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_Cookers();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getPrimarySpaceHeater <em>Primary Space Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Space Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getPrimarySpaceHeater()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_PrimarySpaceHeater();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSecondarySpaceHeater <em>Secondary Space Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Secondary Space Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSecondarySpaceHeater()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_SecondarySpaceHeater();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getCentralWaterSystem <em>Central Water System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Central Water System</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getCentralWaterSystem()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_CentralWaterSystem();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSecondaryWaterHeater <em>Secondary Water Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Secondary Water Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSecondaryWaterHeater()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_SecondaryWaterHeater();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getCommunityHeatSource <em>Community Heat Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Community Heat Source</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getCommunityHeatSource()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_CommunityHeatSource();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSolarPhotovoltaic <em>Solar Photovoltaic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Solar Photovoltaic</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getSolarPhotovoltaic()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_SolarPhotovoltaic();

	/**
	 * Returns the meta object for the containment reference list '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getAdjusters <em>Adjusters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Adjusters</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getAdjusters()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_Adjusters();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getShower <em>Shower</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shower</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel#getShower()
	 * @see #getTechnologyModel()
	 * @generated
	 */
	EReference getTechnologyModel_Shower();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IAppliance <em>Appliance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Appliance</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IAppliance
	 * @generated
	 */
	EClass getAppliance();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IAppliance#getEfficiency <em>Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IAppliance#getEfficiency()
	 * @see #getAppliance()
	 * @generated
	 */
	EAttribute getAppliance_Efficiency();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.ILight <em>Light</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Light</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ILight
	 * @generated
	 */
	EClass getLight();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ILight#getEfficiency <em>Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ILight#getEfficiency()
	 * @see #getLight()
	 * @generated
	 */
	EAttribute getLight_Efficiency();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ILight#getProportion <em>Proportion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Proportion</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ILight#getProportion()
	 * @see #getLight()
	 * @generated
	 */
	EAttribute getLight_Proportion();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater <em>Space Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Space Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater
	 * @generated
	 */
	EClass getSpaceHeater();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterHeater <em>Water Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Water Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWaterHeater
	 * @generated
	 */
	EClass getWaterHeater();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatSource <em>Heat Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Heat Source</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatSource
	 * @generated
	 */
	EClass getHeatSource();

	/**
	 * Returns the meta object for the reference '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getWaterHeater <em>Water Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Water Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getWaterHeater()
	 * @see #getHeatSource()
	 * @generated
	 */
	EReference getHeatSource_WaterHeater();

	/**
	 * Returns the meta object for the reference '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getSpaceHeater <em>Space Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Space Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatSource#getSpaceHeater()
	 * @see #getHeatSource()
	 * @generated
	 */
	EReference getHeatSource_SpaceHeater();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.INamed <em>Named</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.INamed
	 * @generated
	 */
	EClass getNamed();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.INamed#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.INamed#getName()
	 * @see #getNamed()
	 * @generated
	 */
	EAttribute getNamed_Name();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem <em>Central Water System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Central Water System</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem
	 * @generated
	 */
	EClass getCentralWaterSystem();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isStoreInPrimaryCircuit <em>Store In Primary Circuit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Store In Primary Circuit</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isStoreInPrimaryCircuit()
	 * @see #getCentralWaterSystem()
	 * @generated
	 */
	EAttribute getCentralWaterSystem_StoreInPrimaryCircuit();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isPrimaryPipeworkInsulated <em>Primary Pipework Insulated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary Pipework Insulated</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isPrimaryPipeworkInsulated()
	 * @see #getCentralWaterSystem()
	 * @generated
	 */
	EAttribute getCentralWaterSystem_PrimaryPipeworkInsulated();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isSeparatelyTimeControlled <em>Separately Time Controlled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Separately Time Controlled</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#isSeparatelyTimeControlled()
	 * @see #getCentralWaterSystem()
	 * @generated
	 */
	EAttribute getCentralWaterSystem_SeparatelyTimeControlled();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getSolarWaterHeater <em>Solar Water Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Solar Water Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getSolarWaterHeater()
	 * @see #getCentralWaterSystem()
	 * @generated
	 */
	EReference getCentralWaterSystem_SolarWaterHeater();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getPrimaryWaterHeater <em>Primary Water Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Primary Water Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getPrimaryWaterHeater()
	 * @see #getCentralWaterSystem()
	 * @generated
	 */
	EReference getCentralWaterSystem_PrimaryWaterHeater();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getSecondaryWaterHeater <em>Secondary Water Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Secondary Water Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getSecondaryWaterHeater()
	 * @see #getCentralWaterSystem()
	 * @generated
	 */
	EReference getCentralWaterSystem_SecondaryWaterHeater();

	/**
	 * Returns the meta object for the container reference '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getTechnologyModel <em>Technology Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Technology Model</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem#getTechnologyModel()
	 * @see #getCentralWaterSystem()
	 * @generated
	 */
	EReference getCentralWaterSystem_TechnologyModel();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem <em>Central Heating System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Central Heating System</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem
	 * @generated
	 */
	EClass getCentralHeatingSystem();

	/**
	 * Returns the meta object for the reference '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getHeatSource <em>Heat Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Heat Source</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getHeatSource()
	 * @see #getCentralHeatingSystem()
	 * @generated
	 */
	EReference getCentralHeatingSystem_HeatSource();

	/**
	 * Returns the meta object for the attribute list '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getControls <em>Controls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Controls</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getControls()
	 * @see #getCentralHeatingSystem()
	 * @generated
	 */
	EAttribute getCentralHeatingSystem_Controls();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getEmitterType <em>Emitter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Emitter Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem#getEmitterType()
	 * @see #getCentralHeatingSystem()
	 * @generated
	 */
	EAttribute getCentralHeatingSystem_EmitterType();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater <em>Central Water Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Central Water Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater
	 * @generated
	 */
	EClass getCentralWaterHeater();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater <em>Main Water Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Main Water Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater
	 * @generated
	 */
	EClass getMainWaterHeater();

	/**
	 * Returns the meta object for the reference '{@link uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater#getHeatSource <em>Heat Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Heat Source</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater#getHeatSource()
	 * @see #getMainWaterHeater()
	 * @generated
	 */
	EReference getMainWaterHeater_HeatSource();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank <em>Water Tank</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Water Tank</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWaterTank
	 * @generated
	 */
	EClass getWaterTank();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getInsulation <em>Insulation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Insulation</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getInsulation()
	 * @see #getWaterTank()
	 * @generated
	 */
	EAttribute getWaterTank_Insulation();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#isFactoryInsulation <em>Factory Insulation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Factory Insulation</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWaterTank#isFactoryInsulation()
	 * @see #getWaterTank()
	 * @generated
	 */
	EAttribute getWaterTank_FactoryInsulation();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getVolume <em>Volume</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Volume</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getVolume()
	 * @see #getWaterTank()
	 * @generated
	 */
	EAttribute getWaterTank_Volume();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#isThermostatFitted <em>Thermostat Fitted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Thermostat Fitted</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWaterTank#isThermostatFitted()
	 * @see #getWaterTank()
	 * @generated
	 */
	EAttribute getWaterTank_ThermostatFitted();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getSolarStorageVolume <em>Solar Storage Volume</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Solar Storage Volume</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getSolarStorageVolume()
	 * @see #getWaterTank()
	 * @generated
	 */
	EAttribute getWaterTank_SolarStorageVolume();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getImmersionHeater <em>Immersion Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Immersion Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWaterTank#getImmersionHeater()
	 * @see #getWaterTank()
	 * @generated
	 */
	EReference getWaterTank_ImmersionHeater();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter <em>Visitor Accepter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Visitor Accepter</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter
	 * @generated
	 */
	EClass getVisitorAccepter();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater <em>Solar Water Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Solar Water Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater
	 * @generated
	 */
	EClass getSolarWaterHeater();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getPitch <em>Pitch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pitch</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getPitch()
	 * @see #getSolarWaterHeater()
	 * @generated
	 */
	EAttribute getSolarWaterHeater_Pitch();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getOrientation <em>Orientation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Orientation</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getOrientation()
	 * @see #getSolarWaterHeater()
	 * @generated
	 */
	EAttribute getSolarWaterHeater_Orientation();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getArea <em>Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Area</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getArea()
	 * @see #getSolarWaterHeater()
	 * @generated
	 */
	EAttribute getSolarWaterHeater_Area();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getUsefulAreaRatio <em>Useful Area Ratio</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Useful Area Ratio</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getUsefulAreaRatio()
	 * @see #getSolarWaterHeater()
	 * @generated
	 */
	EAttribute getSolarWaterHeater_UsefulAreaRatio();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getZeroLossEfficiency <em>Zero Loss Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Zero Loss Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getZeroLossEfficiency()
	 * @see #getSolarWaterHeater()
	 * @generated
	 */
	EAttribute getSolarWaterHeater_ZeroLossEfficiency();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getLinearHeatLossCoefficient <em>Linear Heat Loss Coefficient</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Linear Heat Loss Coefficient</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getLinearHeatLossCoefficient()
	 * @see #getSolarWaterHeater()
	 * @generated
	 */
	EAttribute getSolarWaterHeater_LinearHeatLossCoefficient();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getPreHeatTankVolume <em>Pre Heat Tank Volume</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pre Heat Tank Volume</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#getPreHeatTankVolume()
	 * @see #getSolarWaterHeater()
	 * @generated
	 */
	EAttribute getSolarWaterHeater_PreHeatTankVolume();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#isPumpPhotovolatic <em>Pump Photovolatic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pump Photovolatic</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater#isPumpPhotovolatic()
	 * @see #getSolarWaterHeater()
	 * @generated
	 */
	EAttribute getSolarWaterHeater_PumpPhotovolatic();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IStoreContainer <em>Store Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Store Container</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IStoreContainer
	 * @generated
	 */
	EClass getStoreContainer();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.IStoreContainer#getStore <em>Store</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Store</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IStoreContainer#getStore()
	 * @see #getStoreContainer()
	 * @generated
	 */
	EReference getStoreContainer_Store();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater <em>Immersion Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Immersion Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater
	 * @generated
	 */
	EClass getImmersionHeater();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater#isDualCoil <em>Dual Coil</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dual Coil</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater#isDualCoil()
	 * @see #getImmersionHeater()
	 * @generated
	 */
	EAttribute getImmersionHeater_DualCoil();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker <em>Cooker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cooker</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICooker
	 * @generated
	 */
	EClass getCooker();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobBaseLoad <em>Hob Base Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hob Base Load</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobBaseLoad()
	 * @see #getCooker()
	 * @generated
	 */
	EAttribute getCooker_HobBaseLoad();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobOccupancyFactor <em>Hob Occupancy Factor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hob Occupancy Factor</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobOccupancyFactor()
	 * @see #getCooker()
	 * @generated
	 */
	EAttribute getCooker_HobOccupancyFactor();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobFuelType <em>Hob Fuel Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hob Fuel Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICooker#getHobFuelType()
	 * @see #getCooker()
	 * @generated
	 */
	EAttribute getCooker_HobFuelType();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenBaseLoad <em>Oven Base Load</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Oven Base Load</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenBaseLoad()
	 * @see #getCooker()
	 * @generated
	 */
	EAttribute getCooker_OvenBaseLoad();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenOccupancyFactor <em>Oven Occupancy Factor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Oven Occupancy Factor</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenOccupancyFactor()
	 * @see #getCooker()
	 * @generated
	 */
	EAttribute getCooker_OvenOccupancyFactor();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenFuelType <em>Oven Fuel Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Oven Fuel Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICooker#getOvenFuelType()
	 * @see #getCooker()
	 * @generated
	 */
	EAttribute getCooker_OvenFuelType();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICooker#getGainsFactor <em>Gains Factor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Gains Factor</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICooker#getGainsFactor()
	 * @see #getCooker()
	 * @generated
	 */
	EAttribute getCooker_GainsFactor();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IStorageHeater <em>Storage Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Storage Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IStorageHeater
	 * @generated
	 */
	EClass getStorageHeater();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getResponsivenessOverride <em>Responsiveness Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Responsiveness Override</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getResponsivenessOverride()
	 * @see #getStorageHeater()
	 * @generated
	 */
	EAttribute getStorageHeater_ResponsivenessOverride();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getControlType <em>Control Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Control Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getControlType()
	 * @see #getStorageHeater()
	 * @generated
	 */
	EAttribute getStorageHeater_ControlType();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IStorageHeater#getType()
	 * @see #getStorageHeater()
	 * @generated
	 */
	EAttribute getStorageHeater_Type();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource <em>Community Heat Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Community Heat Source</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource
	 * @generated
	 */
	EClass getCommunityHeatSource();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource#isChargingUsageBased <em>Charging Usage Based</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Charging Usage Based</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource#isChargingUsageBased()
	 * @see #getCommunityHeatSource()
	 * @generated
	 */
	EAttribute getCommunityHeatSource_ChargingUsageBased();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource#getHeatEfficiency <em>Heat Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Heat Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource#getHeatEfficiency()
	 * @see #getCommunityHeatSource()
	 * @generated
	 */
	EAttribute getCommunityHeatSource_HeatEfficiency();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP <em>Community CHP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Community CHP</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP
	 * @generated
	 */
	EClass getCommunityCHP();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP#getElectricalEfficiency <em>Electrical Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Electrical Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP#getElectricalEfficiency()
	 * @see #getCommunityCHP()
	 * @generated
	 */
	EAttribute getCommunityCHP_ElectricalEfficiency();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IRoomHeater <em>Room Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Room Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IRoomHeater
	 * @generated
	 */
	EClass getRoomHeater();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IRoomHeater#getEfficiency <em>Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IRoomHeater#getEfficiency()
	 * @see #getRoomHeater()
	 * @generated
	 */
	EAttribute getRoomHeater_Efficiency();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IRoomHeater#isThermostatFitted <em>Thermostat Fitted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Thermostat Fitted</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IRoomHeater#isThermostatFitted()
	 * @see #getRoomHeater()
	 * @generated
	 */
	EAttribute getRoomHeater_ThermostatFitted();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump <em>Heat Pump</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Heat Pump</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatPump
	 * @generated
	 */
	EClass getHeatPump();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getSourceType <em>Source Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getSourceType()
	 * @see #getHeatPump()
	 * @generated
	 */
	EAttribute getHeatPump_SourceType();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getCoefficientOfPerformance <em>Coefficient Of Performance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Coefficient Of Performance</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getCoefficientOfPerformance()
	 * @see #getHeatPump()
	 * @generated
	 */
	EAttribute getHeatPump_CoefficientOfPerformance();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#isWeatherCompensated <em>Weather Compensated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weather Compensated</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatPump#isWeatherCompensated()
	 * @see #getHeatPump()
	 * @generated
	 */
	EAttribute getHeatPump_WeatherCompensated();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#isAuxiliaryPresent <em>Auxiliary Present</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auxiliary Present</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatPump#isAuxiliaryPresent()
	 * @see #getHeatPump()
	 * @generated
	 */
	EAttribute getHeatPump_AuxiliaryPresent();

	/**
	 * Returns the meta object for the containment reference '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getHybrid <em>Hybrid</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Hybrid</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatPump#getHybrid()
	 * @see #getHeatPump()
	 * @generated
	 */
	EReference getHeatPump_Hybrid();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem <em>Warm Air System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Warm Air System</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem
	 * @generated
	 */
	EClass getWarmAirSystem();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getFuelType <em>Fuel Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fuel Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getFuelType()
	 * @see #getWarmAirSystem()
	 * @generated
	 */
	EAttribute getWarmAirSystem_FuelType();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getEfficiency <em>Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getEfficiency()
	 * @see #getWarmAirSystem()
	 * @generated
	 */
	EAttribute getWarmAirSystem_Efficiency();

	/**
	 * Returns the meta object for the reference '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getCirculator <em>Circulator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Circulator</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getCirculator()
	 * @see #getWarmAirSystem()
	 * @generated
	 */
	EReference getWarmAirSystem_Circulator();

	/**
	 * Returns the meta object for the attribute list '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getControls <em>Controls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Controls</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem#getControls()
	 * @see #getWarmAirSystem()
	 * @generated
	 */
	EAttribute getWarmAirSystem_Controls();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater <em>Point Of Use Water Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Point Of Use Water Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater
	 * @generated
	 */
	EClass getPointOfUseWaterHeater();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#getFuelType <em>Fuel Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fuel Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#getFuelType()
	 * @see #getPointOfUseWaterHeater()
	 * @generated
	 */
	EAttribute getPointOfUseWaterHeater_FuelType();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#isMultipoint <em>Multipoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multipoint</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#isMultipoint()
	 * @see #getPointOfUseWaterHeater()
	 * @generated
	 */
	EAttribute getPointOfUseWaterHeater_Multipoint();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#getEfficiency <em>Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater#getEfficiency()
	 * @see #getPointOfUseWaterHeater()
	 * @generated
	 */
	EAttribute getPointOfUseWaterHeater_Efficiency();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem <em>Heat Pump Warm Air System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Heat Pump Warm Air System</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem
	 * @generated
	 */
	EClass getHeatPumpWarmAirSystem();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem#getSourceType <em>Source Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem#getSourceType()
	 * @see #getHeatPumpWarmAirSystem()
	 * @generated
	 */
	EAttribute getHeatPumpWarmAirSystem_SourceType();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem#isAuxiliaryPresent <em>Auxiliary Present</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auxiliary Present</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem#isAuxiliaryPresent()
	 * @see #getHeatPumpWarmAirSystem()
	 * @generated
	 */
	EAttribute getHeatPumpWarmAirSystem_AuxiliaryPresent();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator <em>Warm Air Circulator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Warm Air Circulator</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator
	 * @generated
	 */
	EClass getWarmAirCirculator();

	/**
	 * Returns the meta object for the reference '{@link uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator#getWarmAirSystem <em>Warm Air System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Warm Air System</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator#getWarmAirSystem()
	 * @see #getWarmAirCirculator()
	 * @generated
	 */
	EReference getWarmAirCirculator_WarmAirSystem();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater <em>Primary Space Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primary Space Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater
	 * @generated
	 */
	EClass getPrimarySpaceHeater();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IBackBoiler <em>Back Boiler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Back Boiler</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IBackBoiler
	 * @generated
	 */
	EClass getBackBoiler();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue <em>Fuel And Flue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fuel And Flue</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue
	 * @generated
	 */
	EClass getFuelAndFlue();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue#getFuel <em>Fuel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fuel</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue#getFuel()
	 * @see #getFuelAndFlue()
	 * @generated
	 */
	EAttribute getFuelAndFlue_Fuel();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue#getFlueType <em>Flue Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Flue Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue#getFlueType()
	 * @see #getFuelAndFlue()
	 * @generated
	 */
	EAttribute getFuelAndFlue_FlueType();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource <em>Individual Heat Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Individual Heat Source</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource
	 * @generated
	 */
	EClass getIndividualHeatSource();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IOperationalCost <em>Operational Cost</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operational Cost</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IOperationalCost
	 * @generated
	 */
	EClass getOperationalCost();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IOperationalCost#getAnnualOperationalCost <em>Annual Operational Cost</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Annual Operational Cost</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IOperationalCost#getAnnualOperationalCost()
	 * @see #getOperationalCost()
	 * @generated
	 */
	EAttribute getOperationalCost_AnnualOperationalCost();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic <em>Solar Photovoltaic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Solar Photovoltaic</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic
	 * @generated
	 */
	EClass getSolarPhotovoltaic();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getPeakPower <em>Peak Power</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Peak Power</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getPeakPower()
	 * @see #getSolarPhotovoltaic()
	 * @generated
	 */
	EAttribute getSolarPhotovoltaic_PeakPower();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getOwnUseProportion <em>Own Use Proportion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Own Use Proportion</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic#getOwnUseProportion()
	 * @see #getSolarPhotovoltaic()
	 * @generated
	 */
	EAttribute getSolarPhotovoltaic_OwnUseProportion();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IAdjuster <em>Adjuster</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adjuster</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IAdjuster
	 * @generated
	 */
	EClass getAdjuster();

	/**
	 * Returns the meta object for the attribute list '{@link uk.org.cse.nhm.hom.emf.technologies.IAdjuster#getFuelTypes <em>Fuel Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Fuel Types</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IAdjuster#getFuelTypes()
	 * @see #getAdjuster()
	 * @generated
	 */
	EAttribute getAdjuster_FuelTypes();

	/**
	 * Returns the meta object for the attribute list '{@link uk.org.cse.nhm.hom.emf.technologies.IAdjuster#getDeltas <em>Deltas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Deltas</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IAdjuster#getDeltas()
	 * @see #getAdjuster()
	 * @generated
	 */
	EAttribute getAdjuster_Deltas();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IAdjuster#getGains <em>Gains</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Gains</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IAdjuster#getGains()
	 * @see #getAdjuster()
	 * @generated
	 */
	EAttribute getAdjuster_Gains();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IHybridHeater <em>Hybrid Heater</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hybrid Heater</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHybridHeater
	 * @generated
	 */
	EClass getHybridHeater();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IHybridHeater#getFuel <em>Fuel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fuel</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHybridHeater#getFuel()
	 * @see #getHybridHeater()
	 * @generated
	 */
	EAttribute getHybridHeater_Fuel();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IHybridHeater#getEfficiency <em>Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHybridHeater#getEfficiency()
	 * @see #getHybridHeater()
	 * @generated
	 */
	EAttribute getHybridHeater_Efficiency();

	/**
	 * Returns the meta object for the attribute list '{@link uk.org.cse.nhm.hom.emf.technologies.IHybridHeater#getFraction <em>Fraction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Fraction</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHybridHeater#getFraction()
	 * @see #getHybridHeater()
	 * @generated
	 */
	EAttribute getHybridHeater_Fraction();

	/**
	 * Returns the meta object for class '{@link uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear <em>Has Installation Year</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Has Installation Year</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear
	 * @generated
	 */
	EClass getHasInstallationYear();

	/**
	 * Returns the meta object for the attribute '{@link uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear#getInstallationYear <em>Installation Year</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Installation Year</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear#getInstallationYear()
	 * @see #getHasInstallationYear()
	 * @generated
	 */
	EAttribute getHasInstallationYear_InstallationYear();

	/**
	 * Returns the meta object for enum '{@link uk.org.cse.nhm.hom.emf.technologies.FuelType <em>Fuel Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Fuel Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
	 * @generated
	 */
	EEnum getFuelType();

	/**
	 * Returns the meta object for enum '{@link uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType <em>Heating System Control Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Heating System Control Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType
	 * @generated
	 */
	EEnum getHeatingSystemControlType();

	/**
	 * Returns the meta object for enum '{@link uk.org.cse.nhm.hom.emf.technologies.EmitterType <em>Emitter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Emitter Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.EmitterType
	 * @generated
	 */
	EEnum getEmitterType();

	/**
	 * Returns the meta object for enum '{@link uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType <em>Storage Heater Control Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Storage Heater Control Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType
	 * @generated
	 */
	EEnum getStorageHeaterControlType();

	/**
	 * Returns the meta object for enum '{@link uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType <em>Storage Heater Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Storage Heater Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType
	 * @generated
	 */
	EEnum getStorageHeaterType();

	/**
	 * Returns the meta object for enum '{@link uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType <em>Heat Pump Source Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Heat Pump Source Type</em>'.
	 * @see uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType
	 * @generated
	 */
	EEnum getHeatPumpSourceType();

	/**
	 * Returns the meta object for data type '{@link uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor <em>IEnergy Calculator Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IEnergy Calculator Visitor</em>'.
	 * @see uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor
	 * @model instanceClass="uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor"
	 * @generated
	 */
	EDataType getIEnergyCalculatorVisitor();

	/**
	 * Returns the meta object for data type '{@link uk.org.cse.nhm.energycalculator.api.IInternalParameters <em>IInternal Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IInternal Parameters</em>'.
	 * @see uk.org.cse.nhm.energycalculator.api.IInternalParameters
	 * @model instanceClass="uk.org.cse.nhm.energycalculator.api.IInternalParameters"
	 * @generated
	 */
	EDataType getIInternalParameters();

	/**
	 * Returns the meta object for data type '{@link uk.org.cse.nhm.energycalculator.api.IEnergyState <em>IEnergy State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IEnergy State</em>'.
	 * @see uk.org.cse.nhm.energycalculator.api.IEnergyState
	 * @model instanceClass="uk.org.cse.nhm.energycalculator.api.IEnergyState"
	 * @generated
	 */
	EDataType getIEnergyState();

	/**
	 * Returns the meta object for data type '{@link uk.org.cse.nhm.energycalculator.api.types.EnergyType <em>Energy Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Energy Type</em>'.
	 * @see uk.org.cse.nhm.energycalculator.api.types.EnergyType
	 * @model instanceClass="uk.org.cse.nhm.energycalculator.api.types.EnergyType"
	 * @generated
	 */
	EDataType getEnergyType();

	/**
	 * Returns the meta object for data type '{@link java.util.concurrent.atomic.AtomicInteger <em>Atomic Integer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Atomic Integer</em>'.
	 * @see java.util.concurrent.atomic.AtomicInteger
	 * @model instanceClass="java.util.concurrent.atomic.AtomicInteger"
	 * @generated
	 */
	EDataType getAtomicInteger();

	/**
	 * Returns the meta object for data type '{@link uk.org.cse.nhm.energycalculator.api.IConstants <em>IConstants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IConstants</em>'.
	 * @see uk.org.cse.nhm.energycalculator.api.IConstants
	 * @model instanceClass="uk.org.cse.nhm.energycalculator.api.IConstants"
	 * @generated
	 */
	EDataType getIConstants();

	/**
	 * Returns the meta object for data type '{@link uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters <em>IEnergy Calculator Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IEnergy Calculator Parameters</em>'.
	 * @see uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters
	 * @model instanceClass="uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters"
	 * @generated
	 */
	EDataType getIEnergyCalculatorParameters();

	/**
	 * Returns the meta object for data type '{@link uk.org.cse.nhm.hom.emf.util.Efficiency <em>Efficiency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Efficiency</em>'.
	 * @see uk.org.cse.nhm.hom.emf.util.Efficiency
	 * @model instanceClass="uk.org.cse.nhm.hom.emf.util.Efficiency"
	 * @generated
	 */
	EDataType getEfficiency();

	/**
	 * Returns the meta object for data type '{@link uk.org.cse.nhm.hom.IHeatProportions <em>Heat Proportions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Heat Proportions</em>'.
	 * @see uk.org.cse.nhm.hom.IHeatProportions
	 * @model instanceClass="uk.org.cse.nhm.hom.IHeatProportions"
	 * @generated
	 */
	EDataType getHeatProportions();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ITechnologiesFactory getTechnologiesFactory();

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
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl <em>Technology Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologyModelImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getTechnologyModel()
		 * @generated
		 */
		EClass TECHNOLOGY_MODEL = eINSTANCE.getTechnologyModel();

		/**
		 * The meta object literal for the '<em><b>Appliances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__APPLIANCES = eINSTANCE.getTechnologyModel_Appliances();

		/**
		 * The meta object literal for the '<em><b>Lights</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__LIGHTS = eINSTANCE.getTechnologyModel_Lights();

		/**
		 * The meta object literal for the '<em><b>Individual Heat Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE = eINSTANCE.getTechnologyModel_IndividualHeatSource();

		/**
		 * The meta object literal for the '<em><b>Cookers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__COOKERS = eINSTANCE.getTechnologyModel_Cookers();

		/**
		 * The meta object literal for the '<em><b>Primary Space Heater</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER = eINSTANCE.getTechnologyModel_PrimarySpaceHeater();

		/**
		 * The meta object literal for the '<em><b>Secondary Space Heater</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER = eINSTANCE.getTechnologyModel_SecondarySpaceHeater();

		/**
		 * The meta object literal for the '<em><b>Central Water System</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM = eINSTANCE.getTechnologyModel_CentralWaterSystem();

		/**
		 * The meta object literal for the '<em><b>Secondary Water Heater</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER = eINSTANCE.getTechnologyModel_SecondaryWaterHeater();

		/**
		 * The meta object literal for the '<em><b>Community Heat Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE = eINSTANCE.getTechnologyModel_CommunityHeatSource();

		/**
		 * The meta object literal for the '<em><b>Solar Photovoltaic</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC = eINSTANCE.getTechnologyModel_SolarPhotovoltaic();

		/**
		 * The meta object literal for the '<em><b>Adjusters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__ADJUSTERS = eINSTANCE.getTechnologyModel_Adjusters();

		/**
		 * The meta object literal for the '<em><b>Shower</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TECHNOLOGY_MODEL__SHOWER = eINSTANCE.getTechnologyModel_Shower();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.ApplianceImpl <em>Appliance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.ApplianceImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getAppliance()
		 * @generated
		 */
		EClass APPLIANCE = eINSTANCE.getAppliance();

		/**
		 * The meta object literal for the '<em><b>Efficiency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLIANCE__EFFICIENCY = eINSTANCE.getAppliance_Efficiency();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.LightImpl <em>Light</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.LightImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getLight()
		 * @generated
		 */
		EClass LIGHT = eINSTANCE.getLight();

		/**
		 * The meta object literal for the '<em><b>Efficiency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIGHT__EFFICIENCY = eINSTANCE.getLight_Efficiency();

		/**
		 * The meta object literal for the '<em><b>Proportion</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIGHT__PROPORTION = eINSTANCE.getLight_Proportion();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.SpaceHeaterImpl <em>Space Heater</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.SpaceHeaterImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getSpaceHeater()
		 * @generated
		 */
		EClass SPACE_HEATER = eINSTANCE.getSpaceHeater();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.WaterHeaterImpl <em>Water Heater</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.WaterHeaterImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getWaterHeater()
		 * @generated
		 */
		EClass WATER_HEATER = eINSTANCE.getWaterHeater();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl <em>Heat Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.HeatSourceImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatSource()
		 * @generated
		 */
		EClass HEAT_SOURCE = eINSTANCE.getHeatSource();

		/**
		 * The meta object literal for the '<em><b>Water Heater</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEAT_SOURCE__WATER_HEATER = eINSTANCE.getHeatSource_WaterHeater();

		/**
		 * The meta object literal for the '<em><b>Space Heater</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEAT_SOURCE__SPACE_HEATER = eINSTANCE.getHeatSource_SpaceHeater();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.INamed <em>Named</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.INamed
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getNamed()
		 * @generated
		 */
		EClass NAMED = eINSTANCE.getNamed();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED__NAME = eINSTANCE.getNamed_Name();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl <em>Central Water System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCentralWaterSystem()
		 * @generated
		 */
		EClass CENTRAL_WATER_SYSTEM = eINSTANCE.getCentralWaterSystem();

		/**
		 * The meta object literal for the '<em><b>Store In Primary Circuit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CENTRAL_WATER_SYSTEM__STORE_IN_PRIMARY_CIRCUIT = eINSTANCE.getCentralWaterSystem_StoreInPrimaryCircuit();

		/**
		 * The meta object literal for the '<em><b>Primary Pipework Insulated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CENTRAL_WATER_SYSTEM__PRIMARY_PIPEWORK_INSULATED = eINSTANCE.getCentralWaterSystem_PrimaryPipeworkInsulated();

		/**
		 * The meta object literal for the '<em><b>Separately Time Controlled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CENTRAL_WATER_SYSTEM__SEPARATELY_TIME_CONTROLLED = eINSTANCE.getCentralWaterSystem_SeparatelyTimeControlled();

		/**
		 * The meta object literal for the '<em><b>Solar Water Heater</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER = eINSTANCE.getCentralWaterSystem_SolarWaterHeater();

		/**
		 * The meta object literal for the '<em><b>Primary Water Heater</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER = eINSTANCE.getCentralWaterSystem_PrimaryWaterHeater();

		/**
		 * The meta object literal for the '<em><b>Secondary Water Heater</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER = eINSTANCE.getCentralWaterSystem_SecondaryWaterHeater();

		/**
		 * The meta object literal for the '<em><b>Technology Model</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CENTRAL_WATER_SYSTEM__TECHNOLOGY_MODEL = eINSTANCE.getCentralWaterSystem_TechnologyModel();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralHeatingSystemImpl <em>Central Heating System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CentralHeatingSystemImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCentralHeatingSystem()
		 * @generated
		 */
		EClass CENTRAL_HEATING_SYSTEM = eINSTANCE.getCentralHeatingSystem();

		/**
		 * The meta object literal for the '<em><b>Heat Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CENTRAL_HEATING_SYSTEM__HEAT_SOURCE = eINSTANCE.getCentralHeatingSystem_HeatSource();

		/**
		 * The meta object literal for the '<em><b>Controls</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CENTRAL_HEATING_SYSTEM__CONTROLS = eINSTANCE.getCentralHeatingSystem_Controls();

		/**
		 * The meta object literal for the '<em><b>Emitter Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CENTRAL_HEATING_SYSTEM__EMITTER_TYPE = eINSTANCE.getCentralHeatingSystem_EmitterType();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterHeaterImpl <em>Central Water Heater</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterHeaterImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCentralWaterHeater()
		 * @generated
		 */
		EClass CENTRAL_WATER_HEATER = eINSTANCE.getCentralWaterHeater();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.MainWaterHeaterImpl <em>Main Water Heater</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.MainWaterHeaterImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getMainWaterHeater()
		 * @generated
		 */
		EClass MAIN_WATER_HEATER = eINSTANCE.getMainWaterHeater();

		/**
		 * The meta object literal for the '<em><b>Heat Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAIN_WATER_HEATER__HEAT_SOURCE = eINSTANCE.getMainWaterHeater_HeatSource();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl <em>Water Tank</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.WaterTankImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getWaterTank()
		 * @generated
		 */
		EClass WATER_TANK = eINSTANCE.getWaterTank();

		/**
		 * The meta object literal for the '<em><b>Insulation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WATER_TANK__INSULATION = eINSTANCE.getWaterTank_Insulation();

		/**
		 * The meta object literal for the '<em><b>Factory Insulation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WATER_TANK__FACTORY_INSULATION = eINSTANCE.getWaterTank_FactoryInsulation();

		/**
		 * The meta object literal for the '<em><b>Volume</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WATER_TANK__VOLUME = eINSTANCE.getWaterTank_Volume();

		/**
		 * The meta object literal for the '<em><b>Thermostat Fitted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WATER_TANK__THERMOSTAT_FITTED = eINSTANCE.getWaterTank_ThermostatFitted();

		/**
		 * The meta object literal for the '<em><b>Solar Storage Volume</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WATER_TANK__SOLAR_STORAGE_VOLUME = eINSTANCE.getWaterTank_SolarStorageVolume();

		/**
		 * The meta object literal for the '<em><b>Immersion Heater</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WATER_TANK__IMMERSION_HEATER = eINSTANCE.getWaterTank_ImmersionHeater();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter <em>Visitor Accepter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getVisitorAccepter()
		 * @generated
		 */
		EClass VISITOR_ACCEPTER = eINSTANCE.getVisitorAccepter();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl <em>Solar Water Heater</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getSolarWaterHeater()
		 * @generated
		 */
		EClass SOLAR_WATER_HEATER = eINSTANCE.getSolarWaterHeater();

		/**
		 * The meta object literal for the '<em><b>Pitch</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLAR_WATER_HEATER__PITCH = eINSTANCE.getSolarWaterHeater_Pitch();

		/**
		 * The meta object literal for the '<em><b>Orientation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLAR_WATER_HEATER__ORIENTATION = eINSTANCE.getSolarWaterHeater_Orientation();

		/**
		 * The meta object literal for the '<em><b>Area</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLAR_WATER_HEATER__AREA = eINSTANCE.getSolarWaterHeater_Area();

		/**
		 * The meta object literal for the '<em><b>Useful Area Ratio</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLAR_WATER_HEATER__USEFUL_AREA_RATIO = eINSTANCE.getSolarWaterHeater_UsefulAreaRatio();

		/**
		 * The meta object literal for the '<em><b>Zero Loss Efficiency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLAR_WATER_HEATER__ZERO_LOSS_EFFICIENCY = eINSTANCE.getSolarWaterHeater_ZeroLossEfficiency();

		/**
		 * The meta object literal for the '<em><b>Linear Heat Loss Coefficient</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLAR_WATER_HEATER__LINEAR_HEAT_LOSS_COEFFICIENT = eINSTANCE.getSolarWaterHeater_LinearHeatLossCoefficient();

		/**
		 * The meta object literal for the '<em><b>Pre Heat Tank Volume</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLAR_WATER_HEATER__PRE_HEAT_TANK_VOLUME = eINSTANCE.getSolarWaterHeater_PreHeatTankVolume();

		/**
		 * The meta object literal for the '<em><b>Pump Photovolatic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLAR_WATER_HEATER__PUMP_PHOTOVOLATIC = eINSTANCE.getSolarWaterHeater_PumpPhotovolatic();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.IStoreContainer <em>Store Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.IStoreContainer
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getStoreContainer()
		 * @generated
		 */
		EClass STORE_CONTAINER = eINSTANCE.getStoreContainer();

		/**
		 * The meta object literal for the '<em><b>Store</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STORE_CONTAINER__STORE = eINSTANCE.getStoreContainer_Store();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.ImmersionHeaterImpl <em>Immersion Heater</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.ImmersionHeaterImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getImmersionHeater()
		 * @generated
		 */
		EClass IMMERSION_HEATER = eINSTANCE.getImmersionHeater();

		/**
		 * The meta object literal for the '<em><b>Dual Coil</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMMERSION_HEATER__DUAL_COIL = eINSTANCE.getImmersionHeater_DualCoil();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl <em>Cooker</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CookerImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCooker()
		 * @generated
		 */
		EClass COOKER = eINSTANCE.getCooker();

		/**
		 * The meta object literal for the '<em><b>Hob Base Load</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COOKER__HOB_BASE_LOAD = eINSTANCE.getCooker_HobBaseLoad();

		/**
		 * The meta object literal for the '<em><b>Hob Occupancy Factor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COOKER__HOB_OCCUPANCY_FACTOR = eINSTANCE.getCooker_HobOccupancyFactor();

		/**
		 * The meta object literal for the '<em><b>Hob Fuel Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COOKER__HOB_FUEL_TYPE = eINSTANCE.getCooker_HobFuelType();

		/**
		 * The meta object literal for the '<em><b>Oven Base Load</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COOKER__OVEN_BASE_LOAD = eINSTANCE.getCooker_OvenBaseLoad();

		/**
		 * The meta object literal for the '<em><b>Oven Occupancy Factor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COOKER__OVEN_OCCUPANCY_FACTOR = eINSTANCE.getCooker_OvenOccupancyFactor();

		/**
		 * The meta object literal for the '<em><b>Oven Fuel Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COOKER__OVEN_FUEL_TYPE = eINSTANCE.getCooker_OvenFuelType();

		/**
		 * The meta object literal for the '<em><b>Gains Factor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COOKER__GAINS_FACTOR = eINSTANCE.getCooker_GainsFactor();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.StorageHeaterImpl <em>Storage Heater</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.StorageHeaterImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getStorageHeater()
		 * @generated
		 */
		EClass STORAGE_HEATER = eINSTANCE.getStorageHeater();

		/**
		 * The meta object literal for the '<em><b>Responsiveness Override</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STORAGE_HEATER__RESPONSIVENESS_OVERRIDE = eINSTANCE.getStorageHeater_ResponsivenessOverride();

		/**
		 * The meta object literal for the '<em><b>Control Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STORAGE_HEATER__CONTROL_TYPE = eINSTANCE.getStorageHeater_ControlType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STORAGE_HEATER__TYPE = eINSTANCE.getStorageHeater_Type();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CommunityHeatSourceImpl <em>Community Heat Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CommunityHeatSourceImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCommunityHeatSource()
		 * @generated
		 */
		EClass COMMUNITY_HEAT_SOURCE = eINSTANCE.getCommunityHeatSource();

		/**
		 * The meta object literal for the '<em><b>Charging Usage Based</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMUNITY_HEAT_SOURCE__CHARGING_USAGE_BASED = eINSTANCE.getCommunityHeatSource_ChargingUsageBased();

		/**
		 * The meta object literal for the '<em><b>Heat Efficiency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMUNITY_HEAT_SOURCE__HEAT_EFFICIENCY = eINSTANCE.getCommunityHeatSource_HeatEfficiency();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.CommunityCHPImpl <em>Community CHP</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.CommunityCHPImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getCommunityCHP()
		 * @generated
		 */
		EClass COMMUNITY_CHP = eINSTANCE.getCommunityCHP();

		/**
		 * The meta object literal for the '<em><b>Electrical Efficiency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMUNITY_CHP__ELECTRICAL_EFFICIENCY = eINSTANCE.getCommunityCHP_ElectricalEfficiency();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.RoomHeaterImpl <em>Room Heater</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.RoomHeaterImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getRoomHeater()
		 * @generated
		 */
		EClass ROOM_HEATER = eINSTANCE.getRoomHeater();

		/**
		 * The meta object literal for the '<em><b>Efficiency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOM_HEATER__EFFICIENCY = eINSTANCE.getRoomHeater_Efficiency();

		/**
		 * The meta object literal for the '<em><b>Thermostat Fitted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOM_HEATER__THERMOSTAT_FITTED = eINSTANCE.getRoomHeater_ThermostatFitted();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl <em>Heat Pump</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatPump()
		 * @generated
		 */
		EClass HEAT_PUMP = eINSTANCE.getHeatPump();

		/**
		 * The meta object literal for the '<em><b>Source Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEAT_PUMP__SOURCE_TYPE = eINSTANCE.getHeatPump_SourceType();

		/**
		 * The meta object literal for the '<em><b>Coefficient Of Performance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE = eINSTANCE.getHeatPump_CoefficientOfPerformance();

		/**
		 * The meta object literal for the '<em><b>Weather Compensated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEAT_PUMP__WEATHER_COMPENSATED = eINSTANCE.getHeatPump_WeatherCompensated();

		/**
		 * The meta object literal for the '<em><b>Auxiliary Present</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEAT_PUMP__AUXILIARY_PRESENT = eINSTANCE.getHeatPump_AuxiliaryPresent();

		/**
		 * The meta object literal for the '<em><b>Hybrid</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HEAT_PUMP__HYBRID = eINSTANCE.getHeatPump_Hybrid();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirSystemImpl <em>Warm Air System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirSystemImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getWarmAirSystem()
		 * @generated
		 */
		EClass WARM_AIR_SYSTEM = eINSTANCE.getWarmAirSystem();

		/**
		 * The meta object literal for the '<em><b>Fuel Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WARM_AIR_SYSTEM__FUEL_TYPE = eINSTANCE.getWarmAirSystem_FuelType();

		/**
		 * The meta object literal for the '<em><b>Efficiency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WARM_AIR_SYSTEM__EFFICIENCY = eINSTANCE.getWarmAirSystem_Efficiency();

		/**
		 * The meta object literal for the '<em><b>Circulator</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WARM_AIR_SYSTEM__CIRCULATOR = eINSTANCE.getWarmAirSystem_Circulator();

		/**
		 * The meta object literal for the '<em><b>Controls</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WARM_AIR_SYSTEM__CONTROLS = eINSTANCE.getWarmAirSystem_Controls();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.PointOfUseWaterHeaterImpl <em>Point Of Use Water Heater</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.PointOfUseWaterHeaterImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getPointOfUseWaterHeater()
		 * @generated
		 */
		EClass POINT_OF_USE_WATER_HEATER = eINSTANCE.getPointOfUseWaterHeater();

		/**
		 * The meta object literal for the '<em><b>Fuel Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POINT_OF_USE_WATER_HEATER__FUEL_TYPE = eINSTANCE.getPointOfUseWaterHeater_FuelType();

		/**
		 * The meta object literal for the '<em><b>Multipoint</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POINT_OF_USE_WATER_HEATER__MULTIPOINT = eINSTANCE.getPointOfUseWaterHeater_Multipoint();

		/**
		 * The meta object literal for the '<em><b>Efficiency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POINT_OF_USE_WATER_HEATER__EFFICIENCY = eINSTANCE.getPointOfUseWaterHeater_Efficiency();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpWarmAirSystemImpl <em>Heat Pump Warm Air System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpWarmAirSystemImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatPumpWarmAirSystem()
		 * @generated
		 */
		EClass HEAT_PUMP_WARM_AIR_SYSTEM = eINSTANCE.getHeatPumpWarmAirSystem();

		/**
		 * The meta object literal for the '<em><b>Source Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEAT_PUMP_WARM_AIR_SYSTEM__SOURCE_TYPE = eINSTANCE.getHeatPumpWarmAirSystem_SourceType();

		/**
		 * The meta object literal for the '<em><b>Auxiliary Present</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HEAT_PUMP_WARM_AIR_SYSTEM__AUXILIARY_PRESENT = eINSTANCE.getHeatPumpWarmAirSystem_AuxiliaryPresent();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirCirculatorImpl <em>Warm Air Circulator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirCirculatorImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getWarmAirCirculator()
		 * @generated
		 */
		EClass WARM_AIR_CIRCULATOR = eINSTANCE.getWarmAirCirculator();

		/**
		 * The meta object literal for the '<em><b>Warm Air System</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM = eINSTANCE.getWarmAirCirculator_WarmAirSystem();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater <em>Primary Space Heater</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getPrimarySpaceHeater()
		 * @generated
		 */
		EClass PRIMARY_SPACE_HEATER = eINSTANCE.getPrimarySpaceHeater();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.BackBoilerImpl <em>Back Boiler</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.BackBoilerImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getBackBoiler()
		 * @generated
		 */
		EClass BACK_BOILER = eINSTANCE.getBackBoiler();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue <em>Fuel And Flue</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getFuelAndFlue()
		 * @generated
		 */
		EClass FUEL_AND_FLUE = eINSTANCE.getFuelAndFlue();

		/**
		 * The meta object literal for the '<em><b>Fuel</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUEL_AND_FLUE__FUEL = eINSTANCE.getFuelAndFlue_Fuel();

		/**
		 * The meta object literal for the '<em><b>Flue Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUEL_AND_FLUE__FLUE_TYPE = eINSTANCE.getFuelAndFlue_FlueType();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource <em>Individual Heat Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIndividualHeatSource()
		 * @generated
		 */
		EClass INDIVIDUAL_HEAT_SOURCE = eINSTANCE.getIndividualHeatSource();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.OperationalCostImpl <em>Operational Cost</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.OperationalCostImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getOperationalCost()
		 * @generated
		 */
		EClass OPERATIONAL_COST = eINSTANCE.getOperationalCost();

		/**
		 * The meta object literal for the '<em><b>Annual Operational Cost</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST = eINSTANCE.getOperationalCost_AnnualOperationalCost();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarPhotovoltaicImpl <em>Solar Photovoltaic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.SolarPhotovoltaicImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getSolarPhotovoltaic()
		 * @generated
		 */
		EClass SOLAR_PHOTOVOLTAIC = eINSTANCE.getSolarPhotovoltaic();

		/**
		 * The meta object literal for the '<em><b>Peak Power</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLAR_PHOTOVOLTAIC__PEAK_POWER = eINSTANCE.getSolarPhotovoltaic_PeakPower();

		/**
		 * The meta object literal for the '<em><b>Own Use Proportion</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION = eINSTANCE.getSolarPhotovoltaic_OwnUseProportion();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.AdjusterImpl <em>Adjuster</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.AdjusterImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getAdjuster()
		 * @generated
		 */
		EClass ADJUSTER = eINSTANCE.getAdjuster();

		/**
		 * The meta object literal for the '<em><b>Fuel Types</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADJUSTER__FUEL_TYPES = eINSTANCE.getAdjuster_FuelTypes();

		/**
		 * The meta object literal for the '<em><b>Deltas</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADJUSTER__DELTAS = eINSTANCE.getAdjuster_Deltas();

		/**
		 * The meta object literal for the '<em><b>Gains</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADJUSTER__GAINS = eINSTANCE.getAdjuster_Gains();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.impl.HybridHeaterImpl <em>Hybrid Heater</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.HybridHeaterImpl
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHybridHeater()
		 * @generated
		 */
		EClass HYBRID_HEATER = eINSTANCE.getHybridHeater();

		/**
		 * The meta object literal for the '<em><b>Fuel</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HYBRID_HEATER__FUEL = eINSTANCE.getHybridHeater_Fuel();

		/**
		 * The meta object literal for the '<em><b>Efficiency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HYBRID_HEATER__EFFICIENCY = eINSTANCE.getHybridHeater_Efficiency();

		/**
		 * The meta object literal for the '<em><b>Fraction</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HYBRID_HEATER__FRACTION = eINSTANCE.getHybridHeater_Fraction();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear <em>Has Installation Year</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHasInstallationYear()
		 * @generated
		 */
		EClass HAS_INSTALLATION_YEAR = eINSTANCE.getHasInstallationYear();

		/**
		 * The meta object literal for the '<em><b>Installation Year</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HAS_INSTALLATION_YEAR__INSTALLATION_YEAR = eINSTANCE.getHasInstallationYear_InstallationYear();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.FuelType <em>Fuel Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.FuelType
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getFuelType()
		 * @generated
		 */
		EEnum FUEL_TYPE = eINSTANCE.getFuelType();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType <em>Heating System Control Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatingSystemControlType()
		 * @generated
		 */
		EEnum HEATING_SYSTEM_CONTROL_TYPE = eINSTANCE.getHeatingSystemControlType();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.EmitterType <em>Emitter Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.EmitterType
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getEmitterType()
		 * @generated
		 */
		EEnum EMITTER_TYPE = eINSTANCE.getEmitterType();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType <em>Storage Heater Control Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getStorageHeaterControlType()
		 * @generated
		 */
		EEnum STORAGE_HEATER_CONTROL_TYPE = eINSTANCE.getStorageHeaterControlType();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType <em>Storage Heater Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getStorageHeaterType()
		 * @generated
		 */
		EEnum STORAGE_HEATER_TYPE = eINSTANCE.getStorageHeaterType();

		/**
		 * The meta object literal for the '{@link uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType <em>Heat Pump Source Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatPumpSourceType()
		 * @generated
		 */
		EEnum HEAT_PUMP_SOURCE_TYPE = eINSTANCE.getHeatPumpSourceType();

		/**
		 * The meta object literal for the '<em>IEnergy Calculator Visitor</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIEnergyCalculatorVisitor()
		 * @generated
		 */
		EDataType IENERGY_CALCULATOR_VISITOR = eINSTANCE.getIEnergyCalculatorVisitor();

		/**
		 * The meta object literal for the '<em>IInternal Parameters</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.energycalculator.api.IInternalParameters
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIInternalParameters()
		 * @generated
		 */
		EDataType IINTERNAL_PARAMETERS = eINSTANCE.getIInternalParameters();

		/**
		 * The meta object literal for the '<em>IEnergy State</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.energycalculator.api.IEnergyState
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIEnergyState()
		 * @generated
		 */
		EDataType IENERGY_STATE = eINSTANCE.getIEnergyState();

		/**
		 * The meta object literal for the '<em>Energy Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.energycalculator.api.types.EnergyType
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getEnergyType()
		 * @generated
		 */
		EDataType ENERGY_TYPE = eINSTANCE.getEnergyType();

		/**
		 * The meta object literal for the '<em>Atomic Integer</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.concurrent.atomic.AtomicInteger
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getAtomicInteger()
		 * @generated
		 */
		EDataType ATOMIC_INTEGER = eINSTANCE.getAtomicInteger();

		/**
		 * The meta object literal for the '<em>IConstants</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.energycalculator.api.IConstants
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIConstants()
		 * @generated
		 */
		EDataType ICONSTANTS = eINSTANCE.getIConstants();

		/**
		 * The meta object literal for the '<em>IEnergy Calculator Parameters</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getIEnergyCalculatorParameters()
		 * @generated
		 */
		EDataType IENERGY_CALCULATOR_PARAMETERS = eINSTANCE.getIEnergyCalculatorParameters();

		/**
		 * The meta object literal for the '<em>Efficiency</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.emf.util.Efficiency
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getEfficiency()
		 * @generated
		 */
		EDataType EFFICIENCY = eINSTANCE.getEfficiency();

		/**
		 * The meta object literal for the '<em>Heat Proportions</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uk.org.cse.nhm.hom.IHeatProportions
		 * @see uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl#getHeatProportions()
		 * @generated
		 */
		EDataType HEAT_PROPORTIONS = eINSTANCE.getHeatProportions();

	}

} //ITechnologiesPackage
