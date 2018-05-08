package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static uk.org.cse.nhm.ehcs10.physical.types.Enum1282.Yes;
import static uk.org.cse.nhm.ehcs10.physical.types.Enum1776.*;
import static uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType.DELAYED_START_THERMOSTAT;
import static uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType.PROGRAMMER;
import static uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType.ROOM_THERMOSTAT;
import static uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE;
import static uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL;
import static uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType.CONVECTOR;
import static uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType.FAN;
import static uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType.OLD_LARGE_VOLUME;
import static uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType.ELECTRIC_ROOM_HEATERS;
import static uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType.GAS_COAL_EFFECT_FIRE;
import static uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType.GAS_FIRE;
import static uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType.GAS_FIRE_FLUELESS;
import static uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType.GAS_FIRE_OPEN_FLUE;
import static uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType.OPEN_FIRE;
import static uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType.lookupHeatingTypeForBoiler;
import static uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode.ELECTRIC_STORAGE_MODERN_SLIMLINE_CONVECTOR;
import static uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode.ELECTRIC_STORAGE_MODERN_SLIMLINE_WITH_FAN;
import static uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode.ELECTRIC_STORAGE_OLD_LARGE_VOLUME;
import static uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode.SOLID_BACK_BOILER_CLOSED_FIRE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.boilermatcher.lucene.IBoilerTableEntry;
import uk.org.cse.boilermatcher.lucene.ISedbukIndex;
import uk.org.cse.boilermatcher.lucene.SedbukFix;
import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.physical.ServicesEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.ServicesEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1713;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1776;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1777;
import uk.org.cse.nhm.energycalculator.api.types.ElectricityTariffType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;
import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.domain.services.ISpaceHeatingDTO;
import uk.org.cse.stockimport.domain.services.SecondaryHeatingSystemType;
import uk.org.cse.stockimport.domain.services.SpaceHeatingSystemType;
import uk.org.cse.stockimport.domain.services.impl.BoilerMatch;
import uk.org.cse.stockimport.domain.services.impl.SpaceHeatingDTO;
import uk.org.cse.stockimport.ehcs2010.spss.elementreader.lookup.EHCSPrimaryHeatingCode;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;

/**
 * @since 1.0
 */
public class SpssSpaceHeatingReader extends AbsSpssReader<ISpaceHeatingDTO> {

    private static final Logger logger = LoggerFactory.getLogger(SpssSpaceHeatingReader.class);

    private final SedbukHelper sedbuk;

    /**
     * @since 1.0
     */
    public SpssSpaceHeatingReader(final String executionId, final IHouseCaseSourcesRepositoryFactory providerFactory,
            final SedbukFix fixTable, final ISedbukIndex index) {
        super(executionId, providerFactory);
        this.sedbuk = new SedbukHelper(fixTable, index);
    }

    @Override
    public List<ISpaceHeatingDTO> read(final IHouseCaseSources<Object> provider) {
        final IHouseCaseDTO houseCase = provider.requireOne(HouseCaseDTO.class);
        if (houseCase == null) {
            throw new IllegalArgumentException("Could not find a house case for " + provider.getAacode());
        }

        final List<ISpaceHeatingDTO> dtos = new ArrayList<ISpaceHeatingDTO>();
        final SpaceHeatingDTO dto = new SpaceHeatingDTO();
        dto.setAacode(provider.getAacode());
        if (logger.isDebugEnabled()) {
            logger.debug("building space heating for: " + dto.getAacode());
        }

        final ServicesEntry services = provider.requireOne(ServicesEntry.class);

        final String manufacturer = services.getBoiler_ManufacturerName();
        final String model = services.getBoiler_ModelName_Number();

        final BoilerMatch matchDetails = new BoilerMatch();
        matchDetails.setSurveyMake(manufacturer);
        matchDetails.setSurveyModel(model);
        dto.setBoilerMatch(matchDetails);

        final Optional<IBoilerTableEntry> sedbukMatch = sedbuk.lookup(services.getBoiler_Code(),
                services.getBoilerGroup(),
                services.getMainHeatingFuel(),
                services.getBackBoiler_Type_Fuel(),
                manufacturer, model);

        if (sedbukMatch.isPresent()) {
            setSebukDataOnDTO(dto, sedbukMatch.get());
        } else {
            final EHCSPrimaryHeatingCode ehsCode = EHCSPrimaryHeatingCode.lookupByEHSCode(services.getBoiler_Code());
            final Enum1713 boilerGroup = services.getBoilerGroup();
            final FuelType fuelType = sedbuk.getMostLikelyFuelType(ehsCode, boilerGroup, services.getMainHeatingFuel(), services.getBackBoiler_Type_Fuel());
            if (ehsCode != null) {
                setEHCSPrimaryHeatingCodeDataOnDTO(dto, ehsCode, fuelType, boilerGroup);
            } else {
                if (!fallBackToBackBoilerHeatingData(dto, services)) {
                    setNoPrimaryHeatingSystem(dto);
                }
            }

            setStorageCombiDetailsAbsent(dto);
            dto.setCondensing(Optional.<Boolean>absent());
        }

        setCommonDataOnDTO(dto, services);

        dto.setInstallationYear(getBoilerAge(dto, provider.getSurveyYear(), services, houseCase));

        dtos.add(dto);
        return dtos;
    }

    /**
     * Returns the boiler age. If the heating system is missing sets the age to
     * absent. If a back boiler is present, attempts to use the back boiler age.
     * If this fails, attempts to use the boiler age. If a boiler other than a
     * back boiler is present, attempts to use the boiler age. If the
     * appropriate data is missing, sets the age to absent.
     *
     * @param dto
     * @param surveyYear
     * @param services
     * @param houseCase
     * @return
     * @since 1.0
     */
    public Optional<Integer> getBoilerAge(final SpaceHeatingDTO dto, final int surveyYear, final ServicesEntry services,
            final IHouseCaseDTO houseCase) {
        if (dto.getSpaceHeatingSystemType() == SpaceHeatingSystemType.MISSING) {
            return Optional.<Integer>absent();
        } else if (services.getBackBoiler_Age() != null
                && (dto.getSpaceHeatingSystemType() == SpaceHeatingSystemType.BACK_BOILER || dto
                .getSpaceHeatingSystemType() == SpaceHeatingSystemType.BACK_BOILER_NO_CENTRAL_HEATING)) {
            return Optional.of(getBoilerInstallationDateOrFallbackToHouseConstructionDate(services.getBackBoiler_Age(),
                    surveyYear, houseCase));
        } else if (services.getBoiler_Age() != null) {
            return Optional.of(getBoilerInstallationDateOrFallbackToHouseConstructionDate(services.getBoiler_Age(),
                    surveyYear, houseCase));
        } else {
            return Optional.<Integer>absent();
        }
    }

    /**
     * Returns the boiler age, accounting for the special code 88 = set from
     * house construction date. If age is not 88, it is subtracted from the
     * survey year to give our result. If this is earlier than the earliest date
     * the house could have been built, we use that date instead.
     *
     * @assumption If boiler age is 88, it is the special value from the EHS
     * which means 'use house construction year'. We use the house construction
     * date range end if it is present, otherwise we use the construction date
     * range start.
     * @param age
     * @param surveyYear
     * @param houseCase
     * @return
     */
    private Integer getBoilerInstallationDateOrFallbackToHouseConstructionDate(final int age, final int surveyYear,
            final IHouseCaseDTO houseCase) {
        if (age == 88) {
            return houseCase.getBuildYear();
        } else {
            final int boilerInstallationYear = surveyYear - age;
            if (houseCase.getBuildYear() > boilerInstallationYear) {
                logger.error(String
                        .format("Boiler of age %s resulted in boiler installation date before the earliest possible construction date %s for house %s",
                                age,
                                houseCase.getBuildYear(), houseCase.getAacode()));
                return houseCase.getBuildYear();
            } else {
                return boilerInstallationYear;
            }
        }
    }

    /**
     * <p>
     * Fills a SpaceHeatingDTO based on data looked up in the Sedbuk boiler
     * tables. </p>
     *
     * @assumption CHM appear to use winterEfficiency (the highest of the three
     * efficiency values) from their Sedbuk lookups, although they have not
     * documented this. We are storing winter, summer and annual efficiencies
     * since we can make use of this data in our energy calculation.
     * @param dto The SpaceHeatingDTO to fill with data.
     * @param boilerTable The Sedbuk boiler table.
     * @param sedbukRow The row in the Sedbuk boiler table that the dto should
     * be populated from.
     * @since 1.0
     */
    public void setSebukDataOnDTO(final SpaceHeatingDTO dto, final IBoilerTableEntry boilerEntry) {
        dto.setMainHeatingFuel(BoilerMatchInterface.nhmFromBoilerMatch(boilerEntry.getFuelType()));
        dto.setSpaceHeatingSystemType(lookupHeatingTypeForBoiler(BoilerMatchInterface.nhmFromBoilerMatch(boilerEntry.getBoilerType())).get());

        dto.setFlueType(Optional.of(BoilerMatchInterface.nhmFromBoilerMatch(boilerEntry.getFlueType())));

        dto.setSummerEfficiency(Optional.of(boilerEntry.getSummerEfficiency() / 100.0));
        dto.setWinterEfficiency(Optional.of(boilerEntry.getWinterEfficiency() / 100.0));
        dto.setBasicEfficiency(boilerEntry.getAnnualEfficiency() / 100.0);

        dto.setStorageHeaterType(Optional.<StorageHeaterType>absent());

        dto.setCondensing(Optional.of(boilerEntry.isCondensing()));

        setStorageCombiDetails(dto, boilerEntry);

        final BoilerMatch matchDetails = dto.getBoilerMatch();

        matchDetails.setSedbukBrand(boilerEntry.getBrand());
        matchDetails.setSedbukModel(boilerEntry.getModel());
        matchDetails.setSedbukQualifier(boilerEntry.getQualifier());
        matchDetails.setSedbukRow(boilerEntry.getRow());
    }

    /**
     * @param dto
     * @param boilerEntry
     */
    private void setStorageCombiDetails(final SpaceHeatingDTO dto, final IBoilerTableEntry boilerEntry) {
        if (dto.getSpaceHeatingSystemType() == SpaceHeatingSystemType.STORAGE_COMBI) {
            dto.setStorageCombiCylinderVolume(Optional.fromNullable(boilerEntry.getStoreBoilerVolume()));
            dto.setStorageCombiSolarVolume(Optional.fromNullable(boilerEntry.getStoreSolarVolume()));
            dto.setStorageCombiCylinderInsulationThickness(Optional.fromNullable(boilerEntry
                    .getStoreInsulationThickness()));
            dto.setStorageCombiCylinderThemostatPresent(Optional.<Boolean>absent());
            dto.setStorageCombiCylinderFactoryInsulated(Optional.<Boolean>absent());
        } else {
            setStorageCombiDetailsAbsent(dto);
        }
    }

    private void setStorageCombiDetailsAbsent(final SpaceHeatingDTO dto) {
        dto.setStorageCombiCylinderVolume(Optional.<Double>absent());
        dto.setStorageCombiSolarVolume(Optional.<Double>absent());
        dto.setStorageCombiCylinderInsulationThickness(Optional.<Double>absent());
        dto.setStorageCombiCylinderThemostatPresent(Optional.<Boolean>absent());
        dto.setStorageCombiCylinderFactoryInsulated(Optional.<Boolean>absent());
    }

    /**
     * Fills a SpaceHeatingDTO based on an EHCS primary heating code, with a
     * potential adjustment based on the Spss boiler group.
     *
     * @param dto
     * @param ehsCode
     * @param boilerGroup
     * @since 1.0
     */
    public void setEHCSPrimaryHeatingCodeDataOnDTO(final SpaceHeatingDTO dto, final EHCSPrimaryHeatingCode ehsCode,
            final FuelType fuelType, final Enum1713 boilerGroup) {
        dto.setSpaceHeatingSystemType(getMainHeatingSystemType(ehsCode, boilerGroup));
        dto.setMainHeatingFuel(fuelType);
        dto.setFlueType(Optional.of(ehsCode.getFlueType()));

        dto.setSummerEfficiency(Optional.<Double>absent());
        dto.setWinterEfficiency(Optional.<Double>absent());
        dto.setBasicEfficiency(ehsCode.getEfficiency());
        dto.setStorageHeaterType(getStorageHeaterType(ehsCode));
    }

    /**
     * <p>
     * In some cases, neither of the methods specified by the Cambridge
     * Architectural Research conversion document for identifying the primary
     * heating system give results. </p>
     *
     * @assumption If no main space heating system is present, put a back boiler
     * is, we have used the back boiler as the main space heating system.
     * @param dto
     * @param services
     * @return whether a back boiler was found
     * @since 1.0
     */
    public boolean fallBackToBackBoilerHeatingData(final SpaceHeatingDTO dto, final ServicesEntry services) {
        if (services.getBackBoiler_Present() == Enum10.Yes) {
            dto.setSpaceHeatingSystemType(SpaceHeatingSystemType.BACK_BOILER);
            dto.setMainHeatingFuel(sedbuk.getMainHeatingFuelFromBackBoilerFuel(services.getBackBoiler_Type_Fuel()));
            dto.setStorageHeaterType(Optional.<StorageHeaterType>absent());
            dto.setFlueType(Optional.of(FlueType.OPEN_FLUE));

            dto.setSummerEfficiency(Optional.<Double>absent());
            dto.setWinterEfficiency(Optional.<Double>absent());
            dto.setBasicEfficiency(SOLID_BACK_BOILER_CLOSED_FIRE.getEfficiency());
            return true;
        }
        return false;
    }

    /**
     * <p>
     * If both the CAR specified methods for finding a heating system fail, and
     * there is not a back boiler, set the primary heating to be missing. </p>
     *
     * @assumption CAR do not specify what to do when there are no heating
     * systems present. The CHM model treats as having standard gas boilers. The
     * NHM instead treats these dwellings as having no primary heating, because
     * we have identified a number of cases where the house is likely being
     * heated entirely by other heating.
     * @param dto
     * @since 1.0
     */
    public void setNoPrimaryHeatingSystem(final SpaceHeatingDTO dto) {
        dto.setSpaceHeatingSystemType(SpaceHeatingSystemType.MISSING);
        dto.setMainHeatingFuel(FuelType.MAINS_GAS);
        dto.setStorageHeaterType(Optional.<StorageHeaterType>absent());
        dto.setFlueType(Optional.<FlueType>absent());

        dto.setSummerEfficiency(Optional.<Double>absent());
        dto.setWinterEfficiency(Optional.<Double>absent());
        dto.setBasicEfficiency(0d);
    }

    /**
     * Fills the DTO with data that is taken entirely from the Spss services
     * data, regardless of which mechanism was used to identify the primary
     * heating system.
     *
     * @param dto
     * @param services
     * @since 1.0
     */
    public void setCommonDataOnDTO(final SpaceHeatingDTO dto, final ServicesEntry services) {
        dto.setHeatingSystemControlTypes(new ArrayList<>(getHeatingSystemControlTypes(services)));
        dto.setStorageHeaterControlType(getStorageHeaterControlType(dto.getSpaceHeatingSystemType(), services));
        dto.setSecondaryHeatingSystemType(getSecondaryHeatingSystemType(services));

        final Optional<ElectricityTariffType> electricTariff = getElectricTariff(dto.getMainHeatingFuel(), services.getMainHeatingFuel());
        final Optional<Boolean> communityChargingUsageBased = getIsCommunityChargingUsageBased(dto.getSpaceHeatingSystemType());
        final Optional<Double> CHPFraction = getCHPFraction(dto.getSpaceHeatingSystemType());
        dto.setElectricTariff(electricTariff);
        dto.setCommunityChargingUsageBased(communityChargingUsageBased);
        dto.setChpFraction(CHPFraction);
    }

    private final Map<Enum1776, SecondaryHeatingSystemType> otherHeatingMapping = ImmutableMap
            .<Enum1776, SecondaryHeatingSystemType>builder().put(Enum1776.__MISSING, GAS_FIRE)
            .put(Other, GAS_FIRE).put(Paraffin_PortableHeaters, GAS_FIRE).put(LPG_FixedHeaters, GAS_FIRE)
            .put(MainsGas_LiveEffect_SealedToChim, GAS_COAL_EFFECT_FIRE)
            .put(SolidFuelHeaters_OpenFire, OPEN_FIRE).put(SolidFuelHeaters_Stove_SpaceHeater, OPEN_FIRE)
            .put(MainsGas_Unknown, GAS_FIRE).put(MainsGas_Flueless, GAS_FIRE_FLUELESS)
            .put(ElectricHeaters_Panel_ConvectorOrRa, ELECTRIC_ROOM_HEATERS).put(MainsGas_OpenFlue, GAS_FIRE_OPEN_FLUE)
            .put(ElectricHeaters_Portable, ELECTRIC_ROOM_HEATERS)
            .put(MainsGas_Condensing, GAS_FIRE).put(MainsGas_BalancesFlue, GAS_FIRE)
            .put(ElectricHeaters_IndividualStorageHe, ELECTRIC_ROOM_HEATERS)
            .put(MainsGas_LiveEffect_FanAssistedF, GAS_COAL_EFFECT_FIRE).put(MainsGas_FanAssisted, GAS_FIRE)
            .put(MainsGas_Decorative_OpenToChimney, GAS_COAL_EFFECT_FIRE).build();

    /**
     * <p>
     * Looks up whether a secondary heating system is present, and if so what
     * type it is. </p>
     *
     * @assumption The Cambridge Architectural Research document does not
     * specify how to convert EHS other heating types to their values. We have
     * created a mapping which appears to fit with their data.
     * @assumption CAR have no value we can map to for when EHS other heating
     * type specifies electric storage heaters. We are treating these as
     * electric heaters, but this will result in a much higher electricity bill
     * for these dwellings.
     * @param services
     * @return
     * @since 1.0
     */
    public SecondaryHeatingSystemType getSecondaryHeatingSystemType(final ServicesEntry services) {
        if (services.getOtherHeating_Present() == Enum10.Yes) {
            return otherHeatingMapping.get(services.getOtherHeating_TypeOfSystem());
        }

        return SecondaryHeatingSystemType.NO_SECONDARY_SYSTEM;
    }

    /**
     * Looks up the heating system control types from the EHS services data.
     *
     * @param services
     * @return A set containing the heating control types found.
     * @since 1.0
     */
    public Set<HeatingSystemControlType> getHeatingSystemControlTypes(final ServicesEntry services) {
        final Set<HeatingSystemControlType> results = new HashSet<HeatingSystemControlType>();

        if (services.getPrimaryHeatingControls_CentralTimer() == Yes) {
            results.add(PROGRAMMER);
        }
        if (services.getPrimaryHeatingControls_RoomThermost() == Yes) {
            results.add(ROOM_THERMOSTAT);
        }
        if (services.getPrimaryHeatingControls_Thermostatic() == Yes) {
            results.add(THERMOSTATIC_RADIATOR_VALVE);
        }
        if (services.getPrimaryHeatingControls_TimeAndTemp() == Yes) {
            results.add(TIME_TEMPERATURE_ZONE_CONTROL);
        }
        if (services.getPrimaryHeatingControls_DelayedTime() == Yes) {
            results.add(DELAYED_START_THERMOSTAT);
        }

        return results;
    }

    /**
     * If a storage heater is present, looks through the possible types of
     * storage heater control in the EHS services entry. If no special storage
     * heater controls are found, returns manual control.
     *
     * @param dto
     * @param services
     * @return A storage heater control type if there is a storage heater,
     * otherwise absent.
     * @since 1.0
     */
    public Optional<StorageHeaterControlType> getStorageHeaterControlType(final SpaceHeatingSystemType heatingSystemType,
            final ServicesEntry services) {
        if (heatingSystemType == SpaceHeatingSystemType.STORAGE_HEATER) {
            if (services.getPrimaryHeatingControls_CelectTypeC() == Yes) {
                return Optional.of(StorageHeaterControlType.CELECT_CHARGE_CONTROL);
            }
            if (services.getPrimaryHeatingControls_AutomaticCha() == Yes) {
                return Optional.of(StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL);
            }
            return Optional.of(StorageHeaterControlType.MANUAL_CHARGE_CONTROL);
        }
        return Optional.absent();
    }

    private final Map<EHCSPrimaryHeatingCode, StorageHeaterType> storageHeaters = ImmutableMap.of(
            ELECTRIC_STORAGE_MODERN_SLIMLINE_CONVECTOR, CONVECTOR,
            ELECTRIC_STORAGE_MODERN_SLIMLINE_WITH_FAN, FAN, ELECTRIC_STORAGE_OLD_LARGE_VOLUME, OLD_LARGE_VOLUME);

    /**
     * Gets the type of storage heater from the EHS codes table. Returns absent
     * if the system is not a storage heater, or if the type of storage heater
     * is unknown. This is not specified by Cambridge Architectural Research,
     * but is extra detail we use in our model.
     *
     * @param ehsCode
     * @return
     * @since 1.0
     */
    public Optional<StorageHeaterType> getStorageHeaterType(final EHCSPrimaryHeatingCode ehsCode) {
        if (storageHeaters.containsKey(ehsCode)) {
            return Optional.of(storageHeaters.get(ehsCode));
        }
        return Optional.absent();
    }

    /**
     * <p>
     * Returns whether community charging is usage based. </p>
     *
     * @assumption All community charging is usage based as specified by the CAR
     * conversion document.
     * @param dto
     * @return
     * @since 1.0
     */
    public Optional<Boolean> getIsCommunityChargingUsageBased(final SpaceHeatingSystemType heatingSystemType) {
        if (heatingSystemType == SpaceHeatingSystemType.COMMUNITY_HEATING_WITH_CHP
                || heatingSystemType == SpaceHeatingSystemType.COMMUNITY_HEATING_WITHOUT_CHP) {
            return Optional.of(true);
        }
        return Optional.absent();
    }

    /**
     * Looks up the electricity tariff based on the main heating fuel. Returns
     * absent if the main heating fuel is not electric. The Cambridge
     * Architectural Research conversion document specifies to do this for heat
     * pumps. We have extended this approach to include all electric devices
     * (storage heaters are probably the most important example here).
     *
     * @param fuelType FuelType in our own format. (This is more convenient for
     * checking whether or not it's electricity).
     * @param mainHeatingFuel the EHS field from which the tariff type will be
     * extracted.
     * @return
     * @since 1.0
     */
    public Optional<ElectricityTariffType> getElectricTariff(final FuelType fuelType, final Enum1777 mainHeatingFuel) {
        if (fuelType == FuelType.ELECTRICITY) {
            return Optional.fromNullable(lookupTariff(mainHeatingFuel));
        }
        return Optional.absent();
    }

    private ElectricityTariffType lookupTariff(final Enum1777 mainHeatingFuel) {
        switch (mainHeatingFuel) {
            case Electricity_7HrTariff:
                return ElectricityTariffType.ECONOMY_7;
            case Electricity_10HrTariff:
                return ElectricityTariffType.ECONOMY_10;
            case Electricity_24HrTariff:
                return ElectricityTariffType.TWENTYFOUR_HOUR_HEATING;
            case Electricity_Standard:
                return ElectricityTariffType.FLAT_RATE;
            default:
                return null;
        }
    }

    private final List<Enum1713> combi = Arrays.asList(new Enum1713[]{Enum1713.Combination, Enum1713.CondensingCombi});

    /**
     * <p>
     * Get the space heating system type based on the EHCS code. </p>
     * <p>
     * Uses EHCSPrimaryHeatingCode, which is lookup table 4 in the Cambridge
     * Architectural Research conversion document. For standard boilers, it then
     * checks if they should be classified as a combi or back boiler using the
     * boiler group field from the EHS document. </p>
     *
     * @assumption If the EHS specifies a boiler group of combi or back boiler
     * and the EHS boiler type is a standard boiler, the boiler group should
     * take precedence. CAR specify this only for gas boilers, but we have
     * extended it to cover other fuel types.
     * @param ehsCode
     * @param boilerGroup
     * @return
     * @since 1.0
     */
    public SpaceHeatingSystemType getMainHeatingSystemType(final EHCSPrimaryHeatingCode ehsCode, final Enum1713 boilerGroup) {
        if (ehsCode.getSystem() == SpaceHeatingSystemType.STANDARD) {
            if (combi.contains(boilerGroup)) {
                return SpaceHeatingSystemType.COMBI;
            }
            if (Enum1713.BackBoiler == boilerGroup) {
                return SpaceHeatingSystemType.BACK_BOILER;
            }
        }
        return ehsCode.getSystem();
    }

    /**
     * <p>
     * Returns the CHP fraction if the heating system is a CHP type, otherwise
     * returns Absent. </p>
     *
     * @assumption The CHP fraction of a CHP system is always 0.35 as specified
     * by the CAR conversion document.
     * @param heatingType The type of the main heating system.
     * @return A double between 0 and 1 if CHP is in use, or Absent if it isn't.
     * @since 1.0
     */
    public Optional<Double> getCHPFraction(final SpaceHeatingSystemType heatingType) {
        if (heatingType == SpaceHeatingSystemType.COMMUNITY_HEATING_WITH_CHP) {
            return Optional.of(0.35);
        }
        return Optional.absent();
    }

    @Override
    protected Set<Class<?>> getSurveyEntryClasses() {
        return ImmutableSet.<Class<?>>of(ServicesEntryImpl.class, HouseCaseDTO.class);
    }

    @Override
    protected Class<?> readClass() {
        return ISpaceHeatingDTO.class;
    }

}
