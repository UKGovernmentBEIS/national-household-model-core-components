package uk.org.cse.nhm.simulator.state.dimensions.energy;

public class SAPOccupancy {
	private static final double FLOOR_AREA_THRESHOLD = 13.9;
	private static final double EXPONENTIAL_COEFFICIENT = 1.76;
	private static final double EXPONENTIAL_BIAS=-0.000349;
	private static final double LINEAR_COEFFICIENT = 0.0013;
	
	/**
	 * This is the equation defined in SAP 2012 worksheet step (42).
	 * @param floorArea area in m2
	 * @return SAP occupancy for given area (num people)
	 */
	public static double calculate(final double floorArea) { 
		if (floorArea > FLOOR_AREA_THRESHOLD) {
			final double delta = floorArea - FLOOR_AREA_THRESHOLD;
			return 1 +
					EXPONENTIAL_COEFFICIENT * (1 - Math.exp(EXPONENTIAL_BIAS * delta * delta)) +
					LINEAR_COEFFICIENT * delta;
		} else {
			return 1d;
		}
	}
}
