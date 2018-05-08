package uk.org.cse.nhm.simulator.scope;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.util.RandomSource;

public class SettableComponentsScopeTest extends ComponentsScopeTest {

    private final ArgumentCaptor<ISettableComponentsScope> scopeArg = ArgumentCaptor.forClass(ISettableComponentsScope.class);
    private final ArgumentCaptor<ISettableComponentsScope> scopeArg2 = ArgumentCaptor.forClass(ISettableComponentsScope.class);
    private IComponentsAction action1;
    private IComponentsAction action2;
    private IBranch childBranch1;
    private IBranch childBranch2;

    @Override
    @Before
    public void setup() {
        super.setup();
        action1 = mock(IComponentsAction.class, "action 1");
        action2 = mock(IComponentsAction.class, "action 2");

        childBranch1 = mock(IBranch.class, "branch 1");
        childBranch2 = mock(IBranch.class, "branch 2");
        when(branch.branch(any(int.class))).thenReturn(childBranch1, childBranch2);
    }

    @Test
    public void applyShouldHappenInChildScope() {
        final IComponentsAction componentsAction = mock(IComponentsAction.class);
        when(componentsAction.apply(any(ISettableComponentsScope.class), any(ILets.class))).thenReturn(true);

        scope.apply(componentsAction, ILets.EMPTY);
        verify(componentsAction).apply(scopeArg.capture(), any(ILets.class));
        final ISettableComponentsScope childScope = scopeArg.getValue();

        Assert.assertNotSame("Child scope should have been created, different to the components scope the action was passed to.", scope, childScope);
        Assert.assertTrue("Child scope should have been closed following the action.", childScope.isClosed());
    }

    @Test
    public void applyMultipleActionsShouldHappenOnBranchIfAllRequired() {
        when(action1.apply(any(ISettableComponentsScope.class), any(ILets.class))).thenReturn(true);
        when(action2.apply(any(ISettableComponentsScope.class), any(ILets.class))).thenReturn(true);

        Assert.assertTrue("Apply should have returned true.", scope.applyInSequence(ImmutableList.of(action1, action2), ILets.EMPTY, true));
        verify(action1, times(1)).apply(scopeArg.capture(), any(ILets.class));
        verify(action2, times(1)).apply(scopeArg2.capture(), any(ILets.class));
        final TestScope childScope = (TestScope) scopeArg.getValue();
        final TestScope childScope2 = (TestScope) scopeArg2.getValue();

        Assert.assertSame("Child scope should have child branch.", childBranch1, childScope.getBranch());
        Assert.assertSame("Child scope 2 should have child branch.", childBranch1, childScope2.getBranch());

        assertAllDifferent("Each action should generate a new child scope.", scope, childScope, childScope2);
        assertScopesClosed("Child scopes should have been closed following the action.", childScope, childScope2);

        verify(branch, times(1)).branch(any(int.class));
        verify(branch, times(1)).merge(childBranch1);

        Assert.assertEquals("SubScopes from successful actions should be included.", ImmutableList.of(childScope, childScope2), scope.getSubScopes());
    }

    @Test
    public void applyInSequenceFailsIfAnyActionFails() {
        when(action1.apply(any(ISettableComponentsScope.class), any(ILets.class))).thenReturn(false);

        final boolean result = scope.applyInSequence(ImmutableList.of(action1, action2), ILets.EMPTY, true);

        verify(action1, times(1)).apply(any(ISettableComponentsScope.class), any(ILets.class));
        verify(action2, never()).apply(any(ISettableComponentsScope.class), any(ILets.class));

        Assert.assertFalse("Action.apply() failing should have caused scope.apply() to fail.", result);
        verify(branch, never()).merge(any(IBranch.class));
        Assert.assertTrue("Should never have added any child scopes.", scope.getSubScopes().isEmpty());
    }

    private void assertThatHypothesesHaveBeenEvaluated() {
        verify(action1).apply(scopeArg.capture(), any(ILets.class));
        verify(action2).apply(scopeArg2.capture(), any(ILets.class));

        verify(branch, times(2)).branch(any(int.class));

        final TestScope childScope1 = (TestScope) scopeArg.getValue();
        final TestScope childScope2 = (TestScope) scopeArg2.getValue();
        Assert.assertSame("each scope has a new branch", childBranch1, childScope1.getBranch());
        Assert.assertSame("each scope has a new branch", childBranch2, childScope2.getBranch());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void applyWithHypothesesRunsEveryOptionInABranch() {
        final IPicker picker = mock(IPicker.class);

        final boolean result = scope.apply(ImmutableSet.of(action1, action2), ILets.EMPTY, picker);

        assertThatHypothesesHaveBeenEvaluated();

        Assert.assertFalse("No options are possible, so we failed", result);

        verify(picker, never()).pick(any(RandomSource.class), any(Set.class));
        // check branch was never merged
        verify(branch, never()).merge(any(IBranch.class));
        Assert.assertTrue("no children have been added", scope.getSubScopes().isEmpty());
    }

    @Test
    public void applyWithHypothesesPresentsOnlyValidOptionsToPicker() {
        // this also checks that the option selected by the picker got chosen
        final int[] pickCount = new int[]{0};
        final IPicker picker = new IPicker() {
            @Override
            public PickOption pick(final RandomSource rs, final Set<PickOption> options) {
                Assert.assertEquals("Only one option should be presented", 1, options.size());
                pickCount[0]++;
                return Iterables.get(options, 0);
            }
        };

        when(action1.apply(any(ISettableComponentsScope.class), any(ILets.class))).thenReturn(true);

        final boolean result = scope.apply(ImmutableSet.of(action1, action2), ILets.EMPTY, picker);

        assertThatHypothesesHaveBeenEvaluated();

        Assert.assertTrue("The choice should have succeeded", result);

        // check that branch was merged
        verify(branch, times(1)).merge(childBranch1);

        Assert.assertEquals("The correct subscope has been added (for child 1)",
                ImmutableList.of(scopeArg.getValue()),
                scope.getSubScopes());

        Assert.assertEquals("Picker was invoked exactly once", 1, pickCount[0]);
    }

    private void assertScopesClosed(final String message, final IComponentsScope... scopes) {
        for (final IComponentsScope scope : scopes) {
            Assert.assertTrue(message, scope.isClosed());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> void assertAllDifferent(final String message, final T... things) {
        final int length = things.length;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < i; j++) {
                Assert.assertNotSame(message, things[i], things[j]);
            }
        }
    }
}
