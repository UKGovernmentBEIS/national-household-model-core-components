/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.StepRecorder;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirCirculator;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Warm Air Circulator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.WarmAirCirculatorImpl#getWarmAirSystem <em>Warm Air System</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WarmAirCirculatorImpl extends CentralWaterHeaterImpl implements IWarmAirCirculator {
	private static final Logger log = LoggerFactory.getLogger(WarmAirCirculatorImpl.class);
	
	/**
	 * The cached value of the '{@link #getWarmAirSystem() <em>Warm Air System</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWarmAirSystem()
	 * @generated
	 * @ordered
	 */
	protected IWarmAirSystem warmAirSystem;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WarmAirCirculatorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.WARM_AIR_CIRCULATOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IWarmAirSystem getWarmAirSystem() {
		if (warmAirSystem != null && warmAirSystem.eIsProxy()) {
			InternalEObject oldWarmAirSystem = (InternalEObject)warmAirSystem;
			warmAirSystem = (IWarmAirSystem)eResolveProxy(oldWarmAirSystem);
			if (warmAirSystem != oldWarmAirSystem) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM, oldWarmAirSystem, warmAirSystem));
			}
		}
		return warmAirSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IWarmAirSystem basicGetWarmAirSystem() {
		return warmAirSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWarmAirSystem(IWarmAirSystem newWarmAirSystem, NotificationChain msgs) {
		IWarmAirSystem oldWarmAirSystem = warmAirSystem;
		warmAirSystem = newWarmAirSystem;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM, oldWarmAirSystem, newWarmAirSystem);
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
	public void setWarmAirSystem(IWarmAirSystem newWarmAirSystem) {
		if (newWarmAirSystem != warmAirSystem) {
			NotificationChain msgs = null;
			if (warmAirSystem != null)
				msgs = ((InternalEObject)warmAirSystem).eInverseRemove(this, ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR, IWarmAirSystem.class, msgs);
			if (newWarmAirSystem != null)
				msgs = ((InternalEObject)newWarmAirSystem).eInverseAdd(this, ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR, IWarmAirSystem.class, msgs);
			msgs = basicSetWarmAirSystem(newWarmAirSystem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM, newWarmAirSystem, newWarmAirSystem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM:
				if (warmAirSystem != null)
					msgs = ((InternalEObject)warmAirSystem).eInverseRemove(this, ITechnologiesPackage.WARM_AIR_SYSTEM__CIRCULATOR, IWarmAirSystem.class, msgs);
				return basicSetWarmAirSystem((IWarmAirSystem)otherEnd, msgs);
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
			case ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM:
				return basicSetWarmAirSystem(null, msgs);
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
			case ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM:
				if (resolve) return getWarmAirSystem();
				return basicGetWarmAirSystem();
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
			case ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM:
				setWarmAirSystem((IWarmAirSystem)newValue);
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
			case ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM:
				setWarmAirSystem((IWarmAirSystem)null);
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
			case ITechnologiesPackage.WARM_AIR_CIRCULATOR__WARM_AIR_SYSTEM:
				return warmAirSystem != null;
		}
		return super.eIsSet(featureID);
	}
	
	/*
	 * Here beginneth the overridden water heating methods.
	 */

	@Override
	public double generateHotWaterAndPrimaryGains(final IInternalParameters parameters,
			final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
			final double primaryLosses, final double distributionLossFactor,
			final double systemProportion) {
		
		if (getWarmAirSystem().getFuelType() == FuelType.ELECTRICITY) {
			log.warn("An electric warm air system should not have a warm air circulator fitted");
			return 0;
		}
		
		final double demandToSatisfy = state.getBoundedTotalDemand(EnergyType.DemandsHOT_WATER, systemProportion);
		
		state.increaseSupply(EnergyType.DemandsHOT_WATER, demandToSatisfy);
		state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, primaryLosses);

		final double efficiency = getWarmAirSystem().getEfficiency().value;
		
		state.increaseDemand(getWarmAirSystem().getFuelType().getEnergyType(), 
				(demandToSatisfy + primaryLosses)
				/ efficiency);

		StepRecorder.recordStep(EnergyCalculationStep.WaterHeating_Efficiency, efficiency);
		
		return demandToSatisfy;
	}
	
	@Override
	public void generateSystemGains(final IInternalParameters parameters,
			final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
			final double systemLosses) {
		
		if (getWarmAirSystem().getFuelType() == FuelType.ELECTRICITY) {
			log.warn("An electric warm air system should not have a warm air circulator fitted");
			return;
		}
		
		state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, systemLosses);
		state.increaseDemand(getWarmAirSystem().getFuelType().getEnergyType(), 
				systemLosses / getWarmAirSystem().getEfficiency().value);		
	}

	@Override
	public boolean causesPipeworkLosses() {
		return true;
	}

	@Override
	public boolean isCommunityHeating() {
		return false;
	}
	
	@Override
	public FuelType getFuel() {
		if (getWarmAirSystem() != null) {
			return getWarmAirSystem().getFuel();
		} else {
			return null;
		}
	}
} //WarmAirCirculatorImpl
