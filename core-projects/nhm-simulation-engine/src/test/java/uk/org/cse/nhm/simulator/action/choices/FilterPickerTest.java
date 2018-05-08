package uk.org.cse.nhm.simulator.action.choices;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker;
import uk.org.cse.nhm.simulator.scope.PickOption;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.util.RandomSource;

public class FilterPickerTest extends LettingPickerTest {

    @Override
    protected void childSetup() {
    }

    @Override
    protected LettingPicker buildPicker(final List<ISequenceSpecialAction> ssas) {
        @SuppressWarnings("unchecked")
        final IComponentsFunction<Boolean> test = mock(IComponentsFunction.class);
        when(test.compute(any(IComponentsScope.class), any(ILets.class))).thenReturn(false);
        final IPicker delegate = mock(IPicker.class);

        return new FilterPicker(test, delegate, ssas);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void picksFromFiltered() {
        final IComponentsScope a = mock(IComponentsScope.class);
        final IComponentsScope b = mock(IComponentsScope.class);

        final PickOption pa = new PickOption(a, ILets.EMPTY);
        final PickOption pb = new PickOption(b, ILets.EMPTY);

        final IPicker delegate = mock(IPicker.class);
        when(delegate.pick(any(RandomSource.class),
                any(Set.class))).thenReturn(pa);

        final IComponentsFunction<Boolean> test = mock(IComponentsFunction.class);
        when(test.compute(a, ILets.EMPTY)).thenReturn(true);
        when(test.compute(b, ILets.EMPTY)).thenReturn(false);

        final PickOption picked = new FilterPicker(test, delegate, NO_VARS)
                .pick(random, ImmutableSet.of(pa, pb));

        Assert.assertEquals("Should pick option a.", a, picked.scope);

        verify(delegate, times(1)).pick(random, ImmutableSet.of(pa));
    }
}
