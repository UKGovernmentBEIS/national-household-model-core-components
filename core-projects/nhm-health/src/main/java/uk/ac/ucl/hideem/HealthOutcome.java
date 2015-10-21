package uk.ac.ucl.hideem;

import uk.ac.ucl.hideem.IExposure.*;

public abstract class HealthOutcome {
    private final int EXPOSURE_TYPES = IExposure.Type.values().length;
    private final int DISEASE_TYPES = Disease.Type.values().length;
    private final int OCCUPANCY_TYPES = IExposure.OccupancyType.values().length;
    private final int OH_AGEBANDS = OverheatingAgeBands.values().length;

    private final double initialExposureByOccupancyType[][] = new double[EXPOSURE_TYPES][OCCUPANCY_TYPES];
    private final double finalExposureByOccupancyType[][] = new double[EXPOSURE_TYPES][OCCUPANCY_TYPES];;

    private final double relativeRisk[][] = new double[DISEASE_TYPES][OCCUPANCY_TYPES];
    private final double overheatingRisk[] = new double[OH_AGEBANDS];

    public final double initialExposure(final IExposure.Type eType, final OccupancyType oType) {
        return initialExposureByOccupancyType[eType.ordinal()][oType.ordinal()];
    }

    public final double finalExposure(final IExposure.Type eType, final OccupancyType oType) {
        return finalExposureByOccupancyType[eType.ordinal()][oType.ordinal()];
    }

    public final double deltaExposure(final IExposure.Type eType, final OccupancyType oType) {
        return finalExposure(eType, oType) - initialExposure(eType, oType);
    }

    public final double relativeRisk(final Disease.Type dType, final OccupancyType oType) {
        return relativeRisk[dType.ordinal()][oType.ordinal()];
    }

    public final void setRelativeRisk(final Disease.Type dType, final OccupancyType oType, double value) {
        relativeRisk[dType.ordinal()][oType.ordinal()] = value;
    }

    public final double overheatingRisk(final OverheatingAgeBands band) {
        return overheatingRisk[band.ordinal()];
    }

    public final void setOverheatingRisk(final OverheatingAgeBands band, final double risk) {
        this.overheatingRisk[band.ordinal()] = risk;
    }

    public final void setExposures(final IExposure.Type eType,
                                   final OccupancyType oType,
                                   final double initial,
                                   final double final_) {
        initialExposureByOccupancyType[eType.ordinal()][oType.ordinal()] = initial;
        finalExposureByOccupancyType[eType.ordinal()][oType.ordinal()] = final_;
    }

    public abstract void addEffects(final Disease.Type disease, final int year, final Person whom,
                                    final double mortality, final double morbidity, final double cost);
}
