package uk.org.cse.stockimport.imputation.ceilings;

import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;

/**
 * This may be more CHM than RDSAP; provides ceiling u values.
 *
 * @author hinton
 * @since 1.0
 */
public class CeilingPropertyImputer implements ICeilingPropertyImputer {

    private static final Logger log = LoggerFactory.getLogger(CeilingPropertyImputer.class);

    private final ICeilingUValueTables ceilingUValues;

    public CeilingPropertyImputer(final ICeilingUValueTables ceilingUValues) {
        this.ceilingUValues = ceilingUValues;
    }

    private double interpolate(final double thickness, final TreeMap<Integer, Double> values) {
        final Integer intThickness = (int) Math.round(thickness);

        if (values.containsKey(intThickness)) {
            return values.get(intThickness);
        }

        final Entry<Integer, Double> above = values.ceilingEntry(intThickness);
        if (above == null) {
            return values.lastEntry().getValue();
        }
        final Entry<Integer, Double> below = values.floorEntry(intThickness);
        if (below == null) {
            return values.firstEntry().getValue();
        }

        final double gap = above.getKey() - below.getKey();
        final double offset = above.getKey() - thickness;
        final double proportion = offset / gap;

        return below.getValue() + (above.getValue() - below.getValue()) * proportion;
    }

    /* (non-Javadoc)
	 * @see uk.org.cse.stockimport.imputation.ICeilingPropertyImputer#getRoofUValue(uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType, double, boolean)
     */
    @Override
    public double getRoofUValue(final RoofConstructionType constructionType, final double insulationThickness, final boolean roomInRoof) {
        if (roomInRoof) {
//			log.warn("Not sure that I know how to get roof u values with given loft insulation and room-in-roof, but I will give it a try");
        }

        if (constructionType == null) {
            log.debug("Roof construction type is null, returning 0");
            return 0;
        }

        switch (constructionType) {
            case PitchedSlateOrTiles:
                return interpolate(insulationThickness, ceilingUValues.getInsulatedPitchedUValues());
            case Thatched:
                return interpolate(insulationThickness, ceilingUValues.getInsulatedThatchedUValues());
            case Flat:
            default:
                log.error("Cannot get u value for construction type {}", constructionType);
                return 0;
        }
    }

    /* (non-Javadoc)
	 * @see uk.org.cse.stockimport.imputation.ICeilingPropertyImputer#getRoofUValue(uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue, uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType, boolean)
     */
    @Override
    public double getRoofUValue(final Band ageBand, final RoofConstructionType constructionType, final boolean roomInRoof) {
        if (roomInRoof) {
            if (constructionType == RoofConstructionType.Flat) {
                log.error("Roof u values not known when room in roof is present, insulation is unknown, and roof type is flat");
                return 0;
            }
            return ceilingUValues.getUnknownValuesByRoofTypeAndAgeBandWithRoomInRoof()[constructionType.ordinal()][ageBand.ordinal()];

        } else {
            return ceilingUValues.getUnknownValuesByRoofTypeAndAgeBand()[constructionType.ordinal()][ageBand.ordinal()];
        }
    }
}
