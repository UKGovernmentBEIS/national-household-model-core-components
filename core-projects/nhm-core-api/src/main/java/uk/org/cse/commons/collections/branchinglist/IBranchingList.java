package uk.org.cse.commons.collections.branchinglist;

import java.util.Iterator;

/**
 * An append-only list which may be iterated through in forward or reverse order
 * 
 * Other lists may be branched off of this one in an efficient manner (wthout performing a copy).
 * Lists become invalid if their parent list changes or becomes invalid after they branch.  
 * 
 * @since 1.1.2
 */
public interface IBranchingList<T> extends Iterable<T> {
	void add(T item);
	IBranchingList<T> branch();
	Iterator<T> reverseIterator();
	boolean isValid();
	void merge(final IBranchingList<T> child);
	int size();
}
