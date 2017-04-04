package uk.org.cse.nhm.energycalculator.impl;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.impl.SpecificHeatLosses;

public class SpecificHeatLossesTest {
	@Test
	public void testValues() {
		final SpecificHeatLosses shl = new SpecificHeatLosses(25, 35, 45, 55,0,0, 12);

		Assert.assertEquals(25d, shl.getSpecificHeatLoss(), 0.01);
		Assert.assertEquals(35d, shl.getInterzoneHeatLoss(), 0.01);
        Assert.assertEquals(25d / 55d, shl.getHeatLossParameter(), 0.01);
		Assert.assertEquals(45d, shl.getThermalMassParameter(), 0.01);
        Assert.assertEquals(12d, shl.getAirChangeRate(), 0.01);
	}
}
