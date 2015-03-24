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
	    		RiskConstant.ETS_MI)
	    //wincopd,			//WinCOPD
	    //CommonMentalDisorder,  //Morbidity only
	    //Athsma,				//Morbidity only
	    //OverheatingDeath
	    
	    ;
		
		private final RiskConstant[] risks;
		
		private Type(final RiskConstant... risks) {
			this.risks = risks;
		}
		
		public double relativeRisk(final HealthOutcome result) {
			double acc = 1;
			for (final RiskConstant c : risks) {
				acc *= c.riskDueTo(result);
			}
			return acc;
		}
	}
	
	public final int age;
    public final Sex sex;
	public final double mortality;
	public final double hazard;
	public final double allHazard;
	//public final double morbidity;
	
	
	public Disease(final int age, final Sex sex, final double mortality, final double hazard, final double otherHazard) {
		this.age = age;
		this.sex = sex;
		this.mortality = mortality;
		this.hazard = hazard;
		this.allHazard = otherHazard;
		//this.morbidity = morbidity;
	}
	
	public static Disease readDisease(final String age, final String sex, final String mortality, final double allMortality, final String pop) {

	     return new Disease(
					Integer.parseInt(age),
					Person.Sex.valueOf(sex),			
					Double.parseDouble(mortality),
					Double.parseDouble(mortality)/Double.parseDouble(pop),
					allMortality/Double.parseDouble(pop));

	}
}

