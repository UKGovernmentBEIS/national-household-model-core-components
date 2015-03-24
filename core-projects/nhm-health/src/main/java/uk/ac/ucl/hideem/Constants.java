package uk.ac.ucl.hideem;

import uk.ac.ucl.hideem.Exposure.Type;

public class Constants {
	/**
	 * A risk constant ties together the two parameters for the log ratio with the exposure type.
	 * 
	 * The useful part is {@link #riskDueTo(HealthOutcome)}, which computes the risk resulting from a change in exposure in the given health outcome.
	 */
	public enum RiskConstant {
		INPM_CP(Constants.REL_RISK_PM_CP, Constants.INC_PM_CP, Exposure.Type.INPM2_5),
		OUTPM_CP(Constants.REL_RISK_PM_CP, Constants.INC_PM_CP, Exposure.Type.OUTPM2_5),
		ETS_CA(Constants.REL_RISK_ETS_CA, Constants.INC_ETS_CA, Exposure.Type.ETS),
		INPM_LC(Constants.REL_RISK_PM_LC, Constants.INC_PM_LC, Exposure.Type.INPM2_5),
		OUTPM_LC(Constants.REL_RISK_PM_LC, Constants.INC_PM_LC, Exposure.Type.OUTPM2_5),
		RADON_LC(Constants.REL_RISK_RADON_LC, Constants.INC_RADON_LC, Exposure.Type.Radon),
		ETS_MI(Constants.REL_RISK_ETS_MI, Constants.INC_ETS_MI, Exposure.Type.ETS),
		SIT_CV(Constants.REL_RISK_SIT_CV, Constants.INC_WINCV, Exposure.Type.SIT);
		
		private final double logRatio;
		private final Type exposureType;
		
		private RiskConstant(final double rel, final double inc, final Exposure.Type exposureType) {
			this.logRatio = Math.log(rel) / inc;
			this.exposureType = exposureType;
		}
		
		public double riskDueTo(final HealthOutcome result) {
			return Math.exp(logRatio * result.deltaExposure(exposureType));
		}
	}
	
    //Health coefficients
	public static final double REL_RISK_SIT_CV 	= 	0.913043;
	public static final double REL_RISK_ETS_CA 		= 	1.25;
    public static final double REL_RISK_ETS_MI 		= 	1.3;
    public static final double REL_RISK_PM_CP 		= 	1.082;
    public static final double REL_RISK_PM_LC		= 	1.059;
    public static final double REL_RISK_RADON_LC 	= 	1.16;
    public static final double REL_RISK_SIT_CMD 	= 	0.902952;
    public static final double REL_RISK_MOULD_ASTHMA1 	= 	1.83;
    public static final double REL_RISK_MOULD_ASTHMA2 	= 	1.53;
    public static final double REL_RISK_MOULD_ASTHMA3 	= 	1.53;
    public static final double WEIGHT_CMD 			= 	0.88;
    public static final double WEIGHT_ASTHMA1 		= 	0.97;
    public static final double WEIGHT_ASTHMA2 		= 	0.85;
    public static final double WEIGHT_ASTHMA3 		= 	0.669;
    public static final double PREV_ASTHMA1  		= 	0.093;
    public static final double PREV_ASTHMA2	 		=	0.016;
    public static final double PREV_ASTHMA3	 		= 	0.001;
    public static final double PREV_CMD	 			= 	0.15;
    public static final double INC_WINCV			= 	1;
    public static final double INC_PM_CP			= 	10;
    public static final double INC_PM_LC 			= 	10;
    public static final double INC_ETS_CA 			= 	1;
    public static final double INC_ETS_MI 			= 	1;
    public static final double INC_RADON_LC	 		= 	100;
    public static final double INC_WINCMD	 		= 	1;
    public static final double INC_MOULD_ASTHMA1 	= 	100;
    public static final double INC_MOULD_ASTHMA2 	=   100;
    public static final double INC_MOULD_ASTHMA3 	= 	100;
    
    //Costs
    public static final double COST (final Disease.Type disease) {
    	double[] cost = new double[]{1.26, 861};
    	
    	if (disease == Disease.Type.lungcancer) {
        	cost = new double[]{1.46,861};
        }
    	return cost[0]*cost[1];
    }
    
    //Time functions
    //public final double[][] timeFunction;// = new double[4][3];
    public static final double[] TIME_FUNCTION (final Disease.Type disease) {
        double[] tFunc = new double[]{1, 1, 1};
    	
    	if (disease == Disease.Type.cerebrovascular || disease == Disease.Type.myocardialinfarction) {
    		tFunc = new double[]{5, 2, 0.4};
        } else if (disease == Disease.Type.cardiopulmonary) {
        	tFunc = new double[]{5, 2, 0.3};
        } else if (disease == Disease.Type.lungcancer) {
        	tFunc = new double[]{15, 2, 0.2};
        } 
    	
        return tFunc;
    }
}
