package uk.org.cse.nhm.energycalculator.api.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;

public class WeeklyHeatingScheduleTest {
	@Test
	public void testAverageValue() {
		IHeatingSchedule weekdays = mock(IHeatingSchedule.class);
		IHeatingSchedule weekends = mock(IHeatingSchedule.class);
		final WeeklyHeatingSchedule whs = new WeeklyHeatingSchedule(weekdays, weekends);
		
		when(weekdays.getMeanTemperature(1, 2, 3)).thenReturn(10d);
		when(weekends.getMeanTemperature(1, 2, 3)).thenReturn(20d);
		
		double meanTemperature = whs.getMeanTemperature(1, 2, 3);
		Assert.assertEquals((10 * 5 + 20 * 2) / 7d, meanTemperature, 0);
	}
	
	@Test
	public void testIsOn() {
		IHeatingSchedule weekdays = mock(IHeatingSchedule.class);
		IHeatingSchedule weekends = mock(IHeatingSchedule.class);
		
		final WeeklyHeatingSchedule whs = new WeeklyHeatingSchedule(weekdays, weekends);
		when(weekdays.isHeatingOn()).thenReturn(false);
		when(weekends.isHeatingOn()).thenReturn(false);
		Assert.assertFalse(whs.isHeatingOn());
		when(weekdays.isHeatingOn()).thenReturn(true);
		when(weekends.isHeatingOn()).thenReturn(false);
		Assert.assertTrue(whs.isHeatingOn());
		when(weekdays.isHeatingOn()).thenReturn(false);
		when(weekends.isHeatingOn()).thenReturn(true);
		Assert.assertTrue(whs.isHeatingOn());
		when(weekdays.isHeatingOn()).thenReturn(true);
		when(weekends.isHeatingOn()).thenReturn(true);
		Assert.assertTrue(whs.isHeatingOn());
	}
}
