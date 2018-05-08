package uk.org.cse.nhm.simulator.action.choices;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker;
import uk.org.cse.nhm.simulator.scope.PickOption;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;

public class FallbackPickerTest extends LettingPickerTest {

    private IPicker preferredPicker;
    private IPicker fallbackPicker;
    private ImmutableList<IPicker> delegates;

    private static final Set<PickOption> NONE = Collections.emptySet();
    private IComponentsScope scope;
    private ImmutableSet<PickOption> options;

    @Override
    protected void childSetup() {
        preferredPicker = mock(IPicker.class);
        fallbackPicker = mock(IPicker.class);
        delegates = ImmutableList.of(preferredPicker, fallbackPicker);

        scope = mock(IComponentsScope.class);
        options = ImmutableSet.of(new PickOption(scope, ILets.EMPTY));
    }

    @Override
    protected LettingPicker buildPicker(final List<ISequenceSpecialAction> ssas) {
        return new FallbackPicker(ssas, delegates);
    }

    private LettingPicker buildPicker() {
        return buildPicker(NO_VARS);
    }

    @Test
    public void returnsNullIfEmpty() {
        when(preferredPicker.pick(random, NONE)).thenReturn(null);
        when(fallbackPicker.pick(random, NONE)).thenReturn(null);

        Assert.assertNull("Picking from no options should return null.", buildPicker().pick(random, NONE));
    }

    @Test
    public void usesPreferred() {
        when(preferredPicker.pick(random, options)).thenReturn(options.iterator().next());

        Assert.assertEquals("Preferred picker's selection should have been used.", scope, buildPicker().pick(random, options).scope);
    }

    @Test
    public void usesFallbackIfPreferredFails() {
        when(preferredPicker.pick(random, options)).thenReturn(null);
        when(fallbackPicker.pick(random, options)).thenReturn(options.iterator().next());

        Assert.assertEquals("Fallback picker's selection should have been used.",
                scope, buildPicker().pick(random, options).scope);
    }

    @Test
    public void returnsNullIfAllPickersFail() {
        when(preferredPicker.pick(random, options)).thenReturn(null);
        when(fallbackPicker.pick(random, options)).thenReturn(null);

        Assert.assertNull("Should return null if all pickers fail.", buildPicker().pick(random, options));
    }
}
