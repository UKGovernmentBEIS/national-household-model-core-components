package cse.nhm.ide.ui.results.nav;

import java.io.IOException;
import java.util.zip.ZipInputStream;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import cse.nhm.ide.ui.results.NHMResultViewPlugin;
import cse.nhm.ide.ui.results.efs.ZipDirectory;
import cse.nhm.ide.ui.results.efs.ZipFileSystem;

public class ZipContentProvider implements ITreeContentProvider {
	private static final Object[] NOTHING = new Object[0];

	@Override public void dispose() {}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {}

	@Override
	public Object[] getElements(final Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		IFileStore store = null;
		if (parentElement instanceof IFile) {
			final IFile file = (IFile) parentElement;
			try {
				store = EFS.getStore(ZipFileSystem.createZipURI(file.getLocationURI()));
			} catch (final CoreException e) {}
		} else if (parentElement instanceof IFileStore) {
			store = (IFileStore) parentElement;
		}
		if (store != null) {
			try {
				final String[] childNames = store.childNames(EFS.NONE, new NullProgressMonitor());
				final Object[] result = new Object[childNames.length];
				for (int i = 0; i<childNames.length; i++) {
					result[i] = store.getChild(childNames[i]);
				}
				return result;
			} catch (final CoreException e) {
				NHMResultViewPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, NHMResultViewPlugin.PLUGIN_ID, e.getMessage()));
			}
		}
		return NOTHING;
	}

	@Override
	public Object getParent(final Object element) {
		if (element instanceof IFileStore) {
			return ((IFileStore) element).getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof IFile) {
			final IFile file = (IFile) element;
			try (final ZipInputStream zis = new ZipInputStream(file.getContents())) {
				if (zis.getNextEntry() != null) return true;
			} catch (final IOException e) {
			} catch (final CoreException e) {
			}
		} else if (element instanceof ZipDirectory) {
			return ((ZipDirectory) element).hasChildren();
		}
		return false;
	}
}
