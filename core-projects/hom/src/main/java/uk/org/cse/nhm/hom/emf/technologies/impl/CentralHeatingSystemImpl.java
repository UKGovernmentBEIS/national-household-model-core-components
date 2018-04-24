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

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.types.*;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.constants.PumpAndFanConstants;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.Pump;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Central Heating System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralHeatingSystemImpl#getHeatSource <em>Heat Source</em>}</li>
 * </ul>
 * </p>
 *
 * @generated no (extra interface) - this generated flag just affects the implemented interface list.
 */
public class CentralHeatingSystemImpl extends SpaceHeaterImpl implements ICentralHeatingSystem, IHeatingSystem {
	/**
	 * The cached value of the '{@link #getHeatSource() <em>Heat Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeatSource()
	 * @generated
	 * @ordered
	 */
	protected IHeatSource heatSource;

	/**
	 * The cached value of the '{@link #getControls() <em>Controls</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControls()
	 * @generated
	 * @ordered
	 */
	protected EList<HeatingSystemControlType> controls;

	/**
	 * The default value of the '{@link #getEmitterType() <em>Emitter Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmitterType()
	 * @generated
	 * @ordered
	 */
	protected static final EmitterType EMITTER_TYPE_EDEFAULT = EmitterType.RADIATORS;

	/**
	 * The offset of the flags representing the value of the '{@link #getEmitterType() <em>Emitter Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int EMITTER_TYPE_EFLAG_OFFSET = 0;

	/**
	 * The flags representing the default value of the '{@link #getEmitterType() <em>Emitter Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int EMITTER_TYPE_EFLAG_DEFAULT = EMITTER_TYPE_EDEFAULT.ordinal() << EMITTER_TYPE_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link EmitterType Emitter Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final EmitterType[] EMITTER_TYPE_EFLAG_VALUES = EmitterType.values();

	/**
	 * The flags representing the value of the '{@link #getEmitterType() <em>Emitter Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmitterType()
	 * @generated
	 * @ordered
	 */
	protected static final int EMITTER_TYPE_EFLAG = 0x7 << EMITTER_TYPE_EFLAG_OFFSET;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CentralHeatingSystemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.CENTRAL_HEATING_SYSTEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IHeatSource getHeatSource() {
		if (heatSource != null && heatSource.eIsProxy()) {
			InternalEObject oldHeatSource = (InternalEObject)heatSource;
			heatSource = (IHeatSource)eResolveProxy(oldHeatSource);
			if (heatSource != oldHeatSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE, oldHeatSource, heatSource));
			}
		}
		return heatSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IHeatSource basicGetHeatSource() {
		return heatSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHeatSource(IHeatSource newHeatSource, NotificationChain msgs) {
		IHeatSource oldHeatSource = heatSource;
		heatSource = newHeatSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE, oldHeatSource, newHeatSource);
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
	public void setHeatSource(IHeatSource newHeatSource) {
		if (newHeatSource != heatSource) {
			NotificationChain msgs = null;
			if (heatSource != null)
				msgs = ((InternalEObject)heatSource).eInverseRemove(this, ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER, IHeatSource.class, msgs);
			if (newHeatSource != null)
				msgs = ((InternalEObject)newHeatSource).eInverseAdd(this, ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER, IHeatSource.class, msgs);
			msgs = basicSetHeatSource(newHeatSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE, newHeatSource, newHeatSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<HeatingSystemControlType> getControls() {
		if (controls == null) {
			controls = new EDataTypeUniqueEList<HeatingSystemControlType>(HeatingSystemControlType.class, this, ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__CONTROLS);
		}
		return controls;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EmitterType getEmitterType() {
		return EMITTER_TYPE_EFLAG_VALUES[(flags & EMITTER_TYPE_EFLAG) >>> EMITTER_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEmitterType(EmitterType newEmitterType) {
		EmitterType oldEmitterType = EMITTER_TYPE_EFLAG_VALUES[(flags & EMITTER_TYPE_EFLAG) >>> EMITTER_TYPE_EFLAG_OFFSET];
		if (newEmitterType == null) newEmitterType = EMITTER_TYPE_EDEFAULT;
		flags = flags & ~EMITTER_TYPE_EFLAG | newEmitterType.ordinal() << EMITTER_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__EMITTER_TYPE, oldEmitterType, newEmitterType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	@Override
	public boolean isThermostaticallyControlled() {
		final EList<HeatingSystemControlType> c = getControls();
		if (c.contains(HeatingSystemControlType.APPLIANCE_THERMOSTAT)
				|| c.contains(HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL)
				|| c.contains(HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE)
				|| c.contains(HeatingSystemControlType.ROOM_THERMOSTAT))
			return true;
		else
			return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBroken() {
		return getHeatSource() == null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated nope
	 */
	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
		// so, we need to ask our heat source to do heat generation for us.
		// this could equally well be in the heat source, because it has an inverse
		// reference onto this. However, it makes sense for the responsibility to be here, I think.
		if (getHeatSource() != null) {
			// this will connect up the heat source to supply space heat
			final double effectiveProportion = heatProportions.spaceHeatingProportion(this);
			getHeatSource().acceptFromHeating(constants, parameters, visitor, effectiveProportion, heatingSystemCounter.getAndIncrement());
			// we also need to tell the EC we are here, for responsiveness & background temperature calculations.
			visitor.visitHeatingSystem(this, heatProportions.spaceHeatingProportion(this));
			// we need to include the central heating pump

			final double centralHeatingPumpGains;
			if (getHeatSource().isCommunityHeating()) {
				/* SAP Table 5a */
				centralHeatingPumpGains = 0;
			} else {
				centralHeatingPumpGains = constants.get(PumpAndFanConstants.CENTRAL_HEATING_PUMP_GAINS);
			}

			visitor.visitEnergyTransducer(
					/*
					BEISDOC
					NAME: Central heating pump power demand
					DESCRIPTION: The power consumed by the central heating pump.
					TYPE: formula
					UNIT: W
					SAP: Table 4f
                                        SAP_COMPLIANT: Yes
					BREDEM: Table 4
                                        BREDEM_COMPLIANT: Yes
					DEPS: central-heating-pump-base-power,pump-no-thermostat-modifier
					ID: central-heating-pump-power
					CODSIEB
					*/
					new Pump("CH", ServiceType.PRIMARY_SPACE_HEATING,
					constants.get(PumpAndFanConstants.CENTRAL_HEATING_PUMP_WATTAGE)
					* (getControls().contains(HeatingSystemControlType.ROOM_THERMOSTAT) ?
							1 : constants.get(PumpAndFanConstants.NO_ROOM_THERMOSTAT_MULTIPLIER)),
                             centralHeatingPumpGains,
							EnergyCalculationStep.PumpsFansAndKeepHot_WaterPump));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public double getResponsiveness(final IConstants constants, final EnergyCalculatorType energyCalculatorType, final ElectricityTariffType electricityTariffType) {
		if (isBroken()) {
			/*
			 * This represents the case when the heat source is broken or missing,
			 * and we have to use assumed portable electric heaters instead.

			 * This is included for completeness, but should never actually get called,
			 * because the heating proportion for this system will be 0 if there is no heat source.
			 */
			return 1.0;
		} else {
			return heatSource.getResponsiveness(constants, getControls(), getEmitterType());
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE:
				if (heatSource != null)
					msgs = ((InternalEObject)heatSource).eInverseRemove(this, ITechnologiesPackage.HEAT_SOURCE__SPACE_HEATER, IHeatSource.class, msgs);
				return basicSetHeatSource((IHeatSource)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE:
				return basicSetHeatSource(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE:
				if (resolve) return getHeatSource();
				return basicGetHeatSource();
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__CONTROLS:
				return getControls();
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__EMITTER_TYPE:
				return getEmitterType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE:
				setHeatSource((IHeatSource)newValue);
				return;
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__CONTROLS:
				getControls().clear();
				getControls().addAll((Collection<? extends HeatingSystemControlType>)newValue);
				return;
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__EMITTER_TYPE:
				setEmitterType((EmitterType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE:
				setHeatSource((IHeatSource)null);
				return;
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__CONTROLS:
				getControls().clear();
				return;
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__EMITTER_TYPE:
				setEmitterType(EMITTER_TYPE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__HEAT_SOURCE:
				return heatSource != null;
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__CONTROLS:
				return controls != null && !controls.isEmpty();
			case ITechnologiesPackage.CENTRAL_HEATING_SYSTEM__EMITTER_TYPE:
				return (flags & EMITTER_TYPE_EFLAG) != EMITTER_TYPE_EFLAG_DEFAULT;
		}
		return super.eIsSet(featureID);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (controls: ");
		result.append(controls);
		result.append(", emitterType: ");
		result.append(EMITTER_TYPE_EFLAG_VALUES[(flags & EMITTER_TYPE_EFLAG) >>> EMITTER_TYPE_EFLAG_OFFSET]);
		result.append(')');
		return result.toString();
	}

	/**
	 * This is determined in SAP table 4e, depending on heat source type and controls. Consequently we could
	 * do the lookup here - however, to allow for new kinds of heat source to be introduced without affecting
	 * this logic we will instead delegate to the heat source. Standard heat sources can then do the normal lookup.
	 */
	@Override
	public double getDemandTemperatureAdjustment(final IInternalParameters parameters) {
		if (getHeatSource() == null) return 0;
		return getHeatSource().getDemandTemperatureAdjustment(parameters, getControls());
	}

	@Override
	public Zone2ControlParameter getZoneTwoControlParameter(final IInternalParameters parameters) {
		// SAP table 4e determines the zone 2 control parameter for various different kinds of control type.

		// this also depends on the heat source, so in our case we delegate to the heat source.
		if (getHeatSource() == null) return Zone2ControlParameter.Two;
		return getHeatSource().getZoneTwoControlParameter(parameters, getControls(), getEmitterType());
	}

	@Override
	public FuelType getFuel() {
		if (getHeatSource() != null) {
			return getHeatSource().getFuel();
		} else {
			return null;
		}

	}
} //CentralHeatingSystemImpl
