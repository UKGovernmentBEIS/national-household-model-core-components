package uk.org.cse.stockimport.hom.impl.steps.services.heating;

import static uk.org.cse.stockimport.util.OptionalUtil.get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatPumpSourceType;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityCHP;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IHeatPump;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.hom.emf.technologies.boilers.EfficiencySourceType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoilersFactory;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IInstantaneousCombiBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IStorageCombiBoiler;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.stockimport.domain.services.IHeatingDTO;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.IWaterHeatingDTO;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;
import uk.org.cse.stockimport.domain.services.WaterHeatingSystemType;
import uk.org.cse.stockimport.hom.impl.steps.services.SpaceHeatingBuildStep;
import uk.org.cse.stockimport.sedbuk.tables.BoilerType;

/**
 * Helper for {@link SpaceHeatingBuildStep} which makes boilers.
 * 
 * I have made many of the methods in this static, but that is only intended to indicate that they are
 * 	 (a) pure functions, which don't involve keeping state in the builder and
 *   (b) available to test without instantiating this class. 
 *   
 * I am not entirely sure that this is a good pattern to follow
 * 
 * @author hinton
 * @since 1.0
 */
public class HeatSourceBuilder implements IHeatSourceBuilder {
	private static final Logger log = LoggerFactory.getLogger(HeatSourceBuilder.class);
	private static final IBoilersFactory B = IBoilersFactory.eINSTANCE;
	private static final ITechnologiesFactory T = ITechnologiesFactory.eINSTANCE;
	/**
	 * This is the CHP fraction for when CHP is not specified
	 */
	private static final Double DEFAULT_CHP_FRACTION = 0.35;
	/**
	 * this is whether charging is usage based if not specified.
	 */
	private static final boolean DEFAULT_USAGE_BASED = false;
	
	/**
	 * This is where the actual logic lives. I have made it static because it's stateless, aside from responsiveness
	 * lookups.
	 * 
     * @assumption As per CHM, CPSUs are treated as standard boilers.
     * @assumption As per CHM, no keep hot facility is present.
     * @assumption If a boiler is present, but flue type was not specified, assume balanced flue.
     * @assumption As per the CHM, the circulating pump of boilers with a pump is outside.
     * @assumption Boilers are not weather compensated.
     * @assumption Boilers are not condensing unless we found a Sedbuk row specifying them as such.
     * @assumption Boiler responsiveness set based on CHM table rather than SAP table.
     *
	 * @param model
	 * @param dtoProvider
	 * @param dto
	 * @param responsiveness
	 * @return
	 */
	private static IBoiler createBoiler(final FuelType fuelType, final IHeatingDTO dto, final BoilerType boilerType,
			final double storageVolume, final double solarStorageVolume, double storeInsulationThickness, final boolean condensing) {
		final IBoiler boiler;
		
		switch (boilerType) {
		case CPSU:
			log.debug("Creating CPSU as standard boiler, as per CHM");
		case REGULAR:
			log.debug("Creating standard boiler");
			boiler = B.createBoiler();
			break;
		case INSTANT_COMBI:
			final IInstantaneousCombiBoiler instant = B.createInstantaneousCombiBoiler();
			boiler = instant;
			instant.setKeepHotFacility(null);
			break;
		case STORAGE_COMBI:
			final IStorageCombiBoiler storage = B.createStorageCombiBoiler();
			boiler = storage;
			
			log.debug("Setting up tank in storage combi boiler");
			final IWaterTank waterTank = T.createWaterTank();
			waterTank.setFactoryInsulation(true);
			waterTank.setThermostatFitted(true);
			waterTank.setVolume(storageVolume);
			waterTank.setSolarStorageVolume(solarStorageVolume);
			if (storeInsulationThickness == 0) storeInsulationThickness = 50d;
			waterTank.setInsulation(storeInsulationThickness);
			
			// this is a bit messy - apologies
			storage.setStore(waterTank);
			
			// tank here
			break;
		case UNKNOWN:
		default:
			log.error("Unknown boiler type {} - returning null", boilerType);
			boiler = null;
		}
		
		if (boiler != null) {
			// set basic boiler attributes from dto
			boiler.setFuel(fuelType);
			// efficiencies etc.
			if (boiler.getFuel() == FuelType.ELECTRICITY) {
				boiler.setSummerEfficiency(Efficiency.ONE);
				boiler.setWinterEfficiency(Efficiency.ONE);
				boiler.setFlueType(FlueType.NOT_APPLICABLE);
			} else {
				boiler.setSummerEfficiency(Efficiency.fromDouble(dto.getSummerEfficiency().or(dto.getBasicEfficiency())));
				boiler.setWinterEfficiency(Efficiency.fromDouble(dto.getWinterEfficiency().or(dto.getBasicEfficiency())));
				
				boiler.setFlueType(dto.getFlueType().or(FlueType.BALANCED_FLUE));
			}
			boiler.setPumpInHeatedSpace(false);
			boiler.setWeatherCompensated(false);
			boiler.setCondensing(condensing);
			boiler.setEfficiencySource(EfficiencySourceType.SAP_DEFAULT);
		}
		
		return boiler;
	}
	
	@Override
	public IHeatSource createHeatSource(final int constructionYear, final ISpaceHeatingDTO dto) {
		final IHeatSource result;
		switch (dto.getSpaceHeatingSystemType().getBasicType()) {
		case BOILER:
			result = createBoiler(dto.getMainHeatingFuel(), dto,
					dto.getSpaceHeatingSystemType().getBoilerType(),
					dto.getStorageCombiCylinderVolume().or(0d),
					dto.getStorageCombiSolarVolume().or(0d),
					dto.getStorageCombiCylinderInsulationThickness().or(50d),
					dto.getCondensing().or(false));
			break;
		case COMMUNITY:
			result = createCommunityHeatSource(dto.getMainHeatingFuel(), dto, dto.getSpaceHeatingSystemType() == SpaceHeatingSystemType.COMMUNITY_HEATING_WITH_CHP);
			break;
		case HEAT_PUMP:
			result = createHeatPump(dto.getMainHeatingFuel(), dto, dto.getSpaceHeatingSystemType() == SpaceHeatingSystemType.AIR_SOURCE_HEAT_PUMP);
			break;
		default:
			log.error("I cannot create heat sources for space heating system type {}", dto.getSpaceHeatingSystemType());
			return null;
		}
		
		if (result != null) {
			result.setInstallationYear(dto.getInstallationYear().or(constructionYear));
		}
		
		return result;
	}

	private static ICommunityHeatSource createCommunityHeatSource(final FuelType fuelType, final IHeatingDTO dto, final boolean isCHP) {
		final ICommunityHeatSource hs;
		
		if (isCHP) {
			final ICommunityCHP chp = T.createCommunityCHP();
			hs = chp;
			// EHS default here is 0.35 I think
			chp.setElectricalEfficiency(Efficiency.fromDouble(dto.getChpFraction().or(DEFAULT_CHP_FRACTION)));
		} else {
			hs = T.createCommunityHeatSource();
		}
		
		hs.setFuel(fuelType);
		hs.setHeatEfficiency(Efficiency.fromDouble(dto.getBasicEfficiency()));
		hs.setChargingUsageBased(dto.getCommunityChargingUsageBased().or(DEFAULT_USAGE_BASED));
		
		return hs;
	}

    /**
     * @assumption Heat pumps are not weather compensated
     * @assumption Non-electric heat pump with no flue specified has a balanced flue.
     * @assumption Heat pumps do not have auxiliary backup present
     */
	private static IHeatPump createHeatPump(final FuelType fuel, final IHeatingDTO dto, final boolean isAirSource) {
		final IHeatPump pump = T.createHeatPump();

		// problem! heat pump have a responsiveness set by the basic SAP table, dependent on emitter type.
		pump.setCoefficientOfPerformance(Efficiency.fromDouble(dto.getBasicEfficiency()));
		pump.setWeatherCompensated(false);
		pump.setFuel(fuel);
		pump.setFlueType(dto.getFlueType().or(pump.getFuel() == FuelType.ELECTRICITY ? FlueType.NOT_APPLICABLE : FlueType.BALANCED_FLUE));
		pump.setAuxiliaryPresent(false);
		
		if (isAirSource) {
			pump.setSourceType(HeatPumpSourceType.AIR);
		} else {
			pump.setSourceType(HeatPumpSourceType.GROUND);
		}
		
		return pump;
	}

	@Override
	public IHeatSource createHeatSource(final int constructionYear, final IWaterHeatingDTO dto) {
		final IHeatSource heatSource;
		
		switch (dto.getWaterHeatingSystemType().or(WaterHeatingSystemType.STANDARD_BOILER)) {
		case STANDARD_BOILER:
			heatSource = createBoiler(dto, BoilerType.REGULAR);
			break;
		case COMMUNITY_CHP:
			heatSource =createCommunityHeatSource(get(dto.getMainHeatingFuel(), "water heating fuel"), dto, true);
			break;
		case COMMUNITY:
			heatSource = createCommunityHeatSource(get(dto.getMainHeatingFuel(), "water heating fuel"), dto, false);
			break;
		case AIR_SOURCE_HEAT_PUMP:
			heatSource = createHeatPump(get(dto.getMainHeatingFuel(), "water heating fuel"), dto, true);
			break;
		case GROUND_SOURCE_HEAT_PUMP:
			heatSource = createHeatPump(get(dto.getMainHeatingFuel(), "water heating fuel"), dto, false);
			break;
		default:
			return null;
		}
		
		if (heatSource != null) {
			heatSource.setInstallationYear(dto.getInstallationYear().or(constructionYear));
		} else {
			log.warn("{} - could not construct heat source", dto.getAacode());
		}
		
		return heatSource;
	}

	private static IHeatSource createBoiler(final IWaterHeatingDTO dto, final BoilerType regular) {
		return createBoiler(get(dto.getMainHeatingFuel(), "water heating fuel"), dto, regular, 0, 0, 0, false);
	}
}
