package uk.org.cse.nhm.energycalculator.api;

/**
 * An interface for a constant; just has {@link #getValue(Class)} to ask the constant its value as a particular type.
 * @author hinton
 *
 */
public interface IConstant {
	/**
	 * Get the given constant as the relevant type.
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> T getValue(final Class<T> clazz);
}
