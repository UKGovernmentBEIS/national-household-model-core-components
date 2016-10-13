/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.IAdjuster;
import uk.org.cse.nhm.hom.emf.technologies.IAppliance;
import uk.org.cse.nhm.hom.emf.technologies.IBackBoiler;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ICooker;
import uk.org.cse.nhm.hom.emf.technologies.IElectricShower;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHybridHeater;
import uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IOperationalCost;
import uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic;
import uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TechnologiesFactoryImpl extends EFactoryImpl implements ITechnologiesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ITechnologiesFactory init() {
		try {
			ITechnologiesFactory theTechnologiesFactory = (ITechnologiesFactory)EPackage.Registry.INSTANCE.getEFactory(ITechnologiesPackage.eNS_URI);
			if (theTechnologiesFactory != null) {
				return theTechnologiesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TechnologiesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TechnologiesFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ITechnologiesPackage.TECHNOLOGY_MODEL: return createTechnologyModel();
			case ITechnologiesPackage.APPLIANCE: return createAppliance();
			case ITechnologiesPackage.LIGHT: return createLight();
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM: return createCentralWaterSystem();
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM: return createCentralHeatingSystem();
			case ITechnologiesPackage.MAIN_WATER_HEATER: return createMainWaterHeater();
			case ITechnologiesPackage.WATER_TANK: return createWaterTank();
			case ITechnologiesPackage.SOLAR_WATER_HEATER: return createSolarWaterHeater();
			case ITechnologiesPackage.IMMERSION_HEATER: return createImmersionHeater();
			case ITechnologiesPackage.COOKER: return createCooker();
			case ITechnologiesPackage.ELECTRIC_SHOWER: return createElectricShower();
			case ITechnologiesPackage.STORAGE_HEATER: return createStorageHeater();
			case ITechnologiesPackage.COMMUNITY_HEAT_SOURCE: return createCommunityHeatSource();
			case ITechnologiesPackage.COMMUNITY_CHP: return createCommunityCHP();
			case ITechnologiesPackage.ROOM_HEATER: return createRoomHeater();
			case ITechnologiesPackage.HEAT_PUMP: return createHeatPump();
			case ITechnologiesPackage.WARM_AIR_SYSTEM: return createWarmAirSystem();
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER: return createPointOfUseWaterHeater();
			case ITechnologiesPackage.HEAT_PUMP_WARM_AIR_SYSTEM: return createHeatPumpWarmAirSystem();
			case ITechnologiesPackage.WARM_AIR_CIRCULATOR: return createWarmAirCirculator();
			case ITechnologiesPackage.BACK_BOILER: return createBackBoiler();
			case ITechnologiesPackage.OPERATIONAL_COST: return createOperationalCost();
			case ITechnologiesPackage.SOLAR_PHOTOVOLTAIC: return createSolarPhotovoltaic();
			case ITechnologiesPackage.ADJUSTER: return createAdjuster();
			case ITechnologiesPackage.HYBRID_HEATER: return createHybridHeater();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ITechnologiesPackage.FUEL_TYPE:
				return createFuelTypeFromString(eDataType, initialValue);
			case ITechnologiesPackage.HEATING_SYSTEM_CONTROL_TYPE:
				return createHeatingSystemControlTypeFromString(eDataType, initialValue);
			case ITechnologiesPackage.EMITTER_TYPE:
				return createEmitterTypeFromString(eDataType, initialValue);
			case ITechnologiesPackage.STORAGE_HEATER_CONTROL_TYPE:
				return createStorageHeaterControlTypeFromString(eDataType, initialValue);
			case ITechnologiesPackage.STORAGE_HEATER_TYPE:
				return createStorageHeaterTypeFromString(eDataType, initialValue);
			case ITechnologiesPackage.HEAT_PUMP_SOURCE_TYPE:
				return createHeatPumpSourceTypeFromString(eDataType, initialValue);
			case ITechnologiesPackage.IENERGY_CALCULATOR_VISITOR:
				return createIEnergyCalculatorVisitorFromString(eDataType, initialValue);
			case ITechnologiesPackage.IINTERNAL_PARAMETERS:
				return createIInternalParametersFromString(eDataType, initialValue);
			case ITechnologiesPackage.IENERGY_STATE:
				return createIEnergyStateFromString(eDataType, initialValue);
			case ITechnologiesPackage.ENERGY_TYPE:
				return createEnergyTypeFromString(eDataType, initialValue);
			case ITechnologiesPackage.ATOMIC_INTEGER:
				return createAtomicIntegerFromString(eDataType, initialValue);
			case ITechnologiesPackage.ICONSTANTS:
				return createIConstantsFromString(eDataType, initialValue);
			case ITechnologiesPackage.IENERGY_CALCULATOR_PARAMETERS:
				return createIEnergyCalculatorParametersFromString(eDataType, initialValue);
			case ITechnologiesPackage.EFFICIENCY:
				return createEfficiencyFromString(eDataType, initialValue);
			case ITechnologiesPackage.HEAT_PROPORTIONS:
				return createHeatProportionsFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ITechnologiesPackage.FUEL_TYPE:
				return convertFuelTypeToString(eDataType, instanceValue);
			case ITechnologiesPackage.HEATING_SYSTEM_CONTROL_TYPE:
				return convertHeatingSystemControlTypeToString(eDataType, instanceValue);
			case ITechnologiesPackage.EMITTER_TYPE:
				return convertEmitterTypeToString(eDataType, instanceValue);
			case ITechnologiesPackage.STORAGE_HEATER_CONTROL_TYPE:
				return convertStorageHeaterControlTypeToString(eDataType, instanceValue);
			case ITechnologiesPackage.STORAGE_HEATER_TYPE:
				return convertStorageHeaterTypeToString(eDataType, instanceValue);
			case ITechnologiesPackage.HEAT_PUMP_SOURCE_TYPE:
				return convertHeatPumpSourceTypeToString(eDataType, instanceValue);
			case ITechnologiesPackage.IENERGY_CALCULATOR_VISITOR:
				return convertIEnergyCalculatorVisitorToString(eDataType, instanceValue);
			case ITechnologiesPackage.IINTERNAL_PARAMETERS:
				return convertIInternalParametersToString(eDataType, instanceValue);
			case ITechnologiesPackage.IENERGY_STATE:
				return convertIEnergyStateToString(eDataType, instanceValue);
			case ITechnologiesPackage.ENERGY_TYPE:
				return convertEnergyTypeToString(eDataType, instanceValue);
			case ITechnologiesPackage.ATOMIC_INTEGER:
				return convertAtomicIntegerToString(eDataType, instanceValue);
			case ITechnologiesPackage.ICONSTANTS:
				return convertIConstantsToString(eDataType, instanceValue);
			case ITechnologiesPackage.IENERGY_CALCULATOR_PARAMETERS:
				return convertIEnergyCalculatorParametersToString(eDataType, instanceValue);
			case ITechnologiesPackage.EFFICIENCY:
				return convertEfficiencyToString(eDataType, instanceValue);
			case ITechnologiesPackage.HEAT_PROPORTIONS:
				return convertHeatProportionsToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ITechnologyModel createTechnologyModel() {
		TechnologyModelImpl technologyModel = new TechnologyModelImpl();
		return technologyModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAppliance createAppliance() {
		ApplianceImpl appliance = new ApplianceImpl();
		return appliance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ILight createLight() {
		LightImpl light = new LightImpl();
		return light;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICentralWaterSystem createCentralWaterSystem() {
		CentralWaterSystemImpl centralWaterSystem = new CentralWaterSystemImpl();
		return centralWaterSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICentralHeatingSystem createCentralHeatingSystem() {
		CentralHeatingSystemImpl centralHeatingSystem = new CentralHeatingSystemImpl();
		return centralHeatingSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IMainWaterHeater createMainWaterHeater() {
		MainWaterHeaterImpl mainWaterHeater = new MainWaterHeaterImpl();
		return mainWaterHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IWaterTank createWaterTank() {
		WaterTankImpl waterTank = new WaterTankImpl();
		return waterTank;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISolarWaterHeater createSolarWaterHeater() {
		SolarWaterHeaterImpl solarWaterHeater = new SolarWaterHeaterImpl();
		return solarWaterHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IImmersionHeater createImmersionHeater() {
		ImmersionHeaterImpl immersionHeater = new ImmersionHeaterImpl();
		return immersionHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICooker createCooker() {
		CookerImpl cooker = new CookerImpl();
		return cooker;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IElectricShower createElectricShower() {
		ElectricShowerImpl electricShower = new ElectricShowerImpl();
		return electricShower;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IStorageHeater createStorageHeater() {
		StorageHeaterImpl storageHeater = new StorageHeaterImpl();
		return storageHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICommunityHeatSource createCommunityHeatSource() {
		CommunityHeatSourceImpl communityHeatSource = new CommunityHeatSourceImpl();
		return communityHeatSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICommunityCHP createCommunityCHP() {
		CommunityCHPImpl communityCHP = new CommunityCHPImpl();
		return communityCHP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IRoomHeater createRoomHeater() {
		RoomHeaterImpl roomHeater = new RoomHeaterImpl();
		return roomHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IHeatPump createHeatPump() {
		HeatPumpImpl heatPump = new HeatPumpImpl();
		return heatPump;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IWarmAirSystem createWarmAirSystem() {
		WarmAirSystemImpl warmAirSystem = new WarmAirSystemImpl();
		return warmAirSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IPointOfUseWaterHeater createPointOfUseWaterHeater() {
		PointOfUseWaterHeaterImpl pointOfUseWaterHeater = new PointOfUseWaterHeaterImpl();
		return pointOfUseWaterHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IHeatPumpWarmAirSystem createHeatPumpWarmAirSystem() {
		HeatPumpWarmAirSystemImpl heatPumpWarmAirSystem = new HeatPumpWarmAirSystemImpl();
		return heatPumpWarmAirSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IWarmAirCirculator createWarmAirCirculator() {
		WarmAirCirculatorImpl warmAirCirculator = new WarmAirCirculatorImpl();
		return warmAirCirculator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBackBoiler createBackBoiler() {
		BackBoilerImpl backBoiler = new BackBoilerImpl();
		return backBoiler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IOperationalCost createOperationalCost() {
		OperationalCostImpl operationalCost = new OperationalCostImpl();
		return operationalCost;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISolarPhotovoltaic createSolarPhotovoltaic() {
		SolarPhotovoltaicImpl solarPhotovoltaic = new SolarPhotovoltaicImpl();
		return solarPhotovoltaic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAdjuster createAdjuster() {
		AdjusterImpl adjuster = new AdjusterImpl();
		return adjuster;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IHybridHeater createHybridHeater() {
		HybridHeaterImpl hybridHeater = new HybridHeaterImpl();
		return hybridHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FuelType createFuelTypeFromString(EDataType eDataType, String initialValue) {
		FuelType result = FuelType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertFuelTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HeatingSystemControlType createHeatingSystemControlTypeFromString(EDataType eDataType, String initialValue) {
		HeatingSystemControlType result = HeatingSystemControlType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHeatingSystemControlTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmitterType createEmitterTypeFromString(EDataType eDataType, String initialValue) {
		EmitterType result = EmitterType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEmitterTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StorageHeaterControlType createStorageHeaterControlTypeFromString(EDataType eDataType, String initialValue) {
		StorageHeaterControlType result = StorageHeaterControlType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStorageHeaterControlTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StorageHeaterType createStorageHeaterTypeFromString(EDataType eDataType, String initialValue) {
		StorageHeaterType result = StorageHeaterType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStorageHeaterTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HeatPumpSourceType createHeatPumpSourceTypeFromString(EDataType eDataType, String initialValue) {
		HeatPumpSourceType result = HeatPumpSourceType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHeatPumpSourceTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IEnergyCalculatorVisitor createIEnergyCalculatorVisitorFromString(EDataType eDataType, String initialValue) {
		return (IEnergyCalculatorVisitor)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIEnergyCalculatorVisitorToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IInternalParameters createIInternalParametersFromString(EDataType eDataType, String initialValue) {
		return (IInternalParameters)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIInternalParametersToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IEnergyState createIEnergyStateFromString(EDataType eDataType, String initialValue) {
		return (IEnergyState)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIEnergyStateToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnergyType createEnergyTypeFromString(EDataType eDataType, String initialValue) {
		return (EnergyType)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEnergyTypeToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AtomicInteger createAtomicIntegerFromString(EDataType eDataType, String initialValue) {
		return (AtomicInteger)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAtomicIntegerToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IConstants createIConstantsFromString(EDataType eDataType, String initialValue) {
		return (IConstants)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIConstantsToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IEnergyCalculatorParameters createIEnergyCalculatorParametersFromString(EDataType eDataType, String initialValue) {
		return (IEnergyCalculatorParameters)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIEnergyCalculatorParametersToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NO
	 */
	public Efficiency createEfficiencyFromString(EDataType eDataType, String initialValue) {
		return Efficiency.fromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NO
	 */
	public String convertEfficiencyToString(EDataType eDataType, Object instanceValue) {
		return ((Efficiency) instanceValue).asString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IHeatProportions createHeatProportionsFromString(EDataType eDataType, String initialValue) {
		return (IHeatProportions)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertHeatProportionsToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ITechnologiesPackage getTechnologiesPackage() {
		return (ITechnologiesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ITechnologiesPackage getPackage() {
		return ITechnologiesPackage.eINSTANCE;
	}

} //TechnologiesFactoryImpl
