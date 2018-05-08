package uk.org.cse.nhm.spss;

/**
 * An enumeration of the different kinds of sav variable
 *
 * @author hinton
 *
 */
public enum SavVariableType {
    /**
     * A numeric variable
     */
    NUMBER,
    /**
     * A stringy variable
     */
    STRING,
    /**
     * As far as I can tell, these are some kind of archaic thing which acts to
     * pad out the data file in some way.
     */
    STRING_CONTINUATION
}
