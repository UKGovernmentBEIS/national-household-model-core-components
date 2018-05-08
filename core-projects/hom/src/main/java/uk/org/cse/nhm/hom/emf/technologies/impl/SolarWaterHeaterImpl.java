/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.constants.SolarConstants;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IOperationalCost;
import uk.org.cse.nhm.hom.emf.technologies.IShower;
import uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.Pump;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Solar Water Heater</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl#getAnnualOperationalCost
 * <em>Annual Operational Cost</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl#getPitch
 * <em>Pitch</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl#getOrientation
 * <em>Orientation</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl#getArea
 * <em>Area</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl#getUsefulAreaRatio
 * <em>Useful Area Ratio</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl#getZeroLossEfficiency
 * <em>Zero Loss Efficiency</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl#getLinearHeatLossCoefficient
 * <em>Linear Heat Loss Coefficient</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl#getPreHeatTankVolume
 * <em>Pre Heat Tank Volume</em>}</li>
 * <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.SolarWaterHeaterImpl#isPumpPhotovolatic
 * <em>Pump Photovolatic</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SolarWaterHeaterImpl extends CentralWaterHeaterImpl implements ISolarWaterHeater {

    private final double SAP_BATH_ONLY_HOT_WATER_USE_ADJUSTMENT = 1.09;

    /**
     * The default value of the '{@link #getAnnualOperationalCost() <em>Annual
     * Operational Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getAnnualOperationalCost()
     * @generated
     * @ordered
     */
    protected static final double ANNUAL_OPERATIONAL_COST_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getAnnualOperationalCost() <em>Annual
     * Operational Cost</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getAnnualOperationalCost()
     * @generated
     * @ordered
     */
    protected double annualOperationalCost = ANNUAL_OPERATIONAL_COST_EDEFAULT;

    private static final Logger log = LoggerFactory.getLogger(SolarWaterHeaterImpl.class);

    /**
     * The default value of the '{@link #getPitch() <em>Pitch</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPitch()
     * @generated
     * @ordered
     */
    protected static final double PITCH_EDEFAULT = 0.0;
    /**
     * The cached value of the '{@link #getPitch() <em>Pitch</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPitch()
     * @generated
     * @ordered
     */
    protected double pitch = PITCH_EDEFAULT;
    /**
     * The default value of the '{@link #getOrientation() <em>Orientation</em>}'
     * attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getOrientation()
     * @generated
     * @ordered
     */
    protected static final double ORIENTATION_EDEFAULT = 0.0;
    /**
     * The cached value of the '{@link #getOrientation() <em>Orientation</em>}'
     * attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getOrientation()
     * @generated
     * @ordered
     */
    protected double orientation = ORIENTATION_EDEFAULT;
    /**
     * The default value of the '{@link #getArea() <em>Area</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getArea()
     * @generated
     * @ordered
     */
    protected static final double AREA_EDEFAULT = 0.0;
    /**
     * The cached value of the '{@link #getArea() <em>Area</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getArea()
     * @generated
     * @ordered
     */
    protected double area = AREA_EDEFAULT;
    /**
     * The default value of the '{@link #getUsefulAreaRatio() <em>Useful Area
     * Ratio</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getUsefulAreaRatio()
     * @generated
     * @ordered
     */
    protected static final double USEFUL_AREA_RATIO_EDEFAULT = 0.0;
    /**
     * The cached value of the '{@link #getUsefulAreaRatio() <em>Useful Area
     * Ratio</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getUsefulAreaRatio()
     * @generated
     * @ordered
     */
    protected double usefulAreaRatio = USEFUL_AREA_RATIO_EDEFAULT;
    /**
     * The default value of the '{@link #getZeroLossEfficiency() <em>Zero Loss
     * Efficiency</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getZeroLossEfficiency()
     * @generated
     * @ordered
     */
    protected static final double ZERO_LOSS_EFFICIENCY_EDEFAULT = 0.0;
    /**
     * The cached value of the '{@link #getZeroLossEfficiency() <em>Zero Loss
     * Efficiency</em>}' attribute.
     * <!-- begin-user-doc -->
     * BEISDOC NAME: Zero Loss Efficiency DESCRIPTION: TYPE: value UNIT:
     * Dimensionless SAP: (H2) SAP_COMPLIANT: No, user defined BREDEM: User
     * input BREDEM_COMPLIANT: N/A - user defined SET: measure.solar-dhw STOCK:
     * Set to 4 based on Cambridge Household Model assumptions ID:
     * zero-loss-efficiency CODSIEB
     * <!-- end-user-doc -->
     *
     * @see #getZeroLossEfficiency()
     * @generated
     * @ordered
     */
    protected double zeroLossEfficiency = ZERO_LOSS_EFFICIENCY_EDEFAULT;
    /**
     * The default value of the '{@link #getLinearHeatLossCoefficient()
     * <em>Linear Heat Loss Coefficient</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getLinearHeatLossCoefficient()
     * @generated
     * @ordered
     */

    protected static final double LINEAR_HEAT_LOSS_COEFFICIENT_EDEFAULT = 0.0;
    /**
     * The cached value of the '{@link #getLinearHeatLossCoefficient()
     * <em>Linear Heat Loss Coefficient</em>}' attribute.
     * <!-- begin-user-doc -->
     * BEISDOC NAME: Linear Heat Loss Coefficient DESCRIPTION: TYPE: value UNIT:
     * Dimensionless SAP: (H3b) SAP_COMPLIANT: No, user defined BREDEM: User
     * input BREDEM_COMPLIANT: N/A - user defined SET: measure.solar-dhw STOCK:
     * Set to 0.8 based on Cambridge Household Model assumptions ID:
     * linear-heat-loss-coefficient CODSIEB
     * <!-- end-user-doc -->
     *
     * @see #getLinearHeatLossCoefficient()
     * @generated
     * @ordered
     */
    protected double linearHeatLossCoefficient = LINEAR_HEAT_LOSS_COEFFICIENT_EDEFAULT;
    /**
     * The default value of the '{@link #getPreHeatTankVolume() <em>Pre Heat
     * Tank Volume</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPreHeatTankVolume()
     * @generated
     * @ordered
     */
    protected static final double PRE_HEAT_TANK_VOLUME_EDEFAULT = 0.0;

    /**
     * The cached value of the '{@link #getPreHeatTankVolume() <em>Pre Heat Tank
     * Volume</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #getPreHeatTankVolume()
     * @generated
     * @ordered
     */
    protected double preHeatTankVolume = PRE_HEAT_TANK_VOLUME_EDEFAULT;

    /**
     * The default value of the '{@link #isPumpPhotovolatic() <em>Pump
     * Photovolatic</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #isPumpPhotovolatic()
     * @generated
     * @ordered
     */
    protected static final boolean PUMP_PHOTOVOLATIC_EDEFAULT = false;

    /**
     * The flag representing the value of the '{@link #isPumpPhotovolatic()
     * <em>Pump Photovolatic</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #isPumpPhotovolatic()
     * @generated
     * @ordered
     */
    protected static final int PUMP_PHOTOVOLATIC_EFLAG = 1 << 0;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    protected SolarWaterHeaterImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ITechnologiesPackage.Literals.SOLAR_WATER_HEATER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public double getAnnualOperationalCost() {
        return annualOperationalCost;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setAnnualOperationalCost(double newAnnualOperationalCost) {
        double oldAnnualOperationalCost = annualOperationalCost;
        annualOperationalCost = newAnnualOperationalCost;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_WATER_HEATER__ANNUAL_OPERATIONAL_COST, oldAnnualOperationalCost, annualOperationalCost));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public double getPitch() {
        return pitch;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setPitch(double newPitch) {
        double oldPitch = pitch;
        pitch = newPitch;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_WATER_HEATER__PITCH, oldPitch, pitch));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public double getOrientation() {
        return orientation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setOrientation(double newOrientation) {
        double oldOrientation = orientation;
        orientation = newOrientation;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_WATER_HEATER__ORIENTATION, oldOrientation, orientation));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public double getArea() {
        return area;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setArea(double newArea) {
        double oldArea = area;
        area = newArea;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_WATER_HEATER__AREA, oldArea, area));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public double getUsefulAreaRatio() {
        return usefulAreaRatio;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setUsefulAreaRatio(double newUsefulAreaRatio) {
        double oldUsefulAreaRatio = usefulAreaRatio;
        usefulAreaRatio = newUsefulAreaRatio;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_WATER_HEATER__USEFUL_AREA_RATIO, oldUsefulAreaRatio, usefulAreaRatio));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public double getZeroLossEfficiency() {
        return zeroLossEfficiency;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setZeroLossEfficiency(double newZeroLossEfficiency) {
        double oldZeroLossEfficiency = zeroLossEfficiency;
        zeroLossEfficiency = newZeroLossEfficiency;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_WATER_HEATER__ZERO_LOSS_EFFICIENCY, oldZeroLossEfficiency, zeroLossEfficiency));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public double getLinearHeatLossCoefficient() {
        return linearHeatLossCoefficient;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setLinearHeatLossCoefficient(double newLinearHeatLossCoefficient) {
        double oldLinearHeatLossCoefficient = linearHeatLossCoefficient;
        linearHeatLossCoefficient = newLinearHeatLossCoefficient;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_WATER_HEATER__LINEAR_HEAT_LOSS_COEFFICIENT, oldLinearHeatLossCoefficient, linearHeatLossCoefficient));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public double getPreHeatTankVolume() {
        return preHeatTankVolume;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setPreHeatTankVolume(double newPreHeatTankVolume) {
        double oldPreHeatTankVolume = preHeatTankVolume;
        preHeatTankVolume = newPreHeatTankVolume;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_WATER_HEATER__PRE_HEAT_TANK_VOLUME, oldPreHeatTankVolume, preHeatTankVolume));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public boolean isPumpPhotovolatic() {
        return (flags & PUMP_PHOTOVOLATIC_EFLAG) != 0;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setPumpPhotovolatic(boolean newPumpPhotovolatic) {
        boolean oldPumpPhotovolatic = (flags & PUMP_PHOTOVOLATIC_EFLAG) != 0;
        if (newPumpPhotovolatic) {
            flags |= PUMP_PHOTOVOLATIC_EFLAG;
        } else {
            flags &= ~PUMP_PHOTOVOLATIC_EFLAG;
        }
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.SOLAR_WATER_HEATER__PUMP_PHOTOVOLATIC, oldPumpPhotovolatic, newPumpPhotovolatic));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * adds in the pump load from electric pump
     * <!-- end-user-doc -->
     *
     * @generated no
     */
    @Override
    public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
        if (!isPumpPhotovolatic() && getSystem() != null) {
            visitor.visitEnergyTransducer(new Pump("Solar Pump", ServiceType.WATER_HEATING, constants.get(SolarConstants.CIRCULATION_PUMP_WATTAGE), 0,
                    EnergyCalculationStep.PumpsFansAndKeepHot_SolarWaterHeatingPump));
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ANNUAL_OPERATIONAL_COST:
                return getAnnualOperationalCost();
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PITCH:
                return getPitch();
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ORIENTATION:
                return getOrientation();
            case ITechnologiesPackage.SOLAR_WATER_HEATER__AREA:
                return getArea();
            case ITechnologiesPackage.SOLAR_WATER_HEATER__USEFUL_AREA_RATIO:
                return getUsefulAreaRatio();
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ZERO_LOSS_EFFICIENCY:
                return getZeroLossEfficiency();
            case ITechnologiesPackage.SOLAR_WATER_HEATER__LINEAR_HEAT_LOSS_COEFFICIENT:
                return getLinearHeatLossCoefficient();
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PRE_HEAT_TANK_VOLUME:
                return getPreHeatTankVolume();
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PUMP_PHOTOVOLATIC:
                return isPumpPhotovolatic();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ANNUAL_OPERATIONAL_COST:
                setAnnualOperationalCost((Double) newValue);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PITCH:
                setPitch((Double) newValue);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ORIENTATION:
                setOrientation((Double) newValue);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__AREA:
                setArea((Double) newValue);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__USEFUL_AREA_RATIO:
                setUsefulAreaRatio((Double) newValue);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ZERO_LOSS_EFFICIENCY:
                setZeroLossEfficiency((Double) newValue);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__LINEAR_HEAT_LOSS_COEFFICIENT:
                setLinearHeatLossCoefficient((Double) newValue);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PRE_HEAT_TANK_VOLUME:
                setPreHeatTankVolume((Double) newValue);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PUMP_PHOTOVOLATIC:
                setPumpPhotovolatic((Boolean) newValue);
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
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ANNUAL_OPERATIONAL_COST:
                setAnnualOperationalCost(ANNUAL_OPERATIONAL_COST_EDEFAULT);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PITCH:
                setPitch(PITCH_EDEFAULT);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ORIENTATION:
                setOrientation(ORIENTATION_EDEFAULT);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__AREA:
                setArea(AREA_EDEFAULT);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__USEFUL_AREA_RATIO:
                setUsefulAreaRatio(USEFUL_AREA_RATIO_EDEFAULT);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ZERO_LOSS_EFFICIENCY:
                setZeroLossEfficiency(ZERO_LOSS_EFFICIENCY_EDEFAULT);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__LINEAR_HEAT_LOSS_COEFFICIENT:
                setLinearHeatLossCoefficient(LINEAR_HEAT_LOSS_COEFFICIENT_EDEFAULT);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PRE_HEAT_TANK_VOLUME:
                setPreHeatTankVolume(PRE_HEAT_TANK_VOLUME_EDEFAULT);
                return;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PUMP_PHOTOVOLATIC:
                setPumpPhotovolatic(PUMP_PHOTOVOLATIC_EDEFAULT);
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
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ANNUAL_OPERATIONAL_COST:
                return annualOperationalCost != ANNUAL_OPERATIONAL_COST_EDEFAULT;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PITCH:
                return pitch != PITCH_EDEFAULT;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ORIENTATION:
                return orientation != ORIENTATION_EDEFAULT;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__AREA:
                return area != AREA_EDEFAULT;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__USEFUL_AREA_RATIO:
                return usefulAreaRatio != USEFUL_AREA_RATIO_EDEFAULT;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__ZERO_LOSS_EFFICIENCY:
                return zeroLossEfficiency != ZERO_LOSS_EFFICIENCY_EDEFAULT;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__LINEAR_HEAT_LOSS_COEFFICIENT:
                return linearHeatLossCoefficient != LINEAR_HEAT_LOSS_COEFFICIENT_EDEFAULT;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PRE_HEAT_TANK_VOLUME:
                return preHeatTankVolume != PRE_HEAT_TANK_VOLUME_EDEFAULT;
            case ITechnologiesPackage.SOLAR_WATER_HEATER__PUMP_PHOTOVOLATIC:
                return ((flags & PUMP_PHOTOVOLATIC_EFLAG) != 0) != PUMP_PHOTOVOLATIC_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == IVisitorAccepter.class) {
            switch (derivedFeatureID) {
                default:
                    return -1;
            }
        }
        if (baseClass == IOperationalCost.class) {
            switch (derivedFeatureID) {
                case ITechnologiesPackage.SOLAR_WATER_HEATER__ANNUAL_OPERATIONAL_COST:
                    return ITechnologiesPackage.OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST;
                default:
                    return -1;
            }
        }
        return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
        if (baseClass == IVisitorAccepter.class) {
            switch (baseFeatureID) {
                default:
                    return -1;
            }
        }
        if (baseClass == IOperationalCost.class) {
            switch (baseFeatureID) {
                case ITechnologiesPackage.OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST:
                    return ITechnologiesPackage.SOLAR_WATER_HEATER__ANNUAL_OPERATIONAL_COST;
                default:
                    return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
        result.append(" (annualOperationalCost: ");
        result.append(annualOperationalCost);
        result.append(", pitch: ");
        result.append(pitch);
        result.append(", orientation: ");
        result.append(orientation);
        result.append(", area: ");
        result.append(area);
        result.append(", usefulAreaRatio: ");
        result.append(usefulAreaRatio);
        result.append(", zeroLossEfficiency: ");
        result.append(zeroLossEfficiency);
        result.append(", linearHeatLossCoefficient: ");
        result.append(linearHeatLossCoefficient);
        result.append(", preHeatTankVolume: ");
        result.append(preHeatTankVolume);
        result.append(", pumpPhotovolatic: ");
        result.append((flags & PUMP_PHOTOVOLATIC_EFLAG) != 0);
        result.append(')');
        return result.toString();
    }

    @Override
    public boolean isSolar() {
        return true;
    }

    protected double getCollectorPerformanceFactor(final IConstants constants) {
        /*
		BEISDOC
		NAME: Collector Performance Ratio
		DESCRIPTION: Linear heat loss coefficient divided by zero loss efficiency
		TYPE: formula
		UNIT: ???
		SAP: (H4)
                SAP_COMPLIANT: Yes
		BREDEM: 2.4.2B
                BREDEM_COMPLIANT: Yes
		DEPS: linear-heat-loss-coefficient,zero-loss-efficiency
		ID: collector-performance-ratio
		CODSIEB
         */
        final double hlcOverE = linearHeatLossCoefficient / zeroLossEfficiency;

        /*
		BEISDOC
		NAME: Collector Performance Factor
		DESCRIPTION:
		TYPE: formula
		UNIT: ???
		SAP: (H10)
                SAP_COMPLIANT: Yes
		BREDEM: 2.4.2B
                BREDEM_COMPLIANT: Yes
		DEPS: collector-performance-ratio,collector-performance-ratio-threshold,collector-performance-factor-lower-terms,collector-performance-factor-higher-terms
		ID: collector-performance-factor
		CODSIEB
         */
        final double[] terms
                = hlcOverE < constants.get(SolarConstants.HLC_OVER_E_THRESHOLD)
                ? constants.get(SolarConstants.LOW_HLC_EXPANSION, double[].class)
                : constants.get(SolarConstants.HIGH_HLC_EXPANSION, double[].class);

        return terms[0] + terms[1] * hlcOverE + terms[2] * hlcOverE * hlcOverE;
    }

    @Override
    public double generateHotWaterAndPrimaryGains(final IInternalParameters parameters,
            final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
            final double primaryPipeworkLosses, final double distributionLossFactor,
            final double systemProportion) {
        final IConstants constants = parameters.getConstants();
        final double solarRadiation = parameters.getClimate().getSolarFlux(getPitch(), getOrientation());

        final OvershadingType overshading = OvershadingType.AVERAGE;
        final double overshadingFactor = constants.get(SolarConstants.OVERSHADING_FACTOR, double[].class)[overshading.ordinal()];

        final double performanceFactor = getCollectorPerformanceFactor(constants);
        // TODO this is incorrect if the central heating system doesn't end up generating all the hot water demand
        final double remainingHotWaterDemand = state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER);
        if (remainingHotWaterDemand == 0) {
            log.debug("No hot water demand remaining");
            return 0;
        }

        /*
		BEISDOC
		NAME: Solar Energy Available
		DESCRIPTION:
		TYPE: formula
		UNIT: W
		SAP: (H7)
                SAP_COMPLIANT: Yes
		BREDEM: 2.4.2C (expression in parentheses)
                BREDEM_COMPLIANT: Yes
		DEPS: solar-thermal-collector-area,solar-overshading-factor,effective-solar-flux,zero-loss-efficiency
		ID: solar-energy-available
		CODSIEB
         */
        final double solarEnergyAvailable = area * solarRadiation * overshadingFactor * zeroLossEfficiency;


        /*
		BEISDOC
		NAME: Solar to Load Ratio
		DESCRIPTION: The ratio of available solar energy to hot water demand, with some wrangling for distribution losses.
		TYPE: formula
		UNIT: Dimensionless
		SAP: (H8)
                SAP_COMPLIANT: Yes
		BREDEM: 2.4.2C
                BREDEM_COMPLIANT: Yes
		DEPS: solar-energy-available,distribution-loss-factor,water-heating-power,solar-hot-water-use-adjustment
		ID: solar-to-load-ratio
		CODSIEB
         */
        final double loadRatio = solarEnergyAvailable * getHotWaterUseAdjustmentFactor(parameters) / (remainingHotWaterDemand * (1 + distributionLossFactor));

        log.debug("load ratio {} = {} m2 * {} * {} * {} / {}", loadRatio, area, zeroLossEfficiency, solarRadiation, overshadingFactor, remainingHotWaterDemand);

        /*
		BEISDOC
		NAME: Solar Utilisation Factor
		DESCRIPTION: Utilisation factor for solar hot water.
		TYPE: formula
		UNIT: Dimensionless
		SAP: (H9)
                SAP_COMPLIANT: Yes
		BREDEM: 2.4.2D
                BREDEM_COMPLIANT: Yes
		DEPS: solar-utilisation-factor-thermostat-factor,solar-to-load-ratio
		ID: solar-utilisation-factor
		CODSIEB
         */
        final double utilisationFactor;
        if (store != null && !store.isThermostatFitted()) {
            utilisationFactor = (1 - Math.exp(-1 / loadRatio)) * constants.get(SolarConstants.UTILISATION_FACTOR_THERMOSTAT_FACTOR);
        } else {
            utilisationFactor = 1 - Math.exp(-1 / loadRatio);
        }

        //		IF(AND(SolarLoad>0,DHWCylinderstat=1),
        //         (1-EXP(-1/SolarLoad))*0.9,
        //         IF(AND(SolarLoad>0,DHWCylinderstat=2),1-EXP(-1/SolarLoad),0))
        // this only models twin-coil combined cylinder store
        // perhaps I should do something with the tank to allow for this stuff.
        // basically the two options are:
        // pre-heat tank
        // twin-coil cylinder

        /*
		BEISDOC
		NAME: Solar Store Effective Volume
		DESCRIPTION: The effective volume of a solar store
		TYPE: formula
		UNIT: Litres
		SAP: (H13)
                SAP_COMPLIANT: Yes
		BREDEM: User input, 2.4.2 (footnote)
                BREDEM_COMPLIANT: Yes
		DEPS: solar-dedicated-store-volume,solar-system-store-volume
		ID: solar-store-effective-volume
		CODSIEB
         */
        // issue here is that BREDEM does not appear to model losses from a combi preheat tank,
        // so it can't be the tank in the hot water system.
        final double effectiveSolarVolume;
        if (store == null) {
            /*
			BEISDOC
			NAME: Separate Solar Store Volume
			DESCRIPTION: The volume of a hot water store incorporated into a solar heating system.
			TYPE: value
			UNIT: Litres
			SAP: (H11, H13)
                        SAP_COMPLIANT: Yes
			BREDEM: User input, 2.4.2 (footnote)
                        BREDEM_COMPLIANT: Yes
			STOCK: water-heating.csv (solarstorevolume if either solarstoreincylinder is 'true' or cylindervolume is not present or equal to 0)
			ID: solar-dedicated-store-volume
			CODSIEB
             */
            effectiveSolarVolume = getPreHeatTankVolume();
            log.debug("Hot water tank is null - effective solar volume = {}", effectiveSolarVolume);
        } else {
            /*
			BEISDOC
			NAME: Solar System Store Volume
			DESCRIPTION: For a hot water cylinder which is part of a central hot water system, what volume of it is used for solar hot water.
			TYPE: formula
			UNIT: Litres
			SAP: (H12, H13)
                        SAP_COMPLIANT: Yes
			BREDEM: User input, 2.4.2 (footnote)
                        BREDEM_COMPLIANT: Yes
			DEPS: cylinder-volume,solar-cylinder-remainder
			STOCK: water-heating.csv (solarstorevolume)
			ID: solar-system-store-volume
			CODSIEB
             */
            final double ssv = store.getSolarStorageVolume();
            final double cylinderRemainder = constants.get(SolarConstants.CYLINDER_REMAINDER_FACTOR);
            if (ssv == 0) {
                // in the case of a direct system where there is no dedicated solar storage, Veff is 0.3
                // times the volume of the cylinder
                effectiveSolarVolume = cylinderRemainder * store.getVolume();
                log.debug("Hot water tank has no solar storage, effective solar volume is {}", effectiveSolarVolume);
            } else {
                if (storeIsPrimary) {
                    effectiveSolarVolume = ssv;
                    log.debug("Hot water tank is primary, effective solar volume is {}", effectiveSolarVolume);
                } else {
                    effectiveSolarVolume = ssv + cylinderRemainder * (store.getVolume() - ssv);
                    log.debug("Hot water tank is secondary, effective solar volume is {}", effectiveSolarVolume);
                }
            }
        }

        final double minVolumeFactor = constants.get(SolarConstants.MINIMUM_VOLUME_FACTOR);

        // Solar water heating happens first, so this should always be equal to the actual volume of hot water demanded.
        final double remainingVolume = state.getTotalDemand(EnergyType.DemandsHOT_WATER_VOLUME) * remainingHotWaterDemand / state.getTotalDemand(EnergyType.DemandsHOT_WATER);

        /*
		BEISDOC
		NAME: Solar Storage Volume Factor
		DESCRIPTION:
		TYPE: formula
		UNIT: Dimensionless
		SAP: (H15, H16)
                SAP_COMPLIANT: Yes
		BREDEM: 2.4.2E
                BREDEM_COMPLIANT: Yes
		DEPS: solar-store-effective-volume,water-volume,solar-min-volume-factor,solar-volume-factor-coefficient
		ID: solar-storage-volume-factor
		CODSIEB
         */
        final double solarStorageVolumeFactor
                = Math.max(0, Math.min(minVolumeFactor,
                        minVolumeFactor
                        + constants.get(SolarConstants.VOLUME_FACTOR_COEFFICIENT)
                        * Math.log(effectiveSolarVolume / remainingVolume)));

        /*
		BEISDOC
		NAME: Solar Hot Water Output
		DESCRIPTION: The amount of hot water demand satisfied by a solar water heater.
		TYPE: formula
		UNIT: W
		SAP: (64,H17)
                SAP_COMPLIANT: Yes
		BREDEM: 2.4.2G
                BREDEM_COMPLIANT: Yes
		DEPS: solar-energy-available,solar-utilisation-factor,collector-performance-factor,solar-storage-volume-factor
		ID: solar-hot-water-output
		CODSIEB
         */
        final double demandSatisfied
                = solarEnergyAvailable
                * utilisationFactor
                * performanceFactor
                * solarStorageVolumeFactor;

        if (log.isDebugEnabled()) {
            log.debug("effective solar volume = {}", effectiveSolarVolume);
            log.debug("utilisation factor = {}", utilisationFactor);
            log.debug("performance factor = {}", performanceFactor);
            log.debug("storage volume factor = {}", solarStorageVolumeFactor);
            log.debug("solar generation = {}", demandSatisfied);
        }

        state.increaseDemand(EnergyType.FuelPHOTONS, solarRadiation * area * overshadingFactor);
        state.increaseSupply(EnergyType.DemandsHOT_WATER, demandSatisfied);
        StepRecorder.recordStep(EnergyCalculationStep.WaterHeating_Solar, demandSatisfied);

        return demandSatisfied;
    }

    private double getHotWaterUseAdjustmentFactor(final IInternalParameters parameters) {
        /*
		BEISDOC
		NAME: Solar Hot Water Use Adjustment
		DESCRIPTION: An adjustment to the solar-to-load ratio based on which kinds of showers are present in the dwelling.
		TYPE: formula
		UNIT: Dimensionless
                BREDEM_COMPLIANT: BREDEM mode only
		SAP: (H7a), Table H3
                SAP_COMPLIANT: SAP mode only
		NOTES: Only has an effect in SAP 2012 mode.
		ID: solar-hot-water-use-adjustment
		CODSIEB
         */
        switch (parameters.getCalculatorType().hotWater) {
            case BREDEM2012:
                return 1.0;
            case SAP2012:
                final IShower shower = getSystem().getTechnologyModel().getShower();
                if (shower == null) {
                    return SAP_BATH_ONLY_HOT_WATER_USE_ADJUSTMENT;
                } else {
                    return shower.solarAdjustment();
                }
            default:
                throw new UnsupportedOperationException("Unknown energy calculator type when calculating solar hot water use adjustment " + parameters.getCalculatorType());
        }
    }

    @Override
    public void generateSystemGains(final IInternalParameters parameters,
            final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
            final double systemLosses) {
        // do nothing intentionally.
    }

    @Override
    public boolean causesPipeworkLosses() {
        return false;
    }

    @Override
    public boolean isCommunityHeating() {
        return false;
    }

    @Override
    public FuelType getFuel() {
        return FuelType.PHOTONS;
    }
} //SolarWaterHeaterImpl
