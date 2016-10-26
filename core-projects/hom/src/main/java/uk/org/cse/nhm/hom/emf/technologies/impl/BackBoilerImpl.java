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
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.IBackBoiler;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.ISpaceHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.impl.BoilerImpl;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 *
 * This is a room heater which is also a back boiler - It employs EMF multiple inheritance, which means that some code from {@link RoomHeaterImpl} has been
 * duplicated, unfortunately.
 * 
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.BackBoilerImpl#getEfficiency <em>Efficiency</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.BackBoilerImpl#isThermostatFitted <em>Thermostat Fitted</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BackBoilerImpl extends BoilerImpl implements IBackBoiler {
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
	protected static final int THERMOSTAT_FITTED_EFLAG = 1 << 12;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BackBoilerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.BACK_BOILER;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.BACK_BOILER__EFFICIENCY, oldEfficiency, efficiency));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.BACK_BOILER__THERMOSTAT_FITTED, oldThermostatFitted, newThermostatFitted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBroken() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.BACK_BOILER__EFFICIENCY:
				return getEfficiency();
			case ITechnologiesPackage.BACK_BOILER__THERMOSTAT_FITTED:
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
			case ITechnologiesPackage.BACK_BOILER__EFFICIENCY:
				setEfficiency((Efficiency)newValue);
				return;
			case ITechnologiesPackage.BACK_BOILER__THERMOSTAT_FITTED:
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
			case ITechnologiesPackage.BACK_BOILER__EFFICIENCY:
				setEfficiency(EFFICIENCY_EDEFAULT);
				return;
			case ITechnologiesPackage.BACK_BOILER__THERMOSTAT_FITTED:
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
			case ITechnologiesPackage.BACK_BOILER__EFFICIENCY:
				return EFFICIENCY_EDEFAULT == null ? efficiency != null : !EFFICIENCY_EDEFAULT.equals(efficiency);
			case ITechnologiesPackage.BACK_BOILER__THERMOSTAT_FITTED:
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
		if (baseClass == ISpaceHeater.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IRoomHeater.class) {
			switch (derivedFeatureID) {
				case ITechnologiesPackage.BACK_BOILER__EFFICIENCY: return ITechnologiesPackage.ROOM_HEATER__EFFICIENCY;
				case ITechnologiesPackage.BACK_BOILER__THERMOSTAT_FITTED: return ITechnologiesPackage.ROOM_HEATER__THERMOSTAT_FITTED;
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
		if (baseClass == ISpaceHeater.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IRoomHeater.class) {
			switch (baseFeatureID) {
				case ITechnologiesPackage.ROOM_HEATER__EFFICIENCY: return ITechnologiesPackage.BACK_BOILER__EFFICIENCY;
				case ITechnologiesPackage.ROOM_HEATER__THERMOSTAT_FITTED: return ITechnologiesPackage.BACK_BOILER__THERMOSTAT_FITTED;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public String toString() {
		return super.toString();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * Do boiler's accept and {@link RoomHeaterImpl#accept(IRoomHeater, IEnergyCalculatorParameters, IEnergyCalculatorVisitor, AtomicInteger, double, double)}
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public void accept(IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, IHeatProportions heatProportions) {
		super.accept(constants, parameters, visitor, heatingSystemCounter, heatProportions);
		RoomHeaterImpl.accept(this,constants, parameters, visitor, heatingSystemCounter, heatProportions, true);
	}

} //BackBoilerImpl
