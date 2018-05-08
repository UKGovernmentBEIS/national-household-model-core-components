package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;

public class BREDEMHeatingSeasonalParameters extends SeasonalParameters {

    private IHeatingSchedule z1;

    public BREDEMHeatingSeasonalParameters(MonthType month, IWeather weather, double latitude, final IHeatingSchedule z1) {
        super(month, weather, latitude);
        this.z1 = z1;
    }

    @Override
    public IHeatingSchedule getZone1HeatingSchedule() {
        return z1;
    }

    @Override
    public IHeatingSchedule getZone2HeatingSchedule(Zone2ControlParameter control) {
        return z1;
    }

    @Override
    public boolean isHeatingOn() {
        return z1.isHeatingOn();
    }

    @Override
    public double getHeatingOnFactor(IInternalParameters parameters, ISpecificHeatLosses losses, double revisedGains,
            double[] demandTemperature) {
        return BREDEMHeatingOn.getHeatingOnFactor(parameters, losses, revisedGains, demandTemperature, getExternalTemperature());
    }

}
