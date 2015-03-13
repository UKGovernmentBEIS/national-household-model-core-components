package uk.ac.ucl.hideem;

import com.google.common.base.Preconditions;

/**
 * Contents are just some values broken down by some categories. For
 * simplicity, this is done by indexing into some arrays using the
 * ordinals for those categories.
 */
public class HealthOutcome {
    private double[][] exposures;
    private double[] relativeRisk;
    private double[][] mortalityQalys;
    private double[] morbidityQalys;
    private double[][][] costs;
    final int years;

    public HealthOutcome(final int years) {
        Preconditions.checkArgument(years > 0, "A health outcome must be defined over a positive number of years (%s)", years);
        this.years = years;
        this.exposures = new double[Exposure.Type.values().length][2];
        this.relativeRisk = new double[Disease.Type.values().length];
        this.mortalityQalys = new double[Disease.Type.values().length][years];
        this.morbidityQalys = new double[Disease.Type.values().length];
        this.costs = new double[Disease.Type.values().length]
            [HealthCost.values().length]
            [years];
    }
    
    public HealthOutcome add(HealthOutcome outcome, final int years) {
    	Preconditions.checkArgument(years > 0, "A health outcome must be defined over a positive number of years (%s)", years);
    	
    	for (int i = 0; i < Exposure.Type.values().length; ++i) {
        	for (int j = 0; j < 2; ++j) {
        		this.exposures[i][j] += outcome.exposures[i][j];
        	}
        }
    	
        for (int i = 0; i < Disease.Type.values().length; ++i) {
        	for (int j = 0; j < years; ++j) {
        		this.mortalityQalys[i][j] += outcome.mortalityQalys[i][j];
        	}
        }

        return this;
    }
    
    public double initialExposure(final Exposure.Type e) {
        Preconditions.checkNotNull(e);
        return exposures[e.ordinal()][0];
    }
    
    public double finalExposure(final Exposure.Type e) {
        Preconditions.checkNotNull(e);
        return exposures[e.ordinal()][1];
    }
    
    public double deltaExposure(final Exposure.Type e) {
        Preconditions.checkNotNull(e);
        return exposures[e.ordinal()][1]-exposures[e.ordinal()][0];
    }
    
    public double relativeRisk(final Disease.Type disease) {
        Preconditions.checkNotNull(disease);
        return relativeRisk[disease.ordinal()];
    }
    
    public double mortalityQalys(final Disease.Type disease, final int year) {
        Preconditions.checkNotNull(disease);
        return mortalityQalys[disease.ordinal()][year];
    }
    
    public double morbidityQalys(final Disease.Type disease) {
        Preconditions.checkNotNull(disease);
        return morbidityQalys[disease.ordinal()];
    }
    
    public double cost(final Disease.Type disease, final HealthCost cost, final int year) {
        Preconditions.checkNotNull(disease);
        Preconditions.checkNotNull(cost);
        Preconditions.checkElementIndex(year, years);
        return costs[disease.ordinal()][cost.ordinal()][year];
    }

    public void setInitialExposure(final Exposure.Type e, final double d) {
        Preconditions.checkNotNull(e);
        this.exposures[e.ordinal()][0] = d;
    }

    public void setFinalExposure(final Exposure.Type e, final double d) {
        Preconditions.checkNotNull(e);
        this.exposures[e.ordinal()][1] = d;
    }
    
    public void setRelativeRisk(final Disease.Type disease, final double r) {
        Preconditions.checkNotNull(disease);
        this.relativeRisk[disease.ordinal()] = r;
    }
    
    public void setMortalityQalys(final Disease.Type disease, final int year, final double q) {
        Preconditions.checkNotNull(disease);
        Preconditions.checkElementIndex(year, years);
        this.mortalityQalys[disease.ordinal()][year] += q;
    }
    
    public void setMorbidityQalys(final Disease.Type disease, final double q) {
        Preconditions.checkNotNull(disease);
        this.morbidityQalys[disease.ordinal()] += q;
    }
    
    public void setCost(final Disease.Type disease, final HealthCost cost, final int year, final double c) {
        Preconditions.checkNotNull(disease);
        Preconditions.checkNotNull(cost);
        Preconditions.checkElementIndex(year, years);
        this.costs[disease.ordinal()][cost.ordinal()][year] = c;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();

        sb.append("Health Outcome for " + years + " years:\n");
        sb.append("\tExposures:\n");
        for (final Exposure.Type e : Exposure.Type.values()) {
            sb.append(String.format("\t\t%s: %g -> %g\n", e,
                                    exposures[e.ordinal()][0],
                                    exposures[e.ordinal()][1]));
        }
        
        sb.append("\t\tYear");
        for (final Disease.Type d : Disease.Type.values()) {
            sb.append("\t"+ d);
        }
        sb.append("\n");
        sb.append("\t Relative Risks:\n\t\t");
        for (final Disease.Type d : Disease.Type.values()) {
            sb.append(String.format("\t%g", relativeRisk[d.ordinal()]));
        }
        sb.append("\n");
        sb.append("\t Mortality Qalys:\n");
        for (int i = 0; i < years; i++) {
        	sb.append(String.format("\t\t%d", i+1));
	        for (final Disease.Type d : Disease.Type.values()) {
	            sb.append(String.format("\t%g", mortalityQalys[d.ordinal()][i]));
	        }
	        sb.append("\n");
        }
        //sb.append("\n");
        
        //Cumulative effect
        sb.append("\t Cumulative Mortality Qalys:\n");
        sb.append(String.format("\t\ttot"));
        double[] cumulativeMortality = new double[Disease.Type.values().length];
        for (int i = 0; i < years; i++) {
        	
	        for (final Disease.Type d : Disease.Type.values()) {
	        	cumulativeMortality[d.ordinal()] = cumulativeMortality[d.ordinal()]+mortalityQalys[d.ordinal()][i];    
	        }
        }
        
	    for (final Disease.Type d : Disease.Type.values()) {
		   sb.append(String.format("\t%g", cumulativeMortality[d.ordinal()]));
	    }
	    
	    sb.append("\n");
        
	    //Morbidity QALYS
//        sb.append("\t Morbidity Qalys:\n");
//
//        sb.append(String.format("\t\t%d", years));
//        for (final Disease.Type d : Disease.Type.values()) {
//            sb.append(String.format("\t%g", morbidityQalys[d.ordinal()]));
//        }
//        sb.append("\n");

        //TODO print costs.
        
        return sb.toString();
    }
}
