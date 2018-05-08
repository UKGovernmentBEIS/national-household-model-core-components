/** */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.types.LightType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;
import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;

/**
 *
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Light</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are implemented:
 *
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.LightImpl#getName
 * <em>Name</em>}
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.LightImpl#getEfficiency
 * <em>Efficiency</em>}
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.LightImpl#getProportion
 * <em>Proportion</em>}
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.LightImpl#getType
 * <em>Type</em>}
 * </ul>
 *
 * @generated
 */
public class LightImpl extends MinimalEObjectImpl implements ILight {

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
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

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
    protected static final double EFFICIENCY_EDEFAULT = 0.0;

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
    protected double efficiency = EFFICIENCY_EDEFAULT;

    /**
     * The default value of the '{@link #getProportion() <em>Proportion</em>}'
     * attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getProportion()
     * @generated
     * @ordered
     */
    protected static final double PROPORTION_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getProportion() <em>Proportion</em>}'
     * attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getProportion()
     * @generated
     * @ordered
     */
    protected double proportion = PROPORTION_EDEFAULT;

    /**
     * The default value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getType()
     * @generated
     * @ordered
     */
    protected static final LightType TYPE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getType()
     * @generated
     * @ordered
     */
    protected LightType type = TYPE_EDEFAULT;

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected LightImpl() {
        super();
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ITechnologiesPackage.Literals.LIGHT;
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired()) {
            eNotify(
                    new ENotificationImpl(
                            this, Notification.SET, ITechnologiesPackage.LIGHT__NAME, oldName, name));
        }
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public double getEfficiency() {
        return efficiency;
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void setEfficiency(double newEfficiency) {
        double oldEfficiency = efficiency;
        efficiency = newEfficiency;
        if (eNotificationRequired()) {
            eNotify(
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            ITechnologiesPackage.LIGHT__EFFICIENCY,
                            oldEfficiency,
                            efficiency));
        }
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public double getProportion() {
        return proportion;
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void setProportion(double newProportion) {
        double oldProportion = proportion;
        proportion = newProportion;
        if (eNotificationRequired()) {
            eNotify(
                    new ENotificationImpl(
                            this,
                            Notification.SET,
                            ITechnologiesPackage.LIGHT__PROPORTION,
                            oldProportion,
                            proportion));
        }
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated no
     */
    public LightType getType() {
        if (type == null) {
            // backwards compatibility to infer type from old stocks which have only an efficiency in them
            if (getEfficiency() > ILight.CFL_SPLIT_POINT) {
                type = LightType.Incandescent;
            } else {
                type = LightType.CFL;
            }
        }
        return type;
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public void setType(LightType newType) {
        LightType oldType = type;
        type = newType;
        if (eNotificationRequired()) {
            eNotify(
                    new ENotificationImpl(
                            this, Notification.SET, ITechnologiesPackage.LIGHT__TYPE, oldType, type));
        }
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NO
     */
    public void accept(
            IConstants constants,
            final IEnergyCalculatorParameters parameters,
            final IEnergyCalculatorVisitor visitor,
            AtomicInteger heatingSystemCounter,
            IHeatProportions heatProportions) {
        visitor.visitLight(
                getName(),
                getProportion(),
                getType(),
                constants.get(SplitRateConstants.DEFAULT_FRACTIONS, double[].class));
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ITechnologiesPackage.LIGHT__NAME:
                return getName();
            case ITechnologiesPackage.LIGHT__EFFICIENCY:
                return getEfficiency();
            case ITechnologiesPackage.LIGHT__PROPORTION:
                return getProportion();
            case ITechnologiesPackage.LIGHT__TYPE:
                return getType();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ITechnologiesPackage.LIGHT__NAME:
                setName((String) newValue);
                return;
            case ITechnologiesPackage.LIGHT__EFFICIENCY:
                setEfficiency((Double) newValue);
                return;
            case ITechnologiesPackage.LIGHT__PROPORTION:
                setProportion((Double) newValue);
                return;
            case ITechnologiesPackage.LIGHT__TYPE:
                setType((LightType) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case ITechnologiesPackage.LIGHT__NAME:
                setName(NAME_EDEFAULT);
                return;
            case ITechnologiesPackage.LIGHT__EFFICIENCY:
                setEfficiency(EFFICIENCY_EDEFAULT);
                return;
            case ITechnologiesPackage.LIGHT__PROPORTION:
                setProportion(PROPORTION_EDEFAULT);
                return;
            case ITechnologiesPackage.LIGHT__TYPE:
                setType(TYPE_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ITechnologiesPackage.LIGHT__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case ITechnologiesPackage.LIGHT__EFFICIENCY:
                return efficiency != EFFICIENCY_EDEFAULT;
            case ITechnologiesPackage.LIGHT__PROPORTION:
                return proportion != PROPORTION_EDEFAULT;
            case ITechnologiesPackage.LIGHT__TYPE:
                return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
        }
        return super.eIsSet(featureID);
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) {
            return super.toString();
        }

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: ");
        result.append(name);
        result.append(", efficiency: ");
        result.append(efficiency);
        result.append(", proportion: ");
        result.append(proportion);
        result.append(", type: ");
        result.append(type);
        result.append(')');
        return result.toString();
    }
} // LightImpl
