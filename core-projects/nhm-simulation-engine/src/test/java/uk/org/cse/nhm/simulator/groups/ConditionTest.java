package uk.org.cse.nhm.simulator.groups;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.groups.impl.Condition;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ConditionTest {

    @Test
    public void testConditionDefaultBranchAddRemove() {
        final IDwellingGroup source = mock(IDwellingGroup.class);
        final ICanonicalState state = mock(ICanonicalState.class);
        final Condition cond = new Condition(state, source, Collections.<IComponentsFunction<Boolean>>emptyList());

        final ArgumentCaptor<IDwellingGroupListener> captor = ArgumentCaptor.forClass(IDwellingGroupListener.class);
        verify(source).addListener(captor.capture());

        final IDwellingGroupListener listener = captor.getValue();

        final IDwelling d1 = mock(IDwelling.class);

        final IDwellingGroup defaultGroup = cond.getDefaultGroup();

        Assert.assertFalse(defaultGroup.contains(d1));

        listener.dwellingGroupChanged(null, source, Collections.singleton(d1), Collections.<IDwelling>emptySet());

        Assert.assertTrue(defaultGroup.contains(d1));

        listener.dwellingGroupChanged(null, source, Collections.<IDwelling>emptySet(), Collections.singleton(d1));

        Assert.assertFalse(defaultGroup.contains(d1));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testConditionBranches() {
        final IDwellingGroup source = mock(IDwellingGroup.class);
        final ICanonicalState state = mock(ICanonicalState.class);
        final IComponentsFunction<Boolean> test1 = mock(IComponentsFunction.class);
        final IComponentsFunction<Boolean> test2 = mock(IComponentsFunction.class);

        final ArgumentCaptor<IDwellingGroupListener> captor = ArgumentCaptor.forClass(IDwellingGroupListener.class);

        final IDwelling d1 = mock(IDwelling.class);
        final IDwelling d2 = mock(IDwelling.class);
        final IDwelling d3 = mock(IDwelling.class);

        final IComponentsScope c1 = mock(IComponentsScope.class);
        final IComponentsScope c2 = mock(IComponentsScope.class);
        final IComponentsScope c3 = mock(IComponentsScope.class);

        when(state.detachedScope(d1)).thenReturn(c1);
        when(state.detachedScope(d2)).thenReturn(c2);
        when(state.detachedScope(d3)).thenReturn(c3);

        when(test1.compute(eq(c1), eq(ILets.EMPTY))).thenReturn(true);
        when(test2.compute(eq(c2), eq(ILets.EMPTY))).thenReturn(true);
        when(test2.compute(eq(c3), eq(ILets.EMPTY))).thenReturn(false);

        final Condition cond = new Condition(state, source, ImmutableList.<IComponentsFunction<Boolean>>of(test1, test2));

        final IDwellingGroup b1 = cond.getGroups().get(0);
        final IDwellingGroup b2 = cond.getGroups().get(1);
        final IDwellingGroup b3 = cond.getDefaultGroup();

        verify(source).addListener(captor.capture());

        final IDwellingGroupListener listener = captor.getValue();

        listener.dwellingGroupChanged(null, source, ImmutableSet.of(d1, d2, d3), Collections.<IDwelling>emptySet());

        Assert.assertTrue(b1.contains(d1));
        Assert.assertTrue(b2.contains(d2));
        Assert.assertTrue(b3.contains(d3));
        Assert.assertTrue(Collections.disjoint(b1.getContents(), b2.getContents())
                && Collections.disjoint(b2.getContents(), b3.getContents())
                && Collections.disjoint(b1.getContents(), b3.getContents()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testConditionBranchesOnStateChange() {
        final IDwellingGroup source = mock(IDwellingGroup.class);
        final ICanonicalState state = mock(ICanonicalState.class);
        final IComponentsFunction<Boolean> test1 = mock(IComponentsFunction.class);
        final IComponentsFunction<Boolean> test2 = mock(IComponentsFunction.class);

        final Condition cond = new Condition(state, source, ImmutableList.<IComponentsFunction<Boolean>>of(test1, test2));

        final ArgumentCaptor<IDwellingGroupListener> captor = ArgumentCaptor.forClass(IDwellingGroupListener.class);
        verify(source).addListener(captor.capture());
        final IDwellingGroupListener listener = captor.getValue();

        final ArgumentCaptor<IStateListener> captor2 = ArgumentCaptor.forClass(IStateListener.class);

        verify(state).addStateListener(captor2.capture());

        final IStateListener stateListener = captor2.getValue();

        when(source.contains(any(IDwelling.class))).thenReturn(true);

        final IDwelling d1 = mock(IDwelling.class);
        final IDwelling d2 = mock(IDwelling.class);
        final IDwelling d3 = mock(IDwelling.class);

        final IComponentsScope c1 = mock(IComponentsScope.class);
        final IComponentsScope c2 = mock(IComponentsScope.class);
        final IComponentsScope c3 = mock(IComponentsScope.class);

        when(state.detachedScope(d1)).thenReturn(c1);
        when(state.detachedScope(d2)).thenReturn(c2);
        when(state.detachedScope(d3)).thenReturn(c3);
        when(test1.compute(eq(c1), eq(ILets.EMPTY))).thenReturn(true);
        when(test2.compute(eq(c2), eq(ILets.EMPTY))).thenReturn(true);
        when(test2.compute(eq(c3), eq(ILets.EMPTY))).thenReturn(false);

        final IDwellingGroup b1 = cond.getGroups().get(0);
        final IDwellingGroup b2 = cond.getGroups().get(1);
        final IDwellingGroup b3 = cond.getDefaultGroup();

        listener.dwellingGroupChanged(null, source, ImmutableSet.of(d1, d2, d3), Collections.<IDwelling>emptySet());

        Assert.assertTrue(b1.contains(d1));
        Assert.assertTrue(b2.contains(d2));
        Assert.assertTrue(b3.contains(d3));
        Assert.assertTrue(Collections.disjoint(b1.getContents(), b2.getContents())
                && Collections.disjoint(b2.getContents(), b3.getContents())
                && Collections.disjoint(b1.getContents(), b3.getContents()));

        final IStateChangeNotification notification = mock(IStateChangeNotification.class);
        when(notification.getChangedDwellings(any(Set.class))).thenReturn(ImmutableSet.of(d1, d2, d3));

        when(test1.compute(eq(c1), eq(ILets.EMPTY))).thenReturn(false); // first branch no longer matches d2
        when(test2.compute(eq(c2), eq(ILets.EMPTY))).thenReturn(true);
        when(test1.compute(eq(c3), eq(ILets.EMPTY))).thenReturn(true);

        stateListener.stateChanged(state, notification);

        Assert.assertTrue(b3.contains(d1));
        Assert.assertTrue(b2.contains(d2));
        Assert.assertTrue(b1.contains(d3));
        Assert.assertTrue(Collections.disjoint(b1.getContents(), b2.getContents())
                && Collections.disjoint(b2.getContents(), b3.getContents())
                && Collections.disjoint(b1.getContents(), b3.getContents()));
    }
}
