package uk.org.cse.nhm.simulator.trigger.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Priority;

public class FixedFrequencyTimedTriggerTest extends TimedTriggerTest {

    @Test
    public void initializeShouldDoSomething() throws NHMException {
        ((FixedFrequencyTimedTrigger) trigger).initialize();

        final ArgumentCaptor<IDateRunnable> captor = ArgumentCaptor.forClass(IDateRunnable.class);
        final ArgumentCaptor<DateTime> dates = ArgumentCaptor.forClass(DateTime.class);
        verify(sim, times(1)).schedule(dates.capture(), any(Priority.class), captor.capture());

        final IDateRunnable callback = captor.getValue();

        callback.run(dates.getValue());

        verify(state, times(1)).apply(trigger, action, samplerResult, ILets.EMPTY);
    }

    @Override
    protected TimedTrigger buildTrigger(final ISimulator sim) {
        return new FixedFrequencyTimedTrigger(sim, state, Name.of("test"), Period.years(1), group, sampler, action);
    }
}
