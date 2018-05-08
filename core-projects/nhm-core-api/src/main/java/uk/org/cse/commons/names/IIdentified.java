package uk.org.cse.commons.names;

/**
 * An interface for parts of the scenario which have a name and a location
 *
 * @since 4.0.0
 */
public interface IIdentified {

    /**
     * @return the name of this named thing
     */
    public Name getIdentifier();
}
