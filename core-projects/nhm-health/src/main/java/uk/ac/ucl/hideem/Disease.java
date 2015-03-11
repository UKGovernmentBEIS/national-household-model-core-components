package uk.ac.ucl.hideem;

import uk.ac.ucl.hideem.Person.Sex;

/**
 * Everything HIDEEM needs to know about diseases.
 */


public class Disease {
	public enum Type {
		cerebrovascular,    //CA
	    cardiopulmonary,	//CP
	    lungcancer,			//LC
	    myocardialinfarction,	//MI (Heart Attack)
	    wincerebrovascular,	//WinCA
	    //wincopd,			//WinCOPD
	    wincardiovascular,	//WinCV
	    winmyocardialinfarction	//WinMI
	    //CommonMentalDisorder,  //Morbidity only
	    //Athsma,				//Morbidity only
	    //OverheatingDeath
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
	
	public static Disease readDisease(String age, String sex, String mortality, double allMortality, String pop) {

	     return new Disease(
					Integer.parseInt(age),
					Person.Sex.valueOf(sex),			
					Double.parseDouble(mortality),
					Double.parseDouble(mortality)/Double.parseDouble(pop),
					allMortality/Double.parseDouble(pop));

	}
}

