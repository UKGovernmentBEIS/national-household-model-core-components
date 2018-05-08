package uk.org.cse.stockimport.domain;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.hom.types.TenureType;
import uk.org.cse.stockimport.domain.schema.Constraint;
import uk.org.cse.stockimport.domain.schema.DTO;
import uk.org.cse.stockimport.domain.schema.DTOField;
import uk.org.cse.stockimport.domain.types.DTOFloorConstructionType;

/**
 * IHouseCaseDTO.
 *
 * @author richardt
 * @version $Id: IHouseCaseDTO.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 *
 */
@DTO(value = "cases", required = true, description = "This data file holds what we consider headline information for a survey case, this file needs to present for in every import package as it is the first one processed by the system and thus provides the dictionary of all house cases and AACODES (Unique reference) which should be imported into the housing stock.")
public interface IHouseCaseDTO extends IBasicDTO {

    public static final String FRONT_PLOT_WIDTH = "frontPlotWidth";
    public static final String BACK_PLOT_DEPTH = "backPlotDepth";
    public static final String FRONT_PLOT_DEPTH = "frontPlotDepth";
    public static final String NUM_OF_BEDROOMS = "numOfBedrooms";
    public static final String HAS_LOFT = "hasLoft";
    public static final String PARTLY_OWNS_ROOF = "partlyOwnsRoof";
    public static final String HAS_ACCESS_TO_OUTSIDE_SPACE = "hasAccessToOutsideSpace";
    public static final String ON_GAS_GRID = "onGasGrid";
    public static final String MORPHOLOGY_TYPE = "morphologyType";
    public static final String BACK_PLOT_WIDTH = "backPlotWidth";
    public static final String BUILD_YEAR = "buildYear";
    public static final String HAS_DRAFT_LOBBY = "hasDraftLobby";
    public static final String NUM_OF_HABITAL_ROOMS = "numOfHabitalRooms";
    public static final String LIVING_AREA_FACTION = "livingAreaFaction";
    public static final String GRND_FLOOR_TYPE = "grndFloorType";
    public static final String CHILDREN = "children";
    public static final String ADULTS = "adults";
    public static final String BUILT_FORM_TYPE = "builtFormType";
    public static final String TENURE_TYPE = "tenureType";
    public static final String REGION_TYPE = "regionType";
    public static final String DWELLING_CASE_WEIGHT = "dwellingCaseWeight";
    public static final String HOUSEHOLD_CASE_WEIGHT = "householdCaseWeight";
    public static final String MAIN_FLOOR_LEVEL = "mainFloorLevel";

    @DTOField(value = MAIN_FLOOR_LEVEL,
            constraint = @Constraint(value = Constraint.Type.OPTIONAL,
                    missing = "0"))
    public int getMainFloorLevel();

    public void setMainFloorLevel(final int mainFloorLevel);

    @DTOField(HOUSEHOLD_CASE_WEIGHT)
    public double getHouseholdCaseWeight();

    public void setHouseholdCaseWeight(final double householdCaseWeight);

    @DTOField(DWELLING_CASE_WEIGHT)
    public double getDwellingCaseWeight();

    public void setDwellingCaseWeight(final double dwellingCaseWeight);

    @DTOField(REGION_TYPE)
    public RegionType getRegionType();

    public void setRegionType(final RegionType regionType);

    @DTOField(TENURE_TYPE)
    public TenureType getTenureType();

    public void setTenureType(final TenureType tenureType);

    @DTOField(BUILT_FORM_TYPE)
    public BuiltFormType getBuiltFormType();

    public void setBuiltFormType(final BuiltFormType builtFormType);

    @DTOField(ADULTS)
    public Optional<Integer> getAdultOccupants();

    public void setAdultOccupants(final Optional<Integer> adultOccupants);

    @DTOField(CHILDREN)
    public Optional<Integer> getChildOccupants();

    public void setChildOccupants(final Optional<Integer> childOccupants);

    @DTOField(GRND_FLOOR_TYPE)
    public DTOFloorConstructionType getFloorConstructionType();

    public void setFloorConstructionType(
            final DTOFloorConstructionType floorConstructionType);

    @DTOField(LIVING_AREA_FACTION)
    public double getLivingAreaFaction();

    public void setLivingAreaFaction(final double livingAreaFaction);

    @DTOField(NUM_OF_HABITAL_ROOMS)
    public int getNumOfHabitalRooms();

    public void setNumOfHabitalRooms(final int numOfHabitalRooms);

    @DTOField(HAS_DRAFT_LOBBY)
    public boolean isHasDraftLoby();

    public void setHasDraftLoby(final boolean hasDraftLoby);

    @DTOField(MORPHOLOGY_TYPE)
    public MorphologyType getMorphologyType();

    public void setMorphologyType(final MorphologyType morphologyType);

    @DTOField(ON_GAS_GRID)
    public boolean isOnGasGrid();

    public void setOnGasGrid(final boolean onGasGrid);

    @DTOField(HAS_ACCESS_TO_OUTSIDE_SPACE)
    public boolean isHasAccessToOutsideSpace();

    public void setHasAccessToOutsideSpace(final boolean hasAccessToOutsideSpace);

    @DTOField(PARTLY_OWNS_ROOF)
    public boolean isOwnsPartOfRoof();

    public void setOwnsPartOfRoof(final boolean ownsPartOfRoof);

    @DTOField(HAS_LOFT)
    public boolean isHasLoft();

    public void setHasLoft(final boolean hasLoft);

    @DTOField(NUM_OF_BEDROOMS)
    public int getNumberOfBedrooms();

    public void setNumberOfBedrooms(final int numberOfBedrooms);

    @DTOField(FRONT_PLOT_DEPTH)
    public double getFrontPlotDepth();

    public void setFrontPlotDepth(final double frontPlotDepth);

    @DTOField(FRONT_PLOT_WIDTH)
    public double getFrontPlotWidth();

    public void setFrontPlotWidth(final double frontPlotWidth);

    @DTOField(BACK_PLOT_DEPTH)
    public double getBackPlotDepth();

    public void setBackPlotDepth(final double backPlotDepth);

    @DTOField(BACK_PLOT_WIDTH)
    public double getBackPlotWidth();

    public void setBackPlotWidth(final double backPlotWidth);

    @DTOField(BUILD_YEAR)
    public int getBuildYear();

    public void setBuildYear(final int buildYear);

    // WAT
    public double getArea();

    public void setArea(final double area);

}
