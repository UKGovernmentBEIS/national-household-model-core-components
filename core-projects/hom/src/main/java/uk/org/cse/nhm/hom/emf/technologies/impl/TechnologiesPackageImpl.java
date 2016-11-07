/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

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
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ICooker;
import uk.org.cse.nhm.hom.emf.technologies.IElectricShower;
import uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue;
import uk.org.cse.nhm.hom.emf.technologies.IHasInstallationYear;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IHybridHeater;
import uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater;
import uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IMixerShower;
import uk.org.cse.nhm.hom.emf.technologies.INamed;
import uk.org.cse.nhm.hom.emf.technologies.IOperationalCost;
import uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IPrimarySpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.IShower;
import uk.org.cse.nhm.hom.emf.technologies.ISolarPhotovoltaic;
import uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStoreContainer;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.IWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TechnologiesPackageImpl extends EPackageImpl implements ITechnologiesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass technologyModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applianceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lightEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass spaceHeaterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass waterHeaterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass heatSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass namedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass centralWaterSystemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass centralHeatingSystemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass centralWaterHeaterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mainWaterHeaterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass waterTankEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass visitorAccepterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass solarWaterHeaterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass storeContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass immersionHeaterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cookerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass storageHeaterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communityHeatSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass communityCHPEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass roomHeaterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass heatPumpEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass warmAirSystemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pointOfUseWaterHeaterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass heatPumpWarmAirSystemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass warmAirCirculatorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass primarySpaceHeaterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass backBoilerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fuelAndFlueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass individualHeatSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass operationalCostEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass solarPhotovoltaicEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass adjusterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hybridHeaterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hasInstallationYearEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass showerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mixerShowerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass electricShowerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum fuelTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum heatingSystemControlTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum emitterTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum storageHeaterControlTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum storageHeaterTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum heatPumpSourceTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iEnergyCalculatorVisitorEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iInternalParametersEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iEnergyStateEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType energyTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType atomicIntegerEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iConstantsEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iEnergyCalculatorParametersEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType efficiencyEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType heatProportionsEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TechnologiesPackageImpl() {
		super(eNS_URI, ITechnologiesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ITechnologiesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ITechnologiesPackage init() {
		if (isInited) return (ITechnologiesPackage)EPackage.Registry.INSTANCE.getEPackage(ITechnologiesPackage.eNS_URI);

		// Obtain or create and register package
		TechnologiesPackageImpl theTechnologiesPackage = (TechnologiesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TechnologiesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TechnologiesPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		BoilersPackageImpl theBoilersPackage = (BoilersPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(IBoilersPackage.eNS_URI) instanceof BoilersPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(IBoilersPackage.eNS_URI) : IBoilersPackage.eINSTANCE);

		// Create package meta-data objects
		theTechnologiesPackage.createPackageContents();
		theBoilersPackage.createPackageContents();

		// Initialize created meta-data
		theTechnologiesPackage.initializePackageContents();
		theBoilersPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTechnologiesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ITechnologiesPackage.eNS_URI, theTechnologiesPackage);
		return theTechnologiesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTechnologyModel() {
		return technologyModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_Appliances() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_Lights() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_IndividualHeatSource() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_Cookers() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_PrimarySpaceHeater() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_SecondarySpaceHeater() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_CentralWaterSystem() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_SecondaryWaterHeater() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_CommunityHeatSource() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_SolarPhotovoltaic() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_Adjusters() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTechnologyModel_Shower() {
		return (EReference)technologyModelEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAppliance() {
		return applianceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAppliance_Efficiency() {
		return (EAttribute)applianceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLight() {
		return lightEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLight_Efficiency() {
		return (EAttribute)lightEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLight_Proportion() {
		return (EAttribute)lightEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSpaceHeater() {
		return spaceHeaterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWaterHeater() {
		return waterHeaterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHeatSource() {
		return heatSourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHeatSource_WaterHeater() {
		return (EReference)heatSourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHeatSource_SpaceHeater() {
		return (EReference)heatSourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNamed() {
		return namedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNamed_Name() {
		return (EAttribute)namedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCentralWaterSystem() {
		return centralWaterSystemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCentralWaterSystem_StoreInPrimaryCircuit() {
		return (EAttribute)centralWaterSystemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCentralWaterSystem_PrimaryPipeworkInsulated() {
		return (EAttribute)centralWaterSystemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCentralWaterSystem_SeparatelyTimeControlled() {
		return (EAttribute)centralWaterSystemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCentralWaterSystem_SolarWaterHeater() {
		return (EReference)centralWaterSystemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCentralWaterSystem_PrimaryWaterHeater() {
		return (EReference)centralWaterSystemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCentralWaterSystem_SecondaryWaterHeater() {
		return (EReference)centralWaterSystemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCentralWaterSystem_TechnologyModel() {
		return (EReference)centralWaterSystemEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCentralHeatingSystem() {
		return centralHeatingSystemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCentralHeatingSystem_HeatSource() {
		return (EReference)centralHeatingSystemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCentralHeatingSystem_Controls() {
		return (EAttribute)centralHeatingSystemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCentralHeatingSystem_EmitterType() {
		return (EAttribute)centralHeatingSystemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCentralWaterHeater() {
		return centralWaterHeaterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMainWaterHeater() {
		return mainWaterHeaterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMainWaterHeater_HeatSource() {
		return (EReference)mainWaterHeaterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWaterTank() {
		return waterTankEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWaterTank_Insulation() {
		return (EAttribute)waterTankEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWaterTank_FactoryInsulation() {
		return (EAttribute)waterTankEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWaterTank_Volume() {
		return (EAttribute)waterTankEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWaterTank_ThermostatFitted() {
		return (EAttribute)waterTankEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWaterTank_SolarStorageVolume() {
		return (EAttribute)waterTankEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWaterTank_ImmersionHeater() {
		return (EReference)waterTankEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVisitorAccepter() {
		return visitorAccepterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSolarWaterHeater() {
		return solarWaterHeaterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSolarWaterHeater_Pitch() {
		return (EAttribute)solarWaterHeaterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSolarWaterHeater_Orientation() {
		return (EAttribute)solarWaterHeaterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSolarWaterHeater_Area() {
		return (EAttribute)solarWaterHeaterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSolarWaterHeater_UsefulAreaRatio() {
		return (EAttribute)solarWaterHeaterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSolarWaterHeater_ZeroLossEfficiency() {
		return (EAttribute)solarWaterHeaterEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSolarWaterHeater_LinearHeatLossCoefficient() {
		return (EAttribute)solarWaterHeaterEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSolarWaterHeater_PreHeatTankVolume() {
		return (EAttribute)solarWaterHeaterEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSolarWaterHeater_PumpPhotovolatic() {
		return (EAttribute)solarWaterHeaterEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStoreContainer() {
		return storeContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStoreContainer_Store() {
		return (EReference)storeContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImmersionHeater() {
		return immersionHeaterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImmersionHeater_DualCoil() {
		return (EAttribute)immersionHeaterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCooker() {
		return cookerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCooker_HobBaseLoad() {
		return (EAttribute)cookerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCooker_HobOccupancyFactor() {
		return (EAttribute)cookerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCooker_HobFuelType() {
		return (EAttribute)cookerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCooker_OvenBaseLoad() {
		return (EAttribute)cookerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCooker_OvenOccupancyFactor() {
		return (EAttribute)cookerEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCooker_OvenFuelType() {
		return (EAttribute)cookerEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCooker_GainsFactor() {
		return (EAttribute)cookerEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStorageHeater() {
		return storageHeaterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStorageHeater_ResponsivenessOverride() {
		return (EAttribute)storageHeaterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStorageHeater_ControlType() {
		return (EAttribute)storageHeaterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStorageHeater_Type() {
		return (EAttribute)storageHeaterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommunityHeatSource() {
		return communityHeatSourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommunityHeatSource_ChargingUsageBased() {
		return (EAttribute)communityHeatSourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommunityHeatSource_HeatEfficiency() {
		return (EAttribute)communityHeatSourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCommunityCHP() {
		return communityCHPEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCommunityCHP_ElectricalEfficiency() {
		return (EAttribute)communityCHPEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRoomHeater() {
		return roomHeaterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoomHeater_Efficiency() {
		return (EAttribute)roomHeaterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoomHeater_ThermostatFitted() {
		return (EAttribute)roomHeaterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHeatPump() {
		return heatPumpEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeatPump_SourceType() {
		return (EAttribute)heatPumpEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeatPump_CoefficientOfPerformance() {
		return (EAttribute)heatPumpEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeatPump_WeatherCompensated() {
		return (EAttribute)heatPumpEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeatPump_AuxiliaryPresent() {
		return (EAttribute)heatPumpEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHeatPump_Hybrid() {
		return (EReference)heatPumpEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWarmAirSystem() {
		return warmAirSystemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWarmAirSystem_FuelType() {
		return (EAttribute)warmAirSystemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWarmAirSystem_Efficiency() {
		return (EAttribute)warmAirSystemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWarmAirSystem_Circulator() {
		return (EReference)warmAirSystemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWarmAirSystem_Controls() {
		return (EAttribute)warmAirSystemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPointOfUseWaterHeater() {
		return pointOfUseWaterHeaterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPointOfUseWaterHeater_FuelType() {
		return (EAttribute)pointOfUseWaterHeaterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPointOfUseWaterHeater_Multipoint() {
		return (EAttribute)pointOfUseWaterHeaterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPointOfUseWaterHeater_Efficiency() {
		return (EAttribute)pointOfUseWaterHeaterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHeatPumpWarmAirSystem() {
		return heatPumpWarmAirSystemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeatPumpWarmAirSystem_SourceType() {
		return (EAttribute)heatPumpWarmAirSystemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHeatPumpWarmAirSystem_AuxiliaryPresent() {
		return (EAttribute)heatPumpWarmAirSystemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWarmAirCirculator() {
		return warmAirCirculatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getWarmAirCirculator_WarmAirSystem() {
		return (EReference)warmAirCirculatorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPrimarySpaceHeater() {
		return primarySpaceHeaterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBackBoiler() {
		return backBoilerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFuelAndFlue() {
		return fuelAndFlueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFuelAndFlue_Fuel() {
		return (EAttribute)fuelAndFlueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFuelAndFlue_FlueType() {
		return (EAttribute)fuelAndFlueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIndividualHeatSource() {
		return individualHeatSourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOperationalCost() {
		return operationalCostEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOperationalCost_AnnualOperationalCost() {
		return (EAttribute)operationalCostEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSolarPhotovoltaic() {
		return solarPhotovoltaicEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSolarPhotovoltaic_PeakPower() {
		return (EAttribute)solarPhotovoltaicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSolarPhotovoltaic_OwnUseProportion() {
		return (EAttribute)solarPhotovoltaicEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdjuster() {
		return adjusterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdjuster_FuelTypes() {
		return (EAttribute)adjusterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdjuster_Deltas() {
		return (EAttribute)adjusterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdjuster_Gains() {
		return (EAttribute)adjusterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHybridHeater() {
		return hybridHeaterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHybridHeater_Fuel() {
		return (EAttribute)hybridHeaterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHybridHeater_Efficiency() {
		return (EAttribute)hybridHeaterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHybridHeater_Fraction() {
		return (EAttribute)hybridHeaterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHasInstallationYear() {
		return hasInstallationYearEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHasInstallationYear_InstallationYear() {
		return (EAttribute)hasInstallationYearEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getShower() {
		return showerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getShower_TechnologyModel() {
		return (EReference)showerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMixerShower() {
		return mixerShowerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElectricShower() {
		return electricShowerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getFuelType() {
		return fuelTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getHeatingSystemControlType() {
		return heatingSystemControlTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEmitterType() {
		return emitterTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getStorageHeaterControlType() {
		return storageHeaterControlTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getStorageHeaterType() {
		return storageHeaterTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getHeatPumpSourceType() {
		return heatPumpSourceTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getIEnergyCalculatorVisitor() {
		return iEnergyCalculatorVisitorEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getIInternalParameters() {
		return iInternalParametersEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getIEnergyState() {
		return iEnergyStateEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getEnergyType() {
		return energyTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getAtomicInteger() {
		return atomicIntegerEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getIConstants() {
		return iConstantsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getIEnergyCalculatorParameters() {
		return iEnergyCalculatorParametersEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getEfficiency() {
		return efficiencyEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getHeatProportions() {
		return heatProportionsEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ITechnologiesFactory getTechnologiesFactory() {
		return (ITechnologiesFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		technologyModelEClass = createEClass(TECHNOLOGY_MODEL);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__APPLIANCES);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__LIGHTS);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__INDIVIDUAL_HEAT_SOURCE);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__COOKERS);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__PRIMARY_SPACE_HEATER);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__SECONDARY_SPACE_HEATER);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__CENTRAL_WATER_SYSTEM);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__SECONDARY_WATER_HEATER);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__COMMUNITY_HEAT_SOURCE);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__SOLAR_PHOTOVOLTAIC);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__ADJUSTERS);
		createEReference(technologyModelEClass, TECHNOLOGY_MODEL__SHOWER);

		applianceEClass = createEClass(APPLIANCE);
		createEAttribute(applianceEClass, APPLIANCE__EFFICIENCY);

		lightEClass = createEClass(LIGHT);
		createEAttribute(lightEClass, LIGHT__EFFICIENCY);
		createEAttribute(lightEClass, LIGHT__PROPORTION);

		spaceHeaterEClass = createEClass(SPACE_HEATER);

		waterHeaterEClass = createEClass(WATER_HEATER);

		heatSourceEClass = createEClass(HEAT_SOURCE);
		createEReference(heatSourceEClass, HEAT_SOURCE__WATER_HEATER);
		createEReference(heatSourceEClass, HEAT_SOURCE__SPACE_HEATER);

		namedEClass = createEClass(NAMED);
		createEAttribute(namedEClass, NAMED__NAME);

		centralWaterSystemEClass = createEClass(CENTRAL_WATER_SYSTEM);
		createEAttribute(centralWaterSystemEClass, CENTRAL_WATER_SYSTEM__STORE_IN_PRIMARY_CIRCUIT);
		createEAttribute(centralWaterSystemEClass, CENTRAL_WATER_SYSTEM__PRIMARY_PIPEWORK_INSULATED);
		createEAttribute(centralWaterSystemEClass, CENTRAL_WATER_SYSTEM__SEPARATELY_TIME_CONTROLLED);
		createEReference(centralWaterSystemEClass, CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER);
		createEReference(centralWaterSystemEClass, CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER);
		createEReference(centralWaterSystemEClass, CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER);
		createEReference(centralWaterSystemEClass, CENTRAL_WATER_SYSTEM__TECHNOLOGY_MODEL);

		centralHeatingSystemEClass = createEClass(CENTRAL_HEATING_SYSTEM);
		createEReference(centralHeatingSystemEClass, CENTRAL_HEATING_SYSTEM__HEAT_SOURCE);
		createEAttribute(centralHeatingSystemEClass, CENTRAL_HEATING_SYSTEM__CONTROLS);
		createEAttribute(centralHeatingSystemEClass, CENTRAL_HEATING_SYSTEM__EMITTER_TYPE);

		centralWaterHeaterEClass = createEClass(CENTRAL_WATER_HEATER);

		mainWaterHeaterEClass = createEClass(MAIN_WATER_HEATER);
		createEReference(mainWaterHeaterEClass, MAIN_WATER_HEATER__HEAT_SOURCE);

		waterTankEClass = createEClass(WATER_TANK);
		createEAttribute(waterTankEClass, WATER_TANK__INSULATION);
		createEAttribute(waterTankEClass, WATER_TANK__FACTORY_INSULATION);
		createEAttribute(waterTankEClass, WATER_TANK__VOLUME);
		createEAttribute(waterTankEClass, WATER_TANK__THERMOSTAT_FITTED);
		createEAttribute(waterTankEClass, WATER_TANK__SOLAR_STORAGE_VOLUME);
		createEReference(waterTankEClass, WATER_TANK__IMMERSION_HEATER);

		visitorAccepterEClass = createEClass(VISITOR_ACCEPTER);

		solarWaterHeaterEClass = createEClass(SOLAR_WATER_HEATER);
		createEAttribute(solarWaterHeaterEClass, SOLAR_WATER_HEATER__PITCH);
		createEAttribute(solarWaterHeaterEClass, SOLAR_WATER_HEATER__ORIENTATION);
		createEAttribute(solarWaterHeaterEClass, SOLAR_WATER_HEATER__AREA);
		createEAttribute(solarWaterHeaterEClass, SOLAR_WATER_HEATER__USEFUL_AREA_RATIO);
		createEAttribute(solarWaterHeaterEClass, SOLAR_WATER_HEATER__ZERO_LOSS_EFFICIENCY);
		createEAttribute(solarWaterHeaterEClass, SOLAR_WATER_HEATER__LINEAR_HEAT_LOSS_COEFFICIENT);
		createEAttribute(solarWaterHeaterEClass, SOLAR_WATER_HEATER__PRE_HEAT_TANK_VOLUME);
		createEAttribute(solarWaterHeaterEClass, SOLAR_WATER_HEATER__PUMP_PHOTOVOLATIC);

		storeContainerEClass = createEClass(STORE_CONTAINER);
		createEReference(storeContainerEClass, STORE_CONTAINER__STORE);

		immersionHeaterEClass = createEClass(IMMERSION_HEATER);
		createEAttribute(immersionHeaterEClass, IMMERSION_HEATER__DUAL_COIL);

		cookerEClass = createEClass(COOKER);
		createEAttribute(cookerEClass, COOKER__HOB_BASE_LOAD);
		createEAttribute(cookerEClass, COOKER__HOB_OCCUPANCY_FACTOR);
		createEAttribute(cookerEClass, COOKER__HOB_FUEL_TYPE);
		createEAttribute(cookerEClass, COOKER__OVEN_BASE_LOAD);
		createEAttribute(cookerEClass, COOKER__OVEN_OCCUPANCY_FACTOR);
		createEAttribute(cookerEClass, COOKER__OVEN_FUEL_TYPE);
		createEAttribute(cookerEClass, COOKER__GAINS_FACTOR);

		storageHeaterEClass = createEClass(STORAGE_HEATER);
		createEAttribute(storageHeaterEClass, STORAGE_HEATER__RESPONSIVENESS_OVERRIDE);
		createEAttribute(storageHeaterEClass, STORAGE_HEATER__CONTROL_TYPE);
		createEAttribute(storageHeaterEClass, STORAGE_HEATER__TYPE);

		communityHeatSourceEClass = createEClass(COMMUNITY_HEAT_SOURCE);
		createEAttribute(communityHeatSourceEClass, COMMUNITY_HEAT_SOURCE__CHARGING_USAGE_BASED);
		createEAttribute(communityHeatSourceEClass, COMMUNITY_HEAT_SOURCE__HEAT_EFFICIENCY);

		communityCHPEClass = createEClass(COMMUNITY_CHP);
		createEAttribute(communityCHPEClass, COMMUNITY_CHP__ELECTRICAL_EFFICIENCY);

		roomHeaterEClass = createEClass(ROOM_HEATER);
		createEAttribute(roomHeaterEClass, ROOM_HEATER__EFFICIENCY);
		createEAttribute(roomHeaterEClass, ROOM_HEATER__THERMOSTAT_FITTED);

		heatPumpEClass = createEClass(HEAT_PUMP);
		createEAttribute(heatPumpEClass, HEAT_PUMP__SOURCE_TYPE);
		createEAttribute(heatPumpEClass, HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE);
		createEAttribute(heatPumpEClass, HEAT_PUMP__WEATHER_COMPENSATED);
		createEAttribute(heatPumpEClass, HEAT_PUMP__AUXILIARY_PRESENT);
		createEReference(heatPumpEClass, HEAT_PUMP__HYBRID);

		warmAirSystemEClass = createEClass(WARM_AIR_SYSTEM);
		createEAttribute(warmAirSystemEClass, WARM_AIR_SYSTEM__FUEL_TYPE);
		createEAttribute(warmAirSystemEClass, WARM_AIR_SYSTEM__EFFICIENCY);
		createEReference(warmAirSystemEClass, WARM_AIR_SYSTEM__CIRCULATOR);
		createEAttribute(warmAirSystemEClass, WARM_AIR_SYSTEM__CONTROLS);

		pointOfUseWaterHeaterEClass = createEClass(POINT_OF_USE_WATER_HEATER);
		createEAttribute(pointOfUseWaterHeaterEClass, POINT_OF_USE_WATER_HEATER__FUEL_TYPE);
		createEAttribute(pointOfUseWaterHeaterEClass, POINT_OF_USE_WATER_HEATER__MULTIPOINT);
		createEAttribute(pointOfUseWaterHeaterEClass, POINT_OF_USE_WATER_HEATER__EFFICIENCY);

		heatPumpWarmAirSystemEClass = createEClass(HEAT_PUMP_WARM_AIR_SYSTEM);
		createEAttribute(heatPumpWarmAirSystemEClass, HEAT_PUMP_WARM_AIR_SYSTEM__SOURCE_TYPE);
		createEAttribute(heatPumpWarmAirSystemEClass, HEAT_PUMP_WARM_AIR_SYSTEM__AUXILIARY_PRESENT);

		warmAirCirculatorEClass = createEClass(WARM_AIR_CIRCULATOR);
		createEReference(warmAirCirculatorEClass, WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM);

		primarySpaceHeaterEClass = createEClass(PRIMARY_SPACE_HEATER);

		backBoilerEClass = createEClass(BACK_BOILER);

		fuelAndFlueEClass = createEClass(FUEL_AND_FLUE);
		createEAttribute(fuelAndFlueEClass, FUEL_AND_FLUE__FUEL);
		createEAttribute(fuelAndFlueEClass, FUEL_AND_FLUE__FLUE_TYPE);

		individualHeatSourceEClass = createEClass(INDIVIDUAL_HEAT_SOURCE);

		operationalCostEClass = createEClass(OPERATIONAL_COST);
		createEAttribute(operationalCostEClass, OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST);

		solarPhotovoltaicEClass = createEClass(SOLAR_PHOTOVOLTAIC);
		createEAttribute(solarPhotovoltaicEClass, SOLAR_PHOTOVOLTAIC__PEAK_POWER);
		createEAttribute(solarPhotovoltaicEClass, SOLAR_PHOTOVOLTAIC__OWN_USE_PROPORTION);

		adjusterEClass = createEClass(ADJUSTER);
		createEAttribute(adjusterEClass, ADJUSTER__FUEL_TYPES);
		createEAttribute(adjusterEClass, ADJUSTER__DELTAS);
		createEAttribute(adjusterEClass, ADJUSTER__GAINS);

		hybridHeaterEClass = createEClass(HYBRID_HEATER);
		createEAttribute(hybridHeaterEClass, HYBRID_HEATER__FUEL);
		createEAttribute(hybridHeaterEClass, HYBRID_HEATER__EFFICIENCY);
		createEAttribute(hybridHeaterEClass, HYBRID_HEATER__FRACTION);

		hasInstallationYearEClass = createEClass(HAS_INSTALLATION_YEAR);
		createEAttribute(hasInstallationYearEClass, HAS_INSTALLATION_YEAR__INSTALLATION_YEAR);

		showerEClass = createEClass(SHOWER);
		createEReference(showerEClass, SHOWER__TECHNOLOGY_MODEL);

		mixerShowerEClass = createEClass(MIXER_SHOWER);

		electricShowerEClass = createEClass(ELECTRIC_SHOWER);

		// Create enums
		fuelTypeEEnum = createEEnum(FUEL_TYPE);
		heatingSystemControlTypeEEnum = createEEnum(HEATING_SYSTEM_CONTROL_TYPE);
		emitterTypeEEnum = createEEnum(EMITTER_TYPE);
		storageHeaterControlTypeEEnum = createEEnum(STORAGE_HEATER_CONTROL_TYPE);
		storageHeaterTypeEEnum = createEEnum(STORAGE_HEATER_TYPE);
		heatPumpSourceTypeEEnum = createEEnum(HEAT_PUMP_SOURCE_TYPE);

		// Create data types
		iEnergyCalculatorVisitorEDataType = createEDataType(IENERGY_CALCULATOR_VISITOR);
		iInternalParametersEDataType = createEDataType(IINTERNAL_PARAMETERS);
		iEnergyStateEDataType = createEDataType(IENERGY_STATE);
		energyTypeEDataType = createEDataType(ENERGY_TYPE);
		atomicIntegerEDataType = createEDataType(ATOMIC_INTEGER);
		iConstantsEDataType = createEDataType(ICONSTANTS);
		iEnergyCalculatorParametersEDataType = createEDataType(IENERGY_CALCULATOR_PARAMETERS);
		efficiencyEDataType = createEDataType(EFFICIENCY);
		heatProportionsEDataType = createEDataType(HEAT_PROPORTIONS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		IBoilersPackage theBoilersPackage = (IBoilersPackage)EPackage.Registry.INSTANCE.getEPackage(IBoilersPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theBoilersPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		technologyModelEClass.getESuperTypes().add(this.getVisitorAccepter());
		applianceEClass.getESuperTypes().add(this.getNamed());
		applianceEClass.getESuperTypes().add(this.getVisitorAccepter());
		lightEClass.getESuperTypes().add(this.getNamed());
		lightEClass.getESuperTypes().add(this.getVisitorAccepter());
		heatSourceEClass.getESuperTypes().add(this.getFuelAndFlue());
		heatSourceEClass.getESuperTypes().add(this.getOperationalCost());
		heatSourceEClass.getESuperTypes().add(this.getHasInstallationYear());
		centralWaterSystemEClass.getESuperTypes().add(this.getWaterHeater());
		centralWaterSystemEClass.getESuperTypes().add(this.getVisitorAccepter());
		centralWaterSystemEClass.getESuperTypes().add(this.getStoreContainer());
		centralHeatingSystemEClass.getESuperTypes().add(this.getPrimarySpaceHeater());
		centralHeatingSystemEClass.getESuperTypes().add(this.getVisitorAccepter());
		mainWaterHeaterEClass.getESuperTypes().add(this.getCentralWaterHeater());
		solarWaterHeaterEClass.getESuperTypes().add(this.getCentralWaterHeater());
		solarWaterHeaterEClass.getESuperTypes().add(this.getVisitorAccepter());
		solarWaterHeaterEClass.getESuperTypes().add(this.getOperationalCost());
		immersionHeaterEClass.getESuperTypes().add(this.getCentralWaterHeater());
		cookerEClass.getESuperTypes().add(this.getVisitorAccepter());
		storageHeaterEClass.getESuperTypes().add(this.getPrimarySpaceHeater());
		storageHeaterEClass.getESuperTypes().add(this.getVisitorAccepter());
		storageHeaterEClass.getESuperTypes().add(this.getOperationalCost());
		communityHeatSourceEClass.getESuperTypes().add(this.getHeatSource());
		communityCHPEClass.getESuperTypes().add(this.getCommunityHeatSource());
		roomHeaterEClass.getESuperTypes().add(this.getSpaceHeater());
		roomHeaterEClass.getESuperTypes().add(this.getVisitorAccepter());
		roomHeaterEClass.getESuperTypes().add(this.getFuelAndFlue());
		heatPumpEClass.getESuperTypes().add(this.getIndividualHeatSource());
		heatPumpEClass.getESuperTypes().add(this.getVisitorAccepter());
		warmAirSystemEClass.getESuperTypes().add(this.getPrimarySpaceHeater());
		warmAirSystemEClass.getESuperTypes().add(this.getVisitorAccepter());
		pointOfUseWaterHeaterEClass.getESuperTypes().add(this.getWaterHeater());
		pointOfUseWaterHeaterEClass.getESuperTypes().add(this.getVisitorAccepter());
		heatPumpWarmAirSystemEClass.getESuperTypes().add(this.getWarmAirSystem());
		warmAirCirculatorEClass.getESuperTypes().add(this.getCentralWaterHeater());
		primarySpaceHeaterEClass.getESuperTypes().add(this.getSpaceHeater());
		backBoilerEClass.getESuperTypes().add(theBoilersPackage.getBoiler());
		backBoilerEClass.getESuperTypes().add(this.getRoomHeater());
		individualHeatSourceEClass.getESuperTypes().add(this.getHeatSource());
		solarPhotovoltaicEClass.getESuperTypes().add(this.getVisitorAccepter());
		adjusterEClass.getESuperTypes().add(this.getNamed());
		adjusterEClass.getESuperTypes().add(this.getVisitorAccepter());
		showerEClass.getESuperTypes().add(this.getVisitorAccepter());
		mixerShowerEClass.getESuperTypes().add(this.getShower());
		electricShowerEClass.getESuperTypes().add(this.getShower());

		// Initialize classes and features; add operations and parameters
		initEClass(technologyModelEClass, ITechnologyModel.class, "TechnologyModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTechnologyModel_Appliances(), this.getAppliance(), null, "appliances", null, 0, -1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTechnologyModel_Lights(), this.getLight(), null, "lights", null, 0, -1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTechnologyModel_IndividualHeatSource(), this.getIndividualHeatSource(), null, "individualHeatSource", null, 0, 1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTechnologyModel_Cookers(), this.getCooker(), null, "cookers", null, 0, -1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTechnologyModel_PrimarySpaceHeater(), this.getPrimarySpaceHeater(), null, "primarySpaceHeater", null, 0, 1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTechnologyModel_SecondarySpaceHeater(), this.getRoomHeater(), null, "secondarySpaceHeater", null, 0, 1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTechnologyModel_CentralWaterSystem(), this.getCentralWaterSystem(), this.getCentralWaterSystem_TechnologyModel(), "centralWaterSystem", null, 0, 1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTechnologyModel_SecondaryWaterHeater(), this.getWaterHeater(), null, "secondaryWaterHeater", null, 0, 1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTechnologyModel_CommunityHeatSource(), this.getCommunityHeatSource(), null, "communityHeatSource", null, 0, 1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTechnologyModel_SolarPhotovoltaic(), this.getSolarPhotovoltaic(), null, "solarPhotovoltaic", null, 0, 1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTechnologyModel_Adjusters(), this.getAdjuster(), null, "adjusters", null, 0, -1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTechnologyModel_Shower(), this.getShower(), this.getShower_TechnologyModel(), "shower", null, 0, 1, ITechnologyModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(technologyModelEClass, ecorePackage.getEDouble(), "getTotalOperationalCost", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(applianceEClass, IAppliance.class, "Appliance", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAppliance_Efficiency(), ecorePackage.getEDouble(), "efficiency", null, 1, 1, IAppliance.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(lightEClass, ILight.class, "Light", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLight_Efficiency(), ecorePackage.getEDouble(), "efficiency", null, 1, 1, ILight.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLight_Proportion(), ecorePackage.getEDouble(), "proportion", null, 1, 1, ILight.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(spaceHeaterEClass, ISpaceHeater.class, "SpaceHeater", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(spaceHeaterEClass, ecorePackage.getEBoolean(), "isBroken", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(waterHeaterEClass, IWaterHeater.class, "WaterHeater", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(waterHeaterEClass, ecorePackage.getEBoolean(), "isBroken", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(heatSourceEClass, IHeatSource.class, "HeatSource", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHeatSource_WaterHeater(), this.getMainWaterHeater(), this.getMainWaterHeater_HeatSource(), "waterHeater", null, 0, 1, IHeatSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHeatSource_SpaceHeater(), this.getCentralHeatingSystem(), this.getCentralHeatingSystem_HeatSource(), "spaceHeater", null, 0, 1, IHeatSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(heatSourceEClass, null, "acceptFromHeating", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIConstants(), "constants", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIEnergyCalculatorParameters(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIEnergyCalculatorVisitor(), "visitor", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDouble(), "proportion", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "priority", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(heatSourceEClass, ecorePackage.getEDouble(), "getResponsiveness", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIConstants(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getHeatingSystemControlType(), "controls", 0, -1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getEmitterType(), "emitterType", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(heatSourceEClass, ecorePackage.getEDouble(), "getDemandTemperatureAdjustment", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIInternalParameters(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getHeatingSystemControlType(), "controlTypes", 0, -1, IS_UNIQUE, !IS_ORDERED);

		op = addEOperation(heatSourceEClass, ecorePackage.getEDouble(), "generateHotWaterAndPrimaryGains", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIInternalParameters(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIEnergyState(), "state", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getWaterTank(), "store", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "storeIsPrimary", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDouble(), "primaryCorrectionFactor", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDouble(), "distributionLossFactor", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDouble(), "proportion", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(heatSourceEClass, null, "generateHotWaterSystemGains", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIInternalParameters(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIEnergyState(), "state", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getWaterTank(), "store", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "storeIsPrimary", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDouble(), "systemLosses", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(heatSourceEClass, ecorePackage.getEDouble(), "getStorageTemperatureFactor", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIInternalParameters(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getWaterTank(), "store", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "storeInPrimaryCircuit", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(heatSourceEClass, ecorePackage.getEDouble(), "getContainedTankLosses", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIInternalParameters(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(heatSourceEClass, ecorePackage.getEBoolean(), "isCommunityHeating", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(namedEClass, INamed.class, "Named", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamed_Name(), ecorePackage.getEString(), "name", null, 1, 1, INamed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(centralWaterSystemEClass, ICentralWaterSystem.class, "CentralWaterSystem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCentralWaterSystem_StoreInPrimaryCircuit(), ecorePackage.getEBoolean(), "storeInPrimaryCircuit", null, 1, 1, ICentralWaterSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCentralWaterSystem_PrimaryPipeworkInsulated(), ecorePackage.getEBoolean(), "primaryPipeworkInsulated", null, 1, 1, ICentralWaterSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCentralWaterSystem_SeparatelyTimeControlled(), ecorePackage.getEBoolean(), "separatelyTimeControlled", null, 1, 1, ICentralWaterSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCentralWaterSystem_SolarWaterHeater(), this.getSolarWaterHeater(), null, "solarWaterHeater", null, 0, 1, ICentralWaterSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCentralWaterSystem_PrimaryWaterHeater(), this.getCentralWaterHeater(), null, "primaryWaterHeater", null, 1, 1, ICentralWaterSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCentralWaterSystem_SecondaryWaterHeater(), this.getCentralWaterHeater(), null, "secondaryWaterHeater", null, 0, 1, ICentralWaterSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCentralWaterSystem_TechnologyModel(), this.getTechnologyModel(), this.getTechnologyModel_CentralWaterSystem(), "technologyModel", null, 0, 1, ICentralWaterSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(centralWaterSystemEClass, ecorePackage.getEBoolean(), "isBroken", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(centralHeatingSystemEClass, ICentralHeatingSystem.class, "CentralHeatingSystem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCentralHeatingSystem_HeatSource(), this.getHeatSource(), this.getHeatSource_SpaceHeater(), "heatSource", null, 1, 1, ICentralHeatingSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCentralHeatingSystem_Controls(), this.getHeatingSystemControlType(), "controls", null, 0, -1, ICentralHeatingSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getCentralHeatingSystem_EmitterType(), this.getEmitterType(), "emitterType", null, 1, 1, ICentralHeatingSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(centralHeatingSystemEClass, ecorePackage.getEBoolean(), "isThermostaticallyControlled", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(centralHeatingSystemEClass, ecorePackage.getEBoolean(), "isBroken", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(centralWaterHeaterEClass, ICentralWaterHeater.class, "CentralWaterHeater", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(centralWaterHeaterEClass, ecorePackage.getEBoolean(), "isSolar", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(centralWaterHeaterEClass, ecorePackage.getEDouble(), "generateHotWaterAndPrimaryGains", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIInternalParameters(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIEnergyState(), "state", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getWaterTank(), "store", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "storeIsPrimary", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDouble(), "primaryLosses", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDouble(), "distributionLossFactor", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDouble(), "systemProportion", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(centralWaterHeaterEClass, null, "generateSystemGains", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIInternalParameters(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIEnergyState(), "state", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getWaterTank(), "store", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "storeIsPrimary", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDouble(), "systemLosses", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(centralWaterHeaterEClass, this.getCentralWaterSystem(), "getSystem", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(centralWaterHeaterEClass, ecorePackage.getEBoolean(), "causesPipeworkLosses", 1, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(centralWaterHeaterEClass, ecorePackage.getEBoolean(), "isCommunityHeating", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(mainWaterHeaterEClass, IMainWaterHeater.class, "MainWaterHeater", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMainWaterHeater_HeatSource(), this.getHeatSource(), this.getHeatSource_WaterHeater(), "heatSource", null, 1, 1, IMainWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(waterTankEClass, IWaterTank.class, "WaterTank", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWaterTank_Insulation(), ecorePackage.getEDouble(), "insulation", null, 1, 1, IWaterTank.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWaterTank_FactoryInsulation(), ecorePackage.getEBoolean(), "factoryInsulation", null, 1, 1, IWaterTank.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWaterTank_Volume(), ecorePackage.getEDouble(), "volume", null, 1, 1, IWaterTank.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWaterTank_ThermostatFitted(), ecorePackage.getEBoolean(), "thermostatFitted", null, 1, 1, IWaterTank.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWaterTank_SolarStorageVolume(), ecorePackage.getEDouble(), "solarStorageVolume", null, 1, 1, IWaterTank.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWaterTank_ImmersionHeater(), this.getImmersionHeater(), null, "immersionHeater", null, 0, 1, IWaterTank.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(waterTankEClass, ecorePackage.getEDouble(), "getStandingLosses", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIInternalParameters(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(visitorAccepterEClass, IVisitorAccepter.class, "VisitorAccepter", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(visitorAccepterEClass, null, "accept", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIConstants(), "constants", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIEnergyCalculatorParameters(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIEnergyCalculatorVisitor(), "visitor", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getAtomicInteger(), "heatingSystemCounter", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getHeatProportions(), "heatProportions", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(solarWaterHeaterEClass, ISolarWaterHeater.class, "SolarWaterHeater", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSolarWaterHeater_Pitch(), ecorePackage.getEDouble(), "pitch", null, 1, 1, ISolarWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSolarWaterHeater_Orientation(), ecorePackage.getEDouble(), "orientation", null, 0, 1, ISolarWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSolarWaterHeater_Area(), ecorePackage.getEDouble(), "area", null, 1, 1, ISolarWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSolarWaterHeater_UsefulAreaRatio(), ecorePackage.getEDouble(), "usefulAreaRatio", null, 1, 1, ISolarWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSolarWaterHeater_ZeroLossEfficiency(), ecorePackage.getEDouble(), "zeroLossEfficiency", null, 1, 1, ISolarWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSolarWaterHeater_LinearHeatLossCoefficient(), ecorePackage.getEDouble(), "linearHeatLossCoefficient", null, 1, 1, ISolarWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSolarWaterHeater_PreHeatTankVolume(), ecorePackage.getEDouble(), "preHeatTankVolume", null, 1, 1, ISolarWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSolarWaterHeater_PumpPhotovolatic(), ecorePackage.getEBoolean(), "pumpPhotovolatic", null, 1, 1, ISolarWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(storeContainerEClass, IStoreContainer.class, "StoreContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStoreContainer_Store(), this.getWaterTank(), null, "store", null, 0, 1, IStoreContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(immersionHeaterEClass, IImmersionHeater.class, "ImmersionHeater", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImmersionHeater_DualCoil(), ecorePackage.getEBoolean(), "dualCoil", null, 1, 1, IImmersionHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cookerEClass, ICooker.class, "Cooker", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCooker_HobBaseLoad(), ecorePackage.getEDouble(), "hobBaseLoad", null, 1, 1, ICooker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCooker_HobOccupancyFactor(), ecorePackage.getEDouble(), "hobOccupancyFactor", null, 1, 1, ICooker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCooker_HobFuelType(), this.getFuelType(), "hobFuelType", null, 1, 1, ICooker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCooker_OvenBaseLoad(), ecorePackage.getEDouble(), "ovenBaseLoad", null, 1, 1, ICooker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCooker_OvenOccupancyFactor(), ecorePackage.getEDouble(), "ovenOccupancyFactor", null, 1, 1, ICooker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCooker_OvenFuelType(), this.getFuelType(), "ovenFuelType", null, 1, 1, ICooker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCooker_GainsFactor(), ecorePackage.getEDouble(), "gainsFactor", null, 1, 1, ICooker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(storageHeaterEClass, IStorageHeater.class, "StorageHeater", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStorageHeater_ResponsivenessOverride(), ecorePackage.getEDoubleObject(), "responsivenessOverride", null, 1, 1, IStorageHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStorageHeater_ControlType(), this.getStorageHeaterControlType(), "controlType", null, 1, 1, IStorageHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStorageHeater_Type(), this.getStorageHeaterType(), "type", null, 1, 1, IStorageHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(communityHeatSourceEClass, ICommunityHeatSource.class, "CommunityHeatSource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommunityHeatSource_ChargingUsageBased(), ecorePackage.getEBoolean(), "chargingUsageBased", null, 1, 1, ICommunityHeatSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCommunityHeatSource_HeatEfficiency(), this.getEfficiency(), "heatEfficiency", null, 1, 1, ICommunityHeatSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(communityCHPEClass, ICommunityCHP.class, "CommunityCHP", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCommunityCHP_ElectricalEfficiency(), this.getEfficiency(), "electricalEfficiency", null, 1, 1, ICommunityCHP.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(roomHeaterEClass, IRoomHeater.class, "RoomHeater", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRoomHeater_Efficiency(), this.getEfficiency(), "efficiency", null, 1, 1, IRoomHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoomHeater_ThermostatFitted(), ecorePackage.getEBoolean(), "thermostatFitted", null, 1, 1, IRoomHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(heatPumpEClass, IHeatPump.class, "HeatPump", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHeatPump_SourceType(), this.getHeatPumpSourceType(), "sourceType", null, 1, 1, IHeatPump.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHeatPump_CoefficientOfPerformance(), this.getEfficiency(), "coefficientOfPerformance", null, 1, 1, IHeatPump.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHeatPump_WeatherCompensated(), ecorePackage.getEBoolean(), "weatherCompensated", null, 1, 1, IHeatPump.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHeatPump_AuxiliaryPresent(), ecorePackage.getEBoolean(), "auxiliaryPresent", null, 1, 1, IHeatPump.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHeatPump_Hybrid(), this.getHybridHeater(), null, "hybrid", null, 0, 1, IHeatPump.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(warmAirSystemEClass, IWarmAirSystem.class, "WarmAirSystem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWarmAirSystem_FuelType(), this.getFuelType(), "fuelType", null, 1, 1, IWarmAirSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWarmAirSystem_Efficiency(), this.getEfficiency(), "efficiency", null, 1, 1, IWarmAirSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWarmAirSystem_Circulator(), this.getWarmAirCirculator(), this.getWarmAirCirculator_WarmAirSystem(), "circulator", null, 0, 1, IWarmAirSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWarmAirSystem_Controls(), this.getHeatingSystemControlType(), "controls", null, 0, -1, IWarmAirSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(pointOfUseWaterHeaterEClass, IPointOfUseWaterHeater.class, "PointOfUseWaterHeater", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPointOfUseWaterHeater_FuelType(), this.getFuelType(), "fuelType", null, 1, 1, IPointOfUseWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPointOfUseWaterHeater_Multipoint(), ecorePackage.getEBoolean(), "multipoint", null, 1, 1, IPointOfUseWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPointOfUseWaterHeater_Efficiency(), this.getEfficiency(), "efficiency", null, 1, 1, IPointOfUseWaterHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(heatPumpWarmAirSystemEClass, IHeatPumpWarmAirSystem.class, "HeatPumpWarmAirSystem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHeatPumpWarmAirSystem_SourceType(), this.getHeatPumpSourceType(), "sourceType", null, 1, 1, IHeatPumpWarmAirSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHeatPumpWarmAirSystem_AuxiliaryPresent(), ecorePackage.getEBoolean(), "auxiliaryPresent", null, 1, 1, IHeatPumpWarmAirSystem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(warmAirCirculatorEClass, IWarmAirCirculator.class, "WarmAirCirculator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWarmAirCirculator_WarmAirSystem(), this.getWarmAirSystem(), this.getWarmAirSystem_Circulator(), "warmAirSystem", null, 1, 1, IWarmAirCirculator.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(primarySpaceHeaterEClass, IPrimarySpaceHeater.class, "PrimarySpaceHeater", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(backBoilerEClass, IBackBoiler.class, "BackBoiler", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(fuelAndFlueEClass, IFuelAndFlue.class, "FuelAndFlue", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFuelAndFlue_Fuel(), this.getFuelType(), "fuel", null, 1, 1, IFuelAndFlue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFuelAndFlue_FlueType(), theBoilersPackage.getFlueType(), "flueType", null, 1, 1, IFuelAndFlue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(individualHeatSourceEClass, IIndividualHeatSource.class, "IndividualHeatSource", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(operationalCostEClass, IOperationalCost.class, "OperationalCost", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOperationalCost_AnnualOperationalCost(), ecorePackage.getEDouble(), "annualOperationalCost", null, 1, 1, IOperationalCost.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(solarPhotovoltaicEClass, ISolarPhotovoltaic.class, "SolarPhotovoltaic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSolarPhotovoltaic_PeakPower(), ecorePackage.getEDouble(), "peakPower", null, 1, 1, ISolarPhotovoltaic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSolarPhotovoltaic_OwnUseProportion(), ecorePackage.getEDouble(), "ownUseProportion", null, 1, 1, ISolarPhotovoltaic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(adjusterEClass, IAdjuster.class, "Adjuster", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAdjuster_FuelTypes(), this.getFuelType(), "fuelTypes", null, 0, -1, IAdjuster.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdjuster_Deltas(), ecorePackage.getEDouble(), "deltas", null, 0, -1, IAdjuster.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAdjuster_Gains(), ecorePackage.getEDouble(), "gains", null, 1, 1, IAdjuster.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(hybridHeaterEClass, IHybridHeater.class, "HybridHeater", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHybridHeater_Fuel(), this.getFuelType(), "fuel", null, 1, 1, IHybridHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHybridHeater_Efficiency(), this.getEfficiency(), "efficiency", null, 1, 1, IHybridHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHybridHeater_Fraction(), ecorePackage.getEDouble(), "fraction", null, 12, 12, IHybridHeater.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(hasInstallationYearEClass, IHasInstallationYear.class, "HasInstallationYear", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHasInstallationYear_InstallationYear(), ecorePackage.getEInt(), "installationYear", "0", 1, 1, IHasInstallationYear.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(showerEClass, IShower.class, "Shower", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getShower_TechnologyModel(), this.getTechnologyModel(), this.getTechnologyModel_Shower(), "technologyModel", null, 0, 1, IShower.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(showerEClass, ecorePackage.getEDouble(), "solarAdjustment", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(mixerShowerEClass, IMixerShower.class, "MixerShower", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(electricShowerEClass, IElectricShower.class, "ElectricShower", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(fuelTypeEEnum, FuelType.class, "FuelType");
		addEEnumLiteral(fuelTypeEEnum, FuelType.MAINS_GAS);
		addEEnumLiteral(fuelTypeEEnum, FuelType.BULK_LPG);
		addEEnumLiteral(fuelTypeEEnum, FuelType.BOTTLED_LPG);
		addEEnumLiteral(fuelTypeEEnum, FuelType.ELECTRICITY);
		addEEnumLiteral(fuelTypeEEnum, FuelType.OIL);
		addEEnumLiteral(fuelTypeEEnum, FuelType.BIOMASS_PELLETS);
		addEEnumLiteral(fuelTypeEEnum, FuelType.BIOMASS_WOODCHIP);
		addEEnumLiteral(fuelTypeEEnum, FuelType.BIOMASS_WOOD);
		addEEnumLiteral(fuelTypeEEnum, FuelType.PHOTONS);
		addEEnumLiteral(fuelTypeEEnum, FuelType.HOUSE_COAL);
		addEEnumLiteral(fuelTypeEEnum, FuelType.COMMUNITY_HEAT);
		addEEnumLiteral(fuelTypeEEnum, FuelType.PEAK_ELECTRICITY);
		addEEnumLiteral(fuelTypeEEnum, FuelType.OFF_PEAK_ELECTRICITY);
		addEEnumLiteral(fuelTypeEEnum, FuelType.EXPORTED_ELECTRICITY);

		initEEnum(heatingSystemControlTypeEEnum, HeatingSystemControlType.class, "HeatingSystemControlType");
		addEEnumLiteral(heatingSystemControlTypeEEnum, HeatingSystemControlType.PROGRAMMER);
		addEEnumLiteral(heatingSystemControlTypeEEnum, HeatingSystemControlType.ROOM_THERMOSTAT);
		addEEnumLiteral(heatingSystemControlTypeEEnum, HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE);
		addEEnumLiteral(heatingSystemControlTypeEEnum, HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL);
		addEEnumLiteral(heatingSystemControlTypeEEnum, HeatingSystemControlType.APPLIANCE_THERMOSTAT);
		addEEnumLiteral(heatingSystemControlTypeEEnum, HeatingSystemControlType.BYPASS);
		addEEnumLiteral(heatingSystemControlTypeEEnum, HeatingSystemControlType.FLOW_SWITCH);
		addEEnumLiteral(heatingSystemControlTypeEEnum, HeatingSystemControlType.BOILER_ENERGY_MANAGER);
		addEEnumLiteral(heatingSystemControlTypeEEnum, HeatingSystemControlType.BOILER_INTERLOCK);
		addEEnumLiteral(heatingSystemControlTypeEEnum, HeatingSystemControlType.DELAYED_START_THERMOSTAT);

		initEEnum(emitterTypeEEnum, EmitterType.class, "EmitterType");
		addEEnumLiteral(emitterTypeEEnum, EmitterType.RADIATORS);
		addEEnumLiteral(emitterTypeEEnum, EmitterType.UNDERFLOOR_TIMBER);
		addEEnumLiteral(emitterTypeEEnum, EmitterType.UNDERFLOOR_SCREED);
		addEEnumLiteral(emitterTypeEEnum, EmitterType.UNDERFLOOR_CONCRETE);
		addEEnumLiteral(emitterTypeEEnum, EmitterType.WARM_AIR_FAN_COIL);

		initEEnum(storageHeaterControlTypeEEnum, StorageHeaterControlType.class, "StorageHeaterControlType");
		addEEnumLiteral(storageHeaterControlTypeEEnum, StorageHeaterControlType.MANUAL_CHARGE_CONTROL);
		addEEnumLiteral(storageHeaterControlTypeEEnum, StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL);
		addEEnumLiteral(storageHeaterControlTypeEEnum, StorageHeaterControlType.CELECT_CHARGE_CONTROL);

		initEEnum(storageHeaterTypeEEnum, StorageHeaterType.class, "StorageHeaterType");
		addEEnumLiteral(storageHeaterTypeEEnum, StorageHeaterType.OLD_LARGE_VOLUME);
		addEEnumLiteral(storageHeaterTypeEEnum, StorageHeaterType.SLIMLINE);
		addEEnumLiteral(storageHeaterTypeEEnum, StorageHeaterType.CONVECTOR);
		addEEnumLiteral(storageHeaterTypeEEnum, StorageHeaterType.FAN);
		addEEnumLiteral(storageHeaterTypeEEnum, StorageHeaterType.INTEGRATED_DIRECT_ACTING);

		initEEnum(heatPumpSourceTypeEEnum, HeatPumpSourceType.class, "HeatPumpSourceType");
		addEEnumLiteral(heatPumpSourceTypeEEnum, HeatPumpSourceType.GROUND);
		addEEnumLiteral(heatPumpSourceTypeEEnum, HeatPumpSourceType.AIR);

		// Initialize data types
		initEDataType(iEnergyCalculatorVisitorEDataType, IEnergyCalculatorVisitor.class, "IEnergyCalculatorVisitor", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(iInternalParametersEDataType, IInternalParameters.class, "IInternalParameters", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(iEnergyStateEDataType, IEnergyState.class, "IEnergyState", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(energyTypeEDataType, EnergyType.class, "EnergyType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(atomicIntegerEDataType, AtomicInteger.class, "AtomicInteger", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(iConstantsEDataType, IConstants.class, "IConstants", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(iEnergyCalculatorParametersEDataType, IEnergyCalculatorParameters.class, "IEnergyCalculatorParameters", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(efficiencyEDataType, Efficiency.class, "Efficiency", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(heatProportionsEDataType, IHeatProportions.class, "HeatProportions", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //TechnologiesPackageImpl
