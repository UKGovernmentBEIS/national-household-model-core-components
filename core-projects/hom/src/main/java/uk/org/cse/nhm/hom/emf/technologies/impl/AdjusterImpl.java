/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IAdjuster;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.AdjustingTransducer;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Adjuster</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.AdjusterImpl#getName
 * <em>Name</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.AdjusterImpl#getFuelTypes
 * <em>Fuel Types</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.AdjusterImpl#getDeltas
 * <em>Deltas</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.AdjusterImpl#getGains
 * <em>Gains</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AdjusterImpl extends MinimalEObjectImpl implements IAdjuster {

    /**
     * A set of bit flags representing the values of boolean attributes and
     * whether unsettable features have been set.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    protected int flags = 0;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The cached value of the '{@link #getFuelTypes() <em>Fuel Types</em>}'
     * attribute list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getFuelTypes()
     * @generated
     * @ordered
     */
    protected EList<FuelType> fuelTypes;

    /**
     * The cached value of the '{@link #getDeltas() <em>Deltas</em>}' attribute
     * list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDeltas()
     * @generated
     * @ordered
     */
    protected EList<Double> deltas;

    /**
     * The default value of the '{@link #getGains() <em>Gains</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getGains()
     * @generated
     * @ordered
     */
    protected static final double GAINS_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getGains() <em>Gains</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getGains()
     * @generated
     * @ordered
     */
    protected double gains = GAINS_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    protected AdjusterImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ITechnologiesPackage.Literals.ADJUSTER;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.ADJUSTER__NAME, oldName, name));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public EList<FuelType> getFuelTypes() {
        if (fuelTypes == null) {
            fuelTypes = new EDataTypeUniqueEList<FuelType>(FuelType.class, this, ITechnologiesPackage.ADJUSTER__FUEL_TYPES);
        }
        return fuelTypes;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public EList<Double> getDeltas() {
        if (deltas == null) {
            deltas = new EDataTypeUniqueEList<Double>(Double.class, this, ITechnologiesPackage.ADJUSTER__DELTAS);
        }
        return deltas;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public double getGains() {
        return gains;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public void setGains(double newGains) {
        double oldGains = gains;
        gains = newGains;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.ADJUSTER__GAINS, oldGains, gains));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated no
     */
    @Override
    public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor,
            final AtomicInteger heatingSystemCounter, IHeatProportions heatProportions) {
        switch (parameters.getCalculatorType().appliances) {
            case SAP2012:
                return;
            default:
                visitor.visitEnergyTransducer(new AdjustingTransducer(getFuelTypes(), getDeltas(), getGains()));
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ITechnologiesPackage.ADJUSTER__NAME:
                return getName();
            case ITechnologiesPackage.ADJUSTER__FUEL_TYPES:
                return getFuelTypes();
            case ITechnologiesPackage.ADJUSTER__DELTAS:
                return getDeltas();
            case ITechnologiesPackage.ADJUSTER__GAINS:
                return getGains();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ITechnologiesPackage.ADJUSTER__NAME:
                setName((String) newValue);
                return;
            case ITechnologiesPackage.ADJUSTER__FUEL_TYPES:
                getFuelTypes().clear();
                getFuelTypes().addAll((Collection<? extends FuelType>) newValue);
                return;
            case ITechnologiesPackage.ADJUSTER__DELTAS:
                getDeltas().clear();
                getDeltas().addAll((Collection<? extends Double>) newValue);
                return;
            case ITechnologiesPackage.ADJUSTER__GAINS:
                setGains((Double) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case ITechnologiesPackage.ADJUSTER__NAME:
                setName(NAME_EDEFAULT);
                return;
            case ITechnologiesPackage.ADJUSTER__FUEL_TYPES:
                getFuelTypes().clear();
                return;
            case ITechnologiesPackage.ADJUSTER__DELTAS:
                getDeltas().clear();
                return;
            case ITechnologiesPackage.ADJUSTER__GAINS:
                setGains(GAINS_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ITechnologiesPackage.ADJUSTER__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case ITechnologiesPackage.ADJUSTER__FUEL_TYPES:
                return fuelTypes != null && !fuelTypes.isEmpty();
            case ITechnologiesPackage.ADJUSTER__DELTAS:
                return deltas != null && !deltas.isEmpty();
            case ITechnologiesPackage.ADJUSTER__GAINS:
                return gains != GAINS_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc --> @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) {
            return super.toString();
        }

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: ");
        result.append(name);
        result.append(", fuelTypes: ");
        result.append(fuelTypes);
        result.append(", deltas: ");
        result.append(deltas);
        result.append(", gains: ");
        result.append(gains);
        result.append(')');
        return result.toString();
    }

} // AdjusterImpl
