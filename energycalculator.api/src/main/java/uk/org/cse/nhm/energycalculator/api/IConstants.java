package uk.org.cse.nhm.energycalculator.api;

/**
 * An interface for a constants table. See also {@link IConstant}.
 * 
 * @author hinton
 *
 */
public interface IConstants {
	/**
	 * Get a constant out of the constants table, returning it as an instance of the given class if possible
	 * @param key the constant to look up
	 * @param clazz the class to return
	 * @return the value of the given constant, if it can be represented as the given class. Otherwise you will get a runtime exception.
	 */
	public <T, Q extends Enum<Q> & IConstant> T get(final Q key, final Class<T> clazz);
	/**
	 * Synonymous with <code>get(key, Double.class)</code>
	 * @param key
	 * @return the double value for the given key
	 */
	public <Q extends Enum<Q> & IConstant> double get(final Q key);
	/**
	 * Synonymous with <code>get(key, double[].class)[index]</code>
	 * @param key
	 * @param index
	 * @return the double value for the given key at the index.
	 */
	public <Q extends Enum<Q> & IConstant> double get(final Q key, final int index);
	
	/**
	 * Synonymous with <code>get(key, index.ordinal())</code>. The intent is that for constants which are tables (i.e. arrays of doubles) indexed by
	 * an enum type, you can use this to lookup the appropriate value.
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public <Q extends Enum<Q> & IConstant> double get(final Q key, final Enum<?> index);
}
