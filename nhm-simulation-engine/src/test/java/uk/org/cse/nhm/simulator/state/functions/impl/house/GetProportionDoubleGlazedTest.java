package uk.org.cse.nhm.simulator.state.functions.impl.house;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.nhm.hom.BasicCaseAttributes;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.state.IDimension;

/**
 * GetProportionDoubleGlazedTest.
 *
 * @author richardTiffin
 */
@RunWith(MockitoJUnitRunner.class)
public class GetProportionDoubleGlazedTest {

    @Mock
    IDimension<StructureModel> structure;
    @Mock
    IDimension<BasicCaseAttributes> basicAttributes;
    @Mock
    ILogEntryHandler handler;

    private GetProportionOfDoubleGlazedWindows function;

    @Before
    public void initialiseTests() {
        function = new GetProportionOfDoubleGlazedWindows(structure, handler, basicAttributes);
    }

    @Test
    public void IfNoWindowsAreDoubleGlazedThenShouldReturnZero() throws Exception {
        Map<ElevationType, Elevation> elevations = buildAllElevationsWithGivenDoubleGlazedPercentage(0d);

        assertEquals(Double.valueOf(0), function.getProportionDoubleGlazed(elevations, "AACODE"));
    }

    @Test
    public void IfOneElevationIsAllDoubleGlazedReturnsCorrectDoubleGlazedProportion() {
        Map<ElevationType, Elevation> elevations = buildAllElevationsWithGivenDoubleGlazedPercentage(0d);
        elevations.put(ElevationType.FRONT, buildElevation(1d, 0d));

        assertEquals(Double.valueOf(0.25), function.getProportionDoubleGlazed(elevations, "AACODE"));
    }

    @Test
    public void IfAllElevationsAreDoubleGlazedReturnsCorrectDoubleGlazedProportion() {
        Map<ElevationType, Elevation> elevations = buildAllElevationsWithGivenDoubleGlazedPercentage(1d);

        assertEquals(Double.valueOf(1), function.getProportionDoubleGlazed(elevations, "AACODE"));
    }

    @Test
    public void IfAllElevationsAreHalfDoubleGlazedDoubleGlazedReturnsCorrectDoubleGlazedProportion() {
        Map<ElevationType, Elevation> elevations = buildAllElevationsWithGivenDoubleGlazedPercentage(0.5d);

        assertEquals(Double.valueOf(0.5), function.getProportionDoubleGlazed(elevations, "AACODE"));
    }

    @Test
    public void IfAllButThreeElevationsAreDoubleGlazedDoubleGlazedReturnsCorrectDoubleGlazedProportion() {
        Map<ElevationType, Elevation> elevations = buildAllElevationsWithGivenDoubleGlazedPercentage(1d);
        elevations.put(ElevationType.FRONT, buildElevation(0d, 0d));

        assertEquals(Double.valueOf(0.75), function.getProportionDoubleGlazed(elevations, "AACODE"));
    }

    @Test
    public void IfAGlazingIsTripleGlazedThisIsRecoredAgainstTheDoubleProportion() throws Exception {
        Map<ElevationType, Elevation> elevations = buildAllElevationsWithGivenDoubleGlazedPercentage(0d);
        elevations.put(ElevationType.FRONT, buildElevation(0d, 1d));

        assertEquals(Double.valueOf(0.25), function.getProportionDoubleGlazed(elevations, "AACODE"));
    }

    @Test
    public void LogIsGeneratedIfThereIsAmissingElevation() throws Exception {
        Map<ElevationType, Elevation> elevations = buildAllElevationsWithGivenDoubleGlazedPercentage(0d);
        elevations.remove(ElevationType.FRONT);

        function.getProportionDoubleGlazed(elevations, "AACODE");
        verify(handler, times(1)).acceptLogEntry(any(WarningLogEntry.class));
    }

    protected Map<ElevationType, Elevation> buildAllElevationsWithGivenDoubleGlazedPercentage(Double prcntgDblGlzd) {
        Map<ElevationType, Elevation> elevations = new HashMap<>();
        for (ElevationType elevation : ElevationType.values()) {
            elevations.put(elevation, buildElevation(prcntgDblGlzd, 0d));
        }

        return elevations;
    }

    protected Elevation buildElevation(Double prcntgDblGlzd, Double prcntgTrplGlzd) {
        Glazing singleGlazed = new Glazing(1 - prcntgDblGlzd, GlazingType.Single, FrameType.Metal);
        Glazing doubleGlazed = new Glazing(prcntgDblGlzd, GlazingType.Double, FrameType.Metal);
        Glazing tripledGlazed = new Glazing(prcntgTrplGlzd, GlazingType.Triple, FrameType.Metal);

        Elevation elevation = new Elevation();
        elevation.setGlazings(Arrays.asList(singleGlazed, doubleGlazed, tripledGlazed));

        return elevation;
    }
}
