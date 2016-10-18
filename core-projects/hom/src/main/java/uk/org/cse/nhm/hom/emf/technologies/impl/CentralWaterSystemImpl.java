/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.hom.IHeatProportions;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ISolarWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStoreContainer;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IVisitorAccepter;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.impl.util.CentralHotWaterTransducer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Central Water System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl#getStore <em>Store</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl#isStoreInPrimaryCircuit <em>Store In Primary Circuit</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl#isPrimaryPipeworkInsulated <em>Primary Pipework Insulated</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl#isSeparatelyTimeControlled <em>Separately Time Controlled</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl#getSolarWaterHeater <em>Solar Water Heater</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl#getPrimaryWaterHeater <em>Primary Water Heater</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl#getSecondaryWaterHeater <em>Secondary Water Heater</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CentralWaterSystemImpl extends WaterHeaterImpl implements ICentralWaterSystem {
	/**
	 * The cached value of the '{@link #getStore() <em>Store</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStore()
	 * @generated
	 * @ordered
	 */
	protected IWaterTank store;

	/**
	 * The default value of the '{@link #isStoreInPrimaryCircuit() <em>Store In Primary Circuit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStoreInPrimaryCircuit()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STORE_IN_PRIMARY_CIRCUIT_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isStoreInPrimaryCircuit() <em>Store In Primary Circuit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStoreInPrimaryCircuit()
	 * @generated
	 * @ordered
	 */
	protected static final int STORE_IN_PRIMARY_CIRCUIT_EFLAG = 1 << 0;

	/**
	 * The default value of the '{@link #isPrimaryPipeworkInsulated() <em>Primary Pipework Insulated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPrimaryPipeworkInsulated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PRIMARY_PIPEWORK_INSULATED_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isPrimaryPipeworkInsulated() <em>Primary Pipework Insulated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPrimaryPipeworkInsulated()
	 * @generated
	 * @ordered
	 */
	protected static final int PRIMARY_PIPEWORK_INSULATED_EFLAG = 1 << 1;

	/**
	 * The default value of the '{@link #isSeparatelyTimeControlled() <em>Separately Time Controlled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSeparatelyTimeControlled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SEPARATELY_TIME_CONTROLLED_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isSeparatelyTimeControlled() <em>Separately Time Controlled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSeparatelyTimeControlled()
	 * @generated
	 * @ordered
	 */
	protected static final int SEPARATELY_TIME_CONTROLLED_EFLAG = 1 << 2;

	/**
	 * The cached value of the '{@link #getSolarWaterHeater() <em>Solar Water Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolarWaterHeater()
	 * @generated
	 * @ordered
	 */
	protected ISolarWaterHeater solarWaterHeater;

	/**
	 * The cached value of the '{@link #getPrimaryWaterHeater() <em>Primary Water Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimaryWaterHeater()
	 * @generated
	 * @ordered
	 */
	protected ICentralWaterHeater primaryWaterHeater;

	/**
	 * The cached value of the '{@link #getSecondaryWaterHeater() <em>Secondary Water Heater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryWaterHeater()
	 * @generated
	 * @ordered
	 */
	protected ICentralWaterHeater secondaryWaterHeater;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CentralWaterSystemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.CENTRAL_WATER_SYSTEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IWaterTank getStore() {
		return store;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStore(IWaterTank newStore, NotificationChain msgs) {
		IWaterTank oldStore = store;
		store = newStore;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE, oldStore, newStore);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStore(IWaterTank newStore) {
		if (newStore != store) {
			NotificationChain msgs = null;
			if (store != null)
				msgs = ((InternalEObject)store).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE, null, msgs);
			if (newStore != null)
				msgs = ((InternalEObject)newStore).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE, null, msgs);
			msgs = basicSetStore(newStore, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE, newStore, newStore));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStoreInPrimaryCircuit() {
		return (flags & STORE_IN_PRIMARY_CIRCUIT_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStoreInPrimaryCircuit(boolean newStoreInPrimaryCircuit) {
		boolean oldStoreInPrimaryCircuit = (flags & STORE_IN_PRIMARY_CIRCUIT_EFLAG) != 0;
		if (newStoreInPrimaryCircuit) flags |= STORE_IN_PRIMARY_CIRCUIT_EFLAG; else flags &= ~STORE_IN_PRIMARY_CIRCUIT_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE_IN_PRIMARY_CIRCUIT, oldStoreInPrimaryCircuit, newStoreInPrimaryCircuit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPrimaryPipeworkInsulated() {
		return (flags & PRIMARY_PIPEWORK_INSULATED_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrimaryPipeworkInsulated(boolean newPrimaryPipeworkInsulated) {
		boolean oldPrimaryPipeworkInsulated = (flags & PRIMARY_PIPEWORK_INSULATED_EFLAG) != 0;
		if (newPrimaryPipeworkInsulated) flags |= PRIMARY_PIPEWORK_INSULATED_EFLAG; else flags &= ~PRIMARY_PIPEWORK_INSULATED_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_PIPEWORK_INSULATED, oldPrimaryPipeworkInsulated, newPrimaryPipeworkInsulated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSeparatelyTimeControlled() {
		return (flags & SEPARATELY_TIME_CONTROLLED_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeparatelyTimeControlled(boolean newSeparatelyTimeControlled) {
		boolean oldSeparatelyTimeControlled = (flags & SEPARATELY_TIME_CONTROLLED_EFLAG) != 0;
		if (newSeparatelyTimeControlled) flags |= SEPARATELY_TIME_CONTROLLED_EFLAG; else flags &= ~SEPARATELY_TIME_CONTROLLED_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SEPARATELY_TIME_CONTROLLED, oldSeparatelyTimeControlled, newSeparatelyTimeControlled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ISolarWaterHeater getSolarWaterHeater() {
		return solarWaterHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSolarWaterHeater(ISolarWaterHeater newSolarWaterHeater, NotificationChain msgs) {
		ISolarWaterHeater oldSolarWaterHeater = solarWaterHeater;
		solarWaterHeater = newSolarWaterHeater;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER, oldSolarWaterHeater, newSolarWaterHeater);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSolarWaterHeater(ISolarWaterHeater newSolarWaterHeater) {
		if (newSolarWaterHeater != solarWaterHeater) {
			NotificationChain msgs = null;
			if (solarWaterHeater != null)
				msgs = ((InternalEObject)solarWaterHeater).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER, null, msgs);
			if (newSolarWaterHeater != null)
				msgs = ((InternalEObject)newSolarWaterHeater).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER, null, msgs);
			msgs = basicSetSolarWaterHeater(newSolarWaterHeater, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER, newSolarWaterHeater, newSolarWaterHeater));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICentralWaterHeater getPrimaryWaterHeater() {
		return primaryWaterHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrimaryWaterHeater(ICentralWaterHeater newPrimaryWaterHeater, NotificationChain msgs) {
		ICentralWaterHeater oldPrimaryWaterHeater = primaryWaterHeater;
		primaryWaterHeater = newPrimaryWaterHeater;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER, oldPrimaryWaterHeater, newPrimaryWaterHeater);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrimaryWaterHeater(ICentralWaterHeater newPrimaryWaterHeater) {
		if (newPrimaryWaterHeater != primaryWaterHeater) {
			NotificationChain msgs = null;
			if (primaryWaterHeater != null)
				msgs = ((InternalEObject)primaryWaterHeater).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER, null, msgs);
			if (newPrimaryWaterHeater != null)
				msgs = ((InternalEObject)newPrimaryWaterHeater).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER, null, msgs);
			msgs = basicSetPrimaryWaterHeater(newPrimaryWaterHeater, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER, newPrimaryWaterHeater, newPrimaryWaterHeater));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ICentralWaterHeater getSecondaryWaterHeater() {
		return secondaryWaterHeater;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecondaryWaterHeater(ICentralWaterHeater newSecondaryWaterHeater, NotificationChain msgs) {
		ICentralWaterHeater oldSecondaryWaterHeater = secondaryWaterHeater;
		secondaryWaterHeater = newSecondaryWaterHeater;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER, oldSecondaryWaterHeater, newSecondaryWaterHeater);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecondaryWaterHeater(ICentralWaterHeater newSecondaryWaterHeater) {
		if (newSecondaryWaterHeater != secondaryWaterHeater) {
			NotificationChain msgs = null;
			if (secondaryWaterHeater != null)
				msgs = ((InternalEObject)secondaryWaterHeater).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER, null, msgs);
			if (newSecondaryWaterHeater != null)
				msgs = ((InternalEObject)newSecondaryWaterHeater).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER, null, msgs);
			msgs = basicSetSecondaryWaterHeater(newSecondaryWaterHeater, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER, newSecondaryWaterHeater, newSecondaryWaterHeater));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBroken() {
		return getPrimaryWaterHeater() == null && getSecondaryWaterHeater() == null && 
						(getStore() == null || getStore().getImmersionHeater() == null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated no
	 */
	public void accept(IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, AtomicInteger heatingSystemCounter, IHeatProportions heatProportions) {
		if (!isBroken() && heatProportions.providesHotWater(this)) {
			visitor.visitEnergyTransducer(new CentralHotWaterTransducer(this, parameters));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE:
				return basicSetStore(null, msgs);
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER:
				return basicSetSolarWaterHeater(null, msgs);
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER:
				return basicSetPrimaryWaterHeater(null, msgs);
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER:
				return basicSetSecondaryWaterHeater(null, msgs);
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
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE:
				return getStore();
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE_IN_PRIMARY_CIRCUIT:
				return isStoreInPrimaryCircuit();
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_PIPEWORK_INSULATED:
				return isPrimaryPipeworkInsulated();
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SEPARATELY_TIME_CONTROLLED:
				return isSeparatelyTimeControlled();
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER:
				return getSolarWaterHeater();
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER:
				return getPrimaryWaterHeater();
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER:
				return getSecondaryWaterHeater();
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
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE:
				setStore((IWaterTank)newValue);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE_IN_PRIMARY_CIRCUIT:
				setStoreInPrimaryCircuit((Boolean)newValue);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_PIPEWORK_INSULATED:
				setPrimaryPipeworkInsulated((Boolean)newValue);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SEPARATELY_TIME_CONTROLLED:
				setSeparatelyTimeControlled((Boolean)newValue);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER:
				setSolarWaterHeater((ISolarWaterHeater)newValue);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER:
				setPrimaryWaterHeater((ICentralWaterHeater)newValue);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER:
				setSecondaryWaterHeater((ICentralWaterHeater)newValue);
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
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE:
				setStore((IWaterTank)null);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE_IN_PRIMARY_CIRCUIT:
				setStoreInPrimaryCircuit(STORE_IN_PRIMARY_CIRCUIT_EDEFAULT);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_PIPEWORK_INSULATED:
				setPrimaryPipeworkInsulated(PRIMARY_PIPEWORK_INSULATED_EDEFAULT);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SEPARATELY_TIME_CONTROLLED:
				setSeparatelyTimeControlled(SEPARATELY_TIME_CONTROLLED_EDEFAULT);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER:
				setSolarWaterHeater((ISolarWaterHeater)null);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER:
				setPrimaryWaterHeater((ICentralWaterHeater)null);
				return;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER:
				setSecondaryWaterHeater((ICentralWaterHeater)null);
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
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE:
				return store != null;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE_IN_PRIMARY_CIRCUIT:
				return ((flags & STORE_IN_PRIMARY_CIRCUIT_EFLAG) != 0) != STORE_IN_PRIMARY_CIRCUIT_EDEFAULT;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_PIPEWORK_INSULATED:
				return ((flags & PRIMARY_PIPEWORK_INSULATED_EFLAG) != 0) != PRIMARY_PIPEWORK_INSULATED_EDEFAULT;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SEPARATELY_TIME_CONTROLLED:
				return ((flags & SEPARATELY_TIME_CONTROLLED_EFLAG) != 0) != SEPARATELY_TIME_CONTROLLED_EDEFAULT;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SOLAR_WATER_HEATER:
				return solarWaterHeater != null;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__PRIMARY_WATER_HEATER:
				return primaryWaterHeater != null;
			case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__SECONDARY_WATER_HEATER:
				return secondaryWaterHeater != null;
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
		if (baseClass == IStoreContainer.class) {
			switch (derivedFeatureID) {
				case ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE: return ITechnologiesPackage.STORE_CONTAINER__STORE;
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
		if (baseClass == IStoreContainer.class) {
			switch (baseFeatureID) {
				case ITechnologiesPackage.STORE_CONTAINER__STORE: return ITechnologiesPackage.CENTRAL_WATER_SYSTEM__STORE;
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
		result.append(" (storeInPrimaryCircuit: ");
		result.append((flags & STORE_IN_PRIMARY_CIRCUIT_EFLAG) != 0);
		result.append(", primaryPipeworkInsulated: ");
		result.append((flags & PRIMARY_PIPEWORK_INSULATED_EFLAG) != 0);
		result.append(", separatelyTimeControlled: ");
		result.append((flags & SEPARATELY_TIME_CONTROLLED_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean hasImmersionHeater() {
		return getStore() != null && getStore().getImmersionHeater() != null;
	}

} //CentralWaterSystemImpl
