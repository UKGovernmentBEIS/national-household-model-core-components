package uk.org.cse.nhm.simulator.integration.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.EnumSet;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;

/**
 * A lightweight helper that reads a monthly TSV file. Doesn't support any fancy
 * CSV options. Probably should.
 *
 * @author hinton
 *
 */
public class RegionalMonthlyTable {

    private double[][] values = new double[RegionType.values().length][12];

    public RegionalMonthlyTable(final Reader reader) throws IOException {
        final BufferedReader br = new BufferedReader(reader);
        String line = null;
        int lineNumber = 0;
        final EnumSet<RegionType> seenRegions = EnumSet.noneOf(RegionType.class);
        while ((line = br.readLine()) != null) {
            lineNumber++;
            String[] fields = line.split("\t+");

            if (fields.length != 13) {
                throw new IOException("Bad file format on line " + lineNumber + " (" + line + ") - expected 13 columns, not " + fields.length + " (" + Arrays.toString(fields) + ")");
            }
            int fieldNumber = 0;
            try {
                final RegionType regionType = RegionType.valueOf(fields[fieldNumber++].trim());
                if (seenRegions.contains(regionType)) {
                    throw new IOException("Region " + regionType + " is specified twice (second time on line " + lineNumber + ")");
                }
                seenRegions.add(regionType);
                while (fieldNumber < fields.length) {
                    final double d = Double.parseDouble(fields[fieldNumber++].trim());
                    values[regionType.ordinal()][fieldNumber - 2] = d;
                }
            } catch (Exception exception) {
                throw new IOException("Bad file format on line " + lineNumber + ", field " + fieldNumber + " (" + fields[fieldNumber] + ")", exception);
            }
        }

        if (!seenRegions.containsAll(EnumSet.allOf(RegionType.class))) {
            throw new IOException("Missing data for regions " + EnumSet.complementOf(seenRegions));
        }
    }

    public double get(final RegionType region, final int month) {
        return values[region.ordinal()][month];
    }
}
