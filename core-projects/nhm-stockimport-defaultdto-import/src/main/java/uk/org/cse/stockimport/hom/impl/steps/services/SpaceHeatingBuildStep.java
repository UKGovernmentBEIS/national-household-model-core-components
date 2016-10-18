package uk.org.cse.stockimport.hom.impl.steps.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.EmitterType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ICentralHeatingSystem;
import uk.org.cse.nhm.hom.emf.technologies.ICommunityHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IIndividualHeatSource;
import uk.org.cse.nhm.hom.emf.technologies.IRoomHeater;
import uk.org.cse.nhm.hom.emf.technologies.IStorageHeater;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.BasicAttributesBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.StructureInitializingBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.IHeatSourceBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.IRoomHeaterBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.ISecondaryHeatingSystemBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.IStorageHeaterBuilder;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.IWarmAirSystemBuilder;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * Build step which takes an {@link ISpaceHeatingDTO} and creates space heating systems from it.
 * @author hinton
 * @since 1.0
 */
public class SpaceHeatingBuildStep implements ISurveyCaseBuildStep {
	private static final Logger log = LoggerFactory.getLogger(SpaceHeatingBuildStep.class);
	
	static final String IDENTIFIER = SpaceHeatingBuildStep.class.getCanonicalName();
	
	private IHeatSourceBuilder heatSourceBuilder;
	private IStorageHeaterBuilder storageHeaterBuilder;
	private IRoomHeaterBuilder roomHeaterBuilder;
	private IWarmAirSystemBuilder warmAirBuilder;
	private ISecondaryHeatingSystemBuilder secondaryBuilder;
	
	/**
	 * This is a representation of SAP table 11, as understood by the CHM spreadsheet
	 */
	private final Map<SpaceHeatingSystemType, Double> secondaryHeatingProportion = 
			ImmutableMap.<SpaceHeatingSystemType, Double>builder()
				.put(SpaceHeatingSystemType.STANDARD, 0.1)
				.put(SpaceHeatingSystemType.COMBI, 0.1)
				.put(SpaceHeatingSystemType.STORAGE_COMBI, 0.1)
				.put(SpaceHeatingSystemType.BACK_BOILER, 0.1)
				.put(SpaceHeatingSystemType.BACK_BOILER_NO_CENTRAL_HEATING, 0.1)
				.put(SpaceHeatingSystemType.STORAGE_HEATER, 0.1) // unless main heating heater flue type is something else.
				.put(SpaceHeatingSystemType.ROOM_HEATER, 0.2)
				.put(SpaceHeatingSystemType.WARM_AIR, 0.1)
				.put(SpaceHeatingSystemType.COMMUNITY_HEATING_WITH_CHP, 0.1)
				.put(SpaceHeatingSystemType.COMMUNITY_HEATING_WITHOUT_CHP, 0.1)
				.put(SpaceHeatingSystemType.GROUND_SOURCE_HEAT_PUMP, 0.1)
				.put(SpaceHeatingSystemType.AIR_SOURCE_HEAT_PUMP, 0.1)
				.put(SpaceHeatingSystemType.CPSU, 0.1)
				.put(SpaceHeatingSystemType.MISSING, 1.0)
			.build();
	
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	@Override
	public Set<String> getDependencies() {
		return ImmutableSet.of(StructureInitializingBuildStep.IDENTIFIER, BasicAttributesBuildStep.IDENTIFIER);
	}
	
	/**
     * @since 1.0
     */
    public IStorageHeaterBuilder getStorageHeaterBuilder() {
		return storageHeaterBuilder;
	}

    /**
     * @since 1.0
     */
    public void setStorageHeaterBuilder(final IStorageHeaterBuilder storageHeaterBuilder) {
		this.storageHeaterBuilder = storageHeaterBuilder;
	}
	
    /**
     * @since 1.0
     */
    public IRoomHeaterBuilder getRoomHeaterBuilder() {
		return roomHeaterBuilder;
	}

    /**
     * @since 1.0
     */
    public void setRoomHeaterBuilder(final IRoomHeaterBuilder roomHeaterBuilder) {
		this.roomHeaterBuilder = roomHeaterBuilder;
	}

	
    /**
     * @since 1.0
     */
    public IHeatSourceBuilder getHeatSourceBuilder() {
		return heatSourceBuilder;
	}

    /**
     * @since 1.0
     */
    public void setHeatSourceBuilder(final IHeatSourceBuilder heatSourceBuilder) {
		this.heatSourceBuilder = heatSourceBuilder;
	}

    /**
     * @since 1.0
     */
    public IWarmAirSystemBuilder getWarmAirBuilder() {
		return warmAirBuilder;
	}
	
    /**
     * @since 1.0
     */
    public ISecondaryHeatingSystemBuilder getSecondaryBuilder() {
		return secondaryBuilder;
	}

    /**
     * @since 1.0
     */
    public void setSecondaryBuilder(final ISecondaryHeatingSystemBuilder secondaryBuilder) {
		this.secondaryBuilder = secondaryBuilder;
	}

    /**
     * @since 1.0
     */
    public void setWarmAirBuilder(final IWarmAirSystemBuilder warmAirBuilder) {
		this.warmAirBuilder = warmAirBuilder;
	}

	@Override
	public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
		final List<ISpaceHeatingDTO> dtos = dtoProvider.getAll(ISpaceHeatingDTO.class);
		log.debug("Building {} space heating records for {}", dtos.size(), dtoProvider.getAacode());
		
		for (final ISpaceHeatingDTO dto : dtos) {
			build(model, dtoProvider, dto);
		}
	}

	private void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider, final ISpaceHeatingDTO dto) {
		final ITechnologyModel tech = model.getTechnologies();
		
		
		
		if (dto.getSpaceHeatingSystemType() != SpaceHeatingSystemType.MISSING) {
			final IRoomHeater roomHeater;
			switch (dto.getSpaceHeatingSystemType().getBasicType()) {
			case BOILER:
			case HEAT_PUMP:
			case COMMUNITY:
				final IHeatSource heatSource = heatSourceBuilder.createHeatSource(model.getBuildYear(), dto);
				if (heatSource != null) {
					final ICentralHeatingSystem central = getCentralHeatingSystem(tech);
					if (central.getHeatSource() != null) {
						log.warn("{} already has a central heating system with a boiler, which I am replacing", dtoProvider.getAacode());
					}
					if (heatSource instanceof ICommunityHeatSource) {
						fixGasGridIncompatibility(model, (ICommunityHeatSource) heatSource);
						tech.setCommunityHeatSource((ICommunityHeatSource) heatSource);
					} else if (heatSource instanceof IIndividualHeatSource) {
						tech.setIndividualHeatSource((IIndividualHeatSource) heatSource);
					}
					central.setHeatSource(heatSource);
				} else {
					log.error("Heat source builder returned null for {}", dtoProvider.getAacode());
				}
				break;
			case BACK_BOILER:
			case ROOM_HEATER:
				roomHeater = roomHeaterBuilder.buildRoomHeater(model.getBuildYear(), dto);
				if (roomHeater != null) {
					tech.setSecondarySpaceHeater(roomHeater);
					if (roomHeater instanceof IHeatSource && dto.getSpaceHeatingSystemType() != SpaceHeatingSystemType.BACK_BOILER_NO_CENTRAL_HEATING) {
						final ICentralHeatingSystem central = getCentralHeatingSystem(tech);
						if (central.getHeatSource() != null) {
							log.warn("{} already has a central heating system with a boiler, which I am replacing", dtoProvider.getAacode());
						}
						central.setHeatSource((IHeatSource) roomHeater);
					}
				} else {
					log.error("Room heater builder returned null for {}", dtoProvider.getAacode());
				}
				break;
			case STORAGE_HEATER:
				final IStorageHeater heater = storageHeaterBuilder.buildStorageHeater(dto);
				if (heater != null) {
					tech.setPrimarySpaceHeater(heater);
				} else {
					log.error("Storage heater builder returned null for {}", dtoProvider.getAacode());
				}
				break;
			case WARM_AIR_SYSTEM:
				final IWarmAirSystem was = warmAirBuilder.buildWarmAirSystem(dto);
				if (was != null) {
					tech.setPrimarySpaceHeater(was);
				} else {
					log.error("Warm air system builder returned null for {}", dtoProvider.getAacode());
				}
				break;
			default:
				break;
			}
		
		} else {
			log.warn("No space heating system type in DTO for {}", dtoProvider.getAacode());
		}
		// heating system controls
		// if there is a central heating system, stick some controls on it
		final ICentralHeatingSystem system = getCentralHeatingSystem(tech, false);
		if (system != null) system.getControls().addAll(dto.getHeatingSystemControlTypes());

		final IRoomHeater secondaryHeater = secondaryBuilder.createSecondaryHeatingSystem(dto.getSecondaryHeatingSystemType());
		fixSecondaryHeatingOffGasGrid(secondaryHeater, model.getStructure().isOnGasGrid());
		
		// deal with possible collision between secondary heater and room heater
		installSecondaryHeater(dtoProvider.getAacode(), tech, secondaryHeater);
				
		ensureHeatingSystemIsPresent(tech);
	}

	/**
	 * A final fallback which will install an electric room heater if there are no heating systems
	 *  
	 * @param tech
	 */
	private void ensureHeatingSystemIsPresent(final ITechnologyModel tech) {
		if (tech.getPrimarySpaceHeater() == null && tech.getSecondarySpaceHeater() == null) {
			log.warn("No primary or secondary space heating system was installed. Falling back to electric room heaters.");
			final IRoomHeater heater = roomHeaterBuilder.buildDefaultElectricRoomHeater();
			tech.setSecondarySpaceHeater(heater);
		}
	}

	/**
	 * Install the given secondary heater into the given technology model.
	 * 
	 * If there is already a secondary heater which has the same fuel type, copy the responsiveness & efficiency out of secondaryHeater.
	 * 
	 * @param aacode
	 * @param tech
	 * @param secondaryHeater
	 * @issue NHM-444
	 */
	protected void installSecondaryHeater(final String aacode,
			final ITechnologyModel tech,
			final IRoomHeater secondaryHeater) {
		if (secondaryHeater != null && tech.getSecondarySpaceHeater() != null) {
			// peculiar situation
			final IRoomHeater roomHeater = tech.getSecondarySpaceHeater();
			if (secondaryHeater.getFuel() != roomHeater.getFuel()) {
				log.warn("{} back boiler fuel : {}, secondary heater fuel: {} - throwing away secondary heater.", 
						new Object[] {aacode, roomHeater.getFuel(),
							secondaryHeater.getFuel()});
			} else {
				roomHeater.setEfficiency(secondaryHeater.getEfficiency());
				roomHeater.setResponsiveness(secondaryHeater.getResponsiveness());
			}
		} else if (secondaryHeater != null) {
			tech.setSecondarySpaceHeater(secondaryHeater);
		}
	}

	/**
	 * We set secondary heating system based on 'other heating present' and
	 * 'other heating system type' as specified by the CAR conversion document.
	 * However, in some cases this results in us installing a main gas system
	 * when the dwelling is not on the gas grid.
	 * 
	 * @assumption Secondary heating systems which use mains gas, but are not on
	 *             the gas grid, should use bulk lpg instead.
	 * 
	 * @param secondaryHeater
	 * @param onGasGrid
	 */
	void fixSecondaryHeatingOffGasGrid(final IRoomHeater secondaryHeater, final boolean onGasGrid) {
		if (!onGasGrid && secondaryHeater != null && secondaryHeater.getFuel() == FuelType.MAINS_GAS) {
			secondaryHeater.setFuel(FuelType.BULK_LPG);
		}
	}

	/**
	 * We have no information on what fuel type community heating systems are
	 * using. The CAR conversion document specifies that they should be assumed
	 * to use mains gas. However, in some cases these dwellings are not on the
	 * gas grid. This method resolves this situation based on morphology type.
	 * 
	 * @assumption  If Community heating is present in an off-gasgrid Urban or TownAndFringe dwelling, we assume that onGasGrid was answered
	 *             incorrectly in the survey and set it to true.
	 * 
	 * @assumption  If Community heating is present in an off-gasgrid rural (Village or Hamlets and Isolated
	 *             Dwellings) dwelling, it is assumed to use wood biomass.
	 * 
	 * @param model
	 * @param heatSource
	 */
	void fixGasGridIncompatibility(final SurveyCase model, final ICommunityHeatSource heatSource) {
		model.getBasicAttributes();
		final StructureModel structure = model.getStructure();
		if (!structure.isOnGasGrid() && heatSource.getFuel() == FuelType.MAINS_GAS) {
			switch (model.getBasicAttributes().getMorphologyType()) {

			case TownAndFringe:
			case Urban:
				structure.setOnGasGrid(true);
				break;
			case HamletsAndIsolatedDwellings:
			case Village:
				heatSource.setFuel(FuelType.BIOMASS_WOOD);
				break;
			default:
				throw new IllegalArgumentException("Unknown morphology type " + model.getBasicAttributes().getMorphologyType());
			}
		}
	}

	/**
     * @since 1.0
     */
    public static ICentralHeatingSystem getCentralHeatingSystem(final ITechnologyModel tech) {
		return getCentralHeatingSystem(tech, true);
	}
	
	/**
	 * Get a central heating system from the technology model, or create one and add it if there isn't one.
	 * 
	 * @param tech
	 * @return a central heating system
	 * @since 1.0
	 */
	public static ICentralHeatingSystem getCentralHeatingSystem(final ITechnologyModel tech, final boolean createIfAbsent) {
		if (tech.getPrimarySpaceHeater() instanceof ICentralHeatingSystem) {
			return (ICentralHeatingSystem) tech.getPrimarySpaceHeater();
		} else if (tech.getPrimarySpaceHeater() == null) {
			if (createIfAbsent) {
				final ICentralHeatingSystem create = ITechnologiesFactory.eINSTANCE.createCentralHeatingSystem();
				
				create.setEmitterType(EmitterType.RADIATORS);
				
				tech.setPrimarySpaceHeater(create);
				
				return create;
			} else {
				return null;
			}
		} else {
			log.warn("{} is not a central heating system", tech.getPrimarySpaceHeater().eClass().getName());
			return null;
		}
	}
}
