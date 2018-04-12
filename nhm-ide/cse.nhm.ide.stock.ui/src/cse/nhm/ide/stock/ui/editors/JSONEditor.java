package cse.nhm.ide.stock.ui.editors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.common.io.CountingInputStream;

import cse.nhm.ide.stock.ui.editors.JsonObjectViewer.IFieldSelectionListener;

public class JSONEditor extends EditorPart implements ISelectionChangedListener {
	private IURIEditorInput uriInput;

	private final List<List<Object>> columns = new ArrayList<>();
	private TableViewer master;
	private Sorter masterSorter;
	
	private final List<Object> contentsModel = Collections.synchronizedList(new ArrayList<>());
	private final Job loadInputJob = new Job("Load input") {
		private JsonValue dedup(final JsonValue input, final Interner<String> si) {
			if (input.isString()) {
				return Json.value(si.intern(input.asString()));
			} else if (input.isObject()) {
				final JsonObject jo = input.asObject();
				final JsonObject joo = Json.object();
				for (final String s : jo.names()) {
					joo.add(si.intern(s), dedup(jo.get(s), si));
				}
				return joo;
			} else if (input.isArray()) {
				final JsonArray ja = input.asArray();
				final JsonArray jao = ((JsonArray) Json.array());
				for (int i = 0; i<ja.size(); i++) {
					jao.add(dedup(ja.get(i), si));
				}
				return jao;
			} else {
				return input;
			}
		}
		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			contentsModel.clear();
			
			Runnable refresh = new Runnable() {
				@Override
				public void run() {
					master.refresh();
				}
			};
			
			if (uriInput != null) {
				monitor.beginTask("Load data from " + uriInput.getName(), 100);
				try {
					final Interner<String> duplicateStrings = 
							Interners.newStrongInterner();
					final IFileStore store = EFS.getStore(uriInput.getURI());
					final int totalCount = (int) store.fetchInfo().getLength();
					final CountingInputStream countstream;
					try (final BufferedReader r = new BufferedReader(new InputStreamReader(maybeUnzip(uriInput.getName(), countstream = new CountingInputStream(store.openInputStream(EFS.NONE, new SubProgressMonitor(monitor, 10))))))) {
						final SubProgressMonitor readMonitor = new SubProgressMonitor(monitor, 90);
						readMonitor.beginTask("Reading data", totalCount);
						
						int offset = 0;
						final List<JsonValue> buffer = new ArrayList<>(100);
						// the json parser in eclipsesource minimal json does not like
						// reading multiple values from a stream, which is a shame
						// however, I think we can assume it is one line per object.
						String line;
						while ((line = r.readLine()) != null) {
							final JsonValue v = Json.parse(line);
							buffer.add(dedup(v, duplicateStrings));
							if (monitor.isCanceled()) break;
							if (buffer.size() > 100) {
								contentsModel.addAll(buffer);
								buffer.clear();
								Display.getDefault().asyncExec(refresh);
							}
							readMonitor.worked((int) (countstream.getCount() - offset));
							offset = (int) countstream.getCount();
						}
						if (!monitor.isCanceled()) {
							contentsModel.addAll(buffer);
							buffer.clear();
						}
					}
				} catch (CoreException | IOException e) {
					e.printStackTrace();
				}
			}
			
			monitor.done();
			
			return monitor.isCanceled() ? Status.CANCEL_STATUS : Status.OK_STATUS;
		}
		private InputStream maybeUnzip(final String name, final InputStream countingInputStream) throws IOException {
			if (name.endsWith(".stock") || name.endsWith(".json.gz")) {
				return new GZIPInputStream(countingInputStream);
			} else {
				return countingInputStream;
			}
 		}
	};
	
	private JsonObjectViewer detail;


	static class Sorter implements SelectionListener, DisposeListener {
		final LinkedList<TableColumn> columns = new LinkedList<>();
		final LinkedList<Boolean> sortOrder = new LinkedList<>();
		private TableViewer viewer;
		
		public Sorter(TableViewer master) {
			viewer = master;
		}

		public ViewerComparator getComparator() {
			final ImmutableList.Builder<List<?>> bcolumns = 
					ImmutableList.builder();
			
			for (final TableColumn tc : columns) {
				final Object p = tc.getData("path");
				if (p instanceof List) {
					bcolumns.add((List<?>)tc.getData("path"));
				}
			}
			
			final List<List<?>> columns = bcolumns.build();
			final List<Boolean> sortOrder = ImmutableList.copyOf(this.sortOrder);
			return new ViewerComparator() {

				@Override
				public int compare(final Viewer v, final Object e1, final Object e2) {
					// these two things are two json nodes
					// want to get the column for each one and so on.
					if (columns.isEmpty())
						return 0;
					if (e1 instanceof JsonValue && e2 instanceof JsonValue) {
						final JsonValue j1 = (JsonValue) e1;
						final JsonValue j2 = (JsonValue) e2;
						final Iterator<Boolean> sort = sortOrder.iterator();
						for (final List<?> l : columns) {
							final boolean dir = sort.next();
							final int gt = dir ? -1 : 1;
							final int lt = gt * -1;
							final Object v1 = getColumn(j1, l);
							final Object v2 = getColumn(j2, l);
							if (Objects.equal(v1, v2))
								continue;
							if (v1 == null)
								return lt;
							if (v2 == null)
								return gt;
							if (v1 instanceof JsonValue && v2 instanceof JsonValue) {
								final JsonValue jv1 = (JsonValue) v1;
								final JsonValue jv2 = (JsonValue) v2;
								if (jv1.isNumber() && jv2.isNumber()) {

									final int nc = Double.compare(
											jv1.asDouble(), jv2.asDouble());
									if (nc < 0)
										return lt;
									else if (nc > 0)
										return gt;
									else
										continue;

								}
							}
							
							final int sc = String.valueOf(v1).compareTo(String.valueOf(v2));
							if (sc < 0) return lt;
							else if (sc > 0) return gt;
							else continue;
						}
					}
					return 0;
				}
			};
		}
		
		@Override
		public void widgetSelected(final SelectionEvent e) {
			// column was clicked
			if (e.widget instanceof TableColumn) {
				final TableColumn theColumn = (TableColumn) e.widget;
				final int index = columns.indexOf(theColumn);
				if (index == 0) {
					final Boolean b = sortOrder.poll();
					if (b == null || b == false) {
						sortOrder.addFirst(true);
					} else {
						sortOrder.addFirst(false);
					}
				} else {
					// push this item to front of queue
					if (index >= 0) {
						columns.remove(index);
						sortOrder.remove(index);
					}
					columns.addFirst(theColumn);
					
					sortOrder.add(false);
				}
				final TableColumn peek = columns.peek();
				peek.getParent().setSortColumn(peek);
				peek.getParent().setSortDirection(sortOrder.peek() ? SWT.UP : SWT.DOWN);
				
				viewer.setComparator(getComparator());
			}
		}

		@Override
		public void widgetDefaultSelected(final SelectionEvent e) {
			widgetSelected(e);
		}

		@Override
		public void widgetDisposed(final DisposeEvent e) {
			final Widget w = e.widget;
			
			if (w instanceof TableColumn) {
				((TableColumn) w).removeSelectionListener(this);
				((TableColumn) w).removeDisposeListener(this);
			}
			
			final int ix = columns.indexOf(w);
			if (ix >= 0) {
				columns.remove(ix);
				sortOrder.remove(ix);
			}
		}
	}
	
	public JSONEditor() {
		columns.add(ImmutableList.<Object>of("basicAttributes", "aacode"));
		columns.add(ImmutableList.<Object>of("basicAttributes", "dwellingCaseWeight"));
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {
		
	}

	@Override
	public void doSaveAs() {
		
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}
	
	@Override
	public void dispose() {
		loadInputJob.cancel();
		super.dispose();
	}
	
	protected static Object getColumn(final JsonValue jo, final List<?> path) {
		Object o = jo;
		for (final Object k : path) {
			if (o == null) continue;
			if (k instanceof String) {
				if (o instanceof JsonValue && ((JsonValue) o).isObject()) {
					o = ((JsonValue) o).asObject().get((String) k);
					continue;
				}
			} else if (k instanceof Integer) {
				if (o instanceof JsonValue && ((JsonValue)o).isArray()) {
					o = ((JsonValue) o).asArray().get((Integer)k); 
					continue;
				}
			}
			o = null;
			break;
		}
		
		return o;
	}
	
	static class Line {
		int number;
		String value;
		public Line(final int number, final String value) {
			super();
			this.number = number;
			this.value = value;
		}
	}

	@Override
	protected void setInput(final IEditorInput input) {
		super.setInput(input);
		setPartName(input.getName());
		if (input instanceof IURIEditorInput) {
			uriInput = (IURIEditorInput) input;
			setContentDescription(uriInput.getURI().toString());
			setColumns();
			loadInputJob.cancel();
			loadInputJob.schedule();
		}
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(final Composite parent) {
		// we are going to have two bits
		// top bit: a list of all the rows
		// bottom bit: tree viewer
		
		parent.setLayout(new FillLayout());
		final SashForm sash = new SashForm(parent, SWT.HORIZONTAL);
		
		master = new TableViewer(sash, SWT.BORDER | SWT.VIRTUAL | SWT.FULL_SELECTION);
		master.setUseHashlookup(true);
		detail = new JsonObjectViewer(sash, SWT.BORDER | SWT.FULL_SELECTION);
		
		
		final ArrayContentProvider dcp = ArrayContentProvider.getInstance();
		masterSorter = new Sorter(master);
		master.setContentProvider(dcp);
		
		final Table t = master.getTable();
		t.setHeaderVisible(true);
		t.setLinesVisible(true);
		
		master.addSelectionChangedListener(this);
		
		detail.addFieldSelectionListener(new IFieldSelectionListener() {
			@Override
			public void fieldSelected(final List<Object> key) {
				columns.add(ImmutableList.<Object>copyOf(key));
				setColumns();
			}
		});
		
		setColumns();
		
		detail.getTree().setToolTipText("Double-click to add a column above");
		
		master.setInput(contentsModel);
	}
	
	private void setColumns() {
		if (master != null) {
			
			final Set<String> labels = new HashSet<>();
			
			for (final List<Object> k : columns) {
				final String label = Joiner.on(".").join(k);
				labels.add(label);
			}
			
			for (final TableColumn tc : master.getTable().getColumns()) {
				final String label = tc.getText();
				if (labels.contains(label)) {
					labels.remove(label);
				} else {
					tc.dispose();
				}
			}
			
			for (final List<Object> k : columns) {
				final String label = Joiner.on(".").join(k);
				
				if (!labels.contains(label)) continue; // we already have this column.
				
				final TableViewerColumn tvc = new TableViewerColumn(master, SWT.NONE);

				tvc.setLabelProvider(
						new ColumnLabelProvider() {
							@Override
							public String getText(final Object element) {
								if (element instanceof JsonValue) {
									final Object o = getColumn((JsonValue) element, k);
									return String.valueOf(o);
								}
								return "";
							}
						});
				
				tvc.getColumn().setWidth(200);
				tvc.getColumn().setData("path", k);
				tvc.getColumn().setText(label);
				tvc.getColumn().addSelectionListener(masterSorter);
				tvc.getColumn().addDisposeListener(masterSorter);
			}
			master.getTable().layout();
			master.refresh(true);
		}
	}
	
	@Override
	public void setFocus() {
		
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		if (event.getSelectionProvider() == master) {
			final ISelection sel = event.getSelection();
			if (sel instanceof IStructuredSelection) {
				final IStructuredSelection ssel = (IStructuredSelection) sel;
				final Object o = ssel.getFirstElement();
				if (o instanceof JsonValue) {
					detail.setInput(o);
				}
			}
		}
	}

}
