package uk.ac.ucl.hideem;

import uk.ac.ucl.hideem.Exposure.OccupancyType;

import com.google.common.base.Preconditions;

/**
 * Contents are just some values broken down by some categories. For
 * simplicity, this is done by indexing into some arrays using the
 * ordinals for those categories.
 */
public class HealthOutcome {
    private final double[][][] exposures;
    private final double[][][][] occExposures;
    private final double[][] relativeRisk;
    private final double[][][] mortalityQalys;
    private final double[][][] morbidityQalys;
    private final double[][][] costs;
    final int years;
    final int numberOfOccupants;

    public HealthOutcome(final int years, final int numberOfOccupants) {
        Preconditions.checkArgument(years > 0, "A health outcome must be defined over a positive number of years (%s)", years);
        Preconditions.checkArgument(numberOfOccupants > 0, "A health outcome must be defined for at least one occupant (%s)", numberOfOccupants);
        this.years = years;
        this.numberOfOccupants = numberOfOccupants;
        this.exposures = new double[Exposure.Type.values().length][2][4]; //2 is before/after for 4 occupancies
        this.occExposures = new double[Exposure.Type.values().length][2][years][numberOfOccupants]; //2 is before/after for 4 occupancies
        this.relativeRisk = new double[Disease.Type.values().length][4]; //for the 4 types of occupancy
        this.mortalityQalys = new double[Disease.Type.values().length][years][numberOfOccupants];
        this.morbidityQalys = new double[Disease.Type.values().length][years][numberOfOccupants];
        this.costs = new double[Disease.Type.values().length][years][numberOfOccupants];
    }
          
    public double initialExposure(final Exposure.Type e, final OccupancyType occupancy) {
        Preconditions.checkNotNull(e);
        return exposures[e.ordinal()][0][occupancy.ordinal()];
    }
    
    public double finalExposure(final Exposure.Type e, final OccupancyType occupancy) {
        Preconditions.checkNotNull(e);
        return exposures[e.ordinal()][1][occupancy.ordinal()];
    }

    public double initialOccExposure(final Exposure.Type e, final int year, final int occ) {
        Preconditions.checkNotNull(e);
        return occExposures[e.ordinal()][0][year][occ];
    }
    
    public double finalOccExposure(final Exposure.Type e, final int year, final int occ) {
        Preconditions.checkNotNull(e);
        return occExposures[e.ordinal()][1][year][occ];
    }
    
    public double deltaExposure(final Exposure.Type e, final OccupancyType occupancy) {
        Preconditions.checkNotNull(e);
        return exposures[e.ordinal()][1][occupancy.ordinal()]-exposures[e.ordinal()][0][occupancy.ordinal()];
    }
    
    public double relativeRisk(final Disease.Type disease, final OccupancyType occupancy) {
        Preconditions.checkNotNull(disease);
        return relativeRisk[disease.ordinal()][occupancy.ordinal()];
    }
    
    public double mortalityQalys(final Disease.Type disease, final int year, final int occ) {
        Preconditions.checkNotNull(disease);
        return mortalityQalys[disease.ordinal()][year][occ];
    }
    
    public double morbidityQalys(final Disease.Type disease, final int year, final int occ) {
        Preconditions.checkNotNull(disease);
        return morbidityQalys[disease.ordinal()][year][occ];
    }
    
    public double cost(final Disease.Type disease, final int year, final int occ) {
        Preconditions.checkNotNull(disease);
        Preconditions.checkElementIndex(year, years);
        return costs[disease.ordinal()][year][occ];
    }

    public void setInitialExposure(final Exposure.Type e, final OccupancyType occupancy, final double d) {
        Preconditions.checkNotNull(e);
        this.exposures[e.ordinal()][0][occupancy.ordinal()] = d;
    }

    public void setFinalExposure(final Exposure.Type e, final OccupancyType occupancy, final double d) {
        Preconditions.checkNotNull(e);
        this.exposures[e.ordinal()][1][occupancy.ordinal()] = d;
    }
    
    public void setInitialOccExposure(final Exposure.Type e, final int year, final int occ, final OccupancyType occupancy) {
        Preconditions.checkNotNull(e);
        this.occExposures[e.ordinal()][0][year][occ] = exposures[e.ordinal()][0][occupancy.ordinal()];
    }

    public void setFinalOccExposure(final Exposure.Type e, final int year, final int occ, final OccupancyType occupancy) {
        Preconditions.checkNotNull(e);
        this.occExposures[e.ordinal()][1][year][occ] = exposures[e.ordinal()][1][occupancy.ordinal()];
    }
    
    public void setRelativeRisk(final Disease.Type disease, final OccupancyType occupancy, final double r) {
        Preconditions.checkNotNull(disease);
        this.relativeRisk[disease.ordinal()][occupancy.ordinal()] = r;
    }
    
    public void setMortalityQalys(final Disease.Type disease, final int year, final double q, final int occ) {
        Preconditions.checkNotNull(disease);
        Preconditions.checkElementIndex(year, years);
        Preconditions.checkElementIndex(occ, numberOfOccupants);
        this.mortalityQalys[disease.ordinal()][year][occ] = q;
    }
    
    public void setMorbidityQalys(final Disease.Type disease, final int year, final double q, final int occ) {
        Preconditions.checkNotNull(disease);
        Preconditions.checkElementIndex(occ, numberOfOccupants);
        this.morbidityQalys[disease.ordinal()][year][occ] = q;
    }
    
    public void setCost(final Disease.Type disease, final int year, final double c, final int occ) {
        Preconditions.checkNotNull(disease);
        Preconditions.checkElementIndex(year, years);
        Preconditions.checkElementIndex(occ, numberOfOccupants);
        this.costs[disease.ordinal()][year][occ] = c;
    }

    //print out exposure stuff
    public String printExposures(final String aacode) {
        final StringBuffer sb = new StringBuffer();
        
//        for (final OccupancyType o :  OccupancyType.values()){
//        	sb.append(String.format(",%s_Before,%s_After",o , o));
//        }
//        
//        sb.append(String.format("\n"));
//
//	    for (final Exposure.Type e : Exposure.Type.values()) {
//	    	sb.append(String.format("%s", e));
//	    	for (final OccupancyType o :  OccupancyType.values()){
//	        sb.append(String.format(",%g,%g", 
//	                                exposures[e.ordinal()][0][o.ordinal()],
//	                                exposures[e.ordinal()][1][o.ordinal()]));
//	    	}
//	    	sb.append(String.format("\n"));
//	    }
        
        sb.append("AACode,Occupant,Year");
        for (final Exposure.Type e : Exposure.Type.values()) {
        	sb.append(String.format(",Before_%s,After_%s", e,e));
        }
        
        sb.append(String.format("\n"));
        for (int occ = 0; occ < numberOfOccupants; occ++) {
            for (int i = 0; i < years; i++) {
            	sb.append(String.format("%s,%d,%d",aacode, occ+1, i+1));
    	        for (final Exposure.Type e : Exposure.Type.values()) {
    	            sb.append(String.format(",%g,%g", occExposures[e.ordinal()][0][i][occ], occExposures[e.ordinal()][1][i][occ]));
    	        }      
    	    	sb.append(String.format("\n"));
    	    }
        }

        return sb.toString();
    }
        
    //print out qalys
    public String printQalys(final String aacode) {
        final StringBuffer sb = new StringBuffer();
        
        sb.append("AACode,Occupant,Year");
        for (final Disease.Type d : Disease.Type.values()) {
        	sb.append(String.format(",%s", d));
        }
        
        sb.append(String.format("\n"));
        for (int occ = 0; occ < numberOfOccupants; occ++) {
            for (int i = 0; i < years; i++) {
            	sb.append(String.format("%s,%d,%d",aacode, occ+1, i+1));
    	        for (final Disease.Type d : Disease.Type.values()) {
    	            sb.append(String.format(",%g", mortalityQalys[d.ordinal()][i][occ]));
    	        }      
    	    	sb.append(String.format("\n"));
    	    }
        }

        return sb.toString();
    }
    
    //print out qalys
    public String printMorbidityQalys(final String aacode) {
        final StringBuffer sb = new StringBuffer();
        
        sb.append("AACode,Occupant,Year");
        for (final Disease.Type d : Disease.Type.values()) {
        	sb.append(String.format(",%s", d));
        }
        
        sb.append(String.format("\n"));
        for (int occ = 0; occ < numberOfOccupants; occ++) {
            for (int i = 0; i < years; i++) {
            	sb.append(String.format("%s,%d,%d",aacode, occ+1, i+1));
    	        for (final Disease.Type d : Disease.Type.values()) {
    	            sb.append(String.format(",%g", morbidityQalys[d.ordinal()][i][occ]));
    	        }      
    	    	sb.append(String.format("\n"));
    	    }
        }

        return sb.toString();
    }
    
    //print out costs
    public String printCosts(final String aacode) {
        final StringBuffer sb = new StringBuffer();
        
        sb.append("AACode,Occupant,Year");
        for (final Disease.Type d : Disease.Type.values()) {
        	sb.append(String.format(",%s", d));
        }
        
        sb.append(String.format("\n"));
        for (int occ = 0; occ < numberOfOccupants; occ++) {
            for (int i = 0; i < years; i++) {
            	sb.append(String.format("%s,%d,%d",aacode, occ+1, i+1));
    	        for (final Disease.Type d : Disease.Type.values()) {
    	            sb.append(String.format(",%g", costs[d.ordinal()][i][occ]));
    	        }      
    	    	sb.append(String.format("\n"));
    	    }
        }

        return sb.toString();
    }
    
    
    
//        
//        sb.append("\t\tYear");
//        for (final Disease.Type d : Disease.Type.values()) {
//            sb.append(" & \t"+ d);
//        }
//        sb.append("\n");
//        sb.append("\t Relative Risks:\n\t\t");
//        for (final Disease.Type d : Disease.Type.values()) {
//        	//just print RR for one type of occupancy
//            sb.append(String.format("\t%g", relativeRisk[d.ordinal()][1]));
//        }
//        sb.append("\n");
//        sb.append("\t Mortality Qalys:\n");
//        for (int i = 0; i < years; i++) {
//        	sb.append(String.format("\t\t%d", i+1));
//	        for (final Disease.Type d : Disease.Type.values()) {
//	            sb.append(String.format(" & \t%g", mortalityQalys[d.ordinal()][i]));
//	        }
//	        sb.append("\\\\ \n");
//        }
//        //sb.append("\n");
//        
//        //Cumulative effect
//        sb.append("\t Cumulative Mortality Qalys:\n");
//        sb.append(String.format("\t\ttot"));
//        final double[] cumulativeMortality = new double[Disease.Type.values().length];
//        for (int i = 0; i < years; i++) {
//        	
//	        for (final Disease.Type d : Disease.Type.values()) {
//	        	cumulativeMortality[d.ordinal()] = cumulativeMortality[d.ordinal()]+mortalityQalys[d.ordinal()][i];    
//	        }
//        }
//        
//	    for (final Disease.Type d : Disease.Type.values()) {
//		   sb.append(String.format(" & \t%g", cumulativeMortality[d.ordinal()]));
//	    }
//	    
//	    sb.append("\\\\ \n");
//        
//	    //Morbidity QALYS
//	    sb.append("\t Morbidity Qalys:\n");
//        for (int i = 0; i < years; i++) {
//        	sb.append(String.format("\t\t%d", i+1));
//	        for (final Disease.Type d : Disease.Type.values()) {
//	            sb.append(String.format(" & \t%g", morbidityQalys[d.ordinal()][i]));
//	        }
//	        sb.append("\\\\ \n");
//        }
//        
//        
//        //Cumulative effect
//        sb.append("\t Cumulative Morbidity Qalys:\n");
//        sb.append(String.format("\t\ttot"));
//        final double[] cumulativeMorbidity = new double[Disease.Type.values().length];
//        for (int i = 0; i < years; i++) {
//        	
//	        for (final Disease.Type d : Disease.Type.values()) {
//	        	cumulativeMorbidity[d.ordinal()] = cumulativeMorbidity[d.ordinal()]+morbidityQalys[d.ordinal()][i];    
//	        }
//        }
//        
//	    for (final Disease.Type d : Disease.Type.values()) {
//		   sb.append(String.format(" & \t%g", cumulativeMorbidity[d.ordinal()]));
//	    }
//	    
//	    sb.append("\\\\ \n");
//        //print costs.
//	    sb.append("\t Cost:\n");
//        for (int i = 0; i < years; i++) {
//        	sb.append(String.format("\t\t%d", i+1));
//	        for (final Disease.Type d : Disease.Type.values()) {
//	            sb.append(String.format(" & \t%g", costs[d.ordinal()][i]));
//	        }
//	        sb.append("\\\\ \n");
//        }
//
//        //Cumulative effect
//        sb.append("\t Cumulative Cost:\n");
//        sb.append(String.format("\t\ttot"));
//        final double[] cumulativeCost = new double[Disease.Type.values().length];
//        for (int i = 0; i < years; i++) {
//        	
//	        for (final Disease.Type d : Disease.Type.values()) {
//	        	cumulativeCost[d.ordinal()] = cumulativeCost[d.ordinal()]+costs[d.ordinal()][i];    
//	        }
//        }
//        
//	    for (final Disease.Type d : Disease.Type.values()) {
//		   sb.append(String.format(" & \t%g", cumulativeCost[d.ordinal()]));
//	    }
//	    
//	    sb.append("\\\\ \n");

}
