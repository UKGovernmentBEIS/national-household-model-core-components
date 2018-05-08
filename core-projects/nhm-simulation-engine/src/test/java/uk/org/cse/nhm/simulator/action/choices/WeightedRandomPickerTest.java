package uk.org.cse.nhm.simulator.action.choices;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.PickOption;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class WeightedRandomPickerTest extends LettingPickerTest {

    @SuppressWarnings("unchecked")
    @Override
    protected LettingPicker buildPicker(final List<ISequenceSpecialAction> ssas) {
        final IComponentsFunction<Number> weight = mock(IComponentsFunction.class);
        when(weight.compute(any(IComponentsScope.class), any(ILets.class))).thenReturn(0.0);
        return new WeightedRandomPicker(ssas, weight);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void zeroWeightNotPicked() {
        final IComponentsFunction<Number> weight = mock(IComponentsFunction.class);

        final IComponentsScope a = mock(IComponentsScope.class);
        final IComponentsScope b = mock(IComponentsScope.class);

        when(weight.compute(a, ILets.EMPTY)).thenReturn(0.0);
        when(weight.compute(b, ILets.EMPTY)).thenReturn(1.0);

        final PickOption picked = new WeightedRandomPicker(NO_VARS, weight).pick(random,
                ImmutableSet.of(new PickOption(a, ILets.EMPTY),
                        new PickOption(b, ILets.EMPTY)));

        Assert.assertEquals("Should not pick a 0 weight option.", b, picked.scope);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void choosesRandomlyBasedOnWeight() {
        final IComponentsFunction<Number> weight = mock(IComponentsFunction.class);

        final IComponentsScope a = mock(IComponentsScope.class);
        final IComponentsScope b = mock(IComponentsScope.class);

        when(weight.compute(a, ILets.EMPTY)).thenReturn(0.3);
        when(weight.compute(b, ILets.EMPTY)).thenReturn(0.6);

        final WeightedRandomPicker picker = new WeightedRandomPicker(NO_VARS, weight);

        double aCount = 0;
        double bCount = 0;
        for (int i = 0; i < 100000; i++) {
            final PickOption pick = picker.pick(random, ImmutableSet.of(new PickOption(a, ILets.EMPTY), new PickOption(b, ILets.EMPTY)));
            if (pick.scope == a) {
                aCount++;
            } else if (pick.scope == b) {
                bCount++;
            } else {
                Assert.fail("Should never pick " + pick);
            }
        }

        final double reasonableError = 0.2;
        Assert.assertEquals(String.format("Ratio of b (%f) to a (%f) should be 2 (+-%f).", bCount, aCount, reasonableError), 2, bCount / aCount, reasonableError);
    }
}
