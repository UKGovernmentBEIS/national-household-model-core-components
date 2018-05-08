package uk.org.cse.nhm.energycalculator.api.types;

import java.util.Map;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;

/**
 * SAPAgeBandValue.
 *
 * See Sap Table S1 : Age bands
 *
 * @author richardt
 * @version $Id: SAPAgeBandValue.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public class SAPAgeBandValue {

    public enum Band {
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H,
        I,
        J,
        K,
        L;

        public boolean after(Band b) {
            return this.compareTo(b) > 0;
        }

        public boolean before(Band b) {
            return this.compareTo(b) < 0;
        }
    }

    private static SAPAgeBandValue[] englandAndWales = new SAPAgeBandValue[]{
        band(Band.A, null, 1899),
        band(Band.B, 1900, 1929),
        band(Band.C, 1930, 1949),
        band(Band.D, 1950, 1966),
        band(Band.E, 1967, 1975),
        band(Band.F, 1976, 1982),
        band(Band.G, 1983, 1990),
        band(Band.H, 1991, 1995),
        band(Band.I, 1996, 2002),
        band(Band.J, 2003, 2006),
        band(Band.K, 2007, 2011),
        band(Band.L, 2012, null)
    };

    private static Map<Country, SAPAgeBandValue[]> byCountry = ImmutableMap.of(
            Country.England, englandAndWales,
            Country.Wales, englandAndWales,
            Country.Scotland, new SAPAgeBandValue[]{
                band(Band.A, null, 1918),
                band(Band.B, 1919, 1929),
                band(Band.C, 1930, 1949),
                band(Band.D, 1950, 1964),
                band(Band.E, 1965, 1975),
                band(Band.F, 1976, 1983),
                band(Band.G, 1984, 1991),
                band(Band.H, 1992, 1998),
                band(Band.I, 1999, 2002),
                band(Band.J, 2003, 2007),
                band(Band.K, 2008, 2011),
                band(Band.L, 2012, null)},
            Country.NorthernIreland, new SAPAgeBandValue[]{
                band(Band.A, null, 1918),
                band(Band.B, 1919, 1929),
                band(Band.C, 1930, 1949),
                band(Band.D, 1950, 1973),
                band(Band.E, 1974, 1977),
                band(Band.F, 1978, 1985),
                band(Band.G, 1986, 1991),
                band(Band.H, 1992, 1999),
                band(Band.I, 2000, 2006),
                /* Northern Ireland has no band J*/
                band(Band.K, 2007, 2013),
                band(Band.L, 2014, null)}
    );

    private final DateTime startDate;
    private final DateTime endDate;
    private final Band name;

    private SAPAgeBandValue(final Band name, final Integer startDate, final Integer endDate) {
        this.name = name;
        this.startDate = startYear(startDate);
        this.endDate = endYear(endDate);
    }

    private static SAPAgeBandValue band(final Band name, final Integer startDate, final Integer endDate) {
        return new SAPAgeBandValue(name, startDate, endDate);
    }

    public Band getName() {
        return name;
    }

    private static DateTime startYear(final Integer year) {
        if (year == null) {
            return null;
        } else {
            return new DateTime(year, 01, 01, 0, 0, 0);
        }
    }

    private static DateTime endYear(final Integer year) {
        if (year == null) {
            return null;
        } else {
            return startYear(year + 1);
        }
    }

    private boolean isYearInBand(final int year) {
        return (startDate == null || startDate.getYear() <= year)
                && (endDate == null || endDate.getYear() > year);
    }

    public static SAPAgeBandValue fromYear(final int year, final Country country, final Band maxBand) {
        if (!byCountry.containsKey(country)) {
            throw new IllegalArgumentException("Unknown country: " + country);
        }

        for (final SAPAgeBandValue band : byCountry.get(country)) {
            if (band.name == maxBand) {
                return band;
            }
            if (band.isYearInBand(year)) {
                return band;
            }
        }
        throw new IllegalArgumentException("Year did not fit into any SAP age band. SAPAgeBandValue must be defined incorrectly: " + year);
    }

    public static SAPAgeBandValue fromYear(final int year, final RegionType region) {
        return fromYear(year, region.getCountry());
    }

    public static SAPAgeBandValue fromYear(final int year, final Country country) {
        return fromYear(year, country, Band.L);
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
