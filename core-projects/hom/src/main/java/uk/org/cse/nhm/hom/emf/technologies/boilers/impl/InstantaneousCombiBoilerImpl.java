/**
 */
package uk.org.cse.nhm.hom.emf.technologies.boilers.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersPackage;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IKeepHotFacility;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instantaneous Combi Boiler</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.boilers.impl.InstantaneousCombiBoilerImpl#getKeepHotFacility <em>Keep Hot Facility</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InstantaneousCombiBoilerImpl extends CombiBoilerImpl implements IInstantaneousCombiBoiler {
	/**
	 * The cached value of the '{@link #getKeepHotFacility() <em>Keep Hot Facility</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeepHotFacility()
	 * @generated
	 * @ordered
	 */
	protected IKeepHotFacility keepHotFacility;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstantaneousCombiBoilerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBoilersPackage.Literals.INSTANTANEOUS_COMBI_BOILER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IKeepHotFacility getKeepHotFacility() {
		return keepHotFacility;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKeepHotFacility(IKeepHotFacility newKeepHotFacility, NotificationChain msgs) {
		IKeepHotFacility oldKeepHotFacility = keepHotFacility;
		keepHotFacility = newKeepHotFacility;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBoilersPackage.INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY, oldKeepHotFacility, newKeepHotFacility);
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
	public void setKeepHotFacility(IKeepHotFacility newKeepHotFacility) {
		if (newKeepHotFacility != keepHotFacility) {
			NotificationChain msgs = null;
			if (keepHotFacility != null)
				msgs = ((InternalEObject)keepHotFacility).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBoilersPackage.INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY, null, msgs);
			if (newKeepHotFacility != null)
				msgs = ((InternalEObject)newKeepHotFacility).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBoilersPackage.INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY, null, msgs);
			msgs = basicSetKeepHotFacility(newKeepHotFacility, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBoilersPackage.INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY, newKeepHotFacility, newKeepHotFacility));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IBoilersPackage.INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY:
				return basicSetKeepHotFacility(null, msgs);
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
			case IBoilersPackage.INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY:
				return getKeepHotFacility();
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
			case IBoilersPackage.INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY:
				setKeepHotFacility((IKeepHotFacility)newValue);
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
			case IBoilersPackage.INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY:
				setKeepHotFacility((IKeepHotFacility)null);
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
			case IBoilersPackage.INSTANTANEOUS_COMBI_BOILER__KEEP_HOT_FACILITY:
				return keepHotFacility != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	protected double getAdditionalUsageLosses(final IInternalParameters parameters, final IEnergyState state) {
		/*
		BEISDOC
		NAME: Instant combi losses
		DESCRIPTION: Extra losses which only apply to combi boilers - instantaneous version
		TYPE: formula
		UNIT: W
		SAP: Table 3a
                SAP_COMPLIANT: Yes
		BREDEM: Table 13
                BREDEM_COMPLIANT: Yes
		DEPS: combi-losses-instant-keep-hot,instantaneous-factor,combi-loss-water-usage-limit
		ID: combi-losses-instant
		CODSIEB
		*/
		final IKeepHotFacility facility = getKeepHotFacility();
		if (facility == null) {
			//SAP table 3a, instantaneous combi without anything
			final double fu =
					Math.min(1.0d,
							state.getTotalDemand(EnergyType.DemandsHOT_WATER_VOLUME)
							/
							parameters.getConstants().get(HeatingSystemConstants.COMBI_HOT_WATER_USAGE_LIMIT));
			return fu * parameters.getConstants().get(HeatingSystemConstants.INSTANTANEOUS_COMBI_FACTOR);
		} else {
			return facility.getAdditionalUsageLosses(parameters, state);
		}
	}
} //InstantaneousCombiBoilerImpl
