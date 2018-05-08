package uk.ac.ucl.hideem;

import uk.ac.ucl.hideem.IExposure.OccupancyType;
import uk.ac.ucl.hideem.IExposure.OverheatingAgeBands;

public abstract class HealthOutcome {

    protected final int horizon;

    private final int EXPOSURE_TYPES = IExposure.Type.values().length;
    private final int DISEASE_TYPES = Disease.Type.values().length;
    private final int OCCUPANCY_TYPES = IExposure.OccupancyType.values().length;
    private final int OH_AGEBANDS = OverheatingAgeBands.values().length;

    private final double initialExposureByOccupancyType[][] = new double[EXPOSURE_TYPES][OCCUPANCY_TYPES];
    private final double finalExposureByOccupancyType[][] = new double[EXPOSURE_TYPES][OCCUPANCY_TYPES];
    ;
       
    private final double relativeRisk[][] = new double[DISEASE_TYPES][OCCUPANCY_TYPES];
    private final double overheatingRisk[] = new double[OH_AGEBANDS];

    private final double qalys[] = new double[DISEASE_TYPES];
    private final double morbQalys[] = new double[DISEASE_TYPES];
    private final double cost[] = new double[DISEASE_TYPES];

    public int horizon() {
        return horizon;
    }

    HealthOutcome(final int horizon) {
        this.horizon = horizon;
    }

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

    public final double qaly(final Disease.Type dType) {
        return qalys[dType.ordinal()];
    }

    public final double morbQaly(final Disease.Type dType) {
        return morbQalys[dType.ordinal()];
    }

    public final double cost(final Disease.Type dType) {
        return cost[dType.ordinal()];
    }

    public final void setQaly(final Disease.Type dType, double value) {
        qalys[dType.ordinal()] += value;
    }

    public final void setMorbQaly(final Disease.Type dType, double value) {
        morbQalys[dType.ordinal()] += value;
    }

    public final void setCost(final Disease.Type dType, double value) {
        cost[dType.ordinal()] += value;
    }

    public abstract void addEffects(final Disease.Type disease, final int year, final Person whom,
            final double mortality, final double morbidity, final double cost);

    //print out exposure stuff
    public String printExposures(final String aacode) {
        final StringBuffer sb = new StringBuffer();

        sb.append("AACode");
        for (final IExposure.Type e : IExposure.Type.values()) {
            sb.append(String.format(",Before_%s,After_%s", e, e));
        }

        sb.append(String.format("\n"));

        for (final OccupancyType o : OccupancyType.values()) {
            sb.append(String.format("%s %s", aacode, o));
            for (final IExposure.Type e : IExposure.Type.values()) {
                sb.append(String.format(",%g,%g", initialExposureByOccupancyType[e.ordinal()][o.ordinal()], finalExposureByOccupancyType[e.ordinal()][o.ordinal()]));
            }
            sb.append(String.format("\n"));
        }

        return sb.toString();
    }

    //print out risks
    public String printQalys(final String aacode) {
        final StringBuffer sb = new StringBuffer();

        sb.append("AACode");
        for (final Disease.Type d : Disease.Type.values()) {
            sb.append(String.format(",%s", d));
        }

        sb.append(String.format("\n"));
        sb.append(String.format("%s", aacode));
        for (final Disease.Type d : Disease.Type.values()) {
            sb.append(String.format(",%g", qalys[d.ordinal()]));
        }
        sb.append(String.format("\n"));

        return sb.toString();
    }

    public String printMorbQalys(final String aacode) {
        final StringBuffer sb = new StringBuffer();

        sb.append("AACode");
        for (final Disease.Type d : Disease.Type.values()) {
            sb.append(String.format(",%s", d));
        }

        sb.append(String.format("\n"));
        sb.append(String.format("%s", aacode));
        for (final Disease.Type d : Disease.Type.values()) {
            sb.append(String.format(",%g", morbQalys[d.ordinal()]));
        }
        sb.append(String.format("\n"));

        return sb.toString();
    }

    public String printCost(final String aacode) {
        final StringBuffer sb = new StringBuffer();

        sb.append("AACode");
        for (final Disease.Type d : Disease.Type.values()) {
            sb.append(String.format(",%s", d));
        }

        sb.append(String.format("\n"));
        sb.append(String.format("%s", aacode));
        for (final Disease.Type d : Disease.Type.values()) {
            sb.append(String.format(",%g", cost[d.ordinal()]));
        }
        sb.append(String.format("\n"));

        return sb.toString();
    }

}
