package uk.org.cse.stockimport.hom.structure.impl;

import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.BackElevation_Centre;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.BackElevation_Left;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.BackElevation_Right;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.FrontElevation_Centre;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.FrontElevation_Left;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.FrontElevation_Right;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.LeftElevation_Back;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.LeftElevation_Centre;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.LeftElevation_Front;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.NoAdditionalPart;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.RightElevation_Back;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.RightElevation_Centre;
import static uk.org.cse.stockimport.domain.types.AddedFloorModulePosition.RightElevation_Front;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.structure.IMutableWall;
import uk.org.cse.nhm.hom.structure.impl.Storey;
import uk.org.cse.stockimport.domain.types.AddedFloorModulePosition;
import uk.org.cse.stockimport.util.FloorPoylgonBuilder;
import uk.org.cse.stockimport.util.Path;

/**
 * StoreyTest.
 *
 * @author richardt
 * @version $Id: StoreyTest.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class StoreyTest {

    private Storey storey;

    /**
     * TODO.
     * 
     * @throws java.lang.Exception
     * @since 0.0.1-SNAPSHOT
     */
    @Before
    public void setUp() throws Exception {
        storey = new Storey();
    }

    @Test
    public void assertPerimitedOfWallsCreatedWithCorrectWidth() {
        final Polygon storeyPolygon = FloorPoylgonBuilder.createFloorPolygon(2.0, 2.0, 0.0, 0.0,
                NoAdditionalPart).toSillyPolygon();
        storey.setPerimeter(storeyPolygon, FloorPoylgonBuilder.SCALING_FACTOR);

        for (final IMutableWall wall : storey.getWalls()) {
            Assert.assertEquals(2.0, wall.getLength(), 0.01);
        }
    }

    @Test
    public void testCorrectNumOfWallsAndElevationsCreatedForPatterns() {
        // Square house
        assertWallsCreatedCorrectly(NoAdditionalPart, 4, 1, 1, 1, 1);

        // Back Elevations
        assertWallsCreatedCorrectly(BackElevation_Left, 6, 2, 2, 1, 1);
        assertWallsCreatedCorrectly(BackElevation_Centre, 8, 3, 2, 1, 2);
        assertWallsCreatedCorrectly(BackElevation_Right, 6, 2, 1, 1, 2);

        // Front Elevations
        assertWallsCreatedCorrectly(FrontElevation_Left, 6, 1, 2, 2, 1);
        assertWallsCreatedCorrectly(FrontElevation_Centre, 8, 1, 2, 3, 2);
        assertWallsCreatedCorrectly(FrontElevation_Right, 6, 1, 1, 2, 2);

        // Right Elevations
        assertWallsCreatedCorrectly(RightElevation_Back, 6, 1, 2, 2, 1);
        assertWallsCreatedCorrectly(RightElevation_Centre, 8, 2, 3, 2, 1);
        assertWallsCreatedCorrectly(RightElevation_Front, 6, 2, 2, 1, 1);

        // Left Elevations
        assertWallsCreatedCorrectly(LeftElevation_Back, 6, 1, 1, 2, 2);
        assertWallsCreatedCorrectly(LeftElevation_Centre, 8, 2, 1, 2, 3);
        assertWallsCreatedCorrectly(LeftElevation_Front, 6, 2, 1, 1, 2);
    }

    /**
     * Helper test method, draws the floor as a jpg if there is an error in the expected geometry or elevations.
     * 
     * @param elevationPattern
     * @param expectedWalls
     * @param backElev - walls expected for back elevation
     * @param rghtElev - walls expected for right elevation
     * @param frntElev - walls expected for front elevation
     * @param lftElev - walls expected for left elevation
     * @since 0.0.1-SNAPSHOT
     */
    private void assertWallsCreatedCorrectly(final AddedFloorModulePosition elevationPattern, final int expectedWalls, final int backElev, final int rghtElev,
            final int frntElev, final int lftElev) {
        final double mainDepth = 2;
        final double mainWidth = 2;
        final double additionalDepth = 1;
        final double additionalWidth = 1;

        int backElevations = 0;
        int rightElevations = 0;
        int frontElevations = 0;
        int leftElevations = 0;

        final Polygon storeyPolygon = FloorPoylgonBuilder.createFloorPolygon(mainDepth, mainWidth, additionalDepth,
                additionalWidth, elevationPattern).toSillyPolygon();

        storey.setPerimeter(storeyPolygon);

        int numOfWalls = 0;
        for (final IMutableWall wall : storey.getWalls()) {
            switch (wall.getElevationType()) {
                case BACK:
                    backElevations++;
                    break;
                case RIGHT:
                    rightElevations++;
                    break;
                case FRONT:
                    frontElevations++;
                    break;
                case LEFT:
                    leftElevations++;
                    break;
                default:
                    break;
            }
            ++numOfWalls;
        }

        System.out.println("elevationPattern: " + elevationPattern);
        System.out.println("back elevations: " + backElevations);
        System.out.println("right elevations: " + rightElevations);
        System.out.println("front elevations: " + frontElevations);
        System.out.println("left elevations: " + leftElevations);
        System.out.println("=-=-=-=-=-=-=-=-");

        try {
            Assert.assertEquals(elevationPattern + ", num walls", expectedWalls, numOfWalls);
            Assert.assertEquals(elevationPattern + ", back elevations", backElev, backElevations);
            Assert.assertEquals(elevationPattern + ", right elevations", rghtElev, rightElevations);
            Assert.assertEquals(elevationPattern + ", front elevations", frntElev, frontElevations);
            Assert.assertEquals(elevationPattern + ", left Elevations", lftElev, leftElevations);
        } catch (final AssertionError e) {
            drawTheFloor(storeyPolygon, elevationPattern);
            throw (e);
        }
    }

    private void drawTheFloor(final Polygon polyFloor, final AddedFloorModulePosition elevationPattern) {
        // write as image
        final int width = 3000;
        final int height = 3000;
        final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g2 = bi.createGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, width, height);

        g2.setStroke(new BasicStroke(1.0f));
        g2.setPaint(Color.BLUE);

        g2.translate(1500, 1500);
        g2.draw(polyFloor);

        final File f = new File(Path.file("src", "test", "resources", "spss2010", "theImage_test_" + elevationPattern + "_.jpg"));
        try {
            ImageIO.write(bi, "png", f);
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }
}
