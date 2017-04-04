package cse.nhm.ide.runner.ui.views;

import java.io.IOException;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import com.google.common.io.CharStreams;

import java.util.Collection;
import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.api.IScenarioRunner;
import cse.nhm.ide.runner.api.IScenarioRunnerListener;

import org.eclipse.swt.widgets.Display;

public class LogView extends ViewPart implements ISelectionListener, IScenarioRunnerListener {
    public static final String ID = "cse.nhm.ide.runner.ui.views.LogView";

    private IScenarioRun content = null;
    private Text text;

	public LogView() {
		
	}

	@Override
	public void createPartControl(final Composite parent) {
		text = new Text(parent, SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.MULTI);

        getSite().getWorkbenchWindow().getSelectionService().addPostSelectionListener(ScenarioRunnerView.ID, this);

        updateSelection();
    }

	@Override
	public void setFocus() {
		text.setFocus();

        updateSelection();
    }

    private void updateSelection() {
        selectionChanged(getSite().getWorkbenchWindow().getActivePage().findView(ScenarioRunnerView.ID),
                         getSite().getWorkbenchWindow().getSelectionService().getSelection(ScenarioRunnerView.ID));
    }

	@Override
	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService().removePostSelectionListener(
				ScenarioRunnerView.ID, this);
        super.dispose();

        if (content != null) {
            content.getRunner().removeListener(this);
            content = null;
        }
	}

	@Override
    public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			if (((IStructuredSelection) selection).size() == 1) {
				final Object o = ((IStructuredSelection) selection).iterator().next();
                if (o instanceof IScenarioRun) {
                    final IScenarioRun r = (IScenarioRun) o;

                    if (this.content == r) {
                        update();
                        return;
                    }

                    if (content != null) {
                        content.getRunner().removeListener(this);
                        content = null;
                    }

                    this.content = r;

                    if (content != null) {
                        content.getRunner().addListener(this);
                    }

                    update();

                    return;
				}
			}
        }

        if (content != null) {
            content.getRunner().removeListener(this);
            content = null;
        }

        update();
    }

    protected void update() {
        if (Display.getCurrent() == null) {
            Display.getDefault().asyncExec(new Runnable() {
                    @Override public void run() { LogView.this.update(); }
                });
        } else {
            if (content != null) {
                try {
                    final String log = CharStreams.toString(content.getLogText());
                    text.setText(log.isEmpty() ? "No log output yet..." : log);
                    text.setSelection(text.getText().length());
                } catch (final IOException e) {
                    text.setText(e.getMessage());
                }
            } else {
                text.setText("No selection");
            }
        }
    }

    @Override
    public void scenarioRunnerUpdated(final IScenarioRunner runner,
                                      final Collection<? extends IScenarioRun> removed,
                                      final Collection<? extends IScenarioRun> changed,
                                      final Collection<? extends IScenarioRun> added) {
        if (content == null || removed.contains(content)) {
            runner.removeListener(this);
            content = null;
            update();
        } else if (changed.contains(content)) {
            update();
        }
    }
}
