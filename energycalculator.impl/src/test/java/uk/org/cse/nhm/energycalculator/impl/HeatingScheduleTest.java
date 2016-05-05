package uk.org.cse.nhm.energycalculator.impl;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;

public class HeatingScheduleTest {
	@Test
	public void testHeatingSchedule() {
		final DailyHeatingSchedule schedule = new DailyHeatingSchedule();
		
		Assert.assertEquals("Mean temp = background with no heating on", 5.0, schedule.getMeanTemperature(10, 5, 10), 0.01);
	
		// half a day of heating
		schedule.addHeatingPeriod(0, 12 * 60);
		
		Assert.assertEquals("Mean temp = half", 5.0, schedule.getMeanTemperature(10, 0, 0), 0.01);
		
		Assert.assertEquals("Mean temp = 7.5 (cooldown for second half of schedule)", 7.5, schedule.getMeanTemperature(10, 0, 12 * 60), 0.01);
	}
}
