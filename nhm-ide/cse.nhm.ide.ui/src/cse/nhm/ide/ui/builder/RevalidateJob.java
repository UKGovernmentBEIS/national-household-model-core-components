package cse.nhm.ide.ui.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.resources.ResourcesPlugin;

import cse.nhm.ide.ui.NHMUIPlugin;

public class RevalidateJob extends Job {
	private IProject project;

	public RevalidateJob(final IProject p) {
		super("Revalidate scenarios in " + p.getName());
		this.project = p;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			project.build(IncrementalProjectBuilder.FULL_BUILD, NHMBuilder.BUILDER_ID, null, monitor);}
        catch (CoreException e) {
			NHMUIPlugin.logException("Revalidating scenarios", e);
		}
		return Status.OK_STATUS;
	}
}
