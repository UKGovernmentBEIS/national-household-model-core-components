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
	
    // Region coding. This matches the NHM region coding, because it has to.
    // However, there is some other region coding in constants.java
    public enum Region {
        WesternScotland,
        EasternScotland,
        NorthEast,
        YorkshireAndHumber,
        NorthWest,
        EastMidlands,
        WestMidlands,
        SouthWest,
        EastOfEngland,
        SouthEast,
        London,
        NorthernScotland,
        Wales,
        NorthernIreland;
    }
}
