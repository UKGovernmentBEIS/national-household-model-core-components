package cse.nhm.ide.ui.results.efs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

import cse.nhm.ide.ui.results.NHMResultViewPlugin;

public class ZipFileFile extends ZipFileStore {
	private final ZipEntry entry;

	public ZipFileFile(final URI archiveURI, final ZipDirectory directory, final String name, final ZipEntry entry) {
		super(archiveURI, directory, name);
		this.entry = entry;
	}

	@Override
	public String[] childNames(final int options, final IProgressMonitor monitor) throws CoreException {
		return new String[0];
	}

	@Override
	public IFileStore getChild(final String name) {
		return this;
	}
	
	@Override
	public FileInfo fetchInfo(final int options, final IProgressMonitor monitor) throws CoreException {
		final FileInfo info = super.fetchInfo(options, monitor);
		info.setDirectory(false);
		info.setLastModified(this.entry.getTime());
		info.setLength(this.entry.getSize());
		info.setAttribute(EFS.ATTRIBUTE_OWNER_READ, true);
		info.setAttribute(EFS.ATTRIBUTE_OWNER_WRITE, true);
		return info;
	}

	@Override
	public InputStream openInputStream(final int options, final IProgressMonitor monitor) throws CoreException {
		final IFileStore archive = EFS.getStore(this.archiveURI);
		final File file = archive.toLocalFile(EFS.CACHE, new NullProgressMonitor());
		try {
			return new ZipFile(file).getInputStream(this.entry);
		} catch (final IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, NHMResultViewPlugin.PLUGIN_ID, e.getMessage()));
		}
	}
}
