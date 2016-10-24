/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.impl.HeatTransducer;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IFuelAndFlue;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.util.FlueVentilationHelper;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.DirectElectricHeatTransducer;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.RoomHeaterHeatingSystem;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Room Heater</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.RoomHeaterImpl#getFuel <em>Fuel</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.RoomHeaterImpl#getFlueType <em>Flue Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.RoomHeaterImpl#getEfficiency <em>Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.RoomHeaterImpl#isThermostatFitted <em>Thermostat Fitted</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RoomHeaterImpl extends SpaceHeaterImpl implements IRoomHeater {
	/**
	 * The default value of the '{@link #getFuel() <em>Fuel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFuel()
	 * @generated
	 * @ordered
	 */
	protected static final FuelType FUEL_EDEFAULT = FuelType.MAINS_GAS;

	/**
	 * The offset of the flags representing the value of the '{@link #getFuel() <em>Fuel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_EFLAG_OFFSET = 0;

	/**
	 * The flags representing the default value of the '{@link #getFuel() <em>Fuel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_EFLAG_DEFAULT = FUEL_EDEFAULT.ordinal() << FUEL_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link FuelType Fuel Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final FuelType[] FUEL_EFLAG_VALUES = FuelType.values();

	/**
	 * The flags representing the value of the '{@link #getFuel() <em>Fuel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFuel()
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_EFLAG = 0xf << FUEL_EFLAG_OFFSET;

	/**
	 * The default value of the '{@link #getFlueType() <em>Flue Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFlueType()
	 * @generated
	 * @ordered
	 */
	protected static final FlueType FLUE_TYPE_EDEFAULT = FlueType.CHIMNEY;

	/**
	 * The offset of the flags representing the value of the '{@link #getFlueType() <em>Flue Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FLUE_TYPE_EFLAG_OFFSET = 4;

	/**
	 * The flags representing the default value of the '{@link #getFlueType() <em>Flue Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FLUE_TYPE_EFLAG_DEFAULT = FLUE_TYPE_EDEFAULT.ordinal() << FLUE_TYPE_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link FlueType Flue Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final FlueType[] FLUE_TYPE_EFLAG_VALUES = FlueType.values();

	/**
	 * The flags representing the value of the '{@link #getFlueType() <em>Flue Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFlueType()
	 * @generated
	 * @ordered
	 */
	protected static final int FLUE_TYPE_EFLAG = 0x7 << FLUE_TYPE_EFLAG_OFFSET;

	/**
	 * The default value of the '{@link #getEfficiency() <em>Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfficiency()
	 * @generated
	 * @ordered
	 */
	protected static final Efficiency EFFICIENCY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEfficiency() <em>Efficiency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEfficiency()
	 * @generated
	 * @ordered
	 */
	protected Efficiency efficiency = EFFICIENCY_EDEFAULT;

	/**
	 * The default value of the '{@link #isThermostatFitted() <em>Thermostat Fitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isThermostatFitted()
	 * @generated
	 * @ordered
	 */
	protected static final boolean THERMOSTAT_FITTED_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isThermostatFitted() <em>Thermostat Fitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isThermostatFitted()
	 * @generated
	 * @ordered
	 */
	protected static final int THERMOSTAT_FITTED_EFLAG = 1 << 7;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RoomHeaterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.ROOM_HEATER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FuelType getFuel() {
		return FUEL_EFLAG_VALUES[(flags & FUEL_EFLAG) >>> FUEL_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFuel(FuelType newFuel) {
		FuelType oldFuel = FUEL_EFLAG_VALUES[(flags & FUEL_EFLAG) >>> FUEL_EFLAG_OFFSET];
		if (newFuel == null) newFuel = FUEL_EDEFAULT;
		flags = flags & ~FUEL_EFLAG | newFuel.ordinal() << FUEL_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.ROOM_HEATER__FUEL, oldFuel, newFuel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FlueType getFlueType() {
		return FLUE_TYPE_EFLAG_VALUES[(flags & FLUE_TYPE_EFLAG) >>> FLUE_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFlueType(FlueType newFlueType) {
		FlueType oldFlueType = FLUE_TYPE_EFLAG_VALUES[(flags & FLUE_TYPE_EFLAG) >>> FLUE_TYPE_EFLAG_OFFSET];
		if (newFlueType == null) newFlueType = FLUE_TYPE_EDEFAULT;
		flags = flags & ~FLUE_TYPE_EFLAG | newFlueType.ordinal() << FLUE_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.ROOM_HEATER__FLUE_TYPE, oldFlueType, newFlueType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Efficiency getEfficiency() {
		return efficiency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEfficiency(Efficiency newEfficiency) {
		Efficiency oldEfficiency = efficiency;
		efficiency = newEfficiency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.ROOM_HEATER__EFFICIENCY, oldEfficiency, efficiency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isThermostatFitted() {
		return (flags & THERMOSTAT_FITTED_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThermostatFitted(boolean newThermostatFitted) {
		boolean oldThermostatFitted = (flags & THERMOSTAT_FITTED_EFLAG) != 0;
		if (newThermostatFitted) flags |= THERMOSTAT_FITTED_EFLAG; else flags &= ~THERMOSTAT_FITTED_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.ROOM_HEATER__THERMOSTAT_FITTED, oldThermostatFitted, newThermostatFitted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * Accept the given visitor - this should present the room heater as a heating system.
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public void accept(IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, IHeatProportions heatProportions) {
		accept(this, constants, parameters, visitor, heatingSystemCounter, heatProportions);
	}
	
	/**
	 * Factored out into a static method to allow use from multiply inheriting {@link BackBoilerImpl}.
	 * @param self
	 * @param parameters
	 * @param visitor
	 * @param heatingSystemCounter
	 * @param heatProportions 
	 */
	protected static void accept(final IRoomHeater self, final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, IHeatProportions heatProportions) {
		visitor.visitHeatingSystem(new RoomHeaterHeatingSystem(self), heatProportions.spaceHeatingProportion(self));
		final double effectiveProportion = heatProportions.spaceHeatingProportion(self);
		
		/*
		BEISDOC
		NAME: Room heater Fuel Energy demand
		DESCRIPTION: The fuel energy used by a room heater to provide space heating.
		TYPE: formula
		UNIT: W
		SAP: (215)
		BREDEM: 8J,8K
		DEPS: heat-demand,space-heating-fraction
		NOTES: This code constructs a 'heat transducer', which is an object in the energy calculator which models converting fuel into heat.
		ID: room-heater-fuel-energy-demand
		CODSIEB
		*/
		if (self.getFuel() == FuelType.ELECTRICITY) {
			visitor.visitEnergyTransducer(new DirectElectricHeatTransducer(heatingSystemCounter.getAndIncrement(), effectiveProportion));
		} else {
			visitor.visitEnergyTransducer(
					new HeatTransducer(self.getFuel().getEnergyType(), self.getEfficiency().value, effectiveProportion, true, heatingSystemCounter.getAndIncrement(), ServiceType.SECONDARY_SPACE_HEATING)
					);
		}
		
		// TODO flue goes here; if we have a back boiler, it shouldn't have a flue
		if (self.getFuel() != FuelType.ELECTRICITY) {
			if (self.getFlueType() == FlueType.OPEN_FLUE || self.getFlueType() == FlueType.CHIMNEY) {
				// these are the only kind of flue which cause ventilation
				FlueVentilationHelper.addInfiltration(visitor, self.getFlueType(), constants);
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.ROOM_HEATER__FUEL:
				return getFuel();
			case ITechnologiesPackage.ROOM_HEATER__FLUE_TYPE:
				return getFlueType();
			case ITechnologiesPackage.ROOM_HEATER__EFFICIENCY:
				return getEfficiency();
			case ITechnologiesPackage.ROOM_HEATER__THERMOSTAT_FITTED:
				return isThermostatFitted();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ITechnologiesPackage.ROOM_HEATER__FUEL:
				setFuel((FuelType)newValue);
				return;
			case ITechnologiesPackage.ROOM_HEATER__FLUE_TYPE:
				setFlueType((FlueType)newValue);
				return;
			case ITechnologiesPackage.ROOM_HEATER__EFFICIENCY:
				setEfficiency((Efficiency)newValue);
				return;
			case ITechnologiesPackage.ROOM_HEATER__THERMOSTAT_FITTED:
				setThermostatFitted((Boolean)newValue);
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
			case ITechnologiesPackage.ROOM_HEATER__FUEL:
				setFuel(FUEL_EDEFAULT);
				return;
			case ITechnologiesPackage.ROOM_HEATER__FLUE_TYPE:
				setFlueType(FLUE_TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.ROOM_HEATER__EFFICIENCY:
				setEfficiency(EFFICIENCY_EDEFAULT);
				return;
			case ITechnologiesPackage.ROOM_HEATER__THERMOSTAT_FITTED:
				setThermostatFitted(THERMOSTAT_FITTED_EDEFAULT);
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
			case ITechnologiesPackage.ROOM_HEATER__FUEL:
				return (flags & FUEL_EFLAG) != FUEL_EFLAG_DEFAULT;
			case ITechnologiesPackage.ROOM_HEATER__FLUE_TYPE:
				return (flags & FLUE_TYPE_EFLAG) != FLUE_TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.ROOM_HEATER__EFFICIENCY:
				return EFFICIENCY_EDEFAULT == null ? efficiency != null : !EFFICIENCY_EDEFAULT.equals(efficiency);
			case ITechnologiesPackage.ROOM_HEATER__THERMOSTAT_FITTED:
				return ((flags & THERMOSTAT_FITTED_EFLAG) != 0) != THERMOSTAT_FITTED_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IVisitorAccepter.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IFuelAndFlue.class) {
			switch (derivedFeatureID) {
				case ITechnologiesPackage.ROOM_HEATER__FUEL: return ITechnologiesPackage.FUEL_AND_FLUE__FUEL;
				case ITechnologiesPackage.ROOM_HEATER__FLUE_TYPE: return ITechnologiesPackage.FUEL_AND_FLUE__FLUE_TYPE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IVisitorAccepter.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IFuelAndFlue.class) {
			switch (baseFeatureID) {
				case ITechnologiesPackage.FUEL_AND_FLUE__FUEL: return ITechnologiesPackage.ROOM_HEATER__FUEL;
				case ITechnologiesPackage.FUEL_AND_FLUE__FLUE_TYPE: return ITechnologiesPackage.ROOM_HEATER__FLUE_TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (fuel: ");
		result.append(FUEL_EFLAG_VALUES[(flags & FUEL_EFLAG) >>> FUEL_EFLAG_OFFSET]);
		result.append(", flueType: ");
		result.append(FLUE_TYPE_EFLAG_VALUES[(flags & FLUE_TYPE_EFLAG) >>> FLUE_TYPE_EFLAG_OFFSET]);
		result.append(", efficiency: ");
		result.append(efficiency);
		result.append(", thermostatFitted: ");
		result.append((flags & THERMOSTAT_FITTED_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}
} //RoomHeaterImpl
