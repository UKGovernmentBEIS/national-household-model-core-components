package uk.org.cse.nhm.spss;

import java.util.List;

import uk.org.cse.nhm.spss.impl.SavVariableValues;

/**
 * Metadata for a sav file (this is the header).
 *
 * @author hinton
 *
 */
public interface SavMetadata {

    /**
     * @return A list of the variables (columns) in this sav file.
     */
    public List<? extends SavVariable> getVariables();

    public int getEntryCount();

    /**
     * @param varname
     * @return the variable with that name, or null if no such variable
     */
    public SavVariable getVariable(String varname);

    public List<SavVariableValues> getVariableValues();

    public SavVariable getVariableIgnoreCase(String string);
}
