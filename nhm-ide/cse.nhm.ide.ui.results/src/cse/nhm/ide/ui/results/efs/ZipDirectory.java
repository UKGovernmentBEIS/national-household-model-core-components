package cse.nhm.ide.ui.results.efs;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import cse.nhm.ide.ui.results.NHMResultViewPlugin;

public class ZipDirectory extends ZipFileStore {
	private final Map<String, ZipFileStore> children = new TreeMap<>();
	
	public ZipDirectory(final URI archiveURI) {
		super(archiveURI);
	}
	
	public ZipDirectory(final URI archiveURI, final ZipDirectory directory, final String name) {
		super(archiveURI, directory, name);
	}

	public boolean hasChildren() {
		return !this.children.isEmpty();
	}

	@Override
	public FileInfo fetchInfo(final int options, final IProgressMonitor monitor) throws CoreException {
		final FileInfo fi = super.fetchInfo(options, monitor);
		
		fi.setDirectory(true);
		fi.setAttribute(EFS.ATTRIBUTE_OWNER_READ, true);
		fi.setAttribute(EFS.ATTRIBUTE_OWNER_WRITE, true);
		fi.setAttribute(EFS.ATTRIBUTE_OWNER_EXECUTE, true);
		
		return fi;
	}
	
	public ZipDirectory getSubdirectory(final String string) {
		if (!this.children.containsKey(string)) {
			this.children.put(string, new ZipDirectory(this.archiveURI, this, string));
		}
		final ZipFileStore s = this.children.get(string);
		if (s instanceof ZipDirectory) {
			return ((ZipDirectory) s);
		} else {
			throw new UnsupportedOperationException(string + " is represented twice in this zip file, once as a directory and once as a file");
		}
	}

	public void addFile(final ZipFileFile zipFileFile) {
		if (this.children.containsKey(zipFileFile.getName())) {
			throw new UnsupportedOperationException(zipFileFile.getName() + " is represented twice in this zip file");
		}
		this.children.put(zipFileFile.getName(), zipFileFile);
	}

	@Override
	public String[] childNames(final int options, final IProgressMonitor monitor) throws CoreException {
		return this.children.keySet().toArray(new String[this.children.keySet().size()]);
	}

	@Override
	public IFileStore getChild(final String name) {
		return this.children.get(name);
	}

	@Override
	public InputStream openInputStream(final int options, final IProgressMonitor monitor) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, NHMResultViewPlugin.PLUGIN_ID, String.format("%s is a directory", toURI())));
	}
}
