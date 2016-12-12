package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;

/**
 * A heating schedule which takes the weighted average of two other heating schedules, 
 * the first being weekdays and the second being weekend days.
 * 
 * The intention is that this is used to combine two {@link DailyHeatingSchedule}s, which
 * handle the daily averages.
 * 
 * @author hinton
 *
 */
public class WeeklyHeatingSchedule implements IHeatingSchedule {
	private static final double WEEKEND_DAYS_PER_WEEK = 2.0;
	private static final double WEEKDAYS_PER_WEEK = 5.0;
	private static final double DAYS_PER_WEEK = WEEKEND_DAYS_PER_WEEK + WEEKDAYS_PER_WEEK;
	
	private final IHeatingSchedule weekdays, weekends;
	
	/**
	 * Make a new weekly schedule which has the given sub-schedules for weekdays and weekends
	 * 
	 * @param weekdays
	 * @param weekends
	 */
	public WeeklyHeatingSchedule(final IHeatingSchedule weekdays, final IHeatingSchedule weekends) {
		this.weekdays = weekdays;
		this.weekends = weekends;
	}
	
	@Override
	public double getMeanTemperature(final double demandTemperature,
			final double backgroundTemperature, final double cutoffTime) {
		/*
		BEISDOC
		NAME: Mean zonal temperatures
		DESCRIPTION: The zone 1 and zone 2 mean internal temperatures, accounting for the heating schedule.
		TYPE: formula 
		UNIT: ℃
		SAP: (87,90), Table 9c (Step 7)
		BREDEM: 7P, 7X
		DEPS: weekday-and-weekend-mean-temperatures 
		ID: mean-zonal-temperatures
		CODSIEB
		*/
		return (weekdays.getMeanTemperature(demandTemperature, backgroundTemperature, cutoffTime) * WEEKDAYS_PER_WEEK
				+ weekends.getMeanTemperature(demandTemperature, backgroundTemperature, cutoffTime) * WEEKEND_DAYS_PER_WEEK) / DAYS_PER_WEEK;
	}
	
	@Override
	public boolean isHeatingOn() {
		return weekdays.isHeatingOn() || weekends.isHeatingOn();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((weekdays == null) ? 0 : weekdays.hashCode());
		result = prime * result + ((weekends == null) ? 0 : weekends.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final WeeklyHeatingSchedule other = (WeeklyHeatingSchedule) obj;
		if (weekdays == null) {
			if (other.weekdays != null)
				return false;
		} else if (!weekdays.equals(other.weekdays))
			return false;
		if (weekends == null) {
			if (other.weekends != null)
				return false;
		} else if (!weekends.equals(other.weekends))
			return false;
		return true;
	}
}
