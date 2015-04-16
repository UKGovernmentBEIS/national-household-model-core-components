package uk.ac.ucl.hideem;

import uk.ac.ucl.hideem.Exposure.Type;
import uk.ac.ucl.hideem.Person.Sex;

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
    public static final double COST_PER_CASE 	= 	861;
    
    //Incidence factor to get cases is incidence/deaths from Global Heath Statistics book
    //Global burden of disease and injury page 655 (1990s data) 
    public static final double INCIDENCE (final Disease.Type disease, final int age, final Sex sex) {
    	
    	int a,s = 0;
    	
    	//male					   //female
    	double[][] incidence = new double[][]{{0, 0, 4.625, 3.292, 1.609},{0, 0, 5.8, 4.6, 1.352}};  //Cerebovascular
    	
    	if (age < 5) {
    		a = 0;
    	} else if (age >= 5 && age < 15){
    		a = 1;
    	} else if (age >= 15 && age < 45){
    		a = 2;
    	} else if (age >= 45 && age < 60){
    		a = 3;
    	} else {
    		a = 4;
    	}
    	
    	if (sex == Sex.MALE){
    		s = 0;
    	} else{
    		s = 1;
    	}
    	
    	return incidence[s][a];
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
