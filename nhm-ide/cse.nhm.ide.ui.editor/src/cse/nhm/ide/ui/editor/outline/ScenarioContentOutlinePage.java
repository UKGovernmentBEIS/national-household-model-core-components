package cse.nhm.ide.ui.editor.outline;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import cse.nhm.ide.ui.editor.ScenarioEditor;
import cse.nhm.ide.ui.editor.ScenarioModel;
import cse.nhm.ide.ui.editor.ScenarioModel.Node;

public class ScenarioContentOutlinePage extends ContentOutlinePage {
	ScenarioModel lastModel;
	private final ScenarioEditor scenarioEditor;
	
	public ScenarioContentOutlinePage(final ScenarioEditor scenarioEditor) {
		this.scenarioEditor = scenarioEditor;
	}

	public void updateModel(final ScenarioModel model) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				final TreeViewer v = getTreeViewer();
				if (v != null) {
					v.setInput(model);
					v.expandToLevel(1);
				}
				ScenarioContentOutlinePage.this.lastModel = model;
			}
		});
	}
	
	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		getTreeViewer().setContentProvider(new ScenarioContentProvider());
		getTreeViewer().setLabelProvider(new ScenarioLabelProvider());
		getTreeViewer().setInput(this.lastModel);
		getTreeViewer().addSelectionChangedListener(this);
	}
	
	static class ScenarioLabelProvider extends LabelProvider {
		@Override
		public String getText(final Object element) {
			if (element instanceof Node) {
				final Node node = (Node) element;
				if (node.name.isEmpty()) {
					return node.type; 
				} else {
					return node.type + " : " + node.name;
				}
			}
			return String.valueOf(element);
		}
	}
	
	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		final ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection sel = (IStructuredSelection) selection;
			final Object o = sel.getFirstElement();
			this.scenarioEditor.display(o);
		}
	}
	
	static class ScenarioContentProvider implements ITreeContentProvider {
		private static final Object[] NOTHING = {};

		@Override
		public void dispose() {}

		// not using an event driven model thing.
		@Override
		public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {}

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof ScenarioModel) {
				return ((ScenarioModel) inputElement).getNodes().toArray();
			} else {
				return NOTHING;
			}
		}

		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof Node) {
				return ((Node) parentElement).children.toArray();
			} else {
				return NOTHING;
			}
		}

		@Override
		public Object getParent(final Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(final Object object) {
			if (object instanceof ScenarioModel) {
				return !((ScenarioModel) object).getNodes().isEmpty();
			} else if (object instanceof Node) {
				return !((Node) object).children.isEmpty();
			} else {
				return false;
			}
		}
	}
}
