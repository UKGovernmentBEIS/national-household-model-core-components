package uk.org.cse.nhm.energycalculator.api.impl;

import java.util.ArrayList;
import java.util.Collections;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;

/**
 * A simple heating schedule based on periods where the system is switched on for a certain amount of time.
 * 
 * add heating periods using {@link #addHeatingPeriod(double, double)} 
 * 
 * @author hinton
 * @since 1.0.0
 */
public class DailyHeatingSchedule implements IHeatingSchedule {
	private static final double MINUTES_PER_DAY = 24 * 60;
	private final ArrayList<Time> times = new ArrayList<Time>();
	
	/**
	 * Create a daily heating schedule using a given set of times.
	 * 
	 * @since 1.0.0
	 * @param doubles start time, end time pairs
	 */
	public DailyHeatingSchedule(final double... doubles) {
		if (doubles.length % 2 != 0) {
			throw new RuntimeException("Daily heating schedule requires an equal number of inputs");
		}
		
		for (int i = 0; i<doubles.length; i+=2) {
			addHeatingPeriod(doubles[i], doubles[i+1]);
		}
	}
	
	private class Time implements Comparable<Time> {
		public final double from, to;

		public Time(final double from2, final double to2) {
			this.from = from2;
			this.to = to2;
		}

		@Override
		public int compareTo(final Time o) {
			if (from < o.from) {
				return -1;
			} else if (from > o.from) {
				return 1;
			} else {
				return 0;
			}
		}

		public double length() {
			return to - from;
		}
	}
	
	/**
	 * Enable the heating between the two given times, in minutes
	 * 
	 * @since 1.0.0
	 * @param from
	 * @param to
	 */
	public void addHeatingPeriod(final double from, final double to) {
		times.add(new Time(from, to));
		Collections.sort(times);
	}
	
	@Override
	public double getMeanTemperature(final double demandTemperature, final double backgroundTemperature, final double cutoffTime) {
		/*
		BEISDOC
		NAME: Temperature increase for heating period
		DESCRIPTION: This is the proportion of the demand temperature which should be mixed into the mean internal temperature for the zone.
		TYPE: formula
		UNIT: Dimensionless
		SAP: Table 9b
		BREDEM: 7M,7U
		DEPS: heating-schedule,cooling-time
		NOTES: SAP and BREDEM multiply in (demand temperature - background temperature) here, while we do it later for computational efficiency.
		NOTES: We've also rearranged the formula here for the same reasons. The end result is the same calculation.
		ID: temperature-increase-for-heated-period
		CODSIEB
		*/
		final double triangle = cutoffTime / 2;
		/**
		 * The area under the curve so far, normalized
		 */
		double auc = 0;
		
		// what we do here is loop through the times, and add to the auc
		// 1. the area under the bit where the heating is on
		// 2. the area in the bit where we are cooling down to the next time
		for (int i = 0; i<times.size(); i++) {
			final Time t = times.get(i);
			// this is the area under the bit where it's switched on
			auc += t.length();
			final Time next = times.get((i+1) % times.size());
			
			/**
			 * The time until the next heating period
			 */
			double offTime;
			
			if (t.to > next.from) {
				// if we've wrapped around, we need to add on to get to the next day
				offTime = (next.from + MINUTES_PER_DAY) - t.to;
			} else {
				// otherwise just subtract
				offTime = next.from - t.to;	
			}
			
			if (offTime >= cutoffTime) {
				// in this case there's enough time to relax all the way back to zero
				auc += triangle;
			} else {
				// here we have to scale off the end of the relaxing triangle; by similar
				// triangles we can just subtract the relevant proportion
				auc += triangle - (triangle * (cutoffTime - offTime) / cutoffTime);
			}
		}
		
		/*
		BEISDOC
		NAME: Weekday and weekend mean temperatures 
		DESCRIPTION: The mean internal temperatures for each zone during weekdays and weekends separately. Calculated by adding the temperature increases for heating periods to the background temperature.
		TYPE: formula 
		UNIT: ℃
		SAP: Table 9c (Steps 4 and 6)
		BREDEM: 7N,7O,7V,7W
		DEPS: background-temperatures,zone-1-demand-temperature,zone-2-demand-temperature,temperature-increase-for-heated-period
		NOTES: SAP and BREDEM do this in reverse, subtracting a temperature reduction from the demand temperature. This is an equivalent calculation.  
		ID: weekday-and-weekend-mean-temperatures
		CODSIEB
		*/
		// now we scale the AUC up by demand and background temperature, and then divide by the duration to get avg. degrees.
		return backgroundTemperature + (auc * (demandTemperature - backgroundTemperature)) / MINUTES_PER_DAY;
	}
	
	@Override
	public boolean isHeatingOn() {
		return !times.isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((times == null) ? 0 : times.hashCode());
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
		final DailyHeatingSchedule other = (DailyHeatingSchedule) obj;
		if (times == null) {
			if (other.times != null)
				return false;
		} else if (!times.equals(other.times))
			return false;
		return true;
	}
}
