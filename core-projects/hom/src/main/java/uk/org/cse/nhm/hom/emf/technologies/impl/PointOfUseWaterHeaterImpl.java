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
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.IPointOfUseWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.PointOfUseWaterHeaterTransducer;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Point Of Use Water Heater</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.PointOfUseWaterHeaterImpl#getFuelType <em>Fuel Type</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.PointOfUseWaterHeaterImpl#isMultipoint <em>Multipoint</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.PointOfUseWaterHeaterImpl#getEfficiency <em>Efficiency</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PointOfUseWaterHeaterImpl extends WaterHeaterImpl implements IPointOfUseWaterHeater {
	/**
	 * The default value of the '{@link #getFuelType() <em>Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFuelType()
	 * @generated
	 * @ordered
	 */
	protected static final FuelType FUEL_TYPE_EDEFAULT = FuelType.MAINS_GAS;

	/**
	 * The offset of the flags representing the value of the '{@link #getFuelType() <em>Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_TYPE_EFLAG_OFFSET = 0;

	/**
	 * The flags representing the default value of the '{@link #getFuelType() <em>Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_TYPE_EFLAG_DEFAULT = FUEL_TYPE_EDEFAULT.ordinal() << FUEL_TYPE_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link FuelType Fuel Type}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	private static final FuelType[] FUEL_TYPE_EFLAG_VALUES = FuelType.values();

	/**
	 * The flags representing the value of the '{@link #getFuelType() <em>Fuel Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFuelType()
	 * @generated
	 * @ordered
	 */
	protected static final int FUEL_TYPE_EFLAG = 0xf << FUEL_TYPE_EFLAG_OFFSET;

	/**
	 * The default value of the '{@link #isMultipoint() <em>Multipoint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultipoint()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTIPOINT_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isMultipoint() <em>Multipoint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultipoint()
	 * @generated
	 * @ordered
	 */
	protected static final int MULTIPOINT_EFLAG = 1 << 4;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PointOfUseWaterHeaterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.POINT_OF_USE_WATER_HEATER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FuelType getFuelType() {
		return FUEL_TYPE_EFLAG_VALUES[(flags & FUEL_TYPE_EFLAG) >>> FUEL_TYPE_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFuelType(FuelType newFuelType) {
		FuelType oldFuelType = FUEL_TYPE_EFLAG_VALUES[(flags & FUEL_TYPE_EFLAG) >>> FUEL_TYPE_EFLAG_OFFSET];
		if (newFuelType == null) newFuelType = FUEL_TYPE_EDEFAULT;
		flags = flags & ~FUEL_TYPE_EFLAG | newFuelType.ordinal() << FUEL_TYPE_EFLAG_OFFSET;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__FUEL_TYPE, oldFuelType, newFuelType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMultipoint() {
		return (flags & MULTIPOINT_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultipoint(boolean newMultipoint) {
		boolean oldMultipoint = (flags & MULTIPOINT_EFLAG) != 0;
		if (newMultipoint) flags |= MULTIPOINT_EFLAG; else flags &= ~MULTIPOINT_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__MULTIPOINT, oldMultipoint, newMultipoint));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__EFFICIENCY, oldEfficiency, efficiency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * We need to introduce an energy transducer which does some water heating.
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	public void accept(IConstants constants, IEnergyCalculatorParameters parameters, IEnergyCalculatorVisitor visitor, AtomicInteger heatingSystemCounter, IHeatProportions heatProportions) {
		if (heatProportions.providesHotWater(this)) {
			visitor.visitEnergyTransducer(new PointOfUseWaterHeaterTransducer(
					5 //TODO this is less than ideal; should come after shower and central water heater.
					, getFuelType(), 
					1, 
					getEfficiency().value, 
					isMultipoint()
				));
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
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__FUEL_TYPE:
				return getFuelType();
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__MULTIPOINT:
				return isMultipoint();
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__EFFICIENCY:
				return getEfficiency();
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
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__FUEL_TYPE:
				setFuelType((FuelType)newValue);
				return;
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__MULTIPOINT:
				setMultipoint((Boolean)newValue);
				return;
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__EFFICIENCY:
				setEfficiency((Efficiency)newValue);
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
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__FUEL_TYPE:
				setFuelType(FUEL_TYPE_EDEFAULT);
				return;
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__MULTIPOINT:
				setMultipoint(MULTIPOINT_EDEFAULT);
				return;
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__EFFICIENCY:
				setEfficiency(EFFICIENCY_EDEFAULT);
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
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__FUEL_TYPE:
				return (flags & FUEL_TYPE_EFLAG) != FUEL_TYPE_EFLAG_DEFAULT;
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__MULTIPOINT:
				return ((flags & MULTIPOINT_EFLAG) != 0) != MULTIPOINT_EDEFAULT;
			case ITechnologiesPackage.POINT_OF_USE_WATER_HEATER__EFFICIENCY:
				return EFFICIENCY_EDEFAULT == null ? efficiency != null : !EFFICIENCY_EDEFAULT.equals(efficiency);
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
		result.append(" (fuelType: ");
		result.append(FUEL_TYPE_EFLAG_VALUES[(flags & FUEL_TYPE_EFLAG) >>> FUEL_TYPE_EFLAG_OFFSET]);
		result.append(", multipoint: ");
		result.append((flags & MULTIPOINT_EFLAG) != 0);
		result.append(", efficiency: ");
		result.append(efficiency);
		result.append(')');
		return result.toString();
	}

} //PointOfUseWaterHeaterImpl
