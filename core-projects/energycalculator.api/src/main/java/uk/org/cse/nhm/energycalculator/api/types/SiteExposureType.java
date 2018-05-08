package uk.org.cse.nhm.energycalculator.api.types;

public enum SiteExposureType {
    /*
	 * Site exposure factor from BREDEM 2012 Table 21.
	 * 
	 * The factors relating to this are in EnergyCalculatorConstants.SITE_EXPOSURE_FACTOR, and can be configured by the user.
     */
    Exposed,
    AboveAverage,
    Average,
    BelowAverage,
    Sheltered;
}
