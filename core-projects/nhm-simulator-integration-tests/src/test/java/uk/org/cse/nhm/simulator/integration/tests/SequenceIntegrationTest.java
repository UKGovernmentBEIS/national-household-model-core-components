package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class SequenceIntegrationTest extends SimulatorIntegrationTest {

    @Test
    public void multipleValueSet() throws Exception {
        final IntegrationTestOutput out = super.runSimulation(dataService, loadScenario("sequence/multiple-set.nhm"), true, Collections.<Class<?>>emptySet());

        for (final IDwelling d : out.state.getDwellings()) {
            final IFlags f = out.state.get(out.flags, d);
            Assert.assertEquals(1d, f.getRegister("A").get(), 0.01);
            Assert.assertEquals(1d, f.getRegister("B").get(), 0.01);
            Assert.assertEquals(2d, f.getRegister("C").get(), 0.01);

            Assert.assertEquals(55d, f.getRegister("D").get(), 0.01);
            Assert.assertEquals(1d, f.getRegister("E").get(), 0.01);
            Assert.assertEquals(1d, f.getRegister("F").get(), 0.01);
        }
    }

    @Test
    public void simpleSequenceHasExpectedBehaviour() throws NHMException, InterruptedException, IOException {
        final IntegrationTestOutput out = super.runSimulation(dataService, loadScenario("sequence/yield-flow.s"), true, Collections.<Class<?>>emptySet());
        for (final IDwelling d : out.state.getDwellings()) {
            final IFlags f = out.state.get(out.flags, d);

            if (!f.getRegister("output-1").isPresent()) {
                System.err.println("wat " + f);
            }
            final double output1 = f.getRegister("output-1").get();
            final double output2 = f.getRegister("output-2").get();
            final double output3 = f.getRegister("output-3").get();
            final double output4 = f.getRegister("output-4").get();
            final double output5 = f.getRegister("output-5").get();
            final double output6 = f.getRegister("output-6").get();

            Assert.assertEquals(88d, output1, 0);
            Assert.assertEquals(20d, output2, 0);
            Assert.assertEquals(0d, output3, 0);
            Assert.assertEquals(20d, output4, 0);
            Assert.assertEquals(20d, output5, 0);
            Assert.assertEquals(10d, output6, 0);
        }
    }
}
