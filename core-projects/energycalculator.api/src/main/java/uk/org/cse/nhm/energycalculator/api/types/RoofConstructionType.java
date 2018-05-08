package uk.org.cse.nhm.energycalculator.api.types;

public enum RoofConstructionType {
    PitchedSlateOrTiles(true),
    Thatched(true),
    Flat(false);

    private final boolean pitched;

    RoofConstructionType(final boolean pitched) {
        this.pitched = pitched;
    }

    public final boolean isPitched() {
        return pitched;
    }
}
