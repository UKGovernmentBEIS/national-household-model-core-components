package cse.nhm.ide.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Arrays;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.ide.undo.CreateProjectOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;

import cse.nhm.ide.ui.NHMUIPlugin;
import cse.nhm.ide.ui.PluginPreferences.VersionPreference;
import cse.nhm.ide.ui.builder.NHMNature;

public class NHMProjectWizard extends Wizard implements INewWizard {
	private WizardNewProjectCreationPage firstPage;
	private ModelVersionSelectionPage versionPage;
	private ScenarioProjectDefaultsPage defaultsPage;
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {}

	@Override
	public void addPages() {
		setWindowTitle("Create a new NHM project.");
		firstPage = new WizardNewProjectCreationPage("Basic project details");
		firstPage.setTitle("Basic project details");
		firstPage.setDescription("Choose the name of the project, and optionally where on your computer to store the files.");
		addPage(firstPage);
		
		versionPage = new ModelVersionSelectionPage();
		addPage(versionPage);
		
		defaultsPage = new ScenarioProjectDefaultsPage();
		addPage(defaultsPage);
	}
	
	@Override
	public boolean performFinish() {
		final IProject projectHandle = firstPage.getProjectHandle();
		final URI location = firstPage.useDefaults() ? firstPage.getLocationURI() : null;

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProjectDescription description = workspace.newProjectDescription(projectHandle.getName());
		description.setLocationURI(location);
		final String[] natures = Arrays.copyOf(description.getNatureIds(), description.getNatureIds().length + 1);
		natures[natures.length - 1] = NHMNature.NATURE_ID;
		description.setNatureIds(natures);
		
		// for reasons unknown, this does not trigger the configure method in the nature
		// so we must also do what that would do
		try {
			NHMNature.configureProject(description);
		} catch (CoreException e) {
			NHMUIPlugin.logException("Creating new project", e);
		}
		
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						new CreateProjectOperation(description, "Creating new project...")
						   .execute(monitor, WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
					} catch (ExecutionException e) {
						throw new InvocationTargetException(e);
					}
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			NHMUIPlugin.logException("Creating new project", e);
			return false;
		}
		
		
		final VersionPreference selectedVersion = versionPage.getSelectedVersion();
		selectedVersion.save(projectHandle);
		
		defaultsPage.createScenario(projectHandle);
		
		return true;
	}
}
