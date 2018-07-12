package uk.ac.ucl.hideem;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.BuiltFormType;

public class OverheatingExposure implements IExposure {

    @Override
    public void modify(
            final double[] coefsVent1,
            // effect of change
            final double t1,
            final double t2,
            final double p1,
            final double p2,
            final double e1,
            final double e2,
            // details
            final boolean smoker,
            final int mainFloorLevel,
            final BuiltFormType builtFormType,
            final RegionType region,
            final boolean wasDoubleGlazed,
            final boolean isDoubleGlazed,
            // occupancy, outcome to modify
            final OccupancyType occupancy,
            final HealthOutcome result) {
        // The data does not contain any rows for this type of exposure, we just add it as a constant for all built forms and so on.
        // Overheating Temp isn't dependent exposure coefs so out of loop

        final double initialSITMax = getSIT2DayMax(e1, wasDoubleGlazed, region);
        final double finalSITMax = getSIT2DayMax(e2, isDoubleGlazed, region);

        result.setExposures(Type.SIT2DayMax, occupancy, initialSITMax, finalSITMax);

        // RR for overheating is age dependent
        for (final OverheatingAgeBands ageBand : OverheatingAgeBands.values()) {
            result.setOverheatingRisk(ageBand,
                    Disease.Type.overheating.relativeRisk(result, occupancy, region, ageBand));
        }
    }

    //array of values for different exposure occupancies (not needed for this)
    @Override
    public double[] getCoefs(final OccupancyType occupancy) {
        return new double[]{0, 0, 0, 0, 0};
    }

    private double getSIT2DayMax(
            final double heatLoss,
            final boolean isDoubleGlazed,
            final RegionType region) {

        final double SITMax
                = Constants.SIT_MAX_INTERCEPT
                + heatLoss * Constants.HEAT_LOSS
                + (isDoubleGlazed ? Constants.DBL_GLAZ : 0)
                + Constants.OVERHEAT_THRESH[region.ordinal()] * Constants.AVG_2DAY_P95
                + Constants.OVERHEAT_COEFS[region.ordinal()];

        return SITMax;
    }
}
