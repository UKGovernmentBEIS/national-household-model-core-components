package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IGlobals;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class HooksIntegrationTest extends SimulatorIntegrationTest {
    @Test
    public void repeatEventuallyTerminates() throws Exception {
        final IntegrationTestOutput out = super.runSimulation(dataService, loadScenario("hook/repeat.nhm"), true, Collections.<Class<?>>emptySet());
        final int ticks = out.state.getGlobals().getVariable("ticks", Number.class).get().intValue();
        System.out.println(ticks);
        Assert.assertTrue("At least one go round", ticks > 1);
        Assert.assertTrue("Not more than a few hundred odd " + ticks, ticks < 600);
    }

    @Test
    public void repeatRollbackWorks() throws Exception {
        final IntegrationTestOutput out = super.runSimulation(dataService, loadScenario("hook/repeat-rollback.nhm"), true, Collections.<Class<?>>emptySet());
        final int ticks = out.state.getGlobals().getVariable("ticks", Number.class).get().intValue();
        Assert.assertEquals(21, ticks);

        Assert.assertFalse(Iterables.size(out.dwellingsWithFlag("everyone")) ==
                           out.state.getDwellings().size());
    }
    
    @Test
    public void repeatRollsBackSeed() throws Exception {
        final IntegrationTestOutput out = super.runSimulation(
            dataService,
            loadScenario("hook/repeat-rollback-random.nhm"), true, Collections.<Class<?>>emptySet());

        double pinged = 0;
        double total = 0;
        int count = 0;
        
        for (final IDwelling d : out.state.getDwellings()) {
            count++;
            final IFlags flags = out.state.get(out.flags, d);
            final Optional<Double> val = flags.getRegister("pings");
            total += d.getWeight();
            if (val.isPresent()) {
                Assert.assertEquals("Everything should get 2 pings at once", 2d, val.get().doubleValue(), 0.001);
                pinged += d.getWeight();
            }
        }
        Assert.assertEquals("10% pinged " +pinged + " / " + total + " ("+count+")", 0.1, pinged / total, 0.05);
    }
    
    @Test
	public void simpleSummaryStats() throws NHMException, InterruptedException, IOException {
		final IntegrationTestOutput out = super.runSimulation(dataService, loadScenario("summarystats/summarystats.s"), true, Collections.<Class<?>>emptySet());
		
		final IGlobals g = out.state.getGlobals();
		final double mean = g.getVariable("mean", Number.class).get().doubleValue();
		final double median = g.getVariable("median", Number.class).get().doubleValue();
		
		/* assertion values checked by dumping the relevant table out and computing mean and median with R */
		Assert.assertEquals("The mean function is a bit below southwest", 6.4, mean, 0.1);
		Assert.assertEquals("The median function comes out southwest", 7.0, median, 0);
	}
	
	@Test
	public void changeHook() throws Exception {
		final IntegrationTestOutput out = super.runSimulation(dataService, loadScenario("hook/changehook.s"), true, Collections.<Class<?>>emptySet());
		
		double t = 0, c = 0;
		
		for (final IDwelling d : out.state.getDwellings()) {
			t += d.getWeight();
			if (out.state.get(out.flags, d).testFlag("world")) {
				c += d.getWeight();
			}
		}
		Assert.assertTrue(t / 10 <= c);
		
		Assert.assertEquals(
				out.state.getDwellings().size(),
				Iterables.size(out.dwellingsWithFlag("everyone")));
	}
	
	@Test
	public void applyActionUsingCount() throws Exception {
		final IntegrationTestOutput out = super.runSimulation(dataService, loadScenario("hook/applyhook2.s"), true, Collections.<Class<?>>emptySet());
		double baseCount = 0;
		double helloLondonCount = 0;
		
		for (final IDwelling d : out.state.getDwellings()) {
			if (out.state.get(out.basicAttributes, d).getRegionType().equals(RegionType.London)) {
				if (out.state.get(out.flags, d).testFlag("hello-london")) {
					helloLondonCount+= d.getWeight();
				}
				baseCount += d.getWeight();
			}
		}
		
		Assert.assertEquals(0.5, helloLondonCount / baseCount, 0.05);
	}
	@Test
	public void applyAction() throws Exception {
		final IntegrationTestOutput out = super.runSimulation(dataService, loadScenario("hook/applyhook.s"), true, Collections.<Class<?>>emptySet());
		
		double baseCount = 0;
		double helloCount = 0;
		double helloLondonCount = 0;
		
		for (final IDwelling d : out.state.getDwellings()) {
			if (out.state.get(out.basicAttributes, d).getRegionType().equals(RegionType.London)) {
				Assert.assertTrue(out.state.get(out.flags, d).testFlag("london"));
				if (out.state.get(out.flags, d).testFlag("hello-london")) {
					helloLondonCount+=d.getWeight();
				}
			}
			
			baseCount += d.getWeight();
			if (out.state.get(out.flags, d).testFlag("hello")) {
				helloCount += d.getWeight();
			}
		}
		
		Assert.assertEquals(4000, helloLondonCount, 400);
		Assert.assertEquals(0.1, helloCount / baseCount, 0.05);
	}

    
}
