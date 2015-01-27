package uk.ac.ucl.hideem;

import java.util.Map;

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
		Mould;
	}
	
	public enum ExposureBuild {
		Flat1a,
	    Flat1b,
	    Flat1c,
	    Flat2a,
	    Flat2b,
	    Flat2c,
	    Flat3a,
	    Flat3b,
	    Flat3c,
	    House1,
	    House2,
	    House3,
	    House4,
	    House5,
	    House6,
	    House7;
	}
	
	public enum Vtype {
		NOTE,
		T,
		E,
		TE;
	}
	
	public final ExposureBuild built;
	public final Vtype vtype;
	public final double b0;
	public final double b1;
	public final double b2;
	public final double b3;
	public final double b4;
	
	public Exposure(final ExposureBuild built, final Vtype vtype, final double b0, final double b1, final double b2, final double b3, final double b4) {
		this.built = built;
		this.vtype = vtype;
		this.b0 = b0;
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		this.b4 = b4;
	}
	
	public static Exposure readExposure(String[] row) {
		return new Exposure(
				Enum.valueOf(Exposure.ExposureBuild.class, row[1]),
				Enum.valueOf(Exposure.Vtype.class, row[2]),
				Double.parseDouble(row[5]),
				Double.parseDouble(row[6]),
				Double.parseDouble(row[7]),
				Double.parseDouble(row[8]),
				Double.parseDouble(row[9]));
	}

}
