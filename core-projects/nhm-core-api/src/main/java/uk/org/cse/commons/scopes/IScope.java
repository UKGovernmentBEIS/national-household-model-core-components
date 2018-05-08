package uk.org.cse.commons.scopes;

import java.util.List;

import com.google.common.base.Optional;

/**
 * An interface for a generic scoping mechanism
 *
 * @since 1.1.2
 */
public interface IScope<Tag> {

    /**
     * @since 1.1.2
     * @return the tag for this scope (cause/name/whatever)
     */
    public Tag getTag();

    /**
     * @since 1.1.2 Modify this note to contain the given object as a note
     */
    public void addNote(final Object note);

    /**
     * @since 1.1.2 Return all of the objects passed to {#addNote(Object)} on
     * this which are assignable to the given class.
     */
    public <T> List<T> getLocalNotes(final Class<T> noteClass);

    /**
     * @since 1.1.2 Return a single object passed to {#addNote(Object)} on this
     * which are assignable to the given class. Returns absent if 0 or many such
     * objects exist.
     */
    public <T> Optional<T> getLocalNote(final Class<T> noteClass);

    /**
     *
     * @since 1.1.2
     * @param visitor
     */
    public void accept(final IScopeVisitor<? super Tag> visitor);

    /**
     * @since 1.1.2 Returns the nearest object which matches the given class;
     * this is nearest in the sense that: if there are matching notes at this
     * level, the rightmost note is returned. Otherwise the result from the
     * rightmost child scope which yields a result is returned.
     */
    public <T> Optional<T> getNearestNote(final Class<T> noteClass);

    /**
     * @since 1.1.2 Return the sum of {#getLocalNotes(Class)} for the given
     * class together with {#getAllNotes(Class)} for the given class across
     * {#getSubScopes()}
     */
    public <T> List<T> getAllNotes(final Class<T> noteClass);

    /**
     * Return all the current child scopes of this scope. At most one child
     * scope can be open, and it must be the rightmost one
     *
     * @since 1.1.2
     */
    public List<? extends IScope<? extends Tag>> getSubScopes();

    /**
     * @return true if the scope is closed; if so you cannot add more notes to
     * it this should be true iff all child scopes are closed
     * @since 1.1.2
     */
    public boolean isClosed();

    /**
     * @return true if this scope contains no notes, and all its children are
     * empty
     * @since 1.2.2
     */
    public boolean isEmpty();
}
