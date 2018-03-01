/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import static uk.org.cse.nhm.hom.constants.SplitRateConstants.DUAL_IMMERSION_TERM1;
import static uk.org.cse.nhm.hom.constants.SplitRateConstants.DUAL_IMMERSION_TERM2;
import static uk.org.cse.nhm.hom.constants.SplitRateConstants.DUAL_IMMERSION_TERM3;
import static uk.org.cse.nhm.hom.constants.SplitRateConstants.DUAL_IMMERSION_TERM4;
import static uk.org.cse.nhm.hom.constants.SplitRateConstants.SINGLE_IMMERSION_TERM1;
import static uk.org.cse.nhm.hom.constants.SplitRateConstants.SINGLE_IMMERSION_TERM2;
import static uk.org.cse.nhm.hom.constants.SplitRateConstants.SINGLE_IMMERSION_TERM3;
import static uk.org.cse.nhm.hom.constants.SplitRateConstants.SINGLE_IMMERSION_TERM4;
import static uk.org.cse.nhm.hom.constants.SplitRateConstants.SINGLE_IMMERSION_TERM5;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.StepRecorder;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Immersion Heater</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.ImmersionHeaterImpl#isDualCoil <em>Dual Coil</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImmersionHeaterImpl extends CentralWaterHeaterImpl implements IImmersionHeater {
	private static final Logger log = LoggerFactory.getLogger(ImmersionHeaterImpl.class);

	/**
	 * The default value of the '{@link #isDualCoil() <em>Dual Coil</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDualCoil()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DUAL_COIL_EDEFAULT = false;

	/**
	 * The flag representing the value of the '{@link #isDualCoil() <em>Dual Coil</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDualCoil()
	 * @generated
	 * @ordered
	 */
	protected static final int DUAL_COIL_EFLAG = 1 << 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImmersionHeaterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.IMMERSION_HEATER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDualCoil() {
		return (flags & DUAL_COIL_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDualCoil(boolean newDualCoil) {
		boolean oldDualCoil = (flags & DUAL_COIL_EFLAG) != 0;
		if (newDualCoil) flags |= DUAL_COIL_EFLAG; else flags &= ~DUAL_COIL_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.IMMERSION_HEATER__DUAL_COIL, oldDualCoil, newDualCoil));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.IMMERSION_HEATER__DUAL_COIL:
				return isDualCoil();
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
			case ITechnologiesPackage.IMMERSION_HEATER__DUAL_COIL:
				setDualCoil((Boolean)newValue);
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
			case ITechnologiesPackage.IMMERSION_HEATER__DUAL_COIL:
				setDualCoil(DUAL_COIL_EDEFAULT);
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
			case ITechnologiesPackage.IMMERSION_HEATER__DUAL_COIL:
				return ((flags & DUAL_COIL_EFLAG) != 0) != DUAL_COIL_EDEFAULT;
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
		result.append(" (dualCoil: ");
		result.append((flags & DUAL_COIL_EFLAG) != 0);
		result.append(')');
		return result.toString();
	}

	@Override
	public double generateHotWaterAndPrimaryGains(final IInternalParameters parameters,
			final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
			final double primaryPipeworkLosses, final double distributionLossFactor,
			final double systemProportion) {
		if (store == null) {
			log.warn("Immersion heater used in system with no shared tank");
			return 0;
		}

		if (systemProportion > 0) {
		    // Electric immersion heater has an efficiency of 1.
            StepRecorder.recordStep(EnergyCalculationStep.WaterHeating_Efficiency, 1);
		}

		final double demand = systemProportion * state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER);

		final double highRateFraction = getHighRateFraction(parameters, store.getVolume());

		state.increaseElectricityDemand(highRateFraction, demand);

		state.increaseSupply(EnergyType.DemandsHOT_WATER, demand);

		return demand;
	}

	@Override
	public void generateSystemGains(final IInternalParameters parameters,
			final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
			final double systemLosses) {
		if (store == null) {
			log.warn("Immersion heater used in system with no shared tank");
			return;
		}

		final double highRateFraction = getHighRateFraction(parameters, store.getVolume());
		state.increaseElectricityDemand(highRateFraction, systemLosses);
		state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, systemLosses);
	}

	private double clamp(final double value) {
		if (value < 0) return 0;
		if (value > 1) return 1;
		return value;
	}

	private double getHighRateFraction(final IInternalParameters parameters, final double tankVolume) {
		final double numberOfPeople = parameters.getNumberOfOccupants();
		if (parameters.getTarrifType() == ElectricityTariffType.FLAT_RATE) return 1;

		final IConstants c = parameters.getConstants();
		final ElectricityTariffType tt = parameters.getTarrifType();
		/*
		BEISDOC
		NAME: Immersion Split Rate
		DESCRIPTION: The fraction of electricity used by an immersion heater which should be at the higher rate
		TYPE: formula
		UNIT: Dimensionless
		SAP: Table 13 (footnote 2)
                SAP_COMPLIANT: Yes
                BREDEM_COMPLIANT: N/A - out of scope
		DEPS: single-coil-immersion-split-rate-terms,dual-coil-immersion-split-rate-terms,occupancy,cylinder-volume,
		ID: immersion-split-rate
		CODSIEB
		*/
		if (isDualCoil()) {
			final double value = ((c.get(DUAL_IMMERSION_TERM1, tt) - c.get(DUAL_IMMERSION_TERM2, tt) * tankVolume) * numberOfPeople + c.get(DUAL_IMMERSION_TERM3, tt) - c.get(DUAL_IMMERSION_TERM4, tt) * tankVolume);
			return clamp(value / 100.0);
		} else {
			final double value = ((c.get(SINGLE_IMMERSION_TERM1, tt) - c.get(SINGLE_IMMERSION_TERM2, tt) * numberOfPeople) / (c.get(SINGLE_IMMERSION_TERM3, tt) * tankVolume) - c.get(SINGLE_IMMERSION_TERM4, tt) + c.get(SINGLE_IMMERSION_TERM5, tt) * numberOfPeople);
			return clamp(value / 100.0);
		}
	}

	@Override
	public boolean causesPipeworkLosses() {
		return false;
	}

	@Override
	public boolean isCommunityHeating() {
		return false;
	}
} //ImmersionHeaterImpl
