package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Test;

public class AssertionIntegrationTest extends SimulatorIntegrationTest {

    @Test
    public void passingAssertionsPass() throws Exception {
        runSimulation(dataService, loadScenario("assertions/passing.nhm"), true, Collections.<Class<?>>emptySet());
    }

    @Test(expected = RuntimeException.class)
    public void failingAssertionsFail() throws Exception {
        runSimulation(dataService, loadScenario("assertions/failing.nhm"), true, Collections.<Class<?>>emptySet());
    }

    @Test
    public void perHouseAssertionsWork() throws Exception {
        runSimulation(dataService, loadScenario("assertions/houses.nhm"), true, Collections.<Class<?>>emptySet());
    }

    @Test(expected = Exception.class)
    public void validatorFailsWhenNoHouses() throws Exception {
        runSimulation(dataService, loadScenario("assertions/no-houses.nhm"), true, Collections.<Class<?>>emptySet());
    }
}
