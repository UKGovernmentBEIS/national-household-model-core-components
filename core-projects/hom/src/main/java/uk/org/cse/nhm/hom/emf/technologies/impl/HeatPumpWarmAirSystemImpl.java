/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.hom.constants.SplitRateConstants;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPumpWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Heat Pump Warm Air System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpWarmAirSystemImpl#getSourceType <em>Source Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.HeatPumpWarmAirSystemImpl#isAuxiliaryPresent <em>Auxiliary Present</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HeatPumpWarmAirSystemImpl extends WarmAirSystemImpl implements IHeatPumpWarmAirSystem {
	/**
	 * The default value of the '{@link #getSourceType() <em>Source Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	protected static final int SOURCE_TYPE_EFLAG_OFFSET = 4;

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
	 * The default value of the '{@link #isAuxiliaryPresent() <em>Auxiliary Present</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	protected static final int AUXILIARY_PRESENT_EFLAG = 1 << 5;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HeatPumpWarmAirSystemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.HEAT_PUMP_WARM_AIR_SYSTEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HeatPumpSourceType getSourceType() {
		return SOURCE_TYPE_EFLAG_VALUES[(flags & SOURCE_TYPE_EFLAG) >>> SOURCE_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceType(HeatPumpSourceType newSourceType) {
		HeatPumpSourceType oldSourceType = SOURCE_TYPE_EFLAG_VALUES[(flags & SOURCE_TYPE_EFLAG) >>> SOURCE_TYPE_EFLAG_OFFSET];
		if (newSourceType == null) newSourceType = SOURCE_TYPE_EDEFAULT;
		flags = flags & ~SOURCE_TYPE_EFLAG | newSourceType.ordinal() << SOURCE_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP_WARM_AIR_SYSTEM__SOURCE_TYPE, oldSourceType, newSourceType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAuxiliaryPresent() {
		return (flags & AUXILIARY_PRESENT_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAuxiliaryPresent(boolean newAuxiliaryPresent) {
		boolean oldAuxiliaryPresent = (flags & AUXILIARY_PRESENT_EFLAG) != 0;
		if (newAuxiliaryPresent) flags |= AUXILIARY_PRESENT_EFLAG; else flags &= ~AUXILIARY_PRESENT_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.HEAT_PUMP_WARM_AIR_SYSTEM__AUXILIARY_PRESENT, oldAuxiliaryPresent, newAuxiliaryPresent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.HEAT_PUMP_WARM_AIR_SYSTEM__SOURCE_TYPE:
				return getSourceType();
			case ITechnologiesPackage.HEAT_PUMP_WARM_AIR_SYSTEM__AUXILIARY_PRESENT:
				return isAuxiliaryPresent();
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
			case ITechnologiesPackage.HEAT_PUMP_WARM_AIR_SYSTEM__SOURCE_TYPE:
				setSourceType((HeatPumpSourceType)newValue);
				return;
			case ITechnologiesPackage.HEAT_PUMP_WARM_AIR_SYSTEM__AUXILIARY_PRESENT:
				setAuxiliaryPresent((Boolean)newValue);
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
			case ITechnologiesPackage.HEAT_PUMP_WARM_AIR_SYSTEM__SOURCE_TYPE:
				setSourceType(SOURCE_TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.HEAT_PUMP_WARM_AIR_SYSTEM__AUXILIARY_PRESENT:
				setAuxiliaryPresent(AUXILIARY_PRESENT_EDEFAULT);
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
			case ITechnologiesPackage.HEAT_PUMP_WARM_AIR_SYSTEM__SOURCE_TYPE:
				return (flags & SOURCE_TYPE_EFLAG) != SOURCE_TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.HEAT_PUMP_WARM_AIR_SYSTEM__AUXILIARY_PRESENT:
				return ((flags & AUXILIARY_PRESENT_EFLAG) != 0) != AUXILIARY_PRESENT_EDEFAULT;
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
		result.append(" (sourceType: ");
		result.append(SOURCE_TYPE_EFLAG_VALUES[(flags & SOURCE_TYPE_EFLAG) >>> SOURCE_TYPE_EFLAG_OFFSET]);
		result.append(", auxiliaryPresent: ");
		result.append((flags & AUXILIARY_PRESENT_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}

	/**
	 * Electric warm air systems have reduced responsiveness, but electric heat pump warm air systems don't
	 */
	@Override
	public double getResponsiveness(IConstants constants, EnergyCalculatorType calculatorType, final ElectricityTariffType electricityTariffType) {
		return 1;
	}
	
	/**
	 * SAP 2009 table 12a details the split rate constants for different kinds of heat pumps.
	 */
	@Override
	protected SplitRateConstants getSplitRateConstants() {
		if (getFuelType() == FuelType.ELECTRICITY) {
			if (getSourceType() == HeatPumpSourceType.AIR) {
				return SplitRateConstants.AIR_SOURCE_SPACE_HEAT;
			} else if (getSourceType() == HeatPumpSourceType.GROUND) {
				return SplitRateConstants.GROUND_SOURCE_SPACE_HEAT;
			} else {
				return super.getSplitRateConstants();
			}
		} else {
			return super.getSplitRateConstants();
		}
	}
} //HeatPumpWarmAirSystemImpl
