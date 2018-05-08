package uk.org.cse.stockimport.ehcs2010.spss;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Predicate;

import uk.org.cse.nhm.ehcs10.derived.Physical_09Plus10Entry;
import uk.org.cse.nhm.ehcs10.derived.types.Enum10;
import uk.org.cse.nhm.ehcs10.derived.types.Enum69;
import uk.org.cse.nhm.ehcs10.physical.ElevateEntry;
import uk.org.cse.nhm.ehcs10.physical.FlatdetsEntry;
import uk.org.cse.nhm.ehcs10.physical.IntroomsEntry;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1303;
import uk.org.cse.nhm.ehcs10.physical.types.Enum1650;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;

/**
 * Spss2012HelperMethods.
 *
 * @author richardt
 * @version $Id: Spss2012HelperMethods.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public final class Spss2012HelperMethods {

    private static final double TEN = 10.00;
    private static final int NOT_APPLICABLE = 88;
    private static final int UNKNOWN = 99;

    /**
     * Determines whether case is a house or other (flat).
     *
     * @param physicalProps
     * @return
     * @since 0.0.1-SNAPSHOT
     */
    public static final boolean isHouse(final Physical_09Plus10Entry physicalProps) {
        switch (physicalProps.getDwellingType_DWTYPE8X()) {
            case DetachedHouse:
            case Medium_LargeTerracedHouse:
            case Semi_DetachedHouse:
            case SmallTerracedHouse:
            case Bungalow:
                return true;
            default:
                return false;
        }
    }

    /**
     * Determines whether has room in roof, using
     * {@link Physical_09Plus10Entry#getAtticPresentInDwelling()}, DoesNotApply,
     * No and NoAnswer are false otherwise true.
     *
     * @param physicalProps
     * {@link Physical_09Plus10Entry#getAtticPresentInDwelling()}
     * @return true if not DoesNotApply, No and NoAnswer
     * @since 0.0.1-SNAPSHOT
     */
    public static final boolean hasRoomInRoof(final Physical_09Plus10Entry physicalProps) {
        switch (physicalProps.getAtticPresentInDwelling()) {
            case DoesNotApply:
            case No:
            case NoAnswer:
                return false;
            default:
                return true;
        }
    }

    /**
     * Uses {@link Physical_09Plus10Entry#getBasementPresentInDwelling()},
     * returns true if values equals {@link Enum69#Yes} otherwise returns false.
     *
     * @param physicalProps {@link Physical_09Plus10Entry} to get value from
     * @return boolean if property has a basement
     * @since 1.0.2
     */
    public static final boolean hasBasement(final Physical_09Plus10Entry physicalProps) {
        switch (physicalProps.getBasementPresentInDwelling()) {
            case Yes:
                return true;
            case DoesNotApply:
            case No:
            case NoAnswer:
            default:
                return false;
        }
    }

    /**
     * Builds a room type predicate, returns all rooms that have a value of
     * room.
     *
     * @param room {@link Enum69} room type to filter
     * @return {@link Predicate}
     * @since 1.0.2
     */
    public static final Predicate<IntroomsEntry> buildRoomTypePredicate(final Enum1650 room) {
        return new Predicate<IntroomsEntry>() {
            public boolean apply(IntroomsEntry input) {
                return input != null && input.getRoom().equals(room);
            }
        };
    }

    /**
     * 1. If isHouse then returns
     * {@link Spss2012HelperMethods#getTenthsAttached(ElevationType, ElevateEntry, FlatdetsEntry, boolean)}.<br/><br/>
     *
     * @param elevation
     * @param elevateEntry
     * @param flatDetsEntry
     * @param isHouse
     * @return
     * @since 0.0.1-SNAPSHOT
     */
    public static final double getTenthsPartyWall(final ElevationType elevation,
            final ElevateEntry elevateEntry, final FlatdetsEntry flatDetsEntry, boolean isHouse) {

        if (isHouse) {
            return getTenthsAttached(elevation, elevateEntry, flatDetsEntry, isHouse);
        } else {
            Map<ElevationType, Integer> partyWall = new HashMap<ElevationType, Integer>();
            putValueIntoMap(partyWall, ElevationType.FRONT,
                    flatDetsEntry.getTENTHSOFWALLEXPOSEDFrontWall_ToOtherFlats());
            putValueIntoMap(partyWall, ElevationType.BACK, flatDetsEntry.getTENTHSOFWALLEXPOSEDBackWall_ToOtherFlats());
            putValueIntoMap(partyWall, ElevationType.LEFT, flatDetsEntry.getTENTHSOFWALLEXPOSEDLeftWall_ToOtherFlats());
            putValueIntoMap(partyWall, ElevationType.RIGHT,
                    flatDetsEntry.getTENTHSOFWALLEXPOSEDRightWall_ToOtherFlats());

            return getCorrectFieldValue(partyWall.get(elevation));
        }
    }

    /**
     * In the shape file, there are tenths attached columns and face columns;
     * when face = 10_10 attached, tenths attached is invalid, even though it
     * really means ten. This method takes a tenths attached column, and a face
     * column, and returns the most appropriate number of tenths attached for
     * those values
     *
     * @param tenthsAttachedVariable
     * @param faceVariable
     * @return a number from 0 to ten.
     */
    private static int getTenthsAttached(final Integer tenthsAttachedVariable, final Enum1303 faceVariable) {
        if (faceVariable == Enum1303._1010Attached) {
            return (int) TEN;
        } else {
            return nullToZero(tenthsAttachedVariable);
        }
    }

    private static int nullToZero(final Integer integer) {
        return integer == null ? 0 : integer.intValue();
    }

    /**
     * <p>
     * Returns tenths attached, depending on whether the dwelling is a house or
     * flat will use:
     * <pre>
     * {@link ElevateEntry#getVIEWSFRONT_FrontFace(), back etc.. } for a house.</pre>
     * <pre>
     * {@link FlatdetsEntry#getTENTHSOFWALLEXPOSEDFrontWall_ToOtherFlats, etc..} if a value exists or
     * {@link FlatdetsEntry#getTENTHSOFWALLEXPOSEDFrontWall_ToInternalAccessways, etc..}</pre>
     * </p>
     * <p>
     * Uses {@link Spss2012HelperMethods#getCorrectFieldValue(double)} to clean
     * output value.</p>
     *
     * @param elevation
     * @param elevateEntry {@link ElevateEntry}
     * @param flatDetsEntry {@link FlatdetsEntry}
     * @param isHouse
     * @return
     * @since 0.0.1-SNAPSHOT
     */
    public static final double getTenthsAttached(final ElevationType elevation,
            final ElevateEntry elevateEntry, final FlatdetsEntry flatDetsEntry, boolean isHouse) {
        final Map<ElevationType, Integer> attachedElevation = new HashMap<ElevationType, Integer>();

        if (flatDetsEntry.getDIMENSIONSOfFlat_ExternalDimensionsSameAsModule() == Enum10.No) {
            attachedElevation.put(ElevationType.FRONT, nullToZero(flatDetsEntry.getTENTHSOFWALLEXPOSEDFrontWall_ToOtherFlats()));
            attachedElevation.put(ElevationType.BACK, nullToZero(flatDetsEntry.getTENTHSOFWALLEXPOSEDBackWall_ToOtherFlats()));
            attachedElevation.put(ElevationType.LEFT, nullToZero(flatDetsEntry.getTENTHSOFWALLEXPOSEDLeftWall_ToOtherFlats()));
            attachedElevation.put(ElevationType.RIGHT, nullToZero(flatDetsEntry.getTENTHSOFWALLEXPOSEDRightWall_ToOtherFlats()));
        } else {
            attachedElevation.put(ElevationType.FRONT, nullToZero(elevateEntry.getVIEWSFRONT_TenthsAttached()));
            attachedElevation.put(ElevationType.BACK, getTenthsAttached(elevateEntry.getVIEWSBACK_TenthsAttached(), elevateEntry.getVIEWSBACK_BackFace()));
            attachedElevation.put(ElevationType.LEFT, getTenthsAttached(elevateEntry.getVIEWSLEFT_TenthsAttached(), elevateEntry.getVIEWSLEFT_LeftFace()));
            attachedElevation.put(ElevationType.RIGHT, getTenthsAttached(elevateEntry.getVIEWSRIGHT_TenthsAttached(), elevateEntry.getVIEWSRIGHT_RightFace()));
        }

        return getCorrectFieldValue(attachedElevation.get(elevation));
    }

    /**
     * Returns the tenths open for the given elevation, mappings are:
     * <p>
     * <ul>
     * <li>Front Elevation =
     * {@link ElevateEntry#getFRONTFACE_FenestrationWindow()}</li>
     * <li>Back Elevation =
     * {@link ElevateEntry#getBACKFACE_FenestrationWindow()}</li>
     * <li>Left Elevation =
     * {@link ElevateEntry#getLEFTFACE_FenestrationWindow()}</li>
     * <li>Right Elevation =
     * {@link ElevateEntry#getRIGHTFACE_FenestrationWindow()}</li>
     * </ul>
     *
     * Finally calls {@link Spss2012HelperMethods#getCorrectFieldValue(double)}
     * to ensure value is returned correctly.
     *
     * @param elevation
     * @param elevateEntry
     * @return double value tenths glazed
     * @since 1.0.2
     */
    public static final double getTenthsOpening(final ElevationType elevation,
            final ElevateEntry elevateEntry) {
        double tenthsGlazed = 0;

        switch (elevation) {
            case FRONT:
                tenthsGlazed = (elevateEntry.getFRONTFACE_FenestrationWindow() == null ? 0 : elevateEntry
                        .getFRONTFACE_FenestrationWindow());
                break;
            case BACK:
                tenthsGlazed = (elevateEntry.getBACKFACE_FenestrationWindow() == null ? 0 : elevateEntry
                        .getBACKFACE_FenestrationWindow());
                break;
            case LEFT:
                tenthsGlazed = (elevateEntry.getLEFTFACE_FenestrationWindow() == null ? 0 : elevateEntry
                        .getLEFTFACE_FenestrationWindow());

                break;
            case RIGHT:
                tenthsGlazed = (elevateEntry.getRIGHTFACE_FenestrationWindow() == null ? 0 : elevateEntry
                        .getRIGHTFACE_FenestrationWindow());
                break;
            default:
                break;
        }

        return getCorrectFieldValue(tenthsGlazed);
    }

    protected static final void putValueIntoMap(Map<ElevationType, Integer> map, ElevationType key, Integer value) {
        if (value == null) {
            map.put(key, 0);
        } else {
            map.put(key, value);
        }
    }

    /**
     * <p>
     * Used to check that 99 - Unknown or 88 - not applicable is set to
     * 0.0.</p>.
     *
     * @param value
     * @return
     * @since 0.0.1-SNAPSHOT
     */
    public static final double getCorrectFieldValue(double value) {
        if (value == UNKNOWN || value == NOT_APPLICABLE) {
            return 0.0;
        } else {
            return value;
        }
    }
}
