package uk.org.cse.nhm.hom.types;

/**
 * BuiltFormType.
 *
 * @author richardt
 * @version $Id: BuiltFormType.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public enum BuiltFormType {
    EndTerrace,
    MidTerrace,
    SemiDetached,
    Detached,
    Bungalow,
    ConvertedFlat,
    PurposeBuiltLowRiseFlat,
    PurposeBuiltHighRiseFlat;

    public boolean isFlat() {
        return this == ConvertedFlat || this == PurposeBuiltHighRiseFlat || this == PurposeBuiltLowRiseFlat;
    }
}
