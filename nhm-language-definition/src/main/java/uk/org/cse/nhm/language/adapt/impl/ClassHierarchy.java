package uk.org.cse.nhm.language.adapt.impl;

import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

class ClassHierarchy implements Iterable<Class<?>> {
	private final Class<?> root;

	private ClassHierarchy(Class<?> root) {
		super();
		this.root = root;
	}
	
	public static final Iterable<Class<?>> of(final Class<?> root) {
		return new ClassHierarchy(root);
	}

	static class It implements Iterator<Class<?>> {
		final Deque<Class<?>> search;
		final Set<Class<?>> done = new HashSet<Class<?>>();
		public It(final Class<?> root) {
			search = new LinkedList<>();
			search.add(root);
		}

		@Override
		public boolean hasNext() {
			return search.isEmpty();
		}

		@Override
		public Class<?> next() {
			final Class<?> c = search.pollLast();
			if (c.getSuperclass() != null) search.add(c.getSuperclass());
			for (final Class<?> i : c.getInterfaces()) {
				if (done.contains(i)) continue;
				done.add(i);
				search.add(i);
			}
			return c;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public Iterator<Class<?>> iterator() {
		return new It(root);
	}
}
