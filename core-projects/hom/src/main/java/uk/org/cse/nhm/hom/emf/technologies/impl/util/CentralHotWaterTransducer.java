package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.EnergyTransducer;
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
		final double primaryPipeworkFactor = 
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
		BREDEM: 2.2B, 2.2C, Table 9
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
			
			tankLosses = system.getStore().getStandingLosses(parameters) * tankTemperatureFactor;
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
		BREDEM: 2.5
		DEPS: boiler-water-heating-efficiency,combi-losses,community-water-heating-energy-multipliers,solar-hot-water-output
		ID: central-direct-hot-water-fuel-demand
		CODSIEB
		*/
		if (hasSolarHeater) {
			system.getSolarWaterHeater().generateHotWaterAndPrimaryGains(parameters, state, systemStore, 
					systemStoreInPrimaryCircuit, primaryPipeworkFactor, distributionLossFactor, 1);
		}
		
		amountsGenerated[0] = 0;
		amountsGenerated[1] = 0;
		amountsGenerated[2] = 0;
		
		if (primaryWaterHeater != null) {
			amountsGenerated[0] = primaryWaterHeater.generateHotWaterAndPrimaryGains(parameters, state, systemStore, 
					systemStoreInPrimaryCircuit, primaryPipeworkFactor, distributionLossFactor, 1);
			
			log.debug("primary generated {}", amountsGenerated[0]);
		} else if (secondaryWaterHeater != null) {
			amountsGenerated[1] =
						secondaryWaterHeater.generateHotWaterAndPrimaryGains(parameters, state, systemStore, 
								systemStoreInPrimaryCircuit, primaryPipeworkFactor, distributionLossFactor, 1);
			
			log.debug("secondary generated {}", amountsGenerated[1]);
		} else if (immersionHeater != null) {
			amountsGenerated[2] =
					immersionHeater.generateHotWaterAndPrimaryGains(parameters, state, systemStore, 
								systemStoreInPrimaryCircuit, primaryPipeworkFactor, distributionLossFactor, 1);

			log.debug("immersion generated {}", amountsGenerated[2]);
		}
		
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
		BREDEM: 2.21
		DEPS: distribution-loss-factor,usage-adjusted-water-volume,is-main-weater-heating
		ID: central-hot-water-distribution-losses
		CODSIEB
		*/
		/**
		 * The amount of distribution losses that were incurred whilst satisfying demand
		 */
		final double distributionLosses = demandSatisfied * distributionLossFactor;
		
		log.debug("Distribution losses: {}, tank losses: {}", distributionLosses, tankLosses);
		
		/*
		BEISDOC
		NAME: Central System Hot Water Fuel Energy Demand
		DESCRIPTION: From the central hot water system, calls the various heaters. Calculates distribution, pipework and storage losses and accumulates fuel demand caused by those.
		TYPE: formula
		UNIT: W
		SAP: (64,216,217,219)
		BREDEM: 2.5
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
						BREDEM: 2.5A
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
}
