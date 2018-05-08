package uk.org.cse.nhm.energycalculator.impl.demands;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.org.cse.nhm.energycalculator.impl.testutil.TestUtil.around;

import org.junit.Before;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;

public class HotWaterDemandTest {

    private HotWaterDemand09 demand;
    private IEnergyCalculatorHouseCase house;
    private IInternalParameters params;
    private IEnergyState energyState;
    private IBredemShower shower;
    private double occupancy;
    private ISeasonalParameters climate;
    private double usageFactor;

    @Before
    public void setup() {
        shower = mock(IBredemShower.class);
        demand = new HotWaterDemand09(DefaultConstants.INSTANCE, shower);

        house = mock(IEnergyCalculatorHouseCase.class);
        params = mock(IInternalParameters.class);

        when(params.getConstants()).thenReturn(DefaultConstants.INSTANCE);

        occupancy = 2d;
        when(params.getNumberOfOccupants()).thenReturn(occupancy);

        climate = mock(ISeasonalParameters.class);
        when(params.getClimate()).thenReturn(climate);
        when(climate.getMonthOfYear()).thenReturn(1);
        usageFactor = 1.1;

        energyState = mock(IEnergyState.class);
    }

    @Test
    public void testSAPDemand() {
        test(EnergyCalculatorType.SAP2012, usageFactor * (36 + (25 * occupancy)));
    }

    @Test
    public void testBREDEMDemand() {
        final double expectedBath = 50.8 * (0.19 + (0.13 * occupancy));
        final double expectedShower = 1;
        final double expectedOther = 14 + (9.8 * occupancy);

        when(shower.numShowers(occupancy)).thenReturn(1.0);
        when(shower.hotWaterVolumePerShower()).thenReturn(expectedShower);

        test(EnergyCalculatorType.BREDEM2012, usageFactor * (expectedBath + expectedShower + expectedOther));
    }

    private void test(EnergyCalculatorType calc, double expectedVolume) {
        when(params.getCalculatorType()).thenReturn(calc);
        demand.generate(house, params, null, energyState);

        verify(energyState).increaseDemand(eq(EnergyType.DemandsHOT_WATER_VOLUME), around(expectedVolume, 0.01));
        verify(energyState).increaseDemand(eq(EnergyType.DemandsHOT_WATER), around(expectedVolume * 41.2 * 0.85 * (4.19 / 3600) * (1000 / 24.0), 0.01));
    }
}
