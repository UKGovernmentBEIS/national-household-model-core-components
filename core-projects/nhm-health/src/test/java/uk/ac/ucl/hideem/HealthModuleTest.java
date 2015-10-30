package uk.ac.ucl.hideem;

import org.junit.Assert;
import org.junit.Test;

import uk.ac.ucl.hideem.Person.Sex;

import com.google.common.collect.ImmutableList;

public class HealthModuleTest {
    @Test
    public void deltaTemperatureIsSane() {
        final IHealthModule hm = new HealthModule();

        final double tmin = 14;
        final double tmax = 21;

        System.out.println("Rebate function:");
        System.out.println("base\t+100\t+500\t+1000");
        for (int i = 0; i<20; i++) {
            double ti = tmin + i * (tmax-tmin) / 20;
            System.out.println(String.format("%f\t%f\t%f\t%f",
                                             ti,
                                             hm.getRebateDeltaTemperature(ti, 100),
                                             hm.getRebateDeltaTemperature(ti, 500),
                                             hm.getRebateDeltaTemperature(ti, 1000)));
        }

        final double deltaTemperature = hm.getRebateDeltaTemperature(18, 1000);

        System.out.println("Deduced temperature/cost function:");
        for (int i = 0; i<HealthModule.CTC_COST.length; i++) {
            System.out.println(String.format("%f\t%f", HealthModule.CTC_TEMPERATURE[i], HealthModule.CTC_COST[HealthModule.CTC_COST.length - (i + 1)]));
        }

    }

    @Test
    public void testInterpolator() {
        double x = HealthModule.interpolate(0.5, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 0.5, 0);
        x = HealthModule.interpolate(0.1, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 0.1, 0);
        x = HealthModule.interpolate(0.9, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 0.9, 0);

        x = HealthModule.interpolate(0, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 0, 0);

        x = HealthModule.interpolate(1, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 1, 0);

        x = HealthModule.interpolate(2, 2, new double[]{0, 1}, new double[]{1, 0});
        Assert.assertEquals(x, 1, 0);
    }

	@Test
	public void healthImpactOfDoingNothingIsZero() {
		final IHealthModule hm = new HealthModule();
		
        final CumulativeHealthOutcome effect = hm.effectOf(
            CumulativeHealthOutcome.factory(10),
                18d, 18d, 10d, 10d,
                10d, 20d,
                BuiltForm.Type.Bungalow, 100d, BuiltForm.Region.London, 1, true, true, true, true,
                ImmutableList.of(new Person(40, Sex.MALE, true, 2)));
		
		System.out.println(effect);
		for (final Disease.Type d : Disease.Type.values()) {
			for (int i = 0; i<10; i++) {
                Assert.assertEquals("Impact should be 0 for " + d + " in year " + i, 0d, effect.cost(d, i), 0d);
			}
		}
	}
}
