package uk.org.cse.nhm.energycalculator.api.impl;

import org.junit.Assert;
import org.junit.Test;

public class SevenDayHeatingScheduleTest {

    @Test
    public void weightedAverageIsCorrectWhenMondayIsOnAndOthersAreOff() {
        final SevenDayHeatingSchedule sdhs = new SevenDayHeatingSchedule("bob",
                new DailyHeatingSchedule[]{
                    DailyHeatingSchedule.fromHours(0, 24),
                    new DailyHeatingSchedule(),
                    new DailyHeatingSchedule(),
                    new DailyHeatingSchedule(),
                    new DailyHeatingSchedule(),
                    new DailyHeatingSchedule(),
                    new DailyHeatingSchedule()
                });

        // so this should be 1/7 times whatever we query
        Assert.assertEquals(1 / 7d, sdhs.getMeanTemperature(1, 0, 0), 0.01);
    }

    @Test
    public void weightedAverageIsCorrectWhenMondayAndThuAreOn() {
        final SevenDayHeatingSchedule sdhs = new SevenDayHeatingSchedule("bob",
                new DailyHeatingSchedule[]{
                    DailyHeatingSchedule.fromHours(0, 24),
                    new DailyHeatingSchedule(),
                    DailyHeatingSchedule.fromHours(0, 24),
                    DailyHeatingSchedule.fromHours(0, 24),
                    new DailyHeatingSchedule(),
                    new DailyHeatingSchedule(),
                    new DailyHeatingSchedule()
                });

        Assert.assertEquals(3 / 7d, sdhs.getMeanTemperature(1, 0, 0), 0.01);
    }
}
