package uk.org.cse.nhm.simulator.trigger.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Priority;

public class TimeSequenceTriggerTest extends TimedTriggerTest {
	@Test
	public void testTimeSequenceTrigger() throws NHMException {
		trigger.initialize();
		
		final ArgumentCaptor<IDateRunnable> captor = ArgumentCaptor.forClass(IDateRunnable.class);
		final ArgumentCaptor<DateTime> dates = ArgumentCaptor.forClass(DateTime.class);
		verify(sim, times(1)).schedule(dates.capture(), any(Priority.class), captor.capture());

		Assert.assertEquals(new DateTime(0), dates.getValue());
		
		captor.getValue().run(dates.getValue());
		
		verify(state, times(1)).apply(trigger, action, samplerResult, ILets.EMPTY);
	}

	@Override
	protected TimedTrigger buildTrigger(final ISimulator sim) {
		return new TimeSequenceTrigger(sim, state, new DateTime(0), Collections.singletonList(new DateTime(0)), group, Collections.singletonList(sampler), action, Name.of("blah"));
	}
}
