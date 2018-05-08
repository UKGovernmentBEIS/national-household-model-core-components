/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import uk.org.cse.nhm.hom.emf.technologies.boilers.EfficiencySourceType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class BoilersFactoryImpl extends EFactoryImpl implements IBoilersFactory {

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public static IBoilersFactory init() {
        try {
            IBoilersFactory theBoilersFactory = (IBoilersFactory) EPackage.Registry.INSTANCE.getEFactory(IBoilersPackage.eNS_URI);
            if (theBoilersFactory != null) {
                return theBoilersFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new BoilersFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public BoilersFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case IBoilersPackage.BOILER:
                return createBoiler();
            case IBoilersPackage.CPSU:
                return createCPSU();
            case IBoilersPackage.KEEP_HOT_FACILITY:
                return createKeepHotFacility();
            case IBoilersPackage.STORAGE_COMBI_BOILER:
                return createStorageCombiBoiler();
            case IBoilersPackage.INSTANTANEOUS_COMBI_BOILER:
                return createInstantaneousCombiBoiler();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case IBoilersPackage.FLUE_TYPE:
                return createFlueTypeFromString(eDataType, initialValue);
            case IBoilersPackage.EFFICIENCY_SOURCE_TYPE:
                return createEfficiencySourceTypeFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case IBoilersPackage.FLUE_TYPE:
                return convertFlueTypeToString(eDataType, instanceValue);
            case IBoilersPackage.EFFICIENCY_SOURCE_TYPE:
                return convertEfficiencySourceTypeToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public IBoiler createBoiler() {
        BoilerImpl boiler = new BoilerImpl();
        return boiler;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public ICPSU createCPSU() {
        CPSUImpl cpsu = new CPSUImpl();
        return cpsu;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public IKeepHotFacility createKeepHotFacility() {
        KeepHotFacilityImpl keepHotFacility = new KeepHotFacilityImpl();
        return keepHotFacility;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public IStorageCombiBoiler createStorageCombiBoiler() {
        StorageCombiBoilerImpl storageCombiBoiler = new StorageCombiBoilerImpl();
        return storageCombiBoiler;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public IInstantaneousCombiBoiler createInstantaneousCombiBoiler() {
        InstantaneousCombiBoilerImpl instantaneousCombiBoiler = new InstantaneousCombiBoilerImpl();
        return instantaneousCombiBoiler;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public FlueType createFlueTypeFromString(EDataType eDataType, String initialValue) {
        FlueType result = FlueType.get(initialValue);
        if (result == null) {
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        }
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public String convertFlueTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public EfficiencySourceType createEfficiencySourceTypeFromString(EDataType eDataType, String initialValue) {
        EfficiencySourceType result = EfficiencySourceType.get(initialValue);
        if (result == null) {
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        }
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public String convertEfficiencySourceTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public IBoilersPackage getBoilersPackage() {
        return (IBoilersPackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @deprecated @generated
     */
    @Deprecated
    public static IBoilersPackage getPackage() {
        return IBoilersPackage.eINSTANCE;
    }

} //BoilersFactoryImpl
