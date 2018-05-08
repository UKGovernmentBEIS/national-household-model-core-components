package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static uk.org.cse.stockimport.ehcs2010.spss.Spss2012HelperMethods.buildRoomTypePredicate;
import static uk.org.cse.stockimport.ehcs2010.spss.Spss2012HelperMethods.isHouse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.Dimensions_09Plus10Entry;
import uk.org.cse.nhm.ehcs10.derived.impl.Dimensions_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.impl.General_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.impl.Interview_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.impl.Physical_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.derived.types.Enum129;
import uk.org.cse.nhm.ehcs10.derived.types.Enum23;
import uk.org.cse.nhm.ehcs10.derived.types.Enum24;
import uk.org.cse.nhm.ehcs10.derived.types.Enum28;
import uk.org.cse.nhm.ehcs10.derived.types.Enum76;
import uk.org.cse.nhm.ehcs10.physical.AroundEntry;
import uk.org.cse.nhm.ehcs10.physical.FlatdetsEntry;
import uk.org.cse.nhm.ehcs10.physical.InteriorEntry;
import uk.org.cse.nhm.ehcs10.physical.IntroomsEntry;
import uk.org.cse.nhm.ehcs10.physical.ServicesEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.AroundEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.Firstimp_PhysicalEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.FlatdetsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.InteriorEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.IntroomsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.ServicesEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1186;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1338;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1553;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1650;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1757;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1758;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.hom.types.TenureType;
import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.domain.types.DTOFloorConstructionType;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;

/**
 * SpssHouseCaseReader. Retrieves SurveyEntry collections containing EHS survey
 * data from the database. Builds a HouseCaseDTO for every aacode found in the
 * data.
 *
 * @author richardt
 * @version $Id: SpssHouseCaseReader.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class SpssHouseCaseReader extends AbsSpssReader<IHouseCaseDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpssHouseCaseReader.class);

    private final Map<Enum28, MorphologyType> rumorphConversion = ImmutableMap.of(Enum28.HamletsAndIsolatedDwellings,
            MorphologyType.HamletsAndIsolatedDwellings, Enum28.Village,
            MorphologyType.Village, Enum28.TownAndFringe, MorphologyType.TownAndFringe, Enum28.UrbanGreaterThan10K,
            MorphologyType.Urban);

    protected Map<String, FloorLocationType> roomLevelConversion = ImmutableMap.<String, FloorLocationType>builder()
            .put("bb", FloorLocationType.BASEMENT)
            .put("BB", FloorLocationType.BASEMENT).put("gg", FloorLocationType.GROUND)
            .put("GG", FloorLocationType.GROUND).build();

    /**
     * @since 1.0
     */
    public SpssHouseCaseReader(final String executionId, final IHouseCaseSourcesRepositoryFactory mongoProviderFactory) {
        super(executionId, mongoProviderFactory);
    }

    @Override
    protected Set<Class<?>> getSurveyEntryClasses() {
        return ImmutableSet.<Class<?>>of(General_09Plus10EntryImpl.class, Firstimp_PhysicalEntryImpl.class,
                Interview_09Plus10EntryImpl.class, Physical_09Plus10EntryImpl.class,
                IntroomsEntryImpl.class, InteriorEntryImpl.class, Dimensions_09Plus10EntryImpl.class,
                ServicesEntryImpl.class, AroundEntryImpl.class, FlatdetsEntryImpl.class);
    }

    /**
     * @return @since 1.0
     */
    @Override
    public List<IHouseCaseDTO> read(final IHouseCaseSources<Object> provider) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("building case: " + provider.getAacode());
        }

        final List<IHouseCaseDTO> houseCases = new ArrayList<IHouseCaseDTO>();
        final HouseCaseDTO houseCase = new HouseCaseDTO();
        houseCase.setAacode(provider.getAacode());
        final General_09Plus10EntryImpl general = provider.requireOne(General_09Plus10EntryImpl.class);
        final Firstimp_PhysicalEntryImpl firstImp = provider.requireOne(Firstimp_PhysicalEntryImpl.class);
        final Optional<Interview_09Plus10EntryImpl> interview = provider.getOne(Interview_09Plus10EntryImpl.class);
        final InteriorEntry interiorEntry = provider.requireOne(InteriorEntry.class);
        final List<IntroomsEntry> introomsEntries = provider.getAll(IntroomsEntry.class);
        final Physical_09Plus10EntryImpl physical = provider.requireOne(Physical_09Plus10EntryImpl.class);
        final Dimensions_09Plus10Entry dimensions = provider.requireOne(Dimensions_09Plus10EntryImpl.class);
        final AroundEntry aroundEntry = provider.requireOne(AroundEntry.class);
        final Optional<FlatdetsEntry> flatDets = provider.getOne(FlatdetsEntry.class);

        final ServicesEntry services = provider.requireOne(ServicesEntry.class);

        final Enum1758 loftType = services.getLoft_Type();

        if (loftType == Enum1758.NoBoardingOrPartialBoarding
                || loftType == Enum1758.FullyBoarded
                || services.getLoft_ApproxThickness() == Enum1757.NoInsulation) {
            houseCase.setHasLoft(true);
        }

        if (flatDets.isPresent()) {
            final Integer level = flatDets.get().getMainFloorLevel_Numeric();
            if (level != null) {
                houseCase.setMainFloorLevel(level.intValue());
            }
        }

        houseCase.setDwellingCaseWeight(general.getDwellWeight_PairedCases2009_10And2010_11());
        houseCase.setRegionType(governmentOfficeRegionToSAPRegion(general.getRegion()));

        houseCase.setTenureType(ehsTenureTypeToSAPTenureType(general.getTenure_TENURE8X()));

        houseCase.setBuiltFormType(ehsDwellingTypeToBuiltFormType(physical.getDwellingType_DWTYPENX()));

        houseCase
                .setMorphologyType(rumorphToMorphologyType(provider.getAacode(), general.getRurality_Morphology_COA_()));

        houseCase.setBuildYear(getBuildYear(houseCase.getAacode(), firstImp));

        houseCase.setAdultOccupants(Optional.fromNullable(getAdultOccupants(interview)));
        if (interview.isPresent()) {
            houseCase.setChildOccupants(Optional
                    .fromNullable(interview.get().getNumberOfDependentChildrenInHousehold()));
        }

        if (houseCase.getBuiltFormType() == null) {
            LOGGER.error("Imported a non-residential building: " + provider);
        }

        houseCase.setFloorConstructionType(getGroundFloorConstrutionType(interiorEntry, introomsEntries));

        setOnGasGrid(provider, houseCase);

        buildLivingRoomData(houseCase, interiorEntry, dimensions);
        if (isHouse(physical) == false) {
            houseCase.setHasDraftLoby(true);
        }

        setFrontAndBackPlotAreas(provider, houseCase);
        houseCase.setHasAccessToOutsideSpace(hasAccessToOutsideSpace(aroundEntry, houseCase));
        houseCase.setNumberOfBedrooms(getNumberOfBedrooms(interview, interiorEntry));
        houseCase.setOwnsPartOfRoof(getOwnsPartOfRoof(interview));

        houseCases.add(houseCase);
        return houseCases;
    }

    /**
     * @assumption The CAR conversion document specifies to use the EHS age
     * bands. We are using the actual construction year instead since we believe
     * it gives more precise data.
     * @assumption If the EHS does not specify the actual construction date,
     * fall back to start date of its age band (most pessimistic choice). In
     * practice, only H0821202 ('Pre 1850') is missing an actual construction
     * date.
     */
    private int getBuildYear(final String aacode, final Firstimp_PhysicalEntryImpl firstImp) {
        Integer buildYear = firstImp.getActualDateOfConstruction();
        if (buildYear == null) {
            buildYear = ehsAgeBandConversion.get(firstImp.getConstructionDate()).getStart().getYear();
            LOGGER.error(String.format("House case %s had no build year data. Using midpoint date of band %s instead.",
                    aacode, buildYear));
        }

        return buildYear;
    }

    /**
     * Use {@link AroundEntry#getWidthOfPlot()} and other similar variables to
     * set the front/back plot size for the given case
     *
     * @param provider
     * @param houseCase
     */
    private void setFrontAndBackPlotAreas(final IHouseCaseSources<Object> provider, final HouseCaseDTO houseCase) {
        final Optional<AroundEntry> maybeAround = provider.getOne(AroundEntry.class);
        if (maybeAround.isPresent()) {
            final AroundEntry around = maybeAround.get();
            final Integer plotWidth = around.getWidthOfPlot();
            if (around.getDoesFrontPlotExist_() == Enum10.Yes) {
                final Integer frontPlotDepth = around.getFrontPlotDepth();
                if ((plotWidth != null) && (frontPlotDepth != null)) {
                    houseCase.setFrontPlotWidth(plotWidth.intValue());
                    houseCase.setFrontPlotDepth(frontPlotDepth.intValue());
                }
            }

            if (around.getDoesRearPlotExist_() == Enum10.Yes) {
                final Integer rearPlotDepth = around.getRearPlotDepth();
                if ((plotWidth != null) && (rearPlotDepth != null)) {
                    houseCase.setBackPlotWidth(plotWidth.intValue());
                    houseCase.setBackPlotDepth(rearPlotDepth.intValue());
                }
            }
        }
    }

    /**
     * Use FINGASMS to determine whether the given case is on the gas grid
     *
     * @param provider
     * @param houseCase
     */
    private void setOnGasGrid(final IHouseCaseSources<Object> provider, final HouseCaseDTO houseCase) {
        final Optional<ServicesEntry> services = provider.getOne(ServicesEntry.class);
        if (services.isPresent()) {
            houseCase.setOnGasGrid(services.get().getGasSystem_MainsSupply() == Enum10.Yes);
        } else {
            LOGGER.warn("{} has no services - setting gas grid to false", provider.getAacode());
        }
    }

    /**
     * Convert the EHS RUMORPH variable to a {@link MorphologyType} variable.
     *
     * @param rurality_Morphology_COA_
     * @return
     */
    private MorphologyType rumorphToMorphologyType(final String aacode, final Enum28 e) {
        if (rumorphConversion.containsKey(e)) {
            return rumorphConversion.get(e);
        } else {
            LOGGER.warn("{} has no RUMORPH - returning urban", aacode);
            return MorphologyType.Urban;
        }
    }

    /**
     * 1. If neither {@link InteriorEntry#getKitchenLevel()} or
     * {@link InteriorEntry#getLivingRoomLevel()} are at basement or ground
     * level then floor type is set to solid.<br/> <br/> 2. Look at
     * {@link IntroomsEntry#getFLOORS_SolidFloors()}, if either kitchen or
     * living room floors are solid then return solid otherwise return timber
     *
     * @param interiorEntry
     * @since 0.0.1-SNAPSHOT
     */
    protected DTOFloorConstructionType getGroundFloorConstrutionType(final InteriorEntry interiorEntry,
            final List<IntroomsEntry> rooms) {
        DTOFloorConstructionType floorContructionType = DTOFloorConstructionType.Solid;

        final FloorLocationType kitchenFloorLevel = roomLevelConversion.get(interiorEntry.getKitchenLevel());
        final FloorLocationType livingRoomLevel = roomLevelConversion.get(interiorEntry.getLivingRoomLevel());

        final IntroomsEntry kitchen = getRoom(rooms, Enum1650.Kitchen);
        final IntroomsEntry livingRoom = getRoom(rooms, Enum1650.LivingRoom);

        if (FloorLocationType.BASEMENT.equals(kitchenFloorLevel) || FloorLocationType.GROUND.equals(kitchenFloorLevel)
                || FloorLocationType.BASEMENT.equals(livingRoomLevel)
                || FloorLocationType.GROUND.equals(livingRoomLevel)) {

            // If both kitchen and living rooms are not solid floors then set
            // floor to suspended timber
            if (Boolean.FALSE.equals(hasSolidFloor(kitchen)) || Boolean.FALSE.equals(hasSolidFloor(livingRoom))) {
                floorContructionType = DTOFloorConstructionType.SuspendedTimber;
            }
        }

        return floorContructionType;
    }

    protected IntroomsEntry getRoom(final List<IntroomsEntry> rooms, final Enum1650 room) {
        final Iterator<IntroomsEntry> roomItr = Collections2.filter(rooms, buildRoomTypePredicate(room)).iterator();
        return (roomItr.hasNext() ? roomItr.next() : null);
    }

    /**
     * Returns null if room is null or
     * {@link IntroomsEntry#getFLOORS_SolidFloors()} is null<br/> <br/> Returns
     * false if {@link IntroomsEntry#getFLOORS_SolidFloors()} equals
     * {@link Enum10#No}<br/> <br/> Returns true otherwise.
     *
     * @param room
     * @return
     * @since 0.0.1-SNAPSHOT
     */
    protected Boolean hasSolidFloor(final IntroomsEntry room) {
        if ((room == null) || (room.getFLOORS_SolidFloors() == null)) {
            // tri-state, we don't know so return null
            return null;
        } else if (Enum10.No.equals(room.getFLOORS_SolidFloors())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns true if {@link Enum76} equals
     * FreeholderOfHouse,LeaseholderOwningFHCollectively
     * ,LeaseholderOwningFHOfWholeBldg, Commonholder_PropertyBuiltAsCH_ or
     * Commonholder_PropertyConvertedToCH_. Otherwise it returns false, this is
     * also the case for any nulls.
     *
     * @param interview
     * @return
     * @since 1.0
     */
    public boolean getOwnsPartOfRoof(final Optional<Interview_09Plus10EntryImpl> interview) {
        if ((interview.isPresent() == false) || (interview.get().getTypeOfOwnership() == null)) {
            return false;
        } else {
            switch (interview.get().getTypeOfOwnership()) {
                case FreeholderOfHouse:
                case LeaseholderOwningFHCollectively:
                case LeaseholderOwningFHOfWholeBldg:
                case Commonholder_PropertyBuiltAsCH_:
                case Commonholder_PropertyConvertedToCH_:
                    return true;
                default:
                    return false;
            }
        }
    }

    private final Map<Enum24, RegionType> regionTypeConversion = ImmutableMap.<Enum24, RegionType>builder()
            .put(Enum24.NorthWest, RegionType.NorthWest)
            .put(Enum24.NorthEast, RegionType.NorthEast)
            .put(Enum24.YorkshireAndTheHumber, RegionType.YorkshireAndHumber)
            .put(Enum24.WestMidlands, RegionType.WestMidlands)
            .put(Enum24.EastEngland, RegionType.EastOfEngland).put(Enum24.EastMidlands, RegionType.EastMidlands)
            .put(Enum24.SouthWest, RegionType.SouthWest)
            .put(Enum24.SouthEast, RegionType.SouthEast).put(Enum24.London, RegionType.London).build();

    /**
     * Maps EHS Government Office Regions to SAP regions. Returns null for
     * 'DoesNotApply' and 'NoAnswer' survey data. Multiple possible SAP regions
     * exist for some EHS Government Regions. We have chosen one region from
     * each, and documented the alternatives we could have chosen here. We have
     * assumed that the EHS Government Office Regions map to SAP regions as
     * follows:
     *
     * @assumption EHS region NorthWest mapped to SAP region WestPennines (not
     * NorthWestEngland)
     * @assumption EHS region NorthEast mapped to SAP region Borders (not
     * NorthEastEngland)
     * @assumption EHS region YorkshireAndTheHumber mapped to SAP region
     * NorthEastEngland (not EastPennines)
     * @assumption EHS region WestMidlands mapped to SAP region Midlands
     * @assumption EHS region EastEngland mapped to SAP region EastAnglia
     * @assumption EHS region EastMidlands mapped to SAP region EastPennines
     * (not Midlands)
     * @assumption EHS region SouthWest mapped to SAP region Severn (not
     * SouthWestEngland)
     * @assumption EHS region SouthEast mapped to SAP region SouthEastEngland
     * (not SouthEngland or Thames)
     * @assumption EHS region London mapped to SAP region Thames
     * @param governmentOfficeRegion An EHS Government Office Region.
     * @return An SAP RegionType.
     * @since 1.0
     */
    public RegionType governmentOfficeRegionToSAPRegion(final Enum24 governmentOfficeRegion) {
        if ((governmentOfficeRegion == null) || (governmentOfficeRegion == Enum24.DoesNotApply)
                || (governmentOfficeRegion == Enum24.NoAnswer)) {
            return null;
        }
        return Lookup(governmentOfficeRegion, regionTypeConversion);
    }

    private final Map<Enum23, TenureType> tenureTypeConversion = ImmutableMap.<Enum23, TenureType>builder()
            .put(Enum23.RSL_Vacant, TenureType.HousingAssociation)
            .put(Enum23.RSL_Occupied, TenureType.HousingAssociation)
            .put(Enum23.LocalAuthority_Vacant, TenureType.LocalAuthority)
            .put(Enum23.LocalAuthority_Occupied, TenureType.LocalAuthority)
            .put(Enum23.OwnerOccupied_Occupied, TenureType.OwnerOccupied)
            .put(Enum23.OwnerOccupied_Vacant, TenureType.OwnerOccupied)
            .put(Enum23.PrivateRented_Occupied, TenureType.PrivateRented)
            .put(Enum23.PrivateRented_Vacant, TenureType.PrivateRented).build();

    /**
     * Maps EHS tenure types to SAP tenure types. We are using the tenure8x
     * field as recommended by the CAR document. The tenure4x field looks like a
     * better fit for SAP, however.
     *
     * @param ehsTenureType An EHS Tenure Type.
     * @return An SAP TenureType.
     * @since 1.0
     */
    public TenureType ehsTenureTypeToSAPTenureType(final Enum23 ehsTenureType) {
        return Lookup(ehsTenureType, tenureTypeConversion);
    }

    private final Map<Enum129, BuiltFormType> builtFormTypeConversion = ImmutableMap.<Enum129, BuiltFormType>builder()
            .put(Enum129.ConvertedFlat, BuiltFormType.ConvertedFlat)
            .put(Enum129.SemiDetached, BuiltFormType.SemiDetached)
            .put(Enum129.PurposeBuiltFlat_LowRise, BuiltFormType.PurposeBuiltLowRiseFlat)
            .put(Enum129.PurposeBuiltFlat_HighRise, BuiltFormType.PurposeBuiltHighRiseFlat)
            .put(Enum129.Detached, BuiltFormType.Detached)
            .put(Enum129.EndTerrace, BuiltFormType.EndTerrace).put(Enum129.MidTerrace, BuiltFormType.MidTerrace)
            .put(Enum129.Bungalow, BuiltFormType.Bungalow).build();

    /**
     * Maps EHS dwelling type to BuiltFormType. Missing dwelling type returns
     * null.
     *
     * @assumption To calculate built form type, we use the field dwtypeNx
     * instead of dwtype7x (the field recommended by CAR), because we require
     * the extra information about high-rise and low-rise flats.
     * @param ehsDwellingType An EHS dwelling type
     * @return A BuiltFormType
     * @since 1.0
     */
    public BuiltFormType ehsDwellingTypeToBuiltFormType(final Enum129 ehsDwellingType) {
        if (ehsDwellingType == Enum129.__MISSING) {
            return null;
        }
        return Lookup(ehsDwellingType, builtFormTypeConversion);
    }

    private final Map<Enum1338, DateRange> ehsAgeBandConversion = ImmutableMap
            .<Enum1338, DateRange>builder()
            .put(Enum1338.Pre1850, new DateRange((DateTime) null, new DateTime(1850, 1, 1, 0, 0, 0, 0)))
            .put(Enum1338._1850_1899,
                    new DateRange(new DateTime(1850, 1, 1, 0, 0, 0, 0), new DateTime(1900, 1, 1, 0, 0, 0, 0)))
            .put(Enum1338._1900_1918,
                    new DateRange(new DateTime(1900, 1, 1, 0, 0, 0, 0), new DateTime(1919, 1, 1, 0, 0, 0, 0)))
            .put(Enum1338._1919_1944,
                    new DateRange(new DateTime(1919, 1, 1, 0, 0, 0, 0), new DateTime(1945, 1, 1, 0, 0, 0, 0)))
            .put(Enum1338._1945_1964,
                    new DateRange(new DateTime(1945, 1, 1, 0, 0, 0, 0), new DateTime(1965, 1, 1, 0, 0, 0, 0)))
            .put(Enum1338._1965_1974,
                    new DateRange(new DateTime(1965, 1, 1, 0, 0, 0, 0), new DateTime(1975, 1, 1, 0, 0, 0, 0)))
            .put(Enum1338._1975_1980,
                    new DateRange(new DateTime(1975, 1, 1, 0, 0, 0, 0), new DateTime(1981, 1, 1, 0, 0, 0, 0)))
            .put(Enum1338._1981_1990,
                    new DateRange(new DateTime(1981, 1, 1, 0, 0, 0, 0), new DateTime(1991, 1, 1, 0, 0, 0, 0)))
            .put(Enum1338._1991_1995,
                    new DateRange(new DateTime(1991, 1, 1, 0, 0, 0, 0), new DateTime(1996, 1, 1, 0, 0, 0, 0)))
            .put(Enum1338._1996_2002,
                    new DateRange(new DateTime(1996, 1, 1, 0, 0, 0, 0), new DateTime(2003, 1, 1, 0, 0, 0, 0)))
            .put(Enum1338.Post2002, new DateRange(new DateTime(2003, 1, 1, 0, 0, 0, 0), (DateTime) null)).build();

    class DateRange {

        private final DateTime start;
        private final DateTime end;

        public DateRange(final DateTime start, final DateTime end) {
            this.start = start;
            this.end = end;
        }

        public DateTime getStart() {
            return start != null ? start : end;
        }

        public DateTime getEnd() {
            return end != null ? end : start;
        }
    }

    /**
     * Calculates the number of adult occupants in the house based on survey
     * questions. This is the total number of people minus the number of
     * dependent children. Returns 0 and logs an error if the result of this
     * calculation is negative.
     *
     * @param interview The interview entry for this house.
     * @return The number of adult occupants.
     * @since 1.0
     */
    public int getAdultOccupants(final Optional<Interview_09Plus10EntryImpl> interview) {
        int numOfAdults = 0;

        if (interview.isPresent()) {
            if (interview.get().getNumberOfPersonsInTheHousehold() < interview.get()
                    .getNumberOfDependentChildrenInHousehold()) {
                LOGGER.error("Imported house with negative number of adults: " + interview.get().getAacode());
                return 0;
            } else {
                numOfAdults = interview.get().getNumberOfPersonsInTheHousehold()
                        - interview.get().getNumberOfDependentChildrenInHousehold();
            }

        }
        return numOfAdults;
    }

    /**
     * Calculates living area faction as follows:<br/> <br/> 1. If values for
     * {@link InteriorEntry#getLivingRoomDepth_Metres_()} and
     * {@link InteriorEntry#getLivingRoomWidth_Metres_()} and
     * {@link Dimensions_09Plus10Entry#getTotalFloorArea()} exists then living
     * area faction is living room area / total floor area, otherwise sets this
     * value to zero.<br/> <br/> 2. Looks up the number of habitable rooms for
     * property and sets this value on the DTO
     *
     * @param interior
     * @param dimensions
     * @return
     * @since 0.0.1-SNAPSHOT
     */
    public void buildLivingRoomData(final IHouseCaseDTO houseCaseDTO, final InteriorEntry interior,
            final Dimensions_09Plus10Entry dimensions) {
        double livingAreaFaction = 0.00;
        final int numOfHabitalRooms = interior.getNumberHabitableRooms() == null ? 0 : interior
                .getNumberHabitableRooms();

        final Double livingRoomWidth = interior.getLivingRoomWidth_Metres_();
        final Double livingRoomDepth = interior.getLivingRoomDepth_Metres_();
        final Double totalFloorArea = dimensions.getTotalFloorArea();
        if ((livingRoomWidth != null) && (livingRoomDepth != null) && (dimensions.getTotalFloorArea() != null)) {
            livingAreaFaction = ((livingRoomWidth * livingRoomDepth) / totalFloorArea);
            if (Double.isNaN(livingAreaFaction)) {
                livingAreaFaction = 0.00;
            }
        }

        houseCaseDTO.setLivingAreaFaction(livingAreaFaction);
        houseCaseDTO.setNumOfHabitalRooms(numOfHabitalRooms);
        houseCaseDTO.setNumberOfBedrooms(0);
    }

    private final Set<Enum1553> bedroomFunctions = ImmutableSet.of(Enum1553.SingleBedroom, Enum1553.TwinBedroom);

    /**
     * TODO
     *
     * @param interview
     * @param interior
     * @return
     * @since 1.0.1
     */
    public int getNumberOfBedrooms(final Optional<Interview_09Plus10EntryImpl> interview, final InteriorEntry interior) {

        int bedrooms = 0;
        if (interior.getDoesBedroomExist_() == Enum10.Yes) {
            bedrooms++;
        }
        if ((interior.getExtraRoom1_DoesRoomExist_() == Enum10.Yes)
                && bedroomFunctions.contains(interior.getExtraRoom1_Function())) {
            bedrooms++;
        }
        if ((interior.getExtraRoom2_DoesRoomExist_() == Enum10.Yes)
                && bedroomFunctions.contains(interior.getExtraRoom2_Function())) {
            bedrooms++;
        }
        if ((interior.getExtraRoom3_DoesRoomExist_() == Enum10.Yes)
                && bedroomFunctions.contains(interior.getExtraRoom3_Function())) {
            bedrooms++;
        }
        if ((interior.getExtraRoom4_DoesRoomExist_() == Enum10.Yes)
                && bedroomFunctions.contains(interior.getExtraRoom4_Function())) {
            bedrooms++;
        }
        if ((interior.getExtraRoom5_DoesRoomExist_() == Enum10.Yes)
                && bedroomFunctions.contains(interior.getExtraRoom5_Function())) {
            bedrooms++;
        }
        if ((interior.getExtraRoom6_DoesRoomExist_() == Enum10.Yes)
                && bedroomFunctions.contains(interior.getExtraRoom6_Function())) {
            bedrooms++;
        }
        if ((interior.getExtraRoom7_DoesRoomExist_() == Enum10.Yes)
                && bedroomFunctions.contains(interior.getExtraRoom7_Function())) {
            bedrooms++;
        }

        if (interview.isPresent() && (bedrooms != interview.get().getTotalNoOfBedroomsHouseholdActuallyHas())) {
            LOGGER.warn(String.format("InteriorEntry bedrooms %s disagrees with interview bedrooms %s.", bedrooms,
                    interview.get().getTotalNoOfBedroomsHouseholdActuallyHas()));
        }
        return bedrooms;
    }

    /**
     * 1. If plot type is {@link Enum1186#PrivatePlot} or
     * {@link Enum1186#SharedPlotOnly} then if
     * {@link HouseCaseDTO#getBackPlotDepth()} and
     * {@link HouseCaseDTO#getBackPlotWidth()} are greated than zero (same for
     * front) then has acess to outside space, otherwise it's false.
     *
     * @param provider
     * @param houseCase
     * @return
     * @since 1.0.1
     */
    protected boolean hasAccessToOutsideSpace(final AroundEntry aroundEntry, final IHouseCaseDTO houseCase) {

        boolean hasAcessToOutsideSpace = false;

        if (aroundEntry != null) {
            final double min = 0.00;
            Enum1186 typeOfPlot = aroundEntry.getTypeOfPlot();
            typeOfPlot = (typeOfPlot == null ? Enum1186.__MISSING : typeOfPlot);

            switch (typeOfPlot) {
                case PrivatePlot:
                case SharedPlotOnly:
                    if (((houseCase.getFrontPlotDepth() > min) && (houseCase.getFrontPlotWidth() > min))
                            || ((houseCase.getBackPlotDepth() > min) && (houseCase.getBackPlotWidth() > min))) {
                        hasAcessToOutsideSpace = true;
                    }
                    break;
                default:
                    break;
            }
        }

        return hasAcessToOutsideSpace;
    }

    @Override
    protected Class<?> readClass() {
        return HouseCaseDTO.class;
    }
}
