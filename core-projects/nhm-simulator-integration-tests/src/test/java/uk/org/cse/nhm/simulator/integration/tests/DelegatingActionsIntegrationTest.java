package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class DelegatingActionsIntegrationTest extends SimulatorIntegrationTest {

    @Test
    public void delayedAction() throws NHMException, InterruptedException {
        final IntegrationTestOutput output = super.runSimulation(dataService, loadScenario("delegatingactions/delayedAction.s"), true, Collections.<Class<?>>emptySet());

        for (final IDwelling d : output.state.getDwellings()) {
            final IFlags flags = output.state.get(output.flags, d);
            Assert.assertTrue("all houses should have been flagged", flags.hasAllFlags(ImmutableSet.of("flagged")));
        }
    }
}
