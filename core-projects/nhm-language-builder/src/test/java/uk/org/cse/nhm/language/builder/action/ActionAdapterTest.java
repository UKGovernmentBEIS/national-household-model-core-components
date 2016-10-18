package uk.org.cse.nhm.language.builder.action;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.language.definition.action.XHeatingScheduleAction.XHeatingDays.HeatingInterval;

public class ActionAdapterTest {
	@Test
	public void testUnionDayScheduleAllDayOn() {
		final DailyHeatingSchedule dhs = new DailyHeatingSchedule();
		
		HeatingInterval interval = new HeatingInterval();
		interval.setBetween(0);
		interval.setAnd(24);
		List<HeatingInterval> heating = Collections.singletonList(interval);
		ActionAdapter.unionDaySchedule(heating, dhs);
		
		Assert.assertEquals(10d, dhs.getMeanTemperature(10d, 0d, 0), 0);
	}
	
	@Test
	public void testUnionDayScheduleTwoHalfDaysOn() {
		final DailyHeatingSchedule dhs = new DailyHeatingSchedule();
		
		HeatingInterval interval = new HeatingInterval();
		interval.setBetween(0);
		interval.setAnd(12);
		
		HeatingInterval two = new HeatingInterval();
		two.setBetween(12);
		two.setAnd(24);
		
		List<HeatingInterval> heating = ImmutableList.of(interval, two);
		ActionAdapter.unionDaySchedule(heating, dhs);
		
		Assert.assertEquals(10d, dhs.getMeanTemperature(10d, 0d, 0), 0);
	}
	
	@Test
	public void testUnionDayScheduleOneHalfDaysOn() {
		final DailyHeatingSchedule dhs = new DailyHeatingSchedule();
		
		HeatingInterval interval = new HeatingInterval();
		interval.setBetween(0);
		interval.setAnd(12);
		
		
		List<HeatingInterval> heating = ImmutableList.of(interval);
		ActionAdapter.unionDaySchedule(heating, dhs);
		
		Assert.assertEquals(5d, dhs.getMeanTemperature(10d, 0d, 0), 0);
	}
}
