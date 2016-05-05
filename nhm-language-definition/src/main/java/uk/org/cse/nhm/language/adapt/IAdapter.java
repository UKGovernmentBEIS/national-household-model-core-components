package uk.org.cse.nhm.language.adapt;

import java.util.Set;

public interface IAdapter {
    /**
     * @return true if this adapter can convert the object from into some other type
     */
    public boolean adapts(final Object from);
    /**
     * @return true if this adapter can convert the object from into something
     * where to.isInstance(that thing) would be true.
     * If this method returns true, adapts(from) should also be true
     */
	public boolean adapts(final Object from, final Class<?> to);

    /**
     * @return true if there is some possible object A
     * where:
     * a) from.isInstance(A) is true, and
     * b) adapts(A, to) is true
     */
    public boolean adaptsType(final Class<?> from, final Class<?> to);
    /**
     * Convert from into an instance of the class to
     * @return either an instanceof to, or null if impossible
     */
    public <T> T adapt(final Object from, final Class<T> to, IAdaptingScope scope);
    /**
     * @return a set of all the classes X where adaptsType(X, Y) is true for some Y.
     */
	public Set<Class<?>> getAdaptableClasses();
}
