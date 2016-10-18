package cse.nhm.ide.runner.ui.commands;

import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for run button when active part is a scenario editor
 * 
 * Checks whether editor content is saved and valid before running
 */
public class RunFromEditor extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IEditorPart part = HandlerUtil.getActiveEditor(event);
		if (part != null) {
			final boolean dirty = part.isDirty();
			
			
			final IEditorInput input = (IEditorInput) part.getEditorInput();
			
			if (input instanceof IPathEditorInput) {
				// check path is in workspace; if it is not, we can't resolve things properly.
				final IPath path = ((IPathEditorInput) input).getPath();				
				final IWorkspace workspace = ResourcesPlugin.getWorkspace();    
				final IFile[] res = workspace.getRoot().findFilesForLocationURI(path.toFile().toURI());
				if (res.length > 0) {
					// did find a resource to run
					if (!res[0].getFileExtension().equals("nhm")) {
						MessageDialog.openWarning(
								HandlerUtil.getActiveShell(event), 
								res[0] + " is not a scenario", 
								res[0] + " is not a scenario (or it does not have the .nhm file extension)."
								+ "\nOnly files with the .nhm file extension can be run.");
					}
					if (dirty) {
						// display message, trigger save, wait for validate, run
						final int result = askAboutSaving(
								HandlerUtil.getActiveShell(event),
								res[0].getName());
						switch (result) {
						case 0:
							// do save
							part.doSave(new NullProgressMonitor());
							if (part.isDirty()) {
								MessageDialog.openWarning(
										HandlerUtil.getActiveShell(event), 
										res[0].getName() + " did not save", 
										res[0].getName() + " could not be saved, or run.");
								return null;
							}
							break;
						case 1:
							// don't save, but do run
							break;
						default:
							// cancel
							return null;
						}
					}
					// we have dealt with the unsaved changes, now we can run it if we like.
					RunUtil.run(event, Collections.singletonList(res[0]));
				}
			}
		}
		
		return null;	
	}

	private static int askAboutSaving(Shell shell, String name) {
		final MessageDialog md = 
				new MessageDialog(shell, 
						"Save " + name + " before running?", 
						null, 
						name + " has un-saved changes."+
						"\nIf you run it without saving, these changes will not be reflected in the result.", 
						MessageDialog.QUESTION, 
						new String[]{
								"Save before running",
								"Run without saving",
								"Cancel"
						}, 
						0);
		return md.open();
	}
}
