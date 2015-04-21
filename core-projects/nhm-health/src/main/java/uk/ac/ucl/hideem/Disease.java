package uk.ac.ucl.hideem;

import uk.ac.ucl.hideem.Constants.RiskConstant;
import uk.ac.ucl.hideem.Person.Sex;

/**
 * Everything HIDEEM needs to know about diseases.
 */

public class Disease {
	public enum Type {
		cerebrovascular(
				RiskConstant.OUTPM_CP, 
				RiskConstant.INPM_CP, 
				RiskConstant.ETS_CA),   //CA
	    cardiopulmonary(
	    		RiskConstant.OUTPM_CP, 
	    		RiskConstant.INPM_CP),	//CP
	    lungcancer(
	    		RiskConstant.OUTPM_LC, 
	    		RiskConstant.INPM_LC, 
	    		RiskConstant.RADON_LC),			//LC
	    myocardialinfarction(
	    		RiskConstant.OUTPM_CP, 
	    		RiskConstant.INPM_CP, 
	    		RiskConstant.ETS_MI),	//MI (Heart Attack)
	    wincerebrovascular(
	    		RiskConstant.OUTPM_CP, 
	    		RiskConstant.INPM_CP, 
	    		RiskConstant.SIT_CV, 
	    		RiskConstant.ETS_CA),	//WinCA
	    wincardiovascular(
	    		RiskConstant.OUTPM_CP,
	    		RiskConstant.INPM_CP, 
	    		RiskConstant.SIT_CV),	//WinCV
	    winmyocardialinfarction(
	    		RiskConstant.OUTPM_CP, 
	    		RiskConstant.INPM_CP, 
	    		RiskConstant.SIT_CV, 
	    		RiskConstant.ETS_MI),
	    //wincopd,			//WinCOPD
	    commonmentaldisorder(
	    		RiskConstant.SIT_CMD),  //Morbidity only
	    asthma(
	    		RiskConstant.MOULD_ASTHMA1)//,
	    		//RiskConstant.MOULD_ASTHMA2,
	    		//RiskConstant.MOULD_ASTHMA3)				//Morbidity only
	    //OverheatingDeath
	    ;
		
		private final RiskConstant[] risks;
		
		private Type(final RiskConstant... risks) {
			this.risks = risks;
		}
		
		public double relativeRisk(final HealthOutcome result) {
			double acc = 1;
			for (final RiskConstant c : risks) {
				switch(c) {
				case SIT_CMD:
					acc *= c.riskDueToCMD(result);
					break;
//				case MOULD_ASTHMA1:
//					acc = ((1 - Constants.WEIGHT_ASTHMA1) * Constants.PREV_ASTHMA1)*(1 - c.riskDueTo(result));
//					break;
//				case MOULD_ASTHMA2:
//					acc += ((1 - Constants.WEIGHT_ASTHMA2) * Constants.PREV_ASTHMA2)*(1 - c.riskDueTo(result));
//					break;
//				case MOULD_ASTHMA3:
//					acc += ((1 - Constants.WEIGHT_ASTHMA3) * Constants.PREV_ASTHMA3)*(1 - c.riskDueTo(result));
//					break;
				default:
					acc *= c.riskDueTo(result);
					break;
				}	
			}
			return acc;
		}
	}
	
	public final int age;
    public final Sex sex;
	public final double mortality;
	public final double hazard;
	public final double allHazard;
	public final double morbidity;
	
	
	public Disease(final int age, final Sex sex, final double mortality, final double hazard, final double otherHazard, final double morbidity) {
		this.age = age;
		this.sex = sex;
		this.mortality = mortality;
		this.hazard = hazard;
		this.allHazard = otherHazard;
		this.morbidity = morbidity;
	}
	
	public static Disease readDisease(final String age, final String sex, final String mortality, final double allMortality, final String pop, final String morbidityRatio) {

	     return new Disease(
					Integer.parseInt(age),
					Person.Sex.valueOf(sex),			
					Double.parseDouble(mortality),
					Double.parseDouble(mortality)/Double.parseDouble(pop),
					allMortality/Double.parseDouble(pop),
					Double.parseDouble(morbidityRatio));

	}
}

