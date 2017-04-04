package cse.nhm.ide.runner.ui.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerColumn;

import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.api.IScenarioRunner;
import cse.nhm.ide.runner.api.IScenarioRunnerListener;

public class GroupedRunContentProvider implements ITreeContentProvider, IScenarioRunnerListener {
	private final static Object[] E = new Object[0];
	private Viewer viewer;

	private final Runnable refresh = new Runnable() {
		@Override
		public void run() {
			viewer.refresh();
		}
	};

	private List<ViewerColumn> groupColumns = new ArrayList<>();

	private ScenarioRunnerTracker input;

	@Override
	public void dispose() {
		if (input instanceof ScenarioRunnerTracker) {
			((ScenarioRunnerTracker) input).removeListener(this);
		}
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
		this.input = null;
		if (oldInput instanceof ScenarioRunnerTracker)
			((ScenarioRunnerTracker) oldInput).removeListener(this);
		if (newInput instanceof ScenarioRunnerTracker) {
			((ScenarioRunnerTracker) newInput).addListener(this);
			this.input = (ScenarioRunnerTracker) newInput;
		}
	}

	@Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
	}

	@Override
    public Object[] getChildren(Object inputElement) {
        if (inputElement instanceof ScenarioRunnerTracker) {
            return ((ScenarioRunnerTracker) inputElement).getRuns().toArray();
        }
        return E;
	}

	@Override
	public Object getParent(Object element) {
        if (element instanceof IScenarioRun) {
            return input;
        }
        return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	@Override
	public void scenarioRunnerUpdated(IScenarioRunner runner, final Collection<? extends IScenarioRun> removed,
            final Collection<? extends IScenarioRun> changed, final Collection<? extends IScenarioRun> added) {
        viewer.getControl().getDisplay().asyncExec(refresh);
	}
}
