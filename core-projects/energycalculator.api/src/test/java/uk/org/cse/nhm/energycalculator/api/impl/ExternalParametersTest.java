package uk.org.cse.nhm.energycalculator.api.impl;

import org.junit.Assert;

import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.impl.ExternalParameters;

public class ExternalParametersTest {
	@Test
	public void testZoneTwoTemperatureSpecified() {
		final ExternalParameters p = new ExternalParameters();
		Assert.assertFalse(p.isZoneTwoDemandTemperatureSpecified());
		p.setZoneTwoDemandTemperature(1.0);
		Assert.assertTrue(p.isZoneTwoDemandTemperatureSpecified());
		Assert.assertEquals(1d, p.getZoneTwoDemandTemperature(), 0d);
		p.setZoneTwoDemandTemperature(null);
		Assert.assertFalse(p.isZoneTwoDemandTemperatureSpecified());
		try {
			p.getZoneTwoDemandTemperature();
		} catch (RuntimeException ex) {
			return;
		}
		Assert.fail("Expected exception!");
	}

}
