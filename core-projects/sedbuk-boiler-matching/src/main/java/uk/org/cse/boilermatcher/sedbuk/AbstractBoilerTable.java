package uk.org.cse.boilermatcher.sedbuk;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.boilermatcher.types.BoilerType;
import uk.org.cse.boilermatcher.types.FlueType;
import uk.org.cse.boilermatcher.types.FuelType;

/**
 * Class which handles the special case fields for the boiler table in sedbuk.
 *
 * Contains a few protected methods which {@link AutoTable} will fill in for us
 *
 * @author hinton
 * @since 1.0.2
 */
public abstract class AbstractBoilerTable extends BaseTable implements IBoilerTable {

    private static final Map<Integer, FuelType> fuelTypeBySapCode
            = ImmutableMap.<Integer, FuelType>builder()
                    .put(1, FuelType.MAINS_GAS)
                    .put(2, FuelType.BULK_LPG)
                    .put(4, FuelType.OIL)
                    .build();

    private static final Map<Integer, BoilerType> boilerTypesBySedbukCode
            = ImmutableMap.<Integer, BoilerType>builder()
                    .put(1, BoilerType.REGULAR)
                    .put(2, BoilerType.INSTANT_COMBI)
                    .put(3, BoilerType.CPSU)
                    .put(0, BoilerType.UNKNOWN)
                    .build();

    private static final int INAPPLICABLE_STORE_TYPE_CODE = 0;

    @Column(10)
    protected abstract int getFuelTypeCode(int row);

    @Column(13)
    protected abstract int getBoilerTypeCode(int row);

    @Column(17)
    protected abstract int getCondensingCode(int row);

    @Column(18)
    protected abstract int getFlueTypeCode(int row);

    @Column(19)
    protected abstract int getFlueFanAssitanceCode(int row);

    @Column(36)
    protected abstract int getStoreTypeCode(int row);

    @Override
    public Integer matchByBrandAndModel(final String boilerBrand, final String boilerModel) {
        int firstInexactModelMatch = -1;
        int firstInexactBrandMatch = -1;
        int firstInexactMatch = -1;
        for (int i = 0; i < getNumberOfRows(); i++) {
            final String thisBrand = getBrandName(i);
            final String thisModel = getModelName(i);
            final boolean exactBrandMatch = exactMatch(boilerBrand, thisBrand);
            final boolean exactModelMatch = exactMatch(boilerModel, thisModel);

            if (exactBrandMatch && exactModelMatch) {
                // two exact matches beats whatever else
                return i;
            }

            // if we have already got an inexact model match, that will win, so check for that
            if (firstInexactModelMatch == -1) {
                final boolean inexactModelMatch = inexactMatch(boilerModel, thisModel);

                // we know we don't have an inexact model match, so check for it
                if (exactBrandMatch && inexactModelMatch) {
                    firstInexactModelMatch = i;
                } else {
                    // OK this is not an inexact model match, so if we already have an
                    // inexact brand match just give up
                    if (firstInexactBrandMatch == -1) {
                        // OK, no inexact brand match either, check for that
                        final boolean inexactBrandMatch = inexactMatch(boilerBrand, thisBrand);
                        if (exactModelMatch && inexactBrandMatch) {
                            firstInexactBrandMatch = i;
                        } else {
                            // worst option is an inexact match on both - if we already have one
                            // then no need to check for another.
                            if (firstInexactMatch == -1) {
                                if (inexactBrandMatch && inexactModelMatch) {
                                    firstInexactMatch = i;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (firstInexactModelMatch != -1) {
            return firstInexactModelMatch;
        } else if (firstInexactBrandMatch != -1) {
            return firstInexactBrandMatch;
        } else if (firstInexactMatch != -1) {
            return firstInexactMatch;
        } else {
            return null;
        }
    }

    /**
     * @param a
     * @param b
     * @return true if either string has the other as a substring
     */
    private boolean exactMatch(final String a, final String b) {
        return a.trim().equals(b.trim());
    }

    /**
     * @param a
     * @param b
     * @return true if all the letters in one string occur in the right order in
     * the other.
     * @since 1.0.0
     */
    public static boolean inexactMatch(final String a, final String b) {
        return a.contains(b) || b.contains(a);
    }

    @Override
    public FuelType getFuelType(int row) {
        return fuelTypeBySapCode.get(getFuelTypeCode(row));
    }

    /**
     * @param row
     * @return
     * @see uk.org.cse.boilermatcher.sedbuk.IBoilerTable#getBoilerType(int)
     */
    @Override
    public BoilerType getBoilerType(int row) {
        final BoilerType type = boilerTypesBySedbukCode.get(getBoilerTypeCode(row));

        // instant combi is not strictly accurate - we need to check the store type to see if it
        // has a store; if it does, this is a storage combi.
        if (type == BoilerType.INSTANT_COMBI) {
            final int storeType = getStoreTypeCode(row);

            if (storeType != INAPPLICABLE_STORE_TYPE_CODE) {
                return BoilerType.STORAGE_COMBI;
            }
        }

        return type;
    }

    @Override
    public FlueType getFlueType(int row) {
        final int code = getFlueTypeCode(row);
        final int assist = getFlueFanAssitanceCode(row);
        if (assist == 2) {
            return FlueType.FAN_ASSISTED_BALANCED_FLUE;
        } else {
            switch (code) {
                case 1:
                    // open
                    return FlueType.OPEN_FLUE;
                case 2:
                    // room-sealed
                    return FlueType.BALANCED_FLUE;
                default:
                    return FlueType.BALANCED_FLUE;
            }
        }
    }

    @Override
    public Boolean isCondensing(int row) {
        switch (getCondensingCode(row)) {
            case 1:
                return false;
            case 2:
                return true;
            default:
                return null;
        }
    }
}
