package uk.org.cse.stockimport.hom.impl.steps;

import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.__MISSING;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.base.Optional;

import junit.framework.Assert;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.energycalculator.api.types.WallType;
import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.stockimport.domain.geometry.IElevationDTO;
import uk.org.cse.stockimport.domain.geometry.IStoreyDTO;
import uk.org.cse.stockimport.domain.geometry.SimplePolygon;
import uk.org.cse.stockimport.domain.geometry.impl.ElevationDTO;
import uk.org.cse.stockimport.repository.IHouseCaseSources;
import uk.org.cse.stockimport.util.FloorPoylgonBuilder;

/**
 * StoreyBuilderStepTest.
 *
 * @author richardt
 * @version $Id: StoreyBuilderStepTest.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class StoreyBuildStepTest extends Mockito {

    private StoreyBuildStep step;
    private SimplePolygon floorPolygon;

    @Before
    public void initialiseTest() {
        step = new StoreyBuildStep();

        floorPolygon = FloorPoylgonBuilder.createFloorPolygon(
                2.0, 2.0,
                0.0, 0.0,
                __MISSING);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    public void testBuildBuildsForEachStoreyGivenToItFromADTOProvider() throws Exception {
        final SurveyCase model = mock(SurveyCase.class);
        final IHouseCaseSources dtoProvider = mock(IHouseCaseSources.class);
        final StructureModel structureModel = mock(StructureModel.class);

        final List<IStoreyDTO> storeys = new ArrayList<IStoreyDTO>();
        final IStoreyDTO basement = mock(IStoreyDTO.class);
        when(basement.getLocationType()).thenReturn(FloorLocationType.BASEMENT);
        when(basement.getStoreyHeight()).thenReturn(3.4);
        when(basement.getPolygon()).thenReturn(floorPolygon);
        storeys.add(basement);

        final List<IElevationDTO> elevations = new ArrayList<IElevationDTO>();
        final IElevationDTO frontElevation = new ElevationDTO();

        frontElevation.setElevationType(ElevationType.FRONT);
        frontElevation.setTenthsAttached(5);
        frontElevation.setTenthsOpening(5);
        frontElevation.setTenthsPartyWall(0);

        elevations.add(frontElevation);

        final IElevationDTO backElevation = new ElevationDTO();

        backElevation.setElevationType(ElevationType.BACK);
        backElevation.setTenthsAttached(5);
        backElevation.setTenthsOpening(5);
        backElevation.setTenthsPartyWall(0);
        elevations.add(backElevation);

        final IElevationDTO rightElevation = new ElevationDTO();
        rightElevation.setElevationType(ElevationType.RIGHT);
        rightElevation.setTenthsAttached(5);
        rightElevation.setTenthsOpening(5);
        rightElevation.setTenthsPartyWall(0);

        elevations.add(rightElevation);

        final IElevationDTO leftElevation = new ElevationDTO();

        leftElevation.setElevationType(ElevationType.LEFT);
        leftElevation.setTenthsAttached(5);
        leftElevation.setTenthsOpening(5);
        leftElevation.setTenthsPartyWall(0);

        elevations.add(leftElevation);

        when(model.getStructure()).thenReturn(structureModel);
        when(dtoProvider.getAll(IStoreyDTO.class)).thenReturn(storeys);
        when(dtoProvider.getAll(IElevationDTO.class)).thenReturn(elevations);

        // Execute Step
        step.build(model, dtoProvider);

        verify(dtoProvider).getAll(IStoreyDTO.class);

        // Verify story information is used
        verify(basement).getLocationType();
        verify(basement).getPolygon();
    }

    /*
     * Previously we were relying on the default-null behaviour of the mock to carry information
     * so this method is just to replace the default nulls with default absent.
     */
    private void whenInsulation(final IElevationDTO dto) {
    	when(dto.getCavityInsulation()).thenReturn(Optional.<Boolean>absent());
    	when(dto.getInternalInsulation()).thenReturn(Optional.<Boolean>absent());
    	when(dto.getExternalInsulation()).thenReturn(Optional.<Boolean>absent());
    }

    @Test
    public void testInsulationIsAppliedToWallForElevation() throws Exception {
    	final Storey storey = new Storey();
        storey.setPerimeter(FloorPoylgonBuilder.createFloorPolygon(9.8, 6.3, 0.00, 0.00, __MISSING).toSillyPolygon());

        final Iterable<IMutableWall> walls = storey.getWalls();
        final List<IElevationDTO> elevationDTOs = new ArrayList<IElevationDTO>();

        final IElevationDTO front = mock(IElevationDTO.class, "front");
        whenInsulation(front);
        when(front.getElevationType()).thenReturn(ElevationType.FRONT);
        when(front.getInternalInsulation()).thenReturn(Optional.of(true));
		when(front.getExternalWallConstructionType()).thenReturn(Optional.of(WallConstructionType.Cavity));
        elevationDTOs.add(front);

        final IElevationDTO back = mock(IElevationDTO.class, "back");
        whenInsulation(back);
        when(back.getElevationType()).thenReturn(ElevationType.BACK);
        when(back.getExternalInsulation()).thenReturn(Optional.of(true));
		when(back.getExternalWallConstructionType()).thenReturn(Optional.of(WallConstructionType.Cavity));
        elevationDTOs.add(back);

        final IElevationDTO left = mock(IElevationDTO.class, "left");
        whenInsulation(left);
        when(left.getElevationType()).thenReturn(ElevationType.LEFT);
        when(left.getCavityInsulation()).thenReturn(Optional.of(true));
        when(left.getExternalWallConstructionType()).thenReturn(Optional.of(WallConstructionType.Cavity));
        elevationDTOs.add(left);

        final IElevationDTO right = mock(IElevationDTO.class, "right");
        whenInsulation(right);
        when(right.getElevationType()).thenReturn(ElevationType.RIGHT);

		when(right.getExternalWallConstructionType()).thenReturn(Optional.of(WallConstructionType.Cavity));
        elevationDTOs.add(right);


        step.buildExternalWallConstructionType(walls, elevationDTOs);

        for (final IMutableWall wall : storey.getWalls()) {
        	switch (wall.getElevationType()) {
			case BACK:
				Assert.assertEquals(EnumSet.of(WallInsulationType.External), wall.getWallInsulationTypes());
				break;
			case FRONT:
				Assert.assertEquals(EnumSet.of(WallInsulationType.Internal), wall.getWallInsulationTypes());
				break;
			case LEFT:
				Assert.assertEquals(EnumSet.of(WallInsulationType.FilledCavity), wall.getWallInsulationTypes());
				break;
			case RIGHT:
				Assert.assertEquals(EnumSet.noneOf(WallInsulationType.class), wall.getWallInsulationTypes());
				break;
			default:
				break;
        	}
        }
    }

    @Test
    public void testCorrectAreaAttachedIsAssignedToWallForElevation() throws Exception {
        final Storey storey = new Storey();
        storey.setPerimeter(FloorPoylgonBuilder.createFloorPolygon(9.8, 6.3, 0.00, 0.00, __MISSING).toSillyPolygon());

        final List<IElevationDTO> elevationDTOs = new ArrayList<IElevationDTO>();

        final IElevationDTO front = mock(IElevationDTO.class, "front");
        when(front.getElevationType()).thenReturn(ElevationType.FRONT);
        when(front.getTenthsAttached()).thenReturn(5.00);
        elevationDTOs.add(front);

        final IElevationDTO back = mock(IElevationDTO.class, "back");
        when(back.getElevationType()).thenReturn(ElevationType.BACK);
        elevationDTOs.add(back);

        final IElevationDTO left = mock(IElevationDTO.class, "left");
        when(left.getElevationType()).thenReturn(ElevationType.LEFT);
        elevationDTOs.add(left);

        final IElevationDTO right = mock(IElevationDTO.class, "right");
        when(right.getElevationType()).thenReturn(ElevationType.RIGHT);
        elevationDTOs.add(right);

        for (final IMutableWall wall : storey.getWalls()) {
        	// Walls need to have a type for them to be split.
        	wall.setWallConstructionType(WallConstructionType.Cavity);
        }

        step.buildAttachedWalls(storey.getWalls(), elevationDTOs);

        int numPartyWalls = 0;
        for (final IMutableWall wall : storey.getWalls()) {
            if (wall.getWallConstructionType() != null &&
                    wall.getWallConstructionType().getWallType() == WallType.Party) {
                Assert.assertEquals("Incorrect party wall length", 3.15d * FloorPoylgonBuilder.SCALING_FACTOR,
                        wall.getLength());
                numPartyWalls++;
            }
        }
        Assert.assertEquals("Party wall not set", 1, numPartyWalls);
    }
}
