package uk.org.cse.nhm.simulator.state.functions.impl.num;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SnapshotDeltaFunctionTest extends DelegatingFunctionTest {

    private IHypotheticalComponentsScope before;
    private IHypotheticalComponentsScope after;
    private IComponentsScope scope;
    private ILets lets;

    @Override
    @Before
    public void setup() {
        super.setup();

        before = mock(IHypotheticalComponentsScope.class);
        after = mock(IHypotheticalComponentsScope.class);

        scope = mock(IComponentsScope.class);

        lets = mock(ILets.class);
        when(lets.get("before", IHypotheticalComponentsScope.class))
                .thenReturn(Optional.of(before));

        when(lets.get("after", IHypotheticalComponentsScope.class))
                .thenReturn(Optional.of(after));

        when(delegate.compute(before, lets)).thenReturn(1.0d);
        when(delegate.compute(after, lets)).thenReturn(10.0d);
    }

    @Test
    public void shouldCalculateDifferenceAcrossSnapshots() {
        Assert.assertEquals("Should be f(before) - f(after).", 9.0, fun.compute(scope, lets).doubleValue(), NO_ERROR);
    }

    @Override
    protected final IComponentsFunction<? extends Number> buildFun(final IComponentsFunction<? extends Number> delegate) {
        return new SnapshotDeltaFunction(delegate, "before", "after");
    }

}
