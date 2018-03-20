package uk.org.cse.stockimport.simple.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.hom.types.TenureType;
import uk.org.cse.nhm.stockimport.simple.dto.MappableDTOReader;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.domain.types.DTOFloorConstructionType;

public class HouseCaseMapperTest extends AbsMapperTest {
	
	
	final double expectedLivingAreaFraction = 20.23;
	final int expectedNumHabitalRooms = 4;
	final int expectedNumOfBedrooms = 2;
	
	final int expectedHouseHoldWeight = 200;
	final int expectedDwellingWeight = 300;
	
	final BuiltFormType builtFormType = BuiltFormType.Bungalow;
	final boolean hasDraftLoby = true;
	final DTOFloorConstructionType floorConstructionType = DTOFloorConstructionType.SuspendedTimber;
	final boolean partlyOwnsRoof = true;
	
	final boolean hasAccessToOutsideSpace = true;
	final double frontPlotDepth = 2.13;
	final double frontPlotWidth = 12.3;
	final double backPlotDepth = 12.345;
	final double backPlotWidth = 123.556;
	
	final RegionType regionType = RegionType.SouthWest;
	final TenureType tenureType = TenureType.PrivateRented;
	final MorphologyType morphologyType = MorphologyType.HamletsAndIsolatedDwellings;
	final int buildYear = 1066;
	final boolean onGasGrid = true;
	
	final Integer adultOccupants = 2;
	final Integer childOccupants = 2;
	
	final boolean hasLoft = true;
	 
	@Before
	public void initiateTests(){
		fields()
			.add(IBasicDTO.AACODE, aacode)
			
			.add(IHouseCaseDTO.LIVING_AREA_FACTION, String.valueOf(String.valueOf(expectedLivingAreaFraction)))
			.add(IHouseCaseDTO.NUM_OF_HABITAL_ROOMS, String.valueOf(String.valueOf(expectedNumHabitalRooms)))
			.add(IHouseCaseDTO.NUM_OF_BEDROOMS, String.valueOf(String.valueOf(expectedNumOfBedrooms)))
			
			.add(IHouseCaseDTO.DWELLING_CASE_WEIGHT, String.valueOf(expectedDwellingWeight))
			.add(IHouseCaseDTO.HOUSEHOLD_CASE_WEIGHT, String.valueOf(expectedHouseHoldWeight))
			
			.add(IHouseCaseDTO.BUILT_FORM_TYPE, String.valueOf(builtFormType.toString()))
			.add(IHouseCaseDTO.GRND_FLOOR_TYPE, String.valueOf(floorConstructionType.toString()))
			.add(IHouseCaseDTO.HAS_DRAFT_LOBBY, String.valueOf(Boolean.toString(hasDraftLoby)))
			.add(IHouseCaseDTO.PARTLY_OWNS_ROOF, String.valueOf(Boolean.toString(partlyOwnsRoof)))
			
			.add(IHouseCaseDTO.HAS_ACCESS_TO_OUTSIDE_SPACE, String.valueOf(Boolean.toString(hasAccessToOutsideSpace)))
			.add(IHouseCaseDTO.FRONT_PLOT_DEPTH, String.valueOf(frontPlotDepth))
			.add(IHouseCaseDTO.FRONT_PLOT_WIDTH, String.valueOf(frontPlotWidth))
			.add(IHouseCaseDTO.BACK_PLOT_DEPTH, String.valueOf(backPlotDepth))
			.add(IHouseCaseDTO.BACK_PLOT_WIDTH, String.valueOf(backPlotWidth))
	
			.add(IHouseCaseDTO.REGION_TYPE, String.valueOf(regionType.toString()))
			.add(IHouseCaseDTO.TENURE_TYPE, String.valueOf(tenureType.toString()))
			.add(IHouseCaseDTO.MORPHOLOGY_TYPE, String.valueOf(morphologyType.toString()))
			.add(IHouseCaseDTO.BUILD_YEAR, String.valueOf(buildYear))
			.add(IHouseCaseDTO.ON_GAS_GRID, String.valueOf(Boolean.toString(onGasGrid)))
	
			.add(IHouseCaseDTO.ADULTS, String.valueOf(adultOccupants))
			.add(IHouseCaseDTO.CHILDREN, String.valueOf(childOccupants))
			.add(IHouseCaseDTO.HAS_LOFT, String.valueOf(Boolean.toString(hasLoft)));
	}
	
	@Test
	public void testBuildOfCompleteHouseCaseDTOWithMethodMapFieldSet() throws Exception{
		final IHouseCaseDTO dto =new MappableDTOReader<>(HouseCaseDTO.class).read(fieldSet);  
		testBuildReferenceData(dto, aacode);
		testConstrustructionData(dto, builtFormType, hasDraftLoby, floorConstructionType);
		testBuildWeightData(dto, expectedHouseHoldWeight, expectedDwellingWeight);
		testExternalPlotData(dto, hasAccessToOutsideSpace, frontPlotDepth, frontPlotWidth, backPlotDepth, backPlotWidth);
		testGeneralInformationData(dto, regionType, tenureType, morphologyType, buildYear, onGasGrid, partlyOwnsRoof, hasLoft);
		testLivingRoomData(dto, expectedLivingAreaFraction, expectedNumHabitalRooms, expectedNumOfBedrooms);
		testHumanOccupantDetailsData(dto, adultOccupants, childOccupants);
	}
	
	public static final void testHumanOccupantDetailsData(final IHouseCaseDTO houseCaseDTO,
			final Integer adultOccupants,final Integer childOccupants){
		assertEquals("Number of adults",adultOccupants, houseCaseDTO.getAdultOccupants().get());
		assertEquals("Number of children",childOccupants, houseCaseDTO.getChildOccupants().get());
	}
	
	public static final void testGeneralInformationData(final IHouseCaseDTO houseCaseDTO,final RegionType regionType,
			final TenureType tenureType,final MorphologyType morphologyType,final int buildYear,final boolean onGasGrid,final boolean partlyOwnsRoof, final boolean hasLoft){
		assertEquals("RegionType", regionType, houseCaseDTO.getRegionType());
		assertEquals("TenureType",tenureType, houseCaseDTO.getTenureType());
		assertEquals("Morphology",morphologyType, houseCaseDTO.getMorphologyType());
		assertEquals("BuildYear",buildYear, houseCaseDTO.getBuildYear());
		assertEquals("onGasGrid",onGasGrid, houseCaseDTO.isOnGasGrid());
		assertEquals("ownsRoof", partlyOwnsRoof, houseCaseDTO.isOwnsPartOfRoof());
		assertEquals("hasLoft", hasLoft, houseCaseDTO.isHasLoft());
	}
	
	public static final void testExternalPlotData(final IHouseCaseDTO houseCaseDTO,final boolean hasAccessToOutsideSpace,
			final double frontPlotDepth,final double frontPlotWidth,final double backPlotDepth,final double backPlotWidth){
		assertEquals("Outside space",hasAccessToOutsideSpace, houseCaseDTO.isHasAccessToOutsideSpace());
		assertEquals("front plot depth", frontPlotDepth, houseCaseDTO.getFrontPlotDepth(),0d);
		assertEquals("front plot width", frontPlotWidth, houseCaseDTO.getFrontPlotWidth(),0d);
		assertEquals("back plot depth", backPlotDepth, houseCaseDTO.getBackPlotDepth(),0d);
		assertEquals("back plot width", backPlotWidth, houseCaseDTO.getBackPlotWidth(),0d);
	}
	
	public static final void testConstrustructionData(final IHouseCaseDTO houseCaseDTO, 
			final BuiltFormType builtFormType, final boolean hasDraftLobby, final DTOFloorConstructionType grndFloorConstructionType){
		assertEquals("built form type", builtFormType, houseCaseDTO.getBuiltFormType());
		assertEquals("draft lobby", hasDraftLobby,houseCaseDTO.isHasDraftLoby());
		assertEquals("grnd floor type", grndFloorConstructionType,houseCaseDTO.getFloorConstructionType());
	}
	
	public static final void testBuildWeightData(final IHouseCaseDTO houseCaseDTO, final int expectedHouseHoldWeight, final int expectedDwellingWeight){
		assertEquals("house hold weight", expectedHouseHoldWeight,houseCaseDTO.getHouseholdCaseWeight(), 0.01);
		assertEquals("dwelling weight", expectedDwellingWeight,houseCaseDTO.getDwellingCaseWeight(), 0.01);
	}
	
	public static final void testLivingRoomData(final IHouseCaseDTO houseCaseDto,
			final double expectedLivingAreaFraction,
			final int expectedNumHabitalRooms,
			final int expectedNumOfBedrooms){
		assertEquals("Living area", expectedLivingAreaFraction,houseCaseDto.getLivingAreaFaction(),0d);
		assertEquals("Habital rooms", expectedNumHabitalRooms,houseCaseDto.getNumOfHabitalRooms());
		assertEquals("Number of bedrooms", expectedNumOfBedrooms,houseCaseDto.getNumberOfBedrooms());
	}
}
