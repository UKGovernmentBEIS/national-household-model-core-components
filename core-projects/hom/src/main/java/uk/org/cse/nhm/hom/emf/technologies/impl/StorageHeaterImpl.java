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
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
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
	 * The default value of the '{@link #getResponsiveness() <em>Responsiveness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponsiveness()
	 * @generated
	 * @ordered
	 */
	protected static final double RESPONSIVENESS_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getResponsiveness() <em>Responsiveness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponsiveness()
	 * @generated
	 * @ordered
	 */
	protected double responsiveness = RESPONSIVENESS_EDEFAULT;

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
	public double getResponsiveness() {
		return responsiveness;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResponsiveness(double newResponsiveness) {
		double oldResponsiveness = responsiveness;
		responsiveness = newResponsiveness;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.STORAGE_HEATER__RESPONSIVENESS, oldResponsiveness, responsiveness));
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
	 * @generated nooooo
	 */
	@Override
	public void accept(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final AtomicInteger heatingSystemCounter, final IHeatProportions heatProportions) {
		// storage heaters seem pretty straightforward in SAP 2009; they are just
		// a plain old heating system with a given responsiveness (depending on their controls)
		// with a split-rate pricing of 0.2. Hurrah.

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
	public double getDerivedResponsiveness(final IConstants constants) {
		return responsiveness;
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
			case ITechnologiesPackage.STORAGE_HEATER__RESPONSIVENESS:
				return getResponsiveness();
			case ITechnologiesPackage.STORAGE_HEATER__CONTROL_TYPE:
				return getControlType();
			case ITechnologiesPackage.STORAGE_HEATER__TYPE:
				return getType();
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
			case ITechnologiesPackage.STORAGE_HEATER__RESPONSIVENESS:
				setResponsiveness((Double)newValue);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__CONTROL_TYPE:
				setControlType((StorageHeaterControlType)newValue);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__TYPE:
				setType((StorageHeaterType)newValue);
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
			case ITechnologiesPackage.STORAGE_HEATER__RESPONSIVENESS:
				setResponsiveness(RESPONSIVENESS_EDEFAULT);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__CONTROL_TYPE:
				setControlType(CONTROL_TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.STORAGE_HEATER__TYPE:
				setType(TYPE_EDEFAULT);
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
			case ITechnologiesPackage.STORAGE_HEATER__RESPONSIVENESS:
				return responsiveness != RESPONSIVENESS_EDEFAULT;
			case ITechnologiesPackage.STORAGE_HEATER__CONTROL_TYPE:
				return (flags & CONTROL_TYPE_EFLAG) != CONTROL_TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.STORAGE_HEATER__TYPE:
				return (flags & TYPE_EFLAG) != TYPE_EFLAG_DEFAULT;
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
		result.append(", responsiveness: ");
		result.append(responsiveness);
		result.append(", controlType: ");
		result.append(CONTROL_TYPE_EFLAG_VALUES[(flags & CONTROL_TYPE_EFLAG) >>> CONTROL_TYPE_EFLAG_OFFSET]);
		result.append(", type: ");
		result.append(TYPE_EFLAG_VALUES[(flags & TYPE_EFLAG) >>> TYPE_EFLAG_OFFSET]);
		result.append(')');
		return result.toString();
	}

	@Override
	public double[] getBackgroundTemperatures(final double[] demandTemperature,
			final double[] responsiveBackgroundTemperature,
			final double[] unresponsiveBackgroundTemperature,
			final IInternalParameters parameters,
			final IEnergyState state, final ISpecificHeatLosses losses) {
		// TODO responsiveness changes by tarrif type, in scotland (24hr heating tarriff)
		final double responsiveness = getResponsiveness();
		final double unresponsiveness = 1 - responsiveness;
		
		return new double[] {
			responsiveness * responsiveBackgroundTemperature[0] + unresponsiveness * unresponsiveBackgroundTemperature[0],
			responsiveness * responsiveBackgroundTemperature[1] + unresponsiveness * unresponsiveBackgroundTemperature[1]
		};
	}

	/**
	 * This is defined by Table 4e, which states that manual charge control implies +0.3 degrees temperature adjustment.
	 */
	@Override
	public double getDemandTemperatureAdjustment(final IInternalParameters parameters) {
		return parameters.getConstants().get(TemperatureAdjustments.STORAGE_HEATER, getControlType());
	}

	@Override
	public double getZoneTwoControlParameter(final IInternalParameters parameters) {
		// in SAP, this is either 1 or 0 depending on the heating system type (1 2 or 3)
		// all electric storage systems have control type 3, which is a zone 2 control parameter of 1
		return 1;
	}

} //StorageHeaterImpl
