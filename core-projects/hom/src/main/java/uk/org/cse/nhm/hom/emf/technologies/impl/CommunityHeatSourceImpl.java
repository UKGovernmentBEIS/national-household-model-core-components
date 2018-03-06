/**
 */
package uk.org.cse.nhm.hom.emf.technologies.impl;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.impl.EnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.types.*;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.hom.constants.CommunityHeatingConstants;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesPackage;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.util.Efficiency;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Community Heat Source</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CommunityHeatSourceImpl#isChargingUsageBased <em>Charging Usage Based</em>}</li>
 *   <li>{@link uk.org.cse.nhm.hom.emf.technologies.impl.CommunityHeatSourceImpl#getHeatEfficiency <em>Heat Efficiency</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CommunityHeatSourceImpl extends HeatSourceImpl implements ICommunityHeatSource {
	private static final Logger log = LoggerFactory.getLogger(CommunityHeatSourceImpl.class);

	/**
	 * The default value of the '{@link #isChargingUsageBased() <em>Charging Usage Based</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #isChargingUsageBased()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CHARGING_USAGE_BASED_EDEFAULT = false;
	/**
	 * The flag representing the value of the '{@link #isChargingUsageBased() <em>Charging Usage Based</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isChargingUsageBased()
	 * @generated
	 * @ordered
	 */
	protected static final int CHARGING_USAGE_BASED_EFLAG = 1 << 8;

	/**
	 * The default value of the '{@link #getHeatEfficiency() <em>Heat Efficiency</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getHeatEfficiency()
	 * @generated
	 * @ordered
	 */
	protected static final Efficiency HEAT_EFFICIENCY_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getHeatEfficiency() <em>Heat Efficiency</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getHeatEfficiency()
	 * @generated
	 * @ordered
	 */
	protected Efficiency heatEfficiency = HEAT_EFFICIENCY_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected CommunityHeatSourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ITechnologiesPackage.Literals.COMMUNITY_HEAT_SOURCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isChargingUsageBased() {
		return (flags & CHARGING_USAGE_BASED_EFLAG) != 0;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setChargingUsageBased(boolean newChargingUsageBased) {
		boolean oldChargingUsageBased = (flags & CHARGING_USAGE_BASED_EFLAG) != 0;
		if (newChargingUsageBased) flags |= CHARGING_USAGE_BASED_EFLAG; else flags &= ~CHARGING_USAGE_BASED_EFLAG;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COMMUNITY_HEAT_SOURCE__CHARGING_USAGE_BASED, oldChargingUsageBased, newChargingUsageBased));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Efficiency getHeatEfficiency() {
		return heatEfficiency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHeatEfficiency(Efficiency newHeatEfficiency) {
		Efficiency oldHeatEfficiency = heatEfficiency;
		heatEfficiency = newHeatEfficiency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ITechnologiesPackage.COMMUNITY_HEAT_SOURCE__HEAT_EFFICIENCY, oldHeatEfficiency, heatEfficiency));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ITechnologiesPackage.COMMUNITY_HEAT_SOURCE__CHARGING_USAGE_BASED:
				return isChargingUsageBased();
			case ITechnologiesPackage.COMMUNITY_HEAT_SOURCE__HEAT_EFFICIENCY:
				return getHeatEfficiency();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ITechnologiesPackage.COMMUNITY_HEAT_SOURCE__CHARGING_USAGE_BASED:
				setChargingUsageBased((Boolean)newValue);
				return;
			case ITechnologiesPackage.COMMUNITY_HEAT_SOURCE__HEAT_EFFICIENCY:
				setHeatEfficiency((Efficiency)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.COMMUNITY_HEAT_SOURCE__CHARGING_USAGE_BASED:
				setChargingUsageBased(CHARGING_USAGE_BASED_EDEFAULT);
				return;
			case ITechnologiesPackage.COMMUNITY_HEAT_SOURCE__HEAT_EFFICIENCY:
				setHeatEfficiency(HEAT_EFFICIENCY_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ITechnologiesPackage.COMMUNITY_HEAT_SOURCE__CHARGING_USAGE_BASED:
				return ((flags & CHARGING_USAGE_BASED_EFLAG) != 0) != CHARGING_USAGE_BASED_EDEFAULT;
			case ITechnologiesPackage.COMMUNITY_HEAT_SOURCE__HEAT_EFFICIENCY:
				return HEAT_EFFICIENCY_EDEFAULT == null ? heatEfficiency != null : !HEAT_EFFICIENCY_EDEFAULT.equals(heatEfficiency);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated no
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		return (getFuel() == null ? "null" : getFuel().getName()) + " DH " + (heatEfficiency.value * 100) + "%";
	}

	protected void satisfySpaceDemand(final IConstants constants, final IEnergyState state, final double amount, final double controlFactor) {
		state.increaseSupply(EnergyType.DemandsHEAT, amount);

		/**
		 * The distribution loss factor, from SAP table 12c. We don't have any
		 * of the information required to get this number!
		 */
		final double distributionLossFactor = constants.get(CommunityHeatingConstants.DEFAULT_DISTRIBUTION_LOSS_FACTOR);

		/*
		BEISDOC
		NAME: Community space heat fuel energy demand
		DESCRIPTION: The total fuel demanded for community space heating, including the distribution loss factor and the adjustmentments from Table 4c.
		TYPE: formula
		UNIT: W
		SAP: (211)
                SAP_COMPLIANT: Yes
                BREDEM_COMPLIANT: N/A - out of scope
		DEPS: community-distribution-loss-factor,community-space-heating-energy-multipliers,space-heating-fraction,heat-demand
		ID: community-space-heating-fuel-energy-demand
		CODSIEB
		*/
		final double communityHeatDemand = amount * controlFactor * distributionLossFactor;

		state.increaseDemand(EnergyType.FuelCOMMUNITY_HEAT, communityHeatDemand);

		consumeSystemFuel(constants, state, communityHeatDemand);
	}

	private double getWaterHeatingControlFactor(IConstants constants, EnergyCalculatorType calculatorType) {
        /**
         * Control factor from SAP 2012 table 4c(3), worksheet cell 305a
         */
        if (calculatorType == EnergyCalculatorType.SAP2012) {
            if (isChargingUsageBased()) {
                return constants.get(CommunityHeatingConstants.LOW_WATER_USAGE_MULTIPLIER);
            } else {
                return constants.get(CommunityHeatingConstants.HIGH_WATER_USAGE_MULTIPLIER);
            }
        } else {
            // Hot water usage adjustments only apply to the SAP calculator.
            return 1;
        }
    }

	protected void satisfyHotWaterDemand(final IConstants constants, final IEnergyState state, final double hw, final double gains, final double controlFactor) {
		state.increaseSupply(EnergyType.DemandsHOT_WATER, hw);
		state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, gains);

		final double amount = hw + gains;

		final double distributionLossFactor = constants.get(CommunityHeatingConstants.DEFAULT_DISTRIBUTION_LOSS_FACTOR);

		final double communityHeatRequired = amount * controlFactor * distributionLossFactor;

		state.increaseDemand(EnergyType.FuelCOMMUNITY_HEAT, communityHeatRequired);

		consumeSystemFuel(constants, state, communityHeatRequired);

		log.debug("Generated {} of hot water and {} of gains with {} of energy", hw, gains, communityHeatRequired);
	}

	/**
	 * Called to do whatever is required to record the amount of source-fuel
	 * used by the community heat source in generating the heat that the user
	 * will pay for.
	 *
	 * @param state
	 *            the state to add some community fuel usage to (and/or
	 *            electricity generated, in CHP)
	 * @param communityHeatRequired
	 *            the amount of heat being consumed by the household
	 */
	protected void consumeSystemFuel(final IConstants constants, final IEnergyState state, final double communityHeatRequired) {
		final double communitySystemFuelDemand = (communityHeatRequired

		// *
		// constants.get(CommunityHeatingConstants.DEFAULT_DISTRIBUTION_LOSS_FACTOR)

				)
				/ getHeatEfficiency().value;
		state.increaseDemand(getFuel().getCommunityEnergyType(), communitySystemFuelDemand);
	}

	protected Set<EnergyType> getHeatingOutputs() {
		return Collections.singleton(EnergyType.DemandsHEAT);
	}

	@Override
	public void acceptFromHeating(final IConstants constants, final IEnergyCalculatorParameters parameters, final IEnergyCalculatorVisitor visitor, final double proportion, final int priority) {

        StepRecorder.recordStep(EnergyCalculationStep.Community_DistributionLossFactor, constants.get(CommunityHeatingConstants.DEFAULT_DISTRIBUTION_LOSS_FACTOR));

        final double spaceHeatingControlFactor = getSpaceHeatingControlFactor(
                constants,
                parameters.getCalculatorType(),
                getSpaceHeater().isThermostaticallyControlled(),
                getSpaceHeater().getControls()
        );

        StepRecorder.recordStep(EnergyCalculationStep.Community_ChargingFactor_SpaceHeating, spaceHeatingControlFactor);

		visitor.visitEnergyTransducer(new EnergyTransducer(ServiceType.PRIMARY_SPACE_HEATING, priority) {

			@Override
			public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state) {
				final double gen = state.getBoundedTotalHeatDemand(proportion);

				satisfySpaceDemand(parameters.getConstants(), state, gen, spaceHeatingControlFactor);
			}

			@Override
			public String toString() {
				return "Community Heat Source";
			}

			@Override
			public TransducerPhaseType getPhase() {
				return TransducerPhaseType.Heat;
			}
		});

		// Heat demand is always converted 1-for-1 into community heat
		StepRecorder.recordStep(EnergyCalculationStep.SpaceHeating_Efficiency_Main_System1, 1);
	}

	private double getSpaceHeatingControlFactor(
	        final IConstants constants,
            final EnergyCalculatorType calculatorType,
            final boolean systemIsThermostaticallyControlled,
            final EList<HeatingSystemControlType> controls
                                                ) {
        /**
         * This is the factor taken from SAP 2012 Table 4c (3)
         */
        final double controlFactor;

        if (calculatorType != EnergyCalculatorType.SAP2012) {
            controlFactor = 1;
        }
        else if (isChargingUsageBased()) {
            // it seems like the discriminating factor here is whether there are
            // TRVs in the space heater.
            if (controls.contains(HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE)) {
                // 2310, 2306
                controlFactor = constants.get(CommunityHeatingConstants.LOW_SPACE_USAGE_MULTIPLIER);
                log.debug("Control factor for usage-based thermostatic systems : {}", controlFactor);
            } else {
                // 2308, 2309
                controlFactor = constants.get(CommunityHeatingConstants.MEDIUM_SPACE_USAGE_MULTIPLER);
                log.debug("Control factor usage based non-thermostatic systems : {}", controlFactor);
            }
        } else {
            if (systemIsThermostaticallyControlled) {
                // Codes from table in this branch:
                // 2303, 2304, 2307, 2305
                controlFactor = constants.get(CommunityHeatingConstants.MEDIUM_SPACE_USAGE_MULTIPLER);
                log.debug("Control factor for thermostatically controlled flate rate : {}", controlFactor);
            } else {
                // codes in this branch
                // 2301, 2302
                controlFactor = constants.get(CommunityHeatingConstants.HIGH_SPACE_USAGE_MULTIPLER);
                log.debug("Control factor for non-thermostatically controlled flat rate : {}", controlFactor);
            }
        }
        return controlFactor;
    }

	@Override
	public double getDemandTemperatureAdjustment(final IInternalParameters parameters, final EList<HeatingSystemControlType> controlTypes) {

		if (!isChargingUsageBased()) {
			if (!(
			// flat rate charging and no thermostatic control
			controlTypes.contains(HeatingSystemControlType.ROOM_THERMOSTAT) || controlTypes.contains(HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL)
					|| controlTypes.contains(HeatingSystemControlType.APPLIANCE_THERMOSTAT) || controlTypes
						.contains(HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE))) {
				return parameters.getConstants().get(CommunityHeatingConstants.DEMAND_TEMPERATURE_ADJUSTMENT);
			}
		}

		return 0;
	}

	@Override
	public double generateHotWaterAndPrimaryGains(final IInternalParameters parameters, final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary,
			final double primaryPipeworkLosses, final double distributionLossFactor, final double proportion) {
		final IConstants constants = parameters.getConstants();

		final double demandSatisfied = state.getBoundedTotalDemand(EnergyType.DemandsHOT_WATER, proportion);

		log.debug("{} pp losses, {} demand satisied", primaryPipeworkLosses, demandSatisfied);

		// Heating demand is converted 1-for-1 into CommunityHeat
		StepRecorder.recordStep(EnergyCalculationStep.WaterHeating_Efficiency, 1);

		final double controlFactor = getWaterHeatingControlFactor(constants, parameters.getCalculatorType());
        StepRecorder.recordStep(EnergyCalculationStep.Community_ChargingFactor_WaterHeating, controlFactor);

		satisfyHotWaterDemand(constants, state, demandSatisfied, primaryPipeworkLosses, controlFactor);

		return demandSatisfied;
	}

	@Override
	public void generateHotWaterSystemGains(final IInternalParameters parameters, final IEnergyState state, final IWaterTank store, final boolean storeIsPrimary, final double systemLosses) {
		satisfyHotWaterDemand(parameters.getConstants(), state, 0, systemLosses,
                getWaterHeatingControlFactor(parameters.getConstants(), parameters.getCalculatorType()));
	}

	static IWaterTank SPURIOUS_TANK;

	/**
	 * @return a spurious 110l 50mm factory foam tank
	 * @oddity Community heating systems in houses with no hot water tank
	 *         exhibit additional tank-like losses for a 110l, 50mm factory
	 *         insulated tank
	 */
	private static IWaterTank getSpuriousTank() {
		synchronized (CommunityHeatSourceImpl.class) {
			if (SPURIOUS_TANK == null) {
				SPURIOUS_TANK = ITechnologiesFactory.eINSTANCE.createWaterTank();
				SPURIOUS_TANK.setFactoryInsulation(true);
				SPURIOUS_TANK.setInsulation(50d);
				SPURIOUS_TANK.setThermostatFitted(false);
				SPURIOUS_TANK.setVolume(110);
			}

			return SPURIOUS_TANK;
		}
	}

	@Override
	public double getContainedTankLosses(final IInternalParameters parameters) {
		if (getWaterHeater() != null) {
			if (getWaterHeater().getSystem() != null) {
				if (getWaterHeater().getSystem().getStore() == null) {
					final IWaterTank tank = getSpuriousTank();
					return tank.getStandingLosses(
					        parameters,
                            getStorageTemperatureFactor(parameters, tank, false));
				}
			}
		}

		return 0;
	}

	@Override
	public Zone2ControlParameter getZoneTwoControlParameter(final IInternalParameters parameters, final EList<HeatingSystemControlType> controls, final EmitterType emitterType) {
		final boolean hasTRVs = controls.contains(HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE);

		if (isChargingUsageBased()) {
			return hasTRVs ? Zone2ControlParameter.Three : Zone2ControlParameter.Two;
		} else {
			return hasTRVs ? Zone2ControlParameter.Two : Zone2ControlParameter.One;
		}
	}

	@Override
	public boolean isCommunityHeating() {
		return true;
	}

	@Override
	public double getResponsiveness(final IConstants parameters, final EList<HeatingSystemControlType> controls,
			final EmitterType emitterType) {
		return 1;
	}
} // CommunityHeatSourceImpl
