package uk.org.cse.stockimport.imputation.ceilings;

import java.util.TreeMap;

import com.google.common.collect.ImmutableMap;

/**
 * Default RdSAP values for ceilings and roofs.
 *
 * @author richardt
 * @since 3.0
 */
public class RdSAPCeilingUValues implements ICeilingUValueTables {

    private final TreeMap<Integer, Double> insulatedPitchedUValues
            = new TreeMap<Integer, Double>(
                    ImmutableMap.<Integer, Double>builder()
                            .put(0, 2.3)
                            .put(25, 1.0)
                            .put(50, 0.7)
                            .put(75, 0.5)
                            .put(100, 0.4)
                            .put(125, 0.3)
                            .put(150, 0.3)
                            .put(200, 0.2)
                            .put(250, 0.2)
                            .put(300, 0.1)
                            .build());

    private final TreeMap<Integer, Double> insulatedThatchedUValues
            = new TreeMap<Integer, Double>(
                    ImmutableMap.<Integer, Double>builder()
                            .put(0, 0.4)
                            .put(25, 0.3)
                            .put(50, 0.3)
                            .put(75, 0.2)
                            .put(100, 0.2)
                            .put(125, 0.2)
                            .put(150, 0.2)
                            .put(200, 0.1)
                            .put(250, 0.1)
                            .put(300, 0.1)
                            .build());

    /**
     * Unknown insulation thickness u value by roof type, pitched and thatched,
     * no room in roof
     */
    private final double[][] unknownValuesByRoofTypeAndAgeBand = new double[][]{
        {2.30, 2.30, 2.30, 2.30, 1.50, 0.68, 0.40, 0.29, 0.26, 0.16, 0.16}, // pitched
        {0.35, 0.35, 0.35, 0.35, 0.35, 0.35, 0.35, 0.35, 0.35, 0.30, 0.25}, // thatched
        {2.30, 2.30, 2.30, 2.30, 1.50, 0.68, 0.40, 0.35, 0.35, 0.25, 0.25} // flat
    };

    /**
     * Unknown insulation thickness u value by roof type, pitched and thatched.
     * flat is missing, because CHM doesn't have it. room in roof present.
     */
    private final double[][] unknownValuesByRoofTypeAndAgeBandWithRoomInRoof = new double[][]{
        {2.30, 2.30, 2.30, 2.30, 1.50, 0.80, 0.50, 0.35, 0.35, 0.30, 0.25},
        {0.25, 0.25, 0.25, 0.25, 0.25, 0.25, 0.25, 0.25, 0.25, 0.25, 0.25}
    };

    @Override
    public TreeMap<Integer, Double> getInsulatedPitchedUValues() {
        return insulatedPitchedUValues;
    }

    @Override
    public TreeMap<Integer, Double> getInsulatedThatchedUValues() {
        return insulatedThatchedUValues;
    }

    @Override
    public double[][] getUnknownValuesByRoofTypeAndAgeBand() {
        return unknownValuesByRoofTypeAndAgeBand;
    }

    @Override
    public double[][] getUnknownValuesByRoofTypeAndAgeBandWithRoomInRoof() {
        return unknownValuesByRoofTypeAndAgeBandWithRoomInRoof;
    }
}
