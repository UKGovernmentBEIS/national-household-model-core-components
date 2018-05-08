package uk.org.cse.nhm.energycalculator.impl;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;

public class HumanVentilationSystemTest {

    @Test
    public void testHumanVentilationSystemInactive() {
        final HumanVentilationSystem hvs = new HumanVentilationSystem(DefaultConstants.INSTANCE);

        Assert.assertEquals(1d, hvs.getAirChangeRate(1d), 0.01);
        Assert.assertEquals(0.5d, hvs.getAirChangeRate(0d), 0.01);
        Assert.assertEquals(0.5 * (1 + 0.5 * 0.5), hvs.getAirChangeRate(0.5), 0.01);
    }
}
