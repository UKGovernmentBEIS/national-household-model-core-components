package uk.org.cse.nhm.energycalculator.impl;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;

public class ClimateParametersTest {

    static class TestWeather implements IWeather {

        public TestWeather(double flux) {
            super();
            this.flux = flux;
        }

        private double flux;

        @Override
        public double getExternalTemperature(MonthType month) {
            return 0;
        }

        @Override
        public double getHorizontalSolarFlux(MonthType month) {
            return flux;
        }

        @Override
        public double getWindSpeed(MonthType month) {
            return 0;
        }

    }

    @Test
    public void testInsolationAtAngle() {
        // insolation on the flat is unchanged.
        Assert.assertEquals(100d, getInsolation(0, 0, 100), 0.01);
        Assert.assertEquals(100d, getInsolation(0, Math.PI, 100), 0.01);
    }

    private double getInsolation(final double horizontal, final double vertical, final double onPlane) {
        final BREDEMHeatingSeasonalParameters p = new BREDEMHeatingSeasonalParameters(
                MonthType.January,
                new TestWeather(onPlane),
                0, null
        );

        return p.getSolarFlux(horizontal, vertical);
    }
}
