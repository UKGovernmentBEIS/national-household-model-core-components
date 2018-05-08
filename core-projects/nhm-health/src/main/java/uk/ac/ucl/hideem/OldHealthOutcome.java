package uk.ac.ucl.hideem;

import com.google.common.base.Preconditions;

/**
 * Contents are just some values broken down by some categories. For simplicity,
 * this is done by indexing into some arrays using the ordinals for those
 * categories.
 */
public class OldHealthOutcome {

    private final double[][][] exposures;
    private final double[][][][] occExposures;
    private final double[][] relativeRisk;
    private final double[][][] mortalityQalys;
    private final double[][][] morbidityQalys;
    private final double[][][] costs;
    final int years;
    final int numberOfOccupants;

    public OldHealthOutcome(final int years, final int numberOfOccupants) {
        Preconditions.checkArgument(years > 0, "A health outcome must be defined over a positive number of years (%s)", years);
        Preconditions.checkArgument(numberOfOccupants > 0, "A health outcome must be defined for at least one occupant (%s)", numberOfOccupants);
        this.years = years;
        this.numberOfOccupants = numberOfOccupants;
        this.exposures = new double[IExposure.Type.values().length][2][4]; //2 is before/after for 4 occupancies
        this.occExposures = new double[IExposure.Type.values().length][2][years][numberOfOccupants]; //2 is before/after for 4 occupancies
        this.relativeRisk = new double[Disease.Type.values().length][4]; //for the 4 types of occupancy
        this.mortalityQalys = new double[Disease.Type.values().length][years][numberOfOccupants];
        this.morbidityQalys = new double[Disease.Type.values().length][years][numberOfOccupants];
        this.costs = new double[Disease.Type.values().length][years][numberOfOccupants];
    }

    // public double initialExposure(final IExposure.Type e, final OccupancyType occupancy) {
    //     Preconditions.checkNotNull(e);
    //     return exposures[e.ordinal()][0][occupancy.ordinal()];
    // }
    // public double finalExposure(final IExposure.Type e, final OccupancyType occupancy) {
    //     Preconditions.checkNotNull(e);
    //     return exposures[e.ordinal()][1][occupancy.ordinal()];
    // }
    // public double initialOccExposure(final IExposure.Type e, final int year, final int occ) {
    //     Preconditions.checkNotNull(e);
    //     return occExposures[e.ordinal()][0][year][occ];
    // }
    // public double finalOccExposure(final IExposure.Type e, final int year, final int occ) {
    //     Preconditions.checkNotNull(e);
    //     return occExposures[e.ordinal()][1][year][occ];
    // }
    // public double deltaExposure(final IExposure.Type e, final OccupancyType occupancy) {
    //     Preconditions.checkNotNull(e);
    //     return exposures[e.ordinal()][1][occupancy.ordinal()]-exposures[e.ordinal()][0][occupancy.ordinal()];
    // }
    // public double relativeRisk(final Disease.Type disease, final OccupancyType occupancy) {
    //     Preconditions.checkNotNull(disease);
    //     return relativeRisk[disease.ordinal()][occupancy.ordinal()];
    // }
    // //Also have an over heating RR dep on age band
    // public double relativeRisk(final Disease.Type disease, final IExposure.OverheatingAgeBands ageBand) {
    //     Preconditions.checkNotNull(disease);
    //     return relativeRisk[disease.ordinal()][ageBand.ordinal()];
    // }
    // public double mortalityQalys(final Disease.Type disease, final int year, final int occ) {
    //     Preconditions.checkNotNull(disease);
    //     return mortalityQalys[disease.ordinal()][year][occ];
    // }
    // public double morbidityQalys(final Disease.Type disease, final int year, final int occ) {
    //     Preconditions.checkNotNull(disease);
    //     return morbidityQalys[disease.ordinal()][year][occ];
    // }
    // public double cost(final Disease.Type disease, final int year, final int occ) {
    //     Preconditions.checkNotNull(disease);
    //     Preconditions.checkElementIndex(year, years);
    //     return costs[disease.ordinal()][year][occ];
    // }
    // public void setInitialExposure(final IExposure.Type e, final OccupancyType occupancy, final double d) {
    //     Preconditions.checkNotNull(e);
    //     this.exposures[e.ordinal()][0][occupancy.ordinal()] = d;
    // }
    // public void setFinalExposure(final IExposure.Type e, final OccupancyType occupancy, final double d) {
    //     Preconditions.checkNotNull(e);
    //     this.exposures[e.ordinal()][1][occupancy.ordinal()] = d;
    // }
    // public void setInitialOccExposure(final IExposure.Type e, final int year, final int occ, final OccupancyType occupancy) {
    //     Preconditions.checkNotNull(e);
    //     this.occExposures[e.ordinal()][0][year][occ] = exposures[e.ordinal()][0][occupancy.ordinal()];
    // }
    // public void setFinalOccExposure(final IExposure.Type e, final int year, final int occ, final OccupancyType occupancy) {
    //     Preconditions.checkNotNull(e);
    //     this.occExposures[e.ordinal()][1][year][occ] = exposures[e.ordinal()][1][occupancy.ordinal()];
    // }
    // public void setRelativeRisk(final Disease.Type disease, final OccupancyType occupancy, final double r) {
    //     Preconditions.checkNotNull(disease);
    //     this.relativeRisk[disease.ordinal()][occupancy.ordinal()] = r;
    // }
    // public void setRelativeRisk(final Disease.Type disease, final OverheatingAgeBands ageBand, final double r) {
    //     Preconditions.checkNotNull(disease);
    //     this.relativeRisk[disease.ordinal()][ageBand.ordinal()] = r;
    // }
    // public void setMortalityQalys(final Disease.Type disease, final int year, final double q, final int occ) {
    //     Preconditions.checkNotNull(disease);
    //     Preconditions.checkElementIndex(year, years);
    //     Preconditions.checkElementIndex(occ, numberOfOccupants);
    //     this.mortalityQalys[disease.ordinal()][year][occ] = q;
    // }
    // public void setMorbidityQalys(final Disease.Type disease, final int year, final double q, final int occ) {
    //     Preconditions.checkNotNull(disease);
    //     Preconditions.checkElementIndex(occ, numberOfOccupants);
    //     this.morbidityQalys[disease.ordinal()][year][occ] = q;
    // }
    // public void setCost(final Disease.Type disease, final int year, final double c, final int occ) {
    //     Preconditions.checkNotNull(disease);
    //     Preconditions.checkElementIndex(year, years);
    //     Preconditions.checkElementIndex(occ, numberOfOccupants);
    //     this.costs[disease.ordinal()][year][occ] = c;
    // }
    // //print out exposure stuff
    // public String printExposures(final String aacode) {
    //     final StringBuffer sb = new StringBuffer();
    //     sb.append("AACode,Occupant,Year");
    //     for (final IExposure.Type e : IExposure.Type.values()) {
    //     	sb.append(String.format(",Before_%s,After_%s", e,e));
    //     }
    //     sb.append(String.format("\n"));
    //     for (int occ = 0; occ < numberOfOccupants; occ++) {
    //         for (int i = 0; i < years; i++) {
    //         	sb.append(String.format("%s,%d,%d",aacode, occ+1, i+1));
    //             for (final IExposure.Type e : IExposure.Type.values()) {
    // 	            sb.append(String.format(",%g,%g", occExposures[e.ordinal()][0][i][occ], occExposures[e.ordinal()][1][i][occ]));
    // 	        }
    // 	    	sb.append(String.format("\n"));
    // 	    }
    //     }
    //     return sb.toString();
    // }
    // //print out qalys
    // public String printQalys(final String aacode) {
    //     final StringBuffer sb = new StringBuffer();
    //     sb.append("AACode,Occupant,Year");
    //     for (final Disease.Type d : Disease.Type.values()) {
    //     	sb.append(String.format(",%s", d));
    //     }
    //     sb.append(String.format("\n"));
    //     for (int occ = 0; occ < numberOfOccupants; occ++) {
    //         for (int i = 0; i < years; i++) {
    //         	sb.append(String.format("%s,%d,%d",aacode, occ+1, i+1));
    // 	        for (final Disease.Type d : Disease.Type.values()) {
    // 	            sb.append(String.format(",%g", mortalityQalys[d.ordinal()][i][occ]));
    // 	        }
    // 	    	sb.append(String.format("\n"));
    // 	    }
    //     }
    //     return sb.toString();
    // }
    // //print out qalys
    // public String printMorbidityQalys(final String aacode) {
    //     final StringBuffer sb = new StringBuffer();
    //     sb.append("AACode,Occupant,Year");
    //     for (final Disease.Type d : Disease.Type.values()) {
    //     	sb.append(String.format(",%s", d));
    //     }
    //     sb.append(String.format("\n"));
    //     for (int occ = 0; occ < numberOfOccupants; occ++) {
    //         for (int i = 0; i < years; i++) {
    //         	sb.append(String.format("%s,%d,%d",aacode, occ+1, i+1));
    // 	        for (final Disease.Type d : Disease.Type.values()) {
    // 	            sb.append(String.format(",%g", morbidityQalys[d.ordinal()][i][occ]));
    // 	        }
    // 	    	sb.append(String.format("\n"));
    // 	    }
    //     }
    //     return sb.toString();
    // }
    // //print out costs
    // public String printCosts(final String aacode) {
    //     final StringBuffer sb = new StringBuffer();
    //     sb.append("AACode,Occupant,Year");
    //     for (final Disease.Type d : Disease.Type.values()) {
    //     	sb.append(String.format(",%s", d));
    //     }
    //     sb.append(String.format("\n"));
    //     for (int occ = 0; occ < numberOfOccupants; occ++) {
    //         for (int i = 0; i < years; i++) {
    //         	sb.append(String.format("%s,%d,%d",aacode, occ+1, i+1));
    // 	        for (final Disease.Type d : Disease.Type.values()) {
    // 	            sb.append(String.format(",%g", costs[d.ordinal()][i][occ]));
    // 	        }
    // 	    	sb.append(String.format("\n"));
    // 	    }
    //     }
    //     return sb.toString();
    // }
}
