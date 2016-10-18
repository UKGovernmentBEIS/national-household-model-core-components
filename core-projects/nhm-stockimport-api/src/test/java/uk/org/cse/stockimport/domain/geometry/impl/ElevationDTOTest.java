package uk.org.cse.stockimport.domain.geometry.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.stockimport.domain.geometry.IElevationDTO;

/**
 * ElevationDTOTest.
 *
 * @author richardt
 * @version $Id: ElevationDTOTest.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class ElevationDTOTest {

    private IElevationDTO elevationDTO;

    @Before
    public void initialiseTests() {
        elevationDTO = new ElevationDTO();
    }

    @Test
    public void testIncrementDoorIncrementsByOneOnly() throws Exception {
        elevationDTO.addDoors(FrameType.Metal, 1);
        elevationDTO.setNumberOfDoors(FrameType.Metal, 1);
        Assert.assertEquals("num of doors",
                elevationDTO.getNumberOfDoors(FrameType.Metal, ElevationDTO.DEFAULT_DOORTYPE), 1);

        elevationDTO.addDoors(FrameType.Metal, 1);
        Assert.assertEquals("num of doors",
                elevationDTO.getNumberOfDoors(FrameType.Metal, ElevationDTO.DEFAULT_DOORTYPE), 2);
    }
}
