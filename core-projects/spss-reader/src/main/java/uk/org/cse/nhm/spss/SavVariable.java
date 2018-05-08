package uk.org.cse.nhm.spss;

import uk.org.cse.nhm.spss.impl.SavVariableValues;

/**
 * Represents a variable in a sav file (a column in a table, effectively).
 * Yielded typically by {@link SavMetadata#getVariables()}
 *
 * @author hinton
 *
 */
public interface SavVariable {

    /**
     * @return the type of the variable
     */
    public SavVariableType getType();

    /**
     * @return the (lovely 8-character) name of the variable
     */
    public String getName();

    public void rename(final String newName);

    /**
     * @return a descriptive string for the variable
     */
    public String getLabel();

    /**
     * @return as far as I can tell, 0 if this was a numeric value, or a number
     * of bytes if it was a stringy value
     */
    public int getVariableTypeCode();

    /**
     * @return the 0-based index of this variable into its
     * {@link SavMetadata#getVariables()}
     */
    public int getIndex();

    /**
     * @param value
     * @return the given double value for this variable, formatted by looking it
     * up in the missing values table etc.
     */
    public String decode(final double value);

    /**
     * @param value
     * @return true if this double is amissing value marker
     */
    public boolean isMissingValue(final double value);

    public String getValueLabel(final Double value);

    public SavVariableValues getVariableValues();

    public boolean isRestricted();

    boolean isNominal();
}
