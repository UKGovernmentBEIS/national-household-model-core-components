package uk.org.cse.stockimport.ehcs2010.spss.builders;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.stockimport.domain.geometry.IElevationDTO;
import uk.org.cse.stockimport.domain.geometry.impl.ElevationDTO;

/**
 * ElevationElementBuilderTest.
 *
 * @author richardt
 * @version $Id: ElevationElementBuilderTest.java 94 2010-09-30 15:39:21Z
 * richardt
 * @since 0.0.1-SNAPSHOT
 */
public class ElevationElementBuilderTest {

    private ElevationElementBuilder elevationElementBuilder;

    /**
     * TODO.
     *
     * @throws java.lang.Exception
     * @since 0.0.1-SNAPSHOT
     */
    @Before
    public void setUp() throws Exception {
        elevationElementBuilder = new ElevationElementBuilder();
    }

    /**
     * Test method for
     * {@link uk.org.cse.stockimport.ehcs2010.spss.builders.ElevationElementBuilder#buildRow(uk.org.cse.stockimport.domain.geometry.IElevationDTO)}.
     */
    @Test
    public void testBuildRowContatainCorrectDoorInformationForMetalSolid() {
        final IElevationDTO element = new ElevationDTO();
        element.setAacode("aacde");
        element.setNumberOfDoors(FrameType.Metal, 1);
        final List<String> headers = Arrays.asList(elevationElementBuilder.buildHeader(null));
        headers.indexOf("doorframe:metal,doortype:solid");

        final List<String> rows = Arrays.asList(elevationElementBuilder.buildRow(element));
        final String ct = rows.get(headers.indexOf("doorframe:metal,doortype:solid"));

        Assert.assertEquals("Incorrect value found in row", "1", ct);
    }
}
