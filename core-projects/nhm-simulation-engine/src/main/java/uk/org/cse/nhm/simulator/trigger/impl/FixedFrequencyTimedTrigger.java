package uk.org.cse.nhm.simulator.trigger.impl;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.trigger.exposure.IDwellingGroupSampler;
import uk.org.cse.nhm.simulator.util.Repeater;

/**
 * A trigger activator which will fire its trigger every so many days, set with
 * {@link #setWakePeriod(int)}.
 *
 * @author hinton
 *
 */
public class FixedFrequencyTimedTrigger extends TimedTrigger {

    private final IDwellingGroupSampler sampler;

    @AssistedInject
    public FixedFrequencyTimedTrigger(ISimulator simulator, final ICanonicalState state,
            @Assisted final Name id,
            @Assisted("start") final DateTime startDate, @Assisted final Period period, @Assisted("end") final DateTime endDate,
            @Assisted final IDwellingGroup group,
            @Assisted final IDwellingGroupSampler sampler,
            @Assisted final IStateAction action) {
        super(simulator, state, group, action);
        setIdentifier(id);
        this.startDate = startDate;
        this.wakeInterval = period;
        this.endDate = endDate;
        this.sampler = sampler;
    }

    @AssistedInject
    public FixedFrequencyTimedTrigger(ISimulator simulator, final ICanonicalState state,
            @Assisted final Name id,
            @Assisted final Period period, @Assisted("end") final DateTime endDate,
            @Assisted final IDwellingGroup group,
            @Assisted final IDwellingGroupSampler sampler,
            @Assisted final IStateAction action
    ) {
        this(simulator, state, id, null, period, endDate, group, sampler, action);
    }

    @AssistedInject
    public FixedFrequencyTimedTrigger(ISimulator simulator, final ICanonicalState state,
            @Assisted final Name id,
            @Assisted("start") final DateTime startDate, @Assisted final Period period,
            @Assisted final IDwellingGroup group,
            @Assisted final IDwellingGroupSampler sampler,
            @Assisted final IStateAction action
    ) {
        this(simulator, state, id, startDate, period, null, group, sampler, action);
    }

    @AssistedInject
    public FixedFrequencyTimedTrigger(ISimulator simulator, final ICanonicalState state,
            @Assisted final Name id,
            @Assisted final Period period,
            @Assisted final IDwellingGroup group,
            @Assisted final IDwellingGroupSampler sampler,
            @Assisted final IStateAction action
    ) {
        this(simulator, state, id, null, period, null, group, sampler, action);
    }

    /**
     * This is the interval, in days, between trigger activation
     */
    private Period wakeInterval;

    private DateTime startDate;
    private DateTime endDate;

    public Period getWakeInterval() {
        return wakeInterval;
    }

    @Override
    protected IDwellingGroupSampler getSampler() {
        return sampler;
    }

    @Override
    public void initialize() throws NHMException {
        final Repeater repeater = new Repeater(simulator, getPriority(), wakeInterval) {
            @Override
            protected boolean fire(final DateTime date) throws NHMException {
                FixedFrequencyTimedTrigger.this.run(date);
                return endDate == null ? true : date.plus(wakeInterval).isBefore(endDate);
            }
        };

        repeater.start(startDate == null ? simulator.getCurrentDate() : startDate);
    }
}
