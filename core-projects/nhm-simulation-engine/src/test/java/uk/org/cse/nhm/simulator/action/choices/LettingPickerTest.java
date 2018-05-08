package uk.org.cse.nhm.simulator.action.choices;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.scope.PickOption;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.sequence.SnapshotAction;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.util.RandomSource;

abstract public class LettingPickerTest {

    protected final List<ISequenceSpecialAction> NO_VARS = Collections.emptyList();
    final RandomSource random = new RandomSource(0);

    @Before
    public final void setup() {
        childSetup();
    }

    protected void childSetup() {
    }

    ;

	protected abstract LettingPicker buildPicker(List<ISequenceSpecialAction> vars);

    @SuppressWarnings("unchecked")
    protected List<ISequenceSpecialAction> buildLet(final String var, final Double value) {
        final IComponentsFunction<Number> fun = mock(IComponentsFunction.class);
        when(fun.compute(any(IComponentsScope.class), any(ILets.class))).thenReturn(value);
        return Collections.<ISequenceSpecialAction>singletonList(
                new ISequenceSpecialAction() {
            @Override
            public ILets reallyApply(final IComponentsScope scope, final ILets lets) {
                return lets.withBinding(var, value);
            }

            @Override
            public ILets reallyApply(final ISettableComponentsScope scope, final ILets lets) {
                return reallyApply(scope, lets);
            }

            @Override
            public boolean needsIsolation() {
                return false;
            }

            @Override
            public Iterable<? extends IDimension<?>> getDependencies() {
                return null;
            }

            @Override
            public Iterable<? extends DateTime> getChangeDates() {
                return null;
            }
        }
        );
    }

    protected List<ISequenceSpecialAction> buildSnapshots(final String snapshot, final IComponentsAction action) {
        return Collections.<ISequenceSpecialAction>singletonList(
                new SnapshotAction(snapshot, ImmutableList.of(action))
        );
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldLet() {
        final ImmutableList<ISequenceSpecialAction> ssa
                = ImmutableList.<ISequenceSpecialAction>builder()
                        .addAll(buildLet("var", 1.0))
                        .addAll(buildSnapshots("snapshot", mock(IComponentsAction.class)))
                        .build();

        final LettingPicker picker = buildPicker(ssa);

        final IComponentsScope scope = mock(IComponentsScope.class);
        final IHypotheticalComponentsScope hypothesis = mock(IHypotheticalComponentsScope.class);
        when(scope.createHypothesis()).thenReturn(hypothesis);

        final ILets lets = mock(ILets.class);

        final ILets firstLets = mock(ILets.class);

        final ILets innerLets = mock(ILets.class);

        when(lets.withBinding("var", 1d)).thenReturn(firstLets);
        when(firstLets.withBinding("snapshot", hypothesis)).thenReturn(innerLets);

        when(lets.withBindings(any(Map.class))).thenReturn(innerLets);

        picker.pick(random, Collections.singleton(new PickOption(scope, lets)));

        final InOrder ordered = inOrder(scope, lets, firstLets);

        ordered.verify(lets, times(1)).withBinding("var", 1.0);
        ordered.verify(firstLets, times(1)).withBinding("snapshot", hypothesis);
    }
}
