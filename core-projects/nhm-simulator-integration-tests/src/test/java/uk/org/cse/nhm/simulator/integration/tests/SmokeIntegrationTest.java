package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.language.definition.XScenario;

public class SmokeIntegrationTest extends SimulatorIntegrationTest {

    @Test
    public void EC1() {
        runTest("EC1");
    }

    @Test
    public void loftInsulationMeasure() {
        runTest("testLoftInsulationMeasure");
    }

    @Test
    public void setLivingAreaFraction() {
        runTest("testSetLivingAreaFraction");
    }

    @Test
    public void zoneOneDemandTemperature() {
        runTest("testZoneOneDemandTemperature");
    }

    @Test
    public void heatingSchedule() {
        runTest("testHeatingSchedule");
    }

    @Test
    public void thresholdExternalTemperature() {
        runTest("testThresholdExternalTemperature");
    }

    private void runTest(final String s) {
        try {
            log.debug("Running \"smoke test\" {}", s);
            final XScenario scenario = loadScenario(String.format("smoke/%s.s", s));
            log.debug("Loaded scenario, whose name claims {}", scenario.getName());

            loggingService = reportservice.startReportingSession();
            runSimulation(dataService, scenario, true, Collections.<Class<?>>emptySet()).simulator.close();
            lastSimulator = null;
        } catch (final NHMException exception) {
            Assert.fail("Simulation exception in " + s + " - probably a bad assertion : " + exception.getMessage());
        } catch (final Exception exception) {
            exception.printStackTrace();
            Assert.fail("Exception caught whilst running " + s);
        }
    }
}
