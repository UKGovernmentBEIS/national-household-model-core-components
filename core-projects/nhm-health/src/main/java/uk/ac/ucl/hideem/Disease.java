package uk.ac.ucl.hideem;

import uk.ac.ucl.hideem.Constants.RiskConstant;
import uk.ac.ucl.hideem.Person.Sex;
import uk.ac.ucl.hideem.IExposure.OccupancyType;
import uk.ac.ucl.hideem.IExposure.OverheatingAgeBands;

import uk.ac.ucl.hideem.BuiltForm.Region;

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
	    copd(
	    		RiskConstant.SIT_COPD),			//WinCOPD
	    commonmentaldisorder(
	    		RiskConstant.SIT_CMD),  //Morbidity only
	    asthma1(RiskConstant.MOULD_ASTHMA1),			//Morbidity only
	    asthma2(RiskConstant.MOULD_ASTHMA2),
	    asthma3(RiskConstant.MOULD_ASTHMA3),
	    overheating(
	    		RiskConstant.SIT2DayMax_OVERHEAT)
	    ;
		
		private final RiskConstant[] risks;
		
		private Type(final RiskConstant... risks) {
			this.risks = risks;
		}
		
		public double relativeRisk(final HealthOutcome result, final OccupancyType occupancy) {
			double acc = 1;
			for (final RiskConstant c : risks) {
				switch(c) {
				case SIT2DayMax_OVERHEAT:
					break;
				case SIT_CMD:
				case SIT_COPD:
					acc *= c.riskDueToCMD(result, occupancy);
					break;
				default:
					acc *= c.riskDueTo(result, occupancy);
					break;
				}	
			}
			return acc;
		}
		
        public double relativeRisk(final HealthOutcome result, final OccupancyType occupancy, final Region region, final OverheatingAgeBands ageBand) {
			//Only for overheating risk
			final double risk = RiskConstant.SIT2DayMax_OVERHEAT.riskDueToOverheating(result, occupancy, region, ageBand);
			
			return risk;
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

