package uk.org.cse.commons.collections.branchinglist;

import java.util.Iterator;
import java.util.LinkedList;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoDetectPolicy;
import org.pojomatic.annotations.AutoProperty;
import org.pojomatic.annotations.PojomaticPolicy;
import org.pojomatic.annotations.Property;

import com.google.common.collect.Iterators;

@AutoProperty(autoDetect=AutoDetectPolicy.FIELD)
public class BranchingList<T> implements IBranchingList<T> {
	private LinkedList<T> elements = new LinkedList<T>();
	private final BranchingList<T> parent;
	@Property(policy=PojomaticPolicy.NONE)
	private final int parentLength;
	@Property(policy=PojomaticPolicy.NONE)
	private boolean invalidatedByMerge = false;
	
	protected BranchingList(BranchingList<T> parent) {
		if(parent == null) {
			this.parent = null;
			this.parentLength = 0;
		} else {
			this.parent = parent;
			this.parentLength = parent.getSegmentLength();
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		return Pojomatic.equals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
	
	public static <T> BranchingList<T> create() {
		return new BranchingList<T>(null); 
	}

	@Override
	public boolean isValid() {
		return !(invalidatedByMerge) && 
				(parent == null || (parent.isValid() && (parent.getSegmentLength() == parentLength)));
	}

	protected boolean isRoot() {
		return parent == null;
	}
	
	protected void assertValid() {
		if (!isValid()) {
			throw new IllegalStateException("This branching list is no longer valid because one of its ancestors has been modified since it was branched.");
		}
	}
	
	@Override
	public void add(T item) {
		assertValid();
		elements.add(item);
	}

	@Override
	public IBranchingList<T> branch() {
		return new BranchingList<T>(this);
	}
	
	@Override
	public Iterator<T> iterator() {
		assertValid();

		Iterator<T> iterator = elements.iterator();
		
		if (parent != null) {
			iterator = Iterators.concat(parent.iterator(), iterator);
		}
		
		return Iterators.unmodifiableIterator(iterator);
	}

	@Override
	public Iterator<T> reverseIterator() {
		assertValid();

		Iterator<T> iterator = reverseIterator(elements);
		
		if (parent != null) {
			iterator = Iterators.concat(iterator, parent.reverseIterator());
		}
		
		return Iterators.unmodifiableIterator(iterator);
	}

	private Iterator<T> reverseIterator(final LinkedList<T> list) {
		return list.descendingIterator();
	}

	protected int getSegmentLength() {
		return elements.size();
	}
	
	protected void cannotModify() {
		throw new UnsupportedOperationException("Cannot remove from an appendable list iterator.");
	}
	
	@Override
	public final void merge(final IBranchingList<T> child) {
		if (child instanceof BranchingList) {
			final BranchingList<T> _child = (BranchingList<T>) child;
			if (_child.parent != this) {
				throw new IllegalArgumentException("Only one level of child can be merged; the provided child is not my direct descendent");
			}
			// copy children /using add/ so that the subclass' add method is consistent
			for (final T childElement : _child.elements) {
				add(childElement);
			}
			// you may never use child again at this point
			_child.invalidatedByMerge = true;
		} else {
			throw new IllegalArgumentException("Cannot merge " + this.getClass().getCanonicalName() + " with " + child.getClass().getCanonicalName() + " as it is not of my type");
		}
	}
	
	@Override
	public int size() {
		return elements.size() + (parent == null ? 0 : parent.size());
	}
}
