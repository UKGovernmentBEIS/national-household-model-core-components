package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public abstract class DelegatingFunctionTest {

    protected static final double NO_ERROR = 0.0;

    protected IComponentsFunction<Double> delegate;
    protected IDimension<?> dim;
    protected Set<IDimension<?>> dimSet;
    protected DateTime now;
    protected Set<DateTime> dateSet;

    protected IComponentsFunction<? extends Number> fun;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        dim = mock(IDimension.class);
        dimSet = Collections.<IDimension<?>>singleton(dim);
        now = new DateTime(0);
        dateSet = Collections.singleton(now);

        delegate = mock(IComponentsFunction.class);
        when(delegate.getChangeDates()).thenReturn(dateSet);
        when(delegate.getDependencies()).thenReturn(dimSet);

        fun = buildFun(delegate);
    }

    protected abstract IComponentsFunction<? extends Number> buildFun(IComponentsFunction<? extends Number> delegate);

    @Test
    public void shouldTakeDependenciesFromDelegate() {
        Assert.assertEquals("Dependencies should come from delegate function.", dimSet, new SnapshotDeltaFunction(delegate, "before", "after").getDependencies());
    }

    @Test
    public void shouldTakeChangeDatesFromDelegate() {
        Assert.assertEquals("Change dates should come from delegate function.", dateSet, new SnapshotDeltaFunction(delegate, "before", "after").getChangeDates());
    }

}
