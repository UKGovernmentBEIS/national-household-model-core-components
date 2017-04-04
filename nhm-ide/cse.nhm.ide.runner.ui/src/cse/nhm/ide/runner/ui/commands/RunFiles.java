package cse.nhm.ide.runner.ui.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Run command handler for when selection is a single or multiple files, or some containers
 * in which case the user is asked which things to run.
 */
public class RunFiles extends AbstractHandler {
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection sel = HandlerUtil.getCurrentSelection(event);
		
		final Set<IFile> files = new HashSet<>();
		final Set<IFile> contained = new HashSet<>();
		
		if (sel instanceof IStructuredSelection) {
			final IStructuredSelection ssel = (IStructuredSelection) sel;
			for (final Object o : ssel.toArray()) {
				if (o instanceof IFile && ((IFile) o).getFileExtension().equals("nhm")) {
					files.add((IFile)o);
				} else if (o instanceof IContainer) {
					try {
						((IContainer) o).accept(new IResourceVisitor() {
							@Override
							public boolean visit(final IResource resource) throws CoreException {
								if (resource instanceof IFile && resource.getFileExtension().equals("nhm")) {
									contained.add((IFile) resource);
								}
								return true;
							}
						});
					} catch (final CoreException e) {}
				}
			}
			
			contained.removeAll(files);
			
			if (contained.size() > 1) {
				// if there is only one thing, just run it. whatever.
				final Object[] containedA = contained.toArray();
				final ListSelectionDialog lsd = new ListSelectionDialog(HandlerUtil.getActiveShell(event), 
						containedA,
						new ArrayContentProvider(), 
						new WorkbenchLabelProvider(), 
						"The selection contains scenarios from inside projects and folders. \n" + 
						"Select which of these you would like to run:");
				lsd.setInitialSelections(containedA);
				final int result = lsd.open();
				if (result == Window.CANCEL) return null;
				files.addAll((Collection<? extends IFile>) Arrays.asList(lsd.getResult()));
			} else {
				files.addAll(contained);
			}
			
			// now we have many files to run
			RunUtil.run(event, files);
		}
		return null;
	}
}
