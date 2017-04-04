package cse.nhm.ide.stock.ui.editors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.deferred.AbstractConcurrentModel;
import org.eclipse.jface.viewers.deferred.IConcurrentModelListener;

public class ListModel extends AbstractConcurrentModel {
	private List<Object> contents = new ArrayList<>();
	
	@Override
	public void requestUpdate(IConcurrentModelListener listener) {
		listener.setContents(contents());
	}

	public synchronized Object[] contents() {
		return contents.toArray(new Object[contents.size()]);
	}
	
	public synchronized void clear() {
		if (contents.isEmpty()) return;
		final Object[] stuff = contents();
		contents.clear();
		fireRemove(stuff);
	}
	
	public synchronized void add(final Object o) {
		contents.add(o);
		fireAdd(new Object[] {o});
	}

	public synchronized void addAll(Collection<? extends Object> stuff) {
		if (stuff.isEmpty()) return;
		final Object[] added = stuff.toArray();
		contents.addAll(stuff);
		fireAdd(added);
	}
}
