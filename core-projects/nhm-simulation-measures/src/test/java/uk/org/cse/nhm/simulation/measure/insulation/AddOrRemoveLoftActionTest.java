package uk.org.cse.nhm.simulation.measure.insulation;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;

public class AddOrRemoveLoftActionTest {

    @Mock
    IDimension<StructureModel> structure;

    @Mock
    ISettableComponentsScope scope;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void alwaysSuitable() {
        final AddOrRemoveLoftAction a = new AddOrRemoveLoftAction(structure, true);
        Assert.assertTrue(a.isSuitable(scope, ILets.EMPTY));
    }

    private StructureModel withLoft(final boolean b, final double ins) {
        final StructureModel sm = new StructureModel();

        sm.setHasLoft(b);
        if (b) {
            sm.setRoofInsulationThickness(ins);
        }

        return sm;
    }

    private AddOrRemoveLoftAction makeAction(final boolean b) {
        return new AddOrRemoveLoftAction(structure, b);
    }

    @Test
    public void doesNothingIfAlreadySetAndHasLoft() {
        check(makeAction(true), withLoft(true, 100), false, true);
    }

    @Test
    public void doesNothingIfAlreadySetToNoLoft() {
        check(makeAction(false), withLoft(false, 100), false, false);
    }

    @Test
    public void setsLoftIfNotAlreadySet() {
        check(makeAction(true), withLoft(false, 100), true, true);
    }

    @Test
    public void clearsLoftIfAlreadySet() {
        check(makeAction(false), withLoft(true, 100), true, false);
    }

    @SuppressWarnings("unchecked")
    private void check(final AddOrRemoveLoftAction action,
            final StructureModel input, final boolean change, final boolean hasLoftAfter) {
        action.apply(scope, ILets.EMPTY);

        @SuppressWarnings("rawtypes")
        final ArgumentCaptor<IModifier<StructureModel>> cap = (ArgumentCaptor) ArgumentCaptor.forClass(IModifier.class);
        verify(scope).modify(
                same(structure),
                cap.capture());

        Assert.assertEquals(change, cap.getValue().modify(input));
        Assert.assertEquals(hasLoftAfter, input.getHasLoft());
        if (hasLoftAfter == false) {
            Assert.assertEquals(0d, input.getRoofInsulationThickness(), 0d);
        }
    }

}
