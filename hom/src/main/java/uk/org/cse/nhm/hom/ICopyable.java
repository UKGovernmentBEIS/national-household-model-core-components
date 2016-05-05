package uk.org.cse.nhm.hom;

/**
 * This interface is used to mark up objects which can be safely deep-copied, and is typically
 * present on most of the dimensions of a house.
 * 
 * @author hinton
 *
 * @param <T>
 */
public interface ICopyable<T> {
	/**
	 * @return a fully detatched copy of the invokee of this method call.
	 */
	public T copy();
}
