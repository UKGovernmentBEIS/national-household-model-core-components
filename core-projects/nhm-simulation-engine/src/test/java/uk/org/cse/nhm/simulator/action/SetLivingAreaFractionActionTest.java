package uk.org.cse.nhm.simulator.action;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;

public class SetLivingAreaFractionActionTest {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void testSetLivingAreaFractionAction() throws NHMException {
        final IDimension<StructureModel> structure = mock(IDimension.class);
        final SetLivingAreaFractionAction a = new SetLivingAreaFractionAction(structure, 0.44);

        final ISettableComponentsScope co = mock(ISettableComponentsScope.class);

        Assert.assertTrue("Action should be suitable for any house", a.isSuitable(co, ILets.EMPTY));

        final StructureModel sm = mock(StructureModel.class);

        a.apply(co, ILets.EMPTY);

        final ArgumentCaptor<IModifier> captor = ArgumentCaptor.forClass(IModifier.class);
        verify(co).modify(eq(structure), captor.capture());

        captor.getValue().modify(sm);

        Assert.assertTrue("Action should return true when run", a.apply(co, ILets.EMPTY));

        verify(sm).setLivingAreaProportionOfFloorArea(0.44);

        verifyNoMoreInteractions(sm);
    }
}
