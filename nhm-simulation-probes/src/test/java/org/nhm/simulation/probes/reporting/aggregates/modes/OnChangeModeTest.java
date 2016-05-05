package org.nhm.simulation.probes.reporting.aggregates.modes;
import static org.nhm.simulation.probes.reporting.aggregates.modes.ModeTestUtil.data;

import java.util.SortedMap;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.simulation.reporting.aggregates.modes.OnChangeMode;

public class OnChangeModeTest {
	private OnChangeMode mode;

	@Before
	public void setup() {
		mode = new OnChangeMode();
	}
	
	@Test
	public void shouldAlwaysCalculate() {
		ModeTestUtil.shouldAlwaysCalculate(mode);
	}
	
	@Test
	public void shouldAlwaysReturnGivenValue() {
		final ImmutableMap<String, Object> values = data(1d, 2d);
		final ImmutableMap<String, Object> differentValues = data(2d, 4d);
		Assert.assertEquals("Should always return new values given to it.",  Optional.of(values), mode.updateAndMaybeReturn("any", values, true, new DateTime()));
		Assert.assertEquals("Returns absent if values unchanged.",  Optional.<SortedMap<String, Double>>absent(), mode.updateAndMaybeReturn("any", values, true, new DateTime()));
		Assert.assertEquals("Should always return new values given to it.",  Optional.of(differentValues), mode.updateAndMaybeReturn("any", differentValues, false, new DateTime()));
	}
}
