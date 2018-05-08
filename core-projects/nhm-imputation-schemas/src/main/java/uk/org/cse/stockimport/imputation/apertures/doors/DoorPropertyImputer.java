package uk.org.cse.stockimport.imputation.apertures.doors;

import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import uk.org.cse.nhm.energycalculator.api.types.DoorType;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;

/**
 * @since 1.0
 */
public class DoorPropertyImputer implements IDoorPropertyImputer {

    private final Table<DoorType, SAPAgeBandValue.Band, Double> doorUValues = HashBasedTable.create();
    private final Map<DoorType, Double> doorAreasByType;

    /**
     * Constructs object using RdSAP defaults, {@link DoorPropertyImputer#defaultDoorArea}
     * {@link DoorPropertyImputer#sapBandAtoJUValue} and
     * {@link DoorPropertyImputer#sapBandKUValue}
     *
     * @param useRdSapDefaults
     * @since 3.0
     */
    public DoorPropertyImputer() {
        doorAreasByType = new TreeMap<>();
    }

    @Override
    public double getArea(final DoorType doorType) {
        return Optional.fromNullable(doorAreasByType.get(doorType)).or(0d);
    }

    @Override
    public double getUValue(final Band ageBand, final DoorType doorType) {
        final Double value = doorUValues.get(doorType, ageBand);
        if (value == null) {
            return 0d;
        } else {
            return value;
        }
    }

    /**
     * @param doorType
     * @param ageBandValue
     * @since 3.0
     */
    public void addDoorUValue(final DoorType doorType, final Band ageBandValue, final double uValue) {
        doorUValues.put(doorType, ageBandValue, uValue);
    }

    /**
     * @param doorType
     * @param areaInM2
     * @since 3.0
     */
    public void addDoorArea(final DoorType doorType, final double areaInM2) {
        doorAreasByType.put(doorType, areaInM2);
    }
}
