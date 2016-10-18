package uk.org.cse.nhm.energycalculator.api.impl;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.SeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.types.ZoneType;

import com.google.common.base.Optional;

public class ClimateParametersTest {
	@Test
	public void testInsolationAtAngle() {
		// insolation on the flat is unchanged.
		Assert.assertEquals(100d, getInsolation(0, 0, 0, 100), 0.01);
		Assert.assertEquals(100d, getInsolation(0, Math.PI, 0, 100), 0.01);
	}
	
	private double getInsolation(final double horizontal, final double vertical, final double declination, final double onPlane) {
		final SeasonalParameters p = new SeasonalParameters(
				0,declination,0,0,onPlane,0, null, null
				);
		
		return p.getSolarFlux(horizontal, vertical);
	}
	
	
	@Test
	public void testGetHeatingScheduleByZone() {
		final IHeatingSchedule schedule1 = mock(IHeatingSchedule.class);
		final IHeatingSchedule schedule2 = mock(IHeatingSchedule.class);
		
		final SeasonalParameters p = new SeasonalParameters(
				0,0,0, 0,0,0, schedule1, Optional.of(schedule2)
				);
		
		
		Assert.assertSame(schedule1, p.getHeatingSchedule(ZoneType.ZONE1));
		Assert.assertSame(schedule2, p.getHeatingSchedule(ZoneType.ZONE2));
		
		final SeasonalParameters p2 = new SeasonalParameters(
				0,0,0, 0,0,0, schedule1, Optional.<IHeatingSchedule>absent()
				);		
		
		Assert.assertSame(schedule1, p2.getHeatingSchedule(ZoneType.ZONE2));
	}
}
