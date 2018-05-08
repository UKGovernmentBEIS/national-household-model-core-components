package uk.org.cse.nhm.simulator.let;

import java.util.Map;

import com.google.common.base.Optional;

/**
 * Represents the let bindings which have been made in the simulation;
 * effectively a hierarchy of immutable maps; the way to add to a let is through
 * {@link #withBinding(Object, Object)} or {@link #withBindings(Map)}.
 *
 * @author hinton
 *
 */
public interface ILets {

    /**
     * Create a new lets, overriding/adding to the bindings in this one with the
     * given key and value
     *
     * @param key
     * @param value
     * @return a let identical to this one, except where key => value
     */
    public ILets withBinding(final Object key, final Object value);

    /**
     * Create a new lets, adding the additional map of things to the bindings in
     * this one
     *
     * @param additions
     * @return
     */
    public ILets withBindings(final Map<Object, Object> additions);

    /**
     * @param clazz
     * @return a lets which is like this one, but only contains values
     * assignable to the given class.
     */
    public ILets assignableTo(final Class<?> clazz);

    /**
     * Try and lookup a key in the lets.
     *
     * @param name
     * @param clazz
     * @return
     */
    public <T> Optional<T> get(final Object name, final Class<T> clazz);

    public static final ILets EMPTY = new Lets.Empty();

    /**
     * @param letParams
     * @return A let which first looks in the given let and then in this let.
     */
    public ILets withLets(ILets letParams);

    /*
     * If there is a binding to the given name, return the lets which bound it.
     */
    public Optional<ILets> binderOf(final Object name);
}
