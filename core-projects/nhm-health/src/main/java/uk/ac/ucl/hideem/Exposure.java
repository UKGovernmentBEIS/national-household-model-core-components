package uk.ac.ucl.hideem;

/**
 * Everything HIDEEM needs to know about exposures.
 */

public class Exposure {
	public enum Type {
		Radon,
		ETS,
		INPM2_5,
		OUTPM2_5,
		VPX,
		SIT,
		Mould,
		SIT2DayMax;
	}
	
	public enum ExposureBuiltForm {
		Flat1,
	    Flat2,
	    Flat3,
	    House1,
	    House2,
	    House3,
	    House4,
	    House5,
	    House6,
	    House7;
	}
	
	public enum VentilationType {
		NOTE,
		T,
		E,
		TE;
	}
	
	public enum OccupancyType{
		//time living room, bedroom, kitchen
		H45_45_10,   //pensioner
		H55_45_0,    //small child
		W29_33_0,	 //school child
		W21_33_8;    //worker
	}
	
	public enum OverheatingAgeBands{
		Age0_64,
		Age65_74,
		Age75_85,
		Age85;
	}
	
	
	public final ExposureBuiltForm builtForm;
	public final VentilationType ventType;
	//array of values for different exposure occupancies
	public double[][] coefs = new double[4][5];
	
	public Exposure(final ExposureBuiltForm builtForm, final VentilationType ventType, final double b4, final double b3, final double b2, 
			final double b1, final double b0, final double c4, final double c3, final double c2, final double c1, final double c0, final double d4, 
			final double d3, final double d2, final double d1, final double d0, final double e4, final double e3, final double e2, final double e1, 
			final double e0) {
		this.builtForm = builtForm;
		this.ventType = ventType;
		this.coefs[OccupancyType.H45_45_10.ordinal()] = new double[]{b0, b1, b2, b3, b4};
		this.coefs[OccupancyType.H55_45_0.ordinal()] = new double[]{c0, c1, c2, c3, c4};
		this.coefs[OccupancyType.W21_33_8.ordinal()] = new double[]{d0, d1, d2, d3, d4};
		this.coefs[OccupancyType.W29_33_0.ordinal()] = new double[]{e0, e1, e2, e3, e4};
	}
	
	public double dueToPermeability(final OccupancyType occupancy, final double p) {
		final double[] coefs = this.coefs[occupancy.ordinal()];
		double acc = 0;
		for (int i = 0; i<coefs.length; i++) {
			acc += coefs[i] * Math.pow(p, i);
		}
		return acc;
	}
	
	public static Exposure readExposure(final String[] row) {
		return new Exposure(
				Enum.valueOf(Exposure.ExposureBuiltForm.class, row[1]),
				Enum.valueOf(Exposure.VentilationType.class, row[2]),
				Double.parseDouble(row[3]),
				Double.parseDouble(row[4]),
				Double.parseDouble(row[5]),
				Double.parseDouble(row[6]),
				Double.parseDouble(row[7]),
				Double.parseDouble(row[8]),
				Double.parseDouble(row[9]),
				Double.parseDouble(row[10]),
				Double.parseDouble(row[11]),
				Double.parseDouble(row[12]),
				Double.parseDouble(row[13]),
				Double.parseDouble(row[14]),
				Double.parseDouble(row[15]),
				Double.parseDouble(row[16]),
				Double.parseDouble(row[17]),
				Double.parseDouble(row[18]),
				Double.parseDouble(row[19]),
				Double.parseDouble(row[20]),
				Double.parseDouble(row[21]),
				Double.parseDouble(row[22])
				);
	}
	
	public static OccupancyType getOccupancyType(final int age) {
		final OccupancyType occupancy;
		//move elsewhere
		if(age <= 5){
			occupancy = OccupancyType.H55_45_0; 
		} else if(age > 5 && age < 18){
			occupancy = OccupancyType.W29_33_0;
		} else if(age > 65){
			occupancy = OccupancyType.H45_45_10;
		} else{
			occupancy = OccupancyType.W21_33_8;
		}
		
		return occupancy;
	}
	
	public static OverheatingAgeBands getOverheatingAgeBand(final int age) {
		final OverheatingAgeBands ageBand;
		//move elsewhere
		if(age <= 65){
			ageBand = OverheatingAgeBands.Age0_64; 
		} else if(age >= 65 && age < 75){
			ageBand = OverheatingAgeBands.Age65_74;
		} else if(age >= 75 && age < 85){
			ageBand = OverheatingAgeBands.Age75_85;
		} else{
			ageBand = OverheatingAgeBands.Age85;
		}
		
		return ageBand;
	}

}
