package uk.org.cse.nhm.simulator.state.dimensions.energy;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class EnergyTestHelper {
	public static final DateTime START = new DateTime(0);
	public static final int FUTURE_YEARS = 4;
	public static final DateTime FUTURE = START.plusYears(FUTURE_YEARS);
	public static final int FAR_FUTURE_YEARS = 1000;
	public static final DateTime FAR_FUTURE = FUTURE.plusYears(FAR_FUTURE_YEARS);

	public static IPowerTable mockPowerTable(final double power) {
		return mockPowerTable((float) power);
	}
	
	public static IPowerTable mockPowerTable(final float power) {
		IPowerTable powerTable = mock(IPowerTable.class);
		when(powerTable.getPowerByFuel(any(FuelType.class))).thenReturn(power);
		return powerTable;
	}
}
