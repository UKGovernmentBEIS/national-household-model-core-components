package cse.nhm.ide.runner.ui.views;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.services.IEvaluationService;

import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.ui.RunnerUIPlugin;

public class ScenarioRunnerView extends ViewPart {
	static final SimpleDateFormat DTFM = new SimpleDateFormat("YYYY-MM-dd HH:mm");
	
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "cse.nhm.ide.runner.ui.views.ScenarioRunnerView";

	private TreeViewer viewer;

	/**
	 * The constructor.
	 */
	public ScenarioRunnerView() {
	}

    private enum ColumnType {
        // this gives the order the columns appear in
        NAME,
        USER,
        STATE,
        DATE,
        SERVER,
        VERSION;
		
		private Double runState(final IScenarioRun run) {
			switch (run.getState()) {
			case ERROR:
			case FINISHED:
				return 2d;
			case QUEUED:
				return -1d;
			case STARTED:
				return run.getProgress();
			default:
				return 3d;
			}
		}
		
		public int compare(final IScenarioRun a, final IScenarioRun b) {
			switch (this) {
			case DATE: return a.getQueuedDate().compareTo(b.getQueuedDate());
			case NAME: return a.getName().compareTo(b.getName());
			case SERVER: return a.getRunner().getName().compareTo(b.getRunner().getName());
			case STATE: return runState(a).compareTo(runState(b));
            case VERSION: return a.getVersion().compareTo(b.getVersion());
            case USER: return a.getUser().compareTo(b.getUser());
			default: return 0;
			}
		}
		
		public String format(final IScenarioRun run) {
			switch (this) {
			case DATE: return DTFM.format(run.getQueuedDate());
			case NAME: return run.getName();
			case SERVER: return run.getRunner().getName();
			case STATE:
				switch (run.getState()) {
				case ERROR:
					return "Failed";
				case FINISHED:
					return "Complete";
				case QUEUED:
					return "Waiting";
				case STARTED:
					if (run.getProgress() == 0d) {
						return "Started";
					}
					return String.format("%d%% complete",(int) (run.getProgress() * 100));
				default:
					return "??";
				}
            case VERSION: return run.getVersion();
            case USER: return run.getUser();
			default: return "unknown";
			}
		}
	}
	
	static class ColumnTypeLabelProvider extends ColumnLabelProvider {
		private final ColumnType col;
		
		public ColumnTypeLabelProvider(final ColumnType col) {
			super();
			this.col = col;
		}

		@Override
		public String getText(final Object element) {
			if (element instanceof IScenarioRun) {
				return col.format((IScenarioRun) element);
			} else {
				return "";
			}
		}
	}
	
	public static void shuffle(final int[] sortOrder, final ColumnType type) {
		final int t = type.ordinal() + 1;
		if (sortOrder[0] == t || sortOrder[0] == -t) {
			sortOrder[0] *=-1; // flip column
		} else {
			// insert column at front and move other columns down
			int previous = t;
			int temp = 0;
			for (int i = 0; i<sortOrder.length; i++) {
				temp = sortOrder[i];
				sortOrder[i] = previous;
				previous = temp;
				if (previous == t || previous == -t) break;
			}
		}
	}
	
	@Override
	public void createPartControl(final Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
		viewer.setContentProvider(new GroupedRunContentProvider());

		viewer.getTree().setLinesVisible(true);
		viewer.getTree().setHeaderVisible(true);
		
		/**
		 * Index 0 is the first column to sort by;
		 * its value is the ordinal of the ColumnType to use plus 1, or negative that if the column is sorted backwards.
		 */
		final int[] sortOrder = new int[ColumnType.values().length];
		for (final ColumnType ct : ColumnType.values()) {
			sortOrder[ct.ordinal()] = ct.ordinal() + 1;
		}
        // default sort order here, from least to most important.

		shuffle(sortOrder, ColumnType.NAME);
		shuffle(sortOrder, ColumnType.STATE);
		shuffle(sortOrder, ColumnType.SERVER);
		shuffle(sortOrder, ColumnType.DATE);
        shuffle(sortOrder, ColumnType.USER);
		
		final ViewerSorter sorter = new ViewerSorter() {
			@Override
			public int compare(final Viewer viewer, final Object e1, final Object e2) {
				if (e1 instanceof IScenarioRun && e2 instanceof IScenarioRun) {					
					for (final int i : sortOrder) {
						final ColumnType c = ColumnType.values()[Math.abs(i) - 1];
						final int sign = i<0 ? -1 : 1;
						final int compare = c.compare((IScenarioRun)e1, (IScenarioRun)e2);
						if (compare == 0) continue;
						return compare * sign;
					}
					return 0;
				} else {
					return super.compare(viewer, e1, e2);
				}
			}
		};
		
		viewer.setSorter(sorter);
		
		final SelectionListener sortListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final TreeColumn column = (TreeColumn) e.getSource();
				final ColumnType type = (ColumnType) column.getData(ColumnType.class.getName());
				
				shuffle(sortOrder, type);
				
				column.setText((sortOrder[0] < 0 ? "-" : "")  + type.name());
				
				viewer.refresh();
			}
		};
		
		for (final ColumnType type : ColumnType.values()) {
			final TreeViewerColumn col = addColumn(viewer, type);
			col.getColumn().addSelectionListener(sortListener);
		}
		
		// TODO add more columns; make into flat list with group buttons?
		viewer.setInput(RunnerUIPlugin.getDefault().tracker);
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "cse.nhm.ide.runner.ui.viewer");
		getSite().setSelectionProvider(viewer);
		hookContextMenu();
		
		viewer.addDragSupport(DND.DROP_COPY | DND.DROP_MOVE, 
				new Transfer[] {FileTransfer.getInstance()}, 
				new ResultDragSourceListener(viewer));
		
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(final DoubleClickEvent event) {
				viewer.setSelection(event.getSelection());
				final ICommandService service = getViewSite().getService(ICommandService.class);
				final IEvaluationService e = getViewSite().getService(IEvaluationService.class);
				final Command command = service.getCommand("cse.nhm.ide.runner.ui.get");
				try {
					command
						.executeWithChecks(new ExecutionEvent(
								command,
								new HashMap<>(),
								ScenarioRunnerView.this,
								e.getCurrentState()));
				} catch (ExecutionException | NotDefinedException | NotEnabledException | NotHandledException err) {
				}
			}
		});
	}

	private static TreeViewerColumn addColumn(final TreeViewer viewer, final ColumnType type) {
		final TreeViewerColumn nameColumn = new TreeViewerColumn(viewer, SWT.LEFT);
		nameColumn.getColumn().setText(type.name());
		nameColumn.getColumn().setWidth(200);
		nameColumn.setLabelProvider(new ColumnTypeLabelProvider(type));
		nameColumn.getColumn().setData(ColumnType.class.getName(), type);
		return nameColumn;
	}

	private void hookContextMenu() {
		final MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(final IMenuManager manager) {
//				ScenarioRunnerView.this.fillContextMenu(manager);
			}
		});
		final Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
