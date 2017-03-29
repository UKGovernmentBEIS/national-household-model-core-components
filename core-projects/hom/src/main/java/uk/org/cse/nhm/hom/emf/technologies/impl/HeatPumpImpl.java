/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.ElectricHeatTransducer;
import uk.org.cse.nhm.energycalculator.api.impl.HeatTransducer;
import uk.org.cse.nhm.energycalculator.api.impl.HybridHeatpumpTransducer;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.constants.PumpAndFanConstants;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;
import uk.org.cse.nhm.hom.constants.adjustments.TemperatureAdjustments;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHybridHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.util.FlueVentilationHelper;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.Pump;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Heat Pump</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl#getSourceType <em>Source Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl#getCoefficientOfPerformance <em>Coefficient Of Performance</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl#isWeatherCompensated <em>Weather Compensated</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl#isAuxiliaryPresent <em>Auxiliary Present</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpImpl#getHybrid <em>Hybrid</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HeatPumpImpl extends HeatSourceImpl implements IHeatPump {
	/**
	 * The default value of the '{@link #getSourceType() <em>Source Type</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSourceType()
	 * @generated
	 * @ordered
	 */
	protected static final HeatPumpSourceType SOURCE_TYPE_EDEFAULT = HeatPumpSourceType.GROUND;
	/**
	 * The offset of the flags representing the value of the '{@link #getSourceType() <em>Source Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int SOURCE_TYPE_EFLAG_OFFSET = 8;
	/**
	 * The flags representing the default value of the '{@link #getSourceType() <em>Source Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int SOURCE_TYPE_EFLAG_DEFAULT = SOURCE_TYPE_EDEFAULT.ordinal() << SOURCE_TYPE_EFLAG_OFFSET;
	/**
	 * The array of enumeration values for '{@link HeatPumpSourceType Heat Pump Source Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final HeatPumpSourceType[] SOURCE_TYPE_EFLAG_VALUES = HeatPumpSourceType.values();
	/**
	 * The flag representing the value of the '{@link #getSourceType() <em>Source Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceType()
	 * @generated
	 * @ordered
	 */
	protected static final int SOURCE_TYPE_EFLAG = 1 << SOURCE_TYPE_EFLAG_OFFSET;
	/**
	 * The default value of the '{@link #getCoefficientOfPerformance() <em>Coefficient Of Performance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoefficientOfPerformance()
	 * @generated
	 * @ordered
	 */
	protected static final Efficiency COEFFICIENT_OF_PERFORMANCE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getCoefficientOfPerformance() <em>Coefficient Of Performance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoefficientOfPerformance()
	 * @generated
	 * @ordered
	 */
	protected Efficiency coefficientOfPerformance = COEFFICIENT_OF_PERFORMANCE_EDEFAULT;

	/**
	 * The default value of the '{@link #isWeatherCompensated() <em>Weather Compensated</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isWeatherCompensated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WEATHER_COMPENSATED_EDEFAULT = false;
	/**
	 * The flag representing the value of the '{@link #isWeatherCompensated() <em>Weather Compensated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWeatherCompensated()
	 * @generated
	 * @ordered
	 */
	protected static final int WEATHER_COMPENSATED_EFLAG = 1 << 9;
	/**
	 * The default value of the '{@link #isAuxiliaryPresent() <em>Auxiliary Present</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isAuxiliaryPresent()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUXILIARY_PRESENT_EDEFAULT = false;
	/**
	 * The flag representing the value of the '{@link #isAuxiliaryPresent() <em>Auxiliary Present</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAuxiliaryPresent()
	 * @generated
	 * @ordered
	 */
	protected static final int AUXILIARY_PRESENT_EFLAG = 1 << 10;
	/**
	 * The cached value of the '{@link #getHybrid() <em>Hybrid</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHybrid()
	 * @generated
	 * @ordered
	 */
	protected IHybridHeater hybrid;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected HeatPumpImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.HEAT_PUMP;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HeatPumpSourceType getSourceType() {
		return SOURCE_TYPE_EFLAG_VALUES[(flags & SOURCE_TYPE_EFLAG) >>> SOURCE_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceType(HeatPumpSourceType newSourceType) {
		final HeatPumpSourceType oldSourceType = SOURCE_TYPE_EFLAG_VALUES[(flags & SOURCE_TYPE_EFLAG) >>> SOURCE_TYPE_EFLAG_OFFSET];
		if (newSourceType == null) newSourceType = SOURCE_TYPE_EDEFAULT;
		flags = flags & ~SOURCE_TYPE_EFLAG | newSourceType.ordinal() << SOURCE_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__SOURCE_TYPE, oldSourceType, newSourceType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Efficiency getCoefficientOfPerformance() {
		return coefficientOfPerformance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCoefficientOfPerformance(final Efficiency newCoefficientOfPerformance) {
		final Efficiency oldCoefficientOfPerformance = coefficientOfPerformance;
		coefficientOfPerformance = newCoefficientOfPerformance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE, oldCoefficientOfPerformance, coefficientOfPerformance));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isWeatherCompensated() {
		return (flags & WEATHER_COMPENSATED_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setWeatherCompensated(final boolean newWeatherCompensated) {
		final boolean oldWeatherCompensated = (flags & WEATHER_COMPENSATED_EFLAG) != 0;
		if (newWeatherCompensated) flags |= WEATHER_COMPENSATED_EFLAG; else flags &= ~WEATHER_COMPENSATED_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__WEATHER_COMPENSATED, oldWeatherCompensated, newWeatherCompensated));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAuxiliaryPresent() {
		return (flags & AUXILIARY_PRESENT_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAuxiliaryPresent(final boolean newAuxiliaryPresent) {
		final boolean oldAuxiliaryPresent = (flags & AUXILIARY_PRESENT_EFLAG) != 0;
		if (newAuxiliaryPresent) flags |= AUXILIARY_PRESENT_EFLAG; else flags &= ~AUXILIARY_PRESENT_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__AUXILIARY_PRESENT, oldAuxiliaryPresent, newAuxiliaryPresent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IHybridHeater getHybrid() {
		return hybrid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHybrid(final IHybridHeater newHybrid, NotificationChain msgs) {
		final IHybridHeater oldHybrid = hybrid;
		hybrid = newHybrid;
		if (eNotificationRequired()) {
			final ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__HYBRID, oldHybrid, newHybrid);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHybrid(final IHybridHeater newHybrid) {
		if (newHybrid != hybrid) {
			NotificationChain msgs = null;
			if (hybrid != null)
				msgs = ((InternalEObject)hybrid).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.HEAT_PUMP__HYBRID, null, msgs);
			if (newHybrid != null)
				msgs = ((InternalEObject)newHybrid).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.HEAT_PUMP__HYBRID, null, msgs);
			msgs = basicSetHybrid(newHybrid, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP__HYBRID, newHybrid, newHybrid));
	}

	/**
	 * <!-- begin-user-doc --> Puts the flue fan into the system <!--
	 * end-user-doc -->
	 *
	 * @generated no
	 */
	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
		if (getFlueType() == FlueType.OPEN_FLUE) {
			FlueVentilationHelper.addInfiltration(visitor, getFlueType(), constants);
		}

		if (getFuel().isGas() && getFlueType() == FlueType.FAN_ASSISTED_BALANCED_FLUE) {
			visitor.visitEnergyTransducer(new Pump("Heat Pump Flue", ServiceType.PRIMARY_SPACE_HEATING, constants.get(
					PumpAndFanConstants.GAS_HEAT_PUMP_FLUE_FAN_WATTAGE), 0));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(final InternalEObject otherEnd, final int featureID, final NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_PUMP__HYBRID:
				return basicSetHybrid(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(final int featureID, final boolean resolve, final boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_PUMP__SOURCE_TYPE:
				return getSourceType();
			case ITechnologiesPackage.HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE:
				return getCoefficientOfPerformance();
			case ITechnologiesPackage.HEAT_PUMP__WEATHER_COMPENSATED:
				return isWeatherCompensated();
			case ITechnologiesPackage.HEAT_PUMP__AUXILIARY_PRESENT:
				return isAuxiliaryPresent();
			case ITechnologiesPackage.HEAT_PUMP__HYBRID:
				return getHybrid();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(final int featureID, final Object newValue) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_PUMP__SOURCE_TYPE:
				setSourceType((HeatPumpSourceType)newValue);
				return;
			case ITechnologiesPackage.HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE:
				setCoefficientOfPerformance((Efficiency)newValue);
				return;
			case ITechnologiesPackage.HEAT_PUMP__WEATHER_COMPENSATED:
				setWeatherCompensated((Boolean)newValue);
				return;
			case ITechnologiesPackage.HEAT_PUMP__AUXILIARY_PRESENT:
				setAuxiliaryPresent((Boolean)newValue);
				return;
			case ITechnologiesPackage.HEAT_PUMP__HYBRID:
				setHybrid((IHybridHeater)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(final int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_PUMP__SOURCE_TYPE:
				setSourceType(SOURCE_TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE:
				setCoefficientOfPerformance(COEFFICIENT_OF_PERFORMANCE_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_PUMP__WEATHER_COMPENSATED:
				setWeatherCompensated(WEATHER_COMPENSATED_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_PUMP__AUXILIARY_PRESENT:
				setAuxiliaryPresent(AUXILIARY_PRESENT_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_PUMP__HYBRID:
				setHybrid((IHybridHeater)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(final int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_PUMP__SOURCE_TYPE:
				return (flags & SOURCE_TYPE_EFLAG) != SOURCE_TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.HEAT_PUMP__COEFFICIENT_OF_PERFORMANCE:
				return COEFFICIENT_OF_PERFORMANCE_EDEFAULT == null ? coefficientOfPerformance != null : !COEFFICIENT_OF_PERFORMANCE_EDEFAULT.equals(coefficientOfPerformance);
			case ITechnologiesPackage.HEAT_PUMP__WEATHER_COMPENSATED:
				return ((flags & WEATHER_COMPENSATED_EFLAG) != 0) != WEATHER_COMPENSATED_EDEFAULT;
			case ITechnologiesPackage.HEAT_PUMP__AUXILIARY_PRESENT:
				return ((flags & AUXILIARY_PRESENT_EFLAG) != 0) != AUXILIARY_PRESENT_EDEFAULT;
			case ITechnologiesPackage.HEAT_PUMP__HYBRID:
				return hybrid != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated no
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		return String.format("%s %s-source HP %s%%", getFuel() == null ? "" : getFuel().getName(), getSourceType() == null ? "" : getSourceType().getName(),
				coefficientOfPerformance.value * 100);
	}

	@Override
	public void acceptFromHeating(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final double proportion, final int priority) {

		/*
		BEISDOC
		NAME: Heat Pump Fuel Energy Demand
		DESCRIPTION: The amount of fuel used by a heat pump to provide space heating.
		TYPE: formula
		UNIT: W
		SAP: (206, 211)
                SAP_COMPLIANT: Yes
		BREDEM: 8J,8K
                BREDEM_COMPLIANT: Yes
		DEPS: space-heating-fraction,heat-demand
		NOTES: This code constructs a 'heat transducer', which is an object in the energy calculator which models converting fuel into heat.
		ID: heat-pump-fuel-energy-demand
		CODSIEB
		*/

		// heat transducer is all we need
		final double spaceHeatingEfficiency = getCoefficientOfPerformance().value;

		final double highRateFraction;
		if (getSourceType() == HeatPumpSourceType.AIR) {
			highRateFraction = constants.get(SplitRateConstants.AIR_SOURCE_SPACE_HEAT, parameters.getTarrifType());
		} else {
			highRateFraction = constants.get(SplitRateConstants.GROUND_SOURCE_SPACE_HEAT, parameters.getTarrifType());
		}

		final IHybridHeater hybrid = getHybrid();
		if (hybrid == null) {
			if (getFuel() == FuelType.ELECTRICITY) {
				visitor.visitEnergyTransducer(new ElectricHeatTransducer(proportion, priority) {
					@Override
					protected double getHighRateFraction(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state) {
						return highRateFraction;
					}

					@Override
					protected double getEfficiency() {
						return spaceHeatingEfficiency;
					}
				});
			} else {
				visitor.visitEnergyTransducer(
						new HeatTransducer(getFuel().getEnergyType(), spaceHeatingEfficiency, proportion, true, priority, ServiceType.PRIMARY_SPACE_HEATING)
						);
			}

		} else {
			final double[] months = new double[12];
			for (int i = 0; i<12 && i<hybrid.getFraction().size(); i++) {
				months[i] = hybrid.getFraction().get(i);
			}
			if (getFuel() == FuelType.ELECTRICITY) {
				visitor.visitEnergyTransducer(
						new HybridHeatpumpTransducer(priority,
								highRateFraction, hybrid.getFuel().getEnergyType(),
								spaceHeatingEfficiency,
								hybrid.getEfficiency().value, proportion,
								months
								));
			} else {
				visitor.visitEnergyTransducer(
						new HybridHeatpumpTransducer(priority,
								getFuel().getEnergyType(),
								hybrid.getFuel().getEnergyType(),
								spaceHeatingEfficiency,
								hybrid.getEfficiency().value, proportion,
								months
								));
			}
		}
	}

	@Override
	public double getDemandTemperatureAdjustment(final IInternalParameters parameters, final EList<HeatingSystemControlType> controlTypes) {
		if (getSpaceHeater() != null && getSpaceHeater().isThermostaticallyControlled() == false) {
			return parameters.getConstants().get(TemperatureAdjustments.HEAT_PUMP_NO_THERMOSTAT);
		} else {
			return 0;
		}
	}

	@Override
	public double generateHotWaterAndPrimaryGains(final IInternalParameters parameters, final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
			final double primaryLosses, final double distributionLossFactor, final double proportion) {

		final double adjustedEfficiency;
		final FuelType fuel;
		if (getHybrid() == null) {
			adjustedEfficiency = getCoefficientOfPerformance().value;
			fuel = getFuel();
		} else {
			final IHybridHeater hybrid = getHybrid();
			adjustedEfficiency = hybrid.getEfficiency().value;
			fuel = hybrid.getFuel();
		}

		final double hotWaterToGenerate = state.getBoundedTotalDemand(EnergyType.DemandsHOT_WATER, proportion);

		final double totalToGenerate = hotWaterToGenerate + primaryLosses;

		state.increaseSupply(EnergyType.DemandsHOT_WATER, hotWaterToGenerate);
		state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, primaryLosses);

		final double sourceEnergy = totalToGenerate / adjustedEfficiency;

		if (fuel == FuelType.ELECTRICITY) {
			// high rate depends on presence of immersion heater, which is a bit
			// of a nuisance
			// presume immersion heater always present? do some instanceof
			// horrors?
			state.increaseElectricityDemand(getDHWHighRateFraction(parameters), sourceEnergy);
		} else {
			state.increaseDemand(fuel.getEnergyType(), sourceEnergy);
		}

		return hotWaterToGenerate;
	}

	private double getDHWHighRateFraction(final IInternalParameters parameters) {
		return parameters.getConstants().get(
				getWaterHeater().getSystem().hasImmersionHeater() ? SplitRateConstants.HEAT_PUMP_DHW_WITH_IMMERSION_HEATER
						: SplitRateConstants.HEAT_PUMP_DHW_WITHOUT_IMMERSION_HEATER, parameters.getTarrifType());
	}

	@Override
	public void generateHotWaterSystemGains(final IInternalParameters parameters, final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary, final double systemLosses) {
		final double adjustedEfficiency;
		final FuelType fuel;

		if (getHybrid() == null) {
			adjustedEfficiency = getCoefficientOfPerformance().value;
			fuel = getFuel();
		} else {
			final IHybridHeater hybrid = getHybrid();
			adjustedEfficiency = hybrid.getEfficiency().value;
			fuel = hybrid.getFuel();
		}

		state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, systemLosses);

		if (fuel == FuelType.ELECTRICITY) {
			// high rate depends on presence of immersion heater, which is a bit
			// of a nuisance
			// presume immersion heater always present? do some instanceof
			// horrors?
			state.increaseElectricityDemand(getDHWHighRateFraction(parameters), systemLosses / adjustedEfficiency);
		} else {
			state.increaseDemand(fuel.getEnergyType(), systemLosses / adjustedEfficiency);
		}
	}

	/**
	 * @assumption SAP specifies a zone two control parameter for
	 *             "Programmer and at least two room stats", but we lack
	 *             information about the number of room stats, and merely know
	 *             that there is at least one or none. Consequently we assume that
         *             there is programmer and room thermostat, corresponding to
         *             the fourth row in Group 2 of SAP table 4e.
	 */
	@Override
	public Zone2ControlParameter getZoneTwoControlParameter(final IInternalParameters parameters, final EList<HeatingSystemControlType> controls, final EmitterType emitterType) {

		if (controls.contains(HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL)) {
			return Zone2ControlParameter.Three;
		} else if (controls.containsAll(EnumSet.of(
				HeatingSystemControlType.PROGRAMMER,
				HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE,
				HeatingSystemControlType.BYPASS
			))) {
			return Zone2ControlParameter.Two;
		} else {
			return Zone2ControlParameter.One;
		}
	}

	@Override
	public double getResponsiveness(final IConstants parameters,
			final EList<HeatingSystemControlType> controls, final EmitterType emitterType) {
		return getSAPTable4dResponsiveness(parameters, controls, emitterType);
	}

	@Override
	public boolean isCommunityHeating() {
		return false;
	}
} // HeatPumpImpl
