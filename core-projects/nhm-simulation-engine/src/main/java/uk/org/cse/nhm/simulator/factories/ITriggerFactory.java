package uk.org.cse.nhm.simulator.factories;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.trigger.exposure.IDwellingGroupSampler;
import uk.org.cse.nhm.simulator.trigger.impl.DelayedGroupMembershipTrigger;
import uk.org.cse.nhm.simulator.trigger.impl.FixedFrequencyTimedTrigger;
import uk.org.cse.nhm.simulator.trigger.impl.TimeSequenceTrigger;

public interface ITriggerFactory {

    public TimeSequenceTrigger createTimeSequenceTrigger(
            final Name id,
            final List<DateTime> times, final IDwellingGroup group,
            final List<IDwellingGroupSampler> samplers, final IStateAction action);

    public FixedFrequencyTimedTrigger createFixedFrequencyTrigger(
            final Name id,
            @Assisted("start")
            final DateTime start,
            final Period period,
            @Assisted("end")
            final DateTime end, final IDwellingGroup group,
            final IDwellingGroupSampler sampler, final IStateAction action);

    public FixedFrequencyTimedTrigger createFixedFrequencyTrigger(
            final Name id,
            final Period period, @Assisted("end") final DateTime end,
            final IDwellingGroup group, final IDwellingGroupSampler sampler,
            final IStateAction action);

    public FixedFrequencyTimedTrigger createFixedFrequencyTrigger(
            final Name id,
            @Assisted("start") final DateTime start, final Period period,
            final IDwellingGroup group, final IDwellingGroupSampler sampler,
            final IStateAction action);

    public FixedFrequencyTimedTrigger createFixedFrequencyTrigger(
            final Name id,
            final Period period, final IDwellingGroup group,
            final IDwellingGroupSampler sampler, final IStateAction action);

    public DelayedGroupMembershipTrigger createEntryTrigger(
            final Name id,
            Period delay,
            IDwellingGroup group, IStateAction action);
}
