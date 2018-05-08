package uk.org.cse.nhm.simulator.state.dimensions.energy;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyTestHelper.FUTURE;
import static uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyTestHelper.FUTURE_YEARS;
import static uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyTestHelper.START;
import static uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyTestHelper.mockPowerTable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;
import uk.org.cse.nhm.simulator.util.TimeUtil;

public class EnergyMeterDimensionTest {

    private EnergyMeterDimension energyMeterDimension;
    private ITimeDimension timeDimension;
    private IDimension<IPowerTable> powerDimension;
    private IDwelling d1;
    private IDwelling d2;
    private IState state;
    private static final double ANNUAL_POWER = 10000.0;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        state = mock(IState.class);

        timeDimension = mock(ITimeDimension.class);
        when(state.getGeneration(eq(timeDimension), any(IDwelling.class))).thenReturn(1);
        when(state.get(eq(timeDimension), any(IDwelling.class))).thenReturn(TimeUtil.mockTime(START));

        powerDimension = mock(IDimension.class);
        when(state.getGeneration(eq(powerDimension), any(IDwelling.class))).thenReturn(1);
        final IPowerTable powerTable = mockPowerTable(ANNUAL_POWER);
        when(state.get(eq(powerDimension), any(IDwelling.class))).thenReturn(powerTable);

        d1 = mock(IDwelling.class);
        when(d1.getID()).thenReturn(1);
        d2 = mock(IDwelling.class);
        when(d2.getID()).thenReturn(2);

        energyMeterDimension = new EnergyMeterDimension(new DimensionCounter(), state, timeDimension, powerDimension);
    }

    @Test
    public void generationBasedOnTimeAndPowerAndResets() {
        Assert.assertEquals("Generation based on time and power.", 2, energyMeterDimension.getGeneration(d1));
    }

    @Test
    public void generationShouldIncreaseWithResets() {
        energyMeterDimension.reset(d1.getID());
        Assert.assertEquals("Generation increases with resets.", 3, energyMeterDimension.getGeneration(d1));
        energyMeterDimension.reset(d1.getID());
        Assert.assertEquals("Generation increases with resets.", 4, energyMeterDimension.getGeneration(d1));

        Assert.assertEquals("Resets affect only reset dwelling.", 2, energyMeterDimension.getGeneration(d2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cannotResetAfterBranch() {
        final EnergyMeterDimension branched = (EnergyMeterDimension) energyMeterDimension.branch(mock(IBranch.class), 1);
        branched.reset(0);
    }

    @Test
    public void getOnUnseenDwellingReturnsTableOfZeroes() {
        final IEnergyMeter meter = energyMeterDimension.get(d1);
        assertEnergyUsageForAllFuels(meter, 0.0);
    }

    @Test
    public void getCaches() {
        final IEnergyMeter meter1 = energyMeterDimension.get(d1);
        final IEnergyMeter meter2 = energyMeterDimension.get(d1);
        Assert.assertSame("If nothing has changed, the energy meter should be the same.", meter1, meter2);
    }

    @Test
    public void getCachesThroughBranch() {
        final IEnergyMeter meter1 = energyMeterDimension.get(d1);
        final IInternalDimension<IEnergyMeter> branched = energyMeterDimension.branch(mock(IBranch.class), 1);
        final IEnergyMeter meter2 = branched.get(d1);
        Assert.assertSame("If nothing has changed, the energy meter should be the same.", meter1, meter2);
    }

    @Test
    public void getMetersEnergyIfTimePasses() {
        energyMeterDimension.get(d1);
        timePasses();

        final IEnergyMeter futureMeter = energyMeterDimension.get(d1);
        assertEnergyUsageForAllFuels(futureMeter, FUTURE_YEARS * ANNUAL_POWER, 2.0);
    }

    @Test
    public void getAfterResetReturnsZeroes() {
        energyMeterDimension.get(d1);
        timePasses();
        energyMeterDimension.reset(d1.getID());

        final IEnergyMeter futureMeter = energyMeterDimension.get(d1);
        assertEnergyUsageForAllFuels(futureMeter, 0.0, 0.0);
    }

    private void timePasses() {
        when(state.getGeneration(timeDimension, d1)).thenReturn(2);
        when(state.get(timeDimension, d1)).thenReturn(TimeUtil.mockTime(FUTURE));
    }

    private void assertEnergyUsageForAllFuels(final IEnergyMeter meter, final double expectedUsage) {
        assertEnergyUsageForAllFuels(meter, expectedUsage, 0.0);
    }

    private void assertEnergyUsageForAllFuels(final IEnergyMeter meter, final double expectedUsage, final double errorDelta) {
        for (final FuelType fuel : FuelType.values()) {
            Assert.assertEquals("Fuel usage should be " + expectedUsage, expectedUsage, meter.getEnergyUseByFuel(fuel), errorDelta);
        }
    }
}
