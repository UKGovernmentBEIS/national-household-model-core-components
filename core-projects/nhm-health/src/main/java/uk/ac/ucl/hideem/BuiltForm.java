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
		CatA,//pre 1919
		CatB,//1919-44
		CatC,//1945-64
		CatD,//1965-80
		CatE,//1981-90
		CatF;//post 1990
	}
	
	public enum Tenure { //tenure4x 
		RSL,  
		LocalAuthority, 
		OwnerOccupied, 
		PrivateRented;
	}
	
	public enum OwnerAge{ //agehrp6x
		CatA,//16 - 24
		CatB,//25 - 34
		CatC,//35 - 44
		CatD,//45 - 54
		CatE,//55 - 64
		CatF;//65 or over
	}

}