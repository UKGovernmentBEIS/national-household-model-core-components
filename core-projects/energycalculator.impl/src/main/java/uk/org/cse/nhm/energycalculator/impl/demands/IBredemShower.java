package uk.org.cse.nhm.energycalculator.impl.demands;

public interface IBredemShower {
	double hotWaterVolumePerShower();
	
	/**
	 * @param occupancy
	 * @return the number of showers taken per day by occupants of the dwelling
	 */
	double numShowers(final double occupancy);
}
