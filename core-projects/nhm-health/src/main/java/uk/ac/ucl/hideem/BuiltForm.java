package uk.ac.ucl.hideem;

public class BuiltForm {

	public enum Type {
		EndTerrace,
		MidTerrace,
	    SemiDetached,
	    Detached,
	    Bungalow,
	    ConvertedFlat,
	    PurposeBuiltFlatLowRise,
	    PurposeBuiltFlatHighRise;
	}

	public enum DwellingAge { //dwage6x
		Pre_1919,
		In_1919_44,
		In_1945_64,
		In_1965_80,
		In_1981_90,
		Post_1990;
	}
	
	public enum Tenure { //tenure4x 
		RSL,  
		LocalAuthority, 
		OwnerOccupied, 
		PrivateRented;
	}
	
	public enum OwnerAge{ //agehrp6x
		In_16_24,
		In_25_34,
		In_35_44,
		In_45_54,
		In_55_64,
		Over_64;
	}

}