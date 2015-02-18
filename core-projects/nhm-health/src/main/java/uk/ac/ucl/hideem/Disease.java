package uk.ac.ucl.hideem;

import uk.ac.ucl.hideem.Person.Sex;

/**
 * Everything HIDEEM needs to know about diseases.
 */


public class Disease {
	public enum Type {
	    CardiovascularCold,
	    Cardiopulmonary,
	    HeartAttack,
	    Stroke,
	    LungCancer,
	    //CommonMentalDisorder,
	    //Athsma,
	    //ChronicObstructivePulmonaryDisorder,
	    //OverheatingDeath
	}
	
	public final int age;
    public final Sex sex;
	public final double nA;
	public final double nB;
	public final double nC;
	public final double pA;
	public final double pB;
	public final double pC;
	public final double morbidity;
	
	
	public Disease(final int age, final Sex sex, final double nA, final double nB, final double nC, final double pA, final double pB, final double pC, final double morbidity) {
		this.age = age;
		this.sex = sex;
		this.nA = nA;
		this.nB = nB;
		this.nC = nC;
		this.pA = pA;
		this.pB = pB;
		this.pC = pC;
		this.morbidity = morbidity;
	}
	
	public static Disease readDisease(String[] row) {
		return new Disease(
				Integer.parseInt(row[1]),
				Enum.valueOf(Person.Sex.class, row[2]),
				Double.parseDouble(row[4]),
				Double.parseDouble(row[5]),
				Double.parseDouble(row[6]),
				Double.parseDouble(row[7]),
				Double.parseDouble(row[8]),
				Double.parseDouble(row[9]),
				Double.parseDouble(row[10]));
	}
}

