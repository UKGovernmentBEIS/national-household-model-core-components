package uk.org.cse.nhm.simulation.measure.hotwater;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.nhm.hom.emf.technologies.ICentralWaterSystem;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWaterTank;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.IWetHeatingMeasureFactory;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * FitStorageTankThermostatTest.
 *
 * @author richardTiffin
 */
@RunWith(MockitoJUnitRunner.class)
public class FitStorageTankThermostatTest {

    @Mock
    private IComponentsScope components;
    @Mock
    private IDimension<ITechnologyModel> techDimension;
    @Mock
    private IWetHeatingMeasureFactory wetHeatingFactory;
    @Mock
    private IComponentsFunction<Number> wetHeatingCapex;
    @Mock
    private ITechnologyModel techModel;
    @Mock
    private ICentralWaterSystem centralWaterHeatingSystem;

    private FitStorageTankThermostat measure;

    @Before
    public void initialiseTests() {
        measure = new FitStorageTankThermostat(techDimension, null);

        when(components.get(techDimension)).thenReturn(techModel);
        when(techModel.getCentralWaterSystem()).thenReturn(centralWaterHeatingSystem);
    }

    @Test
    public void MeasureShouldBeSuitabeIfHotwaterTankPresentAndHasNoThermostat() throws Exception {
        final IWaterTank store = mock(IWaterTank.class);
        when(centralWaterHeatingSystem.getStore()).thenReturn(store);
        when(store.isThermostatFitted()).thenReturn(false);

        assertTrue(measure.isSuitable(components, mock(ILets.class)));
    }

    @Test
    public void MeasureShouldNotBeSuitableIfNoHotwateTankPresent() throws Exception {
        assertFalse(measure.isSuitable(components, mock(ILets.class)));
    }

    @Test
    public void MeasureShouldNotBeSuitableIfHotWaterTankPresentAndStatFitted() throws Exception {
        final IWaterTank store = mock(IWaterTank.class);
        when(centralWaterHeatingSystem.getStore()).thenReturn(store);
        when(store.isThermostatFitted()).thenReturn(true);

        assertFalse(measure.isSuitable(components, mock(ILets.class)));
    }

    @Ignore("Not sure whether this actually tests anything yet!")
    @Test
    public void IfMeasureIsAppliedCorrectlyStoreShouldHaveAThermostatFitted() throws Exception {
        measure.doApply(mock(ISettableComponentsScope.class));
    }
}
