/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Central Water Heater</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class CentralWaterHeaterImpl extends MinimalEObjectImpl implements ICentralWaterHeater {

    /**
     * A set of bit flags representing the values of boolean attributes and
     * whether unsettable features have been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    protected int flags = 0;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    protected CentralWaterHeaterImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ITechnologiesPackage.Literals.CENTRAL_WATER_HEATER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public ICentralWaterSystem getSystem() {
        return (ICentralWaterSystem) eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated not
     */
    abstract public boolean causesPipeworkLosses();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated NOT
     */
    public abstract boolean isCommunityHeating();

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated no default is false.
     */
    public boolean isSolar() {
        return false;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public double generateHotWaterAndPrimaryGains(IInternalParameters parameters, IEnergyState state, IWaterTank store, boolean storeIsPrimary, double primaryLosses, double distributionLossFactor, double systemProportion) {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public void generateSystemGains(IInternalParameters parameters, IEnergyState state, IWaterTank store, boolean storeIsPrimary, double systemLosses) {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

} //CentralWaterHeaterImpl
