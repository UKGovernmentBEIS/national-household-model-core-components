package uk.org.cse.nhm.simulator.integration.tests;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class InOrderIntegrationTest extends SimulatorIntegrationTest {

    @Test
    public void inOrderWorks() throws Exception {
        final IntegrationTestOutput output = runSimulation(dataService, loadScenario("action/inorder.nhm"), true, Collections.<Class<?>>emptySet());

        double as = 0;
        double bs = 0;

        for (final IDwelling d : output.state.getDwellings()) {
            final IFlags f = output.state.get(output.flags, d);
            final Optional<Double> register = f.getRegister("obj");
            if (register.isPresent()) {
                if (f.hasAnyFlag(ImmutableSet.of("got-a"))) {
                    as += d.getWeight();
                    Assert.assertEquals(1, register.get().doubleValue(), 0.01d);
                } else if (f.hasAnyFlag(ImmutableSet.of("got-b"))) {
                    bs += d.getWeight();
                    Assert.assertEquals(2, register.get().doubleValue(), 0.01d);
                } else {
                    Assert.fail(f + " has not got either flag, but does have a value set for the objective");
                }
            }
        }

        Assert.assertTrue(as + " should nearly exceed 10k, " + bs + " should nearly exceed 5k"
                + " state values : "
                + output.state.getGlobals(),
                as <= 10000
                && bs <= 5000
                && (as + output.state.getGlobals().getVariable("A", Number.class).get().doubleValue()) == 10000
                && (bs + output.state.getGlobals().getVariable("B", Number.class).get().doubleValue()) == 5000);
    }
}
