package uk.org.cse.nhm.simulator.integration.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.hom.structure.impl.Elevation;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;

public class GlazingIntegrationTest extends SimulatorIntegrationTest {

    @Test
    public void glazing() throws NHMException, InterruptedException, IOException {
        final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("insulation/glazing.s"), true, Collections.<Class<?>>emptySet());
        final Glazing measureGlazing = makeGlazing();

        List<SurveyCase> cases = dataService.getSurveyCases("stock", null);
        assertTrue("Dwellings > 0", cases.size() > 0);

        for (final IDwelling dwelling : output.dwellingsWithFlag("affected")) {
            StructureModel structureModel = output.state.get(output.structure, dwelling);
            for (Elevation e : structureModel.getElevations().values()) {
                List<Glazing> glazings = e.getGlazings();
                Assert.assertEquals("Should be one type of glazing", glazings.size(), 1);
                Assert.assertEquals("Installed glazing should match the measure", glazings.get(0), measureGlazing);
            }
        }
    }

    private Glazing makeGlazing() {
        Glazing g = new Glazing();
        g.setFrameFactor(0.9);
        g.setFrameType(FrameType.uPVC);
        g.setUValue(1.8);
        g.setGainsTransmissionFactor(0.59);
        g.setGlazedProportion(1d);
        g.setGlazingType(GlazingType.Double);
        g.setInsulationType(WindowInsulationType.LowEHardCoat);
        g.setLightTransmissionFactor(0.65);
        return g;
    }
}
