package uk.org.cse.nhm.hom.components.fabric.types;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * FloorLocationTypeTest.
 *
 * @author richardt
 * @version $Id: FloorLocationTypeTest.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class FloorLocationTypeTest {

    /**
     * TODO.
     * 
     * @throws java.lang.Exception
     * @since 0.0.1-SNAPSHOT
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test method for {@link uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType#getNextLevel(uk.org.cse.nhm.hom.components.fabric.types.FloorLocationType)}.
     */
    @Test
    public void testGetNextLevelReturnsHigherIfCurrentLevelIs2ND() {
        Assert.assertEquals(FloorLocationType.HIGHER_FLOOR,
                FloorLocationType.getNextLevel(FloorLocationType.SECOND_FLOOR));
    }
}
