package uk.org.cse.stockimport.hom.impl.steps;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.stockimport.domain.geometry.IElevationDTO;

/**
 * ElevationBuilderStepTest.
 *
 * @author richardt
 * @version $Id: ElevationBuilderStepTest.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class ElevationBuildStepTest {

    private ElevationBuildStep step;

    @Before
    public void initaliseTests() {
        step = new ElevationBuildStep();
    }

    @Test
    public void testBuildGlazingCreatesCorrectProportionOfDoubleGlazedWindowsIf100PercentDoubleGlazed()
        throws Exception {
        final FrameType frameType = FrameType.Metal;

        final IElevationDTO elevationDTO = mock(IElevationDTO.class);
        when(elevationDTO.getPercentageWindowDblGlazed()).thenReturn(100d);
        when(elevationDTO.getDoubleGlazedWindowFrame()).thenReturn(Optional.fromNullable(frameType));

        final Elevation elevation = new Elevation();

        step.buildGlazing(elevationDTO, elevation);

        final List<Glazing> glazing = elevation.getGlazings();
        Assert.assertNotNull(glazing);
        Assert.assertEquals("Should only create one window", 1, glazing.size());

        final Glazing doubleGlazedWindow = glazing.get(0);
        Assert.assertEquals("Window proportion", 1d, doubleGlazedWindow.getGlazedProportion(), 0.01);
        Assert.assertEquals("Frame Type", frameType, doubleGlazedWindow.getFrameType());
    }

    @Test
    public void testBuildGlazingCreatesCorrectProportionOfDoubleGlazedWindowsIf50PercentDoubleGlazed()
        throws Exception {
        final FrameType doubleFrameType = FrameType.Metal;
        final FrameType singleFrameType = FrameType.uPVC;

        final IElevationDTO elevationDTO = mock(IElevationDTO.class);
        when(elevationDTO.getPercentageWindowDblGlazed()).thenReturn(50.00d);
        when(elevationDTO.getDoubleGlazedWindowFrame()).thenReturn(Optional.fromNullable(doubleFrameType));
        when(elevationDTO.getSingleGlazedWindowFrame()).thenReturn(Optional.of(singleFrameType));

        final Elevation elevation = new Elevation();

        step.buildGlazing(elevationDTO, elevation);

        final List<Glazing> glazing = elevation.getGlazings();
        Assert.assertNotNull(glazing);
        Assert.assertEquals("Should only create one window", 2, glazing.size());

        final Glazing singleGlazedWindow = glazing.get(0);
        Assert.assertEquals("Window proportion for single glazed", 0.5, singleGlazedWindow.getGlazedProportion(), 0.01);
        Assert.assertEquals("Frame Type for single glazed", singleFrameType, singleGlazedWindow.getFrameType());

        final Glazing doubleGlazedWindow = glazing.get(1);
        Assert.assertEquals("Window proportion for double glazed", 0.5, doubleGlazedWindow.getGlazedProportion(), 0.01);
        Assert.assertEquals("Frame Type for double glazed", doubleFrameType, doubleGlazedWindow.getFrameType());
    }

    @Test
    public void testBuildGlazingCreatesCorrectProportionOfSingleGlazedWindowsIf100PercentSingleGlazed()
        throws Exception {
        final FrameType frameType = FrameType.Metal;

        final IElevationDTO elevationDTO = mock(IElevationDTO.class);
        when(elevationDTO.getPercentageWindowDblGlazed()).thenReturn(0d);
        when(elevationDTO.getSingleGlazedWindowFrame()).thenReturn(Optional.of(frameType));

        final Elevation elevation = new Elevation();

        step.buildGlazing(elevationDTO, elevation);

        final List<Glazing> glazing = elevation.getGlazings();
        Assert.assertNotNull(glazing);
        Assert.assertEquals("Should only create one window", 1, glazing.size());

        final Glazing singleGlazedWindow = glazing.get(0);
        Assert.assertEquals("Window proportion", 1d, singleGlazedWindow.getGlazedProportion(), 0.01);
        Assert.assertEquals("Frame Type", frameType, singleGlazedWindow.getFrameType());
    }
}
