package org.nhm.simulation.probes.reporting.aggregates;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulation.reporting.aggregates.CrossGroups;
import uk.org.cse.nhm.simulation.reporting.aggregates.IGroups.IListener;
import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class CrossGroupsTest {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void testCrossGroupsMakeCrosses() throws NHMException {
        final IComponentsFunction<Object> f1 = mock(IComponentsFunction.class, "f1");
        final IComponentsFunction<Object> f2 = mock(IComponentsFunction.class, "f2");
        final List<IComponentsFunction<?>> divisions = ImmutableList.<IComponentsFunction<?>>of(f1, f2);
        final IDwellingGroup source = mock(IDwellingGroup.class);
        final ISimulator simulator = mock(ISimulator.class);
        final ICanonicalState state = mock(ICanonicalState.class);
        final CrossGroups cg = new CrossGroups(state, simulator, source, divisions);
        final IStateChangeNotification notification = mock(IStateChangeNotification.class);
        final IStateChangeSource changeSource = mock(IStateChangeSource.class);
        final Name changeSourceName = Name.of("cause");

        final IListener l = mock(IListener.class);

        cg.addListener(l);

        final IComponentsScope c1 = mock(IComponentsScope.class, "d1 in s");
        final IComponentsScope c2 = mock(IComponentsScope.class, "d2 in s");

        final IDwelling dw1 = mock(IDwelling.class, "d1");
        final IDwelling dw2 = mock(IDwelling.class, "d2");

        when(state.detachedScope(dw1)).thenReturn(c1);
        when(state.detachedScope(dw2)).thenReturn(c2);

        when(source.getContents()).thenReturn(ImmutableSet.of(dw1, dw2));

        when(f1.compute(eq(c1), eq(ILets.EMPTY))).thenReturn("A");
        when(f2.compute(eq(c1), eq(ILets.EMPTY))).thenReturn("B");

        when(f1.compute(eq(c2), eq(ILets.EMPTY))).thenReturn("C");
        when(f2.compute(eq(c2), eq(ILets.EMPTY))).thenReturn("D");

        verify(source).addListener(cg);
        verify(simulator).addSimulationStepListener(cg);
        verify(state).addStateListener(cg);

        final IStateScope rootScopeOfChange = mock(IStateScope.class);
        when(rootScopeOfChange.getTag()).thenReturn(changeSource);
        when(notification.getRootScope()).thenReturn(rootScopeOfChange);
        when(changeSource.getIdentifier()).thenReturn(changeSourceName);

        cg.dwellingGroupChanged(notification, source, null, null);
        cg.simulationStepped(new DateTime(0), new DateTime(1), false);

        when(f1.compute(eq(c1), eq(ILets.EMPTY))).thenReturn("E");
        when(f1.compute(eq(c2), eq(ILets.EMPTY))).thenReturn("E");

        when(f2.compute(eq(c1), eq(ILets.EMPTY))).thenReturn("F");
        when(f2.compute(eq(c2), eq(ILets.EMPTY))).thenReturn("F");

        cg.dwellingGroupChanged(notification, source, null, null);
        cg.simulationStepped(new DateTime(0), new DateTime(1), false);

        final ArgumentCaptor<ImmutableMap> identifierCaptor = ArgumentCaptor.forClass(ImmutableMap.class);
        final ArgumentCaptor<Set> contentsCaptor = ArgumentCaptor.forClass(Set.class);
        final ArgumentCaptor<Set> causesCaptor = ArgumentCaptor.forClass(Set.class);

        verify(l, times(5)).groupChanged(identifierCaptor.capture(), contentsCaptor.capture(), causesCaptor.capture(), anyBoolean());

        final Iterator<ImmutableMap> it1 = identifierCaptor.getAllValues().iterator();
        final Iterator<Set> it2 = contentsCaptor.getAllValues().iterator();

        boolean firstRoundA = true, firstRoundB = true;

        while (it1.hasNext() && it2.hasNext()) {
            final ImmutableMap label = it1.next();
            final Set<IDwelling> value = it2.next();
            System.err.println(String.format("%s: %s", label, value));
            if (label.equals(ImmutableMap.of("f1", "C", "f2", "D"))) {
                if (firstRoundA) {
                    firstRoundA = false;
                    Assert.assertEquals(ImmutableSet.of(dw2), value);
                } else {
                    Assert.assertFalse(firstRoundB);
                    Assert.assertTrue(value.isEmpty());
                }
            } else if (label.equals(ImmutableMap.of("f1", "A", "f2", "B"))) {
                if (firstRoundB) {
                    firstRoundB = false;
                    Assert.assertEquals(ImmutableSet.of(dw1), value);
                } else {
                    Assert.assertFalse(firstRoundA);
                    Assert.assertTrue(value.isEmpty());
                }
            } else if (label.equals(ImmutableMap.of("f1", "E", "f2", "F"))) {
                Assert.assertEquals(ImmutableSet.of(dw1, dw2), value);
                Assert.assertTrue("This should only happen after we have seen the other conditions", !(firstRoundA && firstRoundB));
            } else {
                Assert.fail("Did not expect " + label);
            }
        }

        final Set causes = Iterables.get(causesCaptor.getAllValues(), 0);
        Assert.assertEquals("Cross groups should have correctly identified the name of the source of the changes.", ImmutableSet.of(changeSourceName.getPath()), causes);
    }
}
