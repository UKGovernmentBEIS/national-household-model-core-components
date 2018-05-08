package uk.org.cse.stockimport.hom.impl.steps.services;

import static uk.org.cse.stockimport.util.OptionalUtil.get;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.emf.technologies.*;
import uk.org.cse.nhm.hom.emf.technologies.boilers.IBoiler;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICPSU;
import uk.org.cse.nhm.hom.emf.technologies.boilers.ICombiBoiler;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.services.IWaterHeatingDTO;
import uk.org.cse.stockimport.domain.services.ImmersionHeaterType;
import uk.org.cse.stockimport.domain.services.WaterHeatingSystemType;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.services.heating.IHeatSourceBuilder;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * Construct hot water systems from {@link IWaterHeatingDTO}
 *
 * @author hinton
 * @since 1.0
 */
public class HotWaterBuildStep implements ISurveyCaseBuildStep {

    private static final double DEFAULT_CYLINDER_VOLUME = 110d;
    private static final double DEFAULT_INSULATION_THICKNESS = 25d;
    protected static final Logger log = LoggerFactory.getLogger(HotWaterBuildStep.class);
    /**
     * @since 1.0
     */
    public static final String IDENTIFIER = HotWaterBuildStep.class.getCanonicalName();
    protected static final ITechnologiesFactory T = ITechnologiesFactory.eINSTANCE;

    private IHeatSourceBuilder heatSourceBuilder;

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public Set<String> getDependencies() {
        return ImmutableSet.of(SpaceHeatingBuildStep.IDENTIFIER);
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

    @Override
    public void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider) {
        final IWaterHeatingDTO waterHeating = dtoProvider.requireOne(IWaterHeatingDTO.class);
        final IHouseCaseDTO house = dtoProvider.requireOne(IHouseCaseDTO.class);
        build(model, dtoProvider, waterHeating, house);
    }

    private void build(final SurveyCase model, final IHouseCaseSources<IBasicDTO> dtoProvider, final IWaterHeatingDTO dto, final IHouseCaseDTO house) {
        Preconditions.checkNotNull(dto, "Water heating DTO null for %s", dtoProvider.getAacode());
        Preconditions.checkNotNull(house, "House case DTO null for %s", dtoProvider.getAacode());

        final ITechnologyModel tech = model.getTechnologies();
        final ICentralWaterHeater newHeater;

        log.debug("Build hot water for {}", dtoProvider.getAacode());

        if (dto.isWithCentralHeating() || shouldAttachToMainHeating(dto.getWaterHeatingSystemType().orNull(), tech)) {
            newHeater = connectToMain(tech);
        } else {
            newHeater = null;
            installNewWaterHeater(model.getBasicAttributes().getBuildYear(), tech, dto, heatSourceBuilder);
        }

        if (newHeater != null) {
            log.debug("Attaching new central water heater to central hot water system");
            attachCentralWaterHeater(tech, newHeater);
        }

        installWaterTank(dto, tech);

        installImmersionHeater(dto, tech);

        final ICentralWaterSystem central = getCentralWaterSystem(tech, false);
        if (central != null) {
            ensureHeatingIfCentralHotWater(central, tech, dtoProvider);
            ensureWaterTankMatchesHeating(central, T);
            central.setPrimaryPipeworkInsulated(getPipeWorkInsulation(house));
            setWaterTankDetailsFromDTO(central.getStore(), dto);
        }

        if (dto.isSolarHotWaterPresent()) {
            installSolarHotWater(dto, tech);
        }

        if (dto.getHasElectricShower().isPresent() && dto.getHasElectricShower().get()) {
            tech.setShower(T.createElectricShower());
        } else {
            tech.setShower(T.createMixerShower());
        }
    }

    /**
     * If a central hot water system is present, ensures that it has at least
     * one of the following kinds of heating in order: 1. Existing primary 2.
     * Existing immersion heater 3. Connect up space heating as primary 4.
     * Install fallback immersion heater
     *
     * @param central
     * @param tech
     * @param dtoProvider
     */
    private void ensureHeatingIfCentralHotWater(final ICentralWaterSystem central, final ITechnologyModel tech, final IHouseCaseSources<IBasicDTO> dtoProvider) {
        final ICentralWaterHeater primary = central.getPrimaryWaterHeater();
        if (primary == null) {
            if (!hasImmersionHeater(central)) {
                final ICentralWaterHeater bodge = connectToMain(tech);
                if (bodge != null) {
                    log.warn("{} has central hot water, but does not have a heater - connecting to main", dtoProvider.getAacode());
                    attachCentralWaterHeater(tech, bodge);
                } else {
                    log.warn("{} their is no heater connected, no is there any suitable main system - adding an immersion heater as primary", dtoProvider.getAacode());
                    installImmersionHeater(dtoProvider.getAacode(), tech, false);
                }
            }
        }
    }

    private boolean hasImmersionHeater(final ICentralWaterSystem central) {
        return central.getStore() != null && central.getStore().getImmersionHeater() != null;
    }

    /**
     * Makes sure that heating systems which require tanks get a default tank
     * installed if it is missing.
     *
     * Additionally, heating systems which cannot have tanks will have the tank
     * removed if it is present.
     *
     * @param central
     * @param T
     */
    private void ensureWaterTankMatchesHeating(final ICentralWaterSystem central, final ITechnologiesFactory T) {
        final ICentralWaterHeater primary = central.getPrimaryWaterHeater();
        if (primary != null) {
            if (mustHaveTank(primary)) {
                if (central.getStore() == null) {
                    log.warn("Adding default hot water store because the primary water heater needs one.");
                    central.setStore(T.createWaterTank());
                }
            } else if (cannotHaveTank(primary)) {
                if (central.getStore() != null) {
                    log.warn("Removing hot water store because the primary water heater cannot have one.");
                    central.setStore(null);
                }
            }
        }
    }

    /**
     * The conversion document specifies that primary pipework is insulated if
     * the house was built after 1995.
     *
     * @assumption The CAR conversion document specifies that a 'post 1995'
     * dwelling should default to having insulated pipework. This is unclear as
     * construction dates are specified as ranges. We are using the actual
     * construction date, so this ambiguity should not affect us..
     *
     * @param house
     * @return Whether the primary pipework should be insulated for this house.
     * @since 1.0
     */
    public boolean getPipeWorkInsulation(final IHouseCaseDTO house) {
        try {
            return house.getBuildYear() > 1995;
        } catch (final NullPointerException ex) {
            return false;
        }
    }

    private void installSolarHotWater(final IWaterHeatingDTO dto, final ITechnologyModel tech) {
        final double solarStoreVolume = dto.getSolarStoreVolume();

        final ICentralWaterSystem centralWaterSystem = getCentralWaterSystemOrCreate(tech);

        final ISolarWaterHeater swh = T.createSolarWaterHeater();
        if (dto.isSolarStoreInCylinder() == false || centralWaterSystem.getStore() == null) {
            swh.setPreHeatTankVolume(solarStoreVolume);
        }

        // values from CHM.
        swh.setArea(3.0);
        swh.setUsefulAreaRatio(1d);
        swh.setZeroLossEfficiency(0.8);
        swh.setLinearHeatLossCoefficient(4d);
        swh.setPumpPhotovolatic(true);

        // pitch and orientation
        swh.setPitch(30d * Math.PI / 180d);
        swh.setOrientation(Math.PI / 2);

        centralWaterSystem.setSolarWaterHeater(swh);
    }

    private void installImmersionHeater(final IWaterHeatingDTO dto, final ITechnologyModel tech) {
        if (dto.getImmersionHeaterType().isPresent()) {
            installImmersionHeater(dto.getAacode(), tech, dto.getImmersionHeaterType().get().equals(ImmersionHeaterType.DUAL_COIL));
        }
    }

    private IImmersionHeater installImmersionHeater(final String aacode, final ITechnologyModel tech, final boolean isDualCoil) {
        final IImmersionHeater immersionHeater = T.createImmersionHeater();
        immersionHeater.setDualCoil(isDualCoil);
        attachImmersionHeater(aacode, tech, immersionHeater);
        return immersionHeater;
    }

    private void attachImmersionHeater(final String aacode, final ITechnologyModel tech, final IImmersionHeater immersionHeater) {
        final ICentralWaterSystem system = getCentralWaterSystemOrCreate(tech);
        IWaterTank store = system.getStore();
        if (store == null) {
            log.warn("{} has an immersion heater, but no storage tank. Install a storage tank.", aacode);
            system.setStore(T.createWaterTank());
            store = system.getStore();
        }
        store.setImmersionHeater(immersionHeater);
    }

    protected static void setWaterTankDetailsFromDTO(final IWaterTank tank, final IWaterHeatingDTO dto) {
        if (tank != null) {
            tank.setVolume(dto.getCylinderVolume().or(DEFAULT_CYLINDER_VOLUME));
            tank.setFactoryInsulation(dto.getCylinderFactoryInsulated().or(true));
            tank.setInsulation(dto.getCylinderInsulationThickness().or(DEFAULT_INSULATION_THICKNESS));
            tank.setThermostatFitted(dto.getCylinderThermostatPresent().or(true));

            if (dto.isSolarStoreInCylinder()) {
                tank.setSolarStorageVolume(dto.getSolarStoreVolume());
            }
        }
    }

    protected static void installWaterTank(final IWaterHeatingDTO dto, final ITechnologyModel tech) {
        if (dto.getCylinderVolume().isPresent() && dto.getCylinderVolume().get() > 0) {
            final ICentralWaterSystem system = getCentralWaterSystemOrCreate(tech);
            final ICentralWaterHeater primary = system.getPrimaryWaterHeater();
            if (primary != null && cannotHaveTank(primary)) {
                log.warn("{} has a heating type which cannot have a water tank, but also has tank details specified. Ignoring tank details.", dto.getAacode());
                return;
            } else {
                log.debug("Setting up tank in central water system");
                final IWaterTank tank = T.createWaterTank();

                system.setStore(tank);
            }
        }
    }

    /**
     * This is a utility method to test whether we should attach to an existing
     * heat source.
     *
     * This should happen if either (a) the DTO says we should or (b) it would
     * be ridiculous not to
     *
     * @param dto
     * @param tech
     * @return
     */
    protected static boolean shouldAttachToMainHeating(final WaterHeatingSystemType type, final ITechnologyModel tech) {
        if (type == null) {
            return false;
        }

        final boolean result;

        switch_statement:
        switch (type) {
            case MULTIPOINT:
            case SINGLEPOINT:
                result = false;
                break;
            case WARM_AIR:
                if (tech.getPrimarySpaceHeater() instanceof IWarmAirSystem
                        && ((IWarmAirSystem) tech.getPrimarySpaceHeater()).getFuelType() != FuelType.ELECTRICITY) {
                    result = true;
                } else {
                    result = false;
                }
                break;
            case BACK_BOILER:
                result = true;
                break;
            case STANDARD_BOILER:
                if (tech.getIndividualHeatSource() instanceof IBoiler) {
                    result = true;
                    break switch_statement;
                }
                result = false;
                break;
            case COMMUNITY:
            case COMMUNITY_CHP:
                // check for existing community heating system - you would never have two
                if (tech.getCommunityHeatSource() instanceof ICommunityHeatSource) {
                    result = true;
                    break switch_statement;
                }
                result = false;
                break;
            case AIR_SOURCE_HEAT_PUMP:
            case GROUND_SOURCE_HEAT_PUMP:
                // check for existing heat pump - again, you wouldn't have two
                if (tech.getIndividualHeatSource() instanceof IHeatPump) {
                    result = true;
                    break switch_statement;
                }
                result = false;
                break;
            default:
                result = false;
                break;
        }

        if (result == true) {
            log.warn("DTO says not to attach to main heating, but main heating and DHW type are the same, so am attaching to main heating anyway");
        }

        return result;
    }

    /**
     * Install a brand new water heater and possibly associated heat source in
     * accordance with the DTO
     *
     * @param tech
     * @param dto
     * @param heatSourceBuilder
     */
    protected static void installNewWaterHeater(final int constructionYear, final ITechnologyModel tech, final IWaterHeatingDTO dto, final IHeatSourceBuilder heatSourceBuilder) {
        final ICentralWaterHeater newCentralWaterHeater;

        if (dto.getWaterHeatingSystemType().isPresent()) {
            final WaterHeatingSystemType systemType = dto.getWaterHeatingSystemType().get();
            switch (systemType) {
                // these are system types which actually are standalone
                case MULTIPOINT:
                case SINGLEPOINT:
                    final IPointOfUseWaterHeater heater = T.createPointOfUseWaterHeater();
                    heater.setMultipoint(systemType == WaterHeatingSystemType.MULTIPOINT);
                    heater.setEfficiency(Efficiency.fromDouble(dto.getBasicEfficiency()));
                    heater.setFuelType(get(dto.getMainHeatingFuel(), "water heating fuel"));

                    tech.setSecondaryWaterHeater(heater);

                    newCentralWaterHeater = null;
                    break;

                case STANDARD_BOILER:
                case COMMUNITY:
                case COMMUNITY_CHP:
                case GROUND_SOURCE_HEAT_PUMP:
                case AIR_SOURCE_HEAT_PUMP:
                    final IHeatSource source = heatSourceBuilder.createHeatSource(constructionYear, dto);
                    if (source instanceof ICommunityHeatSource) {
                        tech.setCommunityHeatSource((ICommunityHeatSource) source);
                    } else if (source instanceof IIndividualHeatSource) {
                        tech.setIndividualHeatSource((IIndividualHeatSource) source);
                    }
                    final IMainWaterHeater mwh = T.createMainWaterHeater();
                    newCentralWaterHeater = mwh;
                    mwh.setHeatSource(source);
                    break;

                case BACK_BOILER:
                case WARM_AIR:
                default:
                    log.warn("DHW System Type {} cannot be a standalone system", dto.getWaterHeatingSystemType());
                    newCentralWaterHeater = null;
            }

            if (newCentralWaterHeater != null) {
                attachCentralWaterHeater(tech, newCentralWaterHeater);
            }
        }
    }

    protected static void attachCentralWaterHeater(final ITechnologyModel tech, final ICentralWaterHeater newHeater) {
        final ICentralWaterSystem system = getCentralWaterSystemOrCreate(tech);

        if (system.getPrimaryWaterHeater() != null) {
            system.setSecondaryWaterHeater(newHeater);
        } else {
            system.setPrimaryWaterHeater(newHeater);
        }
    }

    protected static ICentralWaterSystem getCentralWaterSystemOrCreate(final ITechnologyModel tech) {
        return getCentralWaterSystem(tech, true);
    }

    /**
     * Locate or create a central hot water system
     *
     * @param tech
     * @return
     */
    protected static ICentralWaterSystem getCentralWaterSystem(final ITechnologyModel tech, final boolean create) {
        if (tech.getCentralWaterSystem() != null) {
            return tech.getCentralWaterSystem();
        } else if (create) {
            log.debug("Creating new central water system");
            final ICentralWaterSystem created = T.createCentralWaterSystem();
            tech.setCentralWaterSystem(created);
            return created;
        } else {
            return null;
        }
    }

    /**
     * Connect a central water heating system to the main heating system, where
     * possible
     *
     * @param tech
     * @param waterHeatingSystemType
     * @return
     */
    protected static ICentralWaterHeater connectToMain(final ITechnologyModel tech) {
        ICentralWaterHeater heater = null;

        heater = connectToMainHeatSource(tech, IHeatSource.class);
        if (heater == null) {
            heater = connectToWarmAir(tech);
            if (heater == null) {
                heater = connectToBackBoiler(tech);
                if (heater == null) {
                    log.warn("No connectable main heating system found at all!");
                }
            }
        }

        return heater;
    }

    protected static ICentralWaterHeater connectToBackBoiler(final ITechnologyModel tech) {
        if (tech.getSecondarySpaceHeater() instanceof IBackBoiler) {
            final IBackBoiler backBoiler = (IBackBoiler) tech.getSecondarySpaceHeater();
            final IMainWaterHeater result = T.createMainWaterHeater();
            result.setHeatSource(backBoiler);
            return result;
        } else {
            log.warn("Cannot connect to backboiler - can't find one");
            return null;
        }
    }

    protected static ICentralWaterHeater connectToWarmAir(final ITechnologyModel tech) {
        if (tech.getPrimarySpaceHeater() instanceof IWarmAirSystem) {
            final IWarmAirSystem primarySpaceHeater = (IWarmAirSystem) tech.getPrimarySpaceHeater();

            if (primarySpaceHeater.getFuelType() == FuelType.ELECTRICITY) {
                log.warn("Cannot connect to warm air system - electric warm air systems cannot provide hot water");
                return null;
            }

            final IWarmAirCirculator circulator = T.createWarmAirCirculator();

            circulator.setWarmAirSystem(primarySpaceHeater);

            return circulator;
        } else {
            log.warn("Cannot connect to warm air system - can't find one");
            return null;
        }
    }

    protected static ICentralWaterHeater connectToMainHeatSource(final ITechnologyModel tech, final Class<? extends IHeatSource> heatSourceClass) {
        IHeatSource selectedHeatSource = null;

        if (heatSourceClass.isInstance(tech.getIndividualHeatSource())) {
            selectedHeatSource = tech.getIndividualHeatSource();
        } else if (heatSourceClass.isInstance(tech.getCommunityHeatSource())) {
            selectedHeatSource = tech.getCommunityHeatSource();
        } else {
            if (heatSourceClass.isInstance(tech.getSecondarySpaceHeater())) {
                selectedHeatSource = (IHeatSource) tech.getSecondarySpaceHeater();
            }
        }

        if (selectedHeatSource == null) {
            log.warn("Cannot find heat source of class {}, looking for something else", heatSourceClass);
            if (tech.getIndividualHeatSource() != null) {
                selectedHeatSource = tech.getIndividualHeatSource();
            } else if (tech.getCommunityHeatSource() != null) {
                selectedHeatSource = tech.getCommunityHeatSource();
            } else if (tech.getSecondarySpaceHeater() instanceof IHeatSource) {
                selectedHeatSource = (IHeatSource) tech.getSecondarySpaceHeater();
            }
        }

        if (selectedHeatSource == null) {
            log.warn("Cannot connect to main heat source - can't find one");
            return null;
        } else {
            final IMainWaterHeater result = T.createMainWaterHeater();
            result.setHeatSource(selectedHeatSource);
            return result;
        }
    }

    private static Class<?>[] tanklessBoilers = new Class<?>[]{ICombiBoiler.class, ICPSU.class};

    /**
     * Return true if the heating type cannot have a tank. This is
     * !mustHaveTank, unless the DHW is from a community heat source.
     *
     * @param central
     * @return
     * @since 1.0
     */
    public static boolean cannotHaveTank(final ICentralWaterHeater central) {
        if (central instanceof IMainWaterHeater) {
            if (((IMainWaterHeater) central).getHeatSource() instanceof ICommunityHeatSource) {
                return false;
            }
        }
        return !mustHaveTank(central);
    }

    /**
     * Returns true if the heating type must have a tank
     *
     * @param central
     * @return
     * @since 1.0
     */
    public static boolean mustHaveTank(final ICentralWaterHeater central) {
        if (central instanceof IMainWaterHeater) {
            final Class<?> clazz = ((IMainWaterHeater) central).getHeatSource().getClass();
            for (final Class<?> tanklessType : tanklessBoilers) {
                if (tanklessType.isAssignableFrom(clazz)) {
                    return false;
                }
            }
            return true;
        } else if (central instanceof IWarmAirCirculator) {
            return true;
        } else {
            throw new IllegalArgumentException("Type of heater should not exist as the primary water heater: " + central.getClass());
        }
    }
}
