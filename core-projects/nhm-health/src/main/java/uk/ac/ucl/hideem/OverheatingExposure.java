package uk.ac.ucl.hideem;

public class OverheatingExposure implements IExposure {
    @Override
    public void modify(
        // effect of change
        final double t1,
        final double t2,
        final double p1,
        final double p2,

        // details
        final boolean smoker,
        final int mainFloorLevel,
        final BuiltForm.Type builtFormType,
        final BuiltForm.Region region,

        // occupancy, outcome to modify
        final OccupancyType occupancy,
        final HealthOutcome result) {
        // The data does not contain any rows for this type of exposure, we just add it as a constant for all built forms and so on.
        // Overheating Temp isn't dependent exposure coefs so out of loop
        if (matchedExposure == Exposure.Type.SIT2DayMax){
            final double initialSITMax = getSIT2DayMax(e1, doubleGlaz, region);
            final double finalSITMax   = getSIT2DayMax(e2, doubleGlaz, region);
            result.setInitialExposure(matchedExposure, occupancy, initialSITMax);
            result.setFinalExposure(matchedExposure, occupancy, finalSITMax);
            //RR for overheating is age dependent
            for (final Exposure.OverheatingAgeBands ageBand : OverheatingAgeBands.values()) {
                result.setRelativeRisk(Disease.Type.overheating, ageBand,
                                       Disease.Type.overheating.relativeRisk(result, occupancy, region, ageBand));
            }
        }
    }
}
