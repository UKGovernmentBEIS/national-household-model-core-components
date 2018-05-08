package uk.org.cse.nhm.simulator.trigger.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.trigger.exposure.IDwellingGroupSampler;
import uk.org.cse.nhm.simulator.util.RandomSource;

public abstract class TimedTriggerTest {

    protected TimedTrigger trigger;
    protected ISimulator sim;
    protected Set<IDwelling> samplerResult;
    protected IStateAction action;
    protected ICanonicalState state;
    protected IDwellingGroupSampler sampler;
    protected IDwellingGroup group;
    protected RandomSource random = new RandomSource(0);

    @Before
    public void setUp() throws NHMException {
        sim = mock(ISimulator.class);
        state = mock(ICanonicalState.class);
        when(sim.getCurrentDate()).thenReturn(new DateTime());
        when(state.getRandom()).thenReturn(random);
        setupGroupAndStuff();
        trigger = buildTrigger(sim);
    }

    @SuppressWarnings("unchecked")
    private void setupGroupAndStuff() throws NHMException {
        IDwellingGroup group = mock(IDwellingGroup.class);
        Set<IDwelling> groupContents = mock(Set.class);
        samplerResult = mock(Set.class);
        when(group.getContents()).thenReturn(groupContents);

        action = mock(IStateAction.class);

        IDwellingGroupSampler sampler = mock(IDwellingGroupSampler.class);
        when(sampler.sample(random, groupContents)).thenReturn(samplerResult);

        this.group = group;
        this.sampler = sampler;
    }

    abstract protected TimedTrigger buildTrigger(ISimulator sim);
}
