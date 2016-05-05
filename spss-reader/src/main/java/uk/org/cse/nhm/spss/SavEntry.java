package uk.org.cse.nhm.spss;

/**
 * A row from a SAV file
 * 
 * @author hinton
 *
 */
public interface SavEntry {
	/**
	 * Get the value of the given variable out of the row, in the given type
	 * @param variable the variable to get (as picked from {@link SavMetadata#getVariables()})
	 * @param clazz the type to return
	 * @return
	 */
	public <T> T getValue(final SavVariable variable, Class<T> clazz);
	/**
	 * Get the value of the given variable out of the row, in the given type
	 * @param variable the column number of the variable (0 based, in the order given by {@link SavMetadata#getVariables()}
	 * @param clazz the type to return
	 * @return
	 */
	public <T> T getValue(final int variable, Class<T> clazz);
	
	/**
	 * @param variable
	 * @return true if the stored value for the variable at the given index is a missing value marker
	 */
	public boolean isMissing(final int variable);
	
	/**
	 * @param variable
	 * @return {@link #isMissing(int)}( {@link SavVariable#getIndex()} )
	 */
	public boolean isMissing(final SavVariable variable);
	/**
	 * 
	 * @param variable
	 * @return true iff the given variable has one of the predefined values in this entry
	 */
	public boolean isPredefinedValue(SavVariable variable);
}
