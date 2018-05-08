package uk.org.cse.nhm.simulator.integration.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.junit.Test;

import uk.org.cse.nhm.hom.emf.technologies.ILight;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;

public class ChangeProportionOfLightTypeTest extends SimulatorIntegrationTest {

    @Test
    public void testEneryUseDoesNotChangeIfHouseProportionsAreNotDifferentFromBeforeMeasure() throws Exception {
        final IntegrationTestOutput output = super.runSimulation(super.restrictHouseCases(dataService, "J0011101"),
                loadScenario("lighting/lightingProportionTest.s"), true, Collections.<Class<?>>emptySet());

        assertTrue("Dwellings > 0", output.state.getDwellings().size() > 0);
        for (final IDwelling d : output.state.getDwellings()) {
            ITechnologyModel tech = output.state.get(output.technology, d);

            EList<ILight> lights = tech.getLights();
            assertEquals("Should have 4 different types of light", 4, lights.size());

            //TODO: Should not be suitable if proportions don't sum to 1...
            lights.forEach(light -> {
                assertEquals(String.format("Light efficiency %s expected proportion:", light.getType()), 0.25, light.getProportion(), 0d);

            });

        }
    }
}
