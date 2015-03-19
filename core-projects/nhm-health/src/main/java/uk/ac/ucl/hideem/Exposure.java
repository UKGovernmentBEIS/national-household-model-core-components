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
		Mould;
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
	
	public final ExposureBuiltForm builtForm;
	public final VentilationType ventType;
	public final double b0;
	public final double b1;
	public final double b2;
	public final double b3;
	public final double b4;
	
	public Exposure(final ExposureBuiltForm builtForm, final VentilationType ventType, final double b4, final double b3, final double b2, final double b1, final double b0) {
		this.builtForm = builtForm;
		this.ventType = ventType;
		this.b0 = b0;
		this.b1 = b1;
		this.b2 = b2;
		this.b3 = b3;
		this.b4 = b4;
	}
	
	public static Exposure readExposure(String[] row) {
		return new Exposure(
				Enum.valueOf(Exposure.ExposureBuiltForm.class, row[1]),
				Enum.valueOf(Exposure.VentilationType.class, row[2]),
				Double.parseDouble(row[3]),
				Double.parseDouble(row[4]),
				Double.parseDouble(row[5]),
				Double.parseDouble(row[6]),
				Double.parseDouble(row[7]));
	}

}
