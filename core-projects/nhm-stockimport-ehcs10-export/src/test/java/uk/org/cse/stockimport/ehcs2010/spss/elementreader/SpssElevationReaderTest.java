package uk.org.cse.stockimport.ehcs2010.spss.elementreader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ehcs10.derived.Physical_09Plus10Entry;
import uk.org.cse.nhm.ehcs10.derived.impl.Physical_09Plus10EntryImpl;
import uk.org.cse.nhm.ehcs10.derived.types.Enum103;
import uk.org.cse.nhm.ehcs10.derived.types.Enum124;
import uk.org.cse.nhm.ehcs10.derived.types.Enum135;
import uk.org.cse.nhm.ehcs10.derived.types.Enum146;
import uk.org.cse.nhm.ehcs10.physical.DoorsEntry;
import uk.org.cse.nhm.ehcs10.physical.ElevateEntry;
import uk.org.cse.nhm.ehcs10.physical.FlatdetsEntry;
import uk.org.cse.nhm.ehcs10.physical.impl.DoorsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.ElevateEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.impl.FlatdetsEntryImpl;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1289;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.stockimport.domain.geometry.IElevationDTO;
import uk.org.cse.stockimport.domain.geometry.impl.ElevationDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRepositoryFactory;
import uk.org.cse.stockimport.repository.IHouseCaseSourcesRespository;

/**
 * SpssElevationReaderTest.
 *
 * @author richardt
 * @version $Id: SpssElevationReaderTest.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
@RunWith(MockitoJUnitRunner.class)
public class SpssElevationReaderTest extends Mockito {

    private SpssElevationReader elevationReader;
    private final String executionId = "";

    @Mock
    IHouseCaseSourcesRepositoryFactory itrFactory;

    @Mock
    IHouseCaseSourcesRespository<Object> iteratorProvider;

    /**
     * TODO.
     *
     * @throws java.lang.Exception
     * @since 0.0.1-SNAPSHOT
     */
    @Before
    public void setUp() throws Exception {
        when(itrFactory.build(ImmutableSet.<Class<?>>of(
                Physical_09Plus10EntryImpl.class,
                ElevateEntryImpl.class,
                FlatdetsEntryImpl.class,
                DoorsEntryImpl.class), "")).thenReturn(iteratorProvider);

        elevationReader = new SpssElevationReader(executionId, itrFactory);
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.ehcs2010.spss.elementreader.SpssElevationReader#buildElevationsForHouse(java.lang.String)}.
     */
    @Ignore
    @Test
    public void testBuildElevationsForHouse() {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.ehcs2010.spss.elementreader.SpssElevationReader#addWindowConstructionData(uk.org.cse.stockimport.domain.geometry.IElevationDTO, uk.org.cse.nhm.ehcs10.derived.Physical_09Plus10Entry)}.
     */
    @Test
    public void testAddWindowConstructionData() {
        final Enum124 extentDoubleGlazing = Enum124.MoreThanHalf;
        final Enum135 extendDbleGlazz2 = Enum135._80_OrMoreDoubleGlazed;
        final Enum103 predominantTypeOfWindow = Enum103.Double_Glazed_Metal;
        final double doubleGlazingPercentage = elevationReader.calculateGlazingPercentage(extendDbleGlazz2,
                extentDoubleGlazing);

        final IElevationDTO dto = mock(IElevationDTO.class);

        final Physical_09Plus10Entry physicalProps = mock(Physical_09Plus10Entry.class);
        when(physicalProps.getExtentOfDoubleGlazing()).thenReturn(extentDoubleGlazing);
        when(physicalProps.getExtentOfDoubleGlazing_DBLGLAZ2()).thenReturn(extendDbleGlazz2);
        when(physicalProps.getPredominantTypeOfWindow()).thenReturn(predominantTypeOfWindow);

        // Execute test method
        elevationReader.addWindowConstructionData(dto, physicalProps);

        // Verify results
        verify(physicalProps).getExtentOfDoubleGlazing();
        verify(physicalProps).getExtentOfDoubleGlazing_DBLGLAZ2();
        verify(physicalProps).getPredominantTypeOfWindow();
        verify(dto).setPercentageWindowDblGlazed(doubleGlazingPercentage);
        verify(dto).setSingleGlazedWindowFrame(Optional.of(FrameType.Metal));
        verify(dto).setDoubleGlazedWindowFrame(Optional.of(FrameType.Metal));
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.ehcs2010.spss.elementreader.SpssElevationReader#calculateGlazingPercentage(uk.org.cse.nhm.ehcs10.derived.types.Enum135, uk.org.cse.nhm.ehcs10.derived.types.Enum124)}.
     */
    @Test
    public void testCalculateGlazingPercentage() {
        testGlazingPercentage(Enum124.EntireHouse, Enum135.__MISSING, 100.00);
        testGlazingPercentage(Enum124.MoreThanHalf, Enum135.__MISSING, 75.00);
        testGlazingPercentage(Enum124.LessThanHalf, Enum135.__MISSING, 25.00);
        testGlazingPercentage(Enum124.NoDoubleGlazing, Enum135.__MISSING, 0.00);

        testGlazingPercentage(Enum124.__MISSING, Enum135._80_OrMoreDoubleGlazed, 100.00);
        testGlazingPercentage(Enum124.__MISSING, Enum135.LessThan80_DoubleGlazed, 0.00);
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.ehcs2010.spss.elementreader.SpssElevationReader#addDoorConstructionData(uk.org.cse.stockimport.domain.geometry.IElevationDTO, java.util.List)}.
     */
    @Test
    public void testAddDoorConstructionDataBuildTheCorrectNumberOfDoorsForElevations() {
        final List<DoorsEntry> caseDoorEntries = new ArrayList<DoorsEntry>();

        final DoorsEntry metalDoors = mock(DoorsEntry.class);
        final int numMetalDoors = 2;
        when(metalDoors.getTypeOfDoor()).thenReturn(Enum1289.Metal);
        when(metalDoors.getFRONT_Number()).thenReturn(numMetalDoors);
        when(metalDoors.getBACK_Number()).thenReturn(numMetalDoors);

        caseDoorEntries.add(metalDoors);

        final DoorsEntry upvcDoors = mock(DoorsEntry.class);
        final int numUpvcDoors = 4;
        when(upvcDoors.getTypeOfDoor()).thenReturn(Enum1289.UPVC);
        when(upvcDoors.getFRONT_Number()).thenReturn(numUpvcDoors);
        when(upvcDoors.getBACK_Number()).thenReturn(numUpvcDoors);
        caseDoorEntries.add(upvcDoors);

        final DoorsEntry woodDoors = mock(DoorsEntry.class);
        final int numWoodDoors = 6;
        when(woodDoors.getTypeOfDoor()).thenReturn(Enum1289.Wood);
        when(woodDoors.getFRONT_Number()).thenReturn(numWoodDoors);
        when(woodDoors.getBACK_Number()).thenReturn(numWoodDoors);
        caseDoorEntries.add(woodDoors);

        // Test Front Elevation build
        final IElevationDTO frontElevatioDTO = mock(IElevationDTO.class);
        when(frontElevatioDTO.getElevationType()).thenReturn(ElevationType.FRONT);
        elevationReader.addDoorConstructionData(frontElevatioDTO, caseDoorEntries);

        verify(frontElevatioDTO).addDoors(SpssElevationReader.frameTypeConversion.get(Enum1289.Metal), numMetalDoors);
        verify(frontElevatioDTO).addDoors(SpssElevationReader.frameTypeConversion.get(Enum1289.UPVC), numUpvcDoors);
        verify(frontElevatioDTO).addDoors(SpssElevationReader.frameTypeConversion.get(Enum1289.Wood), numWoodDoors);

        // Test Back Elevation build
        final IElevationDTO backElevatioDTO = mock(IElevationDTO.class);
        when(backElevatioDTO.getElevationType()).thenReturn(ElevationType.BACK);
        elevationReader.addDoorConstructionData(backElevatioDTO, caseDoorEntries);
        verify(backElevatioDTO).addDoors(SpssElevationReader.frameTypeConversion.get(Enum1289.Metal), numMetalDoors);
        verify(backElevatioDTO).addDoors(SpssElevationReader.frameTypeConversion.get(Enum1289.UPVC), numUpvcDoors);
        verify(backElevatioDTO).addDoors(SpssElevationReader.frameTypeConversion.get(Enum1289.Wood), numWoodDoors);
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.ehcs2010.spss.elementreader.SpssElevationReader#addWallConstructionData(uk.org.cse.stockimport.domain.geometry.IElevationDTO, uk.org.cse.nhm.ehcs10.derived.types.Enum146, uk.org.cse.nhm.ehcs10.physical.ElevateEntry)}.
     */
    @Test
    public void testAddWallConstructionData() {
        final Enum146 predominentWallStructure = Enum146.MasonryCavity;
        final WallConstructionType wallConstructionType = SpssElevationReader.wallTypeConversion
                .get(predominentWallStructure);

        final IElevationDTO dto = mock(IElevationDTO.class);
        when(dto.getElevationType()).thenReturn(ElevationType.FRONT);

        final ElevateEntry elevateEntry = mock(ElevateEntry.class);

        elevationReader.addWallConstructionData(dto, predominentWallStructure, elevateEntry);

        verify(elevateEntry).getFRONTFACE_CavityWallInsulation();
        verify(elevateEntry).getFRONTFACE_ExternalInsulation();
        verify(dto).setExternalWallConstructionType(Optional.of(wallConstructionType));
        verify(dto).setInternalInsulation(Optional.<Boolean>absent());
        verify(dto).setExternalInsulation(Optional.of(false));
        verify(dto).setCavityInsulation(Optional.of(false));
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.ehcs2010.spss.elementreader.SpssElevationReader#buildExternalWall(uk.org.cse.stockimport.domain.geometry.IElevationDTO, uk.org.cse.nhm.ehcs10.derived.types.Enum146, uk.org.cse.nhm.ehcs10.physical.ElevateEntry)}.
     */
    @Test
    public void testBuildExternalWallGetsCorrectInsulationInformationFromElevateEntryForEachElevation() {
        final Enum146 predominentWallStructure = Enum146.MasonryCavity;
        final WallConstructionType wallConstructionType = SpssElevationReader.wallTypeConversion
                .get(predominentWallStructure);

        final IElevationDTO dto = mock(IElevationDTO.class);
        final ElevateEntry elevateEntry = mock(ElevateEntry.class);

        when(dto.getElevationType()).thenReturn(ElevationType.FRONT);
        elevationReader.buildExternalWall(dto, predominentWallStructure, elevateEntry);
        verify(elevateEntry).getFRONTFACE_CavityWallInsulation();
        verify(elevateEntry).getFRONTFACE_ExternalInsulation();

        when(dto.getElevationType()).thenReturn(ElevationType.BACK);
        elevationReader.buildExternalWall(dto, predominentWallStructure, elevateEntry);
        verify(elevateEntry).getBACKFACE_CavityWallInsulation();
        verify(elevateEntry).getBACKFACE_ExternalInsulation();

        when(dto.getElevationType()).thenReturn(ElevationType.LEFT);
        elevationReader.buildExternalWall(dto, predominentWallStructure, elevateEntry);
        verify(elevateEntry).getLEFTFACE_CavityWallInsulation();
        verify(elevateEntry).getLEFTFACE_ExternalInsulation();

        when(dto.getElevationType()).thenReturn(ElevationType.RIGHT);
        elevationReader.buildExternalWall(dto, predominentWallStructure, elevateEntry);
        verify(elevateEntry).getRIGHTFACE_CavityWallInsulation();
        verify(elevateEntry).getRIGHTFACE_ExternalInsulation();

        final InOrder inOrder = inOrder(dto);
        final IElevationDTO verify = inOrder.verify(dto, times(4));

        verify.setExternalWallConstructionType(Optional.of(wallConstructionType));
        verify.setInternalInsulation(Optional.<Boolean>absent());
        verify.setExternalInsulation(Optional.of(false));
        verify.setCavityInsulation(Optional.of(false));
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.ehcs2010.spss.elementreader.SpssElevationReader#addElevationData(uk.org.cse.stockimport.domain.geometry.IElevationDTO, uk.org.cse.nhm.hom.components.fabric.types.ElevationType, uk.org.cse.nhm.ehcs10.physical.ElevateEntry, uk.org.cse.nhm.ehcs10.physical.FlatdetsEntry, boolean)}.
     */
    @Test
    public void testAddElevationData() {
        final IElevationDTO dto = mock(IElevationDTO.class);
        final ElevationType elevationType = ElevationType.FRONT;
        final boolean isHouse = true;
        final ElevateEntry elevateEntry = mock(ElevateEntry.class);
        final FlatdetsEntry flatDetsEntry = mock(FlatdetsEntry.class);

        elevationReader.addElevationData(dto, elevationType, elevateEntry, flatDetsEntry, isHouse);
        verify(dto).setElevationType(elevationType);
        verify(dto).setTenthsAttached(0d);
        verify(dto).setTenthsOpening(0d);
        verify(dto).setTenthsPartyWall(0d);
    }

    @Test
    public void testAddElevationDataCorrectlySetsPartyWallAreaForHouses() throws Exception {
        final double expectedPartyWallTenths = 5.00;
        final double tenthsAttached = 5.00;

        final IElevationDTO dto = mock(IElevationDTO.class);
        final ElevationType elevationType = ElevationType.FRONT;
        final boolean isHouse = true;
        final ElevateEntry elevateEntry = mock(ElevateEntry.class);
        final FlatdetsEntry flatDetsEntry = mock(FlatdetsEntry.class);

        when(elevateEntry.getVIEWSFRONT_TenthsAttached()).thenReturn((int) tenthsAttached);

        elevationReader.addElevationData(dto, elevationType, elevateEntry, flatDetsEntry, isHouse);
        verify(dto).setElevationType(elevationType);
        verify(dto).setTenthsAttached(tenthsAttached);
        verify(dto).setTenthsOpening(0d);
        verify(dto).setTenthsPartyWall(expectedPartyWallTenths);
    }

    @Test
    public void testAddElevationDataCorrectlySetsAreaForFlat() {
        final IElevationDTO dto = new ElevationDTO();
        final ElevationType elevationType = ElevationType.FRONT;
        final boolean isHouse = false;
        final ElevateEntry elevateEntry = mock(ElevateEntry.class);
        final FlatdetsEntry flatDetsEntry = mock(FlatdetsEntry.class);

        // Test Elevation totally detached (all exposed to air)
        when(elevateEntry.getVIEWSFRONT_TenthsAttached()).thenReturn(0);
        when(flatDetsEntry.getTENTHSOFWALLEXPOSEDFrontWall_ToOtherFlats()).thenReturn(0);

        elevationReader.addElevationData(dto, elevationType, elevateEntry, flatDetsEntry, isHouse);
        assertEquals("elevation type", elevationType, dto.getElevationType());
        assertEquals("tenths attached", 0.00, dto.getTenthsAttached(), 0.00);
        assertEquals("tenths partyWall", 0.00, dto.getTenthsPartyWall(), 0.00);

        // Test Elevation totally attached
        when(elevateEntry.getVIEWSFRONT_TenthsAttached()).thenReturn(10);
        when(flatDetsEntry.getTENTHSOFWALLEXPOSEDFrontWall_ToOtherFlats()).thenReturn(10);
        elevationReader.addElevationData(dto, elevationType, elevateEntry, flatDetsEntry, isHouse);
        assertEquals("elevation type", elevationType, dto.getElevationType());
        assertEquals("tenths attached", 10.00, dto.getTenthsAttached(), 0.00);
        assertEquals("tenths partyWall", 10.00, dto.getTenthsPartyWall(), 0.00);
    }

    private final void testGlazingPercentage(final Enum124 doubleGlazingExtent1, final Enum135 doubleGlazingExtent2,
            final double expectedPercentage) {
        final double percentageGlazed = elevationReader.calculateGlazingPercentage(doubleGlazingExtent2, doubleGlazingExtent1);
        assertEquals(
                "Incorrect glazed percentagr for (" + doubleGlazingExtent1 + "," + doubleGlazingExtent2 + ")",
                expectedPercentage, percentageGlazed, 0.0);
    }
}
