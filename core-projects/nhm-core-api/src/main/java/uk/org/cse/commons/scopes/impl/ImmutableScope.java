package uk.org.cse.commons.scopes.impl;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Optional;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.commons.scopes.IScopeVisitor;

public class ImmutableScope<Tag> implements IScope<Tag> {

    private final Tag tag;

    public static final <Tag> ImmutableScope<Tag> of(final Tag tag) {
        return new ImmutableScope<Tag>(tag);
    }

    protected ImmutableScope(final Tag tag) {
        this.tag = tag;
    }

    @Override
    public Tag getTag() {
        return tag;
    }

    protected final void die() {
        throw new UnsupportedOperationException("Cannot modify an immutable scope");
    }

    @Override
    public void addNote(final Object note) {
        die();
    }

    @Override
    public <T> List<T> getLocalNotes(final Class<T> noteClass) {
        return Collections.emptyList();
    }

    @Override
    public <T> Optional<T> getLocalNote(final Class<T> noteClass) {
        return Optional.absent();
    }

    @Override
    public <T> List<T> getAllNotes(final Class<T> noteClass) {
        return Collections.emptyList();
    }

    @Override
    public List<? extends IScope<? extends Tag>> getSubScopes() {
        return Collections.emptyList();
    }

    @Override
    public boolean isClosed() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public <T> Optional<T> getNearestNote(final Class<T> noteClass) {
        return Optional.absent();
    }

    @Override
    public void accept(final IScopeVisitor<? super Tag> visitor) {
        visitor.enterScope(tag);
        visitor.exitScope();
    }
}
