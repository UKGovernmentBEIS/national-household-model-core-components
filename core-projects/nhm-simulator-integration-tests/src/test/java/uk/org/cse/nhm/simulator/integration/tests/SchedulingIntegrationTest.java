package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class SchedulingIntegrationTest extends SimulatorIntegrationTest {

    @Test
    public void testInitScheduler() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("scheduler/initschedulertest.s"), true, Collections.<Class<?>>emptySet());
        final Set<IDwelling> dwellings = output.state.getDwellings();
        for (final IDwelling dwelling : dwellings) {
            final IFlags iFlags = output.state.get(output.flags, dwelling);
            final boolean testFlag = iFlags.testFlag("scheduled-init-boom");
            Assert.assertTrue("scheduled-init-boom should be set for all", testFlag);
        }
    }
}
