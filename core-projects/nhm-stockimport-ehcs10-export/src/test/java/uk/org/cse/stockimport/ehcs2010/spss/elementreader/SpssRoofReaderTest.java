package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.Physical_09Plus10Entry;
import uk.org.cse.nhm.ehcs10.derived.impl.Physical_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.types.Enum110;
import uk.org.cse.nhm.ehcs10.derived.types.Enum131;
import uk.org.cse.nhm.ehcs10.derived.types.Enum136;
import uk.org.cse.nhm.ehcs10.physical.ServicesEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.ServicesEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1757;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.stockimport.domain.geometry.IRoofDTO;
import uk.org.cse.stockimport.domain.geometry.IStoreyDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRespository;

/**
 * SpssRoofReaderTest.
 *
 * @author richardt
 * @version $Id: SpssRoofReaderTest.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@RunWith(MockitoJUnitRunner.class)
public class SpssRoofReaderTest {
    private SpssRoofReader reader;
    
    @Mock
    IHouseCaseSourcesRepositoryFactory factory;
    
    @Mock
    IHouseCaseSourcesRespository<Object> provider;
    
    @Mock
    IHouseCaseSources<Object> providerIterable;

    @SuppressWarnings("unchecked")
	@Before
    public void setUp() {
    	
        //when(providerIterable.iterator()).thenReturn(Collections.<IHouseCaseElementProvider<Object>>singleton(provider).iterator());
		when(factory.build(any(Iterable.class), any(String.class))).thenReturn(provider);
        
        reader = new SpssRoofReader("62b1c66b-4f55-4ddb-aa86-4c9e546e8d41", 
        		factory);
    }
    
    /**
     * This test was constructed using the following unix pipeline to determine the distinct combinations of
     * the chm's roof type and the two survey variables typercov and typerstr
     * <code>sort < roof2.csv | uniq  | grep -v bork | sed -e 's/Pitched, slates or tiles/Pitched slates or tiles/'</code>
     */
    @Test
    public void testConstructionTypeMapping() {
		final Enum110[] typercov = { Enum110.Asphalt, Enum110.Asphalt, Enum110.Asphalt, Enum110.ClayTile, Enum110.ClayTile, Enum110.ClayTile, Enum110.ClayTile,
				Enum110.ClayTile, Enum110.ConcreteTile, Enum110.ConcreteTile, Enum110.ConcreteTile, Enum110.ConcreteTile, Enum110.ConcreteTile, Enum110.Felt,
				Enum110.Felt, Enum110.Felt, Enum110.Felt, Enum110.Glass_Metal_Laminate, Enum110.Glass_Metal_Laminate, Enum110.Glass_Metal_Laminate,
				Enum110.Glass_Metal_Laminate, Enum110.ManMadeSlate, Enum110.ManMadeSlate, Enum110.ManMadeSlate, Enum110.ManMadeSlate, Enum110.ManMadeSlate,
				Enum110.MixedTypes, Enum110.MixedTypes, Enum110.MixedTypes, Enum110.MixedTypes, Enum110.MixedTypes, Enum110.NaturalSlate_Stone_Shingle,
				Enum110.NaturalSlate_Stone_Shingle, Enum110.NaturalSlate_Stone_Shingle, Enum110.NaturalSlate_Stone_Shingle, Enum110.NaturalSlate_Stone_Shingle,
				Enum110.Thatch, Enum110.Thatch };

		final Enum131[] typerstr = { Enum131.Flat, Enum131.Mansard, Enum131.Pitched, Enum131.Chalet, Enum131.Flat, Enum131.Mansard, Enum131.MixedTypes,
				Enum131.Pitched, Enum131.Chalet, Enum131.Flat, Enum131.Mansard, Enum131.MixedTypes, Enum131.Pitched, Enum131.Chalet, Enum131.Flat,
				Enum131.MixedTypes, Enum131.Pitched, Enum131.Flat, Enum131.Mansard, Enum131.MixedTypes, Enum131.Pitched, Enum131.Chalet, Enum131.Flat,
				Enum131.Mansard, Enum131.MixedTypes, Enum131.Pitched, Enum131.Chalet, Enum131.Flat, Enum131.Mansard, Enum131.MixedTypes, Enum131.Pitched,
				Enum131.Chalet, Enum131.Flat, Enum131.Mansard, Enum131.MixedTypes, Enum131.Pitched, Enum131.Chalet, Enum131.Pitched };

		final RoofConstructionType[] output = { RoofConstructionType.Flat, RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles,
				RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.Flat, RoofConstructionType.PitchedSlateOrTiles,
				RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles,
				RoofConstructionType.Flat, RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles,
				RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.Flat,
				RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.Flat,
				RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles,
				RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.Flat, RoofConstructionType.PitchedSlateOrTiles,
				RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles,
				RoofConstructionType.Flat, RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles,
				RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.Flat,
				RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles, RoofConstructionType.PitchedSlateOrTiles,
				RoofConstructionType.Thatched, RoofConstructionType.Thatched };
		
		for (int i = 0; i<typercov.length; i++) {
			final Enum110 cov = typercov[i];
			final Enum131 str = typerstr[i];
			final RoofConstructionType out = output[i];
			Assert.assertEquals(String.format("{}, {} => {}", cov, str, out),
					out, reader.getRoofConstructionType(cov, str));
		}
    }
    
    @Test
    public void testDependencies() {
    	final Set<Class<?>> surveyEntryClasses = reader.getSurveyEntryClasses();
    	Assert.assertEquals("If you have added a requirement, add it to this test",
    			ImmutableSet.of(
    					IStoreyDTO.class,
    					ServicesEntryImpl.class,
    					Physical_09Plus10EntryImpl.class),
    					surveyEntryClasses);
    }
    
    @Test
    public void testInsulationPropertiesReadWithInsAndPitched() throws Exception {
    	final ServicesEntry services = mock(ServicesEntry.class);
		when(providerIterable.requireOne(ServicesEntry.class)).thenReturn(services);
    	final Physical_09Plus10Entry physical = mock(Physical_09Plus10Entry.class);
		when(providerIterable.requireOne(Physical_09Plus10Entry.class)).thenReturn(physical);
    	when(providerIterable.getAll(IStoreyDTO.class)).thenReturn(Collections.<IStoreyDTO>emptyList());
    	
    	when(services.getLoft_ApproxThickness()).thenReturn(Enum1757._250Mm);
    	when(physical.getPredominantTypeOfRoofCovering()).thenReturn(Enum110.ClayTile);
    	when(physical.getPredominantTypeOfRoofStucture()).thenReturn(Enum131.Pitched);
    	when(physical.getDwellingType_DWTYPE8X()).thenReturn(Enum136.ConvertedFlat);
    	
    	final List<IRoofDTO> read = reader.read(providerIterable);
    	
    	Assert.assertEquals("1 result should come out", 1, read.size());
    	
    	final IRoofDTO result = read.get(0);
    	
    	Assert.assertEquals("250mm of insulation please", 250d, result.getInsulationThickness().get(), 0);
    	Assert.assertEquals("Pitched roof", RoofConstructionType.PitchedSlateOrTiles,
    			result.getRoofType());
    }
    
    @Test
    public void testInsulationPropertiesReadWithNoInsAndThatch() throws Exception {
    	final ServicesEntry services = mock(ServicesEntry.class);
		when(providerIterable.requireOne(ServicesEntry.class)).thenReturn(services);
    	final Physical_09Plus10Entry physical = mock(Physical_09Plus10Entry.class);
		when(providerIterable.requireOne(Physical_09Plus10Entry.class)).thenReturn(physical);
    	when(providerIterable.getAll(IStoreyDTO.class)).thenReturn(Collections.<IStoreyDTO>emptyList());
    	
    	when(services.getLoft_ApproxThickness()).thenReturn(Enum1757.NoInsulation);
    	when(physical.getPredominantTypeOfRoofCovering()).thenReturn(Enum110.Thatch);
    	when(physical.getPredominantTypeOfRoofStucture()).thenReturn(Enum131.Pitched);
    	when(physical.getDwellingType_DWTYPE8X()).thenReturn(Enum136.ConvertedFlat);
    	
    	final List<IRoofDTO> read = reader.read(providerIterable);
    	
    	Assert.assertEquals("1 result should come out", 1, read.size());
    	
    	final IRoofDTO result = read.get(0);
    	
    	Assert.assertFalse("Insulation absent", result.getInsulationThickness().isPresent());
    	Assert.assertEquals("Thatched roof", RoofConstructionType.Thatched,
    			result.getRoofType());
    }

    // @Test
    // public void testCalculateFloorAreaSetsAreaToLargestFloorInHouseMinusRoomInRoof() throws Exception {
    // Physical_09Plus10Entry physicalProps = mock(Physical_09Plus10Entry.class);
    // when(physicalProps.getDwellingType_DWTYPE8X()).thenReturn(Enum136.DetachedHouse);
    //
    // IFloorDTO f0 = new FloorDTO("", 10, FloorLocationType.BASEMENT);
    // IFloorDTO f1 = new FloorDTO("", 20, FloorLocationType.FIRST_FLOOR);
    // IFloorDTO f2 = new FloorDTO("", 30, FloorLocationType.SECOND_FLOOR);
    // IFloorDTO f3 = new FloorDTO("", 15, FloorLocationType.ROOM_IN_ROOF);
    //
    // List<IFloorDTO> floors = Arrays.asList(f1, f0, f2, f3);
    //
    // IRoofDTO roof = new RoofDTO();
    // reader.calculateRoofArea(roof, floors, physicalProps);
    // Assert.assertThat("Roof area for house", roof.getArea(), equalTo(f2.getArea() - f3.getArea()));
    // }
    //
    // @Test
    // public void testRoofAreaIsZeroIfFlatIsNotTopFloorFlat() throws Exception {
    // Physical_09Plus10Entry physicalProps = mock(Physical_09Plus10Entry.class);
    // when(physicalProps.getDwellingType_DWTYPE8X()).thenReturn(Enum136.ConvertedFlat);
    //
    // IFloorDTO topFloor = new FloorDTO("", 15, FloorLocationType.HIGHER_FLOOR);
    // List<IFloorDTO> floors = Arrays.asList(topFloor);
    //
    // IRoofDTO roof = new RoofDTO();
    // reader.calculateRoofArea(roof, floors, physicalProps);
    // Assert.assertThat("Roof area for flat", roof.getArea(), equalTo(0.0));
    // }
    //
    // @Test
    // public void testCalculateFloorAreaSetsAreaToLargestTopFloorInTopFloorFlat() throws Exception {
    // Physical_09Plus10Entry physicalProps = mock(Physical_09Plus10Entry.class);
    // when(physicalProps.getDwellingType_DWTYPE8X()).thenReturn(Enum136.ConvertedFlat);
    //
    // IFloorDTO topFloor = new FloorDTO("", 15, FloorLocationType.TOP_FLOOR);
    // List<IFloorDTO> floors = Arrays.asList(topFloor);
    //
    // IRoofDTO roof = new RoofDTO();
    // reader.calculateRoofArea(roof, floors, physicalProps);
    // Assert.assertThat("Roof area for flat", roof.getArea(), equalTo(topFloor.getArea()));
    // }
}