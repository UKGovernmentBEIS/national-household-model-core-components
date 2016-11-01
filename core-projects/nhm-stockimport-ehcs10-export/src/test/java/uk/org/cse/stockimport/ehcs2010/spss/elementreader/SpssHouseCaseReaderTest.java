/**
 * 
 */
package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;

import uk.org.cse.nhm.ehcs10.derived.Dimensions_09Plus10Entry;
import uk.org.cse.nhm.ehcs10.derived.impl.Interview_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.derived.types.Enum129;
import uk.org.cse.nhm.ehcs10.derived.types.Enum24;
import uk.org.cse.nhm.ehcs10.derived.types.Enum76;
import uk.org.cse.nhm.ehcs10.physical.AroundEntry;
import uk.org.cse.nhm.ehcs10.physical.InteriorEntry;
import uk.org.cse.nhm.ehcs10.physical.IntroomsEntry;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1186;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1650;
import uk.org.cse.stockimport.domain.IHouseCaseDTO;
import uk.org.cse.stockimport.domain.impl.HouseCaseDTO;
import uk.org.cse.stockimport.domain.types.DTOFloorConstructionType;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRespository;

/**
 * @author glenns
 */
@RunWith(MockitoJUnitRunner.class)
public class SpssHouseCaseReaderTest extends Mockito {

    private SpssHouseCaseReader houseCaseReader;

    @Mock
    IHouseCaseDTO houseCaseDTO;

    @Mock
    private Optional<Interview_09Plus10EntryImpl> interviewOption;

    @Mock
    private Interview_09Plus10EntryImpl interview;

    @Mock
    IHouseCaseSourcesRepositoryFactory itrFactory;

    @Mock
    IHouseCaseSourcesRespository<Object> iteratorProvider;

	@SuppressWarnings("unchecked")
	@Before
    public void initialiseTests() {
    	when(itrFactory.build((Iterable<Class<?>>) any(), anyString())).thenReturn(iteratorProvider);
        houseCaseReader = new SpssHouseCaseReader("", itrFactory);
    }

    @Test
    public void numberOfAdultsShouldBeNumberOfPeopleMinusChildren() {
        when(interviewOption.get()).thenReturn(interview);
        when(interviewOption.isPresent()).thenReturn(true);
        when(interview.getNumberOfPersonsInTheHousehold()).thenReturn(4);
        when(interview.getNumberOfDependentChildrenInHousehold()).thenReturn(2);

        assertEquals("Number of adults calculated incorrectly", 2, houseCaseReader.getAdultOccupants(interviewOption));
    }

    @Test
    public void numberOfAdultsShouldNotBeNegative() {
        when(interviewOption.isPresent()).thenReturn(true);
        when(interviewOption.get()).thenReturn(interview);
        when(interview.getNumberOfPersonsInTheHousehold()).thenReturn(2);
        when(interview.getNumberOfDependentChildrenInHousehold()).thenReturn(4);

        assertEquals("Number of adults calculated as negative", 0, houseCaseReader.getAdultOccupants(interviewOption));
    }

    @Test
    public void doesNotApplyRegionShouldMapToNull() {
        assertEquals("DoesNotApply should map to null RegionType", null,
                houseCaseReader.governmentOfficeRegionToSAPRegion(Enum24.DoesNotApply));
    }

    @Test
    public void noAnswerRegionShouldMapToNull() {
        assertEquals("No Answer should map to null RegionType", null,
                houseCaseReader.governmentOfficeRegionToSAPRegion(Enum24.NoAnswer));
    }

    @Test
    public void missingDwellingTypeShouldMapToNull() {
        assertEquals("Missing dwelling type should map to null.",
                null,
                houseCaseReader.ehsDwellingTypeToBuiltFormType(Enum129.__MISSING));
    }

    @Test
    public void testBuildLivingRoomDataSetsLivingRoomFactionIfValuesForDepthAndWidthArePresent() throws Exception {
        final int numOfHabitalRooms = 0;
        final Double livingRoomWidth = 4.12;
        final Double livingRoomDepth = 3.25;
        final Double totalFloorArea = 1500.44;
        final double expectedLivingAreaFaction = (livingRoomWidth * livingRoomDepth) / totalFloorArea;

        final IHouseCaseDTO houseCaseDTO = new HouseCaseDTO();

        final InteriorEntry interior = mock(InteriorEntry.class);
        when(interior.getNumberHabitableRooms()).thenReturn(numOfHabitalRooms);
        when(interior.getLivingRoomWidth_Metres_()).thenReturn(livingRoomWidth);
        when(interior.getLivingRoomDepth_Metres_()).thenReturn(livingRoomDepth);

        final Dimensions_09Plus10Entry dimensions = mock(Dimensions_09Plus10Entry.class);
        when(dimensions.getTotalFloorArea()).thenReturn(totalFloorArea);

        houseCaseReader.buildLivingRoomData(houseCaseDTO, interior, dimensions);

        assertEquals("living area faction", expectedLivingAreaFaction, houseCaseDTO.getLivingAreaFaction(), 0.01);
        assertEquals("num of habital rooms", numOfHabitalRooms, houseCaseDTO.getNumOfHabitalRooms(), 0.01);
    }

    @Test
    public void testBuildLivingRoomDataSetsNumberOfHabitalRooms() throws Exception {
        final int numOfHabitalRooms = 4;
        final double expectedLivingAreaFaction = 0.00;
        final IHouseCaseDTO houseCaseDTO = new HouseCaseDTO();

        final InteriorEntry interior = mock(InteriorEntry.class);
        when(interior.getNumberHabitableRooms()).thenReturn(numOfHabitalRooms);

        final Dimensions_09Plus10Entry dimensions = mock(Dimensions_09Plus10Entry.class);

        houseCaseReader.buildLivingRoomData(houseCaseDTO, interior, dimensions);

        assertEquals("living area faction", expectedLivingAreaFaction, houseCaseDTO.getLivingAreaFaction(), 0.01);
        assertEquals("num of habital rooms", numOfHabitalRooms, houseCaseDTO.getNumOfHabitalRooms(), 0.01);
    }

    @Test
    public void testGetGroundFloorConstrutionTypeReturnedCorrectlyFromSpssData() throws Exception {
        final InteriorEntry interiorEntry = mock(InteriorEntry.class);
        final IntroomsEntry livingRoom = mock(IntroomsEntry.class);
        final IntroomsEntry kitchen = mock(IntroomsEntry.class);
        final List<IntroomsEntry> rooms = Arrays.asList(livingRoom, kitchen);

        when(interiorEntry.getKitchenLevel()).thenReturn("GG");
        when(kitchen.getRoom()).thenReturn(Enum1650.Kitchen);
        when(livingRoom.getRoom()).thenReturn(Enum1650.LivingRoom);
        when(interiorEntry.getLivingRoomLevel()).thenReturn("GG");

        // Test both solid floors then ground floor is solid
        when(kitchen.getFLOORS_SolidFloors()).thenReturn(Enum10.Yes);
        when(livingRoom.getFLOORS_SolidFloors()).thenReturn(Enum10.Yes);
        DTOFloorConstructionType floor = houseCaseReader.getGroundFloorConstrutionType(interiorEntry, rooms);
        assertEquals("when kitche+livingroom=ground,solid", DTOFloorConstructionType.Solid, floor);

        // Test Suspended returned if kitchen on ground floor and is suspended
        when(kitchen.getFLOORS_SolidFloors()).thenReturn(Enum10.No);
        when(livingRoom.getFLOORS_SolidFloors()).thenReturn(Enum10.Yes);
        floor = houseCaseReader.getGroundFloorConstrutionType(interiorEntry, rooms);
        assertEquals("when kitchen=ground,suspended", DTOFloorConstructionType.SuspendedTimber, floor);

        // Test Suspended returned if living room on ground floor and is suspended
        when(kitchen.getFLOORS_SolidFloors()).thenReturn(Enum10.Yes);
        when(livingRoom.getFLOORS_SolidFloors()).thenReturn(Enum10.No);
        floor = houseCaseReader.getGroundFloorConstrutionType(interiorEntry, rooms);
        assertEquals("when livingroom=ground,suspended", DTOFloorConstructionType.SuspendedTimber, floor);

        // Test if kitchen on ground floor + suspended but living room is not on ground that still suspended
        when(kitchen.getFLOORS_SolidFloors()).thenReturn(Enum10.No);
        when(interiorEntry.getLivingRoomLevel()).thenReturn("01");
        when(livingRoom.getFLOORS_SolidFloors()).thenReturn(Enum10.Yes);
        floor = houseCaseReader.getGroundFloorConstrutionType(interiorEntry, rooms);
        assertEquals("when kitchen=ground,suspended but livingroom=first,solid", DTOFloorConstructionType.SuspendedTimber,
                floor);

        // Test if living room on ground floor + suspended but kitchen room is not on ground that still suspended
        when(kitchen.getFLOORS_SolidFloors()).thenReturn(Enum10.Yes);
        when(interiorEntry.getKitchenLevel()).thenReturn("01");
        when(interiorEntry.getLivingRoomLevel()).thenReturn("GG");
        when(livingRoom.getFLOORS_SolidFloors()).thenReturn(Enum10.No);
        floor = houseCaseReader.getGroundFloorConstrutionType(interiorEntry, rooms);
        assertEquals("when livingroom=ground,suspended but kitchen=first,solid", DTOFloorConstructionType.SuspendedTimber,
                floor);

        // Test both suspended floors but on 1st floor then ground floor is solid
        when(kitchen.getFLOORS_SolidFloors()).thenReturn(Enum10.No);
        when(livingRoom.getFLOORS_SolidFloors()).thenReturn(Enum10.No);
        when(interiorEntry.getKitchenLevel()).thenReturn("01");
        when(interiorEntry.getLivingRoomLevel()).thenReturn("01");
        floor = houseCaseReader.getGroundFloorConstrutionType(interiorEntry, rooms);
        assertEquals("when kitche+livingroom=01,suspended", DTOFloorConstructionType.Solid, floor);
    }

    @Test
    public void testHasAccessToOutsideSpace() throws Exception {
        final AroundEntry aroundEntry = mock(AroundEntry.class);
        final IHouseCaseDTO houseCase = mock(IHouseCaseDTO.class);

        // Private + back plot
        when(aroundEntry.getTypeOfPlot()).thenReturn(Enum1186.PrivatePlot);
        when(houseCase.getBackPlotDepth()).thenReturn(1d);
        when(houseCase.getBackPlotWidth()).thenReturn(1d);

        assertEquals("when plot=private & backplot area > 0", true,
                houseCaseReader.hasAccessToOutsideSpace(aroundEntry, houseCase));

        // Shared + back plot
        when(aroundEntry.getTypeOfPlot()).thenReturn(Enum1186.SharedPlotOnly);
        when(houseCase.getBackPlotDepth()).thenReturn(1d);
        when(houseCase.getBackPlotWidth()).thenReturn(1d);

        assertEquals("when plot=shared & backplot area > 0", true,
                houseCaseReader.hasAccessToOutsideSpace(aroundEntry, houseCase));

        // Private + front plot
        when(aroundEntry.getTypeOfPlot()).thenReturn(Enum1186.PrivatePlot);
        when(houseCase.getBackPlotDepth()).thenReturn(0d);
        when(houseCase.getBackPlotWidth()).thenReturn(0d);
        when(houseCase.getFrontPlotDepth()).thenReturn(1d);
        when(houseCase.getFrontPlotWidth()).thenReturn(1d);

        assertEquals("when plot=private & frontplot area > 0", true,
                houseCaseReader.hasAccessToOutsideSpace(aroundEntry, houseCase));

        // Not front or back but is private
        when(aroundEntry.getTypeOfPlot()).thenReturn(Enum1186.PrivatePlot);
        when(houseCase.getBackPlotDepth()).thenReturn(0d);
        when(houseCase.getBackPlotWidth()).thenReturn(0d);
        when(houseCase.getFrontPlotDepth()).thenReturn(0d);
        when(houseCase.getFrontPlotWidth()).thenReturn(0d);

        assertEquals("when plot=private & frontplot area == 0", false,
                houseCaseReader.hasAccessToOutsideSpace(aroundEntry, houseCase));
    }
    
    @Test
	public void testGetOwnsPartOfRoofReturnsTrueForExpectedValues() throws Exception {
    	when(interviewOption.isPresent()).thenReturn(true);
    	when(interviewOption.get()).thenReturn(interview);
    	
    	when(interview.getTypeOfOwnership()).thenReturn(Enum76.FreeholderOfHouse);
    	assertTrue("ownType=FreeholderOfHouse",houseCaseReader.getOwnsPartOfRoof(interviewOption));
    	
    	when(interview.getTypeOfOwnership()).thenReturn(Enum76.LeaseholderOwningFHCollectively);
    	assertTrue("ownType=LeaseholderOwningFHCollectively",houseCaseReader.getOwnsPartOfRoof(interviewOption));
    	
    	when(interview.getTypeOfOwnership()).thenReturn(Enum76.LeaseholderOwningFHOfWholeBldg);
    	assertTrue("ownType=LeaseholderOwningFHOfWholeBldg",houseCaseReader.getOwnsPartOfRoof(interviewOption));
    	
    	when(interview.getTypeOfOwnership()).thenReturn(Enum76.Commonholder_PropertyBuiltAsCH_);
    	assertTrue("ownType=Commonholder_PropertyBuiltAsCH_",houseCaseReader.getOwnsPartOfRoof(interviewOption));
    	
    	when(interview.getTypeOfOwnership()).thenReturn(Enum76.Commonholder_PropertyConvertedToCH_);
    	assertTrue("ownType=Commonholder_PropertyConvertedToCH_",houseCaseReader.getOwnsPartOfRoof(interviewOption));
	}
    
    @Test
	public void testGetOwnsPartOfRoofReturnsFalseForExpectedValues() throws Exception {
    	when(interviewOption.isPresent()).thenReturn(true);
    	when(interviewOption.get()).thenReturn(interview);
    	    	
    	when(interview.getTypeOfOwnership()).thenReturn(Enum76.DoesNotApply);
    	assertFalse("ownType=DoesNotApply",houseCaseReader.getOwnsPartOfRoof(interviewOption));
    
    	when(interview.getTypeOfOwnership()).thenReturn(Enum76.NoAnswer);
    	assertFalse("ownType=NoAnswer",houseCaseReader.getOwnsPartOfRoof(interviewOption));
    	
    	when(interview.getTypeOfOwnership()).thenReturn(Enum76.Leaseholder_NoShareOfFH);
    	assertFalse("ownType=Leaseholder_NoShareOfFH",houseCaseReader.getOwnsPartOfRoof(interviewOption));
    	
    	when(interview.getTypeOfOwnership()).thenReturn(Enum76.FreeholderOfFlat_OwningFHOfFlatOnly);
    	assertFalse("ownType=FreeholderOfFlat_OwningFHOfFlatOnly",houseCaseReader.getOwnsPartOfRoof(interviewOption));
    	
    	when(interview.getTypeOfOwnership()).thenReturn(null);
    	assertFalse("ownType=null",houseCaseReader.getOwnsPartOfRoof(interviewOption));
    }
}
