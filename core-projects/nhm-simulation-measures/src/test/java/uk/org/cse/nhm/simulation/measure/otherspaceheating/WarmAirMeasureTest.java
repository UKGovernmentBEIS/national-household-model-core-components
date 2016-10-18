package uk.org.cse.nhm.simulation.measure.otherspaceheating;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.EnumSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.technologies.IWarmAirSystem;
import uk.org.cse.nhm.hom.emf.util.Efficiency;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

/**
 * WarmAirMeasureTest.
 *
 * @author richardTiffin
 */
@RunWith(MockitoJUnitRunner.class)
public class WarmAirMeasureTest {

    @Mock private IComponentsScope components;
    @Mock private IDimension<ITechnologyModel> techDimension;
    @Mock private ITechnologyModel techModel;

    private WarmAirMeasure createFuelTypeMeasure(final FuelType fuelType) {
        return new WarmAirMeasure(null, techDimension, fuelType, null, null, null,
                                  new ConstantComponentsFunction<Number>(null, 0));
    }

    @Test
    public void ShouldBeSuitableIfHouseIsOnMainsGasOrLPG() throws Exception {
        assertTrue("MAINS GAS", createFuelTypeMeasure(FuelType.MAINS_GAS).hasCorrectFuelType());
        assertTrue("BOTTLED GAS", createFuelTypeMeasure(FuelType.BOTTLED_LPG).hasCorrectFuelType());
        assertTrue("BULK LPG", createFuelTypeMeasure(FuelType.BULK_LPG).hasCorrectFuelType());
    }

    @Test
    public void ShouldNotBeSuitableIfNot_LPG_Or_MainsGas() throws Exception {
        final EnumSet<FuelType> wrongFuelTypes = EnumSet.complementOf(EnumSet.of(FuelType.MAINS_GAS, FuelType.BOTTLED_LPG,
                                                                           FuelType.BULK_LPG));

        for (final FuelType fuelType : wrongFuelTypes) {
            assertFalse(fuelType.toString(), createFuelTypeMeasure(fuelType).hasCorrectFuelType());
        }
    }

    @Test
    public void ShouldNotBeSuitableIfDoesNotHaveAWarmAirSystemFitted() throws Exception {
        assertFalse(createFuelTypeMeasure(FuelType.MAINS_GAS).hasValidWarmAirSystemFitted(techModel, null, null));
    }

    @Test
    public void ShouldNotBeSuitableIfEfficiencyIsTheSameOrLessThanCurrentlyInstalledSystem() throws Exception {
        final WarmAirMeasure measure = new WarmAirMeasure(null, null, null, null, null, null,
                                                    new ConstantComponentsFunction<Number>(null, 0.5));

        final IWarmAirSystem warmAirSystem = mock(IWarmAirSystem.class);
        when(techModel.getPrimarySpaceHeater()).thenReturn(warmAirSystem);
        when(warmAirSystem.getEfficiency()).thenReturn(Efficiency.fromDouble(0.5));

        assertFalse(measure.hasValidWarmAirSystemFitted(techModel, null, null));
    }

    @Test
    public void ShouldBeSuitableIfEfficientyIsGreaterThanCurrentlyInstalledSystem() throws Exception {
        final WarmAirMeasure measure = new WarmAirMeasure(null, null, null, null, null, null, new ConstantComponentsFunction<Number>(null, 0.5));

        final IWarmAirSystem warmAirSystem = mock(IWarmAirSystem.class);
        when(techModel.getPrimarySpaceHeater()).thenReturn(warmAirSystem);
        when(warmAirSystem.getEfficiency()).thenReturn(Efficiency.fromDouble(0.4));

        assertTrue(measure.hasValidWarmAirSystemFitted(techModel, null, null));
    }
}
