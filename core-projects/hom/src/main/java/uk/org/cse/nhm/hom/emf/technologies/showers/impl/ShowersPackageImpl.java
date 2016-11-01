/**
 */
package uk.org.cse.nhm.hom.emf.technologies.showers.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;

import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;

import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilersPackageImpl;

import uk.org.cse.nhm.hom.emf.technologies.impl.TechnologiesPackageImpl;

import uk.org.cse.nhm.hom.emf.technologies.showers.IElectricShower;
import uk.org.cse.nhm.hom.emf.technologies.showers.IMixerShower;
import uk.org.cse.nhm.hom.emf.technologies.showers.IShower;
import uk.org.cse.nhm.hom.emf.technologies.showers.IShowersFactory;
import uk.org.cse.nhm.hom.emf.technologies.showers.IShowersPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ShowersPackageImpl extends EPackageImpl implements IShowersPackage {
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
	 * @see uk.org.cse.nhm.hom.emf.technologies.showers.IShowersPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ShowersPackageImpl() {
		super(eNS_URI, IShowersFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IShowersPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IShowersPackage init() {
		if (isInited) return (IShowersPackage)EPackage.Registry.INSTANCE.getEPackage(IShowersPackage.eNS_URI);

		// Obtain or create and register package
		ShowersPackageImpl theShowersPackage = (ShowersPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ShowersPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ShowersPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		TechnologiesPackageImpl theTechnologiesPackage = (TechnologiesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ITechnologiesPackage.eNS_URI) instanceof TechnologiesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ITechnologiesPackage.eNS_URI) : ITechnologiesPackage.eINSTANCE);
		BoilersPackageImpl theBoilersPackage = (BoilersPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(IBoilersPackage.eNS_URI) instanceof BoilersPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(IBoilersPackage.eNS_URI) : IBoilersPackage.eINSTANCE);

		// Create package meta-data objects
		theShowersPackage.createPackageContents();
		theTechnologiesPackage.createPackageContents();
		theBoilersPackage.createPackageContents();

		// Initialize created meta-data
		theShowersPackage.initializePackageContents();
		theTechnologiesPackage.initializePackageContents();
		theBoilersPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theShowersPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IShowersPackage.eNS_URI, theShowersPackage);
		return theShowersPackage;
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
	public IShowersFactory getShowersFactory() {
		return (IShowersFactory)getEFactoryInstance();
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
		showerEClass = createEClass(SHOWER);
		createEReference(showerEClass, SHOWER__TECHNOLOGY_MODEL);

		mixerShowerEClass = createEClass(MIXER_SHOWER);

		electricShowerEClass = createEClass(ELECTRIC_SHOWER);
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
		showerEClass.getESuperTypes().add(theTechnologiesPackage.getVisitorAccepter());
		mixerShowerEClass.getESuperTypes().add(this.getShower());
		electricShowerEClass.getESuperTypes().add(this.getShower());

		// Initialize classes and features; add operations and parameters
		initEClass(showerEClass, IShower.class, "Shower", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getShower_TechnologyModel(), theTechnologiesPackage.getTechnologyModel(), theTechnologiesPackage.getTechnologyModel_Shower(), "technologyModel", null, 0, 1, IShower.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(showerEClass, ecorePackage.getEDouble(), "solarAdjustment", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(mixerShowerEClass, IMixerShower.class, "MixerShower", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(electricShowerEClass, IElectricShower.class, "ElectricShower", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
	}

} //ShowersPackageImpl
