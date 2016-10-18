package cse.nhm.ide.stock.ui.editors;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.google.common.collect.ImmutableList;

public class JsonObjectViewer extends TreeViewer {

	public JsonObjectViewer(final Composite parent, final int style) {
		super(parent, style);
		
		setContentProvider(new ITreeContentProvider() {
			@Override
			public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {}
			
			@Override
			public void dispose() {}
			
			@Override
			public boolean hasChildren(final Object element) {
				if (element instanceof JsonValue) {
					final JsonValue jo = (JsonValue) element;
					if (jo.isArray()) {
						return jo.asArray().size() > 0;
					} else if (jo.isObject()) {
						return !jo.asObject().names().isEmpty();
					}
				} else if (element instanceof Object[]) {
					final Object[] oo = (Object[]) element;
					return hasChildren(oo[1]);
				}
				return false;
			}
			
			@Override
			public Object getParent(final Object element) {
				if (element instanceof Object[]) {
					return ((Object[])element)[2];
				} else {
					return null;
				}
			}
			
			@Override
			public Object[] getElements(final Object inputElement) {
				if (inputElement instanceof JsonValue) {
					return getChildren((JsonValue) inputElement, null);
				} else {
					return new Object[0];
				}
			}
			
			private Object[] getChildren(final JsonValue element, final Object parent) {
				final JsonValue jo = element;
				
				if (jo.isObject()) {
					final JsonObject jo_ = jo.asObject();
					final List<String> names = ImmutableList.copyOf(jo_.names());
					final Object[] result = new Object[names.size()];
					for (int i = 0; i<names.size(); i++) {
						result[i] = new Object[] {names.get(i), jo_.get(names.get(i)), parent};
					}
					return result;
				} else if (jo.isArray()) {
					final Object[] result = new Object[jo.asArray().size()];
					for (int i = 0; i<result.length; i++) {
						result[i] = new Object[] {i, jo.asArray().get(i), parent};
					}
					return result;
				}
				
				return new Object[0];
			}
			
			@Override
			public Object[] getChildren(final Object element) {
				if (element instanceof Object[]) {
					final Object[] oo = (Object[]) element;
					if (oo[1] instanceof JsonValue) {
						return getChildren((JsonValue) oo[1], element);
					}
				}
				return new Object[0];
			}
		});
		

		final TreeViewerColumn key = new TreeViewerColumn(this, SWT.NONE);
		final TreeViewerColumn val = new TreeViewerColumn(this, SWT.NONE);
		getTree().setHeaderVisible(true);
		getTree().setLinesVisible(true);
		
		key.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof Object[]) {
					return String.valueOf(((Object[]) element)[0]);
				} else {
					return super.getText(element);
				}
			}
		});
		
		val.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof Object[]) {
					return String.valueOf(((Object[]) element)[1]);
				} else {
					return super.getText(element);
				}
			}
		});
		
		key.getColumn().setText("Attribute");
		key.getColumn().setWidth(100);
		val.getColumn().setText("Value");
		val.getColumn().setWidth(100);
		
		addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(final DoubleClickEvent event) {
				// add a column to the master table for the field selected
				final ISelection selection = event.getSelection();
				// we want to know the path to here.
				if (selection instanceof IStructuredSelection) {
					Object o = ((IStructuredSelection) selection).getFirstElement();
					final LinkedList<Object> path = new LinkedList<>();
					while (o instanceof Object[]) {
						final Object[] oo = (Object[]) o;
						if (oo.length > 2) {
							path.addFirst(oo[0]);
							final Object t = oo[2];
							if (t != null) {
								o = t;
							} else {
								break;
							}
						} else {
							break;
						}
					}
					for (final IFieldSelectionListener fsl : listeners) {
						fsl.fieldSelected(path);
					}
				}
			}
		});
	}
	
	private final List<IFieldSelectionListener> listeners = new LinkedList<JsonObjectViewer.IFieldSelectionListener>();
	
	public void addFieldSelectionListener(final IFieldSelectionListener fsl) {
		listeners.add(fsl);
	}

	public interface IFieldSelectionListener {
		public void fieldSelected(final List<Object> key);
	}
}
