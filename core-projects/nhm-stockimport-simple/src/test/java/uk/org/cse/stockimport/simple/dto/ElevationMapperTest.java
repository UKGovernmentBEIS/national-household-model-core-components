package uk.org.cse.stockimport.simple.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.google.common.collect.Table;

import uk.org.cse.nhm.hom.components.fabric.types.DoorType;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.stockimport.simple.dto.MappableDTOReader;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.geometry.IElevationDTO;
import uk.org.cse.stockimport.domain.geometry.impl.ElevationDTO;

@RunWith(MockitoJUnitRunner.class)
public class ElevationMapperTest extends AbsMapperTest {

	final static WallConstructionType expectedWallConstructionType = WallConstructionType.Sandstone;
	final static Boolean isCavityInsulated = Boolean.FALSE;
	final static Boolean isExternallyInsulated = Boolean.TRUE;
	final static Boolean isInternallyInsulated = Boolean.FALSE;
	
	final static double percentageDblGlazed = 66.66d;
	final static FrameType dblGlzdFrame = FrameType.Metal;
	final static FrameType snglGlzdFrame = FrameType.Wood;
	
	final static ElevationType elevationType = ElevationType.FRONT;
	final static double tenthsAttached = 3d;
	final static double tenthsOpening = 5d;
	final static double tenthsPartyWall = 2d;
	
	@Before
	public void initiateTests(){
		
		fields()
			.add(IBasicDTO.AACODE, aacode)
			
			.add(IElevationDTO.NUM_METAL_GLAZED_DOOR, "1")
			.add(IElevationDTO.NUM_METAL_SOLID_DOOR, "1")
			.add(IElevationDTO.NUM_UPVC_GLAZED_DOOR, "1")
			.add(IElevationDTO.NUM_UPVC_SOLID_DOOR, "1")
			.add(IElevationDTO.NUM_WOOD_GLAZED_DOOR, "1")
			.add(IElevationDTO.NUM_WOOD_SOLID_DOOR, "1")
			
			//External Walls
			.add(IElevationDTO.EXTERNAL_WALL_TYPE_FIELD, expectedWallConstructionType.toString())
			.add(IElevationDTO.CAVITYINSULATED_FIELD, isCavityInsulated.toString())
			.add(IElevationDTO.EXTERNALINSULATED_FIELD, isExternallyInsulated.toString())
			.add(IElevationDTO.INTERNALINSULATED_FIELD, isInternallyInsulated.toString())
			                         
			//Windows                
			.add(IElevationDTO.PERCENTAGE_DBLGLZD_FIELD, String.valueOf(percentageDblGlazed))
			.add(IElevationDTO.FRAME_DBLGLZD_FIELD, dblGlzdFrame.toString())
			.add(IElevationDTO.FRAME_SNGLGLZD_FIELD, snglGlzdFrame.toString())
			                         
			//Elevation              
			.add(IElevationDTO.ELEVATIONTYPE_FIELD, elevationType.toString())
			.add(IElevationDTO.ATTACHED_FIELD, String.valueOf(tenthsAttached))
			.add(IElevationDTO.OPEN_FIELD, String.valueOf(tenthsOpening))
			.add(IElevationDTO.PARTY_FIELD, String.valueOf(tenthsPartyWall));
	}
	
	@Test
	public void testMappingOfWholeThing() throws Exception {
		final IElevationDTO dto = new MappableDTOReader<>(ElevationDTO.class).read(fieldSet); 
		
		
		testBuildReferenceData(dto, aacode);
		testDoorsData(dto, 1);
		testExternalWallData(dto, expectedWallConstructionType, isCavityInsulated, isExternallyInsulated, isInternallyInsulated);		
		testWindowData(dto, percentageDblGlazed, dblGlzdFrame, snglGlzdFrame);
		testElevationData(dto, elevationType, tenthsAttached, tenthsOpening, tenthsPartyWall);
		
		
	}
	
	@Test
	public void ifDoubleGlazedFrameIsNullIsSetToNullInDTO() throws Exception {
		fields().add(IElevationDTO.FRAME_DBLGLZD_FIELD, "");
		final IElevationDTO dto = new MappableDTOReader<>(ElevationDTO.class).read(fieldSet); 
		
		
		testWindowData(dto, percentageDblGlazed, null, snglGlzdFrame);
	}
	
	public static final void testDoorsData(final IElevationDTO elvationDTO, final int expectedNumOfDoorsForEachType) {
		final Table<FrameType, DoorType, Integer>  doors = elvationDTO.getDoors();
		assertNotNull("Doors not created", doors);
		
		for (final FrameType frameType : FrameType.values()){
			for (final DoorType doorType : DoorType.values()){
				assertEquals("Number of doors for door type:" + doorType, expectedNumOfDoorsForEachType, doors.get(frameType, doorType).intValue());
			}
		}
	}

	public static final void testExternalWallData(
			final IElevationDTO elvationDTO,
			final WallConstructionType external, final boolean cavityInstalled,
			final boolean externalInsulation, final boolean internallyInsulated) {
		assertEquals("Wall type", Optional.of(expectedWallConstructionType), elvationDTO.getExternalWallConstructionType());
		assertEquals("cavity insultation", Optional.of(cavityInstalled), elvationDTO.getCavityInsulation());
		assertEquals("external insulation", Optional.of(externalInsulation), elvationDTO.getExternalInsulation());
		assertEquals("internal insulation", Optional.of(internallyInsulated), elvationDTO.getInternalInsulation());
	}
	
	public static final void testWindowData(final IElevationDTO elvationDTO,
			final Double percentageDblGlazed, final FrameType dblGlzdFrame,
			final FrameType snglGlzdFrame) {
		assertEquals("Percentage double glazed", percentageDblGlazed, elvationDTO.getPercentageWindowDblGlazed(), 0d);
		assertEquals("Double glazed frame", Optional.fromNullable(dblGlzdFrame), elvationDTO.getDoubleGlazedWindowFrame());
		assertEquals("Single glazed frame", Optional.of(snglGlzdFrame), elvationDTO.getSingleGlazedWindowFrame());
	}
	
	public static final void testElevationData(final IElevationDTO elvationDTO,
			final ElevationType elevationType, final Double tenthsAttached,
			final Double tenthsOpening, final Double tenthsPartyWall) {

		assertEquals("Elevation Type", elevationType,  elvationDTO.getElevationType());
		assertEquals("Tenths attached", tenthsAttached,  elvationDTO.getTenthsAttached(), 0d);
		assertEquals("Tenths opening", tenthsOpening,  elvationDTO.getTenthsOpening(), 0d);
		assertEquals("Tenths party wall", tenthsPartyWall,  elvationDTO.getTenthsPartyWall(), 0d);
	}
}
