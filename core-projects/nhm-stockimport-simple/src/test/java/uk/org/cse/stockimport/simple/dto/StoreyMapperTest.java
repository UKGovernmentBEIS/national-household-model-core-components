package uk.org.cse.stockimport.simple.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType;
import uk.org.cse.nhm.stockimport.simple.dto.MappableDTOReader;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.domain.geometry.IStoreyDTO;
import uk.org.cse.stockimport.domain.geometry.SimplePolygon;
import uk.org.cse.stockimport.domain.geometry.impl.StoreyDTO;

public class StoreyMapperTest extends AbsMapperTest {

    public final int maxPolyPoints = 4;
    public final String polyXpoints = "{0, 980, 980, 0}";
    public final String polyYpoints = "{0, 0, 630, 630}";
    public final FloorLocationType floorLocation = FloorLocationType.BASEMENT;

    final double expectedCeilingHeight = 2.4d;
    final double expectedDepth = 3.12d;
    final double expectedWidth = 2.13d;
    final double expectedStoreyHeight = 3.0d;

    @Before
    public void initiateTests() {

        fields()
                .add(IBasicDTO.AACODE, aacode)
                //Floor Polygon
                .add(IStoreyDTO.X_POLYPOINTS_FIELD, polyXpoints)
                .add(IStoreyDTO.Y_POLYPOINTS_FIELD, polyYpoints)
                .add(IStoreyDTO.NUM_POLYPOINTS_FIELD, String.valueOf(maxPolyPoints))
                //Floor Location
                .add(IStoreyDTO.TYPE_FIELD, floorLocation.toString())
                //Dimensions
                .add(IStoreyDTO.CEILINGHEIGHT_FIELD, String.valueOf(expectedCeilingHeight))
                .add(IStoreyDTO.STOREYHEIGHT_FIELD, String.valueOf(expectedStoreyHeight));
    }

    @Test
    public void testMapFieldSet() throws Exception {
        final IStoreyDTO returnedDTO = new MappableDTOReader<>(StoreyDTO.class).read(fieldSet);
        testBuildReferenceData(returnedDTO, aacode);
        testFloorPolygonData(polyXpoints, polyYpoints, maxPolyPoints, returnedDTO);
        testFloorTypeData(returnedDTO, floorLocation);
        testDimensionData(returnedDTO, expectedCeilingHeight, expectedDepth, expectedWidth, expectedStoreyHeight);
    }

    public static final void testFloorTypeData(final IStoreyDTO dto, final FloorLocationType floorLocation) {
        assertEquals("Incorrect location type", floorLocation, dto.getLocationType());
    }

    public static final void testFloorPolygonData(
            final String expectedXPoints, final String expectedYPoints, final int expectedNumPoints, final IStoreyDTO dto) {

        final SimplePolygon poly = dto.getPolygon();
        assertNotNull("No polygon created", poly);
        assertEquals("Incorrect number of points in polygon", expectedNumPoints, poly.size());
    }

    public static final void testDimensionData(
            final IStoreyDTO dto, final double expectedCeilingHeight,
            final double expectedDepth, final double expectedWidth, final double expectedStoreyHeight) {
        assertEquals("", expectedCeilingHeight, dto.getCeilingHeight(), 0d);
        assertEquals("", expectedStoreyHeight, dto.getStoreyHeight(), 0d);
    }
}
