package uk.ac.ucl.hideem;

import uk.ac.ucl.hideem.Exposure.Type;
import uk.ac.ucl.hideem.Person.Sex;
import uk.ac.ucl.hideem.Exposure.OccupancyType;

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
		SIT_CV(Constants.REL_RISK_SIT_CV, Constants.INC_WINCV, Exposure.Type.SIT),
		SIT_COPD(Constants.REL_RISK_SIT_COPD, Constants.INC_WINCOPD, Exposure.Type.SIT),
		SIT_CMD(Constants.REL_RISK_SIT_CMD, Constants.INC_WINCMD, Exposure.Type.SIT),
		MOULD_ASTHMA1(Constants.REL_RISK_MOULD_ASTHMA1, Constants.INC_MOULD_ASTHMA1, Exposure.Type.Mould),
		MOULD_ASTHMA2(Constants.REL_RISK_MOULD_ASTHMA2, Constants.INC_MOULD_ASTHMA2, Exposure.Type.Mould),
		MOULD_ASTHMA3(Constants.REL_RISK_MOULD_ASTHMA3, Constants.INC_MOULD_ASTHMA3, Exposure.Type.Mould);

		private final double logRatio;
		private final double ratio;
		private final Type exposureType;
		
		private RiskConstant(final double rel, final double inc, final Exposure.Type exposureType) {
			this.ratio = rel / inc;
			this.logRatio = Math.log(rel) / inc;
			this.exposureType = exposureType;
		}
		
		public double riskDueTo(final HealthOutcome result, final OccupancyType occupancy) {
			return Math.exp(logRatio * result.deltaExposure(exposureType, occupancy));
		}
		
		public double riskDueToCMD(final HealthOutcome result, final OccupancyType occupancy) {
			return Math.pow(ratio, result.deltaExposure(exposureType, occupancy));
		}
		
	}
	
    //Health coefficients
	public static final double REL_RISK_SIT_CV 	= 	0.913043;
	public static final double REL_RISK_ETS_CA 		= 	1.25;
    public static final double REL_RISK_ETS_MI 		= 	1.3;
    public static final double REL_RISK_PM_CP 		= 	1.082;
    public static final double REL_RISK_PM_LC		= 	1.059;
    public static final double REL_RISK_RADON_LC 	= 	1.16;
    public static final double REL_RISK_SIT_COPD 	= 	0.9;    //Done
    public static final double REL_RISK_SIT_CMD 	= 	0.902952;
    public static final double REL_RISK_MOULD_ASTHMA1 	= 	1.83;
    public static final double REL_RISK_MOULD_ASTHMA2 	= 	1.53;
    public static final double REL_RISK_MOULD_ASTHMA3 	= 	1.53;
    public static final double WEIGHT_COPD 			= 	0.88;//Done
    public static final double WEIGHT_CMD 			= 	0.751;
    public static final double WEIGHT_ASTHMA1 		= 	0.97;
    public static final double WEIGHT_ASTHMA2 		= 	0.85;
    public static final double WEIGHT_ASTHMA3 		= 	0.669;
    public static final double PREV_ASTHMA1  		= 	0.093;
    public static final double PREV_ASTHMA2	 		=	0.016;
    public static final double PREV_ASTHMA3	 		= 	0.001;
    public static final double PREV_COPD	 		= 	0.059;
    public static final double PREV_CMD	 			= 	0.15;
    public static final double INC_WINCV			= 	1;
    public static final double INC_PM_CP			= 	10;
    public static final double INC_PM_LC 			= 	10;
    public static final double INC_ETS_CA 			= 	1;
    public static final double INC_ETS_MI 			= 	1;
    public static final double INC_RADON_LC	 		= 	100;
    public static final double INC_WINCMD	 		= 	1;
    public static final double INC_WINCOPD	 		= 	1;
    public static final double INC_MOULD_ASTHMA1 	= 	100;
    public static final double INC_MOULD_ASTHMA2 	=   100;
    public static final double INC_MOULD_ASTHMA3 	= 	100;
    
    //Costs
    public static final double COST_PER_CASE(final Disease.Type disease) {
    	double[] cost = new double[Disease.Type.values().length];
    	cost[Disease.Type.cerebrovascular.ordinal()] = 860.83;
    	cost[Disease.Type.cardiopulmonary.ordinal()] = 1194.10;
    	cost[Disease.Type.myocardialinfarction.ordinal()] = 765.55; //CHD
    	cost[Disease.Type.lungcancer.ordinal()] = 4951.47;
    	cost[Disease.Type.wincerebrovascular.ordinal()] = cost[Disease.Type.cerebrovascular.ordinal()];
    	cost[Disease.Type.wincardiovascular.ordinal()] = 1047.09;
    	cost[Disease.Type.winmyocardialinfarction.ordinal()] = cost[Disease.Type.myocardialinfarction.ordinal()];
    	cost[Disease.Type.commonmentaldisorder.ordinal()] = 2248.77;
    	cost[Disease.Type.copd.ordinal()] = 820.39;
    	cost[Disease.Type.asthma1.ordinal()]= 
    		cost[Disease.Type.asthma2.ordinal()]= 
    		cost[Disease.Type.asthma3.ordinal()] = 312.08;
    	
    	return cost[disease.ordinal()];
    }
    
    //Incidence factor to get cases is incidence/deaths from Global Heath Statistics book
    //Global burden of disease and injury (1990s data) 
    public static final double INCIDENCE (final Disease.Type disease, final int age, final Sex sex) {
    	
    	int a,s = 0;
    	
    	double[][][] incidence = new double[Disease.Type.values().length][2][5];
    	//male					   //female
    	incidence[Disease.Type.cerebrovascular.ordinal()] = new double[][]{{0, 0, 37/8, 79/24, 465/289},{0, 0, 29/5, 69/15, 603/467}};  //page 655
    	incidence[Disease.Type.cardiopulmonary.ordinal()] = new double[][]{{0, 0, 60/18, 282/98, 857/713},{0, 0, 17/5, 78/29, 921/805}}; //Ischemic heart disease p646
    	incidence[Disease.Type.myocardialinfarction.ordinal()] = incidence[Disease.Type.cardiopulmonary.ordinal()]; //p646
    	incidence[Disease.Type.lungcancer.ordinal()] = new double[][]{{0, 0, 8/6, 57/50, 241/223},{0, 0, 4/3, 24/17, 97/81}}; //p556
    	incidence[Disease.Type.wincerebrovascular.ordinal()] = incidence[Disease.Type.cerebrovascular.ordinal()];	//p646
    	incidence[Disease.Type.wincardiovascular.ordinal()] = incidence[Disease.Type.cardiopulmonary.ordinal()];	//p646
    	incidence[Disease.Type.winmyocardialinfarction.ordinal()] = incidence[Disease.Type.cardiopulmonary.ordinal()];	//p646
    	
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
    	
    	return incidence[disease.ordinal()][s][a];
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
    
    //SIT constants from Ian's regression analysis
    //BEDROOM
    public static final double INTERCEPT_BR 	= 	19.20722323;
    public static final double E_COEF_BR 	= 	-0.00048454;
    //								dwage6x   pre 1919, 1919-44,1945-64,1965-80,1981-90,post 1990
    public static final double[] DW_AGE_BR = new double[]{0, 0.62893546, 0.98263756, 0.98077427, 0.87405793, 0.71870003};
    //								agehrp6x  16 - 24,  25 - 34, 35 - 44,  45 - 54,  55 - 64, 65 or over
    public static final double[] OC_AGE_BR = new double[]{0.23854955, -0.26124335, -0.77245027, -0.83600921, -0.71920557, 0};
    // children     (0, 1) where 1 is >=1 child
    public static final double[] CH_BR = new double[]{-0.65692081, 0};
    // fpflgf     Not in FP - full income definition, In FP - full income definition
    public static final double[] FP_BR = new double[]{0, -0.89033484};
    
    //LIVING ROOM
    public static final double INTERCEPT_LR 	= 	20.91762877;
    public static final double E_COEF_LR 	= 	-0.00137557;
    //								dwage6x   pre 1919, 1919-44,1945-64,1965-80,1981-90,post 1990
    public static final double[] DW_AGE_LR = new double[]{0, 0.47378301, 0.818369, 0.57985708, 0.64579968, 0.26898741};
    // 									tenure4x  RSL,  local authority, owner occupied, private rented
    public static final double[] TENURE_LR = new double[]{0.0692168, 0.9329455, -0.11287118, 0};
    //								agehrp6x  16 - 24,  25 - 34, 35 - 44,  45 - 54,  55 - 64, 65 or over
    public static final double[] OC_AGE_LR = new double[]{-1.29218261, -1.89237734, -1.56224335, -1.52381985, -1.05854816, 0};
    // children     (0, 1) where 1 is >=1 child
    public static final double[] CH_LR = new double[]{-0.94049087, 0};
    // fpflgf     Not in FP - full income definition, In FP - full income definition
    public static final double[] FP_LR = new double[]{0, -0.69756518};
    
    //Radon regional factors
    public static final double[] RADON_FACTS = new double[]{0.77, 0.91, 1, 0.92, 1.47, 1.08, 0.62,	0.43, 1.72,	1.11};
    
    //SIT E-value coefficients
    public static final double[] LR_SIT_CONSTS = new double[]{0,-3.10552E-11, 3.95406E-07, -0.003177483,19.97883737};
    public static final double[] BR_SIT_CONSTS = new double[]{0,-3.63348E-11, 6.50441E-07, -0.003972248,18.60539276};
    
    //Fuel rebate info
    public static final double REBATE_AMMOUNT 	= 	200;  //£
    public static final double REBATE_PRICE 	=  	0.031;  //£/kWh
    
    
    
}
