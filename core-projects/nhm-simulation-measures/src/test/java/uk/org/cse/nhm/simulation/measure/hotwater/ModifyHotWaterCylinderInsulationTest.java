package uk.org.cse.nhm.simulation.measure.hotwater;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulation.measure.hotwater.InstallHotWaterCylinderInsulation.Modifier;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * ModifyHotWateCylinderInsulationTest.
 *
 * @author richardTiffin
 */
@RunWith(MockitoJUnitRunner.class)
public class ModifyHotWaterCylinderInsulationTest {

    @Mock private IComponentsScope components;
    @Mock private IDimension<ITechnologyModel> techDimension;
    @Mock private IWetHeatingMeasureFactory wetHeatingFactory;
    @Mock private IComponentsFunction<Number> wetHeatingCapex;
    @Mock private ITechnologyModel techModel;
    @Mock private ICentralWaterSystem centralWaterHeatingSystem;

    private InstallHotWaterCylinderInsulation measure;

    @Before
    public void initialiseTests() {
        measure = new InstallHotWaterCylinderInsulation(techDimension, null);

        when(components.get(techDimension)).thenReturn(techModel);
        when(techModel.getCentralWaterSystem()).thenReturn(centralWaterHeatingSystem);
    }

    @Test
    public void ShouldNotBeSuitableForInsulationIfHotWaterCylinderIsNotPresent() throws Exception {
        assertFalse(measure.isSuitable(components, mock(ILets.class)));
    }

    @Test
    public void ShouldBeSuitableForInsulationIfHotWaterCylinderPresentAndHasNoInsulation() throws Exception {
        final IWaterTank store = mock(IWaterTank.class);
        when(centralWaterHeatingSystem.getStore()).thenReturn(store);
        when(store.getInsulation()).thenReturn(0d);

        assertTrue(measure.isSuitable(components, mock(ILets.class)));
    }

    @Test
    public void IfFactoryInsulatedAndInsulationEqualToOrGreaterThanMaxCurrentFactoryInsulationShouldNotBeSuitable()
            throws Exception {
        final IWaterTank store = mock(IWaterTank.class);
        when(centralWaterHeatingSystem.getStore()).thenReturn(store);
        when(store.isFactoryInsulation()).thenReturn(true);
        when(store.getInsulation()).thenReturn(measure.getMaxCurrentFactoryInsulation());

        assertFalse(measure.isSuitable(components, mock(ILets.class)));
    }

    @Test
    public void IfFactoryInsulationNotPreseentAndInsulationEqualsToOrGreateThanMaxJacketInsulationShouldNotBeSuitable() {
        final IWaterTank store = mock(IWaterTank.class);
        when(centralWaterHeatingSystem.getStore()).thenReturn(store);
        when(store.isFactoryInsulation()).thenReturn(false);
        when(store.getInsulation()).thenReturn(measure.getMaxJacketInsulation());

        assertFalse(measure.isSuitable(components, mock(ILets.class)));
    }

    @Test
    public void HasFactoryInsulationLessThan25mmShouldBeSuitable() throws Exception {
        final IWaterTank store = mock(IWaterTank.class);
        when(centralWaterHeatingSystem.getStore()).thenReturn(store);
        when(store.isFactoryInsulation()).thenReturn(true);
        when(store.getInsulation()).thenReturn(measure.getMaxCurrentFactoryInsulation() - 1);

        assertTrue(measure.isSuitable(components, mock(ILets.class)));
    }

    @Test
    public void HasJacketInsulationLessThan80mmShouldBeSuitable() throws Exception {
        final IWaterTank store = mock(IWaterTank.class);
        when(centralWaterHeatingSystem.getStore()).thenReturn(store);
        when(store.isFactoryInsulation()).thenReturn(false);
        when(store.getInsulation()).thenReturn(measure.getMaxJacketInsulation() - 1);

        assertTrue(measure.isSuitable(components, mock(ILets.class)));
    }

    @Test
    public void InstallationShouldAddInsulationToMaxFactoryFittedIfCurrentInsulationIsFactoryFitted() throws Exception {
        final IWaterTank store = mock(IWaterTank.class);
        when(centralWaterHeatingSystem.getStore()).thenReturn(store);
        when(store.isFactoryInsulation()).thenReturn(true);

        Modifier modifier = measure.new Modifier();
        modifier.modify(techModel);
        verify(store, times(1)).setInsulation(measure.getMaxCurrentFactoryInsulation());
    }

    @Test
    public void InstallationShouldAddInsulationToMaxJacketInsulationIfInsulationNotFactoryFitted() throws Exception {
        final IWaterTank store = mock(IWaterTank.class);
        when(centralWaterHeatingSystem.getStore()).thenReturn(store);
        when(store.isFactoryInsulation()).thenReturn(false);

        Modifier modifier = measure.new Modifier();
        modifier.modify(techModel);
        verify(store, times(1)).setInsulation(measure.getMaxJacketInsulation());
    }
}
