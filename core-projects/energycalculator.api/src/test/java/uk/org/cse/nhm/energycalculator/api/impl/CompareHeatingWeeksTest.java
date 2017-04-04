package uk.org.cse.nhm.energycalculator.api.impl;

import org.junit.Assert;
import org.junit.Test;

public class CompareHeatingWeeksTest {
	@Test
	public void sevenDayHeatingScheduleEqualsWeeklyHeatingSchedule() {
		final DailyHeatingSchedule weekdays = DailyHeatingSchedule.fromHours(7,9,16,23);
		final DailyHeatingSchedule weekends = DailyHeatingSchedule.fromHours(7,23);

		final SevenDayHeatingSchedule sdhs = new SevenDayHeatingSchedule("test", new DailyHeatingSchedule[] {
				weekdays,
				weekdays,
				weekdays,
				weekdays,
				weekdays,
				weekends,
				weekends
		});

		final WeeklyHeatingSchedule whs = new WeeklyHeatingSchedule(weekdays, weekends);

		Assert.assertEquals(
				sdhs.getMeanTemperature(21, 18, 3),
				whs.getMeanTemperature(21, 18, 3),
				0.1
				);

		Assert.assertEquals(
				sdhs.getMeanTemperature(24, 18, 0),
				whs.getMeanTemperature(24, 18, 0),
				0.1
				);
	}
}
