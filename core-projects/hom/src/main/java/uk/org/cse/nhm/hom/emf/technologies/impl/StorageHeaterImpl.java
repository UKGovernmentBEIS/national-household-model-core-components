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
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.constants.adjustments.TemperatureAdjustments;
import uk.org.cse.nhm.hom.emf.technologies.IOperationalCost;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.StorageHeatingTransducer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Storage Heater</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.StorageHeaterImpl#getResponsiveness <em>Responsiveness</em>}</li>
 * </ul>
 * </p>
 *
 * @generated no (IHeatingSystem interface added)
 */
public class StorageHeaterImpl extends SpaceHeaterImpl implements IStorageHeater, IHeatingSystem {
	/**
	 * The default value of the '{@link #getAnnualOperationalCost() <em>Annual Operational Cost</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnualOperationalCost()
	 * @generated
	 * @ordered
	 */
	protected static final double ANNUAL_OPERATIONAL_COST_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getAnnualOperationalCost() <em>Annual Operational Cost</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnualOperationalCost()
	 * @generated
	 * @ordered
	 */
	protected double annualOperationalCost = ANNUAL_OPERATIONAL_COST_EDEFAULT;

	/**
	 * The default value of the '{@link #getResponsivenessOverride() <em>Responsiveness Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponsivenessOverride()
	 * @generated
	 * @ordered
	 */
	protected static final double RESPONSIVENESS_OVERRIDE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getResponsivenessOverride() <em>Responsiveness Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponsivenessOverride()
	 * @generated
	 * @ordered
	 */
	protected double responsivenessOverride = RESPONSIVENESS_OVERRIDE_EDEFAULT;

	/**
	 * The default value of the '{@link #getControlType() <em>Control Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControlType()
	 * @generated
	 * @ordered
	 */
	protected static final StorageHeaterControlType CONTROL_TYPE_EDEFAULT = StorageHeaterControlType.MANUAL_CHARGE_CONTROL;

	/**
	 * The offset of the flags representing the value of the '{@link #getControlType() <em>Control Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int CONTROL_TYPE_EFLAG_OFFSET = 0;

	/**
	 * The flags representing the default value of the '{@link #getControlType() <em>Control Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int CONTROL_TYPE_EFLAG_DEFAULT = CONTROL_TYPE_EDEFAULT.ordinal() << CONTROL_TYPE_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link StorageHeaterControlType Storage Heater Control Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final StorageHeaterControlType[] CONTROL_TYPE_EFLAG_VALUES = StorageHeaterControlType.values();

	/**
	 * The flags representing the value of the '{@link #getControlType() <em>Control Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getControlType()
	 * @generated
	 * @ordered
	 */
	protected static final int CONTROL_TYPE_EFLAG = 0x3 << CONTROL_TYPE_EFLAG_OFFSET;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final StorageHeaterType TYPE_EDEFAULT = StorageHeaterType.OLD_LARGE_VOLUME;

	/**
	 * The offset of the flags representing the value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int TYPE_EFLAG_OFFSET = 2;

	/**
	 * The flags representing the default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int TYPE_EFLAG_DEFAULT = TYPE_EDEFAULT.ordinal() << TYPE_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link StorageHeaterType Storage Heater Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final StorageHeaterType[] TYPE_EFLAG_VALUES = StorageHeaterType.values();

	/**
	 * The flags representing the value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final int TYPE_EFLAG = 0x7 << TYPE_EFLAG_OFFSET;

	/**
	 * The default value of the '{@link #isHasResponsivenessOverride() <em>Has Responsiveness Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasResponsivenessOverride()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_RESPONSIVENESS_OVERRIDE_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isHasResponsivenessOverride() <em>Has Responsiveness Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasResponsivenessOverride()
	 * @generated
	 * @ordered
	 */
	protected static final int HAS_RESPONSIVENESS_OVERRIDE_EFLAG = 1 << 5;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StorageHeaterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.STORAGE_HEATER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getAnnualOperationalCost() {
		return annualOperationalCost;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAnnualOperationalCost(double newAnnualOperationalCost) {
		double oldAnnualOperationalCost = annualOperationalCost;
		annualOperationalCost = newAnnualOperationalCost;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.STORAGE_HEATER__ANNUAL_OPERATIONAL_COST, oldAnnualOperationalCost, annualOperationalCost));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getResponsivenessOverride() {
		return responsivenessOverride;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResponsivenessOverride(double newResponsivenessOverride) {
		double oldResponsivenessOverride = responsivenessOverride;
		responsivenessOverride = newResponsivenessOverride;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.STORAGE_HEATER__RESPONSIVENESS_OVERRIDE, oldResponsivenessOverride, responsivenessOverride));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StorageHeaterControlType getControlType() {
		return CONTROL_TYPE_EFLAG_VALUES[(flags & CONTROL_TYPE_EFLAG) >>> CONTROL_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setControlType(StorageHeaterControlType newControlType) {
		StorageHeaterControlType oldControlType = CONTROL_TYPE_EFLAG_VALUES[(flags & CONTROL_TYPE_EFLAG) >>> CONTROL_TYPE_EFLAG_OFFSET];
		if (newControlType == null) newControlType = CONTROL_TYPE_EDEFAULT;
		flags = flags & ~CONTROL_TYPE_EFLAG | newControlType.ordinal() << CONTROL_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.STORAGE_HEATER__CONTROL_TYPE, oldControlType, newControlType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StorageHeaterType getType() {
		return TYPE_EFLAG_VALUES[(flags & TYPE_EFLAG) >>> TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(StorageHeaterType newType) {
		StorageHeaterType oldType = TYPE_EFLAG_VALUES[(flags & TYPE_EFLAG) >>> TYPE_EFLAG_OFFSET];
		if (newType == null) newType = TYPE_EDEFAULT;
		flags = flags & ~TYPE_EFLAG | newType.ordinal() << TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.STORAGE_HEATER__TYPE, oldType, newType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHasResponsivenessOverride() {
		return (flags & HAS_RESPONSIVENESS_OVERRIDE_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHasResponsivenessOverride(boolean newHasResponsivenessOverride) {
		boolean oldHasResponsivenessOverride = (flags & HAS_RESPONSIVENESS_OVERRIDE_EFLAG) != 0;
		if (newHasResponsivenessOverride) flags |= HAS_RESPONSIVENESS_OVERRIDE_EFLAG; else flags &= ~HAS_RESPONSIVENESS_OVERRIDE_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.STORAGE_HEATER__HAS_RESPONSIVENESS_OVERRIDE, oldHasResponsivenessOverride, newHasResponsivenessOverride));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated nooooo
	 */
	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
		// storage heaters seem pretty straightforward in SAP 2009; they are just
		// a plain old heating system with a given responsiveness (depending on their controls)
		// with a split-rate pricing of 0.2. Hurrah.

		/*
		BEISDOC
		NAME: Storage Heater Fuel Energy Demand
		DESCRIPTION: The fuel demand of a storage heater for space heating
		TYPE: formula
		UNIT: W
		SAP: (211)
                SAP_COMPLIANT: Yes
		BREDEM: 8J,8K
                BREDEM_COMPLIANT: Yes
		DEPS: heat-demand,space-heating-fraction
		NOTES: This code constructs a 'heat transducer', which is an object in the energy calculator which models converting fuel into heat.
		ID: storage-heater-fuel-energy-demand
		CODSIEB
		*/
		visitor.visitHeatingSystem(this, heatProportions.spaceHeatingProportion(this));
		visitor.visitEnergyTransducer(
				new StorageHeatingTransducer(heatProportions.spaceHeatingProportion(this),
				heatingSystemCounter.getAndIncrement(),
				getType() == StorageHeaterType.INTEGRATED_DIRECT_ACTING));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	@Override
	public double getResponsiveness(final IConstants constants, final EnergyCalculatorType calculatorType, final ElectricityTariffType electricityTariffType) {
		/*
		BEISDOC
		NAME: Storage Heater Responsiveness
		DESCRIPTION: The responsiveness of a storage heater
		TYPE: lookup
		UNIT: Dimensionless
		SAP: Table 4a (Category 7)
                SAP_COMPLIANT: SAP mode only
		BREDEM: Defers to SAP
                BREDEM_COMPLIANT: N/A - out of scope
		SET: measure.storage-heater
		NOTES: In BREDEM 2012 mode, the storage heater responsiveness can be overridden by the scenario author. In SAP 2012 mode, it cannot.
		ID: storage-heater-responsiveness
		CODSIEB
		*/
		switch(calculatorType) {
		case BREDEM2012:
			// Use the override if it has been specified, otherwise fall through to the SAP behaviour.
			if (isHasResponsivenessOverride()) {
				return getResponsivenessOverride();
			}
		case SAP2012:
			final double baseResponsiveness;

			switch (getType()) {
			case OLD_LARGE_VOLUME:
				return 0;
			case SLIMLINE:
			case CONVECTOR:
				if (electricityTariffType == ElectricityTariffType.TWENTYFOUR_HOUR_HEATING) {
					baseResponsiveness = 0.4;
				} else {
					baseResponsiveness = 0.2;
				}
				break;
			case FAN:
				baseResponsiveness = 0.4;
				break;
			case INTEGRATED_DIRECT_ACTING:
				return 0.6;
			default:
				throw new UnsupportedOperationException("Unknown storage heater type " + getType());
			}

			final boolean isCelect = getControlType() == StorageHeaterControlType.CELECT_CHARGE_CONTROL;

			return baseResponsiveness + (isCelect ? 0.2 : 0.0);
		default:
			throw new UnsupportedOperationException("Unknown energy calculator type when computing storage heater responsiveness " + calculatorType);
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
			case ITechnologiesPackage.STORAGE_HEATER__ANNUAL_OPERATIONAL_COST:
				return getAnnualOperationalCost();
			case ITechnologiesPackage.STORAGE_HEATER__RESPONSIVENESS_OVERRIDE:
				return getResponsivenessOverride();
			case ITechnologiesPackage.STORAGE_HEATER__CONTROL_TYPE:
				return getControlType();
			case ITechnologiesPackage.STORAGE_HEATER__TYPE:
				return getType();
			case ITechnologiesPackage.STORAGE_HEATER__HAS_RESPONSIVENESS_OVERRIDE:
				return isHasResponsivenessOverride();
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
			case ITechnologiesPackage.STORAGE_HEATER__ANNUAL_OPERATIONAL_COST:
				setAnnualOperationalCost((Double)newValue);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__RESPONSIVENESS_OVERRIDE:
				setResponsivenessOverride((Double)newValue);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__CONTROL_TYPE:
				setControlType((StorageHeaterControlType)newValue);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__TYPE:
				setType((StorageHeaterType)newValue);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__HAS_RESPONSIVENESS_OVERRIDE:
				setHasResponsivenessOverride((Boolean)newValue);
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
			case ITechnologiesPackage.STORAGE_HEATER__ANNUAL_OPERATIONAL_COST:
				setAnnualOperationalCost(ANNUAL_OPERATIONAL_COST_EDEFAULT);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__RESPONSIVENESS_OVERRIDE:
				setResponsivenessOverride(RESPONSIVENESS_OVERRIDE_EDEFAULT);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__CONTROL_TYPE:
				setControlType(CONTROL_TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__HAS_RESPONSIVENESS_OVERRIDE:
				setHasResponsivenessOverride(HAS_RESPONSIVENESS_OVERRIDE_EDEFAULT);
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
			case ITechnologiesPackage.STORAGE_HEATER__ANNUAL_OPERATIONAL_COST:
				return annualOperationalCost != ANNUAL_OPERATIONAL_COST_EDEFAULT;
			case ITechnologiesPackage.STORAGE_HEATER__RESPONSIVENESS_OVERRIDE:
				return responsivenessOverride != RESPONSIVENESS_OVERRIDE_EDEFAULT;
			case ITechnologiesPackage.STORAGE_HEATER__CONTROL_TYPE:
				return (flags & CONTROL_TYPE_EFLAG) != CONTROL_TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.STORAGE_HEATER__TYPE:
				return (flags & TYPE_EFLAG) != TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.STORAGE_HEATER__HAS_RESPONSIVENESS_OVERRIDE:
				return ((flags & HAS_RESPONSIVENESS_OVERRIDE_EFLAG) != 0) != HAS_RESPONSIVENESS_OVERRIDE_EDEFAULT;
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
		if (baseClass == IOperationalCost.class) {
			switch (derivedFeatureID) {
				case ITechnologiesPackage.STORAGE_HEATER__ANNUAL_OPERATIONAL_COST: return ITechnologiesPackage.OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST;
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
		if (baseClass == IOperationalCost.class) {
			switch (baseFeatureID) {
				case ITechnologiesPackage.OPERATIONAL_COST__ANNUAL_OPERATIONAL_COST: return ITechnologiesPackage.STORAGE_HEATER__ANNUAL_OPERATIONAL_COST;
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
		result.append(" (annualOperationalCost: ");
		result.append(annualOperationalCost);
		result.append(", responsivenessOverride: ");
		result.append(responsivenessOverride);
		result.append(", controlType: ");
		result.append(CONTROL_TYPE_EFLAG_VALUES[(flags & CONTROL_TYPE_EFLAG) >>> CONTROL_TYPE_EFLAG_OFFSET]);
		result.append(", type: ");
		result.append(TYPE_EFLAG_VALUES[(flags & TYPE_EFLAG) >>> TYPE_EFLAG_OFFSET]);
		result.append(", hasResponsivenessOverride: ");
		result.append((flags & HAS_RESPONSIVENESS_OVERRIDE_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}

	/**
	 * This is defined by Table 4e, which states that manual charge control implies +0.3 degrees temperature adjustment.
	 */
	@Override
	public double getDemandTemperatureAdjustment(final IInternalParameters parameters) {
		return parameters.getConstants().get(TemperatureAdjustments.STORAGE_HEATER, getControlType());
	}

	@Override
	public Zone2ControlParameter getZoneTwoControlParameter(final IInternalParameters parameters) {
		return Zone2ControlParameter.Three;
	}

} //StorageHeaterImpl
