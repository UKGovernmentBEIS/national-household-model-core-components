package uk.org.cse.nhm.energycalculator.api.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;

/**
 * @since 1.3.2
 */
public class SevenDayHeatingSchedule implements IHeatingSchedule {
	private final List<IHeatingSchedule> schedules = new ArrayList<IHeatingSchedule>();
	private final List<Integer> daysPerSchedule = new ArrayList<Integer>();
	private boolean isHeatingOn = false;
	private final String name;
	
	public SevenDayHeatingSchedule(final String name, final DailyHeatingSchedule[] days) {
		this.name = name;
		if (days.length != 7) throw new RuntimeException("Expecting an ISO standard 7-day week, not "+days.length);
		int daysLikeYesterday = 1;
		DailyHeatingSchedule previousDay = days[0];
		for (int i = 1; i< days.length; i++) {
			if (previousDay.equals(days[i])) {
				daysLikeYesterday++;
			} else {
				addSubSchedule(previousDay, daysLikeYesterday);
				daysLikeYesterday = 1;
			}
			
			previousDay = days[i];
		}
		
		addSubSchedule(previousDay, daysLikeYesterday);
	}

	private void addSubSchedule(final IHeatingSchedule s, final int weight) {
		schedules.add(s);
		daysPerSchedule.add(weight);
		isHeatingOn = isHeatingOn || s.isHeatingOn();
	}
	
	@Override
	public double getMeanTemperature(final double demandTemperature,
			final double backgroundTemperature, final double cutoffTime) {
		double weightedSum = 0;
		double totalWeight = 0;
		final Iterator<IHeatingSchedule> si = schedules.iterator();
		final Iterator<Integer> wi = daysPerSchedule.iterator();
		
		while (si.hasNext() && wi.hasNext()) {
			final int weightOfSi = wi.next();
			weightedSum += si.next().getMeanTemperature(demandTemperature, backgroundTemperature, cutoffTime)
					* weightOfSi;
			
			totalWeight += weightOfSi;
		}
		
		if (weightedSum == 0) return 0;
		else return weightedSum / totalWeight;
	}

	@Override
	public boolean isHeatingOn() {
		return isHeatingOn;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((daysPerSchedule == null) ? 0 : daysPerSchedule.hashCode());
		result = prime * result + (isHeatingOn ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((schedules == null) ? 0 : schedules.hashCode());
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
		final SevenDayHeatingSchedule other = (SevenDayHeatingSchedule) obj;
		if (daysPerSchedule == null) {
			if (other.daysPerSchedule != null)
				return false;
		} else if (!daysPerSchedule.equals(other.daysPerSchedule))
			return false;
		if (isHeatingOn != other.isHeatingOn)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (schedules == null) {
			if (other.schedules != null)
				return false;
		} else if (!schedules.equals(other.schedules))
			return false;
		return true;
	}
}
