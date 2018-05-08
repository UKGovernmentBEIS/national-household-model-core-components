package uk.org.cse.nhm.simulator.state.dimensions.energy;

import static org.mockito.Mockito.mock;
import static uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyTestHelper.FAR_FUTURE;
import static uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyTestHelper.FAR_FUTURE_YEARS;
import static uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyTestHelper.FUTURE;
import static uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyTestHelper.FUTURE_YEARS;
import static uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyTestHelper.START;
import static uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyTestHelper.mockPowerTable;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class EnergyMeterTest {

    @Test
    public void meterStartsWithNoEnergyUsed() {
        IEnergyMeter meter = EnergyMeter.start(START, mock(IPowerTable.class));
        assertEnergyUsageForAllFuels(meter, 0.0, 0.0);
    }

    private void assertEnergyUsageForAllFuels(IEnergyMeter meter, double expectedEnergy, double error_delta) {
        for (FuelType fuel : FuelType.values()) {
            Assert.assertEquals("Starting energy should be " + expectedEnergy, expectedEnergy, meter.getEnergyUseByFuel(fuel), error_delta);
        }
    }

    @Test
    public void integrateWithNoTimePassedShouldProduceNoEnergy() {
        IPowerTable powerTable = mockPowerTable(10000.0f);
        EnergyMeter meter = (EnergyMeter) EnergyMeter.start(START, powerTable);

        IEnergyMeter result = meter.integrateAndUpdate(START, mock(IPowerTable.class));
        assertEnergyUsageForAllFuels(result, 0.0, 0.0);
    }

    @Test
    public void integratingIntoTheFutureUsesOldPowerValueToGenerateEnergy() {
        double startPower = 10000.0;

        IPowerTable powerTable1 = mockPowerTable(startPower);
        EnergyMeter meter = (EnergyMeter) EnergyMeter.start(START, powerTable1);

        double futurePower = 5000.0;
        IPowerTable powerTable2 = mockPowerTable(futurePower);
        meter = (EnergyMeter) meter.integrateAndUpdate(FUTURE, powerTable2);
        assertEnergyUsageForAllFuels(meter, FUTURE_YEARS * startPower, 2);

        meter = (EnergyMeter) meter.integrateAndUpdate(FAR_FUTURE, mock(IPowerTable.class));
        assertEnergyUsageForAllFuels(meter, (FUTURE_YEARS * startPower) + (FAR_FUTURE_YEARS * futurePower), 100);
    }
}
