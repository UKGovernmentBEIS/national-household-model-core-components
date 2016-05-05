package org.nhm.simulation.probes.reporting.aggregates.modes;

import static org.nhm.simulation.probes.reporting.aggregates.modes.ModeTestUtil.data;
import static org.nhm.simulation.probes.reporting.aggregates.modes.ModeTestUtil.noDataPlease;
import static org.nhm.simulation.probes.reporting.aggregates.modes.ModeTestUtil.thisDataPlease;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.simulation.reporting.aggregates.modes.SumMode;

public class SumModeTest {
	private static final DateTime NOW = new DateTime(0);
	private SumMode mode;

	@Before
	public void setup() {
		mode = new SumMode();
	}
	
	@Test
	public void shouldAlwaysCalculate() {
		ModeTestUtil.shouldAlwaysCalculate(mode);
	}
	
	@Test
	public void shouldReturnSummedNumbersOnFinalStep() {
		final ImmutableMap<String, Object> values = data(1d, 2d);
		
		noDataPlease("Should not return values before final step.", mode, values, false, NOW);
		noDataPlease("Should not return values before final step.", mode, values, false, NOW);
		thisDataPlease("Should return sum of values on final step.", mode, values, true, NOW, 3d, 6d);
	}
}
