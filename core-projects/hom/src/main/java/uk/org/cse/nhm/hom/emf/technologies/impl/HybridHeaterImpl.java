/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IHybridHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Hybrid Heater</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HybridHeaterImpl#getFuel
 * <em>Fuel</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HybridHeaterImpl#getEfficiency
 * <em>Efficiency</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HybridHeaterImpl#getFraction
 * <em>Fraction</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HybridHeaterImpl extends MinimalEObjectImpl implements IHybridHeater {

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
     * The default value of the '{@link #getFuel() <em>Fuel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFuel()
     * @generated
     * @ordered
     */
    protected static final FuelType FUEL_EDEFAULT = FuelType.MAINS_GAS;

    /**
     * The offset of the flags representing the value of the '{@link #getFuel()
     * <em>Fuel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    protected static final int FUEL_EFLAG_OFFSET = 0;

    /**
     * The flags representing the default value of the '{@link #getFuel()
     * <em>Fuel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    protected static final int FUEL_EFLAG_DEFAULT = FUEL_EDEFAULT.ordinal() << FUEL_EFLAG_OFFSET;

    /**
     * The array of enumeration values for '{@link FuelType Fuel Type}'
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    private static final FuelType[] FUEL_EFLAG_VALUES = FuelType.values();

    /**
     * The flags representing the value of the '{@link #getFuel()
     * <em>Fuel</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFuel()
     * @generated
     * @ordered
     */
    protected static final int FUEL_EFLAG = 0xf << FUEL_EFLAG_OFFSET;

    /**
     * The default value of the '{@link #getEfficiency() <em>Efficiency</em>}'
     * attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getEfficiency()
     * @generated
     * @ordered
     */
    protected static final Efficiency EFFICIENCY_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEfficiency() <em>Efficiency</em>}'
     * attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getEfficiency()
     * @generated
     * @ordered
     */
    protected Efficiency efficiency = EFFICIENCY_EDEFAULT;

    /**
     * The cached value of the '{@link #getFraction() <em>Fraction</em>}'
     * attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFraction()
     * @generated
     * @ordered
     */
    protected EList<Double> fraction;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    protected HybridHeaterImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ITechnologiesPackage.Literals.HYBRID_HEATER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public FuelType getFuel() {
        return FUEL_EFLAG_VALUES[(flags & FUEL_EFLAG) >>> FUEL_EFLAG_OFFSET];
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public void setFuel(FuelType newFuel) {
        FuelType oldFuel = FUEL_EFLAG_VALUES[(flags & FUEL_EFLAG) >>> FUEL_EFLAG_OFFSET];
        if (newFuel == null) {
            newFuel = FUEL_EDEFAULT;
        }
        flags = flags & ~FUEL_EFLAG | newFuel.ordinal() << FUEL_EFLAG_OFFSET;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HYBRID_HEATER__FUEL, oldFuel, newFuel));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public Efficiency getEfficiency() {
        return efficiency;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public void setEfficiency(Efficiency newEfficiency) {
        Efficiency oldEfficiency = efficiency;
        efficiency = newEfficiency;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HYBRID_HEATER__EFFICIENCY, oldEfficiency, efficiency));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public EList<Double> getFraction() {
        if (fraction == null) {
            fraction = new EDataTypeEList<Double>(Double.class, this, ITechnologiesPackage.HYBRID_HEATER__FRACTION);
        }
        return fraction;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ITechnologiesPackage.HYBRID_HEATER__FUEL:
                return getFuel();
            case ITechnologiesPackage.HYBRID_HEATER__EFFICIENCY:
                return getEfficiency();
            case ITechnologiesPackage.HYBRID_HEATER__FRACTION:
                return getFraction();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ITechnologiesPackage.HYBRID_HEATER__FUEL:
                setFuel((FuelType) newValue);
                return;
            case ITechnologiesPackage.HYBRID_HEATER__EFFICIENCY:
                setEfficiency((Efficiency) newValue);
                return;
            case ITechnologiesPackage.HYBRID_HEATER__FRACTION:
                getFraction().clear();
                getFraction().addAll((Collection<? extends Double>) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case ITechnologiesPackage.HYBRID_HEATER__FUEL:
                setFuel(FUEL_EDEFAULT);
                return;
            case ITechnologiesPackage.HYBRID_HEATER__EFFICIENCY:
                setEfficiency(EFFICIENCY_EDEFAULT);
                return;
            case ITechnologiesPackage.HYBRID_HEATER__FRACTION:
                getFraction().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ITechnologiesPackage.HYBRID_HEATER__FUEL:
                return (flags & FUEL_EFLAG) != FUEL_EFLAG_DEFAULT;
            case ITechnologiesPackage.HYBRID_HEATER__EFFICIENCY:
                return EFFICIENCY_EDEFAULT == null ? efficiency != null : !EFFICIENCY_EDEFAULT.equals(efficiency);
            case ITechnologiesPackage.HYBRID_HEATER__FRACTION:
                return fraction != null && !fraction.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) {
            return super.toString();
        }

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (fuel: ");
        result.append(FUEL_EFLAG_VALUES[(flags & FUEL_EFLAG) >>> FUEL_EFLAG_OFFSET]);
        result.append(", efficiency: ");
        result.append(efficiency);
        result.append(", fraction: ");
        result.append(fraction);
        result.append(')');
        return result.toString();
    }

} //HybridHeaterImpl
