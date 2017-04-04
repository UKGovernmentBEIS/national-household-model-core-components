package cse.nhm.ide.ui.results.nav;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import cse.nhm.ide.ui.results.efs.ZipFileStore;
import cse.nhm.ide.ui.results.efs.ZipFileSystem;

/**
 * Either unzips a file/folder from a zip file, or extracts all the contents of a zip file in one go.
 * 
 * @author hinton
 *
 */
public class UnzipCommandHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final ISelectionService selectionService = HandlerUtil.getActiveWorkbenchWindow(event).getSelectionService();
		final ISelection selection = selectionService.getSelection();
		
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.isEmpty()) return null;
			final int count = structuredSelection.size();
			// ask for target directory, maybe also tickbox for whether to preserve folder structure
			
			final DirectoryDialog dialog = new DirectoryDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
			final String pathS = dialog.open();
			
			if (pathS != null) {
				final Path outputPath = Paths.get(pathS);
				try {
					final IFileStore output = EFS.getStore(outputPath.toUri());
					PlatformUI.getWorkbench().getProgressService()
					.busyCursorWhile(new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
							monitor.beginTask("Unzipping selected files", count * 100);
							
							for (final Object o : structuredSelection.toList()) {
								if (o instanceof ZipFileStore) {
									// extract element from zip file
									// we can do this fairly directly
									final SubProgressMonitor spm = new SubProgressMonitor(monitor, 100);
									try {
										((ZipFileStore) o).copy(
												output.getChild(((ZipFileStore) o).getName())
												, EFS.OVERWRITE, spm);
									} catch (CoreException e) {
										throw new RuntimeException(e.getMessage(), e);
									}
								} else if (o instanceof IFile) {
									// extract entire zip file.
									// get a zipfile store for the thing
									// and then copy that
									try {
										final SubProgressMonitor spm = new SubProgressMonitor(monitor, 100);
										IFileStore src = EFS.getStore(ZipFileSystem.createZipURI(((IResource) o).getLocationURI()));
										final IFileStore target = output.getChild(((IFile) o).getFullPath().removeFileExtension().lastSegment());
										target.mkdir(0, null);
										IFileInfo info = target.fetchInfo();
										info.setAttribute(EFS.ATTRIBUTE_OWNER_WRITE, true);
										info.setAttribute(EFS.ATTRIBUTE_OWNER_EXECUTE, true);
										target.putInfo(info, EFS.SET_ATTRIBUTES, null);
										src.copy(target, EFS.OVERWRITE, spm);
									} catch (CoreException e) {
										throw new RuntimeException(e.getMessage(), e);
									}
								}
							}
						}
					});
				} catch (InvocationTargetException | InterruptedException | CoreException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
		
		return null;
	}

}
