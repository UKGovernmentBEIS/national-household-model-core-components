package uk.org.cse.nhm.simulator.state.functions.impl;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ConditionComponentsFunctionTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testCondition() {
        final IComponentsFunction<Object> defaultFunction = mock(IComponentsFunction.class);

        final IComponentsFunction<Boolean> test1 = mock(IComponentsFunction.class);
        final IComponentsFunction<Boolean> test2 = mock(IComponentsFunction.class);

        final IComponentsFunction<Object> f1 = mock(IComponentsFunction.class);
        final IComponentsFunction<Object> f2 = mock(IComponentsFunction.class);

        final IComponentsScope c1 = mock(IComponentsScope.class);
        final IComponentsScope c2 = mock(IComponentsScope.class);

        when(test1.compute(eq(c1), eq(ILets.EMPTY))).thenReturn(true);

        final Object o1 = new Object();
        when(f1.compute(eq(c1), eq(ILets.EMPTY))).thenReturn(o1);

        final ConditionComponentsFunction<Object> ccf = new ConditionComponentsFunction<Object>(
                ImmutableList.of(test1, test2), ImmutableList.of(f1, f2), defaultFunction
        );

        final Object compute = ccf.compute(c1, ILets.EMPTY);

        Assert.assertSame(o1, compute);

        final Object o2 = new Object();

        when(f2.compute(eq(c2), eq(ILets.EMPTY))).thenReturn(o2);

        when(test1.compute(eq(c2), eq(ILets.EMPTY))).thenReturn(false);
        when(test2.compute(eq(c2), eq(ILets.EMPTY))).thenReturn(true);

        Assert.assertSame(o2, ccf.compute(c2, ILets.EMPTY));
    }
}
