package uk.org.cse.nhm.language.builder.action;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.SevenDayHeatingSchedule;
import uk.org.cse.nhm.language.definition.action.XHeatingScheduleAction.XHeatingDays;
import uk.org.cse.nhm.language.definition.action.XHeatingScheduleAction.XHeatingDays.HeatingInterval;
import uk.org.cse.nhm.language.definition.action.XHeatingScheduleAction.XHeatingDays.XDayType;

public class ActionAdapterTest {

    @Test
    public void testUnionDayScheduleAllDayOn() {
        final DailyHeatingSchedule dhs = new DailyHeatingSchedule();

        final HeatingInterval interval = new HeatingInterval();
        interval.setBetween(0);
        interval.setAnd(24);
        final List<HeatingInterval> heating = Collections.singletonList(interval);
        ActionAdapter.unionDaySchedule(heating, dhs);

        Assert.assertEquals(10d, dhs.getMeanTemperature(10d, 0d, 0), 0);
    }

    @Test
    public void testUnionDayScheduleTwoHalfDaysOn() {
        final DailyHeatingSchedule dhs = new DailyHeatingSchedule();

        final HeatingInterval interval = new HeatingInterval();
        interval.setBetween(0);
        interval.setAnd(12);

        final HeatingInterval two = new HeatingInterval();
        two.setBetween(12);
        two.setAnd(24);

        final List<HeatingInterval> heating = ImmutableList.of(interval, two);
        ActionAdapter.unionDaySchedule(heating, dhs);

        Assert.assertEquals(10d, dhs.getMeanTemperature(10d, 0d, 0), 0);
    }

    @Test
    public void testUnionDayScheduleOneHalfDaysOn() {
        final DailyHeatingSchedule dhs = new DailyHeatingSchedule();

        final HeatingInterval interval = new HeatingInterval();
        interval.setBetween(0);
        interval.setAnd(12);

        final List<HeatingInterval> heating = ImmutableList.of(interval);
        ActionAdapter.unionDaySchedule(heating, dhs);

        Assert.assertEquals(5d, dhs.getMeanTemperature(10d, 0d, 0), 0);
    }

    @Test
    public void testSapScheduleMatches() {
        final DailyHeatingSchedule weekdays = DailyHeatingSchedule.fromHours(7, 9, 16, 23);
        final DailyHeatingSchedule weekends = DailyHeatingSchedule.fromHours(7, 23);

        final SevenDayHeatingSchedule sdhs = new SevenDayHeatingSchedule("test", new DailyHeatingSchedule[]{
            weekdays,
            weekdays,
            weekdays,
            weekdays,
            weekdays,
            weekends,
            weekends
        });

        final XHeatingDays xWeekdays = new XHeatingDays();
        final XHeatingDays xWeekends = new XHeatingDays();
        xWeekdays.setOn(XDayType.Weekdays);
        final HeatingInterval morning = new HeatingInterval();
        morning.setBetween(7);
        morning.setAnd(9);
        final HeatingInterval evening = new HeatingInterval();
        evening.setBetween(16);
        evening.setAnd(23);
        xWeekdays.getHeating().addAll(ImmutableList.of(morning, evening));

        final HeatingInterval allDay = new HeatingInterval();
        evening.setBetween(7);
        evening.setAnd(23);

        xWeekends.setOn(XDayType.Weekends);
        xWeekdays.getHeating().add(allDay);

        final IHeatingSchedule schedule = ActionAdapter.buildHeatingSchedule(
                Name.of("test"),
                (ImmutableList.of(
                        xWeekdays,
                        xWeekends
                ))
        );

        Assert.assertEquals(
                sdhs.getMeanTemperature(21, 18, 3),
                schedule.getMeanTemperature(21, 18, 3),
                0.1
        );

    }
}
