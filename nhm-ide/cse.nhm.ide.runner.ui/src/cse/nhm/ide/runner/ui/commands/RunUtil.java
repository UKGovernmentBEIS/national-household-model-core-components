package cse.nhm.ide.runner.ui.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.views.IViewRegistry;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;

import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.api.ScenarioSubmission;
import cse.nhm.ide.runner.api.IScenarioRunner;
import cse.nhm.ide.runner.ui.RunnerUIPlugin;
import cse.nhm.ide.runner.ui.preferences.RunnerUIPreferences;
import cse.nhm.ide.runner.ui.views.ScenarioRunnerView;
import cse.nhm.ide.ui.NHMUIPlugin;

public class RunUtil {
	public static final String SERVICE_NAME_PARAMETER = RunnerUIPlugin.PLUGIN_ID + ".serviceNameParameter";
	public static final String COMMAND_ID = "cse.nhm.ide.runner.ui.run";

	public static void run(final ExecutionEvent event, final Collection<IFile> input) {
		final List<IFile> toRun = new LinkedList<>(input);
		if (toRun.isEmpty())
			return;
		try {
			final Set<IFile> badFiles = new HashSet<>();

			// it might be useful at this point to ask the user if they want to add text to the job name?
			// annoyingly we don't have the list of names yet.
			final Set<String> existingNames = new HashSet<>();
			for (final IScenarioRunner service : RunnerUIPlugin.getDefault().tracker.getRunners()) {
				for (final IScenarioRun run : service.getScenarioRuns()) {
					existingNames.add(run.getName());
				}
			}

			final Set<String> duplicatedNames = new HashSet<>();
			for (final IFile i : input) {
				if (existingNames.contains(i.getName())) {
					duplicatedNames.add(i.getName());
				}
			}

			final String disambiguate;

			if (duplicatedNames.size() > 0) {
                final InputDialog i = new InputDialog(HandlerUtil.getActiveShell(event),
                        "Distinguishing note for run",
						"There are already runs named for the following scenarios:\n" +
						Joiner.on("\n - ").join(duplicatedNames) + ".\n" +
						"If you would like, you can supply a note to disambiguate the new runs:"
						, "", null);
				if (i.open() == Window.OK) {
					final String d = i.getValue();
					if (d.isEmpty()) {
						disambiguate = "";
					} else {
						disambiguate = " [" + d + "]";
					}
				} else {
					return;
				}
			} else {
				disambiguate = "";
			}

            final List<ScenarioSubmission> jobs = new LinkedList<>();

			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
				@Override
				public void run(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Running scenarios", 100 + 100 * toRun.size());
					final IWorkspace workspace = ResourcesPlugin.getWorkspace();

					// trigger build just in case...
					try {
						monitor.subTask("Validating scenarios");
						workspace.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, new SubProgressMonitor(monitor, 100));
					} catch (final CoreException e1) {
					}

					// check for validation errors, if there are any, make a warning.
					final Iterator<IFile> toRunIt = toRun.iterator();
					while (toRunIt.hasNext()){
						final IFile f = toRunIt.next();
						try {
							final int severity = f.findMaxProblemSeverity(NHMUIPlugin.PLUGIN_ID + ".scenarioProblem", true, IResource.DEPTH_INFINITE);
							if (severity >= IMarker.SEVERITY_ERROR) {
								toRunIt.remove();
								badFiles.add(f);
							}
						} catch (final CoreException e) {}
					}

                    final Optional<IScenarioRunner> runWith =
                        RunnerUIPreferences.findService(event.getParameter(SERVICE_NAME_PARAMETER));

                    if (!runWith.isPresent()) {
                        StatusManager.getManager().handle(new Status(IStatus.ERROR,
                                                                     RunnerUIPlugin.PLUGIN_ID,
                                                                     "No scenario running service could be found; your scenario cannot be run.\n"+
                                                                     "Contact your systems administrator."),
                                                          StatusManager.LOG | StatusManager.SHOW);
                        return;
                    }

                    final IScenarioRunner runWith_ = runWith.get();

					for (final IFile f : toRun) {
                        jobs.add(
                                 runWith_.submit(f.getName() + disambiguate, f, new SubProgressMonitor(monitor, 100))
                                 );
					}
				}
			});

			// select the jobs
			final IViewRegistry viewRegistry = PlatformUI.getWorkbench().getViewRegistry();
            viewRegistry.find(ScenarioRunnerView.ID);
            final List<IScenarioRun> jobs_ = new LinkedList<>();
            final StringBuffer dups = new StringBuffer();

            for (final ScenarioSubmission sub : jobs) {
                if (sub.duplicate) {
                    final String desiredName = sub.inputName;
                    final String realName = sub.run.getName();
                    if (realName.equals(desiredName)) {
                        dups.append("  " + realName + "\n");
                    } else {
                        dups.append("  " + desiredName + ", under the name " + realName + "\n");
                    }
                }
                jobs_.add(sub.run);
            }

            // this does not quite work, I think because it's in the wrong place in the eventloop.
			try {
				HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(ScenarioRunnerView.ID);
				final IViewPart part = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage()
					.findView(ScenarioRunnerView.ID);
                if (part != null) {
                    part.getSite().getSelectionProvider().setSelection(new StructuredSelection(jobs_));
                }
            } catch (final PartInitException e) {
				RunnerUIPlugin.error("Error activating the scenario runs view", e);
			}

            if (dups.length() > 0) {
                StatusManager.getManager().handle(new Status(IStatus.INFO,
                                                             RunnerUIPlugin.PLUGIN_ID,
                                                             "The following scenarios have been run already, and so will not be run again:\n"+
                                                             dups.toString() +
                                                             "This only happens when the model version, the scenario text and all includes, and the imported stock are all identical to those in a previous run." +
                                                             "\nIf you want to force a re-run, delete the existing run and try again."), StatusManager.LOG | StatusManager.SHOW);

            }

			if (!badFiles.isEmpty()) {
				final StringBuffer sb = new StringBuffer();
				for (final IFile f : badFiles) {
					sb.append(" - " + f.getFullPath().toString() + "\n");
				}
				StatusManager.getManager().handle(new Status(IStatus.ERROR,
						RunnerUIPlugin.PLUGIN_ID,
						"The following scenarios could not be run as they have validation errors.\n"+
						sb +
						"Consult the problems view to fix the errors, and try again."
						), StatusManager.LOG | StatusManager.SHOW);
			}
		} catch (final InvocationTargetException e) {
			RunnerUIPlugin.error("An error prevented this scenario from running", e.getCause());
		} catch (final InterruptedException e) {
			RunnerUIPlugin.error("An error prevented this scenario from running", e);
		}
	}
}
