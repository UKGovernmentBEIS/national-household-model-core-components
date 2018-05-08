package uk.org.cse.nhm.bundle.api;

/**
 * Represents a region in a scenario file
 */
public interface ILocation<P> {

    public P path();

    public int line();

    public int column();

    public int offset();

    public int length();

    public LocationType type();

    public enum LocationType {
        Source,
        Include,
        Template
    }
}
