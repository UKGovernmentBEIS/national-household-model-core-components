package uk.org.cse.nhm.energycalculator.api.types;

/**
 * RegionType.
 *
 * @author richardt
 * @version $Id: RegionType.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public enum RegionType {
	/*
	BEISDOC
	NAME: Latitude
	DESCRIPTION: The latitude in degrees of a UK region
	TYPE: value
	UNIT: degrees
	SAP: Table U4
	BREDEM: Table A1 (latitude)
	ID: latitude
	CODSIEB
	*/
    WesternScotland("W. Scotland", 55.8, Country.Scotland),
    EasternScotland("E. Scotland", 56.4, Country.Scotland),
    NorthEast("North East",55.5, Country.England),
    YorkshireAndHumber("Yorkshire and The Humber",54.5, Country.England),
    NorthWest("NorthWest",54.1, Country.England),
    EastMidlands("East Midlands",53.4, Country.England),
    WestMidlands("",52.7, Country.England),
    SouthWest("",51.0, Country.England),
    EastOfEngland("",52.3, Country.England),
    SouthEast("",51.3, Country.England),
    London("",51.5, Country.England),
    NorthernScotland("",57.2, Country.Scotland),
	Wales("Wales", 52.5, Country.Wales),
    NorthernIreland("Northern Ireland", 54.6, Country.NorthernIreland);

	private static double UK_AVERAGE_LATITUDE_DEGS = 35.5;
	public static double UK_AVERAGE_LATITUDE_RADIANS = UK_AVERAGE_LATITUDE_DEGS * Math.PI / 180;

    private final String friendlyName;
    private final double latitude, latrads;
	private final Country country;

    private RegionType(final String friendlyName, final double latitude, final Country country) {
        this.friendlyName = friendlyName;
        this.latitude = latitude;
		this.country = country;
        this.latrads = latitude * Math.PI / 180;
    }

    @Override
	public String toString() {
        return super.toString();
    }

    public double getLatitudeDegrees() {
		return latitude;
	}

    public double getLatitudeRadians() {
    	return latrads;
    }

    public Country getCountry() {
		return country;
	}

	public enum Country {
    	Scotland,
    	England,
    	Wales,
    	NorthernIreland
    }
}
