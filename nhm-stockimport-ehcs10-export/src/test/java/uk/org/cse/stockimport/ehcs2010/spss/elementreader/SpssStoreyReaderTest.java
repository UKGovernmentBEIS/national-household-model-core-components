package uk.org.cse.stockimport.ehcs2010.spss.elementreader;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.impl.Dimensions_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.impl.Physical_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.physical.InteriorEntry;
import uk.org.cse.nhm.ehcs10.physical.ShapeEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.FlatdetsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.InteriorEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.ServicesEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.ShapeEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.old.types.Enum1677;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.stockimport.domain.geometry.IStoreyDTO;
import uk.org.cse.stockimport.domain.geometry.SimplePolygon;
import uk.org.cse.stockimport.domain.geometry.impl.StoreyDTO;
import uk.org.cse.stockimport.ehcs2010.spss.SpssFloorPolygonBuilder;
import uk.org.cse.stockimport.ehcs2010.spss.elementreader.SpssStoreyReader.BuiltStorey;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRespository;

/**
 * FloorFromSpssElementBuilderTest.
 * 
 * @author richardt
 * @version $Id: FloorFromSpssElementBuilderTest.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@RunWith(MockitoJUnitRunner.class)
public class SpssStoreyReaderTest extends Mockito {

    private SpssStoreyReader builder;
    private final double allowedDeviation = 0.001d;

    @Mock
    IHouseCaseSourcesRepositoryFactory itrFactory;

    @Mock
    IHouseCaseSourcesRespository<Object> iteratorProvider;

    @Before
    public void setUp() {
        final String executionId = "";

        when(itrFactory.build(ImmutableSet.<Class<?>> of(
                ShapeEntryImpl.class,
                Physical_09Plus10EntryImpl.class,
                Dimensions_09Plus10EntryImpl.class,
                FlatdetsEntryImpl.class,
                ServicesEntryImpl.class,
                InteriorEntryImpl.class), executionId)).thenReturn(iteratorProvider);

        builder = new SpssStoreyReader(executionId, itrFactory);
    }

    @Test
    public void testCalculateAverageCeilingHeight() throws Exception {
        final InteriorEntry interiorDetails = mock(InteriorEntry.class);
        when(interiorDetails.getKitchenCeilingHeight_Metres_()).thenReturn(1.74d);
        when(interiorDetails.getLivingRoomCeilingHeight_Metres_()).thenReturn(1.74d);
        when(interiorDetails.getBedroomCeilingHeight_Metres_()).thenReturn(1.74d);
        when(interiorDetails.getBathroomCeilingHeight_Metres_()).thenReturn(1.74d);

        final double averageHeight = builder.calculateAverageCeilingHeight(interiorDetails);
        assertEquals("Average ceiling height incorrect", averageHeight, 1.74d, allowedDeviation);
    }

    @Test
    public void testDimensionsOfBasementDerivedFromModule1() throws Exception {
        final double expectedDepth = 2d;
        final double expectedWidth = 2d;
        final String houseCaseRef = "TEST001";

        final List<BuiltStorey> floors = new ArrayList<BuiltStorey>();
        final ShapeEntry shapeEntry = buildDefaultShapeEntry();
        when(shapeEntry.getMAIN1STLEVEL_Depth()).thenReturn(expectedDepth);
        when(shapeEntry.getMAIN1STLEVEL_Width()).thenReturn(expectedWidth);
        when(shapeEntry.getMAIN1STLEVEL_Level()).thenReturn("BB");
        when(shapeEntry.getADDITIONAL1STLEVEL_Level()).thenReturn("88");

        builder.buildStoreysForHouse(floors, shapeEntry, houseCaseRef, 0, false, false);

        boolean basementCreated = false;
        for (final BuiltStorey floor : floors) {
            if (floor.dto.getLocationType().equals(FloorLocationType.BASEMENT)) {
                basementCreated = true;
                assertEquals("Area set", expectedDepth * expectedWidth, floor.area, allowedDeviation);
            }
        }

        assertTrue("Basement not created", basementCreated);
    }

    @Test
    public void testHigherFloorsCreated() throws Exception {
        final String houseCaseRef = "TEST001";
        final double expectedDepth = 1d;
        final double expectedWidth = 2d;
        final List<BuiltStorey> floors = new ArrayList<>();

        final ShapeEntry shapeEntry = buildDefaultShapeEntry();
        when(shapeEntry.getMAIN1STLEVEL_Depth()).thenReturn(expectedDepth);
        when(shapeEntry.getMAIN1STLEVEL_Width()).thenReturn(expectedWidth);
        when(shapeEntry.getMAIN1STLEVEL_Level()).thenReturn("00");
        when(shapeEntry.getADDITIONAL1STLEVEL_Level()).thenReturn("88");

        builder.buildStoreysForHouse(floors, shapeEntry, houseCaseRef, 4, false, false);

        assertEquals("Total floors incorrect", 4, floors.size());
        final List<IStoreyDTO> higherFloors = new ArrayList<IStoreyDTO>();
        for (final BuiltStorey floor : floors) {
            if (floor.dto.getLocationType().equals(FloorLocationType.HIGHER_FLOOR)) {
                higherFloors.add(floor.dto);
            }
        }

        assertEquals("Higher floor not added", 1, higherFloors.size());
    }

    private static BuiltStorey createSquareStorey(final double area, final FloorLocationType type) {
    	final StoreyDTO dto = new StoreyDTO();
    	dto.setAacode("");
    	dto.setLocationType(type);
    	
    	final double d = Math.sqrt(area);
    	
    	dto.setPolygon(SimplePolygon.builder().add(0, 0).add(d, 0).add(d, d).add(0,d).build());
    	
    	return new BuiltStorey(area, dto);
    }
    
    @Test
    public void testAdjustFloorDimensionseEnsuresTFAAcrossFloorsMatchesDerivedTFA() throws Exception {
        final List<BuiltStorey> floors = new ArrayList<BuiltStorey>();
        floors.add(createSquareStorey(4, FloorLocationType.BASEMENT));
        floors.add(createSquareStorey(16, FloorLocationType.BASEMENT));
        floors.add(createSquareStorey(4, FloorLocationType.BASEMENT));

        // actual area is 4 + 4 + 16 = 24
        
        // scale up by 2
        builder.adjustStoreyDimensions(floors, 24 * 4);
        Assert.assertEquals(16, floors.get(0).dto.getPolygon().area(), 0.001);
        Assert.assertEquals(64, floors.get(1).dto.getPolygon().area(), 0.001);
        Assert.assertEquals(16, floors.get(2).dto.getPolygon().area(), 0.001);
        
        // scale down again
        // our target area is now a lie, because the area of the built storey
        // will not match the area in the poly.
        builder.adjustStoreyDimensions(floors, 24 / 4);
        Assert.assertEquals(4, floors.get(0).dto.getPolygon().area(), 0.001);
        Assert.assertEquals(16, floors.get(1).dto.getPolygon().area(), 0.001);
        Assert.assertEquals(4, floors.get(2).dto.getPolygon().area(), 0.001);
    }

    @Test
    public void testRoomInRoofIsAddedAndHasHalfNextHighestFloorDimensions() throws Exception {
        final double expectedDepth = 2.23d;
        final double expectedWidth = 2.23d;

        final List<BuiltStorey> floors = new ArrayList<BuiltStorey>();

        final ShapeEntry shapeEntry = buildDefaultShapeEntry();
        when(shapeEntry.getMAIN1STLEVEL_Depth()).thenReturn(expectedDepth * 2);
        when(shapeEntry.getMAIN1STLEVEL_Width()).thenReturn(expectedWidth * 2);
        when(shapeEntry.getMAIN1STLEVEL_Level()).thenReturn("00");
        when(shapeEntry.getADDITIONAL1STLEVEL_Level()).thenReturn("88");

        builder.buildStoreysForHouse(floors, shapeEntry, "AACODE", 1, true, false);

        assertEquals("Room in roof not added", 2, floors.size());
        
        final IStoreyDTO roofInRoof = floors.get(1).dto;
        assertEquals("Roof Location incorrect", FloorLocationType.ROOM_IN_ROOF, roofInRoof.getLocationType());
        assertEquals("Height(length) not set correctly", floors.get(0).area / 2, floors.get(1).area,
                allowedDeviation);
    }

    @Test
    public void testFloorOrdering() throws Exception {
        final BuiltStorey f0 = createSquareStorey(0, FloorLocationType.BASEMENT);
        final BuiltStorey f1 = createSquareStorey(0, FloorLocationType.FIRST_FLOOR);
        final BuiltStorey f2 = createSquareStorey(0, FloorLocationType.SECOND_FLOOR);

        final List<BuiltStorey> floors = new ArrayList<>(ImmutableList.of(f1, f0, f2));
        Collections.shuffle(floors);
        Collections.sort(floors);
        
        assertEquals("Floor 0", FloorLocationType.BASEMENT, floors.get(0).dto.getLocationType());
        assertEquals("Floor 1", FloorLocationType.FIRST_FLOOR, floors.get(1).dto.getLocationType());
        assertEquals("Floor 2", FloorLocationType.SECOND_FLOOR, floors.get(2).dto.getLocationType());
    }

    @Test
    public void testCeilingHeightsIsLeftAsAverageHeightForLowestFloorOfHouse() throws Exception {
        final double averageHeight = 2.23d;

        final InteriorEntry interiorEntry = mock(InteriorEntry.class);
        when(interiorEntry.getLivingRoomCeilingHeight_Metres_()).thenReturn(averageHeight);
        when(interiorEntry.getKitchenCeilingHeight_Metres_()).thenReturn(averageHeight);
        when(interiorEntry.getBathroomCeilingHeight_Metres_()).thenReturn(averageHeight);
        when(interiorEntry.getBathroomCeilingHeight_Metres_()).thenReturn(averageHeight);

        // Test basement as lowest
        BuiltStorey basement = createSquareStorey(0, FloorLocationType.BASEMENT); 
        List<BuiltStorey> floors = Arrays.asList(basement);

        builder.adjustCeilingHeights(floors, interiorEntry);
        Assert.assertThat("Basement height not set correctly", basement.dto.getCeilingHeight(), equalTo(averageHeight));

        // Test ground as lowest
        BuiltStorey ground = createSquareStorey(0d, FloorLocationType.GROUND);
        floors = Arrays.asList(ground);

        builder.adjustCeilingHeights(floors, interiorEntry);
        Assert.assertThat("Ground height not set correctly as lowest floors", ground.dto.getCeilingHeight(),
                equalTo(averageHeight));

        // Test basement as lowest, ground should average plus additional
        basement = createSquareStorey(0d, FloorLocationType.BASEMENT);
        ground = createSquareStorey(0d, FloorLocationType.GROUND);
        floors = Arrays.asList(ground, basement);

        builder.adjustCeilingHeights(floors, interiorEntry);
        Assert.assertThat("Basement height not set correctly", basement.dto.getCeilingHeight(), equalTo(averageHeight));
        Assert.assertThat("Ground height not set correctly as main floor", ground.dto.getCeilingHeight(),
                equalTo(averageHeight));
    }

    @Test
    public void testRoomInRoofCeilingHeightIsSetToStandardValue() throws Exception {
        final double averageHeight = 2.23d;

        // Test basement as lowest
        final BuiltStorey basement = createSquareStorey(0d, FloorLocationType.BASEMENT);
        final BuiltStorey roomInRoof = createSquareStorey(0d, FloorLocationType.ROOM_IN_ROOF);
        final List<BuiltStorey> floors = Arrays.asList(basement, roomInRoof);

        final InteriorEntry interiorEntry = mock(InteriorEntry.class);
        when(interiorEntry.getLivingRoomCeilingHeight_Metres_()).thenReturn(averageHeight);
        when(interiorEntry.getKitchenCeilingHeight_Metres_()).thenReturn(averageHeight);
        when(interiorEntry.getBathroomCeilingHeight_Metres_()).thenReturn(averageHeight);
        when(interiorEntry.getBathroomCeilingHeight_Metres_()).thenReturn(averageHeight);

        builder.adjustCeilingHeights(floors, interiorEntry);
        Assert.assertThat("Basement height not set correctly", roomInRoof.dto.getCeilingHeight(),
                equalTo(SpssStoreyReader.roomInRoofStoryHeight));
    }

    @Test
    public void testCreateFloorAsPointsCreateCorrectGeometryForFloor() throws Exception {
        final double mainDepth = 2;
        final double mainWidth = 2;
        final double additionalDepth = 1;
        final double additionalWidth = 1;

        final SimplePolygon floor = SpssFloorPolygonBuilder.createFloorPolygon(mainDepth, mainWidth, additionalDepth,
                additionalWidth,
                Enum1677.LeftElevation_Front);

        assertEquals("IncorrectNumXPoints", 6, floor.size());
    }

    @Test
    public void testCorrectNumberOfStoreysAreCreatedForHouses() throws Exception {
        final List<BuiltStorey> stories = new ArrayList<>();
        int numFloorsAboveGround = 3;
        final String aacode = "";
        final ShapeEntry shapeEntry = mock(ShapeEntry.class);
        boolean hasRoomInRoof = true;

        // No basement, 3 floors, room in roof
        stories.removeAll(stories);
        numFloorsAboveGround = 3;
        hasRoomInRoof = true;
        when(shapeEntry.getMAIN1STLEVEL_Level()).thenReturn("GG");
        builder.buildStoreysForHouse(stories, shapeEntry, aacode, numFloorsAboveGround, hasRoomInRoof, false);
        Collections.sort(stories);
        assertEquals("Basement:false, RoomInRoof:true, floorsAbvGrn:3", 4, stories.size());
        assertEquals(FloorLocationType.GROUND, stories.get(0).dto.getLocationType());
        assertEquals(FloorLocationType.FIRST_FLOOR, stories.get(1).dto.getLocationType());
        assertEquals(FloorLocationType.SECOND_FLOOR, stories.get(2).dto.getLocationType());
        assertEquals(FloorLocationType.ROOM_IN_ROOF, stories.get(3).dto.getLocationType());

        // No basement, No Room in roof, 2 floors,
        stories.removeAll(stories);
        numFloorsAboveGround = 2;
        hasRoomInRoof = false;
        when(shapeEntry.getMAIN1STLEVEL_Level()).thenReturn("GG");
        builder.buildStoreysForHouse(stories, shapeEntry, aacode, numFloorsAboveGround, hasRoomInRoof, false);
        Collections.sort(stories);
        assertEquals("Basement:false, RoomInRoof:false, floorsAbvGrn:2", 2, stories.size());
        assertEquals(FloorLocationType.GROUND, stories.get(0).dto.getLocationType());
        assertEquals(FloorLocationType.FIRST_FLOOR, stories.get(1).dto.getLocationType());

        // basement, No Room in roof, 2 floors,
        stories.removeAll(stories);
        numFloorsAboveGround = 2;
        hasRoomInRoof = false;
        when(shapeEntry.getMAIN1STLEVEL_Level()).thenReturn("BB");
        builder.buildStoreysForHouse(stories, shapeEntry, aacode, numFloorsAboveGround, hasRoomInRoof, true);
        Collections.sort(stories);
        assertEquals("Basement:true, RoomInRoof:false, floorsAbvGrn:2", 3, stories.size());
        assertEquals(FloorLocationType.BASEMENT, stories.get(0).dto.getLocationType());
        assertEquals(FloorLocationType.GROUND, stories.get(1).dto.getLocationType());
        assertEquals(FloorLocationType.FIRST_FLOOR, stories.get(2).dto.getLocationType());

        // basement, room in roof, 2 floors
        stories.removeAll(stories);
        numFloorsAboveGround = 2;
        hasRoomInRoof = true;
        when(shapeEntry.getMAIN1STLEVEL_Level()).thenReturn("BB");
        builder.buildStoreysForHouse(stories, shapeEntry, aacode, numFloorsAboveGround, hasRoomInRoof, true);
        Collections.sort(stories);
        assertEquals("Basement:true, RoomInRoof:true, floorsAbvGrn:2", 4, stories.size());
        assertEquals(FloorLocationType.BASEMENT, stories.get(0).dto.getLocationType());
        assertEquals(FloorLocationType.GROUND, stories.get(1).dto.getLocationType());
        assertEquals(FloorLocationType.FIRST_FLOOR, stories.get(2).dto.getLocationType());
        assertEquals(FloorLocationType.ROOM_IN_ROOF, stories.get(3).dto.getLocationType());

        // no basement, no room in roof, 4 floors
        stories.removeAll(stories);
        numFloorsAboveGround = 4;
        hasRoomInRoof = false;
        when(shapeEntry.getMAIN1STLEVEL_Level()).thenReturn("GG");
        builder.buildStoreysForHouse(stories, shapeEntry, aacode, numFloorsAboveGround, hasRoomInRoof, false);
        Collections.sort(stories);
        assertEquals("Basement:false, RoomInRoof:false, floorsAbvGrn:3", 4, stories.size());
        assertEquals(FloorLocationType.GROUND, stories.get(0).dto.getLocationType());
        assertEquals(FloorLocationType.FIRST_FLOOR, stories.get(1).dto.getLocationType());
        assertEquals(FloorLocationType.SECOND_FLOOR, stories.get(2).dto.getLocationType());
        assertEquals(FloorLocationType.HIGHER_FLOOR, stories.get(3).dto.getLocationType());

        // basement, no room in roof, 4 floors
        stories.removeAll(stories);
        numFloorsAboveGround = 4;
        hasRoomInRoof = false;
        when(shapeEntry.getMAIN1STLEVEL_Level()).thenReturn("BB");
        builder.buildStoreysForHouse(stories, shapeEntry, aacode, numFloorsAboveGround, hasRoomInRoof, true);
        Collections.sort(stories);
        assertEquals("Basement:true, RoomInRoof:false, floorsAbvGrn:4", 5, stories.size());
        assertEquals(FloorLocationType.BASEMENT, stories.get(0).dto.getLocationType());
        assertEquals(FloorLocationType.GROUND, stories.get(1).dto.getLocationType());
        assertEquals(FloorLocationType.FIRST_FLOOR, stories.get(2).dto.getLocationType());
        assertEquals(FloorLocationType.SECOND_FLOOR, stories.get(3).dto.getLocationType());
        assertEquals(FloorLocationType.HIGHER_FLOOR, stories.get(4).dto.getLocationType());

        // basement, room in roof, 5 floors
        stories.removeAll(stories);
        numFloorsAboveGround = 4;
        hasRoomInRoof = true;
        when(shapeEntry.getMAIN1STLEVEL_Level()).thenReturn("BB");
        builder.buildStoreysForHouse(stories, shapeEntry, aacode, numFloorsAboveGround, hasRoomInRoof, true);
        Collections.sort(stories);
        assertEquals("Basement:true, RoomInRoof:true, floorsAbvGrn:5", 6, stories.size());
        assertEquals(FloorLocationType.BASEMENT, stories.get(0).dto.getLocationType());
        assertEquals(FloorLocationType.GROUND, stories.get(1).dto.getLocationType());
        assertEquals(FloorLocationType.FIRST_FLOOR, stories.get(2).dto.getLocationType());
        assertEquals(FloorLocationType.SECOND_FLOOR, stories.get(3).dto.getLocationType());
        assertEquals(FloorLocationType.HIGHER_FLOOR, stories.get(4).dto.getLocationType());
        assertEquals(FloorLocationType.ROOM_IN_ROOF, stories.get(5).dto.getLocationType());
    }

    private ShapeEntry buildDefaultShapeEntry() {
        final ShapeEntry shapeEntry = mock(ShapeEntry.class);
        when(shapeEntry.getMAIN1STLEVEL_Depth()).thenReturn(null);
        when(shapeEntry.getMAIN1STLEVEL_Width()).thenReturn(null);
        when(shapeEntry.getMAIN1STLEVEL_Level()).thenReturn(null);
        when(shapeEntry.getADDITIONAL1STLEVEL_Level()).thenReturn(null);

        when(shapeEntry.getMAIN2NDLEVEL_Depth()).thenReturn(null);
        when(shapeEntry.getMAIN2NDLEVEL_Width()).thenReturn(null);
        when(shapeEntry.getMAIN2NDLEVEL_Level()).thenReturn(null);
        when(shapeEntry.getADDITIONAL2NDLEVEL_Level()).thenReturn(null);

        when(shapeEntry.getMAIN3RDLEVEL_Depth()).thenReturn(null);
        when(shapeEntry.getMAIN3RDLEVEL_Width()).thenReturn(null);
        when(shapeEntry.getMAIN3RDLEVEL_Level()).thenReturn(null);
        when(shapeEntry.getADDITIONAL3RDLEVEL_Level()).thenReturn(null);

        when(shapeEntry.getMODULESHAPEAdditionalPart_Location()).thenReturn(Enum1677.Unknown);

        return shapeEntry;
    }
}
