/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Keep Hot Facility</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.KeepHotFacilityImpl#isTimeClock <em>Time Clock</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class KeepHotFacilityImpl extends MinimalEObjectImpl implements IKeepHotFacility {
	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected int flags = 0;

	/**
	 * The default value of the '{@link #isTimeClock() <em>Time Clock</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTimeClock()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TIME_CLOCK_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isTimeClock() <em>Time Clock</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTimeClock()
	 * @generated
	 * @ordered
	 */
	protected static final int TIME_CLOCK_EFLAG = 1 << 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KeepHotFacilityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBoilersPackage.Literals.KEEP_HOT_FACILITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTimeClock() {
		return (flags & TIME_CLOCK_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeClock(boolean newTimeClock) {
		boolean oldTimeClock = (flags & TIME_CLOCK_EFLAG) != 0;
		if (newTimeClock) flags |= TIME_CLOCK_EFLAG; else flags &= ~TIME_CLOCK_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.KEEP_HOT_FACILITY__TIME_CLOCK, oldTimeClock, newTimeClock));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IBoilersPackage.KEEP_HOT_FACILITY__TIME_CLOCK:
				return isTimeClock();
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
			case IBoilersPackage.KEEP_HOT_FACILITY__TIME_CLOCK:
				setTimeClock((Boolean)newValue);
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
			case IBoilersPackage.KEEP_HOT_FACILITY__TIME_CLOCK:
				setTimeClock(TIME_CLOCK_EDEFAULT);
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
			case IBoilersPackage.KEEP_HOT_FACILITY__TIME_CLOCK:
				return ((flags & TIME_CLOCK_EFLAG) != 0) != TIME_CLOCK_EDEFAULT;
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
		result.append(" (timeClock: ");
		result.append((flags & TIME_CLOCK_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}

	/**
	 * TODO this does not handle the behaviour when the keep hot facility is electrically powered.
	 */
	@Override
	public double getAdditionalUsageLosses(final IInternalParameters parameters, final IEnergyState state) {
		if (isTimeClock()) {
			return parameters.getConstants().get(HeatingSystemConstants.INSTANTANEOUS_COMBI_FACTOR_KHF_WITH_TIMECLOCK);
		} else {
			return parameters.getConstants().get(HeatingSystemConstants.INSTANTANEOUS_COMBI_FACTOR_KHF_WITHOUT_TIMECLOCK);
		}
	}
} //KeepHotFacilityImpl
