/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.impl.ElectricHeatTransducer;
import uk.org.cse.nhm.energycalculator.api.impl.HeatTransducer;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;
import uk.org.cse.nhm.hom.constants.adjustments.TemperatureAdjustments;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.WarmAirFans;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Warm Air System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirSystemImpl#getFuelType
 * <em>Fuel Type</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirSystemImpl#getEfficiency
 * <em>Efficiency</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirSystemImpl#getCirculator
 * <em>Circulator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated no (added IHeatingSystem interface)
 */
public class WarmAirSystemImpl extends SpaceHeaterImpl implements IWarmAirSystem, IHeatingSystem {

    /**
     * The default value of the '{@link #getFuelType() <em>Fuel Type</em>}'
     * attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFuelType()
     * @generated
     * @ordered
     */
    protected static final FuelType FUEL_TYPE_EDEFAULT = FuelType.MAINS_GAS;
    /**
     * The offset of the flags representing the value of the '{@link #getFuelType()
     * <em>Fuel Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    protected static final int FUEL_TYPE_EFLAG_OFFSET = 0;
    /**
     * The flags representing the default value of the '{@link #getFuelType()
     * <em>Fuel Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    protected static final int FUEL_TYPE_EFLAG_DEFAULT = FUEL_TYPE_EDEFAULT.ordinal() << FUEL_TYPE_EFLAG_OFFSET;
    /**
     * The array of enumeration values for '{@link FuelType Fuel Type}'
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    private static final FuelType[] FUEL_TYPE_EFLAG_VALUES = FuelType.values();
    /**
     * The flags representing the value of the '{@link #getFuelType() <em>Fuel
     * Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getFuelType()
     * @generated
     * @ordered
     */
    protected static final int FUEL_TYPE_EFLAG = 0xf << FUEL_TYPE_EFLAG_OFFSET;
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
     * The cached value of the '{@link #getCirculator() <em>Circulator</em>}'
     * reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getCirculator()
     * @generated
     * @ordered
     */
    protected IWarmAirCirculator circulator;

    /**
     * The cached value of the '{@link #getControls() <em>Controls</em>}'
     * attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getControls()
     * @generated
     * @ordered
     */
    protected EList<HeatingSystemControlType> controls;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    protected WarmAirSystemImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ITechnologiesPackage.Literals.WARM_AIR_SYSTEM;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public FuelType getFuelType() {
        return FUEL_TYPE_EFLAG_VALUES[(flags & FUEL_TYPE_EFLAG) >>> FUEL_TYPE_EFLAG_OFFSET];
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setFuelType(FuelType newFuelType) {
        FuelType oldFuelType = FUEL_TYPE_EFLAG_VALUES[(flags & FUEL_TYPE_EFLAG) >>> FUEL_TYPE_EFLAG_OFFSET];
        if (newFuelType == null) {
            newFuelType = FUEL_TYPE_EDEFAULT;
        }
        flags = flags & ~FUEL_TYPE_EFLAG | newFuelType.ordinal() << FUEL_TYPE_EFLAG_OFFSET;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WARM_AIR_SYSTEM__FUEL_TYPE, oldFuelType, newFuelType));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public Efficiency getEfficiency() {
        return efficiency;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setEfficiency(Efficiency newEfficiency) {
        Efficiency oldEfficiency = efficiency;
        efficiency = newEfficiency;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WARM_AIR_SYSTEM__EFFICIENCY, oldEfficiency, efficiency));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public IWarmAirCirculator getCirculator() {
        if (circulator != null && circulator.eIsProxy()) {
            InternalEObject oldCirculator = (InternalEObject) circulator;
            circulator = (IWarmAirCirculator) eResolveProxy(oldCirculator);
            if (circulator != oldCirculator) {
                if (eNotificationRequired()) {
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR, oldCirculator, circulator));
                }
            }
        }
        return circulator;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public IWarmAirCirculator basicGetCirculator() {
        return circulator;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    public NotificationChain basicSetCirculator(IWarmAirCirculator newCirculator, NotificationChain msgs) {
        IWarmAirCirculator oldCirculator = circulator;
        circulator = newCirculator;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR, oldCirculator, newCirculator);
            if (msgs == null) {
                msgs = notification;
            } else {
                msgs.add(notification);
            }
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setCirculator(IWarmAirCirculator newCirculator) {
        if (newCirculator != circulator) {
            NotificationChain msgs = null;
            if (circulator != null) {
                msgs = ((InternalEObject) circulator).eInverseRemove(this, ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM, IWarmAirCirculator.class, msgs);
            }
            if (newCirculator != null) {
                msgs = ((InternalEObject) newCirculator).eInverseAdd(this, ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM, IWarmAirCirculator.class, msgs);
            }
            msgs = basicSetCirculator(newCirculator, msgs);
            if (msgs != null) {
                msgs.dispatch();
            }
        } else if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR, newCirculator, newCirculator));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public EList<HeatingSystemControlType> getControls() {
        if (controls == null) {
            controls = new EDataTypeUniqueEList<HeatingSystemControlType>(HeatingSystemControlType.class, this, ITechnologiesPackage.WARM_AIR_SYSTEM__CONTROLS);
        }
        return controls;
    }

    /**
     * <!-- begin-user-doc -->
     * The accept method here presents a heating system and a heat transducer to
     * the visitor, if the heating is on.
     * <!-- end-user-doc -->
     *
     * @generated no
     */
    @Override
    public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
        visitor.visitHeatingSystem(this, heatProportions.spaceHeatingProportion(this));
        final double effectiveProportion = heatProportions.spaceHeatingProportion(this);

        /*
		BEISDOC
		NAME: Warm Air System Fuel Energy Demand
		DESCRIPTION: The fuel energy used by a room heater to provide space heating.
		TYPE: formula
		UNIT: W
		SAP: (211)
                SAP_COMPLIANT: Yes
		BREDEM: 8J,8K
                BREDEM_COMPLIANT: Yes
		DEPS: heat-demand,space-heating-fraction
		NOTES: This code constructs a 'heat transducer', which is an object in the energy calculator which models converting fuel into heat.
		ID: warm-air-system-fuel-energy-demand
		CODSIEB
         */
        if (getFuelType() == FuelType.ELECTRICITY) {
            visitor.visitEnergyTransducer(new ElectricHeatTransducer(effectiveProportion, heatingSystemCounter.getAndIncrement()) {
                @Override
                protected double getHighRateFraction(final IEnergyCalculatorHouseCase house,
                        final IInternalParameters parameters, final ISpecificHeatLosses losses,
                        final IEnergyState state) {
                    return parameters.getConstants().get(getSplitRateConstants(), parameters.getTarrifType());
                }

            });
        } else {
            visitor.visitEnergyTransducer(new HeatTransducer(getFuelType().getEnergyType(), getEfficiency().value,
                    effectiveProportion, true, heatingSystemCounter.getAndIncrement(), ServiceType.PRIMARY_SPACE_HEATING));
        }

        visitor.visitEnergyTransducer(new WarmAirFans());

        if (effectiveProportion > 0) {
            StepRecorder.recordStep(
                    EnergyCalculationStep.SpaceHeating_Efficiency_Main_System1,
                    getFuelType() == FuelType.ELECTRICITY ? 1 : getEfficiency().value
            );
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated no
     */
    @Override
    public double getResponsiveness(final IConstants constants, final EnergyCalculatorType calculatorType, final ElectricityTariffType electricityTariffType) {
        if (getFuelType() == FuelType.ELECTRICITY) {
            return 0.75; //TODO remove magic number
        } else {
            return 1;
        }
    }

    protected SplitRateConstants getSplitRateConstants() {
        return SplitRateConstants.DIRECT_ELECTRIC_FRACTIONS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR:
                if (circulator != null) {
                    msgs = ((InternalEObject) circulator).eInverseRemove(this, ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM, IWarmAirCirculator.class, msgs);
                }
                return basicSetCirculator((IWarmAirCirculator) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR:
                return basicSetCirculator(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ITechnologiesPackage.WARM_AIR_SYSTEM__FUEL_TYPE:
                return getFuelType();
            case ITechnologiesPackage.WARM_AIR_SYSTEM__EFFICIENCY:
                return getEfficiency();
            case ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR:
                if (resolve) {
                    return getCirculator();
                }
                return basicGetCirculator();
            case ITechnologiesPackage.WARM_AIR_SYSTEM__CONTROLS:
                return getControls();
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
            case ITechnologiesPackage.WARM_AIR_SYSTEM__FUEL_TYPE:
                setFuelType((FuelType) newValue);
                return;
            case ITechnologiesPackage.WARM_AIR_SYSTEM__EFFICIENCY:
                setEfficiency((Efficiency) newValue);
                return;
            case ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR:
                setCirculator((IWarmAirCirculator) newValue);
                return;
            case ITechnologiesPackage.WARM_AIR_SYSTEM__CONTROLS:
                getControls().clear();
                getControls().addAll((Collection<? extends HeatingSystemControlType>) newValue);
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
            case ITechnologiesPackage.WARM_AIR_SYSTEM__FUEL_TYPE:
                setFuelType(FUEL_TYPE_EDEFAULT);
                return;
            case ITechnologiesPackage.WARM_AIR_SYSTEM__EFFICIENCY:
                setEfficiency(EFFICIENCY_EDEFAULT);
                return;
            case ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR:
                setCirculator((IWarmAirCirculator) null);
                return;
            case ITechnologiesPackage.WARM_AIR_SYSTEM__CONTROLS:
                getControls().clear();
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
            case ITechnologiesPackage.WARM_AIR_SYSTEM__FUEL_TYPE:
                return (flags & FUEL_TYPE_EFLAG) != FUEL_TYPE_EFLAG_DEFAULT;
            case ITechnologiesPackage.WARM_AIR_SYSTEM__EFFICIENCY:
                return EFFICIENCY_EDEFAULT == null ? efficiency != null : !EFFICIENCY_EDEFAULT.equals(efficiency);
            case ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR:
                return circulator != null;
            case ITechnologiesPackage.WARM_AIR_SYSTEM__CONTROLS:
                return controls != null && !controls.isEmpty();
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
        result.append(" (fuelType: ");
        result.append(FUEL_TYPE_EFLAG_VALUES[(flags & FUEL_TYPE_EFLAG) >>> FUEL_TYPE_EFLAG_OFFSET]);
        result.append(", efficiency: ");
        result.append(efficiency);
        result.append(", controls: ");
        result.append(controls);
        result.append(')');
        return result.toString();
    }

    public boolean isThermostaticallyControlled() {
        final EList<HeatingSystemControlType> c = getControls();
        if (c.contains(HeatingSystemControlType.APPLIANCE_THERMOSTAT)
                || c.contains(HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL)
                || c.contains(HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE)
                || c.contains(HeatingSystemControlType.ROOM_THERMOSTAT)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * From SAP table 4e group 5
     */
    @Override
    public double getDemandTemperatureAdjustment(final IInternalParameters parameters) {
        // if no thermostatic control of room temperature, bump demand temperature by 0.3 deg
        if (!isThermostaticallyControlled()) {
            return parameters.getConstants().get(TemperatureAdjustments.WARM_AIR_SYSTEM_NO_THERMOSTAT);
        } else {
            return 0;
        }
    }

    /**
     * From SAP table 4e group 5
     *
     * Ignore the row 'Programmer and at least two room thermostats': we don't
     * have this information.
     */
    @Override
    public Zone2ControlParameter getZoneTwoControlParameter(final IInternalParameters parameters) {
        if (getControls().contains(HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL)) {
            return Zone2ControlParameter.Three;
        } else {
            return Zone2ControlParameter.One;
        }
    }

    @Override
    public FuelType getFuel() {
        return getFuelType();
    }
} //WarmAirSystemImpl
