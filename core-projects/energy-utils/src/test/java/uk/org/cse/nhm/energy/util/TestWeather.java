package uk.org.cse.nhm.energy.util;

import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;

public class TestWeather implements IWeather {

    private double temp;
    private double wind;
    private double ins;

    public TestWeather(double temp, double wind, double ins) {
        this.temp = temp;
        this.wind = wind;
        this.ins = ins;
    }

    @Override
    public double getExternalTemperature(MonthType month) {
        return temp;
    }

    @Override
    public double getHorizontalSolarFlux(MonthType month) {
        return ins;
    }

    @Override
    public double getWindSpeed(MonthType month) {
        return wind;
    }

}
