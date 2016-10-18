package uk.org.cse.commons.scopes.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.commons.scopes.IScopeVisitor;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Provides a naive implementation of IScope; the operating methods
 * are {#root(Tag)} to create a new top-level scope, and {#child(Tag)}
 * to create a new child of a given scope.
 */
public class Scope<Tag> implements IScope<Tag> {
	private static final String MAKE_CHILD = "create a child in";
	private static final String APPEND = "append to";
	private static final String CLOSE = "close";

	private final Tag tag;
	/**
	 * This list of lists contains all of the objects added into this
	 * scope.  It's a list of lists so that we can tell where objects
	 * come in relation to child scopes; the 0th element contains
	 * elements added before any child scope was created. The 1st list
	 * contains those elements added after the first child scope was
	 * closed but before the second was opened, and so on.
	 */
	private List<List<Object>> notes = new ArrayList<List<Object>>(1);
	/**
	 * This list will always be the last element in {#notes}
	 */
	private List<Object> currentNotes = new ArrayList<Object>(1);
	private List<Scope<? extends Tag>> subscopes = new ArrayList<Scope<? extends Tag>>(1);
	private Scope<? extends Tag> rightScope = null;
	private boolean closed = false;

	protected Scope(final Tag tag) {
		this.tag = tag;
		notes.add(currentNotes);
	}

	public static <Tag> Scope<Tag> root(final Tag tag) {
		return new Scope<Tag>(tag);
	}

	public <T2 extends Tag> Scope<T2> child(final T2 tag) {
		final Scope<T2> child = new Scope<T2>(tag);

		addChild(child);
		
		return child;
	}
	
	protected void addChild(final Scope<? extends Tag> child) {
		dieIfClosed(MAKE_CHILD);
		dieIfChildIsOpen(MAKE_CHILD);
		rightScope = child;
		subscopes.add(child);
		currentNotes = new LinkedList<Object>();
		notes.add(currentNotes);
	}

	@Override
	public Tag getTag() {
		return tag;
	}

	@Override
	public void addNote(final Object note) {
		dieIfClosed(APPEND);
		dieIfChildIsOpen(APPEND);
		currentNotes.add(note);
	}

	private <T> void appendAllNotes(final Class<T> noteClass,
									final ImmutableList.Builder<T> builder) {
		// to make sure we get the notes out in the right order,
		// respecting sub-scopes, we iterate through notes and
		// subscopes in tandem, so we need two iterators for that
		
		final Iterator<List<Object>> ni = notes.iterator();
		final Iterator<Scope<? extends Tag>> si = subscopes.iterator();
		
		// this flag is used to tell us whether we just finished
		// looking in a sub-scope; initialized to true to get elements
		// added to this scope before any sub-scope was created.
		boolean doNotes = true;

		while (ni.hasNext() || si.hasNext()) {
			if (ni.hasNext() && (doNotes || !si.hasNext())) {
				// we end up in this condition if we have more notes,
				// and it's either notes' turn or there are no
				// subscopes left
				appendNotes(noteClass, builder, ni.next());
				doNotes = false;
			} else if (si.hasNext()) {
				// otherwise we should do a subscope
				si.next().appendAllNotes(noteClass, builder);
				doNotes = true;
			}
		}
	}

	private static <T> void appendNotes(final Class<T> noteClass, 
										final ImmutableList.Builder<T> builder,
										final List<Object> n) {
		for (final Object o : n) {
			if (noteClass.isInstance(o)) {
				builder.add(noteClass.cast(o));
			}
		}
	}

	@Override
	public <T> List<T> getLocalNotes(final Class<T> noteClass) {
		final ImmutableList.Builder<T> builder = ImmutableList.builder();
		for (final List<Object> list : notes) {
			appendNotes(noteClass, builder, list);
		}
		return builder.build();
	}
	
	@Override
	public <T> Optional<T> getLocalNote(final Class<T> noteClass) {
		final List<T> notes = getLocalNotes(noteClass);
		if(notes.size() == 1) {
			return Optional.of(notes.get(0));
		} else {
			return Optional.absent();
		}
	}

	@Override
	public <T> List<T> getAllNotes(final Class<T> noteClass) {
		final ImmutableList.Builder<T> builder = ImmutableList.builder();

		appendAllNotes(noteClass, builder);

		return builder.build();
	}

	@Override
	public List<Scope<? extends Tag>> getSubScopes() {
		return Collections.unmodifiableList(subscopes);
	}

	@Override
	public boolean isClosed() {
		return closed;
	}
	
	@Override
	public boolean isEmpty() {
		for (final List<Object> notesList : notes) {
			if (!notesList.isEmpty()) return false;
		}
		
		for (final Scope<?> scope : subscopes) {
			if (!scope.isEmpty()) return false;
		}
		
		return true;
	}

	@Override
	public <T> Optional<T> getNearestNote(final Class<T> noteClass) {
		final Queue<Scope<?>> toLookAt = new LinkedList<Scope<?>>();
		toLookAt.add(this);

		while (!toLookAt.isEmpty()) {
			final Scope<?> current = toLookAt.poll();
			final List<T> localNote = current.getLocalNotes(noteClass);
			if (!localNote.isEmpty()) {
				return Optional.of(localNote.get(localNote.size()-1));
			}
			toLookAt.addAll(Lists.reverse(current.getSubScopes()));
		}

		return Optional.absent();
	}
	
	public void close() {
		dieIfClosed(CLOSE);
		dieIfChildIsOpen(CLOSE);
		closed = true;
		
		removeEmptyChildren();
	}

	private void removeEmptyChildren() {
		// this is a list which we are using to accumulate the 
		// new value of notes (a list of lists, each containing the notes
		// recorded before the child at the same index)
		final Deque<ImmutableList.Builder<Object>>
			notesAccumulator = new LinkedList<>();
		
		final ImmutableList.Builder<Scope<? extends Tag>>
			childrenBuilder = ImmutableList.builder();
		
		final Iterator<List<Object>> notesIter = notes.iterator();
		final Iterator<Scope<? extends Tag>> childIter = subscopes.iterator();
		
		notesAccumulator.add(ImmutableList.builder());
		
		while (notesIter.hasNext()) {
			final List<Object> beforeThisChild = notesIter.next();
			notesAccumulator.getLast().addAll(beforeThisChild);
			if (childIter.hasNext()) {
				final Scope<? extends Tag> child = childIter.next();
				if (!child.isEmpty()) {
					notesAccumulator.add(ImmutableList.builder());
					childrenBuilder.add(child);
				}
			}
		}
		
		final ImmutableList.Builder<List<Object>> notesBuilder = ImmutableList.builder();
		for (final ImmutableList.Builder<Object> b : notesAccumulator) {
			notesBuilder.add(b.build());
		}
		
		final ImmutableList<List<Object>> newNotes = notesBuilder.build();
		final ImmutableList<Scope<? extends Tag>> newSubscopes = childrenBuilder.build();
		notes = newNotes;
		subscopes = newSubscopes;
	}
	
	public <T> Optional<T> getNearest(final Class<T> noteClass) {
		final Queue<Scope<?>> toLookAt = new LinkedList<Scope<?>>();
		toLookAt.add(this);
		
		while(!toLookAt.isEmpty()) {
			final Scope<?> current = toLookAt.poll();
			final List<T> localNote = current.getLocalNotes(noteClass);
			if(!localNote.isEmpty()) {
				return Optional.of(localNote.get(0));
			}
			toLookAt.addAll(current.getSubScopes());
		}
		
		return Optional.absent();
	}
	
	@Override
	public void accept(final IScopeVisitor<? super Tag> visitor) {
		visitor.enterScope(tag);
		
		final Iterator<List<Object>> ni = notes.iterator();
		final Iterator<Scope<? extends Tag>> si = subscopes.iterator();
		
		// this flag is used to tell us whether we just finished
		// looking in a sub-scope; initialized to true to get elements
		// added to this scope before any sub-scope was created.
		boolean doNotes = true;
		
		while (ni.hasNext() || si.hasNext()) {
			if (ni.hasNext() && (doNotes || !si.hasNext())) {
				// we end up in this condition if we have more notes,
				// and it's either notes' turn or there are no
				// subscopes left
				for (final Object o : ni.next()) {
					visitor.visit(o);
				}
				doNotes = false;
			} else if (si.hasNext()) {
				// otherwise we should do a subscope
				si.next().accept(visitor);
				doNotes = true;
			}
		}
		
		visitor.exitScope();
	}
	
	

	private void dieIfClosed(final String msg) {
		if (closed) {
			throw new IllegalStateException(String.format("Cannot %s a closed scope", msg));
		}
	}

	private void dieIfChildIsOpen(final String msg) {
		if (rightScope != null && !rightScope.isClosed()) {
			throw new IllegalStateException(String.format("Cannot %s a scope with an open child", msg));
		}
	}
}
