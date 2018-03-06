package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.impl.EnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.hom.constants.CylinderConstants;
import uk.org.cse.nhm.hom.constants.HeatingSystemConstants;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IImmersionHeater;
import uk.org.cse.nhm.hom.emf.technologies.IMainWaterHeater;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.impl.CentralWaterSystemImpl;

/**
 * This is a helper class for the {@link CentralWaterSystemImpl}, which contains the meat of the hot water usage
 * calculation defined in BREDEM 8 2009.
 *
 * It combines together all the {@link ICentralWaterHeater} implementations in the system to produce a single {@link IEnergyTransducer} that will
 * supply some quantity of hot water.
 *
 * In earlier versions this was an inner class in {@link CentralWaterHeatingComponent}.
 *
 * @author hinton
 *
 */
public class CentralHotWaterTransducer extends EnergyTransducer {
	private final ICentralWaterSystem system;
	private static final Logger log = LoggerFactory.getLogger(CentralHotWaterTransducer.class);

	public CentralHotWaterTransducer(final ICentralWaterSystem system, final IEnergyCalculatorParameters parameters) {
		super(ServiceType.WATER_HEATING, 1);
		this.system = system;
	}

	private static IImmersionHeater getTankHeater(final ICentralWaterSystem system) {
		if (system.getStore() != null) {
			return system.getStore().getImmersionHeater();
		} else {
			return null;
		}
	}

	private IImmersionHeater getTankHeater() {
		return getTankHeater(system);
	}

	/**
	 * @assumption When there are two powered DHW systems, tank losses are
	 *             apportioned to them pro rata their generated hot water (i.e.
	 *             if there is a system which produces 10% of hot water, it
	 *             satisfies 10% of demand, and pays for 10% of tank losses).
	 *             BREDEM does not specify this.
	 */
	@Override
	public void generate(final IEnergyCalculatorHouseCase house,
			final IInternalParameters parameters, final ISpecificHeatLosses losses,
			final IEnergyState state) {

		final IConstants constants = parameters.getConstants();

		/**
		 * Whether any of the water heaters in the system are solar water heaters.
		 */
		final boolean hasSolarHeater = system.getSolarWaterHeater() != null;

		final boolean cylinderHasThermostat =
				system.getStore() != null && system.getStore().isThermostatFitted();

		final boolean applySolarPipeworkFactor = hasSolarHeater && cylinderHasThermostat;

		/**
		 * The solar primary pipework correction factor
		 */
		final double solarPipeworkFactor =
				applySolarPipeworkFactor ?
						constants.get(HeatingSystemConstants.CENTRAL_HEATING_SOLAR_PPCF,
								parameters.getClimate().getMonthOfYear()-1) : 1d;

		/**
		 * The amount of water
		 */
		final double[] amountsGenerated = new double[3];

		/**
		 * The proportion of hot water demand that is emitted as distribution losses
		 */
		final double distributionLossFactor = constants.get(HeatingSystemConstants.CENTRAL_HEATING_DISTRIBUTION_LOSSES);

		/**
		 * The amount of tank losses incurred while satisfying demand.
		 */
		double tankLosses;
		final ICentralWaterHeater primaryWaterHeater = system.getPrimaryWaterHeater();
		final ICentralWaterHeater secondaryWaterHeater = system.getSecondaryWaterHeater();
		final ICentralWaterHeater immersionHeater = getTankHeater();

		if (primaryWaterHeater == null && secondaryWaterHeater == null && immersionHeater == null) {
			log.warn("Central hot water system has no heaters connected");
			return;
		}

		/*
		BEISDOC
		NAME: Water storage loss
		DESCRIPTION: Losses from the heating system's water store.
		TYPE: formula
		UNIT: W
		SAP: (51-56), Tables 2, 2a, 2b
                SAP_COMPLIANT: Yes
		BREDEM: 2.2B, 2.2C, Table 9
                BREDEM_COMPLIANT: Yes
		DEPS: tank-losses,storage-combi-storage-temperature-factor,cpsu-storage-temperature-factor
		ID: water-storage-loss
		CODSIEB
		*/
		if (system.getStore() == null) {
			tankLosses = 0;
			if (primaryWaterHeater instanceof IMainWaterHeater) {
				final IHeatSource heatSource = ((IMainWaterHeater) primaryWaterHeater).getHeatSource();
				tankLosses = heatSource.getContainedTankLosses(parameters);
			}
		} else {
			final double tankTemperatureFactor;

			/*
			 * This is the storage temperature factor from SAP table 2b. It's a bit of a hack, because
			 * in SAP it is essentially a bit arbitrary. My (tom's) judgement is that it's OK for it to be
			 * a hack, because it's not worth forcing an extra layer of delegation into the model.
			 */
			if (primaryWaterHeater instanceof IMainWaterHeater) {
				final IHeatSource heatSource = ((IMainWaterHeater) primaryWaterHeater).getHeatSource();
				tankTemperatureFactor = heatSource.getStorageTemperatureFactor(parameters, system.getStore(), system.isStoreInPrimaryCircuit());
			} else if (system.hasImmersionHeater()) {
				tankTemperatureFactor = constants.get(CylinderConstants.TEMPERATURE_FACTOR_BASIC);
			} else {
				// apply default factors (according to SAP table 2b)
				tankTemperatureFactor =
						HotWaterUtilities.getStorageTemperatureFactor(parameters, system.getStore(),
								system.isStoreInPrimaryCircuit(), system.isSeparatelyTimeControlled());
			}

			tankLosses = system.getStore().getStandingLosses(parameters, tankTemperatureFactor);
		}

		/**
		 * The amount of hot water energy required before run
		 */
		final double initialRemainingDemand = state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER);

		/**
		 * The amount of the total demand generated that was generated by non-solar systems
		 */
		double nonSolarGeneration = 0;

		final IWaterTank systemStore = system.getStore();
		final boolean systemStoreInPrimaryCircuit = system.isStoreInPrimaryCircuit();

		/*
		BEISDOC
		NAME: Central Direct Hot Water Fuel Energy Demand
		DESCRIPTION: From the central hot water system, calls the various heaters. Accumulates the overall amount of fuel used for providing hot water, including combi losses.
		TYPE: formula
		UNIT: W
		SAP: (64,216,217,219)
                SAP_COMPLIANT: Yes
		BREDEM: 2.5
                BREDEM_COMPLIANT: Yes
		DEPS: boiler-water-heating-efficiency,combi-losses,community-water-heating-energy-multipliers,solar-hot-water-output
		ID: central-direct-hot-water-fuel-demand
		CODSIEB
		*/
		if (hasSolarHeater) {
			system.getSolarWaterHeater().generateHotWaterAndPrimaryGains(parameters, state, systemStore,
					systemStoreInPrimaryCircuit, 0, distributionLossFactor, 1);
		}

		amountsGenerated[0] = 0;
		amountsGenerated[1] = 0;
		amountsGenerated[2] = 0;

		final double pipeworkLosses;

		if (primaryWaterHeater != null) {
			pipeworkLosses = primaryWaterHeater.causesPipeworkLosses() ?
					getPrimaryPipeworkLosses(
							parameters,
							system.isPrimaryPipeworkInsulated(),
							primaryWaterHeater.isCommunityHeating(),
							systemStore,
							solarPipeworkFactor
						)
					: 0;

			amountsGenerated[0] = primaryWaterHeater.generateHotWaterAndPrimaryGains(parameters, state, systemStore,
					systemStoreInPrimaryCircuit, pipeworkLosses, distributionLossFactor, 1);

			log.debug("primary generated {}", amountsGenerated[0]);
		} else if (secondaryWaterHeater != null) {
			pipeworkLosses = secondaryWaterHeater.causesPipeworkLosses() ?
					getPrimaryPipeworkLosses(
							parameters,
							system.isPrimaryPipeworkInsulated(),
							secondaryWaterHeater.isCommunityHeating(),
							systemStore,
							solarPipeworkFactor
						)
					: 0;

			amountsGenerated[1] =
						secondaryWaterHeater.generateHotWaterAndPrimaryGains(parameters, state, systemStore,
								systemStoreInPrimaryCircuit, pipeworkLosses, distributionLossFactor, 1);

			log.debug("secondary generated {}", amountsGenerated[1]);
		} else if (immersionHeater != null) {
			pipeworkLosses = 0;
			amountsGenerated[2] =
					immersionHeater.generateHotWaterAndPrimaryGains(parameters, state, systemStore,
								systemStoreInPrimaryCircuit, 0, distributionLossFactor, 1);

			log.debug("immersion generated {}", amountsGenerated[2]);
		} else {
			pipeworkLosses = 0;
		}

		StepRecorder.recordStep(EnergyCalculationStep.WaterHeating_PrimaryCircuitLoss_Monthly, pipeworkLosses);

		nonSolarGeneration = amountsGenerated[0] + amountsGenerated[1] + amountsGenerated[2];

		/**
		 * The amount of hot water energy required after run
		 */
		final double finalRemainingDemand = state.getUnsatisfiedDemand(EnergyType.DemandsHOT_WATER);

		/**
		 * The amount of hot water demand that this system has satisfied
		 */
		final double demandSatisfied = initialRemainingDemand - finalRemainingDemand;

		/*
		BEISDOC
		NAME: Central Hot Water Distribution Losses
		DESCRIPTION: The distribution losses from the central water heating system
		TYPE: formula
		UNIT: W
		SAP: (46)
                SAP_COMPLIANT: Yes
		BREDEM: 2.21
                BREDEM_COMPLIANT: Yes
		DEPS: distribution-loss-factor,usage-adjusted-water-volume,is-main-weater-heating
		ID: central-hot-water-distribution-losses
		CODSIEB
		*/
		/**
		 * The amount of distribution losses that were incurred whilst satisfying demand
		 */
		final double distributionLosses = demandSatisfied * distributionLossFactor;
		StepRecorder.recordStep(EnergyCalculationStep.WaterHeating_DistributionLoss, distributionLosses);

		log.debug("Distribution losses: {}, tank losses: {}", distributionLosses, tankLosses);

		/*
		BEISDOC
		NAME: Central System Hot Water Fuel Energy Demand
		DESCRIPTION: From the central hot water system, calls the various heaters. Calculates distribution, pipework and storage losses and accumulates fuel demand caused by those.
		TYPE: formula
		UNIT: W
		SAP: (64,216,217,219)
                SAP_COMPLIANT: Yes
		BREDEM: 2.5
                BREDEM_COMPLIANT: Yes
		DEPS: central-hot-water-distribution-losses,primary-pipework-losses,water-storage-loss
		ID: central-system-hot-water-fuel-demand
		CODSIEB
		*/
		/*
		 * Now we divvy up the system losses between the non-solar water heating elements:
		 */
		if (nonSolarGeneration > 0) {
			if (primaryWaterHeater != null) {
				primaryWaterHeater.generateSystemGains(parameters, state, systemStore,
						systemStoreInPrimaryCircuit,
						(amountsGenerated[0] / nonSolarGeneration) *
						/*
						BEISDOC
						NAME: Total central hot water losses
						DESCRIPTION: Storage losses added to distribution losses for a central hot water system.
						TYPE: formula
						UNIT: W
						SAP: (62)
						SAP_COMPLIANT: Yes
						BREDEM: 2.5A
						BREDEM_COMPLIANT: Yes
						DEPS: central-hot-water-distribution-losses,water-storage-loss
						NOTES: Combi losses and pipework losses are not included here. They are added on inside the various kinds of heat source.
						ID: total-central-hot-water-losses
						CODSIEB
						*/
						(tankLosses + distributionLosses)
						);
			}
			if (amountsGenerated[1] > 0) {
				secondaryWaterHeater.generateSystemGains(parameters, state, systemStore, systemStoreInPrimaryCircuit,
						(amountsGenerated[1] / nonSolarGeneration) * (tankLosses + distributionLosses));
			}
			if (amountsGenerated[2] > 0) {
				immersionHeater.generateSystemGains(parameters, state, systemStore, systemStoreInPrimaryCircuit,
						(amountsGenerated[2] / nonSolarGeneration) * (tankLosses + distributionLosses));
			}
		} else {
			state.increaseSupply(EnergyType.GainsHOT_WATER_SYSTEM_GAINS, distributionLosses+tankLosses);
		}

		state.increaseSupply(EnergyType.GainsHOT_WATER_USAGE_GAINS, demandSatisfied);
	}

	@Override
	public String toString() {
		return "Central Water Heating";
	}

	@Override
	public TransducerPhaseType getPhase() {
		//TODO check for dubious things here, and break the scheduling if there is a problem

		return TransducerPhaseType.HotWater;
	}

	/**
	 * Get standard primary pipework losses for different kinds of {@link IHeatSource} and {@link IWarmAirSystem}
	 * used for DHW.
	 *
	 * @param parameters
	 * @param primaryPipeworkIsInsulated
	 * @param isCommunityHeating
	 * @param store
     * @param solarCorrectionFactor
	 * @return
	 */
	protected final double getPrimaryPipeworkLosses(
			final IInternalParameters parameters,
			final boolean primaryPipeworkIsInsulated,
			final boolean isCommunityHeating,
			final IWaterTank store,
			final double solarCorrectionFactor) {

		/*
		BEISDOC
		NAME: Primary pipework insulated fraction
		DESCRIPTION: The fraction of the primary pipework which is insulated.
		TYPE: Lookup
		UNIT: Dimensionless
		SAP: Table 3
                SAP_COMPLIANT: Yes, but limited data
		BREDEM: Table 10
                BREDEM_COMPLIANT: Yes, but limited data
		NOTES: In the NHM we only implement the rows for uninsulated and fully insulated, and community heating is always fully insulated.
		ID: pipework-insulation-fraction
		CODSIEB
		*/
		final double pipeworkInsulatedFraction = (isCommunityHeating || primaryPipeworkIsInsulated) ? 1 : 0;

		final double hoursPerDayHot = hoursPerDayPrimaryHot(
				parameters,
				isCommunityHeating,
				store.isThermostatFitted()
		);

		final double ppCoefficent = parameters.getConstants().get(HeatingSystemConstants.PRIMARY_PIPEWORK_COEFFICIENT);
		final double insulatedMultiplier = parameters.getConstants().get(HeatingSystemConstants.PRIMARY_PIPEWORK_INSULATED_MULTIPLIER);
		final double uninsulatedMultiplier = parameters.getConstants().get(HeatingSystemConstants.PRIMARY_PIPEWORK_UNINSULATED_MULTIPLIER);
		final double ppConstant = parameters.getConstants().get(HeatingSystemConstants.PRIMARY_PIPEWORK_CONSTANT);

		/*
		BEISDOC
		NAME: Primary Pipework Losses
		DESCRIPTION: Losses due to the primary hot water pipework
		TYPE: formula
		UNIT: W
		SAP: (59), Table 3
                SAP_COMPLIANT: Yes
		BREDEM: 2.2D
                BREDEM_COMPLIANT: Yes
		DEPS: primary-pipework-coefficient, primary-pipework-insulated-multiplier, primary-pipework-uninsulated-multiplier, primary-pipework-constant, pipework-insulation-fraction, hours-per-day-primary-hot, solar-primary-pipework-correction
		ID: primary-pipework-losses
		CODSIEB
		*/
		return ppCoefficent * (
			(
				(
						(pipeworkInsulatedFraction * insulatedMultiplier) +
						((1 - pipeworkInsulatedFraction) * uninsulatedMultiplier)
				)
				* hoursPerDayHot
			)
			+ ppConstant
		) * solarCorrectionFactor;
	}

	protected double hoursPerDayPrimaryHot(
			final IInternalParameters parameters,
			final boolean isCommunityHeating,
			final boolean cylinderThermostat
			) {

		final boolean waterHeatingSeparatelyTimed = false;

		/*
		BEISDOC
		NAME: Hours Primary Hot
		DESCRIPTION: The number of hours per day the primary pipework is hot.
		TYPE: formula
		UNIT: Hours
		SAP: Table 3
                SAP_COMPLIANT: Yes
		BREDEM: Table 11
                BREDEM_COMPLIANT: Yes
		DEPS: month-of-year,hours-per-day-primary-hot-lookup
		NOTES: We assume that water heating is not separately timed by default.
		NOTES:
		ID: hours-per-day-primary-hot
		CODSIEB
		*/
		if (isCommunityHeating) {
			// Community heating always uses the lower value.
			return parameters.getConstants().get(HeatingSystemConstants.HOURS_PIPEWORK_HOT, 2);
		} else if (parameters.getClimate().isHeatingOn()) {
			if (!cylinderThermostat) {
				// Winter no cylinder thermostat
				return parameters.getConstants().get(HeatingSystemConstants.HOURS_PIPEWORK_HOT, 0);
			} else if (waterHeatingSeparatelyTimed) {
				// Winter separate water heater timing
				return parameters.getConstants().get(HeatingSystemConstants.HOURS_PIPEWORK_HOT, 2);
			} else {
				// Winter no separate water heating timing
				return parameters.getConstants().get(HeatingSystemConstants.HOURS_PIPEWORK_HOT, 1);
			}
		} else {
			// SUmmer value
			return parameters.getConstants().get(HeatingSystemConstants.HOURS_PIPEWORK_HOT, 2);
		}
	}
}
