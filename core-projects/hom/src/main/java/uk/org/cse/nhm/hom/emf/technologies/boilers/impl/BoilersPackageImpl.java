/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.*;
import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BoilersPackageImpl extends EPackageImpl implements IBoilersPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass boilerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cpsuEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass keepHotFacilityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass storageCombiBoilerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instantaneousCombiBoilerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass combiBoilerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum flueTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum efficiencySourceTypeEEnum = null;

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
	 * @see uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BoilersPackageImpl() {
		super(eNS_URI, IBoilersFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IBoilersPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IBoilersPackage init() {
		if (isInited) return (IBoilersPackage)EPackage.Registry.INSTANCE.getEPackage(IBoilersPackage.eNS_URI);

		// Obtain or create and register package
		BoilersPackageImpl theBoilersPackage = (BoilersPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BoilersPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BoilersPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		TechnologiesPackageImpl theTechnologiesPackage = (TechnologiesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ITechnologiesPackage.eNS_URI) instanceof TechnologiesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ITechnologiesPackage.eNS_URI) : ITechnologiesPackage.eINSTANCE);

		// Create package meta-data objects
		theBoilersPackage.createPackageContents();
		theTechnologiesPackage.createPackageContents();

		// Initialize created meta-data
		theBoilersPackage.initializePackageContents();
		theTechnologiesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBoilersPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IBoilersPackage.eNS_URI, theBoilersPackage);
		return theBoilersPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBoiler() {
		return boilerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBoiler_SummerEfficiency() {
		return (EAttribute)boilerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBoiler_WinterEfficiency() {
		return (EAttribute)boilerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBoiler_Condensing() {
		return (EAttribute)boilerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBoiler_WeatherCompensated() {
		return (EAttribute)boilerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBoiler_PumpInHeatedSpace() {
		return (EAttribute)boilerEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBoiler_EfficiencySource() {
		return (EAttribute)boilerEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCPSU() {
		return cpsuEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCPSU_StoreTemperature() {
		return (EAttribute)cpsuEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getKeepHotFacility() {
		return keepHotFacilityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getKeepHotFacility_TimeClock() {
		return (EAttribute)keepHotFacilityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStorageCombiBoiler() {
		return storageCombiBoilerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStorageCombiBoiler_StoreInPrimaryCircuit() {
		return (EAttribute)storageCombiBoilerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstantaneousCombiBoiler() {
		return instantaneousCombiBoilerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstantaneousCombiBoiler_KeepHotFacility() {
		return (EReference)instantaneousCombiBoilerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCombiBoiler() {
		return combiBoilerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getFlueType() {
		return flueTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getEfficiencySourceType() {
		return efficiencySourceTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBoilersFactory getBoilersFactory() {
		return (IBoilersFactory)getEFactoryInstance();
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
		boilerEClass = createEClass(BOILER);
		createEAttribute(boilerEClass, BOILER__SUMMER_EFFICIENCY);
		createEAttribute(boilerEClass, BOILER__WINTER_EFFICIENCY);
		createEAttribute(boilerEClass, BOILER__CONDENSING);
		createEAttribute(boilerEClass, BOILER__WEATHER_COMPENSATED);
		createEAttribute(boilerEClass, BOILER__PUMP_IN_HEATED_SPACE);
		createEAttribute(boilerEClass, BOILER__EFFICIENCY_SOURCE);

		cpsuEClass = createEClass(CPSU);
		createEAttribute(cpsuEClass, CPSU__STORE_TEMPERATURE);

		keepHotFacilityEClass = createEClass(KEEP_HOT_FACILITY);
		createEAttribute(keepHotFacilityEClass, KEEP_HOT_FACILITY__TIME_CLOCK);

		storageCombiBoilerEClass = createEClass(STORAGE_COMBI_BOILER);
		createEAttribute(storageCombiBoilerEClass, STORAGE_COMBI_BOILER__STORE_IN_PRIMARY_CIRCUIT);

		instantaneousCombiBoilerEClass = createEClass(INSTANTANEOUS_COMBI_BOILER);
		createEReference(instantaneousCombiBoilerEClass, INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY);

		combiBoilerEClass = createEClass(COMBI_BOILER);

		// Create enums
		flueTypeEEnum = createEEnum(FLUE_TYPE);
		efficiencySourceTypeEEnum = createEEnum(EFFICIENCY_SOURCE_TYPE);
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
		ITechnologiesPackage theTechnologiesPackage = (ITechnologiesPackage)EPackage.Registry.INSTANCE.getEPackage(ITechnologiesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		boilerEClass.getESuperTypes().add(theTechnologiesPackage.getIndividualHeatSource());
		boilerEClass.getESuperTypes().add(theTechnologiesPackage.getVisitorAccepter());
		boilerEClass.getESuperTypes().add(theTechnologiesPackage.getFuelAndFlue());
		cpsuEClass.getESuperTypes().add(this.getBoiler());
		cpsuEClass.getESuperTypes().add(theTechnologiesPackage.getStoreContainer());
		storageCombiBoilerEClass.getESuperTypes().add(this.getCombiBoiler());
		storageCombiBoilerEClass.getESuperTypes().add(theTechnologiesPackage.getStoreContainer());
		instantaneousCombiBoilerEClass.getESuperTypes().add(this.getCombiBoiler());
		combiBoilerEClass.getESuperTypes().add(this.getBoiler());

		// Initialize classes and features; add operations and parameters
		initEClass(boilerEClass, IBoiler.class, "Boiler", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBoiler_SummerEfficiency(), theTechnologiesPackage.getEfficiency(), "summerEfficiency", null, 1, 1, IBoiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBoiler_WinterEfficiency(), theTechnologiesPackage.getEfficiency(), "winterEfficiency", null, 1, 1, IBoiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBoiler_Condensing(), ecorePackage.getEBoolean(), "condensing", null, 1, 1, IBoiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBoiler_WeatherCompensated(), ecorePackage.getEBoolean(), "weatherCompensated", null, 1, 1, IBoiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBoiler_PumpInHeatedSpace(), ecorePackage.getEBoolean(), "pumpInHeatedSpace", null, 1, 1, IBoiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBoiler_EfficiencySource(), this.getEfficiencySourceType(), "efficiencySource", null, 1, 1, IBoiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cpsuEClass, ICPSU.class, "CPSU", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCPSU_StoreTemperature(), ecorePackage.getEDouble(), "storeTemperature", null, 1, 1, ICPSU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(keepHotFacilityEClass, IKeepHotFacility.class, "KeepHotFacility", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getKeepHotFacility_TimeClock(), ecorePackage.getEBoolean(), "timeClock", null, 1, 1, IKeepHotFacility.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(keepHotFacilityEClass, ecorePackage.getEDouble(), "getAdditionalUsageLosses", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theTechnologiesPackage.getIInternalParameters(), "parameters", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theTechnologiesPackage.getIEnergyState(), "state", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(storageCombiBoilerEClass, IStorageCombiBoiler.class, "StorageCombiBoiler", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStorageCombiBoiler_StoreInPrimaryCircuit(), ecorePackage.getEBoolean(), "storeInPrimaryCircuit", null, 1, 1, IStorageCombiBoiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(instantaneousCombiBoilerEClass, IInstantaneousCombiBoiler.class, "InstantaneousCombiBoiler", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInstantaneousCombiBoiler_KeepHotFacility(), this.getKeepHotFacility(), null, "keepHotFacility", null, 0, 1, IInstantaneousCombiBoiler.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(combiBoilerEClass, ICombiBoiler.class, "CombiBoiler", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(flueTypeEEnum, FlueType.class, "FlueType");
		addEEnumLiteral(flueTypeEEnum, FlueType.CHIMNEY);
		addEEnumLiteral(flueTypeEEnum, FlueType.OPEN_FLUE);
		addEEnumLiteral(flueTypeEEnum, FlueType.BALANCED_FLUE);
		addEEnumLiteral(flueTypeEEnum, FlueType.FAN_ASSISTED_BALANCED_FLUE);
		addEEnumLiteral(flueTypeEEnum, FlueType.NOT_APPLICABLE);

		initEEnum(efficiencySourceTypeEEnum, EfficiencySourceType.class, "EfficiencySourceType");
		addEEnumLiteral(efficiencySourceTypeEEnum, EfficiencySourceType.SAP_DEFAULT);
		addEEnumLiteral(efficiencySourceTypeEEnum, EfficiencySourceType.MANUFACTURER_DECLARED);
	}

} //BoilersPackageImpl
